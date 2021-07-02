package com.trabalho.game.animation;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.trabalho.game.PlataformGame;
import com.trabalho.game.tools.Assets;

public class Player {

    public World world;
    public Body body;
    public enum State {PARADO, CORRENDO, PULANDO, CAINDO, MORTO, DESAPARECEU}
    public State estadoAtual;
    private Texture [] parado;
    private Texture [] correndo;
    private Texture [] morto;
    private Texture pulando;
    private Texture caindo;

    private boolean esquerda = false;
    private boolean isDead = false;
    private boolean isDestroy = false;
    private float frames;
    private float delay;

    public Player(World world) {
        this.world = world;
        criarBody();
        this.estadoAtual = State.PARADO;
        parado = new Texture[11];
        correndo = new Texture[12];
        morto = new Texture[7];

        for (int i = 0; i < parado.length; i++) {
            parado[i] = Assets.manager.get("player/parado-"+i+".png");
        }

        for (int i = 0; i < correndo.length; i++) {
            correndo[i] = Assets.manager.get("player/run-"+i+".png");
        }

        for (int i = 0; i < morto.length; i++) {
            morto[i] = Assets.manager.get("player/morto-"+i+".png");
        }


        this.pulando = Assets.manager.get("player/pulando.png");
        this.caindo = Assets.manager.get("player/caindo.png");
    }

    public void criarBody() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(2.2f,0.5f);
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();

        shape.setRadius(6/100f);
        fdef.shape = shape;
        fdef.filter.categoryBits = PlataformGame.PLAYER_BIT;
        body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch){
        if (estadoAtual == State.PARADO)
            batch.draw(parado[(int) frames % 11], body.getPosition().x -(6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,parado[(int) frames % 11].getWidth(), parado[(int) frames % 11].getHeight(), esquerda, false);
        if (estadoAtual == State.CORRENDO)
            batch.draw(correndo[(int) frames % 12], body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,correndo[(int) frames % 12].getWidth(), correndo[(int) frames % 12].getHeight(), esquerda, false);
        if (estadoAtual == State.PULANDO)
            batch.draw(pulando, body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,pulando.getWidth(), pulando.getHeight(), esquerda, false);
        if (estadoAtual == State.CAINDO)
            batch.draw(caindo, body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,caindo.getWidth(), caindo.getHeight(), esquerda, false);
        if (estadoAtual == State.MORTO)
            batch.draw(morto[(int) frames % 7], body.getPosition().x - (6/100f) * 2 ,body.getPosition().y - (5/100f)  ,
                    (6/100f) * 4 , (6/100f) * 4,0,0,morto[(int) frames % 7].getWidth(), morto[(int) frames % 7].getHeight(), esquerda, false);
        }

    public  void update(float delta) {
        if (isDead == false) {
            this.frames += 10 * delta;
            if(body.getLinearVelocity().x == 0)
                estadoAtual = State.PARADO;
            if(body.getLinearVelocity().x > 0)
                estadoAtual = State.CORRENDO;
            if(body.getLinearVelocity().x < 0)
                estadoAtual = State.CORRENDO;
            if (body.getLinearVelocity().y > 0.16f)
                estadoAtual = State.PULANDO;
            if (body.getLinearVelocity().y < -0.16f)
                estadoAtual = State.CAINDO;
            if (body.getPosition().y < 0.1f) {
                isDead = true;
            }
        }
        else {
            this.frames += 5 * delta;

            if(this.frames > delay) {
                estadoAtual = State.DESAPARECEU;

                if(!isDestroy) {
                    world.destroyBody(body);
                    isDestroy = true;
                }
            }
        }
    }

    public void hit() {
        estadoAtual = State.MORTO;
        delay = frames + 6f;
        isDead = true;
    }

    public void reset() {
        isDead = false;
        body.setTransform(2.3f,0.5f,0);
    }

    public void setDirecao(boolean esquerda) {
        this.esquerda = esquerda;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }

}
