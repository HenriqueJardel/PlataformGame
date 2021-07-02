package com.trabalho.game.animation.inimigos;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Inimigo {

    public enum State {PARADO, MOVIMENTANDO, ATACANDO, MORTO}
    public Body body;
    protected World world;
    protected State estadoAtual;
    protected boolean isDead;
    protected boolean isDestroy;
    protected boolean isAtack;
    protected float frames;
    protected float time;

    public Inimigo(World world, float posX, float posY) {
        this.world = world;
        this.estadoAtual = State.PARADO;
        this.isDead = false;
        this.isDestroy = false;
        this.isAtack = false;
        this.frames = 0;
        criarCorpo(posX, posY);
    }

    protected abstract void criarCorpo(float posX, float posY);
    public abstract void draw(Batch batch);
    public abstract void update(float delta);
    public abstract void hit();

    public boolean isDead() {
        return isDead;
    }
    public boolean isAtack() {
        return  isAtack;
    }
    public void setIsAtack(boolean atack) { this.isAtack = atack; }
}
