package com.trabalho.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.trabalho.game.stages.Hud;
import com.trabalho.game.sounds.SoundEffects;
import com.trabalho.game.animation.Player;
import com.trabalho.game.stages.DisplayInfo;

public class LevelManager {

    private OrthographicCamera gameCam;
    private World world;

    private Player player;
    private Level level;
    private Hud hud;
    private SoundEffects soundEffects;
    private LevelCollision levelCollision;
    private WorldScore save;
    private DisplayInfo info;

    private boolean emJogo;
    private boolean show;

    public LevelManager(OrthographicCamera gameCam) {
        this.gameCam = gameCam;
        this.world = new World(new Vector2(0,-10),true);
        this.player = new Player(world);
        this.level = new Level(world);
        this.hud = new Hud(level.getBatch());
        this.soundEffects = new SoundEffects();
        this.levelCollision = new LevelCollision(soundEffects);
        this.world.setContactListener(levelCollision);
        this.save = new WorldScore();
        this.info = new DisplayInfo(level.getBatch());
        this.emJogo = false;
        this.show = false;
    }

    public void draw() {
        level.getRender().render();
        hud.draw();

        if (level.getLevelAtual() == 0 && show == true) {
            info.draw();
        }

        level.getBatch().setProjectionMatrix(gameCam.combined);
        level.getBatch().begin();
        player.draw(level.getBatch());
        level.draw(player.body.getPosition().x - (403/100f), player.body.getPosition().x + (403/100f));
        level.getBatch().end();
    }

    public void update(float delta) {
        world.step(1/60f,6,2);
        hud.setScore(levelCollision.getScore());
        if (level.getLevelAtual() == 0) {
            if(player.body.getPosition().x > 2.1f && player.body.getPosition().x < 21.97) {
                this.gameCam.position.x = player.body.getPosition().x;
            }

            if(player.body.getPosition().x > 0.65f && player.body.getPosition().x < 0.96f) {
                show(1);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(1);
                }
            }
            else if(player.body.getPosition().x > 1.93f && player.body.getPosition().x < 2.23f) {
                show(2);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(2);
                }
            }
            else if(player.body.getPosition().x > 3.2f && player.body.getPosition().x < 3.51f) {
                show(3);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(3);
                }
            }
            else if(player.body.getPosition().x > 6.24f && player.body.getPosition().x < 6.55f) {
                show(4);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(4);
                }
            }
            else if(player.body.getPosition().x > 8.32f && player.body.getPosition().x < 8.64) {
                show(5);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(5);
                }
            }
            else if(player.body.getPosition().x > 10.40f && player.body.getPosition().x < 10.72f) {
                show(6);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(6);
                }
            }
            else if(player.body.getPosition().x > 12.66f && player.body.getPosition().x < 12.94f) {
                show(7);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(7);
                }
            }
            else if(player.body.getPosition().x > 17.14f && player.body.getPosition().x < 17.42f) {
                show(8);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(8);
                }
            }
            else if(player.body.getPosition().x > 21.63f && player.body.getPosition().x < 21.9) {
                show(9);
                if (player.estadoAtual == Player.State.PULANDO && !this.emJogo) {
                    start(9);
                }
            }
            else {
                this.show = false;
            }
        }

        else {
            if(player.body.getPosition().x > 2.1f && player.body.getPosition().x < 45.8f)
                this.gameCam.position.x = player.body.getPosition().x;

            if(hud.getTime() == 0) {
                player.setIsDead(true);
            }

            if(player.isDead()) {
                if (!soundEffects.getTocou()) {
                    this.level.pararSom();
                    soundEffects.play("derrota");
                    soundEffects.setTocou(true);
                }
                else {
                    if (!soundEffects.isPlayingLose())
                        restart();
                }
            }

            if(player.body.getPosition().x > 45.8f)  {
                if (!soundEffects.getTocou()) {
                    this.level.pararSom();
                    soundEffects.play("vitoria");
                    soundEffects.setTocou(true);
                }
                else {
                    if (!soundEffects.isPlayingWin()) {
                       save.saveScore(hud.getScore(), level.getLevelAtual());
                       save.saveTempo(hud.getTime(), level.getLevelAtual());
                       restart();
                    }
                }
            }
        }

        this.gameCam.update();
        level.setView(gameCam);
        level.update(delta);
        hud.update(delta, level.getLevelAtual());
        player.update(delta);
    }

    public void input() {
        if(player.body.getPosition().x < 45.8f && !player.isDead()) {
            if (hud.isRightPressed() && player.body.getLinearVelocity().x < 2) {
                player.setDirecao(false);
                player.body.applyLinearImpulse(new Vector2(0.060f, 0), player.body.getWorldCenter(),true);
            }
            if (hud.isLeftPressed() && player.body.getLinearVelocity().x > -2) {
                player.setDirecao(true);
                player.body.applyLinearImpulse(new Vector2(-0.060f, 0), player.body.getWorldCenter(),true);
            }
            if (Gdx.input.justTouched()){
                if (hud.isUpPressed() && player.estadoAtual != Player.State.PULANDO && player.estadoAtual != Player.State.CAINDO) {
                    soundEffects.play("pular");
                    player.body.applyLinearImpulse(new Vector2(0, 4f), player.body.getWorldCenter(),true);
                }
            }
        }
    }

    public void resize(int width, int height) {
        hud.resize(width, height);
        if (level.getLevelAtual() == 0 && show == true) {
            info.resize(width, height);
        }
    }
    public void start(int level) {
        this.level.setLevelAtual(level);
        this.player.criarBody();
        this.emJogo = true;
    }

    public void restart() {
        this.level.setLevelAtual(0);
        this.hud.setWorldTime(180);
        this.levelCollision.setScore(0);
        player.criarBody();
        player.setIsDead(false);
        soundEffects.setTocou(false);
        this.emJogo = false;
    }

    public void show(int level) {
        this.show = true;
        this.info.setScore(save.getEscore(level));
        this.info.setWorldTime(save.getTempo(level));
    }

}
