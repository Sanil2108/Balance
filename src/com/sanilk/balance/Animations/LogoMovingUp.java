package com.sanilk.balance.Animations;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Admin on 21-01-2017.
 */
public class LogoMovingUp implements MainAnimationsInterface {
    private final Vector2 currentVector;
    private final Vector2 finalVector;

    private final int TOTAL_DURATION;
    private final int TOTAL_SPEED;
    private int duration=0;

    private final Sprite mainSprite;

    public boolean disposed=false;

    public boolean begun=false;
    public boolean ended=false;

    public boolean isBegun() {
        return begun;
    }

    //The original texture should be disposed as this animation class will hold
    //its own instance of the texture

    //About the above comment, something is happening
    //when i dispose the texture of this sprite, the texture of the previous
    //sprite also gets disposed. looking at the code, it is obvious why,
    //this sprite is not a different object but a different instance to the
    //same object. so do not dispose the sprite's texture.
    public LogoMovingUp(Vector2 currentVector, Vector2 finalVector, Sprite mainSprite, int totalDuration){

        //The original texture should be disposed as this animation class will hold
        //its own instance of the texture
        this.mainSprite=mainSprite;

        this.finalVector=finalVector;
        this.currentVector = currentVector;

        TOTAL_DURATION=totalDuration;
        TOTAL_SPEED=(int)(finalVector.y-currentVector.y)/totalDuration;

    }

    public void update(){
        if(begun) {
            if (duration == TOTAL_DURATION) {
                if(!disposed) {
                    dispose();
                }
                ended=true;
            } else {
                duration++;
                currentVector.set(currentVector.x, currentVector.y + TOTAL_SPEED);
                mainSprite.setPosition(currentVector.x, currentVector.y);
            }
        }
    }

    public boolean isEnded() {
        return ended;
    }

    public void begin(){
        begun=true;
    }

    public void draw(SpriteBatch batch){
        if(begun) {
            mainSprite.draw(batch);
        }
    }

    public void dispose(){
//        mainSprite.getTexture().dispose();
        disposed=true;
    }
}
