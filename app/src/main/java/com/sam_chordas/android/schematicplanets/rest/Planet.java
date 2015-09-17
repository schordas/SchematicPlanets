package com.sam_chordas.android.schematicplanets.rest;

import android.database.Cursor;

/**
 * Created by sam_chordas on 8/11/15.
 */
public class Planet {
    public String name;
    public double distFromSun;
    public int imageResource;
    public Planet(){}
    public Planet(String name, double distFromSun, int imageResource){
        this.name = name;
        this.distFromSun = distFromSun;
        this.imageResource = imageResource;
    }

    public String getName(){
        return name;
    }

    public int getImageResource(){
        return imageResource;
    }

    public double getDistFromSun(){
        return distFromSun;
    }

    public static Planet fromCursor(Cursor cursor){
        Planet planet = new Planet();
        return planet;
    }
}
