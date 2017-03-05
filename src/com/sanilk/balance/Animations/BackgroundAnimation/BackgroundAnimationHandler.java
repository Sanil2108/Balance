package com.sanilk.balance.Animations.BackgroundAnimation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sanilk.balance.Screens.GameScreen;

/**
 * Created by Admin on 27-01-2017.
 */
public class BackgroundAnimationHandler {

    private AllBackgroundAnimations allBackgroundAnimations;
    private GameScreen gameScreen;

    public BackgroundAnimationHandler(GameScreen gameScreen){
        this.gameScreen=gameScreen;
        allBackgroundAnimations= AllBackgroundAnimations.getInstance();

        for(int i=0;i<7;i++){
            allBackgroundAnimations.allBackgroundAnimations.add(
                    new BackgroundAnimation((int) BackgroundAnimation.WIDTH*i, 0)
            );
        }
    }

    public void update(){
        for(int i=0;i<allBackgroundAnimations.allBackgroundAnimations.size();i++){
            if(allBackgroundAnimations.allBackgroundAnimations.get(i).readyToDispose()){
                float maxX = 0;
                for(int j=0;j<allBackgroundAnimations.allBackgroundAnimations.size();j++){
                    if(allBackgroundAnimations.allBackgroundAnimations.get(j).mainSprite.getX()>maxX){
                        maxX=allBackgroundAnimations.allBackgroundAnimations.get(j).mainSprite.getX();
                    }
                }
                allBackgroundAnimations.allBackgroundAnimations.get(i).mainSprite.setX(maxX+ BackgroundAnimation.WIDTH-4f);
            }else {
                allBackgroundAnimations.allBackgroundAnimations.get(i).update();
            }
        }
    }

    public void render(SpriteBatch batch){
        for(int i=0;i<allBackgroundAnimations.allBackgroundAnimations.size();i++){
            allBackgroundAnimations.allBackgroundAnimations.get(i).render(batch);
        }
    }
}
