package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 1/22/16.
 */
import java.util.ArrayList;

/**
 * Created by tujasiri on 11/21/15.
 */
public class CountryItemSingleton {

    private ArrayList<CountryItem> arrayList;

    private static CountryItemSingleton instance;

    private CountryItemSingleton(){
        arrayList = new ArrayList<CountryItem>();
    }

    public static CountryItemSingleton getInstance(){
        if (instance == null){
            instance = new CountryItemSingleton();
        }
        return instance;
    }

    public ArrayList<CountryItem> getArrayList() {
        return arrayList;
    }
}

