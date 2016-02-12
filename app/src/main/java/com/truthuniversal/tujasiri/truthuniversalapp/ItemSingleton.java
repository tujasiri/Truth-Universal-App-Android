package com.truthuniversal.tujasiri.truthuniversalapp;

import java.util.ArrayList;

/**
 * Created by tujasiri on 11/21/15.
 */
public class ItemSingleton {

    private ArrayList<MerchItem> arrayList;

    private static ItemSingleton instance;

    private ItemSingleton(){
        arrayList = new ArrayList<MerchItem>();
    }

    public static ItemSingleton getInstance(){
        if (instance == null){
            instance = new ItemSingleton();
        }
        return instance;
    }

    public ArrayList<MerchItem> getArrayList() {
        return arrayList;
    }
}
