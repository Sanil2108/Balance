package com.sanilk.balance.Sprites.Enemy;

import java.util.ArrayList;

/**
 * Created by Admin on 01-02-2017.
 */
public class AllEnemies {
    private static AllEnemies ourInstance=new AllEnemies();
    private ArrayList<Enemy> allEnemies;

    public static AllEnemies getInstance(){
        return ourInstance;
    }

    public ArrayList<Enemy> getAllEnemies() {
        return allEnemies;
    }

    private AllEnemies(){
        allEnemies=new ArrayList<Enemy>();
    }
}
