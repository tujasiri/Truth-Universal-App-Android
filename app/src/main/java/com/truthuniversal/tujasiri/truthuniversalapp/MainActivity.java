package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 11/23/15.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//public class MainActivity extends AppCompatActivity {

    public class MainActivity extends AppCompatActivity {

        private List<CountryItem> countryItemListMain = new ArrayList<CountryItem>();
        private List<StateItem> stateItemListMain = new ArrayList<StateItem>();


        public static final String COUNTRY_DATA_URL = "http://truthuniversal.com/android/countries";
        public static final String STATE_DATA_URL = "http://truthuniversal.com/android/states";




        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);



        countryItemListMain = CountryItemSingleton.getInstance().getArrayList();
            stateItemListMain = StateItemSingleton.getInstance().getArrayList();


            final Button merchButton = (android.widget.Button) findViewById(R.id.merchButton);
            final Button newsButton = (android.widget.Button) findViewById(R.id.newsButton);
            final Button videoButton = (android.widget.Button) findViewById(R.id.videoButton);



            merchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("MERCH Button Clicked!");
                Intent intent = new Intent(getApplicationContext(),MerchActivity.class);
                startActivity(intent);



            }
            });


            newsButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    System.out.println("NEWS Button Clicked!");
                    Intent intent = new Intent(getApplicationContext(),NewsActivity.class);
                    startActivity(intent);



                }
            });

            videoButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.out.println("VID Button Clicked!");
                    Intent intent = new Intent(getApplicationContext(),YouTubeActivity.class);
                    startActivity(intent);

                }

                });
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main_activity_test, menu);

            new AsyncCountryTask().execute(COUNTRY_DATA_URL);
            new AsyncStateTask().execute(STATE_DATA_URL);


            return super.onCreateOptionsMenu(menu);
        }

        public boolean onOptionsItemSelected(MenuItem menuItem){

            int id = menuItem.getItemId();

            System.out.println(String.format("id ==>%d, action_settings ==> %d, R.it.cut_icon==>%d", id, R.id.action_settings, R.id.shopping_cart));

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

                    populateCountryItemList(jArray);

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

        public void populateCountryItemList(JSONArray jsonCountryArray){
            try {
                //JSONArray jsonArray = jsonObject.getJSONArray("");

                for(int i=0;i<jsonCountryArray.length();i++) {
                    ImageView ivTemp = null;

                    CountryItem tempCountryItem = new CountryItem();

                    JSONObject jsonObjectCountryItem =  jsonCountryArray.getJSONObject(i);

                    tempCountryItem.setCountry_name(jsonObjectCountryItem.getString("name"));
                    tempCountryItem.setCountry_abbreviation(jsonObjectCountryItem.getString("code"));



                    countryItemListMain.add(tempCountryItem);

                    //System.out.println("NEWS JSON ADD==>");



                }//end for
            }catch (JSONException je){
                System.out.println("COUNTRY JSON EXCEPTION==>"+je);
            }

        }
        //STATE STUFF

        public class AsyncStateTask extends AsyncTask<String,Void,Boolean> {

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

                    populateStateItemList(jArray);

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

                System.out.println(String.format("here in STATE async task"));

            }

        }//End AsyncCountryTask

        public void populateStateItemList(JSONArray jsonStateArray){
            try {
                //JSONArray jsonArray = jsonObject.getJSONArray("");

                for(int i=0;i<jsonStateArray.length();i++) {

                    StateItem tempStateItem = new StateItem();

                    JSONObject jsonObjectStateItem =  jsonStateArray.getJSONObject(i);

                    tempStateItem.setState_name(jsonObjectStateItem.getString("name"));
                    tempStateItem.setState_abbreviation(jsonObjectStateItem.getString("abbreviation"));



                    stateItemListMain.add(tempStateItem);

                    //System.out.println("NEWS JSON ADD==>");



                }//end for
            }catch (JSONException je){
                System.out.println("STATE JSON EXCEPTION==>"+je);
            }

        }


    }
