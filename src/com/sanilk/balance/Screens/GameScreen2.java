package com.sanilk.balance.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanilk.balance.Animations.StarAnimation.StarsHandler;
import com.sanilk.balance.MyGame;
import com.sanilk.balance.Sprites.Enemy.EnemyHandlerClass;
import com.sanilk.balance.Sprites.Pallet;

import java.util.Random;
import java.util.Scanner;

import static com.badlogic.gdx.Gdx.gl;

/**
 * Created by Admin on 27-01-2017.
 */
public class GameScreen2 implements Screen {

    public EnemyHandlerClass enemyHandlerClass;

    public enum THEME{
        DARK, LIGHT
    }

    private THEME currentTheme = THEME.DARK;

    public THEME getCurrentTheme() {
        return currentTheme;
    }

    public MyGame game;
    Viewport viewport;
    OrthographicCamera camera;
    SpriteBatch batch;
    ShapeRenderer renderer;
    public Pallet pallet;

    private Sound collisionSound;

    //maybe add a single Random in MyGame class since so many classes use it
    private Random random;

    public final static int OPACITY_FALL = 5;
    private boolean inverting=false;
    private int currentOpacity = 0;

    public boolean controlsInverted;

    //Stars animation stuff
    public StarsHandler starsHandler;

    //Score stuff
    private final static int SCORE_INCREASE=1;
    private final static int SCORE_INCREASE_FOR_SWICTH=10;
    private int score=0;

    private static final int HEALTH_DECREASE=10;
    private static final int MAX_HEALTH=50;
    private int health=MAX_HEALTH;

    public int getScore() {
        return score;
    }

    public void incrementScore(){
        score+=SCORE_INCREASE;
    }

    //Intersection stuff
    private static Intersector intersector=new Intersector();

    public boolean paused=false;

    private boolean fading;
    private int fadingRectOpacity;

    private final static int MAX_WAITING=20;
    private int waitingCycles=0;

    public GameScreen2(MyGame game){
        fadingRectOpacity=0;
        fading=false;
        this.game=game;
        this.batch=game.batch;
        this.viewport=game.viewport;
        this.camera=game.camera;
        this.renderer=game.renderer;

        this.renderer.setAutoShapeType(true);

        this.pallet = new Pallet();

        this.starsHandler=new StarsHandler(this);

        enemyHandlerClass=new EnemyHandlerClass(this);

        controlsInverted =true;

        currentTheme=THEME.DARK;
        collisionSound=Gdx.audio.newSound(Gdx.files.internal("collision1.mp3"));

        Gdx.gl.glLineWidth(3);
    }

    @Override
    public void show() {

    }

    public static float fixAngle(float temp1){
        while(temp1>=180){
            temp1-=180;

        }
        return temp1;
    }

    public void checkCollision(){
        for(int i=0;i<enemyHandlerClass.getAllEnemies().getAllEnemies().size();i++){
            if(!enemyHandlerClass.getAllEnemies().getAllEnemies().get(i).isCollided()) {
                if (intersector.intersectRectangles(
                        pallet.getRectangle(),
                        enemyHandlerClass.getAllEnemies().getAllEnemies().get(i).getCollisionRectangle(),
                        new Rectangle(0, 0, 0, 0)
                )) {
                    if(fixAngle(Math.abs(pallet.getAngle()))<Pallet.tempAngle ||
                            (fixAngle(Math.abs(pallet.getAngle()))<180+Pallet.tempAngle &&
                                    fixAngle(Math.abs(pallet.getAngle())) > 180-Pallet.tempAngle)) {
                        health -= HEALTH_DECREASE;
                        System.out.println(health);
                        enemyHandlerClass.getAllEnemies().getAllEnemies().get(i).collide();
                        collisionSound.setVolume(collisionSound.play(), 1f);
                    }
                }
            }
        }
    }

    public void updateScore(){
        score+=SCORE_INCREASE;
        MyGame.setScore(score);
        System.out.println(MyGame.getScore());
    }

    public void invertControls(){
       controlsInverted =!controlsInverted;
    }

    public void update(){
        if(!paused) {
            if(Pallet.getCurrTheme()== Pallet.STATES.DARK_BG && this.currentTheme==THEME.LIGHT){
                System.out.println("SOMETHING");
            }
            if(Pallet.getCurrTheme()== Pallet.STATES.LIGHT_BG && this.currentTheme==THEME.DARK){
                System.out.println("SOMETHING");
            }
            if (health > 0) {
                enemyHandlerClass.update();
                starsHandler.update();
                pallet.update();
                invert();
                checkCollision();
            } else {
                paused=true;
                fading=true;
//                game.setScreen(new GameOverScreen(game));
            }
        }
        if(paused){
            startFinalFading();
        }
    }

    private static float DIFFICULTY_INCREASE=0.5f;
    public static final int RANDOMNESS_FACTOR=4;   //couldn't think of a proper name
    public void increaseDifficulty(){
        score+=SCORE_INCREASE_FOR_SWICTH;
        pallet.increaseDifficulty();
        starsHandler.increaseSpeed(DIFFICULTY_INCREASE);
        enemyHandlerClass.increaseDifficulty(DIFFICULTY_INCREASE);
    }

    private static final int COLOR_CHANGE_FOR_FINAL_FADING=5;

    public void startFinalFading(){
//        waitingCycles=MAX_WAITING;
        fadingRectOpacity+=COLOR_CHANGE_FOR_FINAL_FADING;
//        if(waitingCycles==MAX_WAITING){
        if(fadingRectOpacity>255){
//            if(pallet.currentColor.r>=0) {
//                System.out.println("DONE WAITING");
//                currentOpacity+=COLOR_CHANGE_FOR_FINAL_FADING;
//                pallet.currentColor.r -= COLOR_CHANGE_FOR_FINAL_FADING;
//                pallet.currentColor.g -= COLOR_CHANGE_FOR_FINAL_FADING;
//                pallet.currentColor.b -= COLOR_CHANGE_FOR_FINAL_FADING;
//            }else{
                dispose();
//                MyGame.setScore(score);
                game.setScreen(new GameOverScreen(game));
//                System.out.println("DONE");
//            }
//            starsHandler.update();
//            this.currentTheme=THEME.LIGHT;
        }else{
            waitingCycles++;
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        if (!inverting) {
            if (currentTheme == THEME.LIGHT) {
                Gdx.gl.glClearColor(1, 1, 1, 1);
            } else {
                Gdx.gl.glClearColor(0, 0, 0, 1);
            }
        } else {
            if (currentTheme == THEME.LIGHT) {
                Gdx.gl.glClearColor(1 - (float) currentOpacity / 255f,
                        1 - (float) currentOpacity / 255f,
                        1 - (float) currentOpacity / 255f,
                        1 - (float) currentOpacity / 255f);
            } else {
                Gdx.gl.glClearColor((float) currentOpacity / 255f,
                        (float) currentOpacity / 255f,
                        (float) currentOpacity / 255f,
                        (float) currentOpacity / 255f);
            }
        }
        renderer.begin();
//        gl.glEnable(GL20.GL_BLEND);
//        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//


        enemyHandlerClass.render(renderer);
        starsHandler.render(renderer);
        pallet.render(renderer);
        if(fading){
            renderer.setColor(0, 0, 0, fadingRectOpacity/255f);
//            renderer.setColor(0, 0, 0, 1);
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        renderer.end();

        batch.begin();


        batch.end();
    }

    public void startInverting(){

        increaseDifficulty();
        inverting = true;
        invertControls();
//        starsHandler.startInverting();
    }

    public boolean isInverting() {
        return inverting;
    }

    public int getCurrentOpacity() {
        return currentOpacity;
    }

    public void invert(){
        if(inverting) {
            if (currentTheme == THEME.DARK) {
                if (currentOpacity == 255) {
                    inverting = false;
                    currentTheme= THEME.LIGHT;
                    currentOpacity = 0;
                }else{
                    currentOpacity+=OPACITY_FALL;
                }
            } else {
                if (currentOpacity == 255) {
                    inverting = false;
                    currentTheme= THEME.DARK;
                    currentOpacity = 0;
                }else{
                    currentOpacity+=OPACITY_FALL;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "GameScreen2";
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
        enemyHandlerClass.reset();
        enemyHandlerClass.getAllEnemies().getAllEnemies().clear();
    }
}
