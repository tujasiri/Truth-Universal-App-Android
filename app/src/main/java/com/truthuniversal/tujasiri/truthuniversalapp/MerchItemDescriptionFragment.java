package com.truthuniversal.tujasiri.truthuniversalapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NewApi")
public class MerchItemDescriptionFragment extends Fragment {

    private List<MerchItem> MerchItemDetailList = new ArrayList<>();
    private MerchItem merchItem = new MerchItem();
    private CheckoutCart merchDetail_CART = new CheckoutCart();

    View merchDescView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        merchDescView = inflater.inflate(R.layout.merch_item_description, container,false);
        MerchItemDetailList = ItemSingleton.getInstance().getArrayList();
        merchDetail_CART = CartSingleton.getInstance().getCheckoutCart();

        final Button shopButton = (Button) merchDescView.findViewById(R.id.continueShopping);
        final Button viewCartButton = (Button) merchDescView.findViewById(R.id.viewCart);
        final Button addButton = (Button) merchDescView.findViewById(R.id.addToCartButton);

        shopButton.setEnabled(true);
        viewCartButton.setEnabled(true);
        addButton.setEnabled(true);

        addButton.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // Perform action on click

                System.out.println("Button Clicked!");
                System.out.println(String.format("MerchItemList Item ==> %s", merchItem.getMt_item_desc_short()));
                //System.out.println(String.format(MerchItemDetailList.get(posi)));


                //Check to see if Item is in cart
                //If not in cart add item if in cart, allow user to add another item to quantity

                //CheckoutCart localCheckoutCart = MerchActivity.checkoutCart;
                merchDetail_CART.addItemToCart(merchItem);
                addButton.setEnabled(false);


            }
        });

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new MerchFragment())
                        .commit();

            }
        });

        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new CheckoutCartItemViewFragment())
                        .commit();

            }
        });


        Bundle bundle = this.getArguments();

        //if(bundle != null){
        /*
            String message = bundle.getString(MerchActivity.EXTRA_MESSAGE);
            String itemCost = bundle.getString(MerchActivity.ITEM_COST);
            int itemListPositon = bundle.getInt(MerchActivity.ITEM_LIST_POSITION, -1);
        */

        String message = bundle.getString(MerchFragment.EXTRA_MESSAGE);


        String itemCost = bundle.getString(MerchFragment.ITEM_COST);
        int itemListPositon = bundle.getInt(MerchFragment.ITEM_LIST_POSITION, -1);


        //}

        //Sys


        merchItem = MerchItemDetailList.get(itemListPositon);


        //View merchDescView = getLayoutInflater().inflate(R.layout.merch_item_description, null, false);

/*
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
*/
        Bitmap bmp = (Bitmap) bundle.getParcelable("imagebitmap");
        //View myView = (View) extras.getParcelable("theView");

        TextView txtView2 = (TextView)merchDescView.findViewById(R.id.textViewItemName);

        txtView2.setText(message);
        System.out.println("setText==>"+message);

        TextView costView = (TextView)merchDescView.findViewById(R.id.textViewLongDesc);

        costView.setText(itemCost);

        ImageView iv = (ImageView)merchDescView.findViewById(R.id.itemDescIV);

        //ImageView iv = (ImageView) myView.findViewById(R.id.itemDescIV);

        iv.setImageBitmap(bmp);


        return merchDescView;
    }//end onCreate

}
