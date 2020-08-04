package com.mygdx.game.pantallas;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.text.DecimalFormat;

public class TextoInterface {

    private static BitmapFont bitmapFont1 = new BitmapFont();
    private static SpriteBatch spriteBatch;

    public static float tiempo;

    public static int tiempototal;


    public static void SetSpriteBatch(SpriteBatch batch){
        spriteBatch = batch;
    }

    /*public static void draw (java.lang.CharSequence msg, OrthographicCamera camara){
        Vector3 vector3 = new Vector3(20,40,0);
        camara.unproject(vector3);
        bitmapFont1.getData().scaleX = 0.25f;
        bitmapFont1.getData().scaleY = 0.25f;
        bitmapFont1.setUseIntegerPositions(false);

        bitmapFont1.draw(spriteBatch, msg, vector3.x, vector3.y);
    }*/

    public static void draw (OrthographicCamera camara){

        tiempototal = 300;

        tiempo += Gdx.graphics.getDeltaTime();
        double tiemp = Math.ceil(tiempo);

        DecimalFormat decimalFormat = new DecimalFormat("#");

        tiempototal -=tiemp;

        Vector3 vector3 = new Vector3(20,40,0);
        camara.unproject(vector3);
        bitmapFont1.getData().scaleX = 0.25f;
        bitmapFont1.getData().scaleY = 0.25f;
        bitmapFont1.setUseIntegerPositions(false);

        bitmapFont1.draw(spriteBatch, "Tiempo: "+tiempototal , vector3.x, vector3.y);


    }

}
