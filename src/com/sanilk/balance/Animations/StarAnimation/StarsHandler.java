package com.sanilk.balance.Animations.StarAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sanilk.balance.Screens.GameScreen2;

import java.util.Random;

/**
 * Created by Admin on 27-01-2017.
 */
public class StarsHandler {

    private static final int PROBABILITY_OF_STARS = 6;
    private static final Vector2 SIZE_RANGE = new Vector2(2, 12);

    private GameScreen2 gameScreen;
    private AllStars stars;

    private Random random;
    private Vector2 range;

    public GameScreen2 getGameScreen() {
        return gameScreen;
    }

    public void startInverting(){
        for(int i=0;i<stars.allStarsList.size();i++){
            gameScreen.starsHandler.stars.allStarsList.get(i).startInverting();
        }
    }

    public void increaseSpeed(float value){
        for(int i=0;i<stars.allStarsList.size();i++){
            stars.allStarsList.get(i).currentSpeed.set(
                    stars.allStarsList.get(i).currentSpeed.x+value,
                    stars.allStarsList.get(i).currentSpeed.y+value
            );
        }
    }

    public StarsHandler(GameScreen2 gameScreen){
        this.gameScreen=gameScreen;
        stars=AllStars.getInstance();
        random=new Random();
        range=new Vector2(Gdx.graphics.getWidth()*3, Gdx.graphics.getHeight());
    }

    public void update(){
        if(random.nextInt(1) == 0){
            for(int i=0;i<PROBABILITY_OF_STARS;i++){
                generate();
            }
        }
        for(int i=0;i<stars.allStarsList.size();i++){
            if(gameScreen.starsHandler.stars.allStarsList.get(i).x<0 ||
                    gameScreen.starsHandler.stars.allStarsList.get(i).y<0){
                gameScreen.starsHandler.stars.allStarsList.remove(i);
            }else {
                gameScreen.starsHandler.stars.allStarsList.get(i).update();
            }
        }
    }

    public void render(ShapeRenderer renderer){
        for(int i=0;i<gameScreen.starsHandler.stars.allStarsList.size();i++){
            gameScreen.starsHandler.stars.allStarsList.get(i).render(renderer);
        }
    }

    public void generate(){
        int x=random.nextInt((int)range.x);
        int y=(int)range.y+1;
        generate(x, y);
    }

    public void generate(int x, int y){
        int size = random.nextInt(((int)(SIZE_RANGE.y-SIZE_RANGE.x)))+(int)SIZE_RANGE.x;
        size /= random.nextInt(3)+1;
        Star star=new Star(this, size, x, y);
        gameScreen.starsHandler.stars.allStarsList.add(star);
    }
}
