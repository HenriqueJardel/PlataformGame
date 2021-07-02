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

public class Passaro extends Inimigo {

    private Texture [] voando;
    private float delay;

    public Passaro(World world, float posX, float posY) {
        super(world,posX,posY);
        this.voando = new Texture[9];
        for (int i = 0; i < voando.length; i++) {
            voando[i] = Assets.manager.get("inimigos/passaro/voando-" + i + ".png");
        }
    }

    @Override
    protected void criarCorpo(float posX, float posY) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX,posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = 0f;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(6/100f);
        fdef.shape = shape;
        fdef.filter.categoryBits = PlataformGame.ENEMY_BIT;
        body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-12, 8).scl(1/100f);
        vertice[1] = new Vector2(12, 8).scl(1/100f);
        vertice[2] = new Vector2(-4, 2).scl(1/100f);
        vertice[3] = new Vector2(4, 2).scl(1/100f);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 3f;
        fdef.filter.categoryBits = PlataformGame.ENEMY_HEAD;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(voando[(int) frames % 9], body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (6/100f) * 2,
                (6/100f) * 4 , (6/100f) * 4);
    }

    @Override
    public void update(float delta) {
        this.frames += 10 * delta;
        this.time += 10 * delta;

        if (estadoAtual != State.MORTO) {

            if (time < 25f) {
                body.setLinearVelocity(new Vector2(0, 0.2f));
            }

            if (time > 45f && time < 70) {
                body.setLinearVelocity(new Vector2(0, -0.2f));
            }

            if (time > 90f) {
                time = 0;
            }
        }
        else {
            if (time > delay && !isDestroy) {
                isDead = true;
                isDestroy = true;
                world.destroyBody(body);
            }
        }

    }

    @Override
    public void hit() {
        estadoAtual = State.MORTO;
        delay = time + 20f;
    }
}

