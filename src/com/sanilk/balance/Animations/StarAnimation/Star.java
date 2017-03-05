package com.sanilk.balance.Animations.StarAnimation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.sanilk.balance.Screens.GameScreen2;

import static com.badlogic.gdx.Gdx.gl;

/**
 * Created by Admin on 27-01-2017.
 */
public class Star {

    float x, y;
    int size;

    private static final Color darkColor = new Color(200f/255, 200f/255, 200f/255, 1);
    private static final Color lightColor = new Color(25f/255, 25f/255, 25f/255, 1);

    //this is meant to serve as bounds. I might add code that changes speed based on the speed
    //of obstacles
    private final static Vector2 SPEED = new Vector2(12, 12);

    public Vector2 currentSpeed = new Vector2(12, 12);

    private final StarsHandler starsHandler;

    public void startInverting(){
        currentSpeed.x=-currentSpeed.x;
        currentSpeed.y=-currentSpeed.y;
    }

    public Star(StarsHandler starsHandler, int size, int x, int y){
        this.size=size;
        this.x=x;
        this.y=y;

        this.starsHandler = starsHandler;

        gl.glEnable(GL20.GL_BLEND);
        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void update(){
        x=x-currentSpeed.x;
        y=y-currentSpeed.y;
    }

    public void renderInverted(ShapeRenderer renderer){
        renderer.set(ShapeRenderer.ShapeType.Filled);
        if(starsHandler.getGameScreen().getCurrentTheme() == GameScreen2.THEME.DARK){

            gl.glEnable(GL20.GL_BLEND);
            gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            renderer.setColor(darkColor.r, darkColor.g, darkColor.b, 1);

            renderer.rect(x, y, size, size);
        }else{

            gl.glEnable(GL20.GL_BLEND);
            gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            renderer.setColor(lightColor.r, lightColor.g, lightColor.b, 1);

            renderer.rect(x, y, size, size);
        }
    }

    public void render(ShapeRenderer renderer){
        if(starsHandler.getGameScreen().isInverting()){
            renderInverted(renderer);
        }else {
            if (starsHandler.getGameScreen().getCurrentTheme() == GameScreen2.THEME.DARK) {
                renderer.setColor(darkColor);
            } else {
                renderer.setColor(lightColor);
            }

            gl.glEnable(GL20.GL_BLEND);
            gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.rect(x, y, size, size);
        }

//
//        gl.glEnable(GL20.GL_BLEND);
//        gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//        renderer.setColor(new Color(1, 0, 0, 0.5f));
//        renderer.rect(150, 100, 100, 200);
//        renderer.setColor(new Color(0, 1, 0, 0.5f));
//        renderer.rect(100, 150, 100, 200);
    }
}
