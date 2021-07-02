package com.trabalho.game.sounds;


import com.badlogic.gdx.audio.Music;
import com.trabalho.game.tools.Assets;

public class Sound {

    private Music [] cenario;

    public Sound() {

        this.cenario = new Music[10];
        for(int i = 0; i < cenario.length ; i++) {
            this.cenario[i] = Assets.manager.get("sons/cenario-" + i + ".mp3");
        }
    }

    public void tocarSomCenario(boolean tocando, int level) {
        if (tocando == true){
            cenario[level].setLooping(true);
            cenario[level].play();
        }
        else
            cenario[level].stop();
    }

}
