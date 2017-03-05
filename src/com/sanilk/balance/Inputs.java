package com.sanilk.balance;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.sanilk.balance.Screens.GameOverScreen;
import com.sanilk.balance.Screens.GameScreen2;
import com.sanilk.balance.Screens.MenuScreen;
import com.sanilk.balance.Screens.QuoteScreen;

/**
 * Created by Admin on 27-01-2017.
 */
public class Inputs implements InputProcessor {
    private MyGame game;

    public Inputs(MyGame game){
        this.game=game;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(game.getScreen().toString() == "MenuScreen"){
//            if(((MenuScreen)game.getScreen()).flagOpacityFull) {
//            game.setScreen(new GameScreen2(game));
                ((MenuScreen) game.getScreen()).play_btn_inner = ((MenuScreen) game.getScreen()).currentColorPlayButton;
//            }
        }

        if(game.getScreen().toString() == "GameScreen2"){
//            if(screenY<Gdx.graphics.getHeight()/2) {
//                ((GameScreen2) game.getScreen()).startInverting();
//                ((GameScreen2) game.getScreen()).pallet.startInverting();
//            }else {
                if ((screenX > Gdx.graphics.getWidth()/2 && ((GameScreen2)(game.getScreen())).controlsInverted)||
                        (screenX < Gdx.graphics.getWidth()/2 && !((GameScreen2)(game.getScreen())).controlsInverted)) {
//                    System.out.println("Right");
                    ((GameScreen2) game.getScreen()).pallet.moveRight();
                }
                if ((screenX < Gdx.graphics.getWidth()/2 &&((GameScreen2)(game.getScreen())).controlsInverted)||
                        (screenX > Gdx.graphics.getWidth()/2 && !((GameScreen2)(game.getScreen())).controlsInverted)) {
//                    System.out.println("Left");
                    ((GameScreen2) game.getScreen()).pallet.moveLeft();
                }
//            }
        }

        //Bhenchodh fir se JRE ka EXCEPTION_ACCESS_VIOLATION aa rha hai
        //jb bhi gameoverscreen ko dispose krta hu
        if(game.getScreen().toString() == "GameOverScreen"){
//            game.getScreen().dispose();
            if(((GameOverScreen)(game.getScreen())).isAllAnimationsEnded()==false){
                ((GameOverScreen)(game.getScreen())).setAllAnimationsEnded(true);
            }else{
                MyGame.setScore(0);
                game.setScreen(new GameScreen2(game));
            }

        }

        if(game.getScreen().toString()=="QuoteScreen"){
            ((QuoteScreen)game.getScreen()).skipped();
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(game.getScreen().toString() == "MenuScreen"){
//            game.setScreen(new GameScreen2(game));
            game.setScreen(new QuoteScreen(game));
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
