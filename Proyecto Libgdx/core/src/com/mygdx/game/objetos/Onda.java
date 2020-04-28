package com.mygdx.game.objetos;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.actores.Personaje;

public class Onda extends Actor {

    private Sprite sprite;
    private World world;

    private Personaje pj1;

    private Body body;


    public Onda(World m, Personaje p1){

        this.world = m;
        this.pj1 = p1;

        fisica();

        body.setLinearVelocity(30,20);

    }

    public void fisica(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pj1.getCuerpo().getPosition().x+1,pj1.getCuerpo().getPosition().y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1);
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }
}
