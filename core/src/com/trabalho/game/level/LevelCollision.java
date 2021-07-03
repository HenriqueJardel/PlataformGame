package com.trabalho.game.level;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.trabalho.game.animation.Player;
import com.trabalho.game.animation.inimigos.Inimigo;
import com.trabalho.game.animation.objetos.Moeda;
import com.trabalho.game.animation.objetos.Plataforma;
import com.trabalho.game.animation.objetos.Tiro;
import com.trabalho.game.sounds.SoundEffects;

public class LevelCollision implements ContactListener {

    private SoundEffects soundEffects;
    private Integer score;

    public LevelCollision(SoundEffects soundEffects) {
        this.soundEffects = soundEffects;
        this.score = 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        int c = a.getFilterData().categoryBits | b.getFilterData().categoryBits;

        if (a.getUserData() != null && a.getUserData() != null) {
            if (c == 130) {
                ((Inimigo) a.getUserData()).hit();
                score += 200;
                soundEffects.play("pisar");
            }

            if (c == 62 && a.getUserData() instanceof Player) {
                ((Player) a.getUserData()).hit();
            }

            if (c == 62 && b.getUserData() instanceof Player) {
                ((Player) b.getUserData()).hit();
            }

            if (c == 34 && a.getUserData() instanceof Moeda) {
                ((Moeda) a.getUserData()).hit();
                soundEffects.play("moeda");
                score += 100;
            }

            if (c == 34 && b.getUserData() instanceof Moeda) {
                ((Moeda) b.getUserData()).hit();
                soundEffects.play("moeda");
                score += 100;
            }

            if (a.getUserData() instanceof Plataforma) {
                ((Plataforma) a.getUserData()).hit();
            }

            if(a.getUserData() instanceof Player && b.getUserData() instanceof Tiro) {
                ((Player) a.getUserData()).hit();
                ((Tiro) b.getUserData()).hit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) { this.score = score;}
}
