package com.mygdx.game.actores;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.geom.RectangularShape;

public class Saibaman extends Actor {

    private World world;
    private Body body;
    private Sprite sprite;
    private Texture staticSaibaman;

    public Saibaman(World mundo){
        this.world = mundo;

        staticSaibaman = new Texture("personajes/Saibaman/sai.png");

        propiedadesFisicas();
    }

    public void propiedadesFisicas(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(62,22);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(4, 6);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite = new Sprite(staticSaibaman);
        sprite.setBounds(body.getPosition().x-7,body.getPosition().y-7,16,16);
        sprite.setPosition(body.getPosition().x - 7.5f, body.getPosition().y - 8);
        sprite.draw(batch);
    }

    public void patrullar(){
        
    }
}
