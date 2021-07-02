package com.trabalho.game.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.trabalho.game.tools.Assets;

public class SoundEffects {

    private Sound pular;
    private Sound pisar;
    private Sound moedaPega;

    private Music vitoria;
    private Music derrota;
    private boolean tocou;

    public SoundEffects() {
        this.pular = Assets.manager.get("sons/jump.ogg");
        this.pisar = Assets.manager.get("sons/pisar.wav");
        this.moedaPega = Assets.manager.get("sons/coin.ogg");
        this.vitoria = Assets.manager.get("sons/win.mp3");
        this.derrota = Assets.manager.get("sons/lose.ogg");
        this.tocou = false;
    }

    public void play(String som) {
        if (som.equals("pular"))
            pular.play();
        else if (som.equals("pisar"))
            pisar.play();
        else if (som.equals("moeda"))
            moedaPega.play();
        else if (som.equals("vitoria"))
            vitoria.play();
        else
            derrota.play();
    }
    public boolean getTocou() { return tocou;}
    public void setTocou(boolean tocou) {
        this.tocou = tocou;
    }
    public boolean isPlayingWin() {
        return vitoria.isPlaying();
    }
    public boolean isPlayingLose() { return derrota.isPlaying();}
}
