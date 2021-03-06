package com.truthuniversal.tujasiri.truthuniversalapp;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
import java.util.List;

/**
 * Created by tujasiri on 12/9/15.
 */
public class NewsActivity extends AppCompatActivity{
//public class NewsActivity extends FragmentActivity{

    private List<NewsItem> newsItemList = new ArrayList<NewsItem>();

    private DrawerLayout mDrawerLayout;

    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.news_layout);

        new AsyncNewsTask().execute("http://truthuniversal.com/news");
        /***Drawer **/
        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
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
        //getSupportActionBar().setHomeAsUpIndicator(tb);


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
        /***/

    }



    public class AsyncNewsTask extends AsyncTask<String,Void,Boolean> {

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

                populateNewsItemList(jArray);

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

            System.out.println(String.format("here in news async task"));
            populateListView();

        }
    }

    private void populateListView() {
        ArrayAdapter<NewsItem> newsItemArrayAdapter = new NewsListAdapter();
        ListView newslist = (ListView) findViewById(R.id.news_layout_listview);
        newslist.setAdapter(newsItemArrayAdapter);
    }

    private void populateNewsItemList(JSONArray jsonNewsArray) {
        //Map<String,ImageView> imageViewMap = new HashMap<String,ImageView>();

        //JSONObject jsonObject = (JSONObject)jsonMerchObject;

        //merchItemList = ItemSingleton.getInstance().getArrayList();

        try {
            //JSONArray jsonArray = jsonObject.getJSONArray("");

            for(int i=0;i<jsonNewsArray.length();i++) {
                ImageView ivTemp = null;

                NewsItem tempNewsItem = new NewsItem();

                JSONObject jsonObjectNewsItem =  jsonNewsArray.getJSONObject(i);

                tempNewsItem.setNews_article(jsonObjectNewsItem.getString("news_article"));
                tempNewsItem.setNews_title(jsonObjectNewsItem.getString("news_title"));
                tempNewsItem.setNews_subtitle(jsonObjectNewsItem.getString("news_subtitle"));
                tempNewsItem.setNews_image_link(jsonObjectNewsItem.getString("news_image_link"));



                newsItemList.add(tempNewsItem);

                System.out.println("NEWS JSON ADD==>");



            }//end for
        }catch (JSONException je){
            System.out.println("NEWS JSON EXCEPTION==>"+je);
        }

    }

    private class NewsListAdapter extends ArrayAdapter<NewsItem>
    {
        public NewsListAdapter() {
            super(NewsActivity.this, R.layout.news_list_view, newsItemList);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View newsItemView = convertView;

            NewsViewHolder viewHolder;


            if(newsItemView == null){

                newsItemView = getLayoutInflater().inflate(R.layout.news_list_view, parent,false);

                viewHolder = new NewsViewHolder();

                viewHolder.newsImageView = (ImageView) newsItemView.findViewById(R.id.news_list_imageview);
                viewHolder.newsTitleTextView = (TextView) newsItemView.findViewById(R.id.news_title_textview);
                viewHolder.newsSubtitleTextView = (TextView) newsItemView.findViewById(R.id.news_subtitle_textview);

                newsItemView.setTag(viewHolder);

            }
            else{
                viewHolder = (NewsViewHolder)newsItemView.getTag();

            }



            NewsItem currentNewsItem = newsItemList.get(position);

            String imageUrl = currentNewsItem.getNews_image_link();

            Picasso.with(getApplicationContext()).load(imageUrl).into(viewHolder.newsImageView);

            viewHolder.newsTitleTextView.setText(currentNewsItem.getNews_title());
            viewHolder.newsSubtitleTextView.setText(currentNewsItem.getNews_subtitle());

            System.out.println(String.format("NEWS TITLE==>%s",currentNewsItem.getNews_title()));


            return newsItemView;

        }
    }//end MyListAdapter

    static class NewsViewHolder{
        public ImageView newsImageView;
        public TextView newsTitleTextView;
        public TextView newsSubtitleTextView;
    }

}//end NewsActivity
