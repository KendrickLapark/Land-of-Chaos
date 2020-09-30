package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Plataforma extends Actor {

        private World world;
        private Sprite sprite;
        public Body body;

        public enum Estado  { SUBIENDO, BAJANDO};

        Estado actual;

    public Plataforma(World mundo, float x, float y){

        this.world = mundo;

        sprite = new Sprite(new Texture("Objetos/plataforma.png"));

        propiedadesFisicas(x,y);

    }

    public void propiedadesFisicas(float x, float y){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y); //x=2090 y=69, zona inicial
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(25, 6);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        sprite.setBounds(body.getPosition().x-6, body.getPosition().y-6,12,12);
        sprite.draw(batch);

        move();

    }

    public void move(){

        if(this.body.getPosition().y>100){
           actual = Estado.BAJANDO;
        }else if(this.body.getPosition().y<60){
            actual = Estado.SUBIENDO;
        }

        if(actual == Estado.SUBIENDO){
            body.setLinearVelocity(0,20);
        }else{
            body.setLinearVelocity(0,-10);
        }

    }

    public Body getCuerpo(){
        return body;
    }

}
