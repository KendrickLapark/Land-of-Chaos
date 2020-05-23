package com.mygdx.game.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.actores.Personaje;
import com.mygdx.game.objetos.Onda;

public class Teclado implements InputProcessor {

    public Personaje pj1;
    public Onda onda;

    public Teclado(Personaje personaje){
        this.pj1 = personaje;
    }

    @Override
    public boolean keyDown(int keycode) {

        Gdx.app.log("eventoDown","Input "+keycode);
        switch (keycode) {
            case Input.Keys.D:
                pj1.getCuerpo().applyLinearImpulse(new Vector2(80,0),pj1.getCuerpo().getWorldCenter(),true);
                break;
            case Input.Keys.A:
                pj1.getCuerpo().applyLinearImpulse(new Vector2(-80,0),pj1.getCuerpo().getWorldCenter(),true);
                break;
            case Input.Keys.W:
                if(pj1.getCuerpo().getLinearVelocity().y==0){
                    pj1.getCuerpo().applyLinearImpulse(new Vector2(0,180),pj1.getCuerpo().getWorldCenter(),true);
                }
                break;
            case Input.Keys.S:
                if(pj1.dActual == Personaje.Direccion.DERECHA){
                    pj1.getCuerpo().setLinearVelocity(0,0);
                    System.out.println("Direccion tras bajasr:"+pj1.dActual);

                }else{
                    pj1.getCuerpo().setLinearVelocity(0,0);
                    System.out.println("Direccion tras bajasr:"+pj1.dActual);
                }

                break;
            case Input.Keys.E:
                pj1.setKamehameha(true);
        }
        return true;

    }

    @Override
    public boolean keyUp(int keycode) {

        switch(keycode){
            case Input.Keys.E:
                pj1.setKamehameha(false);
                pj1.kamehamehaSound.stop();
        }

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
