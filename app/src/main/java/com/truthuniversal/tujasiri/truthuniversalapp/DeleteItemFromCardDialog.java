package com.truthuniversal.tujasiri.truthuniversalapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by tujasiri on 12/7/15.
 */
public class DeleteItemFromCardDialog extends DialogFragment implements View.OnClickListener {


    CheckoutCart checkoutCart = CartSingleton.getInstance().getCheckoutCart();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button yes, no;
        View view = inflater.inflate(R.layout.delete_item_from_cart_dialog, null);

        yes = (Button)view.findViewById(R.id.yesButton);
        no = (Button)view.findViewById(R.id.noButton);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        int listitemPosition = getArguments().getInt("position");

        return view;
    }

    @Override
    public void onClick(View v) {

        int listitemPosition = getArguments().getInt("position");

        if(v.getId()==R.id.yesButton){

            //Toast.makeText(getActivity(), "YES Button Clicked.", Toast.LENGTH_SHORT).show();

            checkoutCart.itemsInCart().remove(listitemPosition);

            AppUtilities.recreateActivityCompat(this.getActivity());

            dismiss();

        }

        if(v.getId()==R.id.noButton){

            //Toast.makeText(getActivity(), "NO Button Clicked.", Toast.LENGTH_SHORT).show();
            dismiss();

        }
    }


}
