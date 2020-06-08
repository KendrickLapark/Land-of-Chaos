package com.mygdx.game.pantallas;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class TextoInterface {

    private static BitmapFont bitmapFont = new BitmapFont();
    private static SpriteBatch spriteBatch;

    public static void SetSpriteBatch(SpriteBatch batch){
        spriteBatch = batch;
    }

    public static void draw (java.lang.CharSequence msg, OrthographicCamera camara){
        Vector3 vector3 = new Vector3(20,40,0);
        camara.unproject(vector3);
        bitmapFont.getData().scaleX = 0.25f;
        bitmapFont.getData().scaleY = 0.25f;
        bitmapFont.setUseIntegerPositions(false);

        bitmapFont.draw(spriteBatch, msg, vector3.x, vector3.y);
    }
}
