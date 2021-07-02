package com.trabalho.game.animation.objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.trabalho.game.PlataformGame;
import com.trabalho.game.tools.Assets;

public class Moeda {

    private Body body;
    private World world;
    private Texture [] active;
    private float frames;
    private boolean pega;
    private boolean isDestroy;

    public Moeda(World world , float posX, float posY) {
        this.active = new Texture[5];
        for (int i = 0; i < active.length; i++) {
            active[i] = Assets.manager.get("objetos/moeda-" + i + ".png");
        }

        this.world = world;
        this.pega = false;
        this.isDestroy = false;
        criarCorpo(posX, posY);
    }

    public void criarCorpo(float posX, float posY) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX,posY);
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(6/100f);
        fdef.shape = shape;
        fdef.isSensor=true;
        fdef.filter.categoryBits = PlataformGame.COIN;
        fdef.restitution = 0f;
        body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!pega)
        batch.draw(active[(int) frames % 5], body.getPosition().x - (6/100f) ,body.getPosition().y - (6/100f),
                (6/100f) * 2, (6/100f) * 2);
    }

    public void update(float delta) {
        if(!pega)
            frames += 10 * delta;
        else {
            if(!isDestroy){
            this.world.destroyBody(body);
            this.isDestroy = true;
            }
        }
    }

    public void hit() {
        pega = true;
    }

    public boolean getPega() {
        return pega;
    }

    public float getPosition() {
        return body.getPosition().x;
    }
}
