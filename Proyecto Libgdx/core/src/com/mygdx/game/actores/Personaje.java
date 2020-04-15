package com.mygdx.game.actores;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class Personaje {

    public World world;
    public Body body;

    public Personaje(World mundo){
        this.world = mundo;
    }


    public void definicion(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32,32);

    }

}


