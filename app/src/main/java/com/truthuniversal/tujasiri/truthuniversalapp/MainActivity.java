package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 11/23/15.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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


public class MainActivity extends AppCompatActivity {

        private List<CountryItem> countryItemListMain = new ArrayList<CountryItem>();
        private List<StateItem> stateItemListMain = new ArrayList<StateItem>();


        public static final String COUNTRY_DATA_URL = "http://truthuniversal.com/android/countries";
        public static final String STATE_DATA_URL = "http://truthuniversal.com/android/states";

        private DrawerLayout mDrawerLayout;

        private ActionBarDrawerToggle mDrawerToggle;
        private CharSequence mDrawerTitle="Navigation";
        private CharSequence mTitle="Truth Universal App";

        TUFirebaseInstanceIdService tuFirebaseInstanceIdService = new TUFirebaseInstanceIdService();
        TUFirebaseMessagingService tuFirebaseMessagingService;




        @SuppressLint("NewApi")
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //setContentView(R.layout.main_layout);
            setContentView(R.layout.content_main);



            if (this.getIntent().hasExtra("pushNotificationBundle")) {
                System.out.println("has push bundle");


                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener()
                {

                public void onClick(DialogInterface dialogInterface,int id){

                    hideBackground((ImageView)findViewById(R.id.default_imageview));

                    FragmentManager fragManager = getFragmentManager();
                    fragManager.beginTransaction()
                            .replace(R.id.content_frame, new NewsFragment())
                            .commit();
                }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                //builder.setTitle("Truth Universal Notification");
                builder.setTitle((String) this.getIntent().getBundleExtra("pushNotificationBundle").get("title"));
                //builder.setCustomTitle((View) findViewById(R.id.my_toolbar));
                builder.setCancelable(true);
                //builder.setIcon(R.drawable.ic_truth_universal_logo_firebase);
                builder.setIcon(R.drawable.ic_truth_logo_dark);
                builder.setMessage((String) this.getIntent().getBundleExtra("pushNotificationBundle").get("message"));
                //builder.setPositiveButton("OK", null);
                builder.setPositiveButton("GO TO NEWS", dialogListener);
                builder.setNegativeButton("CANCEL", null);
                builder.show();

            }


            FirebaseMessaging.getInstance().subscribeToTopic("test");
            //String fbToken = FirebaseInstanceId.getInstance().getToken();

            new AsyncRegisterFCMToken().execute();

            ImageView ivAppHeader = (ImageView)findViewById(R.id.app_header);

            Drawable appHeaderDrawable = getResources().getDrawable(R.drawable.ic_truth_universal_header,null);
            ivAppHeader.setImageDrawable(appHeaderDrawable);

            final LinearLayout buttonLayout = (LinearLayout)findViewById(R.id.left_drawer);

            final ImageView default_iv = (ImageView)findViewById(R.id.default_imageview);
            //default_iv.setBackgroundColor(Color.BLACK);
            final Animation anim_in  = AnimationUtils.loadAnimation(getApplication(), android.R.anim.fade_in);


            default_iv.animate().setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationResume(Animator animation) {
                    super.onAnimationResume(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            });

            Drawable splashDrawable = getResources().getDrawable(R.drawable.appsplash_vignette, null);

            default_iv.setImageDrawable(splashDrawable);

            /***Drawer **/
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            int i = 0;
            int j = 0;


            Toolbar tb= (Toolbar)findViewById(R.id.toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(
                    this,
                    mDrawerLayout,
                    tb,
                    R.string.abc_action_bar_home_description,
                    R.string.abc_action_bar_up_description
            ) {


                /**
                 * Called when a drawer has settled in a completely closed state.
                 **/

                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    getSupportActionBar().setTitle(mTitle);
                }

                /**
                 * Called when a drawer has settled in a completely open state.
                 **/

                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    getSupportActionBar().setTitle(mDrawerTitle);
                }
            };

            // Set the drawer toggle as the DrawerListener
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();


        countryItemListMain=CountryItemSingleton.getInstance().

        getArrayList();

        stateItemListMain=StateItemSingleton.getInstance().

        getArrayList();


        final Button merchButton = (android.widget.Button) findViewById(R.id.merchButton);
        final Button newsButton = (android.widget.Button) findViewById(R.id.newsButton);
        final Button videoButton = (android.widget.Button) findViewById(R.id.videoButton);
        final Button socialButton = (android.widget.Button) findViewById(R.id.socialButton);
        final Button musicButton = (android.widget.Button) findViewById(R.id.musicButton);

        merchButton.setOnClickListener(new View.OnClickListener()

        {
            public void onClick (View v){


                mDrawerLayout.closeDrawer(GravityCompat.START);

                disableSelectedButton(merchButton, buttonLayout);

                hideBackground(default_iv);

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new MerchFragment())
                        .commit();

            }
            });


            newsButton.setOnClickListener(new View.OnClickListener(){

                    public void onClick (View v){

                    mDrawerLayout.closeDrawer(GravityCompat.START);

                    disableSelectedButton(newsButton,buttonLayout);

                    hideBackground(default_iv);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new NewsFragment())
                            .commit();

           }
           });

                videoButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick (View v){

                    mDrawerLayout.closeDrawer(GravityCompat.START);

                    disableSelectedButton(videoButton,buttonLayout);
                    hideBackground(default_iv);
                    FragmentManager fragmentManager = getFragmentManager();

                    fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new YouTubeFragment())
                        .commit();
            }
            });

            socialButton.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    disableSelectedButton(socialButton,buttonLayout);

                    if(default_iv.getAlpha()==0.0) {
                        default_iv.setAlpha(1.0f);
                        default_iv.setY(0.0f);
                    }

                    System.out.println("YVal Social==>"+ default_iv.getY());


                    mDrawerLayout.closeDrawer(GravityCompat.START);

                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new SocialFragment())
                            .commit();


                }
            });


            musicButton.setOnClickListener(new View.OnClickListener(){

                public void onClick (View v){

                mDrawerLayout.closeDrawer(GravityCompat.START);

                disableSelectedButton(musicButton,buttonLayout);
                hideBackground(default_iv);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new GooglePlayMusicFragment())
                        .commit();

            }
            });


            }

            /*DRAWER LOGIC'? */

            @Override
            protected void onPostCreate(Bundle savedInstanceState) {
                super.onPostCreate(savedInstanceState);
                // Sync the toggle state after onRestoreInstanceState has occurred.
                mDrawerToggle.syncState();
            }

            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                super.onConfigurationChanged(newConfig);
                mDrawerToggle.onConfigurationChanged(newConfig);
            }

           /* */
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

            if (mDrawerToggle.onOptionsItemSelected(menuItem)) {
                return true;
            }

            if(id == R.id.action_settings){
                return true;

            }

            switch(id){
                case R.id.truth_universal_logo:
                {
                    //Toast.makeText(getBaseContext(), "Clicked LOG Icon!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }
                break;
                case R.id.home_icon:
                {
                    //Toast.makeText(getBaseContext(), "Clicked HOME Icon!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
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
                    /*
                    System.out.println("\nSending 'POST' request to URL : " + url);
                    System.out.println("Post parameters : " + params);
                    System.out.println("Response Code : " + responseCode);
                    */

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

                //System.out.println(String.format("here in COUNTRY async task"));

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

                }//end for
            }catch (JSONException je){
                System.out.println("STATE JSON EXCEPTION==>"+je);
            }




        }

        @SuppressLint("NewApi")
        public void hideBackground(ImageView iv){

            iv.animate()
                    .alpha(0.0f)
                    .translationY(iv.getHeight())
                    .setDuration(600);;

        }

        public void disableSelectedButton(Button button,LinearLayout frmLayout){

                for(int i=0;i<frmLayout.getChildCount();i++){

                    View view = frmLayout.getChildAt(i);

                    if(view instanceof Button){

                        Button button1 = (Button)findViewById(view.getId());

                        if(button == button1){
                           button1.setEnabled(false);
                        }
                        else
                            button1.setEnabled(true);

                    }//end if

                }//end for


            }

        public class AsyncRegisterFCMToken extends AsyncTask<String,Void,Boolean> {
            @Override
            protected Boolean doInBackground(String... params) {
                tuFirebaseInstanceIdService.onTokenRefresh();
                return null;
            }

            @Override
            protected void onPreExecute() {
                System.out.println("registered token");
            }
        }




    }//end Main
