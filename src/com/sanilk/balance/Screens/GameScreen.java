package com.sanilk.balance.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanilk.balance.Animations.BackgroundAnimation.BackgroundAnimationHandler;
import com.sanilk.balance.MyGame;
import com.sanilk.balance.Sprites.Pallet;

public class GameScreen implements Screen{
    private MyGame game;
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Pallet pallet;

    public BackgroundAnimationHandler backgroundAnimationHandler;

    public GameScreen(MyGame game){
        this.game=game;
        this.batch=game.batch;
        this.viewport=game.viewport;
        this.camera=game.camera;
        this.renderer=game.renderer;

        this.renderer.setAutoShapeType(true);

        this.pallet = new Pallet();

        backgroundAnimationHandler=new BackgroundAnimationHandler(this);
    }

    public void update(){
        pallet.update();
        backgroundAnimationHandler.update();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        backgroundAnimationHandler.render(batch);
//        pallet.render(batch);

        batch.end();

        renderer.begin();

        renderer.end();

        update();
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
        this.batch.dispose();
        this.renderer.dispose();
    }
}
