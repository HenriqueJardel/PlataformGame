package com.trabalho.game.level;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.trabalho.game.animation.objetos.Moeda;
import com.trabalho.game.animation.objetos.Plataforma;
import com.trabalho.game.animation.objetos.Tiro;
import com.trabalho.game.sounds.Sound;
import com.trabalho.game.animation.inimigos.Inimigo;
import com.trabalho.game.tools.Assets;

import java.util.ArrayList;

public class Level {

    private TiledMap map [];
    private OrthogonalTiledMapRenderer render;
    private World world;
    private Box2DWorld box2d;
    private Sound som;

    private ArrayList<Inimigo> inimigos;
    private ArrayList<Moeda> moedas;
    private ArrayList<Plataforma> plataformas;
    private ArrayList<Tiro> tiros;
    private int levelAtual;

    public Level(World world) {
        this.world = world;
        this.box2d = new Box2DWorld(world);
        this.som = new Sound();

        this.map = new TiledMap[10];
        for (int i = 0; i < map.length;i++) {
            this.map[i] = Assets.manager.get("cenarios/level-" + i + ".tmx");
        }

        this.levelAtual = 0;
        this.inimigos = new ArrayList<Inimigo>();
        this.moedas = new ArrayList<Moeda>();
        this.plataformas = new ArrayList<Plataforma>();
        this.tiros = new ArrayList<Tiro>();
        init();
    }

    public void init() {
        box2d.createWorld(map[levelAtual]);
        render = new OrthogonalTiledMapRenderer(map[levelAtual],1/100f);
        this.som.tocarSomCenario(true, levelAtual);
    }

    public void startNewLevel(int levelAtual) {
        box2d.destroyWorld(world);
        inimigos.clear();
        moedas.clear();
        plataformas.clear();
        tiros.clear();

        if (levelAtual != 0) {
            inimigos = this.box2d.generateEnemies(map[levelAtual],levelAtual);
            moedas = this.box2d.generateCoins(map[levelAtual]);

            if (levelAtual > 3) {
                plataformas = this.box2d.generateDinamicPlataforms(map[levelAtual]);
            }
        }

        box2d.createWorld(map[levelAtual]);
        render.setMap(map[levelAtual]);
        som.tocarSomCenario(true, levelAtual);
    }

    public void draw(float posMin, float posMax) {
        for (int i = inimigos.size() - 1; i >= 0; i--) {
            if(inimigos.get(i).body.getPosition().x < posMax && inimigos.get(i).body.getPosition().x > posMin)
                inimigos.get(i).draw(render.getBatch());
        }

        for (int i = moedas.size() - 1; i >= 0 ; i--) {
            if(moedas.get(i).getPosition() < posMax && moedas.get(i).getPosition() > posMin)
                moedas.get(i).draw(render.getBatch());
        }

        for (int i = plataformas.size() - 1; i >= 0 ; i--) {
            if(plataformas.get(i).getPosition() < posMax && plataformas.get(i).getPosition() > posMin)
                plataformas.get(i).draw(render.getBatch());
        }

        for (int i = tiros.size() - 1; i >= 0; i--) {
            tiros.get(i).draw(render.getBatch());
        }
    }

    public void update(float delta) {

        for (int i = inimigos.size() - 1; i >= 0; i--) {
            inimigos.get(i).update(delta);
        }

        for (int i = inimigos.size() - 1; i >= 0; i--) {
            if(inimigos.get(i).isDead())
                inimigos.remove(i);
        }

        for (int i = moedas.size() - 1; i >= 0; i--) {
            moedas.get(i).update(delta);
        }

        for (int i = moedas.size() - 1; i >= 0; i--) {
           if(moedas.get(i).getPega() == true)
               moedas.remove(i);
        }

        for (int i = plataformas.size() - 1; i >= 0; i --) {
            plataformas.get(i).update(delta);
        }

        for (int i = plataformas.size() - 1; i >= 0; i --) {
            if(plataformas.get(i).isItFell())
                plataformas.remove(i);
        }

        for (int i = tiros.size() - 1; i >= 0; i--) {
            tiros.get(i).update();
        }

        for (int i = tiros.size() - 1; i >= 0; i--) {
            if(tiros.get(i).isShot())
                tiros.remove(i);
        }

        if(levelAtual >= 7) {
            for (int i = inimigos.size() - 1; i >= 0; i--) {
                if(inimigos.get(i).isAtack()) {
                    tiros.add(new Tiro(world, inimigos.get(i).body.getPosition().x , inimigos.get(i).body.getPosition().y));
                    inimigos.get(i).setIsAtack(false);
                }
            }
        }
    }

    public void pararSom() {
        this.som.tocarSomCenario(false, levelAtual);
    }

    public void setLevelAtual(int level) {
        pararSom();
        this.levelAtual = level;
        startNewLevel(levelAtual);
    }

    public int getLevelAtual() {
        return levelAtual;
    }

    public OrthogonalTiledMapRenderer getRender() {
        return render;
    }

    public Batch getBatch() {
        return render.getBatch();
    }

    public void setView(OrthographicCamera gameCam) {
        render.setView(gameCam);
    }

}
