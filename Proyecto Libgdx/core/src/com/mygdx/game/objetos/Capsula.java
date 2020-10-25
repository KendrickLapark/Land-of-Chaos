package com.mygdx.game.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.actores.Personaje;

public class Capsula extends Recolectable {

    private boolean colision;
    private int recogido = 0;

    public Capsula(World m, String ruta, float x, float y) {
        super(m, ruta, x, y);
    }

    public boolean recoleccion(Personaje p1){
        boolean overlaps=getHitBox().overlaps(p1.getHitBox());
        if(overlaps&&colision==false){
            colision=true;
            Gdx.app.log("Colisionando","con "+p1.getClass().getName());
        }else if(!overlaps){
            colision=false;
        }
        return colision;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(colision==true){
            recogido++;
        }

        if(recogido==0){
            super.draw(batch, parentAlpha);
        }

    }


}


