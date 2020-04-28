package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Juego;
import com.mygdx.game.actores.Personaje;
import com.mygdx.game.actores.Saibaman;
import com.mygdx.game.input.Teclado;

import javax.swing.Box;

public class PrimerMundo implements Screen {

    private Juego juego;
    //variables Box2d
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private TiledMap mapa; //Mapa del juego

    private Personaje p1;
    private Saibaman s1;

    private Music musica;

    private Viewport viewport;
    private OrthographicCamera camara; //Camara del juego
    private OrthogonalTiledMapRenderer renderer; //Renderer del mapa

    float elapsedTime;

    private Teclado teclado;


    public PrimerMundo(Juego j){
        this.juego = j;
        camara = new OrthographicCamera();
        viewport = new FitViewport(300,180,camara);
        world = new World(new Vector2(0,-9.8f),true);
        mapa = new TmxMapLoader().load("mapa/m1,5.tmx");
        renderer = new OrthogonalTiledMapRenderer(mapa,Juego.unitScale);
        p1 = new Personaje(world);
        s1 = new Saibaman(world);


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

        p1.mandoTeclado();

        camara.update();
        camara.position.x = p1.getCuerpo().getPosition().x;
        renderer.setView(camara);

        //p1.updateFrame(elapsedTime);

        renderer.render();

        box2DDebugRenderer.render(world,camara.combined);
        juego.batch.setProjectionMatrix(camara.combined);

        juego.batch.begin();
         p1.animacionAcciones(elapsedTime);
         p1.draw(juego.batch,0);
         s1.animacionAcciones(elapsedTime);
         s1.draw(juego.batch,0);
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

    }


}
