package com.sanilk.balance.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanilk.balance.Animations.LogoMovingUp;
import com.sanilk.balance.Assets;
import com.sanilk.balance.MyGame;
import com.sanilk.balance.RandomUselessStuff.LoadingScreenMessages;

import java.util.Random;

public class DisplayScreen implements Screen {
    MyGame game;

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font2.ttf"));
    BitmapFont font12;

    private final Vector2 FONT_POSITION;

//    private final int WORLD_WIDTH = 1900;
//    private final int WORLD_HEIGHT = 3200;

    private static Sprite logo;

    private Random random;
    private int frames_passed=0;
    private int i;
    private final int MAX_FRAMES=150;

    SpriteBatch batch;
    ShapeRenderer renderer;

    public Assets assets;

    Viewport viewport;
    OrthographicCamera camera;

    private Rectangle loadingBar;

    //Loading bar stuff
    private static final int LOADING_BAR_HEIGHT=2;

    private int waitingCycles=0;
    private static final int WAITING_CYCLES = 30;

    //Logo animation stuff
    public static Vector2 logoFinalVector;
    private LogoMovingUp logoMovingUpAnimation;
    private static final float TOTAL_DURATION = 50;

    private boolean loaded = false;

    public DisplayScreen(MyGame game){

//        batch = new SpriteBatch();
//        renderer=new ShapeRenderer();
//
//        renderer.setAutoShapeType(true);
//
//        camera=new OrthographicCamera();
//        viewport=new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
//        viewport.apply();
//
//        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);


        this.game=game;
        this.batch=game.batch;
        this.viewport=game.viewport;
        this.camera=game.camera;
        this.renderer=game.renderer;

        assets=game.assets;
        assets.startLoading();

        logo=new Sprite(new Texture("logo12.png"));
        logo.setPosition(game.WORLD_WIDTH/2 - logo.getWidth()/2, game.WORLD_HEIGHT/2-logo.getHeight()/2);
//        logo.setScale(1.4f);
        logo.setScale(1.8f, 1.4f);


        loadingBar=new Rectangle(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()-LOADING_BAR_HEIGHT, 0, LOADING_BAR_HEIGHT);

        logoFinalVector = new Vector2(game.WORLD_WIDTH/2-logo.getWidth()/2, game.WORLD_HEIGHT*7.5f/10);
        logoMovingUpAnimation=new LogoMovingUp(
                new Vector2(logo.getX(), logo.getY()),
                logoFinalVector,
                logo,
                (int)TOTAL_DURATION
        );

//        font.getData().setScale(10);
        //for 5, its 18 in drawing
//        font12.getData().setScale(5);

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size=80;
        font12 = generator.generateFont(parameter);

        FONT_POSITION =new Vector2(game.WORLD_WIDTH/3f, game.WORLD_HEIGHT/5f);
        random=new Random();

        i=random.nextInt(LoadingScreenMessages.MESSAGES.length/2);
    }

//    @Override
//    public void create () {
//    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    public void update(){
        if(!assets.getStatus()){
//            System.out.println(assets.getProgress());
            loadingBar.setWidth(assets.getProgress()*Gdx.graphics.getWidth());
            loadingBar.setX(Gdx.graphics.getWidth()/2 - loadingBar.getWidth()/2);
        }else{
            if(!logoMovingUpAnimation.begun) {
                loaded=true;
                logoMovingUpAnimation.begin();
            }

            if(logoMovingUpAnimation.isEnded()) {
                logo.setPosition(logoFinalVector.x, logoFinalVector.y);
                if (waitingCycles < WAITING_CYCLES) {
                    waitingCycles++;
                } else {
                    dispose();
                    game.setScreen(new MenuScreen(game));
//                    game.setScreen(new QuoteScreen(game));
                }
            }
//            System.out.println("DONE");
        }

        logoMovingUpAnimation.update();
    }

    @Override
    public void render (float delta) {
        update();

        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if(frames_passed>MAX_FRAMES) {
            i = random.nextInt(LoadingScreenMessages.MESSAGES.length / 2);
            frames_passed=0;
        }else{
            frames_passed++;
        }
        if(!logoMovingUpAnimation.isBegun()) {
//            font12.draw(batch, LoadingScreenMessages.MESSAGES[2*i],
//                    game.WORLD_WIDTH/2-LoadingScreenMessages.MESSAGES[2*i].length()*19
//                    , FONT_POSITION.y);
//            font12.draw(batch, LoadingScreenMessages.MESSAGES[2*i+1],
//                    game.WORLD_WIDTH/2-LoadingScreenMessages.MESSAGES[2*i+1].length()*19
//                    , FONT_POSITION.y-game.WORLD_HEIGHT/28);
        }

        logoMovingUpAnimation.draw(batch);

        if(logoMovingUpAnimation.isEnded() || !logoMovingUpAnimation.isBegun()) {
            logo.draw(batch);
        }

        batch.end();

        //Drawing the loadingBar

        if(!loaded) {

            renderer.begin();

            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Color.WHITE);
            renderer.rect(
                    loadingBar.x,
                    loadingBar.y,
                    loadingBar.width,
                    loadingBar.height
            );

            //Drawing the bottom loading bar
            renderer.rect(
                    loadingBar.x,
                    0,
                    loadingBar.width,
                    loadingBar.height
            );

            renderer.end();
        }
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
        logo.getTexture().dispose();
        //batch.dispose();
//        renderer.dispose();
//        logoMovingUpAnimation.dispose();
    }
}