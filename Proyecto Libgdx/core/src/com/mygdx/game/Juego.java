package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.pantallas.PrimerMundo;

public class Juego extends Game {

	public SpriteBatch batch;
	Screen pantallaActual;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PrimerMundo(this));
	}

	public void setPantallaActual(Screen pa){
		pantallaActual=pa;
		setScreen(pantallaActual);
	}

}