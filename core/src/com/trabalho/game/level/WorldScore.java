package com.trabalho.game.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class WorldScore {

    private Preferences preferences;

    public WorldScore() {
        preferences = Gdx.app.getPreferences("My Preferences");
    }

    public void saveScore(Integer escore, int levelAtual) {
        String token = "score_" + levelAtual;
        Integer escoreAnterior = preferences.getInteger(token);
        if(escoreAnterior != null) {
            if(escoreAnterior < escore) {
                this.preferences.putInteger(token,escore);
                this.preferences.flush();
            }
        }
        else  {
            this.preferences.putInteger(token, escore);
            this.preferences.flush();
        }
    }

    public void saveTempo(Integer tempo, int levelAtual) {
        String token = "tempo_" + levelAtual;
        Integer tempoAnterior = preferences.getInteger(token);
        if(tempoAnterior != null) {
            if(tempoAnterior < tempo) {
                preferences.putInteger(token,tempo);
                this.preferences.flush();
            }
        }
        else {
            this.preferences.putInteger(token,tempo);
            this.preferences.flush();
        }
    }

    public Integer getEscore(int level) {
        String token = "score_" + level;
        return this.preferences.getInteger(token);
    }

    public Integer getTempo(int level) {
        String token = "tempo_" + level;
        return this.preferences.getInteger(token);
    }
}
