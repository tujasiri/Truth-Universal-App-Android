package com.truthuniversal.tujasiri.truthuniversalapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.logging.Logger;

/**
 * Created by tujasiri on 7/1/2016.
 */
@SuppressWarnings("NewApi")
public class NewsFragment extends Fragment {

    private List<NewsItem> newsItemList = new ArrayList<NewsItem>();

    View myView;

    public final static String EXTRA_ARTICLE = "com.truthuniversal.tujasiri.ARTICLE";
    public final static String EXTRA_ARTICLE_TITLE = "com.truthuniversal.tujasiri.ARTICLE_TITLE";

    Logger logger = Logger.getLogger("newsLogger");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.news_layout,container,false);

        new AsyncNewsTask().execute("http://truthuniversal.com/news");

        return myView;
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
        ListView newslist = (ListView) myView.findViewById(R.id.news_layout_listview);
        newslist.setAdapter(newsItemArrayAdapter);

        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),NewsFragment.class);
                String newsArticle = newsItemList.get(position).getNews_article();
                String newsArticleTitle = newsItemList.get(position).getNews_title();

                ImageView ivNews = (ImageView) view.findViewById(R.id.news_list_imageview);
                ivNews.buildDrawingCache();

                Bitmap imageViewImage = ivNews.getDrawingCache();
                Bitmap imageViewNews = Bitmap.createBitmap(imageViewImage);

                Bundle extras = new Bundle();

                extras.putParcelable("imagebitmap", imageViewNews);
                extras.putString(EXTRA_ARTICLE,newsArticle);
                extras.putString(EXTRA_ARTICLE_TITLE,newsArticleTitle);

                intent.putExtras(extras);

                logger.info("Clicked News Item logger");

                Fragment fragment = new NewsDetailFragment();

                fragment.setArguments(extras);

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        });
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
            //super(NewsActivity.this, R.layout.news_list_view, newsItemList);
            super(getActivity(), R.layout.news_list_view, newsItemList);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View newsItemView = convertView;

            NewsViewHolder viewHolder;


            if(newsItemView == null){

                newsItemView = getActivity().getLayoutInflater().inflate(R.layout.news_list_view, parent,false);
                //newsItemView = getLayoutInflater().inflate(R.layout.news_list_view, parent,false);

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

            //Picasso.with(getApplicationContext()).load(imageUrl).into(viewHolder.newsImageView);
            Picasso.with(getActivity()).load(imageUrl).into(viewHolder.newsImageView);

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

}
