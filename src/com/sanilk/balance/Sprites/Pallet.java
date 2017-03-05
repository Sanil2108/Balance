package com.sanilk.balance.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.sanilk.balance.Screens.GameScreen2;
import com.sanilk.balance.Sprites.Enemy.Enemy;

/**
 * Created by Admin on 26-01-2017.
 */
public class Pallet {
    public enum STATES{
        DARK_BG, LIGHT_BG
    }

    public static STATES getCurrTheme() {
        return currTheme;
    }

    private static STATES currTheme= STATES.DARK_BG;

//    public final static Texture lightTexture =new Texture(Gdx.files.internal("lightPallet.png"));
//    public final static Texture darkTexture =new Texture(Gdx.files.internal("darkPallet.png"));

//    private final static int DEFAULT_Y=(int)(Gdx.graphics.getHeight()*8.5f/100f);

    private final static Color lightColor=new Color(0, 0, 0, 1);
    private final static Color darkColor=new Color(1, 1, 1, 1);
    public static Color currentColor=new Color(darkColor);

    private final static float COLOR_CHANGE= GameScreen2.OPACITY_FALL/255f;

    private float angle=0;
    private final float CHANGE_IN_ANGLE=1;

    private int x=200, y=100;
    private int vy;
    private int ay;
    public final static int WIDTH= (int)(Gdx.graphics.getWidth()*2f/5);
    private final static int HEIGHT= (int)(Gdx.graphics.getHeight()*1f/100f);

    private boolean inverting=false;

    private int defaultFallInAccel = 1;
    private final static int DEFAULT_FALL_IN_VELOCITY = 1;

    public static boolean debug=false;

    private Rectangle collisionRectangle;

    private static final int DEFAULT_X=Gdx.graphics.getWidth()/2-WIDTH/2;
    private static final int DEFAULT_Y=(int)(Gdx.graphics.getHeight()/8f);

    private static final int javaChutiyapaHaiIsliyeMarginOfError=5;

    public static double tempAngle=Math.toDegrees(Math.asin(((float)2*Enemy.WIDTH)/((float)WIDTH-Enemy.WIDTH/2f)))
            +javaChutiyapaHaiIsliyeMarginOfError;

    public Pallet(){

//        rectangle=new Rectangle(x, y, WIDTH, HEIGHT);
        x=DEFAULT_X;
        y=DEFAULT_Y;
        collisionRectangle=new Rectangle(x, y, WIDTH, HEIGHT);
        vy = ay = 0;

        System.out.println((float)Enemy.WIDTH+"  "+(float)WIDTH);
        System.out.println(tempAngle);

        System.out.println(Enemy.WIDTH+"\t"+WIDTH);
        currTheme=STATES.DARK_BG;
        currentColor=new Color(darkColor);
    }

    public Rectangle getRectangle(){
        return collisionRectangle;
    }

    public void invert(){
        if(inverting){
            if(currTheme.equals(STATES.DARK_BG)){
                if(currentColor.r==0){
                    //inverting is done
                    inverting=false;
                    currTheme= STATES.LIGHT_BG;
                }else{
                    if(currentColor.r>0) {
                        currentColor.r -= COLOR_CHANGE;
                        currentColor.g -= COLOR_CHANGE;
                        currentColor.b -= COLOR_CHANGE;
                    }else{
                        currentColor.r=0;
                        currentColor.b=0;
                        currentColor.g=0;
                    }
                }
            }else{
                if(currentColor.r==1){
                    //inverting is done
                    inverting=false;
                    currTheme= STATES.DARK_BG;
                }else{
                    if(currentColor.r>0) {
                        currentColor.r += COLOR_CHANGE;
                        currentColor.g += COLOR_CHANGE;
                        currentColor.b += COLOR_CHANGE;
                    }else{
                        currentColor.r=1;
                        currentColor.b=1;
                        currentColor.g=1;
                    }
                }
            }
        }

//        if(currentColor.equals(darkColor)){
//            currentColor=lightColor;
//        }else{
//            currentColor=darkColor;
//        }

    }

    public void startInverting(){
        inverting=true;

//        ay=-ay;
//        vy=-vy;
    }

    public float getAngle() {
        if(angle>90 && angle<180){
            System.out.println("HERE");
        }
        return angle;
    }

    public void render(ShapeRenderer renderer){
        renderer.setColor(currentColor);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(x, y, WIDTH/2, HEIGHT/2, WIDTH, HEIGHT, 1, 1, angle);
        if(debug){
//            System.out.println(tempAngle+"\t"+angle);
            if(tempAngle>GameScreen2.fixAngle(Math.abs(angle))) {
                renderer.set(ShapeRenderer.ShapeType.Filled);
                renderer.setColor(Color.RED);
                renderer.rect(collisionRectangle.x,
                        collisionRectangle.y,
                        collisionRectangle.width,
                        collisionRectangle.height);
            }else{

            }
        }
    }

    private int currentValueOfChangeInAY =10;
    private final int MAX_VALUE_OF_CHANGE_IN_AY=20;
    private final int MIN_VALUE_OF_CHANGE_IN_AY=10;
    private boolean increasing=true;

    public void increaseDifficulty(){
        if(increasing){
            if(currentValueOfChangeInAY>MAX_VALUE_OF_CHANGE_IN_AY){
                increasing=false;
            }else{
                currentValueOfChangeInAY++;
            }
        }else{
            if(currentValueOfChangeInAY<MIN_VALUE_OF_CHANGE_IN_AY){
                increasing=true;
            }else{
                currentValueOfChangeInAY--;
            }
        }
    }

    public void moveRight(){
        ay= currentValueOfChangeInAY;
    }

    public void moveLeft(){
        ay=-currentValueOfChangeInAY;
    }

    public void updateTempAngle(double newAngle){
//        tempAngle=newAngle;
    }

    public void update(){

        //update temp angle
//        tempAngle=Math.toDegrees(Math.asin(((float)(Enemy.WIDTH))/((float)WIDTH)));


        invert();

        //Rotation stuff
//        angle+=CHANGE_IN_ANGLE;
        angle=vy;

        if(ay>0){
            ay-= defaultFallInAccel;
        }
        if(ay<0){
            ay+= defaultFallInAccel;
        }

        vy+=ay;

        if(vy>0){
            vy-=DEFAULT_FALL_IN_VELOCITY;
        }
        if(vy<0){
            vy+=DEFAULT_FALL_IN_VELOCITY;
        }

//        if(angle!=0) {
//            collisionRectangle.setX((float) (DEFAULT_X + WIDTH / 2 + Math.abs(WIDTH * Math.cos(Math.toRadians(angle)))));
//            collisionRectangle.setY((float) (DEFAULT_Y + HEIGHT / 2 + Math.abs(Math.abs(WIDTH * Math.cos(angle)) * Math.tan(angle))));
//        }
    }
}
