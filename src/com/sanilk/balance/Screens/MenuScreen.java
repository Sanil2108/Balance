package com.sanilk.balance.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanilk.balance.MyGame;

public class MenuScreen implements Screen {
    MyGame game;
    Viewport viewport;
    OrthographicCamera camera;

//    final Vector2 positionOfLogo;

    SpriteBatch batch;
    ShapeRenderer renderer;

    Sprite logo;

    //opacity stuff
    public Color currentColorPlayButton=new Color(0,0,0,1);
//    private final Color FINAL_COLOR_PLAY_BTN = new Color(1,1,1,1);
//    private Color currentInnerColorPlayButton=new Color(1, 1, 1, 1);
//    private final Color FINAL_INNER_COLOR_PLAY_BUTTON=new Color(0, 0, 0, 1);
    private final float COLOR_TRANSITION =1/256f;
    public boolean flagOpacityFull=false;

    private final int WIDTH_OF_PLAY_BUTTON=(int)(Gdx.graphics.getWidth()/3.25f);
    private final int HEIGHT_OF_PLAY_BUTTON=(int)((4f/3)*WIDTH_OF_PLAY_BUTTON);
    public Color play_btn_inner = Color.BLACK;

    private Sprite settings = new Sprite(new Texture(Gdx.files.internal("icons\\settings_white.png")));
    private final int SETTINGS_BTN_SCALE = 4;
    private final Vector2 SETTINGS_BTN_LOCATION = new Vector2(MyGame.WORLD_WIDTH/2-settings.getWidth()/2, MyGame.WORLD_HEIGHT/4f);

    //I can pass the already created viewport here as well
    //but it seems it could cause problems but in case
    //memory problems arise, i must remember this is a
    //valid option
    public MenuScreen(MyGame game){
        this.game=game;
        this.batch=game.batch;
        this.viewport=game.viewport;
        this.camera=game.camera;
        this.renderer=game.renderer;
        renderer.setAutoShapeType(true);
        logo = new Sprite(game.assets.manager.get("logo12.png", Texture.class));
        logo.setPosition(DisplayScreen.logoFinalVector.x,
                DisplayScreen.logoFinalVector.y);
        settings.setScale(SETTINGS_BTN_SCALE);
        settings.setPosition(SETTINGS_BTN_LOCATION.x, SETTINGS_BTN_LOCATION.y);
//        logo.setScale(1.4f);
        logo.setScale(1.8f, 1.4f);
//        positionOfLogo=new Vector2(game.WORLD_WIDTH/2 - logo.getWidth()/2, game.WORLD_HEIGHT/2-logo.getHeight()/2);
    }

    @Override
    public String toString() {
        return "MenuScreen";
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    public void update(){
        if(!flagOpacityFull){
            if(currentColorPlayButton.r<=1){
                currentColorPlayButton.r+= COLOR_TRANSITION;
                currentColorPlayButton.g+= COLOR_TRANSITION;
                currentColorPlayButton.b+= COLOR_TRANSITION;
            }else{
                flagOpacityFull=true;
                currentColorPlayButton.r=1;
                currentColorPlayButton.g=1;
                currentColorPlayButton.b=1;
            }
        }
    }

    @Override
    public void render(float dt) {
        update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        batch.begin();

        logo.draw(batch);
//        settings.draw(batch);
//        batch.draw(settings.getTexture(), SETTINGS_BTN_LOCATION.x, SETTINGS_BTN_LOCATION.y, MyGame.WORLD_WIDTH/10, MyGame.WORLD_HEIGHT/10);

        batch.end();
        renderer.begin();

        int k=10;
        int x1=Gdx.graphics.getWidth()/2-WIDTH_OF_PLAY_BUTTON/2,
                y1=Gdx.graphics.getHeight()/2-HEIGHT_OF_PLAY_BUTTON/2,
                x2=Gdx.graphics.getWidth()/2-WIDTH_OF_PLAY_BUTTON/2,
                y2=Gdx.graphics.getHeight()/2+HEIGHT_OF_PLAY_BUTTON/2,
                x3=Gdx.graphics.getWidth()/2+WIDTH_OF_PLAY_BUTTON/2,
                y3=Gdx.graphics.getHeight()/2;
//        double m1=1000000000, m2=Math.abs((y3-y2)/(x3-x2));
        //For some reason, java cant calculate the value of theta but python does, just take that and put it in there
        /*
                x1=100
                y1=100
                x2=100
                y2=200
                x3=175
                y3=150
                m1=1000000000
                m2=abs((y3-y2)/(x3-x2))
                theta=math.degrees(abs(math.atan((m2-m1)/(1+m1*m2))))
        */
        //then copy it below
//        double theta= Math.toDegrees(Math.atan((m2-m1)/(1+m1*m2)));
        double theta=56.31;
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(currentColorPlayButton);
        renderer.triangle(x1, y1, x2, y2, x3, y3);
        renderer.setColor(play_btn_inner);
        renderer.triangle(
                x1+k,
                (float)(y1+k+Math.abs(k*(1/Math.atan(theta)))),
                x2+k,
                (float)(y2-k-Math.abs(k*(1/Math.atan(theta)))),
                (float)(x3-k*(1/Math.sin(Math.atan((y2-y1)/(double)(2*(x3-x2)))))),
                (y1+y2)/2);

        renderer.end();
//        System.out.println("In "+game.getScreen().toString());
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
        batch.dispose();
        logo.getTexture().dispose();
    }
}
