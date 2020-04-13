package com.mygdx.game.pantallas;

import com.badlogic.gdx.Screen;
import com.mygdx.game.Juego;

public abstract class PantallaBase implements Screen {

    private Juego juego;

    public PantallaBase (Juego j){
        this.juego = j;
    }

    @Override
    public void render(float delta) {

    }
}
