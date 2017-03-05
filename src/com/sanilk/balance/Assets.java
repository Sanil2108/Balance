package com.sanilk.balance;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class Assets {

    public AssetManager manager;

    public Assets(){
        manager=new AssetManager();
    }

    public void startLoading(){
        manager.load("badlogic.jpg", Texture.class);
        manager.load("Banner2.png", Texture.class);
        manager.load("Banner3.png", Texture.class);
        manager.load("Banner4.png", Texture.class);
        manager.load("logo12.png", Texture.class);
        manager.load("image.png", Texture.class);
        manager.load("test.jpg", Texture.class);
        manager.load("test2.jpg", Texture.class);
        manager.load("test3.jpeg", Texture.class);
        manager.load("quoteMain2.png", Texture.class);

    }

    public boolean getStatus(){
        return manager.update();
    }

    public float getProgress(){
        return manager.getProgress();
    }
}
