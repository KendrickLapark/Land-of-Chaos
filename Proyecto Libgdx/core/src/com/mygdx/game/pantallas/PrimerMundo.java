package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Juego;
import com.mygdx.game.Plataforma;
import com.mygdx.game.actores.Personaje;
import com.mygdx.game.actores.Saibaman;
import com.mygdx.game.input.Teclado;
import com.mygdx.game.objetos.Capsula;
import com.mygdx.game.objetos.Onda;

import java.util.Iterator;

import javax.swing.Box;

public class PrimerMundo implements Screen {

    private Juego juego;
    //variables Box2d
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private TiledMap mapa; //Mapa del juego

    private Personaje p1;
    private Saibaman s1;
    private Plataforma plataforma;

    private Onda onda;
    private Capsula c1;

    private Music musica;

    private Viewport viewport;
    private OrthographicCamera camara; //Camara del juego
    private OrthogonalTiledMapRenderer renderer; //Renderer del mapa

    private Array<Onda> ondas, ondasToDestroy;

    float elapsedTime;

    private Teclado teclado;


    public PrimerMundo(Juego j){
        this.juego = j;
        camara = new OrthographicCamera();
        viewport = new FitViewport(300,180,camara);
        world = new World(new Vector2(0,-98f),true);
        mapa = new TmxMapLoader().load("mapa/m1,5.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa);
        p1 = new Personaje(world);
        s1 = new Saibaman(world, 82, 22);
        c1 = new Capsula(world,"Objetos/capsule.png",82,110);
        plataforma = new Plataforma(world,82, 50);

        TextoInterface.SetSpriteBatch(juego.batch);

        ondas = new Array<>();
        ondasToDestroy = new Array<>();


        musica = Gdx.audio.newMusic(Gdx.files.internal("sonido/musica/dbzInicio.mp3"));
        musica.play();
        musica.setVolume(0.03f);
        musica.setLooping(true);





        box2DDebugRenderer = new Box2DDebugRenderer();

        camara.position.set(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2,0);

        BodyDef bodyDef = new BodyDef();
        PolygonShape polygonShape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for(MapObject object : mapa.getLayers().get("suelo").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rectangle = ((RectangleMapObject)object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX() + rectangle.getWidth()/2, rectangle.getY() + rectangle.getHeight()/2);

            body = world.createBody(bodyDef);

            polygonShape.setAsBox(rectangle.getWidth()/2, rectangle.getHeight()/2 );
            fixtureDef.shape = polygonShape;
            body.createFixture(fixtureDef);

        }

        teclado = new Teclado(p1);
        Gdx.input.setInputProcessor(teclado);

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {

                for(int i = 0; i<ondas.size;i++){

                    if(i >= 1 &&  contact.getFixtureA().getBody()==ondas.get(i-1).getCuerpo()&&
                            contact.getFixtureB().getBody()==ondas.get(i).getCuerpo()){
                        System.out.println("SE HA BORRADO");
                        ondasToDestroy.add(ondas.get(i));
                        ondasToDestroy.add(ondas.get(i-1));
                        ondas.removeIndex(i);
                        ondas.removeIndex(i-1);
                    }

                    if(contact.getFixtureA().getBody() == s1.body && contact.getFixtureB().getBody() == ondas.get(i).getCuerpo()){
                        System.out.println("Impacto");
                        ondasToDestroy.add(ondas.get(i));
                        ondas.removeIndex(i);
                        s1.vidas--;

                    }

                }

                    if(contact.getFixtureA().getBody() == p1.getCuerpo() && contact.getFixtureB().getBody() == plataforma.getCuerpo()){
                        System.out.println("En la plataforma");
                        p1.eActual = Personaje.Estado.ENPLATAFORMA;
                    }

                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        for(Onda onda : ondasToDestroy){
                            onda.body.setActive(false);
                        }

                        if(s1.vidas==0){
                            s1.body.setActive(false);
                        }
                    }
                });


            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        elapsedTime += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

        camara.update();
        camara.position.x = p1.getCuerpo().getPosition().x;
        renderer.setView(camara);

        //p1.updateFrame(elapsedTime);

        renderer.render();

        box2DDebugRenderer.render(world,camara.combined);
        juego.batch.setProjectionMatrix(camara.combined);

        juego.batch.begin();
        TextoInterface.draw(camara);
         p1.animacionAcciones(elapsedTime);
         p1.draw(juego.batch,0);

         s1.animacionAcciones(elapsedTime);
         s1.draw(juego.batch,0);

         plataforma.draw(juego.batch,0);

         c1.draw(juego.batch,0);

         for (Onda onda : ondas){

                 onda.draw(juego.batch,0);
         }

         if(p1.salud == 0 || TextoInterface.tiempototal<0){
             juego.setScreen(new GameOverScreen(juego));
             TextoInterface.tiempo=0;
             dispose();
         }

        //System.out.println("Posicion del player x:"+p1.getCuerpo().getPosition().x+"Posicion y:"+p1.getCuerpo().getPosition().y);

         acciones();

        juego.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camara.position.set(camara.viewportWidth/2,camara.viewportHeight/2,0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        renderer.dispose();
        world.dispose();
        musica.dispose();

    }


    public void acciones(){
        if(Gdx.input.isKeyPressed(Input.Keys.F)){
            p1.setOnda(true);
        }else{
            p1.setOnda(false);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.F)){
            ondas.add(new Onda(world,p1));
        }
    }


}
