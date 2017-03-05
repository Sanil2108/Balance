package com.sanilk.balance.RandomUselessStuff;

import com.badlogic.gdx.math.Vector2;
import com.sanilk.balance.Screens.GameOverScreen;

import java.util.ArrayList;

public class GameOverScreenMessages {

    private class Message{
        String message;
        Vector2 percentage;
        public Message(String message, Vector2 percentage){
            //If percentage.y==-1, it signifies infinity
            this.message=message;
            this.percentage=percentage;
        }

        public Vector2 getPercentage() {
            return percentage;
        }

        public String getMessage() {
            return message;
        }
    }

    private ArrayList<Message> allMessages;

    private static GameOverScreenMessages ourInstance=new GameOverScreenMessages();

    private GameOverScreenMessages(){
        this.allMessages=new ArrayList<Message>();
        allMessages.add(new Message("You need to practice more", new Vector2(0, 80)));
        allMessages.add(new Message("You gotta try harder", new Vector2(0, 80)));
        allMessages.add(new Message("So Close ...", new Vector2(80, 100)));
        allMessages.add(new Message("Almost ...", new Vector2(80, 100)));
        allMessages.add(new Message("YOU DID IT !! Now, you can put it on your resume", new Vector2(100, -1)));
        allMessages.add(new Message("AWESOME !!", new Vector2(100, -1)));
    }

    public GameOverScreenMessages getinstance(){
        return ourInstance;
    }

    public ArrayList<Message> getAllMessages() {
        return allMessages;
    }
}
