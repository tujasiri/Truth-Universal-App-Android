package com.truthuniversal.tujasiri.truthuniversalapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tujasiri on 6/14/2016.
 */
public class GooglePlayMusicActivity extends AppCompatActivity {

    private List<GoogleMusicAlbumItem> googleMusicItemList = new ArrayList<GoogleMusicAlbumItem>();

    ExpandableListView expListViewAlbum;

    public static final List<Map<String, String>> groupAlbumMapList = new ArrayList<Map<String, String>>();
    public static final List<Map<String, String>> childSongMapList = new ArrayList<Map<String, String>>();
    public static final Map<String, String>childSongMap = new HashMap<String, String>();

    public static final List<List<Map<String, String>>> childAlbumSongMapList = new ArrayList<List<Map<String, String>>>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.google_album_detail_layout);

        new AsyncGooglePlayMusicTask().execute("http://truthuniversal.com/googlemusicjson");



        //listviewAdapter


    }


    public class AsyncGooglePlayMusicTask extends AsyncTask<String, Void, Boolean> {

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

                //populateNewsItemList(jArray);
                populateGoogleMusicAlbumItemList(jArray);

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

            System.out.println(String.format("here in goolgleplaymusic async task"));
            //populateListView();
            makeMusicExpandableListViews();

        }

        private void populateGoogleMusicAlbumItemList(JSONArray jsonMusicArray) {
            //Map<String,ImageView> imageViewMap = new HashMap<String,ImageView>();

            //JSONObject jsonObject = (JSONObject)jsonMerchObject;

            //merchItemList = ItemSingleton.getInstance().getArrayList();

            try {
                //JSONArray jsonArray = jsonObject.getJSONArray("");

                for (int i = 0; i < jsonMusicArray.length(); i++) {

                    GoogleMusicAlbumItem tempGoogleMusicAlbumItem = new GoogleMusicAlbumItem();

                    JSONObject jsonObjectMusicItem = jsonMusicArray.getJSONObject(i);

                    JSONObject jsonObjectAlbum = jsonObjectMusicItem.getJSONObject("album");

                    tempGoogleMusicAlbumItem.setGma_album_id(jsonObjectAlbum.getString("gma_album_id"));
                    tempGoogleMusicAlbumItem.setGma_album_title(jsonObjectAlbum.getString("gma_album_title"));
                    tempGoogleMusicAlbumItem.setGma_album_image_url(jsonObjectAlbum.getString("gma_album_image_url"));
                    tempGoogleMusicAlbumItem.setGma_album_artist_name(jsonObjectAlbum.getString("gma_artist_name"));
                    tempGoogleMusicAlbumItem.setGma_album_price(jsonObjectAlbum.getDouble("gma_album_price"));

                    //newsItemList.add(tempGoogleMusicAlbumItem);

                    JSONArray jsonArrayAlbum = jsonObjectMusicItem.getJSONArray("album_data");

                    List<GoogleMusicSongItem> embeddedGMSongItems = new ArrayList<GoogleMusicSongItem>();

                    for (int j = 0; j < jsonArrayAlbum.length(); j++) {

                        JSONObject jsonObjectSongItem = jsonArrayAlbum.getJSONObject(j);
                        GoogleMusicSongItem tempGoogleMusicSongItem = new GoogleMusicSongItem();

                        tempGoogleMusicSongItem.setGs_song_id(jsonObjectSongItem.getString("gs_song_id"));
                        tempGoogleMusicSongItem.setGs_song_title(jsonObjectSongItem.getString("gs_song_title"));
                        tempGoogleMusicSongItem.setGs_song_price(jsonObjectSongItem.getDouble("gs_song_price"));

                        embeddedGMSongItems.add(tempGoogleMusicSongItem);
                    }

                    tempGoogleMusicAlbumItem.setGoogleMusicSongItemList(embeddedGMSongItems);
                    googleMusicItemList.add(tempGoogleMusicAlbumItem);

                    System.out.println("Music JSON ADD==>");

                }//end for
                for (GoogleMusicAlbumItem temp : googleMusicItemList) {
                    System.out.println(temp.getGma_album_id());
                    System.out.println("xxxxxHERE");
                    for (GoogleMusicSongItem songtemp : temp.getGoogleMusicSongItemList()) {
                        System.out.println(String.format("song id==>%s", songtemp.getGs_song_id()));
                    }
                    System.out.println("\n");
                }


            } catch (JSONException je) {
                System.out.println("MUSIC JSON EXCEPTION==>" + je);
            }

        }

    }

    public void makeMusicExpandableListViews(){
/*
        // Parent layout
        LinearLayout parentLayout = (LinearLayout)findViewById(R.id.expandable_layout);

        // Layout inflater
        LayoutInflater layoutInflater = getLayoutInflater();
        View view;

        view = layoutInflater.inflate(R.layout.expandable, parentLayout, false);

        //expListViewAlbum = (ExpandableListView) findViewById(R.id.expandableListView_songs);
        expListViewAlbum = (ExpandableListView) view.findViewById(R.id.expandableListView_songs);
   */

        //populate group and child maps
        System.out.println("before for loop");

        int albumTag = 0;

        for (GoogleMusicAlbumItem albumtemp : googleMusicItemList) {
            Map<String, String> tempMapAlbum = new HashMap<String, String>();

            List<Map<String, String>> tempGroupAlbumMapList = new ArrayList<Map<String,String>>();
            List<Map<String, String>> tempSongMapList = new ArrayList<Map<String,String>>();
            List<List<Map<String, String>>> tempChildAlbumSongMapList = new ArrayList<List<Map<String,String>>>();



            //tempMapAlbum.put("album", albumtemp.getGma_album_title());

            tempMapAlbum.put(albumtemp.getGma_album_id(), albumtemp.getGma_album_title());
            groupAlbumMapList.add(tempMapAlbum);
            tempGroupAlbumMapList.add(tempMapAlbum);

            //Map<String, String> tempMapSong = new HashMap<String, String>();

            for (GoogleMusicSongItem songtemp : albumtemp.getGoogleMusicSongItemList()) {

                Map<String, String> tempMapSong = new HashMap<String, String>();

                tempMapSong.put("song", songtemp.getGs_song_title());
                System.out.println(String.format("song id==>%sxxx",songtemp.getGs_song_id()));
                System.out.println(String.format("song title==>%sxxx",songtemp.getGs_song_title()));

                tempSongMapList.add(tempMapSong);
            }
            tempChildAlbumSongMapList.add(tempSongMapList);

            //childSongMapList.add(tempMapSong);


            //add imageview and costtextview
            LinearLayout headerInfoParentLayout = (LinearLayout)findViewById(R.id.album_info_layout);

            LayoutInflater headerInfoLayoutInflater = getLayoutInflater();
            View viewHeaderInfo;

            //headerInfoParentLayout.addView();
            //viewHeaderInfo = headerInfoLayoutInflater.inflate(R.layout.album_header_info_layout, headerInfoParentLayout, false);
            viewHeaderInfo = headerInfoLayoutInflater.inflate(R.layout.album_header_imageview, headerInfoParentLayout, false);


            //add  album simpleexpandablelistview

            // Parent layout
            LinearLayout parentLayout = (LinearLayout)findViewById(R.id.expandable_layout);

            // Layout inflater
            LayoutInflater layoutInflater = getLayoutInflater();
            View view;

            view = layoutInflater.inflate(R.layout.expandable, parentLayout, false);

            String imageUrl = String.format("%s",  albumtemp.getGma_album_image_url());

            //System.out.println(String.format("imageUrl ==> %s",imageUrl));

            //new AsyncDownloadHeaderImage(ivTemp).execute(imageUrl.trim());

            //viewHeaderInfo = layoutInflater.inflate(R.layout.album_header_info_layout, headerInfoParentLayout, false);

            ImageView imageViewHeader = (ImageView)viewHeaderInfo.findViewById(R.id.album_info_header_imageview);
            //ImageView imageViewHeader = (ImageView)view.findViewById(R.id.album_info_header_imageview);



            //System.out.println("imageViewHeader==>"+imageViewHeader);


            Picasso.with(getApplicationContext()).load(imageUrl).into(imageViewHeader);


            //headerInfoParentLayout.addView(imageViewHeader);

            //imageViewMap.put(mItemList.getMt_image_id(), ivTemp);

            //expListViewAlbum = (ExpandableListView) findViewById(R.id.expandableListView_songs);
            expListViewAlbum = (ExpandableListView) view.findViewById(R.id.expandableListView_songs);

            SimpleExpandableListAdapter expListAdapterAlbum = getExpListViewAdapter(albumtemp.getGma_album_id(),tempGroupAlbumMapList,tempChildAlbumSongMapList);
            expListViewAlbum.setAdapter(expListAdapterAlbum);
            expListViewAlbum.setTag(albumTag++);
            System.out.println(String.format("albumtag==>%d", albumTag));


            try {
                parentLayout.addView(imageViewHeader);
                parentLayout.addView(expListViewAlbum);
                parentLayout.bringChildToFront(expListViewAlbum);

                System.out.println(String.format("expListViewAlbum Info==>%s\n",expListViewAlbum.toString()));



            }catch(Exception excep){
                System.out.println(excep.toString());

            }


            expListViewAlbum.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    /*
                    Toast.makeText(getApplicationContext(),
                            groupExpYearMapList.get(groupPosition) + " Year Expanded",
                            Toast.LENGTH_SHORT).show();
                    */

                    ExpandableListView eylv = (ExpandableListView) findViewById(R.id.expandableListViewYear);
                    //eylv.setMinimumHeight(700);

                    ViewGroup.LayoutParams expLayoutParams = expListViewAlbum.getLayoutParams();
                    expLayoutParams.height = 1700;
                    expListViewAlbum.setLayoutParams(expLayoutParams);
                    expListViewAlbum.requestLayout();
                }
            });




            expListViewAlbum.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                    ExpandableListView emlva = (ExpandableListView) parent.findViewById(R.id.expandableListView_songs);
                    //emlva.setMinimumHeight(300);
                    //System.out.println(String.format("group clicked...emlva tag==>%s",emlva.getTag().toString()));
                    System.out.println(String.format("group clicked...parent tag==>%d", parent.getTag()));
                    System.out.println(String.format("** parent==>%s", parent.toString()));

emlva.expandGroup(groupPosition);

                    /*** THIS IS A TEST*/
                    ViewGroup.LayoutParams lp = emlva.getLayoutParams();
                    lp.height = 1500; //change to 1/3 of screen
                    emlva.setLayoutParams(lp);
                    emlva.requestLayout();
                    /***/

                    return false;
                }
            });

            expListViewAlbum.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                    expListViewAlbum.collapseGroup(groupPosition);

                    /*** THIS IS A TEST***/
                    ExpandableListView emlva = (ExpandableListView) parent.findViewById(R.id.expandableListView_songs);
                    ViewGroup.LayoutParams lp = emlva.getLayoutParams();
                    lp.height = 100;
                    emlva.setLayoutParams(lp);
                    emlva.requestLayout();
                    /***/

                    return false;
                }

            });
            expListViewAlbum.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                    /**

                    ExpandableListView emlva = (ExpandableListView) findViewById(R.id.expandableListView_songs);
                    //emlva.setMinimumHeight(300);


                    //ViewGroup.LayoutParams layoutParams = expListViewAlbum.getLayoutParams();
                    //layoutParams.height = 2500;

                    //expListViewAlbum.setLayoutParams(layoutParams);
                    //expListViewAlbum.requestLayout();

                    /*** THIS IS A TEST
                    ViewGroup.LayoutParams lp = emlva.getLayoutParams();
                    lp.height = 1000;
                    emlva.setLayoutParams(lp);
                    emlva.requestLayout();
                    /***/

                    System.out.println("groupPosition====>"+groupPosition);

                }
            });



            System.out.println("\n");

            //albumTag++;
        }
        childAlbumSongMapList.add(childSongMapList);
    /*
        try {
            //listAdapter = new ExpandableListAdapter(getApplication().getApplicationContext(), listDataHeader, countryItemList);"
            System.out.println("\nTrying");



            SimpleExpandableListAdapter expListAdapterAlbum =
                    new SimpleExpandableListAdapter(
                            getApplication().getApplicationContext(),
                            groupAlbumMapList,              // Creating group List.
                            R.layout.google_music_header,             // Group item layout XML.
                            //new String[]{"B4vkt6afnzg4qnczdu2stvdki7u","B5bsgvwcvwjibf6iq2neetzhstu","Buhdkonapleftrmbbjfzfa65lvi"},  // the key of group item.
                            new String[]{"B4vkt6afnzg4qnczdu2stvdki7u","B5bsgvwcvwjibf6iq2neetzhstu","Buhdkonapleftrmbbjfzfa65lvi"},  // the key of group item.
                            new int[]{R.id.album_title_textview},    // ID of each group item.-Data under the key goes into this TextView.
                            childAlbumSongMapList,              // childData describes second-level <span id="IL_AD5" class="IL_AD">entries</span>.
                            R.layout.google_music_child,             // Layout for sub-level entries(second level).
                            new String[]{"song"},      // Keys in childData maps to display.
                            new int[]{R.id.album_song_textview}     // Data under the keys above go into these TextViews.
                    );

            expListViewAlbum.setAdapter(expListAdapterAlbum);

            parentLayout.addView(expListViewAlbum);


        } catch (Exception e) {
            System.out.println(String.format("ALBUM LISTADAPTER EX ==>%s", e.toString()));

        }
      */

            //expListView.setAdapter(listAdapter);


    }
    public SimpleExpandableListAdapter getExpListViewAdapter(String albumKey, List<Map<String,String>>grpAlbumMapList, List<List<Map<String, String>>>chldAlbumSongMapList){
        SimpleExpandableListAdapter simpleExpandableListAdapter = null;

        try {
            //listAdapter = new ExpandableListAdapter(getApplication().getApplicationContext(), listDataHeader, countryItemList);"
            System.out.println("\nTrying");

           // SimpleExpandableListAdapter expListAdapterAlbum =
           simpleExpandableListAdapter =
                    new SimpleExpandableListAdapter(
                            getApplication().getApplicationContext(),
                            grpAlbumMapList,              // Creating group List.
                            R.layout.google_music_header,             // Group item layout XML.
                            //new String[]{"B4vkt6afnzg4qnczdu2stvdki7u","B5bsgvwcvwjibf6iq2neetzhstu","Buhdkonapleftrmbbjfzfa65lvi"},  // the key of group item.
                            new String[]{albumKey},  // the key of group item.
                            new int[]{R.id.album_title_textview},    // ID of each group item.-Data under the key goes into this TextView.
                            chldAlbumSongMapList,              // childData describes second-level <span id="IL_AD5" class="IL_AD">entries</span>.
                            R.layout.google_music_child,             // Layout for sub-level entries(second level).
                            new String[]{"song"},      // Keys in childData maps to display.
                            new int[]{R.id.album_song_textview}     // Data under the keys above go into these TextViews.
                    );


        } catch (Exception e) {
            System.out.println(String.format("ALBUM LISTADAPTER EX ==>%s", e.toString()));
        }


        return simpleExpandableListAdapter;

    }



}
