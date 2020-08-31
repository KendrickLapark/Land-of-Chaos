package com.mygdx.game.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Juego;


public class PantallaSeleccion implements Screen {


    Stage stage;
    int personajeActual, cont;
    Juego juego;
    TextButton startButton;
    Texture texture1;

    void preparaPantalla(){

        FreeTypeFontGenerator freeTypeFontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("recursos/impact.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        freeTypeFontParameter.size = 15;
        freeTypeFontParameter.borderWidth = 1;
        freeTypeFontParameter.borderColor = Color.PURPLE;

        BitmapFont bitmapFont = freeTypeFontGenerator.generateFont(freeTypeFontParameter);


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = bitmapFont;
        textButtonStyle.fontColor = Color.WHITE;

        startButton = new TextButton("START", textButtonStyle);
        startButton.setPosition(stage.getWidth() - startButton.getWidth()/0.45f, stage.getHeight() - startButton.getHeight());

        texture1 = new Texture("personajes/Goku/gstandr.png");

        Image goku = new Image(texture1);
        goku.setBounds(16,16,20,20);
        goku.setPosition(30,40);

        stage.addActor(goku);


    }

    void controlador(){

        if(cont==0){
            startButton.addListener( new ClickListener(){
                @Override
                public void touchUp(InputEvent inputEvent,float x, float y, int pointer, int button){
                    //dispose();
                    juego.setScreen(new PrimerMundo(juego));
                }
            });

            cont++;
            stage.addActor(startButton);
        }



    }

    public PantallaSeleccion(Juego j){
        personajeActual = 0;
        cont = 0;

        this.juego = j;
        FitViewport fitViewport = new FitViewport(160,120);
        stage = new Stage(fitViewport);
        Gdx.input.setInputProcessor(stage);

        preparaPantalla();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();


        controlador();

    }

    @Override
    public void resize(int width, int height) {

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

        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }
}
