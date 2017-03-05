package com.sanilk.balance.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.sanilk.balance.MyGame;

import java.util.ArrayList;

/**
 * Created by Admin on 12-02-2017.
 */
public class QuoteScreen implements Screen {
    MyGame game;
    SpriteBatch batch;
    ShapeRenderer renderer;

//    Sprite sprite=new Sprite(new Texture("quoteMain2.png"));
    Sprite sprite;

    private ArrayList<Block> allBlocks;


    //Placing rectangles and stuff
    private final static float Y_OFFSET=0.1125f*Gdx.graphics.getHeight();
    private final static float[][] ALL_SIZES={            //Remember these go from last to top
            {Gdx.graphics.getWidth(), 0.1475f*Gdx.graphics.getHeight()},
            {Gdx.graphics.getWidth(), 0.3275f*Gdx.graphics.getHeight()},
            {Gdx.graphics.getWidth(), 0.085f*Gdx.graphics.getHeight()},
            {Gdx.graphics.getWidth(), 0.225f*Gdx.graphics.getHeight()},
            {Gdx.graphics.getWidth(), 0.25f*Gdx.graphics.getHeight()}
    };

    public QuoteScreen(MyGame game){

        this.game=game;
        this.batch=game.batch;
        this.renderer=game.renderer;

        this.renderer.setAutoShapeType(true);


        allBlocks=new ArrayList<Block>();
        allBlocks.add(
                new Block(
                        new Rectangle(
                                0, Y_OFFSET, ALL_SIZES[0][0], ALL_SIZES[0][1]
                        )
                )
        );

        sprite=new Sprite(game.assets.manager.get("quoteMain2.png", Texture.class));
        this.sprite.setPosition(0, 0);
        this.sprite.setSize(MyGame.WORLD_WIDTH, MyGame.WORLD_HEIGHT);

//        renderer.setProjectionMatrix(game.camera.combined);


        for(int i=0;i<3;i++) {
            allBlocks.add(
                    new Block(
                            new Rectangle(
                                    0, allBlocks.get(i).mainRect.y + allBlocks.get(i).mainRect.getHeight()+1/80f*Gdx.graphics.getHeight(),
                                    ALL_SIZES[i+1][0],
                                    ALL_SIZES[i+1][1]+0.01875f*Gdx.graphics.getHeight()
                            )
                    )
            );
        }

        allBlocks.get(allBlocks.size()-1).startAnimating();


//        allBlocks.add(
//                new Block(
//                        new Rectangle(
//                                0, allBlocks.get(0).mainRect.y+allBlocks.get(1).mainRect.y+Y_OFFSET, 475, 118
//                        )
//                )
//        );
//
//
//        allBlocks.add(
//                new Block(
//                        new Rectangle(
//                                0, allBlocks.get(2).mainRect.y+allBlocks.get(2).mainRect.height, 475, 118
//                        )
//                )
//        );
    }

    @Override
    public void show() {

    }

    //Counter for starting next screen
    private final static int COUNTER_FOR_NEXT_SCREEN=100;
    private int counterForNextScreen=0;

    public void update(){
        for(int i=0;i<allBlocks.size();i++){
            allBlocks.get(i).update();
        }

        for(int i=allBlocks.size()-1;i>0;i--){
            if(allBlocks.get(i).isAnimationEnded()){
                allBlocks.get(i-1).startAnimating();
            }
        }

        if(allBlocks.get(0).isAnimationEnded()){
            //start counter
            counterForNextScreen++;
        }

        if(counterForNextScreen>=COUNTER_FOR_NEXT_SCREEN){

            //Start next screen
            dispose();
//                game.setScreen(new MenuScreen(game));
            game.setScreen(new GameScreen2(game));
        }
    }

    @Override
    public void render(float delta) {
        update();

        //Clearing the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        batch.end();
        renderer.begin();

        renderer.set(ShapeRenderer.ShapeType.Filled);

        for(int i=allBlocks.size()-1;i>=0;i--){
            allBlocks.get(i).render(renderer);
        }

        renderer.end();

    }

    public void skipped(){
        System.out.println("SKIPPED");
        counterForNextScreen=COUNTER_FOR_NEXT_SCREEN;
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
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private class Block{
        Rectangle mainRect;

        //Animation stuff
        private static final int ANIMATION_COUNTER=50;
        private static final int OPACITY_INCREASE=(int)(255f/ANIMATION_COUNTER);
        private int currentO=255;

        private boolean animationStarted=false;
        private boolean animationEnded=false;

        public Block(Rectangle mainRect){
            this.mainRect=mainRect;
        }

        public void render(ShapeRenderer renderer){
            //For opacity things to work
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            renderer.setColor(
                    0, 0, 0,
                    currentO/255f
            );
            renderer.rect(
                    mainRect.x,
                    mainRect.y,
                    mainRect.width,
                    mainRect.height
            );

        }

        public void update(){
            if(animationStarted) {
                if (currentO > 0) {
                    currentO -= OPACITY_INCREASE;
                }else{
                    animationEnded=true;
                }
            }
        }

        public void startAnimating(){
            animationStarted=true;
        }

        public boolean isAnimationEnded() {
            return animationEnded;
        }
    }

    @Override
    public String toString() {
        return "QuoteScreen";
    }
}
