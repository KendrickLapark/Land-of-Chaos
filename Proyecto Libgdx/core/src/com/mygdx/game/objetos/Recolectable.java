package com.mygdx.game.objetos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Recolectable extends Actor {

    public Sprite sprite; //Sprite que simboliza al actor
    private World mundo; //Mundo de nuestro juego
    private BodyDef propiedadesCuerpo; //Definidor de las propiedades del body
    public Body cuerpo; //Cuerpo del objeto
    private FixtureDef propiedadesFisicasCuerpo;//Definidor de las propiedades físicas del body

    public Recolectable(World m, String ruta, float x, float y){
        mundo = m;
        sprite = new Sprite(new Texture(ruta));

        propiedadesFisicas(x,y);

    }

    public void propiedadesFisicas(float x, float y){

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        cuerpo = mundo.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(4, 4);
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor= true;
        cuerpo.createFixture(fixtureDef);


    }

    public Rectangle getHitBox(){
        return sprite.getBoundingRectangle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(sprite!=null){
            sprite.setOrigin(cuerpo.getPosition().x,cuerpo.getPosition().y);
            sprite.setBounds(cuerpo.getPosition().x-4,cuerpo.getPosition().y-4,8,8);
            sprite.draw(batch);
        }

    }

    public Body getCuerpo(){
        return cuerpo;
    }
}
