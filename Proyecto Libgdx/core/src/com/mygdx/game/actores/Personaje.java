package com.mygdx.game.actores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.objetos.Onda;
import com.mygdx.game.pantallas.PantallaSeleccion;

import java.util.ArrayList;

public class Personaje extends Actor {

    public enum Direccion  { IZQUIERDA, DERECHA}
    public enum Estado {CALLENDO, SALTANDO, ENLASUPERFICIE, ANDANDO, ENPLATAFORMA, STANDBY}
    public enum Situacion{ AIRE, SUELO}
    public Direccion dActual, dPrevio;
    public Estado eActual, ePrevio;


    public World world;
    private Sprite sprite;
    public Music salto, caida, kamehamehaSound;
    private Texture standr,standl,jumpr, fallr, jumpl, falll,andando1,andando2,rafaga1, rafaga2,kamehamehaTexture,kamehamehaTextureL, lanzaR, lanzaL;

    public Body body;
    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Animation walkAnimation;
    private TextureRegion[]walkFrames;
    private TextureRegion[][]tmp;
    TextureRegion currentWalkFrame;

    public ArrayList <Onda> listaOndas;

    public int indexk, salud;

    private float animationTime,animationTime2;

    private Boolean rafagazo, bKamehameha;

    public int activador, personajeNumero;


    public Personaje(World mundo, int personajeElegido){

        this.world = mundo;

        personajeNumero = personajeElegido;

        activador = 0;
        salud = 2;

        andando1 = new Texture("personajes/Goku/animacion/wg.png");
        andando2 = new Texture("personajes/Goku/animacion2/walkinggoku2.png");
        kamehamehaTexture = new Texture("personajes/Goku/kamehameha/kamehamehaR.png");
        kamehamehaTextureL = new Texture("personajes/Goku/kamehameha/kamehamehaL.png");


        if(personajeElegido==1){
            standr = new Texture("personajes/Goku/gstandr.png");
            standl = new Texture("personajes/Goku/gstandl.png");
            jumpr = new Texture("personajes/Goku/gjumpr.png");
            jumpl = new Texture("personajes/Goku/gjumpl.png");
            fallr = new Texture("personajes/Goku/gfallr.png");
            falll = new Texture("personajes/Goku/gfalll.png");
            rafaga1 = new Texture("personajes/Goku/gokurafaga.png");
            rafaga2 = new Texture("personajes/Goku/gokurafagaL.png");

            lanzaR = new Texture("personajes/Goku/kamehameha/lanzandoR.png");
            lanzaL = new Texture("personajes/Goku/kamehameha/lanzandoL.png");

            salto = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/salto.mp3"));
            caida = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/caidatrassalto.mp3"));
            kamehamehaSound = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/kamehameha.mp3"));

            sprite = new Sprite(standr);

            currentWalkFrame = new TextureRegion(standr);
        }

        if(personajeElegido==2){
            standr = new Texture("personajes/Vegeta/vstandr.png");
            sprite = new Sprite(standr);
        }

        if(personajeElegido==3){
            standr = new Texture("personajes/Piccolo/pstandr.png");
            sprite = new Sprite(standr);
        }

        listaOndas = new ArrayList<>();

        eActual = Estado.STANDBY;
        dActual = Direccion.DERECHA;

        rafagazo = false;
        bKamehameha = false;



        propiedadesFisicas();

    }

    @Override
    public void draw(Batch batch, float parentAlpha ) {

       /* actualizar();
        sprite.setBounds(body.getPosition().x-7,body.getPosition().y-7,16,16);
        sprite.setPosition(body.getPosition().x - 7, body.getPosition().y - 6);
        sprite.draw(batch);*/
        //sprite = new Sprite(currentWalkFrame);


        for (Onda onda : listaOndas){
            onda.draw(batch,parentAlpha);
        }

        sprite.setBounds(body.getPosition().x,body.getPosition().y,16,16);
        sprite.setPosition(body.getPosition().x - 7, body.getPosition().y - 7);
        sprite.draw(batch);

        Gdx.app.log("mensaje","Estado de goku:"+eActual);

    }

   /* public void updateFrame(float elapsedTime){
        currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),true);
    }*/

    public void propiedadesFisicas(){

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(32,22);
        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();

        fixtureDef = new FixtureDef();
        polygonShape.setAsBox(6,6);
        fixtureDef.shape = polygonShape;
        body.createFixture(fixtureDef);
        fixtureDef.shape.dispose();

    }

    public void animacionAcciones(float elapsedTime){

        if(personajeNumero==1){

            indexk=0;

            if(body.getPosition().y<-12){
                salud = 0;
            }

            if(body.getLinearVelocity().y>0 && body.getLinearVelocity().x>=0 && eActual != Estado.ENPLATAFORMA && dActual == Direccion.DERECHA ){

                ePrevio = eActual;
                eActual = Estado.SALTANDO;
                sprite = new Sprite(jumpr);
                salto.play();
                salto.setVolume(0.02f);
            }else if(body.getLinearVelocity().y>0 && body.getLinearVelocity().x<0 && eActual != Estado.ENPLATAFORMA && dActual == Direccion.IZQUIERDA){

                ePrevio = eActual;
                eActual = Estado.SALTANDO;
                salto.play();
                salto.setVolume(0.02f);
                sprite = new Sprite(jumpl);
            }else if(body.getLinearVelocity().y <0 && body.getLinearVelocity().x>=0 && eActual != Estado.ENPLATAFORMA && dActual == Direccion.DERECHA){

                ePrevio = eActual;
                eActual = Estado.CALLENDO;
                sprite = new Sprite(fallr);
            }else if(body.getLinearVelocity().y<0 && body.getLinearVelocity().x<0 && eActual != Estado.ENPLATAFORMA && dActual == Direccion.IZQUIERDA){

                ePrevio = eActual;
                eActual = Estado.CALLENDO;
                sprite = new Sprite(falll);
            }else if(body.getLinearVelocity().x>0){
                ePrevio = eActual;
                eActual = Estado.ENLASUPERFICIE;

                tmp = TextureRegion.split(andando1,37,58);

                walkFrames = new TextureRegion[4];

                int index = 0;

                for(int i = 0; i<4;i++){
                    for(int j = 0; j<1;j++){
                        walkFrames[index++] = tmp[j][i];
                    }
                }

                walkAnimation = new Animation(body.getLinearVelocity().x*(1/(7*body.getLinearVelocity().x)),walkFrames);             // a mas número la animacion va mas lento , a 1 va muy lento a 0.001 muy rapido()

                currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),true);

                sprite = new Sprite(currentWalkFrame);

            }else if(body.getLinearVelocity().x<0){

                ePrevio = eActual;
                eActual = Estado.ENLASUPERFICIE;

                tmp = TextureRegion.split(andando2,37,58);

                walkFrames = new TextureRegion[4];

                int index2 = 0;

                for(int i = 0; i<4;i++){
                    for(int j = 0; j<1;j++){
                        walkFrames[index2++] = tmp[j][i];
                    }
                }

                walkAnimation = new Animation(body.getLinearVelocity().x*(1/(7*body.getLinearVelocity().x)),walkFrames);

                currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),true);

                sprite = new Sprite(currentWalkFrame);

            }else if(dActual == Direccion.DERECHA && (eActual == Estado.ENPLATAFORMA || eActual == Estado.ENLASUPERFICIE) ){
                sprite = new Sprite(standr);
            }else if(dActual == Direccion.IZQUIERDA && (eActual == Estado.ENPLATAFORMA || eActual == Estado.ENLASUPERFICIE) ){
                sprite = new Sprite(standl);
            }

            if(ePrevio == Estado.CALLENDO && body.getLinearVelocity().y == 0 ){
                ePrevio = eActual;
                eActual = Estado.ENLASUPERFICIE;
                caida.play();
                caida.setVolume(0.02f);
            }

            if(bKamehameha==true && dActual==Direccion.DERECHA){

                animationTime+=0.03f;

                tmp = TextureRegion.split(kamehamehaTexture,45,44);

                walkFrames = new TextureRegion[11];

                indexk = 0;

                for(int i = 0; i<11;i++){
                    for(int j = 0; j<1;j++){
                        walkFrames[indexk++] = tmp[j][i];
                    }
                }

                walkAnimation = new Animation(0.2f,walkFrames);

                currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((animationTime),false);

                sprite = new Sprite(currentWalkFrame);

                kamehamehaSound.play();
                kamehamehaSound.setVolume(0.03f);

                body.setLinearVelocity(0,0);

            }else if(bKamehameha==true && dActual==Direccion.IZQUIERDA){
                animationTime+=0.03f;

                tmp = TextureRegion.split(kamehamehaTextureL,45,44);

                walkFrames = new TextureRegion[10];

                indexk = 0;

                for(int i = 0; i<10;i++){
                    for(int j = 0; j<1;j++){
                        walkFrames[indexk++] = tmp[j][i];
                    }
                }

                walkAnimation = new Animation(0.2f,walkFrames);

                currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((animationTime),false);

                sprite = new Sprite(currentWalkFrame);

                kamehamehaSound.play();
                kamehamehaSound.setVolume(0.03f);

                body.setLinearVelocity(0,0);
            }else if(bKamehameha==false){
                animationTime=0;
            }

            if(rafagazo && dActual == Direccion.DERECHA && bKamehameha==false){

                sprite = new Sprite(rafaga1);

                body.setLinearVelocity(0,body.getLinearVelocity().y);
            }else if(rafagazo && dActual == Direccion.IZQUIERDA && bKamehameha==false){
                sprite = new Sprite(rafaga2);
                body.setLinearVelocity(0,body.getLinearVelocity().y);
            }

            if(eActual == Estado.ENPLATAFORMA){
                if(dActual == Direccion.IZQUIERDA) {
                    sprite = new Sprite(standl);
                }else{
                    sprite = new Sprite(standr);
                }
            }

        }




    }

    public Body getCuerpo(){
        return body;
    }

    public void setKamehameha(Boolean b){
        bKamehameha = b;
    }

    public void setOnda(Boolean e){
        rafagazo = e;
    }


}


