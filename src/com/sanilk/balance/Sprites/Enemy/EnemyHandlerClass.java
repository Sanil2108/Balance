package com.sanilk.balance.Sprites.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sanilk.balance.Screens.GameScreen2;
import com.sanilk.balance.Sprites.Pallet;

import java.util.Random;

/**
 * Created by Admin on 01-02-2017.
 */
public class EnemyHandlerClass {
    private GameScreen2 gameScreen2;
    private AllEnemies allEnemies;

    private Random random;

    private final float BOUND=1/4f;

    private final static int DEFAULT_Y= Gdx.graphics.getHeight()+10;

    //Random generation stuff
    private static final int PROBABILITY_OF_GENERATION=10;
    private static final int FRAME_WAIT=2;
    private int frame_wait=0;
    private final static int MAX_ENEMIES=6;

    //Switching stuff
    private final static int MAX_DIFFICULTY=10;
    private int MAX_GENERATED=10;
    private int generated=MAX_GENERATED;

    public Enemy currEnemy;

    public EnemyHandlerClass(GameScreen2 gameScreen2){
        this.gameScreen2=gameScreen2;
        allEnemies=AllEnemies.getInstance();
        random=new Random();
    }

    public AllEnemies getAllEnemies() {
        return allEnemies;
    }

    public void reset(){
        for(int i=0;i<allEnemies.getAllEnemies().size();i++){
            allEnemies.getAllEnemies().get(i).reset();
        }
    }

    public void increaseDifficulty(float value){
//        if(MAX_DIFFICULTY>MAX_GENERATED-value) {
//            MAX_GENERATED -= value;
//        }
        for(int i=0;i<allEnemies.getAllEnemies().size();i++){
            allEnemies.getAllEnemies().get(i).changeSpeed(value);
        }
    }

    public void updateTempAngle() {
        if (currEnemy != null){
            gameScreen2.pallet.updateTempAngle(
                    Math.toDegrees(
                            Math.asin(
                                    (float) (
                                            currEnemy.getCollisionRectangle().getY() + currEnemy.getCollisionRectangle().getHeight() -
                                                    gameScreen2.pallet.getRectangle().getY() + gameScreen2.pallet.getRectangle().getHeight()
                                    )
                                            / ((float) Pallet.WIDTH / 2f))
                    )
            );
        }
    }

    public void update(){
//        updateTempAngle();
        if(random.nextInt(100)<=PROBABILITY_OF_GENERATION){
            generate();
        }
        for(int i=0;i<allEnemies.getAllEnemies().size();i++){
            if(allEnemies.getAllEnemies().get(i).getCollisionRectangle().getY()<
                    gameScreen2.pallet.getRectangle().getY()+gameScreen2.pallet.getRectangle().getHeight()+gameScreen2.pallet.getRectangle().getWidth()/2){
                currEnemy=allEnemies.getAllEnemies().get(i);
            }
            if(allEnemies.getAllEnemies().get(i).canDispose()){
                if(allEnemies.getAllEnemies().get(i).isCollided()==false){
                    gameScreen2.updateScore();
                }
                allEnemies.getAllEnemies().get(i).dispose();
                allEnemies.getAllEnemies().remove(i);
            }else{
                allEnemies.getAllEnemies().get(i).update();
            }
        }
    }

    public void render(ShapeRenderer renderer){
        for(int i=0;i<allEnemies.getAllEnemies().size();i++){
            allEnemies.getAllEnemies().get(i).render(renderer);
        }
        if(currEnemy!=null) {
            currEnemy.renderCurrent(renderer);
        }
        if(Pallet.debug){
            renderer.setColor(Color.RED);
            renderer.rect(
                    0,
                    gameScreen2.pallet.getRectangle().getY()+gameScreen2.pallet.getRectangle().getHeight()+gameScreen2.pallet.getRectangle().getWidth()/2,
                    Gdx.graphics.getWidth(),
                    10
            );
        }
    }

    public void generate(){
//        if(allEnemies.getAllEnemies().size()<MAX_ENEMIES) {
            if (frame_wait == 0) {
                int x_temp = 0;
                int y_temp = Gdx.graphics.getHeight();
                if (random.nextInt(2) == 0) {
                    if(allEnemies.getAllEnemies().size()!=0) {
                        if (allEnemies.getAllEnemies().get(allEnemies.getAllEnemies().size() - 1).getCollisionRectangle().y + Enemy.HEIGHT + 30 > Gdx.graphics.getHeight()) {
                            y_temp = (int) allEnemies.getAllEnemies().get(allEnemies.getAllEnemies().size() - 1).getCollisionRectangle().y + Enemy.HEIGHT + 100;
                        }
                    }
                    x_temp = (int) (BOUND * Gdx.graphics.getWidth());
                    Enemy enemy = new Enemy(
                            x_temp,
                            y_temp
                    );
                    allEnemies.getAllEnemies().add(enemy);
                } else {
                    if(allEnemies.getAllEnemies().size()!=0) {
                        if (allEnemies.getAllEnemies().get(allEnemies.getAllEnemies().size() - 1).getCollisionRectangle().y + Enemy.HEIGHT + 30 > Gdx.graphics.getHeight()) {
                            y_temp = (int) allEnemies.getAllEnemies().get(allEnemies.getAllEnemies().size() - 1).getCollisionRectangle().y + Enemy.HEIGHT + 100;
                        }
                    }
                    x_temp = (int) ((1 - BOUND) * Gdx.graphics.getWidth());
                    Enemy enemy = new Enemy(
                            x_temp - Enemy.WIDTH,
                            y_temp
                    );
                    allEnemies.getAllEnemies().add(enemy);
                }
                frame_wait = FRAME_WAIT;
                if (generated == 0) {
                    if(random.nextInt(GameScreen2.RANDOMNESS_FACTOR)==0) {
                        generated = MAX_GENERATED;
                        System.out.println("SWITCH");
                        ((GameScreen2) gameScreen2.game.getScreen()).startInverting();
                        ((GameScreen2) gameScreen2.game.getScreen()).pallet.startInverting();
//                        Enemy.startInverting();
                    }
                } else {
                    generated--;
                }
            } else {
                frame_wait--;
            }
//        }

    }

    public void startInverting(){
//        for(int i=0;i<allEnemies.getAllEnemies().size();i++){
//            allEnemies.getAllEnemies().get(i).startInverting();
//        }
//        Enemy.startInverting();
    }

    public void dispose(){

    }
}
