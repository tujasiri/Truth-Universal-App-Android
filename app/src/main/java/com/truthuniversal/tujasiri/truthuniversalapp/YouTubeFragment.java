package com.truthuniversal.tujasiri.truthuniversalapp;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by tujasiri on 12/21/15.
 */
public class YouTubeFragment extends Fragment {
    private List<YouTubeItem> youTubeItemList = new ArrayList<YouTubeItem>();

    public final static String VIDEO_ID = "com.truthuniversal.tujasiri.testapp.VIDEOID";

    View ytView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ytView = inflater.inflate(R.layout.youtube_layout,container,false);

        new AsyncYouTubeItemTask().execute("http://truthuniversal.com/ios/youtubejson.php");

        return ytView;
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_layout);

        new AsyncYouTubeItemTask().execute("http://truthuniversal.com/ios/youtubejson.php");


    }
    */

    public class AsyncYouTubeItemTask extends AsyncTask<String,Void,Boolean> {

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

                //System.out.println("Response : " + response);

                JSONObject jObj = new JSONObject(response.toString());

                //JSONArray jArray  = new JSONArray(response.toString());
                //System.out.println(String.format("actual JSON array ==> %s", jObj.get("items").toString()));

                JSONArray jArray  = new JSONArray(jObj.get("items").toString());

                populateYouTubeItemList(jArray);

/*
                for (int i=0;i< jArray.length(); i++) {
                    System.out.println(String.format("YouTube Array Object %d ==> %s", i, jArray.get(i).toString()));
                }
*/


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

            System.out.println(String.format("here in youtube async task"));

            populateListView();


        }
    }

    private void populateYouTubeItemList(JSONArray jsonYouTubeArray) {


        try {
            for (int i = 0; i < jsonYouTubeArray.length(); i++) {

                YouTubeItem tempYouTubeItem = new YouTubeItem();

                JSONObject tempYouTubeJSONObj = jsonYouTubeArray.getJSONObject(i);

                tempYouTubeItem.setVideo_description(tempYouTubeJSONObj.getJSONObject("snippet").getString("description"));
                tempYouTubeItem.setVideo_id(tempYouTubeJSONObj.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId"));
                tempYouTubeItem.setVideo_thumbnail_url(tempYouTubeJSONObj.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url"));
                tempYouTubeItem.setVideo_title(tempYouTubeJSONObj.getJSONObject("snippet").getString("title"));

                System.out.println(String.format("\n\nVideo Title ==> %s\n" +
                        "Video Id ==> %s\n" +
                        "Video Thumbnail URL ==> %s\n" +
                        "Video Description ==> %s\n\n", tempYouTubeItem.getVideo_title(), tempYouTubeItem.getVideo_id(), tempYouTubeItem.getVideo_thumbnail_url(), tempYouTubeItem.getVideo_description()));

                youTubeItemList.add(tempYouTubeItem);

            }
        }catch(Exception e){
            System.out.println(String.format("YouTube JSONObject EXCEPTION==>%s",e.toString()));

        }

    }//end PoplulateYouTubeItemList

    private void populateListView() {
        ArrayAdapter<YouTubeItem> youTubeItemArrayAdapter = new YouTubeListAdapter();
        ListView youtubelist = (ListView) ytView.findViewById(R.id.youtube_listview);
        youtubelist.setAdapter(youTubeItemArrayAdapter);

        youtubelist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Intent intent = new Intent(getApplicationContext(),YouTubePlayerUtil.class);
                Intent intent = new Intent(getActivity(),YouTubePlayerUtil.class);
                intent.putExtra(VIDEO_ID,youTubeItemList.get(position).getVideo_id());

                startActivity(intent);



            }
        });
    }

    private class YouTubeListAdapter extends ArrayAdapter<YouTubeItem>
    {
        public YouTubeListAdapter() {
            //super(YouTubeFragment.this, R.layout.youtube_list_view, youTubeItemList);
            super(getActivity(), R.layout.youtube_list_view, youTubeItemList);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View youTubeItemView = convertView;

            YouTubeViewHolder viewHolder;


            if(youTubeItemView == null){

                youTubeItemView = getActivity().getLayoutInflater().inflate(R.layout.youtube_list_view, parent,false);

                viewHolder = new YouTubeViewHolder();

                viewHolder.youtubeImageView = (ImageView) youTubeItemView.findViewById(R.id.youtube_list_imageview);
                viewHolder.youtubeTitleTextView = (TextView) youTubeItemView.findViewById(R.id.youtube_title_textview);

                youTubeItemView.setTag(viewHolder);

            }
            else{
                viewHolder = (YouTubeViewHolder)youTubeItemView.getTag();

            }


            YouTubeItem currentYouTubeItem = youTubeItemList.get(position);

            String videoImageUrl = currentYouTubeItem.getVideo_thumbnail_url();

            //Picasso.with(getApplicationContext()).load(videoImageUrl).into(viewHolder.youtubeImageView);
            Picasso.with(getActivity()).load(videoImageUrl).into(viewHolder.youtubeImageView);

            viewHolder.youtubeTitleTextView.setText(currentYouTubeItem.getVideo_title());
            //viewHolder.newsSubtitleTextView.setText(currentNewsItem.getNews_subtitle());

            return youTubeItemView;

        }
    }//end MyListAdapter

    static class YouTubeViewHolder{
        public ImageView youtubeImageView;
        public TextView youtubeTitleTextView;
        //public TextView newsSubtitleTextView;
    }



}//end YoutubeActivity

