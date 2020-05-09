package com.mygdx.game.objetos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.actores.Personaje;
import com.mygdx.game.actores.Saibaman;

public class Onda extends Actor {

    private Sprite sprite;
    private World world;

    private Personaje pj1;

    private boolean colision;

    private Body body;


    public Onda(World m, Personaje p1){

        this.world = m;
        this.pj1 = p1;

        fisica();

        if(pj1.dActual == Personaje.Direccion.DERECHA){
            body.setLinearVelocity(90,0);
            sprite = new Sprite(new Texture("Objetos/ondaR.png"));
        }else{
            body.setLinearVelocity(-90,0);
            sprite = new Sprite(new Texture("Objetos/ondaL.png"));
        }

    }

    public void fisica(){
        BodyDef bodyDef = new BodyDef();
        if(pj1.dActual == Personaje.Direccion.DERECHA){
            bodyDef.position.set(pj1.getCuerpo().getPosition().x+1,pj1.getCuerpo().getPosition().y);
        }else{
            bodyDef.position.set(pj1.getCuerpo().getPosition().x-1,pj1.getCuerpo().getPosition().y);
        }

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1);
        fixtureDef.shape = circleShape;
        fixtureDef.density=1f;
        body.createFixture(fixtureDef);
        body.setGravityScale(0.0f);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        sprite.setOrigin(body.getPosition().x,body.getPosition().y);
        sprite.setBounds(body.getPosition().x-3,body.getPosition().y-2,6,4);
        sprite.draw(batch);
    }

    public boolean colisionSaibaman(Saibaman c){
        boolean overlaps=getHitBox().overlaps(c.getHitBox());
        if(overlaps&&colision==false){
            colision=true;
            Gdx.app.log("Colisionando","con "+c.getClass().getName());
        }else if(!overlaps){
            colision=false;
        }
        return colision;
    }

    public Rectangle getHitBox(){
        return sprite.getBoundingRectangle();
    }
}
