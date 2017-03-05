package com.sanilk.balance.Animations.StarAnimation;

import java.util.ArrayList;

/**
 * Created by Admin on 27-01-2017.
 */
public class AllStars {
    private static AllStars allStars=new AllStars();
    public ArrayList<Star> allStarsList;

    private AllStars(){
        allStarsList=new ArrayList<Star>();
    }
    public static AllStars getInstance(){
        return allStars;
    }
}
