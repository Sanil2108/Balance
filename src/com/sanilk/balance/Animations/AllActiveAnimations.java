package com.sanilk.balance.Animations;

import java.util.ArrayList;

/**
 * Created by Admin on 21-01-2017.
 */

//As of now, this class is completely inactive along with abstract class
//AnimationsMainClass. the idea was to create this class to hold an arraylist
//of all active animations and add them

//An animation handler class will then appropriately dispose them and handle them
//It seemed useless when it was being programmed but if the need should arise
//it should be completed
public class AllActiveAnimations {
    private static AllActiveAnimations ourInstance = new AllActiveAnimations();

    private ArrayList<AnimationsMainClass> allActiveAnimations;

    public static AllActiveAnimations getInstance() {
        return ourInstance;
    }

    private AllActiveAnimations() {
        allActiveAnimations=new ArrayList<AnimationsMainClass>();
    }

    public void addAnimation(AnimationsMainClass animationsMainClass){
        this.allActiveAnimations.add(animationsMainClass);
    }

    public ArrayList<AnimationsMainClass> getAllActiveAnimations() {
        return allActiveAnimations;
    }
}
