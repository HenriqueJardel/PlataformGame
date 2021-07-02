package com.trabalho.game.level;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.trabalho.game.animation.inimigos.Arvore;
import com.trabalho.game.animation.inimigos.Tartaruga;
import com.trabalho.game.animation.objetos.Moeda;
import com.trabalho.game.animation.inimigos.Cogumelo;
import com.trabalho.game.animation.inimigos.Inimigo;
import com.trabalho.game.animation.inimigos.Passaro;
import com.trabalho.game.animation.objetos.Plataforma;


import java.util.ArrayList;

public class Box2DWorld {

    private World world;

    public Box2DWorld(World world) {
        this.world = world;
    }

    public void createWorld(TiledMap map) {

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject obj : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/100f, (rect.getY() + rect.getHeight()/2)/100f);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() /2)/100f , (rect.getHeight()/2)/100f);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        for (MapObject obj : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth()/2)/100f, (rect.getY() + rect.getHeight()/2)/100f);
            body = world.createBody(bdef);
            shape.setAsBox((rect.getWidth() /2)/100f , (rect.getHeight()/2)/100f);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }


    public ArrayList<Inimigo> generateEnemies(TiledMap map, int level) {
        ArrayList<Inimigo> inimigos = new ArrayList<Inimigo>();
        for (MapObject obj : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            if(level < 7)
                inimigos.add(new Cogumelo(world, rect.x/100f, rect.y/100f));
            else
                inimigos.add(new Arvore(world, rect.x/100f, rect.y/100f));
        }

        for (MapObject obj : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            if(level < 7)
            inimigos.add(new Passaro(world, rect.x/100f, rect.y/100f));
            else
                inimigos.add(new Tartaruga(world, rect.x/100f, rect.y/100f));
        }

        return inimigos;
    }

    public ArrayList<Moeda> generateCoins(TiledMap map) {
        ArrayList<Moeda> moedas = new ArrayList<Moeda>();
        for (MapObject obj : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            moedas.add(new Moeda(world, (rect.x + (rect.getWidth()/2))/100f,
                    (rect.y + (rect.getHeight()/2))/100f));
        }
        return moedas;
    }

    public ArrayList<Plataforma> generateDinamicPlataforms(TiledMap map) {
        ArrayList<Plataforma> plataformas = new ArrayList<Plataforma>();
        for (MapObject obj : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            plataformas.add(new Plataforma(world, (rect.getX() + rect.getWidth()/2)/100f, (rect.getY() + rect.getHeight()/2)/100f,
                    (rect.getWidth())/100f , (rect.getHeight())/100f, false));
        }

        for (MapObject obj : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();
            plataformas.add(new Plataforma(world, (rect.getX() + rect.getWidth()/2)/100f, (rect.getY() + rect.getHeight()/2)/100f,
                    (rect.getWidth())/100f , (rect.getHeight())/100f, true));
        }
        return plataformas;
    }

    public void destroyWorld(World world) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (int i = 0 ; i < bodies.size; i++) {
            world.destroyBody(bodies.get(i));
        }
    }

}
