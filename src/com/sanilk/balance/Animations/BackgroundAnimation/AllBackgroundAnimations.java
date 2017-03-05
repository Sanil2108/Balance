package com.sanilk.balance.Animations.BackgroundAnimation;

import java.util.ArrayList;

/**
 * Created by Admin on 27-01-2017.
 */
public class AllBackgroundAnimations {
    public ArrayList<BackgroundAnimation> allBackgroundAnimations;

    private static AllBackgroundAnimations ourInstance = new AllBackgroundAnimations();

    public static AllBackgroundAnimations getInstance() {
        return ourInstance;
    }

    private AllBackgroundAnimations() {
        allBackgroundAnimations=new ArrayList<BackgroundAnimation>();
    }
}
