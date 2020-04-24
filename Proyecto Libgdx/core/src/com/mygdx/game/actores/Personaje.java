package com.mygdx.game.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
import com.mygdx.game.Juego;

public class Personaje extends Actor {

    public enum Direccion  { IZQUIERDA, DERECHA}
    public enum Estado {CALLENDO, SALTANDO, ENLASUPERFICIE}
    public Direccion dActual, dPrevio;
    public Estado eActual, ePrevio;

    public World world;
    public Body body;
    private Sprite sprite;
    private Music salto, caida;
    private Texture standr,standl, runr, runl, jumpr, fallr, jumpl, falll,andando;

    private Animation walkAnimation;
    private TextureRegion[]walkFrames;
    private TextureRegion[][]tmp;
    TextureRegion currentWalkFrame;

    private Juego juego;

    public Personaje(World mundo){

        this.world = mundo;

        andando = new Texture("personajes/animacion/wg.png");

        standr = new Texture("personajes/gstandr.png");
        standl = new Texture("personajes/gstandl.png");
        runr = new Texture("personajes/gmove.png");
        runl = new Texture("personajes/gmovel.png");
        jumpr = new Texture("personajes/gjumpr.png");
        jumpl = new Texture("personajes/gjumpl.png");
        fallr = new Texture("personajes/gfallr.png");
        falll = new Texture("personajes/gfalll.png");

        salto = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/salto.mp3"));
        caida = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/caidatrassalto.mp3"));

        currentWalkFrame = new TextureRegion(standr);

        eActual = Estado.ENLASUPERFICIE;
        dActual = Direccion.DERECHA;

        propiedadesFisicas();

    }

    @Override
    public void draw(Batch batch, float parentAlpha ) {

       /* actualizar();
        sprite.setBounds(body.getPosition().x-7,body.getPosition().y-7,16,16);
        sprite.setPosition(body.getPosition().x - 7, body.getPosition().y - 6);
        sprite.draw(batch);*/
        //sprite = new Sprite(currentWalkFrame);
        sprite.setBounds(body.getPosition().x,body.getPosition().y,16,16);
        sprite.setPosition(body.getPosition().x - 7, body.getPosition().y - 6);
        sprite.draw(batch);

    }

   /* public void updateFrame(float elapsedTime){
        currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),true);
    }*/


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

    public void actualizar(float elapsedTime){

        if(body.getLinearVelocity().y>0 && body.getLinearVelocity().x>=0){
            dActual = Direccion.DERECHA;
            ePrevio = eActual;
            eActual = Estado.SALTANDO;
            sprite = new Sprite(jumpr);

        }else if(body.getLinearVelocity().y>0 && body.getLinearVelocity().x<0){
            dActual = Direccion.IZQUIERDA;
            ePrevio = eActual;
            eActual = Estado.SALTANDO;
            sprite = new Sprite(jumpl);
        }else if(body.getLinearVelocity().y <0 && body.getLinearVelocity().x>=0){
            dActual = Direccion.DERECHA;
            ePrevio = eActual;
            eActual = Estado.CALLENDO;
            sprite = new Sprite(fallr);
        }else if(body.getLinearVelocity().y<0 && body.getLinearVelocity().x<0){
            dActual = Direccion.IZQUIERDA;
            ePrevio = eActual;
            eActual = Estado.CALLENDO;
            sprite = new Sprite(falll);
        }else if(body.getLinearVelocity().x>0){
            dActual = Direccion.DERECHA;
            ePrevio = eActual;
            eActual = Estado.ENLASUPERFICIE;

            tmp = TextureRegion.split(andando,36,64);

            walkFrames = new TextureRegion[4];

            int index = 0;

            for(int i = 0; i<4;i++){
                for(int j = 0; j<1;j++){
                    walkFrames[index++] = tmp[j][i];
                }
            }

            walkAnimation = new Animation(0.09f,walkFrames);

            currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),true);

            sprite = new Sprite(currentWalkFrame);

            //sprite = new Sprite(runr);
        }else if(body.getLinearVelocity().x<0){
            dActual = Direccion.IZQUIERDA;
            ePrevio = eActual;
            eActual = Estado.ENLASUPERFICIE;
            sprite = new Sprite(runl);
        }else if(dActual == Direccion.DERECHA){
            sprite = new Sprite(standr);
        }else{
            sprite = new Sprite(standl);
        }

        if(ePrevio == Estado.CALLENDO && body.getLinearVelocity().y == 0 && body.getPosition().y<21){
            ePrevio = eActual;
            eActual = Estado.ENLASUPERFICIE;
            caida.play();
            caida.setVolume(0.02f);
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
            salto.setVolume(0.02f);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.S)){
            body.setLinearVelocity(0,0);

        }
    }

}


