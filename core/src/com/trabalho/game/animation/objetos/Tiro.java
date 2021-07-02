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

public class Tiro {

    private World world;
    private Body body;
    private Texture texture;
    private float posIncial;
    private boolean hit;
    private boolean shot;
    private boolean isDestroy;


    public Tiro(World world, float posX , float posY) {
        this.world = world;
        this.posIncial = posX;
        this.shot = false;
        this.hit = false;
        this.isDestroy = false;
        this.texture = Assets.manager.get("inimigos/arvore/bala.png");
        criarCorpo(posX, posY);

    }

    public void criarCorpo(float posX, float posY) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX,posY);
        bdef.gravityScale = 0f;
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(3/100f);
        fdef.shape = shape;
        fdef.isSensor=true;
        fdef.filter.categoryBits = PlataformGame.SHOT;
        fdef.restitution = 0f;
        body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        batch.draw(texture, body.getPosition().x - (3/100f) * 2, body.getPosition().y - (3/100f), (3/100f) * 4, (3/100f) * 4);
    }

    public void update() {
        if(!hit) {
            this.body.setLinearVelocity(-1f, 0);

            if (body.getPosition().x < posIncial - 2.5f) {
                hit = true;
            }

        }
        else {
            if(!isDestroy) {
                world.destroyBody(body);
                isDestroy = true;
                shot = true;
            }

        }
    }

    public void hit() {
        this.hit = true;
    }

    public boolean isShot() {
        return shot;
    }
}
