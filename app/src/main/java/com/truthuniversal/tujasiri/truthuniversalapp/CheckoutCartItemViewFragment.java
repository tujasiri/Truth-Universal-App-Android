package com.truthuniversal.tujasiri.truthuniversalapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
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
public class CheckoutCartItemViewFragment extends Fragment {

    CheckoutCart checkoutCart = new CheckoutCart();
    private List<MerchItem> itemsInCart = new ArrayList<MerchItem>();
    View checkoutCartItemView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       checkoutCartItemView = inflater.inflate(R.layout.checkout_cart_view,container,false);

        View totalLayout = checkoutCartItemView.findViewById(R.id.checkoutlayout);
        final Button checkoutButton = (Button) checkoutCartItemView.findViewById(R.id.checkout_button);
        final Button checkoutShopButton = (Button) checkoutCartItemView.findViewById(R.id.continueShoppingCheckOut);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("CheckOUT Button Clicked!");
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new StripeFragment())
                        .commit();

            }
        });


        checkoutShopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new MerchFragment())
                        .commit();

            }
        });

        checkoutCart = CartSingleton.getInstance().getCheckoutCart();

        itemsInCart = checkoutCart.itemsInCart();

        populateListView();

        return checkoutCartItemView;
    }


    public void populateListView(){
        ArrayAdapter<MerchItem> merchItemsArrayAdapter = new CheckoutCartListAdapter();
        ListView list = (ListView)checkoutCartItemView.findViewById(R.id.checkout_listview);
        list.setAdapter(merchItemsArrayAdapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                System.out.println(String.format("position xxx ==> %d", position));


                showDeleteDialog(position);

            }
        });
    }


    private class CheckoutCartListAdapter extends ArrayAdapter<MerchItem>
    {
        public CheckoutCartListAdapter() {
            //super(CheckoutCartItemViewFragment.this, R.layout.checkout_cart_summary_view, itemsInCart);
            super(getActivity(), R.layout.checkout_cart_summary_view, itemsInCart);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View merchItemsView = convertView;

            ViewHolder viewHolder;

            View merchItemsViewParent;


            if(merchItemsView == null){
                merchItemsView = getActivity().getLayoutInflater().inflate(R.layout.checkout_cart_summary_view, parent,false);
                merchItemsViewParent = getActivity().getLayoutInflater().inflate(R.layout.checkout_cart_view, parent,false);

                viewHolder = new ViewHolder();

                viewHolder.merchItemDescView = (TextView) merchItemsView.findViewById(R.id.merchItemDescView);
                viewHolder.merchItemCostView = (TextView) merchItemsView.findViewById(R.id.merchItemCostView);
                viewHolder.merchItemTotal = (TextView) checkoutCartItemView.findViewById(R.id.total_textview);

                merchItemsView.setTag(viewHolder);

            }
            else{
                viewHolder = (ViewHolder)merchItemsView.getTag();

            }

            MerchItem currentMerchItem = itemsInCart.get(position);

            String imageUrl = String.format("https://www.2checkout.com/va/public/render_image?image_id=%s", currentMerchItem.getMt_image_id());

            viewHolder.merchItemDescView.setText(currentMerchItem.getMt_item_desc_short());
            viewHolder.merchItemCostView.setText(String.format("$%.2f", currentMerchItem.getMt_item_price()));
            viewHolder.merchItemTotal.setText(String.format("TOTAL:\t\t$%.2f", checkoutCart.calculateTotal()));

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
        //android.support.v4.app.FragmentManager manager= getActivity().getFragmentSupportManager();
        FragmentManager manager= getActivity().getFragmentManager();
        DeleteItemFromCardDialog deleteItemDialog = new DeleteItemFromCardDialog();

        Bundle args = new Bundle();
        args.putInt("position",listItemPostion);
        deleteItemDialog.setArguments(args);

        deleteItemDialog.show(manager, "deleteItemDialog");

    }
}
