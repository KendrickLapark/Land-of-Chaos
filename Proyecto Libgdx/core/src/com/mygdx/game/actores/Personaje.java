package com.mygdx.game.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Personaje extends Actor {

    public enum Direccion  { IZQUIERDA, DERECHA}
    public enum Estado {CALLENDO, SALTANDO, ENLASUPERFICIE}
    public Direccion dActual, dPrevio;
    public Estado eActual, ePrevio;

    public World world;
    public Body body;
    private Sprite sprite;
    private Sound salto, caida;
    private long ids;
    private Texture standr,standl, runr, runl, jumpr, fallr, jumpl, falll;

    public Personaje(World mundo){

        this.world = mundo;

        standr = new Texture("personajes/gstandr.png");
        standl = new Texture("personajes/gstandl.png");
        runr = new Texture("personajes/gmove.png");
        runl = new Texture("personajes/gmovel.png");
        jumpr = new Texture("personajes/gjumpr.png");
        jumpl = new Texture("personajes/gjumpl.png");
        fallr = new Texture("personajes/gfallr.png");
        falll = new Texture("personajes/gfalll.png");

        salto = Gdx.audio.newSound(Gdx.files.internal("sonido/efectos/salto.mp3"));
        caida = Gdx.audio.newSound(Gdx.files.internal("sonido/efectos/caidatrassalto.mp3"));


        sprite = new Sprite(standr);

        eActual = Estado.ENLASUPERFICIE;
        dActual = Direccion.DERECHA;

        propiedadesFisicas();

        sprite.setBounds(body.getPosition().x-7,body.getPosition().y-7,16,16);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        actualizar();
        sprite.setBounds(body.getPosition().x-7,body.getPosition().y-7,16,16);
        sprite.setPosition(body.getPosition().x - 7, body.getPosition().y - 6);
        sprite.draw(batch);
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

    public void actualizar(){
        if(body.getLinearVelocity().y>0 && body.getLinearVelocity().x>=0){
            dActual = Direccion.DERECHA;
            eActual = Estado.SALTANDO;
            sprite = new Sprite(jumpr);

        }else if(body.getLinearVelocity().y>0 && body.getLinearVelocity().x<0){
            dActual = Direccion.IZQUIERDA;
            eActual = Estado.SALTANDO;
            sprite = new Sprite(jumpl);
        }else if(body.getLinearVelocity().y <0 && body.getLinearVelocity().x>=0){
            dActual = Direccion.DERECHA;
            eActual = Estado.CALLENDO;
            sprite = new Sprite(fallr);
        }else if(body.getLinearVelocity().y<0 && body.getLinearVelocity().x<0){
            dActual = Direccion.IZQUIERDA;
            eActual = Estado.CALLENDO;
            sprite = new Sprite(falll);
        }else if(body.getLinearVelocity().x>0){
            dActual = Direccion.DERECHA;
            eActual = Estado.ENLASUPERFICIE;
            sprite = new Sprite(runr);
        }else if(body.getLinearVelocity().x<0){
            dActual = Direccion.IZQUIERDA;
            eActual = Estado.ENLASUPERFICIE;
            sprite = new Sprite(runl);
        }else if(dActual == Direccion.DERECHA){
            sprite = new Sprite(standr);
        }else{
            sprite = new Sprite(standl);
        }
    }


    public void mandoTeclado(){

        if(Gdx.input.isKeyJustPressed(Input.Keys.D) && body.getLinearVelocity().x <= 20){
            body.applyLinearImpulse(new Vector2(80,0),body.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.A) && body.getLinearVelocity().x >= -20){
            body.applyLinearImpulse(new Vector2(-80,0),body.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
            body.applyLinearImpulse(new Vector2(0,80),body.getWorldCenter(),true);
            salto.play();
        }
    }

}


