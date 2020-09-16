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

    public enum Direccion  { IZQUIERDA, DERECHA} // Enumerado que indica la dirección del personaje.
    public enum Estado {CALLENDO, SALTANDO, ENLASUPERFICIE, ANDANDO, ENPLATAFORMA, STANDBY} // Enumerado con distintos estados del personaje.
    public enum Situacion{ AIRE, SUELO} // Enumerado que indica si el personaje esta en el suelo o en el aire.
    public Direccion dActual, dPrevio; // Variable con la direccion actual del personaje.
    public Estado eActual, ePrevio; // Variable con el estado actual y previo del personaje.

    public World world; // Mundo del juego.
    private Sprite sprite; // Sprite que muestra el aspecto del personaje;
    public Music salto, caida, kamehamehaSound; // Sonidos del personaje en el juego.
    private Texture standr,standl,jumpr, fallr, jumpl, falll,andando1,andando2,rafaga1, rafaga2,kamehamehaTexture,kamehamehaTextureL, lanzaR, lanzaL; // Texturas que componen los distintas acciones del personaje.

    public Body body; //Cuerpo del personaje
    private BodyDef bodyDef; // Propiedades del cuerpo del personaje.
    private FixtureDef fixtureDef; // Propiedades geométricas del cuerpo del personaje.

    private Animation walkAnimation; // Animación del personaje andando.
    private TextureRegion[]walkFrames; // Array de partes de la textura que compone la animación del personaje andando.
    private TextureRegion[][]tmp; // Array con las partes que componen la animación del personaje.
    private TextureRegion currentWalkFrame; // Textura que muestra el frame actual del personaje andando.

    public ArrayList <Onda> listaOndas; // ArrayList que contiene las ondas lanzadas por el personaje.

    private float animationTime;

    private Boolean rafagazo, bKamehameha, loop; // Boolean para indicar si el personaje esta lanzando una ráfaga, un kamehameha , y para controlar si las animaciones cuando esta andando se repiten o no.

    public int personajeNumero, indexk, salud; // Variable que indica la opción elegida en la pantalla de elección de personaje, un contador para recorrer el bucle de la animación del personaje andando y la salud del personaje.


    public Personaje(World mundo, int personajeElegido){

        this.world = mundo;

        personajeNumero = personajeElegido;

        salud = 2;

        eleccionPersonaje();

        listaOndas = new ArrayList<>();

        eActual = Estado.STANDBY;
        dActual = Direccion.DERECHA;

        rafagazo = false;
        bKamehameha = false;

        propiedadesFisicas();

        loop = true;

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
                int a = 0;
                ePrevio = eActual;
                eActual = Estado.ENLASUPERFICIE;

                if(personajeNumero == 1){
                    a = 3;
                    loop = true;
                    tmp = TextureRegion.split(andando1,37,58);
                }

                if(personajeNumero == 2){
                    a = 2;
                    loop = false;
                    tmp = TextureRegion.split(andando1,35,58);
                }

                if(personajeNumero == 3){
                    a = 2;
                    loop = false;
                    tmp = TextureRegion.split(andando1,37,58);
                }

                walkFrames = new TextureRegion[a];

                int index = 0;

                for(int i = 0; i<a;i++){
                    for(int j = 0; j<1;j++){
                        walkFrames[index++] = tmp[j][i];
                    }
                }

                walkAnimation = new Animation(body.getLinearVelocity().x*(1/(7*body.getLinearVelocity().x)),walkFrames); // a mas número la animacion va mas lento , a 1 va muy lento a 0.001 muy rapido()

                currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),loop);

                sprite = new Sprite(currentWalkFrame);

            }else if(body.getLinearVelocity().x<0){

                int a = 0;

                ePrevio = eActual;
                eActual = Estado.ENLASUPERFICIE;

                if( personajeNumero == 1 ){
                    a = 3;
                    tmp = TextureRegion.split(andando2,37,58);
                }

                if( personajeNumero == 2 ){
                    a = 2;
                    loop = false;
                    tmp = TextureRegion.split(andando2,35,58);
                }

                if( personajeNumero == 3){
                    a = 2;
                    loop = false;
                    tmp = TextureRegion.split(andando2,39,58);
                }

                walkFrames = new TextureRegion[a];

                int index2 = 0;

                for(int i = 0; i<a;i++){
                    for(int j = 0; j<1;j++){
                        walkFrames[index2++] = tmp[j][i];
                    }
                }

                walkAnimation = new Animation(body.getLinearVelocity().x*(1/(7*body.getLinearVelocity().x)),walkFrames);

                currentWalkFrame =  (TextureRegion)walkAnimation.getKeyFrame((elapsedTime),loop);

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

    public void eleccionPersonaje(){

        if(personajeNumero==1){
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


            andando1 = new Texture("personajes/Goku/animacion/wg.png");
            andando2 = new Texture("personajes/Goku/animacion2/walkinggoku2.png");
            kamehamehaTexture = new Texture("personajes/Goku/kamehameha/kamehamehaR.png");
            kamehamehaTextureL = new Texture("personajes/Goku/kamehameha/kamehamehaL.png");

            sprite = new Sprite(standr);

            currentWalkFrame = new TextureRegion(standr);
        }

        if(personajeNumero==2){
            standr = new Texture("personajes/Vegeta/vstandr.png");
            standl = new Texture("personajes/Vegeta/vstandl.png");
            jumpr = new Texture("personajes/Vegeta/vjumpr.png");
            jumpl = new Texture("personajes/Vegeta/vjumpl.png");
            fallr = new Texture("personajes/Vegeta/vfallr.png");
            falll = new Texture("personajes/Vegeta/vfalll.png");
            rafaga1 = new Texture("personajes/Vegeta/vrafagar.png");
            rafaga2 = new Texture("personajes/Vegeta/vrafagal.png");
            andando1 = new Texture("personajes/Vegeta/animacion1/vandandor.png");
            andando2 = new Texture("personajes/Vegeta/animacion2/vandandol.png");

            salto = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/salto.mp3"));
            caida = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/caidatrassalto.mp3"));



            sprite = new Sprite(standr);

            currentWalkFrame = new TextureRegion(standr);
        }

        if(personajeNumero==3){
            standr = new Texture("personajes/Piccolo/pstandr.png");
            standl = new Texture("personajes/Piccolo/pstandl.png");
            jumpr = new Texture("personajes/Piccolo/pjumpr.png");
            jumpl = new Texture("personajes/Piccolo/pjumpl.png");
            fallr = new Texture("personajes/Piccolo/pfallr.png");
            falll = new Texture("personajes/Piccolo/pfalll.png");
            rafaga1 = new Texture("personajes/Piccolo/prafagar.png");
            rafaga2 = new Texture("personajes/Piccolo/prafagal.png");
            andando1 = new Texture("personajes/Piccolo/animacion1/pandandor.png");
            andando2 = new Texture("personajes/Piccolo/animacion2/pandandol.png");

            salto = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/salto.mp3"));
            caida = Gdx.audio.newMusic(Gdx.files.internal("sonido/efectos/caidatrassalto.mp3"));

            sprite = new Sprite(standr);

            currentWalkFrame = new TextureRegion(standr);
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


