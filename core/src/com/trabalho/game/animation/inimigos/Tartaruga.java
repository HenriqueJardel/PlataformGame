package com.trabalho.game.animation.inimigos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.trabalho.game.PlataformGame;
import com.trabalho.game.tools.Assets;

public class Tartaruga extends Inimigo {

    private Texture[] andando;
    private boolean esquerda;

    public Tartaruga(World world, float posX, float posY) {
        super(world,posX,posY);
        this.andando = new Texture[14];

        for (int i = 0;i < andando.length; i++) {
            andando[i] = Assets.manager.get("inimigos/tartaruga/andando-" + i + ".png");
        }
        this.esquerda = false;
    }

    @Override
    protected void criarCorpo(float posX, float posY) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX, posY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        this.body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(6 / 100f);
        fdef.shape = shape;
        fdef.filter.categoryBits = PlataformGame.ENEMY_BIT;
        this.body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(andando[(int) frames % 14], body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                (6/100f) * 4 , (6/100f) * 3,0,0,andando[(int) frames % 14].getWidth(), andando[(int) frames % 14].getHeight(), esquerda, false);
    }

    @Override
    public void update(float delta) {
        this.frames += 10 * delta;
        this.time += 10 * delta;

        if (time < 25f && body.getLinearVelocity().x < 0.4) {
            esquerda = true;
            body.applyLinearImpulse(new Vector2(0.15f, 0), body.getWorldCenter(), true);
        }

        if (time > 45f && time < 70 && body.getLinearVelocity().x > -0.4) {
            esquerda = false;
            body.applyLinearImpulse(new Vector2(-0.15f, 0), body.getWorldCenter(), true);
        }

        if (time > 90f) {
            time = 0;
        }

    }

    @Override
    public void hit() {

    }
}
