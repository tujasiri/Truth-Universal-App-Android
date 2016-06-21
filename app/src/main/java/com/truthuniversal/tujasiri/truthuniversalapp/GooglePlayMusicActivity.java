package com.truthuniversal.tujasiri.truthuniversalapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
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


        expListViewAlbum = (ExpandableListView) findViewById(R.id.expandableListView_songs);

        //populate group and child maps

        for (GoogleMusicAlbumItem albumtemp : googleMusicItemList) {
            Map<String, String> tempMapAlbum = new HashMap<String, String>();
            //tempMapAlbum.put("album", albumtemp.getGma_album_title());

            tempMapAlbum.put(albumtemp.getGma_album_id(), albumtemp.getGma_album_title());
            groupAlbumMapList.add(tempMapAlbum);

            Map<String, String> tempMapSong = new HashMap<String, String>();

            for (GoogleMusicSongItem songtemp : albumtemp.getGoogleMusicSongItemList()) {
                tempMapSong.put("song", songtemp.getGs_song_title());
                //System.out.println(String.format("song id==>%s",songtemp.getGs_song_id()));
            }

            childSongMapList.add(tempMapSong);

            System.out.println("\n");

        }
        childAlbumSongMapList.add(childSongMapList);

        try {
            //listAdapter = new ExpandableListAdapter(getApplication().getApplicationContext(), listDataHeader, countryItemList);

            SimpleExpandableListAdapter expListAdapterAlbum =
                    new SimpleExpandableListAdapter(
                            getApplication().getApplicationContext(),
                            groupAlbumMapList,              // Creating group List.
                            R.layout.google_music_header,             // Group item layout XML.
                            new String[]{"B4vkt6afnzg4qnczdu2stvdki7u","B5bsgvwcvwjibf6iq2neetzhstu","Buhdkonapleftrmbbjfzfa65lvi"},  // the key of group item.
                            new int[]{R.id.album_title_textview},    // ID of each group item.-Data under the key goes into this TextView.
                            childAlbumSongMapList,              // childData describes second-level <span id="IL_AD5" class="IL_AD">entries</span>.
                            R.layout.google_music_child,             // Layout for sub-level entries(second level).
                            new String[]{"song"},      // Keys in childData maps to display.
                            new int[]{R.id.album_song_textview}     // Data under the keys above go into these TextViews.
                    );

            expListViewAlbum.setAdapter(expListAdapterAlbum);

        } catch (Exception e) {
            System.out.println(String.format("ALBUM LISTADAPTER EX ==>%s", e.toString()));

        }


        // setting list adapter
        //expListView.setAdapter(listAdapter);


        expListViewAlbum.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                expListViewAlbum.collapseGroup(groupPosition);

                return false;
            }

        });


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


}
