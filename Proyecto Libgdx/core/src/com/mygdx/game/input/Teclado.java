package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actores.Personaje;

public class Teclado implements InputProcessor {

    public Personaje pj1;

    public Teclado(Personaje personaje){
        this.pj1 = personaje;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode){
            case Input.Keys.A:
                pj1.body.setLinearVelocity(-20,0);
            case Input.Keys.D:
                pj1.body.setLinearVelocity(20,0);

        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
