package com.sanilk.balance.Animations;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Admin on 21-01-2017.
 */
//This is just a substitute until I get the abstract
// class MainAnimationsClass running
public interface MainAnimationsInterface {
    public void update();
    public boolean isBegun();
    public boolean isEnded();
    public void begin();
    public void draw(SpriteBatch batch);
    public void dispose();
}
