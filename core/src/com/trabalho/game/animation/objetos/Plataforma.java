package com.trabalho.game.animation.objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.trabalho.game.tools.Assets;

public class Plataforma {

    private Body body;
    private World world;
    private Texture plataform;
    private boolean toFall;
    private boolean isFalling;
    private boolean itFell;
    private float time;
    private float delay;
    private float width;
    private float height;

    public Plataforma(World word, float posX, float posY, float width, float height, boolean toFall) {
        this.world = word;
        this.plataform = Assets.manager.get("objetos/plataforma.png");
        this.width = width;
        this.height = height;
        this.delay = 0;
        this.toFall = toFall;
        this.isFalling = false;
        this.itFell = false;
        criarCorpo(posX, posY, width, height);
    }

    public void criarCorpo(float posX, float posY, float width, float height) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(posX,posY);
        bdef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width,height);
        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        batch.draw(plataform, body.getPosition().x - width , body.getPosition().y - height , width * 2 , height * 2);
    }


    public void update(float delta) {
        time += 10 * delta;

        if (!toFall) {
            if (time < 40f)
                body.setLinearVelocity(new Vector2(0, 0.15f));
            else if (time > 65f && time < 105f)
                body.setLinearVelocity(new Vector2(0, -0.15f));
            else if (time > 120f)
                time = 0;
            else
                body.setLinearVelocity(new Vector2(0, 0));
        }
        else {
            if(isFalling && time > delay)
                this.body.setLinearVelocity(0,-4f);
            if (body.getPosition().y < 0)
                itFell = true;
        }
    }

    public void hit() {
        if (toFall && !isFalling) {
            delay = time + 10f;
            isFalling = true;
        }
    }

    public float getPosition() {
        return body.getPosition().x;
    }

    public boolean isItFell() {
        return  itFell;
    }

}



