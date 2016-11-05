package com.truthuniversal.tujasiri.truthuniversalapp;

import java.util.ArrayList;

/**
 * Created by tujasiri on 11/18/15.
 */
public class CheckoutCart {

    public CheckoutCart(){
        int test =0;

    }

    //ArrayList<MerchItem> itemsArray;
    private ArrayList<MerchItem> itemsArray = new ArrayList<MerchItem>();

    public ArrayList<MerchItem> itemsInCart(){
        return this.itemsArray;
    }

    public Boolean containsMerchItem(MerchItem merchItem){

        return false;
    }

    public void groupItemsByType(){

        //group items by type and compact itemsArray

    }

    public int countIndividualItem(MerchItem merchItem){
        int itemCount = 0;

        return itemCount;
    }

    public int removeIndividualItem(MerchItem merchItem){
        int itemCount = 0;

        return itemCount;
    }

    public void addItemToCart(MerchItem merchItem){
        itemsArray.add(merchItem);
        /*
        for(MerchItem tmpMerchItem :itemsArray){
            System.out.println(String.format("In cart ==>%s", tmpMerchItem.getMt_item_desc_long()));

        }
        */
    }

    public void removeItemFromCart(int positon){
        itemsInCart().remove(positon);
    }

    public void emptyCart(){

    }

    public double calculateTotal(){

        double itemTotal = 0.0;

        if(itemsInCart().isEmpty()){
            return 0.0;
        }

        for(int i=0;i<itemsInCart().size();i++){
            itemTotal += itemsInCart().get(i).getMt_item_price();
        }
        return itemTotal;

    }

    public float calculateShipping(){

        float itemTotal = 0;

        return itemTotal;

    }

}
