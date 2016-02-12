package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 11/21/15.
 */
public class CartSingleton {

    private CheckoutCart checkoutcart;

    private static CartSingleton instance;

    private CartSingleton(){
        checkoutcart = new CheckoutCart();
    }

    public static CartSingleton getInstance(){
        if (instance == null){
            instance = new CartSingleton();
        }
        return instance;
    }

    public CheckoutCart getCheckoutCart(){
        return checkoutcart;
    }
}
