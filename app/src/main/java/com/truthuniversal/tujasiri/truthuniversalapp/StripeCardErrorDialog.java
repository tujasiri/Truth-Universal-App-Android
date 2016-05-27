package com.truthuniversal.tujasiri.truthuniversalapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by tujasiri on 5/24/16.
 */
public class StripeCardErrorDialog extends DialogFragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Button okButton;
        View view = inflater.inflate(R.layout.stripe_card_charge_error, null);

        okButton = (Button)view.findViewById(R.id.ok_button);
        okButton.setOnClickListener(this);

        String stripeErrorMsg = getArguments().getString("stripeErr");

        TextView errMsgText = (TextView) view.findViewById(R.id.dialog_error_text);
        errMsgText.setText(stripeErrorMsg);

        return view;
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.ok_button){

            //AppUtilities.recreateActivityCompat(this.getActivity());

            dismiss();

        }

    }


}
