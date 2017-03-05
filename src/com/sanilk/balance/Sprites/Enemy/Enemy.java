package com.sanilk.balance.Sprites.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.sanilk.balance.Screens.GameScreen2;
import com.sanilk.balance.Sprites.Pallet;

public class Enemy {
    private Rectangle collisionRectangle;

    private static Pallet.STATES currTheme= Pallet.getCurrTheme();


    private final int BUFFER_HEIGHT;
    public final static int HEIGHT=Gdx.graphics.getHeight()/5;
    public final static int WIDTH= Gdx.graphics.getWidth()/12;

    private static final float DEFAULT_SPEED=(7/800f)*Gdx.graphics.getHeight();
    private static float speed=DEFAULT_SPEED;

    public boolean disposed=false;

    private final static Color lightColor=new Color(0, 0, 0, 1);
    private final static Color darkColor=new Color(1, 1, 1, 1);
    private static Color currentColor=new Color(lightColor);
    private static boolean inverting=false;

    private final static float COLOR_CHANGE= GameScreen2.OPACITY_FALL/255f;

    private boolean collided=false;

    public boolean isCollided() {
        return collided;
    }

    public void collide(){
        collided=true;
    }

    public void changeSpeed(float val){
        speed+=val;
    }

    public void reset(){
        speed=DEFAULT_SPEED;
    }

    public Enemy(int x, int y){
        BUFFER_HEIGHT=(int)(Gdx.graphics.getHeight()/20f);
        collisionRectangle=new Rectangle(x, y, WIDTH, HEIGHT);
        currentColor=new Color(1, 1, 1, 1);
    }

    public Rectangle getCollisionRectangle() {
        return collisionRectangle;
    }

//    public void checkCollision(){
//        if(intersector.intersectRectangles(
//                pallet.getRectangle(),
//
//                )){
//
//        }
//    }
//
//    public void checkCollisionAndUpdateScore(){
//
//    }

    //    public static void invert() {
////        if (inverting) {
////            if (currTheme.equals(Pallet.STATES.DARK_BG)) {
////                if (currentColor.r == 0) {
////                    //inverting is done
////                    inverting = false;
////                    currTheme = Pallet.STATES.LIGHT_BG;
////                } else {
////                    if (currentColor.r > 0) {
////                        currentColor.r -= COLOR_CHANGE;
////                        currentColor.g -= COLOR_CHANGE;
////                        currentColor.b -= COLOR_CHANGE;
////                    } else {
////                        currentColor.r = 0;
////                        currentColor.b = 0;
////                        currentColor.g = 0;
////                    }
////                }
////            } else {
////                if (currentColor.r == 1) {
////                    //inverting is done
////                    inverting = false;
////                    currTheme = Pallet.STATES.DARK_BG;
////                } else {
////                    if (currentColor.r > 0) {
////                        currentColor.r += COLOR_CHANGE;
////                        currentColor.g += COLOR_CHANGE;
////                        currentColor.b += COLOR_CHANGE;
////                    } else {
////                        currentColor.r = 1;
////                        currentColor.b = 1;
////                        currentColor.g = 1;
////                    }
////                }
////            }
////        }
//        if(inverting){
//            if(currTheme.equals(Pallet.STATES.DARK_BG)){
//                if(currentColor.r==0){
//                    //inverting is done
//                    inverting=false;
//                    currTheme= Pallet.STATES.LIGHT_BG;
//                }else{
//                    if(currentColor.r>0) {
//                        currentColor.r -= COLOR_CHANGE;
//                        currentColor.g -= COLOR_CHANGE;
//                        currentColor.b -= COLOR_CHANGE;
//                    }else{
//                        currentColor.r=0;
//                        currentColor.b=0;
//                        currentColor.g=0;
//                    }
//                }
//            }else{
//                if(currentColor.r==1){
//                    //inverting is done
//                    inverting=false;
//                    currTheme= Pallet.STATES.DARK_BG;
//                }else{
//                    if(currentColor.r>0) {
//                        currentColor.r += COLOR_CHANGE;
//                        currentColor.g += COLOR_CHANGE;
//                        currentColor.b += COLOR_CHANGE;
//                    }else{
//                        currentColor.r=1;
//                        currentColor.b=1;
//                        currentColor.g=1;
//                    }
//                }
//            }
//        }
//    }
//
//    public static void startInverting(){
//        inverting=true;
//    }

    public boolean canDispose(){
        return collisionRectangle.y+HEIGHT<0;
    }

    public void update(){
//        invert();
        if(!disposed) {
            collisionRectangle.y -= speed;
        }
    }

    public void render(ShapeRenderer renderer){
        if(!disposed) {
            if(!collided) {
                renderer.setColor(Pallet.currentColor);
            }else{
                renderer.setColor(Color.RED);
            }
            renderer.set(ShapeRenderer.ShapeType.Line);
            renderer.rect(
                    collisionRectangle.x,
                    collisionRectangle.y,
                    WIDTH,
                    HEIGHT
            );
        }
    }

    public void renderCurrent(ShapeRenderer renderer){
        if(!disposed && Pallet.debug) {
            renderer.setColor(Color.RED);
            renderer.set(ShapeRenderer.ShapeType.Line);
            renderer.rect(
                    collisionRectangle.x,
                    collisionRectangle.y,
                    WIDTH,
                    HEIGHT
            );
        }
    }

    public void dispose(){
        disposed=true;
    }
}
