package com.truthuniversal.tujasiri.truthuniversalapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tujasiri on 11/25/15.
 */
public class CheckoutCartItemViewActivity extends AppCompatActivity{

    CheckoutCart checkoutCart = new CheckoutCart();
    private List<MerchItem> itemsInCart = new ArrayList<MerchItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.checkout_cart_view);
        View totalLayout = findViewById(R.id.checkoutlayout);
        final Button checkoutButton = (android.widget.Button) findViewById(R.id.checkout_button);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("CheckOUT Button Clicked!");
                Intent intent = new Intent(getApplicationContext(),StripeActivity.class);
                startActivity(intent);


            }
        });


        checkoutCart = CartSingleton.getInstance().getCheckoutCart();

        itemsInCart = checkoutCart.itemsInCart();

        populateListView();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_test,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();

        if(id == R.id.action_settings){
            return true;

        }

        switch(id){
            /*
            case R.id.shopping_cart:
            {
                Toast.makeText(getBaseContext(), "Clicked Icon!", Toast.LENGTH_SHORT).show();
                System.out.println("CLICKED ICON!");
                Intent intent = new Intent(getApplicationContext(),CheckoutCartItemViewActivity.class);

                startActivity(intent);

            }
            break;
            */
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
            /*
            case R.id.shop_icon:
            {
                //Toast.makeText(getBaseContext(), "Clicked SHP{ Icon!", Toast.LENGTH_SHORT).show();
                //System.out.println("CLICKED ICON!");
                Intent intent = new Intent(getApplicationContext(),MerchActivity.class);

                startActivity(intent);

            }
            break;
            */
        }//end switch(id)
        return true;

    }
/*
    private class MyListAdapter extends ArrayAdapter<MerchItem>
    {
        public MyListAdapter() {
            super(CheckoutCartItemViewActivity.this, R.layout.merch_item_list_view, itemsInCart);

        }
*/


    public void populateListView(){
        ArrayAdapter<MerchItem> merchItemsArrayAdapter = new CheckoutCartListAdapter();
        ListView list = (ListView)findViewById(R.id.checkout_listview);
        list.setAdapter(merchItemsArrayAdapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println(String.format("position xxx ==> %d", position));


                showDeleteDialog(position);

                //fmd.show(getFragmentManager(),"dialog");
                //fmd.show



/*
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog

                        dialog.dismiss();
                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                */

            }
        });



    }


    private class CheckoutCartListAdapter extends ArrayAdapter<MerchItem>
    {
        public CheckoutCartListAdapter() {
            super(CheckoutCartItemViewActivity.this, R.layout.checkout_cart_summary_view, itemsInCart);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View merchItemsView = convertView;

            ViewHolder viewHolder;

            View merchItemsViewParent;

            //View totalLayout = findViewById(R.id.checkoutlayout);



            //View merchItemsViewParent = (View)merchItemsView.getParent();


            if(merchItemsView == null){

                //merchItemsView = getLayoutInflater().inflate(R.layout.checkout_cart_summary_view, parent,false);
                merchItemsView = getLayoutInflater().inflate(R.layout.checkout_cart_summary_view, parent,false);
                merchItemsViewParent = getLayoutInflater().inflate(R.layout.checkout_cart_view, parent,false);

                //View merchItemsViewParent = (View)merchItemsView.getParent();

                viewHolder = new ViewHolder();


                viewHolder.merchItemDescView = (TextView) merchItemsView.findViewById(R.id.merchItemDescView);
                viewHolder.merchItemCostView = (TextView) merchItemsView.findViewById(R.id.merchItemCostView);
                viewHolder.merchItemTotal = (TextView) findViewById(R.id.total_textview);

                //System.out.println(String.format("parent info ==>%s", merchItemsView.getParent().toString()));


                merchItemsView.setTag(viewHolder);

            }
            else{
                viewHolder = (ViewHolder)merchItemsView.getTag();

            }

            //Find Merch Item

            //System.out.println(String.format("view==>%s, position ==> %s, id==> %d",viewHolder.textView2.getRootView().toString(),position,viewHolder.textView2.getId()));


            MerchItem currentMerchItem = itemsInCart.get(position);

            System.out.println(String.format("HERE in itemsInCart ==> %s", currentMerchItem.getMt_item_desc_short()));



            String imageUrl = String.format("https://www.2checkout.com/va/public/render_image?image_id=%s", currentMerchItem.getMt_image_id());

            viewHolder.merchItemDescView.setText(currentMerchItem.getMt_item_desc_short());
            viewHolder.merchItemCostView.setText(String.format("$%.2f", currentMerchItem.getMt_item_price()));
            viewHolder.merchItemTotal.setText(String.format("TOTAL:\t\t$%.2f", checkoutCart.calculateTotal()));
/*
            if(((itemsInCart.size() -1) == position)&& (position != 0)) {
                //viewHolder.merchItemTotal.setText(String.format("$%.2f", checkoutCart.calculateTotal()));
                System.out.println(String.format("cart size ==> %d, position ==>%d", itemsInCart.size(), position));
                viewHolder.merchItemDescView.setText(String.format("TOTAL:\t\t"));
                viewHolder.merchItemCostView.setText(String.format("$%.2f", checkoutCart.calculateTotal()));
                //merchItemsView.
                //TextView tvTotal = new TextView(getApplicationContext());
                //tvTotal.setText(String.format("$%.2f", checkoutCart.calculateTotal()));
                //tvTotal.setId(5);
                //tvTotal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));



                //((LinearLayout) totalLayout).addView(tvTotal);
            }
            else{
                //viewHolder.merchItemDescView.setText(currentMerchItem.getMt_item_desc_short());
                //viewHolder.merchItemCostView.setText(String.format("$%.2f", currentMerchItem.getMt_item_price()));

            }

*/


            /*
            TextView merchItemDescLong = (TextView) merchItemView.findViewById(R.id.textView2);
            merchItemDescLong.setText(currentMerchItem.getMt_item_desc_long());

            TextView merchItemDescShort = (TextView) merchItemView.findViewById(R.id.textView3);
            merchItemDescShort.setText(currentMerchItem.getMt_item_desc_short());



            ImageView merchImageViewFinal = (ImageView) merchItemView.findViewById(R.id.imageView);

            Bitmap bitmaptmp = currentMerchItem.getMt_bitmap();

            merchImageViewFinal.setImageBitmap(currentMerchItem.getMt_bitmap());
            */
            //Bitmap tmpImgView = currentMerchItem.getMt_imageview();
            //merchImageViewFinal.setImageBitmap(currentMerchItem.getMt_imageview());
            //dt.cancel(true);
            //System.out.println(String.format("download task status ==>%s",dt.getStatus().toString()));

            //Fill the View


            //return super.getView(position, convertView, parent);
            return merchItemsView;

        }
    }//end MyListAdapter

    static class ViewHolder{
        public TextView merchItemDescView;
        public TextView merchItemCostView;
        //public TableRow merchItemTotal;
        public TextView merchItemTotal;

    }

    void showDeleteDialog(int listItemPostion){
        System.out.println(String.format("position in showDeletDialog ==>%d",1));
        //android.support.v4.app.FragmentManager manager= getSupportFragmentManager();
        FragmentManager manager= getFragmentManager();
        DeleteItemFromCardDialog deleteItemDialog = new DeleteItemFromCardDialog();

        Bundle args = new Bundle();
        args.putInt("position",listItemPostion);
        deleteItemDialog.setArguments(args);

        deleteItemDialog.show(manager, "deleteItemDialog");

    }

    /*
    public static class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("TEST")
                    .setPositiveButton("POSITIVE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("NEGATIVE", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
    */
}
