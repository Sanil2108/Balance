package com.sanilk.balance.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanilk.balance.MyGame;

public class GameOverScreen implements Screen {
    private MyGame game;
    Viewport viewport;
    OrthographicCamera camera;

    ShapeRenderer renderer;
    SpriteBatch batch;

    Message gameOver;
    Message retry;
    Message score;
    Message highScore;
    Message highScoreScore;
    int scoreMax=1212;
    private final static int TIME_FOR_SCORE_INCREASE=100;
    private final int SCORE_INCREASE;

    Rectangle playAgainRect;

    //Font stuff
    FreeTypeFontGenerator generator=new FreeTypeFontGenerator(Gdx.files.internal("fonts/font10.ttf"));
    BitmapFont mainFont;
//    BitmapFont mainFont2;

//    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/myfont.ttf"));
//    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//    parameter.size = 12;
//    parameter.mono=true;
//    BitmapFont font12 = generator.generateFont(parameter);

    private class Message{
        String message;
        Vector2 position;
        int lines;

        float verticalPos;
        int gap;
        public Message(String message, int lines, int gap, float verticalPos){
            this.message=message;
//            this.position=position;
            this.lines=lines;
//            this.position=new Vector2(Gdx.graphics.getWidth()/2-gap*message.length(),
//                    Gdx.graphics.getHeight()* verticalPos);
            this.position=new Vector2(MyGame.WORLD_WIDTH/2-gap*message.length(),
                    MyGame.WORLD_HEIGHT* verticalPos);
//            mainFont.getData().setScale(2.5f);

            this.gap=gap;
            this.verticalPos=verticalPos;
        }

        public void setVerticalPos(float verticalPos) {
            this.position=new Vector2(MyGame.WORLD_WIDTH/2-gap*message.length(),
                    MyGame.WORLD_HEIGHT* verticalPos);
            this.verticalPos=verticalPos;
            recalculatePosition();
        }

        public void recalculatePosition(){
            this.position=new Vector2(MyGame.WORLD_WIDTH/2-gap*message.length(),
                    MyGame.WORLD_HEIGHT* verticalPos);
        }

        public Vector2 getPosition(){
            return position;
        }

        public String getMessage() {
            return message;
        }

        public int getLines() {
            return lines;
        }
    }

    //BC fir se opacity and timer. getting real tired of this shit
    private final int MAX_TIMER_ONCE_AGAIN_FOR_FUCKS_SAKE;
    private int currentFramesElapsed=0;
    private final int OPACITY_DECREASE_AGAIN_FOR_FUCKS_SAKE;
    private int currentOpacity=255;

    //Moving the gameOver and score up
    private float currentPosition;
    private final float FINAL_POS;
    private final float INCREASE_IN_CURRENT_POS;

    //Animation boolean
    private boolean allAnimationsEnded=false;

    public boolean isAllAnimationsEnded() {
        return allAnimationsEnded;
    }

    public void setAllAnimationsEnded(boolean allAnimationsEnded) {
        this.allAnimationsEnded = allAnimationsEnded;
    }

    private final Rectangle coverRect;

    private int highScoreScoreInt;

    private Preferences preferences;

    public GameOverScreen(MyGame game){
        this.game=game;
        this.viewport=game.viewport;
        this.camera=game.camera;
        this.batch=game.batch;
        this.renderer=game.renderer;

        scoreMax=MyGame.getScore();

        renderer.setAutoShapeType(true);

        preferences=Gdx.app.getPreferences("BalanceScoreFile");

        retry=new GameOverScreen.Message(
                "R E T R Y",
                1,
                52,
                2.2f/5f
        );

        retry.recalculatePosition();

        gameOver=new GameOverScreen.Message(
                "G a m e   O v e r",
//                "B A L A N C E",
//                "I234567890",
                1,
                32,
                3.5f/5f
//                1f/5f
        );

        //the vector2 is just for gameOver, for calculating
        //position of score, use this vector only
        currentPosition=3.5f/5;
        FINAL_POS =4f/5;
        INCREASE_IN_CURRENT_POS=0.01f;

        score=new GameOverScreen.Message(
                "1",
                1,
                38,
//                1f/5
                3f/5f
        );

        highScore=new GameOverScreen.Message(
                "H I G H  S C O R E",
                1,
                30,
                2.85f/5f
        );

//        if(preferences.getInteger("highScore")==null){
//
//        }

        highScoreScoreInt=preferences.getInteger("highScore");
        if(highScoreScoreInt<scoreMax){
            highScoreScoreInt=scoreMax;
            preferences.putInteger("highScore", highScoreScoreInt);
        }
        preferences.flush();
        highScoreScore=new GameOverScreen.Message(
                intToString(highScoreScoreInt),
                1,
                26,
                2.35f/5
        );

        coverRect=new Rectangle(
                0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
        );

        FreeTypeFontGenerator.FreeTypeFontParameter parameter=new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size=150;
        parameter.size=350;
        mainFont=generator.generateFont(
                parameter
        );
//        parameter.size=140;
//        mainFont2=generator.generateFont(
//                parameter
//        );

        SCORE_INCREASE=(int)(scoreMax/(float)TIME_FOR_SCORE_INCREASE)+1;
        MAX_TIMER_ONCE_AGAIN_FOR_FUCKS_SAKE=TIME_FOR_SCORE_INCREASE/5;
        OPACITY_DECREASE_AGAIN_FOR_FUCKS_SAKE=(int)(SCORE_INCREASE*255f/scoreMax);
//        SCORE_INCREASE=0;

//        mainFont.getData().setScale(1.5f);

    }

//    public static int stringToInt(GameOverScreen.Message score, int scoreMax, int SCORE_INCREASE){
//        int finalInt=-1;
//        if(Integer.parseInt(score.message)<=scoreMax) {
//            int before = Integer.parseInt(score.getMessage());
//            finalInt = (Integer.parseInt(score.getMessage()) + SCORE_INCREASE);
//            if (("" + before + 1).length() > ("" + before).length()) {
//                score.recalculatePosition();
//            }
//        }
//
//        return finalInt;
//
//    }

    public static String intToString(int score){
        String initString=""+score;
        String finalString="";
        int i=0;
        for(int j=0;j<initString.length();){
            if(i%2==1){
                finalString=finalString+" ";
                i-=1;
            }
            if(initString.charAt(j)!='1') {
                finalString = finalString + initString.charAt(j) + "";
            }else{
                finalString = finalString + 'I';
            }
            j++;
            i++;

        }
        return finalString;
    }

    public static int stringToInt(String a){
        String finalString="";
        for(int i=0;i<a.length();i++){
            if(a.charAt(i)!='I') {
                if (a.charAt(i) != ' ') {
                    finalString = finalString + a.charAt(i);
                }
            }else{
                finalString = finalString + '1';
            }
        }
        return Integer.parseInt(finalString);
    }

    @Override
    public void show() {

    }

    public void update(){
        if(isAllAnimationsEnded()){
            currentOpacity=0;
            score.message=intToString(scoreMax);
        }
//        if(score!=null) {
//            if(Integer.parseInt(score.message)<=scoreMax) {
//                int before = Integer.parseInt(score.getMessage());
//                score.message = "" + (Integer.parseInt(score.getMessage()) + SCORE_INCREASE);
//                if (("" + before + 1).length() > ("" + before).length()) {
//                    score.recalculatePosition();
//                }
//            }
//        }
//        intToString(43223);
//        score.message=intToString(stringToInt(score, scoreMax, SCORE_INCREASE));

        if(stringToInt(score.getMessage())<scoreMax) {
            if(stringToInt(score.getMessage())+SCORE_INCREASE>scoreMax){
                score.message=intToString(scoreMax);
            }else {
                score.message = intToString(stringToInt(score.message) + SCORE_INCREASE);
            }
        }

        opacityUpdate();
        positionUpdate();

    }

    public void positionUpdate(){
        if(currentPosition<FINAL_POS) {
            currentPosition += INCREASE_IN_CURRENT_POS;
            gameOver.setVerticalPos(currentPosition);
            score.setVerticalPos(currentPosition - 0.5f/5);
//            gameOver.recalculatePosition();
//            score.recalculatePosition();
        }
    }

    public void opacityUpdate(){
        if(currentFramesElapsed>MAX_TIMER_ONCE_AGAIN_FOR_FUCKS_SAKE){
            if(currentOpacity<=0){
                allAnimationsEnded=true;
            }else {
                currentOpacity -= OPACITY_DECREASE_AGAIN_FOR_FUCKS_SAKE;
            }
        }else{
            currentFramesElapsed++;
        }
    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        batch.begin();
        mainFont.draw(batch, highScore.getMessage(), highScore.getPosition().x, highScore.getPosition().y);
        mainFont.draw(batch, highScoreScore.getMessage(), highScoreScore.getPosition().x, highScoreScore.getPosition().y);

        batch.end();


        //For opacity things to work
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //draw the covering rectangle
        renderer.begin();
        renderer.setColor(0, 0, 0, currentOpacity/255f);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(
                coverRect.x,
                coverRect.y,
                coverRect.width,
                coverRect.height
        );
        renderer.end();

//        System.out.println(currentOpacity);

        batch.begin();

        mainFont.draw(batch, gameOver.getMessage(), gameOver.getPosition().x, gameOver.getPosition().y);
        mainFont.draw(batch, score.getMessage(), score.getPosition().x, score.getPosition().y);
//        mainFont2.draw(batch, retry.getMessage(), retry.getPosition().x, retry.getPosition().y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
//        renderer.dispose();
        generator.dispose();
        mainFont.dispose();
        game.dispose();

        //For opacity things to stop working
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public String toString() {
        return "GameOverScreen";
    }
}
