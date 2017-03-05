package com.sanilk.balance;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanilk.balance.Screens.DisplayScreen;
import com.sanilk.balance.Screens.GameOverScreen;
import com.sanilk.balance.Screens.QuoteScreen;

public class MyGame extends Game{
	public final static int WORLD_WIDTH = 3200;
	public final static int WORLD_HEIGHT = 3200;

	private static int score=0;

	public static void setScore(int score) {
		MyGame.score = score;
	}

	public static int getScore() {
		return score;
	}

	public SpriteBatch batch;
	public ShapeRenderer renderer;

	public Viewport viewport;
	public OrthographicCamera camera;

	public Assets assets;

	private Music sound;
	private long id;

	private Inputs inputs;

	@Override
	public void create () {
		assets=new Assets();

		batch = new SpriteBatch();
		renderer=new ShapeRenderer();
		renderer.setAutoShapeType(true);

		camera=new OrthographicCamera();
		viewport=new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		viewport.apply();

		inputs=new Inputs(this);
		Gdx.input.setInputProcessor(inputs);

//		sound=Gdx.audio.newSound(Gdx.files.internal("01.mp3"));
//		id=sound.play();
		sound=Gdx.audio.newMusic(Gdx.files.internal("04.mp3"));
		sound.setVolume(0.3f);

		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
//		setScreen(new QuoteScreen(this));
		setScreen(new DisplayScreen(this));
//		setScreen(new GameOverScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
	}

	@Override
	public void render () {
		super.render();
		camera.update();
		if(!sound.isPlaying()){
			sound.play();
		}
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//		batch.setProjectionMatrix(camera.combined);
//		batch.begin();
//
//		batch.end();
	}

}