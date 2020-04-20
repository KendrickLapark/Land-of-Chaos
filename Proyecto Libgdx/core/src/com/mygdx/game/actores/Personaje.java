package com.mygdx.game.actores;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Personaje {

    public World world;
    public Body body;

    public Personaje(World mundo){

        this.world = mundo;
        definicion();
    }


    public void definicion(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32,32);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);

    }

}


