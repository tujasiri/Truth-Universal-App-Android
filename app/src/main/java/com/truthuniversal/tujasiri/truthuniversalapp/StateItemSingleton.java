package com.truthuniversal.tujasiri.truthuniversalapp;

import java.util.ArrayList;

/**
 * Created by tujasiri on 2/1/16.
 */
public class StateItemSingleton {

    private ArrayList<StateItem> arrayList;

    private static StateItemSingleton instance;

    private StateItemSingleton(){
        arrayList = new ArrayList<StateItem>();
    }

    public static StateItemSingleton getInstance(){
        if (instance == null){
            instance = new StateItemSingleton();
        }
        return instance;
    }

    public ArrayList<StateItem> getArrayList() {
        return arrayList;
    }
}
