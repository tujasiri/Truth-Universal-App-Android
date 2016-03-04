package com.truthuniversal.tujasiri.truthuniversalapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleExpandableListAdapter;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by tujasiri on 1/4/16.
 */
public class StripeActivity extends AppCompatActivity {


    //public static final String STRIPE_URL = "http://truthuniversal.com/ios/stripe-payment.php";
    public static final String STRIPE_URL = "http://truthuniversal.com/android/stripe-payment-test.php";
    public static final String STRIPE_EMAIL_URL = "http://truthuniversal.com/android/stripe-email.php";

    public static final String COUNTRY_DATA_URL = "http://truthuniversal.com/android/countries";


    public static final Map<String, Object> chargeMap = new HashMap<String, Object>();
    public static final Map<String, Object> cardMap = new HashMap<String, Object>();

    public static final List<Map<String,String>> groupMapList = new ArrayList<Map<String,String>>();

    public static final List<Map<String,String>> groupStateMapList = new ArrayList<Map<String,String>>();

    public static final List<Map<String,String>> groupExpMonthMapList = new ArrayList<Map<String,String>>();

    public static final List<Map<String,String>> groupExpYearMapList = new ArrayList<Map<String,String>>();


    public static final List<List<Map<String,String>>> childMap = new ArrayList<List<Map<String,String>>>();

    public static final List<List<Map<String,String>>> childStateMap = new ArrayList<List<Map<String,String>>>();

    //public static final List<List<Map<String,Integer>>> childExpMonthMap = new ArrayList<List<Map<String,Integer>>>();

    //public static final List<List<Map<String,Integer>>> childExpYearhMap = new ArrayList<List<Map<String,Integer>>>();

    public static final List<List<Map<String,String>>> childExpMonthMap = new ArrayList<List<Map<String,String>>>();

    public static final List<List<Map<String,String>>> childExpYearhMap = new ArrayList<List<Map<String,String>>>();




    public static final Map<String, String> childItemMap1 = new HashMap<String, String>();
    public static final Map<String, String> childItemMap2 = new HashMap<String, String>();
    public static final Map<String, String> childItemMap3 = new HashMap<String, String>();

    public static final Map<String, String> childItemMap4 = new HashMap<String, String>();


    public static final Map<String, String> groupItemMap = new HashMap<String, String>();
    public static final Map<String, String> groupStateItemMap = new HashMap<String, String>();
    public static final Map<String, String> groupExpMonthItemMap = new HashMap<String, String>();
    public static final Map<String, String> groupExpYearItemMap = new HashMap<String, String>();




    //public static final List<Map<String,String>> childMapList = new ArrayList<Map<String,String>>();

    public static List<Map<String,String>> childMapList = new ArrayList<Map<String,String>>();
    public static List<Map<String,String>> childStateMapList = new ArrayList<Map<String,String>>();

    //public static List<Map<String,Integer>> childExpMonthMapList = new ArrayList<Map<String,Integer>>();
    public static List<Map<String,String>> childExpMonthMapList = new ArrayList<Map<String,String>>();

    //public static List<Map<String,Integer>> childExpYearMapList = new ArrayList<Map<String,Integer>>();
    public static List<Map<String,String>> childExpYearMapList = new ArrayList<Map<String,String>>();






    // public static final Map<String,String> groupMap = new HashMap<String,String>();


    private List<CountryItem> countryItemList = new ArrayList<CountryItem>();
    private List<StateItem> stateItemList = new ArrayList<StateItem>();


    CheckoutCart checkoutCart = new CheckoutCart();


    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ExpandableListView expStateListView;
    ExpandableListView expMonthListView;
    ExpandableListView expYearListView;




    List<String> listDataHeader = new ArrayList<String>();
    List<String> listStateDataHeader = new ArrayList<String>();


    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<String>> listStateDataChild;



    CountryItem ci = new CountryItem();




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stripe_checkout);

        //test json
        String jsonMonthResource="";
        try {
            jsonMonthResource = getJSONResource("expiration_month");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String jsonYearResource="";
        try {
            jsonYearResource = getJSONResource("expiration_year");
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Button stripeButton = (android.widget.Button) findViewById(R.id.checkout_pay_button);

        countryItemList = CountryItemSingleton.getInstance().getArrayList();
        stateItemList =  StateItemSingleton.getInstance().getArrayList();

        ci.country_name="xxxx";
        ci.country_abbreviation="yy";

        countryItemList.add(ci);

        //new AsyncCountryTask().execute(COUNTRY_DATA_URL);

        // get the country listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // get the state listview
        expStateListView = (ExpandableListView) findViewById(R.id.stateExpandableListView);

        // get the state listview
        expMonthListView = (ExpandableListView) findViewById(R.id.expandableListViewMonth);

        // get the state listview
        expYearListView = (ExpandableListView) findViewById(R.id.expandableListViewYear);

        // preparing list data
        //prepareListData();
        listDataHeader.add("Countries");
        listStateDataHeader.add("States");


        childItemMap1.put("Sub", "TEST ITEM 1");
        childItemMap2.put("Sub", "TEST ITEM 2");
        childItemMap3.put("Sub", "TEST ITEM 2");

        childItemMap4.put("Sub", "TEST ITEM 2");

        childMapList = populateCountryItemList();
        childStateMapList = populateStateItemList();

        try {
            //childExpMonthMapList = populateExpirationList(jsonMonthResource,"month");
            childExpMonthMapList = populateExpirationList(jsonMonthResource,"month");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            childExpYearMapList = populateExpirationList(jsonYearResource,"year");
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("JSON EXCEPTION");
        }

        /*
        childMapList.add(childItemMap1);
        childMapList.add(childItemMap2);
        childMapList.add(childItemMap3);

        childMapList.add(childItemMap4);
        */


        childMap.add(childMapList);
        childStateMap.add(childStateMapList);
        childExpMonthMap.add(childExpMonthMapList);
        childExpYearhMap.add(childExpYearMapList);


        groupItemMap.put("Group Item", "COUNTRY");
        groupStateItemMap.put("State Group Item", "STATE");
        groupExpMonthItemMap.put("ExpMonth Group Item", "EXP. MONTH");
        groupExpYearItemMap.put("ExpYear Group Item", "EXP. YEAR");




        groupMapList.add(groupItemMap);
        groupStateMapList.add(groupStateItemMap);
        groupExpMonthMapList.add(groupExpMonthItemMap);
        groupExpYearMapList.add(groupExpYearItemMap);






        //listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        try {
            //listAdapter = new ExpandableListAdapter(getApplication().getApplicationContext(), listDataHeader, countryItemList);

            SimpleExpandableListAdapter expListAdapter =
                    new SimpleExpandableListAdapter(
                            getApplication().getApplicationContext(),
                            groupMapList,              // Creating group List.
                            R.layout.country_header_view,             // Group item layout XML.
                            new String[] { "Group Item" },  // the key of group item.
                            new int[] { R.id.tvGroup },    // ID of each group item.-Data under the key goes into this TextView.
                            childMap,              // childData describes second-level <span id="IL_AD5" class="IL_AD">entries</span>.
                            R.layout.country_child_view,             // Layout for sub-level entries(second level).
                            new String[] {"country_name"},      // Keys in childData maps to display.
                            new int[] { R.id.tvChild}     // Data under the keys above go into these TextViews.
                    );


            expListView.setAdapter(expListAdapter);



        }
        catch(Exception e){
            System.out.println(String.format("MONTH LISTADAPTER EX ==>%s", e.toString()));

        }



        // setting list adapter
        //expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String cn = countryItemList.get(childPosition).country_name;

                Toast.makeText(getApplicationContext(),
                        String.format("COUNTRY ==> %s", cn),
                        Toast.LENGTH_SHORT).show();

                //expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
                EditText countryText = (EditText) findViewById(R.id.country_edit_text);
                countryText.setText(cn);

                expListView.collapseGroup(groupPosition);

                return false;
            }
        });


            // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupMapList.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();

                ExpandableListView elv = (ExpandableListView) findViewById(R.id.expandableListView);
                elv.setMinimumHeight(1300);

                ViewGroup.LayoutParams params = expListView.getLayoutParams();
                params.height = 1300;
                expListView.setLayoutParams(params);
                expListView.requestLayout();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupMapList.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

                ViewGroup.LayoutParams params = expListView.getLayoutParams();
                params.height = 100;
                expListView.setLayoutParams(params);
                expListView.requestLayout();

            }
        });


        //EXPANDABLE LIST VIEW FOR STATE**********

        try {

            SimpleExpandableListAdapter expStateListAdapter =
                    new SimpleExpandableListAdapter(
                            getApplication().getApplicationContext(),
                            groupStateMapList,              // Creating group List.
                            R.layout.state_header_view,             // Group item layout XML.
                            new String[] { "State Group Item" },  // the key of group item.
                            new int[] { R.id.tvStateGroup },    // ID of each group item.-Data under the key goes into this TextView.
                            childStateMap,              // childData describes second-level <span id="IL_AD5" class="IL_AD">entries</span>.
                            R.layout.state_child_view,             // Layout for sub-level entries(second level).
                            new String[] {"state_abbreviation"},      // Keys in childData maps to display.
                            new int[] { R.id.tvStateChild}     // Data under the keys above go into these TextViews.
                    );



                expStateListView.setAdapter(expStateListAdapter);



        }
        catch(Exception e){
            System.out.println(String.format("STATE LISTADAPTER EX ==>%s", e.toString()));

        }

        expStateListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String sa = stateItemList.get(childPosition).state_abbreviation;

                Toast.makeText(getApplicationContext(),
                        String.format("STATE ==> %s", sa),
                        Toast.LENGTH_SHORT).show();

                //expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);
                EditText stateAbbrevText = (EditText) findViewById(R.id.state_edit_text);
                stateAbbrevText.setText(sa);

                expStateListView.collapseGroup(groupPosition);

                return false;
            }
        });


        // Listview Group click listener
        expStateListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expStateListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupStateMapList.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();

                ExpandableListView eslv = (ExpandableListView) findViewById(R.id.stateExpandableListView);
                eslv.setMinimumHeight(1300);

                ViewGroup.LayoutParams stateLayoutParams = expStateListView.getLayoutParams();
                stateLayoutParams.height = 1300;
                expStateListView.setLayoutParams(stateLayoutParams);
                expStateListView.requestLayout();
            }
        });

        // Listview Group collasped listener
        expStateListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupStateMapList.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

                ViewGroup.LayoutParams stateLayoutParams = expStateListView.getLayoutParams();
                stateLayoutParams.height = 100;
                expStateListView.setLayoutParams(stateLayoutParams);
                expStateListView.requestLayout();

            }
        });


        //********EXPANDABLE LISTVIEW FOR STATE


        //*******EXPANDABLE LISTVIEW FOR EXP MONTH


        try {

            SimpleExpandableListAdapter expExpMonthListAdapter =
                    new SimpleExpandableListAdapter(
                            getApplication().getApplicationContext(),
                            groupExpMonthMapList,              // Creating group List.
                            R.layout.month_header,             // Group item layout XML.
                            new String[] { "ExpMonth Group Item" },  // the key of group item.
                            new int[] { R.id.tvMonthGroup },    // ID of each group item.-Data under the key goes into this TextView.
                            childExpMonthMap,              // childData describes second-level <span id="IL_AD5" class="IL_AD">entries</span>.
                            R.layout.month_child,             // Layout for sub-level entries(second level).
                            new String[] {"month"},      // Keys in childData maps to display.
                            new int[] { R.id.tvMonthChild}     // Data under the keys above go into these TextViews.
                    );



            expMonthListView.setAdapter(expExpMonthListAdapter);



        }
        catch(Exception e){
            System.out.println(String.format("MONTH LISTADAPTER EX ==>%s", e.toString()));

        }

        expMonthListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                System.out.println("xxxxxx==>");

                String expMonth = childExpMonthMapList.get(childPosition).get("month");
                Toast.makeText(getApplicationContext(),
                        String.format("MONTH ==> %s", expMonth),
                        Toast.LENGTH_SHORT).show();


                //expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

                TextView expirationMonth = (TextView) findViewById(R.id.exp_month_value_textview);
                expirationMonth.setText(String.format("%d", Integer.parseInt(expMonth)));


                expMonthListView.collapseGroup(groupPosition);

                return false;
            }
        });


        // Listview Group click listener
        expMonthListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expMonthListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupExpMonthMapList.get(groupPosition) + " Month Expanded",
                        Toast.LENGTH_SHORT).show();

                ExpandableListView emlv = (ExpandableListView) findViewById(R.id.expandableListViewMonth);
                emlv.setMinimumHeight(1300);

                ViewGroup.LayoutParams stateLayoutParams = expMonthListView.getLayoutParams();
                stateLayoutParams.height = 1300;
                expMonthListView.setLayoutParams(stateLayoutParams);
                expMonthListView.requestLayout();
            }
        });

        // Listview Group collasped listener
        expMonthListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupExpMonthMapList.get(groupPosition) + " Month Collapsed",
                        Toast.LENGTH_SHORT).show();

                ViewGroup.LayoutParams monthLayoutParams = expMonthListView.getLayoutParams();
                monthLayoutParams.height = 100;
                expMonthListView.setLayoutParams(monthLayoutParams);
                expMonthListView.requestLayout();

            }
        });



        //*******END EXPANDABLE LISTVIEW FOR EXP MONTH



        //*******EXPANDABLE LISTVIEW FOR EXP YEAR

        try {

            SimpleExpandableListAdapter expExpYearListAdapter =
                    new SimpleExpandableListAdapter(
                            getApplication().getApplicationContext(),
                            groupExpYearMapList,              // Creating group List.
                            R.layout.year_header,             // Group item layout XML.
                            new String[] { "ExpYear Group Item" },  // the key of group item.
                            new int[] { R.id.tvYearGroup },    // ID of each group item.-Data under the key goes into this TextView.
                            childExpYearhMap,              // childData describes second-level <span id="IL_AD5" class="IL_AD">entries</span>.
                            R.layout.year_child,             // Layout for sub-level entries(second level).
                            new String[] {"year"},      // Keys in childData maps to display.
                            new int[] { R.id.tvYearChild}     // Data under the keys above go into these TextViews.
                    );



            expYearListView.setAdapter(expExpYearListAdapter);



        }
        catch(Exception e){
            System.out.println(String.format("MONTH LISTADAPTER EX ==>%s", e.toString()));

        }

        expYearListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                System.out.println("yyyyyy==>");

                String expYear = childExpYearMapList.get(childPosition).get("year");
                Toast.makeText(getApplicationContext(),
                        String.format("MONTH ==> %s", expYear),
                        Toast.LENGTH_SHORT).show();


                //expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

                TextView expirationYear = (TextView) findViewById(R.id.exp_year_value_textview);
                expirationYear.setText(String.format("%d", Integer.parseInt(expYear)));


                expYearListView.collapseGroup(groupPosition);

                return false;
            }
        });


        expYearListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expYearListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupExpYearMapList.get(groupPosition) + " Year Expanded",
                        Toast.LENGTH_SHORT).show();

                ExpandableListView eylv = (ExpandableListView) findViewById(R.id.expandableListViewYear);
                eylv.setMinimumHeight(700);

                ViewGroup.LayoutParams yearLayoutParams = expYearListView.getLayoutParams();
                yearLayoutParams.height = 700;
                expYearListView.setLayoutParams(yearLayoutParams);
                expYearListView.requestLayout();
            }
        });

        // Listview Group collasped listener
        expYearListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        groupExpYearMapList.get(groupPosition) + " Year Collapsed",
                        Toast.LENGTH_SHORT).show();

                ViewGroup.LayoutParams yearLayoutParams = expYearListView.getLayoutParams();
                yearLayoutParams.height = 100;
                expYearListView.setLayoutParams(yearLayoutParams);
                expYearListView.requestLayout();

            }
        });


        //*******END EXPANDABLE LISTVIEW FOR EXP YEAR



        stripeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //VALIDATE FORM

                checkoutCart = CartSingleton.getInstance().getCheckoutCart();


                ArrayList<MerchItem> recipientItems = new ArrayList<MerchItem>();

                recipientItems = checkoutCart.itemsInCart();

                String recipientEmail = "";
                String recipientCountry = "";
                String recipientPhone = "";
                String recipientCreditCard = "";
                String recipientCVC= "";

                String recipientName = "";
                String recipientAddress = "";
                String recipientCity = "";
                String recipientState = "";
                String recipientZip = "";
                double recipientTotal = checkoutCart.calculateTotal();
                double recipientShipping = checkoutCart.calculateShipping();

                int recipientCardExpirationMonth=0;
                int recipientCardExpirationYear=0;

                Map<String,List<Map<String, String>>> recipentItemsDict = new HashMap<String,List<Map<String,String>>>();
                //List<Map<String, String>> encodedMerchItemsList = new ArrayList<Map<String,String>>();

                JSONArray encodedMerchItemsList = new JSONArray();


                Map<String,Object> emailDataMap = new HashMap<String, Object>();



                EditText recipName = (EditText) findViewById(R.id.stripe_name);
                recipientName = recipName.getText().toString().trim();

                EditText recipCity = (EditText) findViewById(R.id.city_edit_text);
                recipientCity = recipCity.getText().toString().trim();

                EditText recipAddress1 = (EditText) findViewById(R.id.address_1);
                recipientAddress = recipAddress1.getText().toString().trim();

                EditText recipAddress2 = (EditText) findViewById(R.id.address_2);
                recipientAddress =  String.format("%s %s", recipientAddress, recipAddress2.getText().toString().trim());

                EditText recipCountry = (EditText) findViewById(R.id.country_edit_text);
                recipientCountry = recipCountry.getText().toString().trim();

                EditText recipEmail = (EditText) findViewById(R.id.email_text);
                recipientEmail = recipEmail.getText().toString().trim();

                EditText recipPhone = (EditText) findViewById(R.id.phone_edit_text);
                recipientPhone = recipPhone.getText().toString().trim();

                EditText recipCreditCard = (EditText) findViewById(R.id.credit_card_edit_text);
                recipientCreditCard = recipCreditCard.getText().toString().trim();

                EditText recipCVC = (EditText) findViewById(R.id.cvc_edit_text);
                recipientCVC = recipCVC.getText().toString().trim();


                EditText recipState = (EditText) findViewById(R.id.state_edit_text);
                recipientState = recipState.getText().toString().trim();

                EditText recipZip = (EditText) findViewById(R.id.zip_edit_text);
                recipientZip = recipZip.getText().toString().trim();


                TextView recipCardExpirationMonth = (TextView) findViewById(R.id.exp_month_value_textview);
                recipientCardExpirationMonth  = Integer.parseInt(recipCardExpirationMonth.getText().toString());

                TextView recipCardExpirationYear = (TextView) findViewById(R.id.exp_year_value_textview);
                recipientCardExpirationYear  = Integer.parseInt(recipCardExpirationYear.getText().toString());

                //emailDataMap.put("recpientEmail");
                emailDataMap.put("recipientName",recipientName);
                emailDataMap.put("recipientEmail",recipientEmail);
                emailDataMap.put("recipientAddress",recipientAddress);
                emailDataMap.put("recipientCity",recipientCity);
                emailDataMap.put("recipientState",recipientState);
                emailDataMap.put("recipientZip",recipientZip);
                emailDataMap.put("recipientCountry",recipientCountry);
                emailDataMap.put("recipientTotal",recipientTotal);
                emailDataMap.put("recipientShipping",recipientShipping);

                //try catch

                encodedMerchItemsList = encodeMerchObject(checkoutCart.itemsInCart());

                emailDataMap.put("recipientItems", encodedMerchItemsList);


                System.out.println("STRIPE Button Clicked!");

                //Intent intent = new Intent(getApplicationContext(),MerchActivity.class);
                //startActivity(intent);

                //TextView tvStripeAmount = findViewById()

                /**********************************************
                 * 1.) Get Total and store in map w/ stripeAmount key
                 * 2.) Get info from form
                 * 3.) Using Country get Currency and store in map w/ stripeCurrency key
                 *
                 *
                 *
                ***********************************************/

                //chargeMap.put("stripeAmount", 100);

                //double itemTotal = checkoutCart.calculateTotal();

                chargeMap.put("stripeAmount", checkoutCart.calculateTotal()*100);
                chargeMap.put("stripeCurrency", "usd");

                /*

                cardMap.put("number", "4242424242424242");
                cardMap.put("exp_month", 12);
                cardMap.put("exp_year", 2020);

                */

                cardMap.put("number", recipientCreditCard.toString().trim());
                cardMap.put("exp_month", recipientCardExpirationMonth);
                cardMap.put("exp_year", recipientCardExpirationYear);

                JSONObject cardMapJSON = new JSONObject(cardMap);

                //chargeMap.put("stripeCard", cardMap);

                chargeMap.put("stripeCard", cardMapJSON);

                JSONObject chargeMapJSON = new JSONObject(chargeMap);


                //new AsyncStripeTask().execute(STRIPE_URL, chargeMapJSON.toString());
                LinearLayout stripeLayout = (LinearLayout)findViewById(R.id.stripe_checkout);



                if(formIsValid(stripeLayout))
                     new AsyncStripeTask().execute(STRIPE_URL, chargeMapJSON);


                //if charge is successful send email


                new AsyncStripeEmailTask().execute(STRIPE_EMAIL_URL, emailDataMap);






            }
        });





    }

    public boolean formIsValid(LinearLayout formLayout){

        for(int i=0;i<formLayout.getChildCount();i++)
        {
            View v = formLayout.getChildAt(i);




            if(v instanceof EditText){

                EditText et = (EditText)findViewById(v.getId());

                System.out.println(String.format("EDITTEXT in formIsValid==> %s\n\n",et.getText()));


                if(et.getText().toString().equals("")){
                    //make text red
                    et.setBackgroundColor(Color.RED);
                    System.out.println("EMPTY");
                }

                System.out.println(String.format("view in formIsValid==> %s ID==>%s ResType ==> %s\n\n ", v.toString(), getResources().getResourceName(v.getId()), getResources().getResourceTypeName(v.getId())));


                System.out.println(String.format("INPUT ID ==>%d",((EditText) v).getInputType()));


            }
            else if(v instanceof EditText){

            }

        }



        return true;
    }

    //public List<Map<String,String>> encodeMerchObject(ArrayList<MerchItem> merchItemArrayList){
        public JSONArray encodeMerchObject(ArrayList<MerchItem> merchItemArrayList){


            //List<Map<String, String>> merchItemsList = new ArrayList<Map<String,String>>();
            JSONArray merchItemsList = new JSONArray();


            for(MerchItem mItem:merchItemArrayList){
            Map<String, String> merchItemMap = new HashMap<String,String>();

            merchItemMap.put("itemName",mItem.getMt_item_desc_short().toString().trim());
            merchItemMap.put("itemPrice",String.format("%3.2f", mItem.getMt_item_price()).trim());
            //merchItemMap.put("itemSize",mItem.getMt_item_size());

            merchItemMap.put("itemType",mItem.getMt_item_type().trim());
            merchItemMap.put("itemQty",String.format("%d",mItem.getMt_item_qty()).trim());

            JSONObject merchMapJSON = new JSONObject(merchItemMap);



            //merchItemsList.add(merchItemMap);

                merchItemsList.put(merchMapJSON);


            }



        return merchItemsList;
    }

    //public List<Map<String,Integer>> populateExpirationList(String jsonString, String resourceKey) throws JSONException {
    public List<Map<String,String>> populateExpirationList(String jsonString, String resourceKey) throws JSONException {


        //List<Map<String,Integer>> mapList = new ArrayList<Map<String,Integer>>();

        List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();


        JSONArray jsonExpArray = new JSONArray(jsonString);

        for(int idx=0; idx<jsonExpArray.length();idx++){


            System.out.println("here");

            JSONObject jsonObject = new JSONObject(jsonExpArray.get(idx).toString());

            //Map<String,Integer> tempExpMap = new HashMap<String,Integer>();

            Map<String,String> tempExpMap = new HashMap<String,String>();

            System.out.println("here here");

            //tempExpMap.put(resourceKey.toString(),(Integer)jsonObject.get(resourceKey.toString()));

            tempExpMap.put(resourceKey.toString(),(String)jsonObject.get(resourceKey.toString()));

            System.out.println("here here here");

            mapList.add(tempExpMap);


        }//end for


        return mapList;


    }


    public List<Map<String,String>> populateCountryItemList(){

            List<Map<String,String>> countryMapList = new ArrayList<Map<String,String>>();

            for(int i=0;i<countryItemList.size();i++) {

                Map<String,String> tempCountryMap = new HashMap<String,String>();

                tempCountryMap.put("country_name", countryItemList.get(i).country_name);

                countryMapList.add(tempCountryMap);

            }//end for

            return countryMapList;


    }

    public List<Map<String,String>> populateStateItemList(){

        List<Map<String,String>> stateMapList = new ArrayList<Map<String,String>>();

        for(int i=0;i<stateItemList.size();i++) {

            Map<String,String> tempStateMap = new HashMap<String,String>();

            tempStateMap.put("state_abbreviation", stateItemList.get(i).state_abbreviation);

            stateMapList.add(tempStateMap);

        }//end for

        return stateMapList;


    }



    //public class AsyncStripeTask extends AsyncTask<String,Void,Boolean> {

    public class AsyncStripeTask extends AsyncTask<Object,Void,Boolean> {


            @Override
        //protected Boolean doInBackground(String... params) {
                protected Boolean doInBackground(Object... params) {



                    String url = (String)params[0];
                    JSONObject stripeMapJSON = (JSONObject)params[1];
                //Map stripeMap = (Map)params[1];


                String resp = "";


                //System.out.println("STRIPE MAP==>"+stripeMapJSON);
/*
                JSONObject jsonParams = new JSONObject(stripeMap);
                params[1] = jsonParams;
*/

                System.out.println("STRIPE REQ PARAMS ==>" + params.toString());

                System.out.println("STRIPE JSON ==>"+params[1].toString());



                try {
                    /*
                    JSONArray ja = new JSONArray();
                    ja.put(stripeMapJSON);
                    params[1] = ja;
                    */

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");


                    con.setRequestProperty("Content-Type","application/json");
                    //con.setRequestProperty("ContenType","application/json");
                //map reqcon.getRequestProperties()

                con.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                //outputStreamWriter.write(params.toString());

                    outputStreamWriter.write(stripeMapJSON.toString());

                    outputStreamWriter.flush();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                //System.out.println("Post parameters : " + params.toString());

                    System.out.println("Post parameters : " + stripeMapJSON.toString());

                    System.out.println("Response Code : " + responseCode);

                //if response code == 200

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine + "\n");
                }
                in.close();

                System.out.println("STRIPE Response : " + response);

                //JSONObject jObj = new JSONObject(response.toString());

                //JSONArray jArray  = new JSONArray(response.toString());



                //JSONArray jsonArray = jObj.getJSONArray("");

            } catch (UnsupportedEncodingException e) {
                System.out.println(String.format("UnsupportedEncodingException==>%s\n",e.toString()));
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }/* catch (ProtocolException e) {
                e.printStackTrace();
            } */catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                return  true;
            }
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            System.out.println(String.format("here in STRIPE async task"));

            try {
                this.get(1000, TimeUnit.MILLISECONDS);
            }catch(Exception ex){
                System.out.println(String.format("AsyncStripeExce",ex.toString()));

            }

        }
    }

    public class AsyncStripeEmailTask extends AsyncTask<Object,Void,Boolean> {


        @Override
        //protected Boolean doInBackground(String... params) {
        protected Boolean doInBackground(Object... params) {



            String url = (String)params[0];

            //JSONArray stripeMapJSON = (JSONArray)params[1];
            Map recipMap = (Map)params[1];
            JSONObject stripeMapJSON = new JSONObject(recipMap);

            //Map stripeMap = (Map)params[1];


            String resp = "";


            //System.out.println("STRIPE MAP==>"+stripeMapJSON);
/*
                JSONObject jsonParams = new JSONObject(stripeMap);
                params[1] = jsonParams;
*/

            System.out.println("STRIPE EMAIL REQ PARAMS ==>"+params.toString());

            System.out.println("STRIPE EMAIL JSON ==>"+params[1].toString());



            try {
                    /*
                    JSONArray ja = new JSONArray();
                    ja.put(stripeMapJSON);
                    params[1] = ja;
                    */

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");


                con.setRequestProperty("Content-Type","application/json");
                //con.setRequestProperty("ContenType","application/json");
                //map reqcon.getRequestProperties()

                con.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                //outputStreamWriter.write(params.toString());

                outputStreamWriter.write(stripeMapJSON.toString());

                outputStreamWriter.flush();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                //System.out.println("Post parameters : " + params.toString());

                System.out.println("Post parameters : " + stripeMapJSON.toString());

                System.out.println("STRIPE EMAIL Response Code : " + responseCode);

                //if response code == 200

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine + "\n");
                }
                in.close();

                System.out.println("STRIPE EMAIL Response : " + response);

                //JSONObject jObj = new JSONObject(response.toString());

                //JSONArray jArray  = new JSONArray(response.toString());



                //JSONArray jsonArray = jObj.getJSONArray("");

            } catch (UnsupportedEncodingException e) {
                System.out.println(String.format("UnsupportedEncodingException==>%s\n",e.toString()));
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }/* catch (ProtocolException e) {
                e.printStackTrace();
            } */catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                return  true;
            }
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            System.out.println(String.format("here in STRIPE EMAIL async task"));

        }
    }

    public String getJSONResource(String fileName) throws IOException,UnsupportedEncodingException {
        String jsonString="";

        int rawResId = this.getResources().getIdentifier(fileName, "raw", this.getPackageName());


        InputStream is = getResources().openRawResource(rawResId);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];

        Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        int n;
        while ((n = reader.read(buffer)) != -1) {
            writer.write(buffer, 0, n);
        }

            is.close();


         jsonString = writer.toString();

        System.out.println(String.format("JSON RAW RESOURCE %s ==> %s",fileName,jsonString));

        return jsonString;
    }



    public String getCurrencyCode(String stripeCountry){
        String currencyCode = "";

        List<CountryItem> countryItemListCurrency = new ArrayList<CountryItem>();
        countryItemListCurrency = CountryItemSingleton.getInstance().getArrayList();

        for(int idx=0; idx < countryItemListCurrency.size(); idx++){
            if(countryItemListCurrency.get(idx).country_name.trim() == stripeCountry.trim() ){

                //determine currency code from abbreviation

                currencyCode=countryItemListCurrency.get(idx).country_abbreviation.trim();
                break;

            }
        }
        return currencyCode;

    }

    public class AsyncCountryTask extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {


            String url = params[0];

            String resp = "";

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("POST");

                con.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());
                outputStreamWriter.write(params.toString());
                outputStreamWriter.flush();

                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'POST' request to URL : " + url);
                System.out.println("Post parameters : " + params);
                System.out.println("Response Code : " + responseCode);

                //if response code == 200

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine + "\n");
                }
                in.close();

                System.out.println("Response : " + response);

                //JSONObject jObj = new JSONObject(response.toString());

                JSONArray jArray = new JSONArray(response.toString());

                //populateCountryItemList(jArray);

                //JSONArray jsonArray = jObj.getJSONArray("");

            } catch (UnsupportedEncodingException e) {
                System.out.println(String.format("UnsupportedEncodingException==>%s\n", e.toString()));
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }/* catch (ProtocolException e) {
                e.printStackTrace();
            } */ catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return true;
            }
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            System.out.println(String.format("here in COUNTRY async task"));

        }

    }//End AsyncCountryTask
}




