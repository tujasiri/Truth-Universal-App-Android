package com.truthuniversal.tujasiri.truthuniversalapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MerchItemDescriptionFragment extends AppCompatActivity {

    private List<MerchItem> MerchItemDetailList = new ArrayList<>();
    private MerchItem merchItem = new MerchItem();
    private CheckoutCart merchDetail_CART = new CheckoutCart();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //CheckoutCart checkoutCart =

        MerchItemDetailList = ItemSingleton.getInstance().getArrayList();
        merchDetail_CART = CartSingleton.getInstance().getCheckoutCart();


        setContentView(R.layout.merch_item_description);

        final Button addButton = (Button) findViewById(R.id.addToCartButton);
        addButton.setEnabled(true);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click

                System.out.println("Button Clicked!");
                System.out.println(String.format("MerchItemList Item ==> %s",merchItem.getMt_item_desc_short()));
                //System.out.println(String.format(MerchItemDetailList.get(posi)));



                //Check to see if Item is in cart
                //If not in cart add item if in cart, allow user to add another item to quantity

                //CheckoutCart localCheckoutCart = MerchActivity.checkoutCart;
                merchDetail_CART.addItemToCart(merchItem);
                addButton.setEnabled(false);



            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra(MerchActivity.EXTRA_MESSAGE);
        String itemCost = intent.getStringExtra(MerchActivity.ITEM_COST);
        int itemListPositon = intent.getIntExtra(MerchActivity.ITEM_LIST_POSITION, -1);

        //Sys


        merchItem = MerchItemDetailList.get(itemListPositon);


        //View merchDescView = getLayoutInflater().inflate(R.layout.merch_item_description, null, false);


        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        //View myView = (View) extras.getParcelable("theView");

        TextView txtView2 = (TextView)findViewById(R.id.textViewItemName);

        txtView2.setText(message);

        TextView costView = (TextView)findViewById(R.id.textViewLongDesc);

        costView.setText(itemCost);

        ImageView iv = (ImageView)findViewById(R.id.itemDescIV);

        //ImageView iv = (ImageView) myView.findViewById(R.id.itemDescIV);


        iv.setImageBitmap(bmp);

    }//end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_test,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();

        System.out.println(String.format("id ==>%d, action_settings ==> %d, R.it.cut_icon==>%d",id, R.id.action_settings,R.id.shopping_cart));

        if(id == R.id.action_settings){
            return true;

        }

        switch(id){

            case R.id.shopping_cart:
            {
                Toast.makeText(getBaseContext(), "Clicked Icon!", Toast.LENGTH_SHORT).show();
                System.out.println("CLICKED ICON!");
                Intent intent = new Intent(getApplicationContext(),CheckoutCartItemViewActivity.class);

                startActivity(intent);

            }
            break;
            case R.id.truth_universal_logo:
            {
                Toast.makeText(getBaseContext(), "Clicked LOG Icon!", Toast.LENGTH_SHORT).show();
                System.out.println("CLICKED ICON!");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                startActivity(intent);

            }
            break;
            case R.id.home_icon:
            {
                Toast.makeText(getBaseContext(), "Clicked HOME Icon!", Toast.LENGTH_SHORT).show();
                System.out.println("CLICKED ICON!");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);

                startActivity(intent);

            }
            break;
            case R.id.shop_icon:
            {
                //Toast.makeText(getBaseContext(), "Clicked SHP{ Icon!", Toast.LENGTH_SHORT).show();
                //System.out.println("CLICKED ICON!");
                Intent intent = new Intent(getApplicationContext(),MerchActivity.class);

                startActivity(intent);

            }
            break;
        }//end switch(id)

        return true;

    }
}
