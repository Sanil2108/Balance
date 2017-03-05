package com.sanilk.balance.Animations.BackgroundAnimation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BackgroundAnimation {

    public Sprite mainSprite;
    private static final Texture mainBackgroundTexture=new Texture(Gdx.files.internal("bg6gs1.png"));
    private static final int SPEED=4;

    public static final float WIDTH = (mainBackgroundTexture.getWidth()/(float)mainBackgroundTexture.getHeight())*(float)Gdx.graphics.getHeight();
    public static final int HEIGHT = Gdx.graphics.getHeight();

    public BackgroundAnimation(int x, int y){
        mainSprite=new Sprite(mainBackgroundTexture);
        mainSprite.setSize(WIDTH, HEIGHT);
        mainSprite.setPosition(x, y);
    }

    public void setX(int x){
        mainSprite.setX(x);
    }

    public void update(){
        mainSprite.setX(mainSprite.getX()-SPEED);
    }

    public void render(SpriteBatch batch){
        mainSprite.draw(batch);
    }

    public boolean readyToDispose(){
        if(mainSprite.getX()+mainSprite.getWidth()<0){
            return true;
        }
        return false;
    }

    public void dispose(){

    }
}