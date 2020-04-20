package com.mygdx.game.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
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
        propiedadesFisicas();
    }


    public void propiedadesFisicas(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32,22);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);

    }

    public void mandoTeclado(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.D)){
            body.applyLinearImpulse(new Vector2(40,0),body.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.A)){
            body.applyLinearImpulse(new Vector2(-40,0),body.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            body.applyLinearImpulse(new Vector2(0,80),body.getWorldCenter(),true);
        }
    }

}


