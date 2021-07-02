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

public class Cogumelo extends Inimigo {

    private boolean esquerda;

    private Texture[] parado;
    private Texture [] andando;
    private Texture morto;
    private float delay;
    private float x;
    private float y;


    public Cogumelo(World world, float posX, float posY) {
        super(world, posX, posY);
        this.esquerda = false;
        this.parado = new Texture[14];
        for (int i = 0; i < parado.length; i++) {
            parado[i] = Assets.manager.get("inimigos/cogumelo/parado-"+i+".png");
        }

        this.andando = new Texture[16];
        for (int i = 0; i < andando.length; i++) {
            andando[i] = Assets.manager.get("inimigos/cogumelo/run-" + i + ".png");
        }

        this.morto = Assets.manager.get("inimigos/cogumelo/morto.png");
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
            batch.draw(parado[(int) frames % 14], body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,parado[(int) frames % 14].getWidth(), parado[(int) frames % 14].getHeight(), esquerda, false);
        if (estadoAtual == State.MOVIMENTANDO)
            batch.draw(andando[(int) frames % 16], body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,andando[(int) frames % 16].getWidth(), andando[(int) frames % 16].getHeight(), esquerda, false);
        if (estadoAtual == State.MORTO)
            batch.draw(morto, x - (6/100f) * 2 ,y - (5/100f) ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,morto.getWidth(), morto.getHeight(), esquerda, false);
    }

    @Override
    public void update(float delta) {
        frames += 10 * delta;
        time += 10 * delta;

        if (estadoAtual != State.MORTO) {

            if (time < 25f && body.getLinearVelocity().x < 0.4) {
                estadoAtual = State.MOVIMENTANDO;
                esquerda = true;
                body.applyLinearImpulse(new Vector2(0.15f, 0), body.getWorldCenter(), true);
            }
            if (time > 25f && time < 45f) {
                estadoAtual = State.PARADO;
            }

            if (time > 45f && time < 70 && body.getLinearVelocity().x > -0.4) {
                estadoAtual = State.MOVIMENTANDO;
                esquerda = false;
                body.applyLinearImpulse(new Vector2(-0.15f, 0), body.getWorldCenter(), true);
            }

            if (time > 70f && time < 90f) {
                estadoAtual = State.PARADO;
            }

            if (time > 90f) {
                time = 0;
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
