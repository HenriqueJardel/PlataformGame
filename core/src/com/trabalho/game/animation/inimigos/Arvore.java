package com.trabalho.game.animation.inimigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.trabalho.game.PlataformGame;
import com.trabalho.game.tools.Assets;

public class Arvore extends Inimigo{

    private Texture [] parado;
    private Texture [] atacando;
    private Texture morto;
    private float delay;
    private float x;
    private float y;

    private boolean jaAtacou;

    public Arvore(World world, float posX, float posY) {
        super(world, posX, posY);

        this.parado = new Texture[18];
        for(int i = 0; i < parado.length; i++) {
            parado[i] = Assets.manager.get("inimigos/arvore/parado-" + i + ".png");
        }

        this.atacando = new Texture[11];
        for(int i = 0; i < atacando.length; i++) {
            atacando[i] = Assets.manager.get("inimigos/arvore/atacando-" + i + ".png");
        }

        this.morto = Assets.manager.get("inimigos/arvore/morto.png");

        this.delay = 0;
    }

    @Override
    protected void criarCorpo(float posX, float posY) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX,posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(6/100f);
        fdef.shape = shape;
        fdef.filter.categoryBits = PlataformGame.ENEMY_BIT;
        body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-7, 8).scl(1/100f);
        vertice[1] = new Vector2(7, 8).scl(1/100f);
        vertice[2] = new Vector2(-3, 3).scl(1/100f);
        vertice[3] = new Vector2(3, 3).scl(1/100f);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 1.2f;
        fdef.filter.categoryBits = PlataformGame.ENEMY_HEAD;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        if (estadoAtual == State.PARADO)
        batch.draw(parado[(int) frames % 18], body.getPosition().x - (6/100f) * 3,body.getPosition().y - (6/100f),
                    (6/100f) * 6 , (6/100f) * 3);
        if (estadoAtual == State.ATACANDO)
        batch.draw(atacando[(int) frames % 11], body.getPosition().x - (6/100f) * 3 ,body.getPosition().y - (6/100f),
                (6/100f) * 6 , (6/100f) * 3);
        if (estadoAtual == State.MORTO)
            batch.draw(morto, x - (6/100f) * 3 ,y - (6/100f),
                    (6/100f) * 6 , (6/100f) * 3);
    }

    @Override
    public void update(float delta) {
        this.frames += 10 * delta;
        this.time += 10 * delta;

        if (this.estadoAtual != State.MORTO) {
            if (time < 20f) {
                this.estadoAtual = State.PARADO;
            }
            else if(time > 20f  && time < 30f) {
                this.estadoAtual = State.ATACANDO;
            }
            else if (time > 30f && time < 31f) {
                if (!jaAtacou) {
                    this.isAtack = true;
                    this.jaAtacou = true;
                }
            }
            else {
                this.time = 0;
                this.jaAtacou = false;
            }
        }
        else {
            if (!isDestroy) {
                isDestroy = true;
                world.destroyBody(body);
            }

            if (time > delay ) {
                isDead = true;
            }
        }

    }

    @Override
    public void hit() {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        estadoAtual = State.MORTO;
        delay = time + 10f;
    }
}
