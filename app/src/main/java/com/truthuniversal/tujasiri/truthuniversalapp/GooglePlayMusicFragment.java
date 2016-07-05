package com.truthuniversal.tujasiri.truthuniversalapp;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;

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
@SuppressWarnings("NewApi")
public class GooglePlayMusicFragment extends Fragment{

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

    View gpmView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        gpmView = inflater.inflate(R.layout.google_album_detail_layout,container,false);

        new AsyncGooglePlayMusicTask().execute("http://truthuniversal.com/googlemusicjson");

        return gpmView;
    }



/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.google_album_detail_layout);

        new AsyncGooglePlayMusicTask().execute("http://truthuniversal.com/googlemusicjson");



        //listviewAdapter


    }
*/


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
            //LinearLayout headerInfoParentLayout = (LinearLayout)gpmView.findViewById(R.id.album_info_layout);

            LinearLayout headerInfoParentLayout = (LinearLayout)gpmView.findViewById(R.id.album_info_layout);



            LayoutInflater headerInfoLayoutInflater = getActivity().getLayoutInflater();

            //View viewHeaderInfo;
            View viewHeaderInfo = new View(getActivity());

            if(viewHeaderInfo == null)
                System.out.println("null viewHeaderInfo");

            //headerInfoParentLayout.addView();
            //viewHeaderInfo = headerInfoLayoutInflater.inflate(R.layout.album_header_info_layout, headerInfoParentLayout, false);

            viewHeaderInfo = headerInfoLayoutInflater.inflate(R.layout.album_header_imageview, headerInfoParentLayout, false);


            //add  album simpleexpandablelistview

            // Parent layout


            LinearLayout parentLayout = (LinearLayout)gpmView.findViewById(R.id.expandable_layout);


            //LinearLayout parentLayout = (LinearLayout)getActivity().findViewById(R.id.expandable_layout);

            // Layout inflater
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            //View view;
            View view = new View(getActivity());

            if(view == null)
                System.out.println("null view");

            view = layoutInflater.inflate(R.layout.expandable, parentLayout, false);

            String imageUrl = String.format("%s",  albumtemp.getGma_album_image_url());

            //System.out.println(String.format("imageUrl ==> %s",imageUrl));

            //new AsyncDownloadHeaderImage(ivTemp).execute(imageUrl.trim());

            //viewHeaderInfo = layoutInflater.inflate(R.layout.album_header_info_layout, headerInfoParentLayout, false);

            ImageView imageViewHeader = (ImageView)viewHeaderInfo.findViewById(R.id.album_info_header_imageview);
            //ImageView imageViewHeader = (ImageView)view.findViewById(R.id.album_info_header_imageview);



            //System.out.println("imageViewHeader==>"+imageViewHeader);


            //Picasso.with(getApplicationContext()).load(imageUrl).into(imageViewHeader);
            Picasso.with(getActivity()).load(imageUrl).into(imageViewHeader);


            //headerInfoParentLayout.addView(imageViewHeader);

            //imageViewMap.put(mItemList.getMt_image_id(), ivTemp);

            //expListViewAlbum = (ExpandableListView) findViewById(R.id.expandableListView_songs);
            expListViewAlbum = (ExpandableListView) view.findViewById(R.id.expandableListView_songs);

            SimpleExpandableListAdapter expListAdapterAlbum = getExpListViewAdapter(albumtemp.getGma_album_id(),tempGroupAlbumMapList,tempChildAlbumSongMapList);
            expListViewAlbum.setAdapter(expListAdapterAlbum);

            expListViewAlbum.setTag(albumTag);
            imageViewHeader.setTag(albumTag++);

            if(parentLayout == null)
                System.out.println(String.format("parentLayout==>%s", parentLayout.toString()));

            try {
                System.out.println(String.format("parent layout==>%s", parentLayout.toString()));

                parentLayout.addView(imageViewHeader);
                parentLayout.addView(expListViewAlbum);
                parentLayout.bringChildToFront(expListViewAlbum);

                System.out.println(String.format("expListViewAlbum Info==>%s\n",expListViewAlbum.toString()));



            }catch(Exception excep){
                System.out.println(excep.toString());

            }

            imageViewHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int albumIndex = (int)v.getTag();
                    GoogleMusicAlbumItem gmai = googleMusicItemList.get(albumIndex);

                    launchGoogleMusicIntent(buildMarketUrl(gmai.getGma_album_id(), "album"));
                }
            });


            expListViewAlbum.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                }
            });



            expListViewAlbum.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                    ExpandableListView emlva = (ExpandableListView) parent.findViewById(R.id.expandableListView_songs);

                    ViewGroup.LayoutParams lp = emlva.getLayoutParams();

                    if (parent.isGroupExpanded(groupPosition)) {
                        lp.height = 100;
                    } else {
                        lp.height = 1700; //change to 1/3 of screen
                    }

                    emlva.setLayoutParams(lp);
                    emlva.requestLayout();

                    return false;
                }
            });

            expListViewAlbum.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {

                }
            });

            expListViewAlbum.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                    ExpandableListView emlva = (ExpandableListView) parent.findViewById(R.id.expandableListView_songs);
                    ViewGroup.LayoutParams lp = emlva.getLayoutParams();


                    //System.out.println("Selected Id ==>"+emlva.getSelectedId()+" SelectedPosition==>"+emlva.getSelectedPosition());
                    //System.out.println(String.format("Selected Id ==> %d \nSelected Position==>%d",parent.getSelectedId(),parent.getSelectedPosition()));

                    System.out.println("Tag==>" + parent.getTag());
                    System.out.println("ChildPositon==>" + childPosition);
                    //googleMusicItemList.get()

                    /******Get Album and Song Info from MasterList***/
                    int albumIndex = (int) parent.getTag();
                    int songIndex = childPosition;

                    GoogleMusicAlbumItem gmai = googleMusicItemList.get(albumIndex);
                    GoogleMusicSongItem gmsi = gmai.getGoogleMusicSongItemList().get(songIndex);

                    System.out.println(String.format("SONG ==>%s", gmsi.getGs_song_title()));
                    System.out.println(String.format("SONG ID ==>%s", gmsi.getGs_song_id()));

                    launchGoogleMusicIntent(buildMarketUrl(gmai.getGma_album_id(), "album"));

                    //launchGoogleMusicIntent(buildMarketUrl(gmsi.getGs_song_id(),"song"));


                    /******/


                    return false;
                }

            });
            expListViewAlbum.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
                }
            });

        }
        childAlbumSongMapList.add(childSongMapList);


    }
    public SimpleExpandableListAdapter getExpListViewAdapter(String albumKey, List<Map<String,String>>grpAlbumMapList, List<List<Map<String, String>>>chldAlbumSongMapList){
        SimpleExpandableListAdapter simpleExpandableListAdapter = null;

        try {
            //listAdapter = new ExpandableListAdapter(getApplication().getApplicationContext(), listDataHeader, countryItemList);"
            System.out.println("\nTrying");

           // SimpleExpandableListAdapter expListAdapterAlbum =
           simpleExpandableListAdapter =
                    new SimpleExpandableListAdapter(
                            /*getApplication().getApplicationContext(),*/
                            getActivity(),
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

    String buildMarketUrl(String mediaId, String mediaType){

        //String marketUrl = "market://details?id="+mediaId;

        String marketUrl = "https://play.google.com/store/music/album/?id="+mediaId;
        //String marketUrl = "market://play.google.com/store/music/album/?id="+mediaId;


        System.out.println("marketUrl ==>"+marketUrl);
        return marketUrl.trim();
    }

    //public void launchGoogleMusicIntent(String uri_id){

    public void launchGoogleMusicIntent(String market_uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setData(Uri.parse(String.format("market://details?id=%s",uri_id).trim()));
        intent.setData(Uri.parse(market_uri.trim()));
        startActivity(intent);

    }



}
