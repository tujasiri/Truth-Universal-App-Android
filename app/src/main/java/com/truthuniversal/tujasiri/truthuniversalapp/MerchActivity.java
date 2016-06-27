package com.truthuniversal.tujasiri.truthuniversalapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.squareup.picasso.*;

public class MerchActivity extends AppCompatActivity {

    CheckoutCart checkoutCart = new CheckoutCart();


    //ItemSingleton itemSingleton = new ItemSingleton();

    //CheckoutCart checkoutCart;

    //checkoutCart.

    public final static String EXTRA_MESSAGE = "com.truthuniversal.tujasiri.testapp.MESSAGE";
    public final static String ITEM_LONG_DESC = "com.truthuniversal.tujasiri.testapp.ITEMLONGDESC";
    public final static String ITEM_COST = "com.truthuniversal.tujasiri.testapp.ITEMCOST";
    public final static String ITEM_IMAGE = "com.truthuniversal.tujasiri.testapp.ITEMIMAGE";

    public final static String ITEM_LIST_POSITION = "com.truthuniversal.tujasiri.testapp.ITEMLISTPOSITION";





    private List<MerchItem> merchItemList = new ArrayList<MerchItem>();
    private Map<String,ImageView> imageViewMap = new HashMap<String,ImageView>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.merch_layout);

        new AsyncMerchItemTask().execute("http://truthuniversal.com/merchjson");
        checkoutCart = CartSingleton.getInstance().getCheckoutCart();

        //populateMerchItemList();
        //populateListView();

        //populateImageViewList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_test,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();

        System.out.println(String.format("id ==>%d, action_settings ==> %d, R.it.cut_icon==>%d",id, R.id.action_settings,R.id.shopping_cart));

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


        }//end switch(id)

        return true;

    }


    public class AsyncMerchItemTask extends AsyncTask<String,Void,Boolean>{

        @Override
        //protected Boolean doInBackground(String... params) {
        protected Boolean doInBackground(String... params) {


                //CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();

            //String url ="http://truthuniversal.com/merchjson";

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

                JSONArray jArray  = new JSONArray(response.toString());

                populateMerchItemList(jArray);

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

            System.out.println(String.format("here"));
            populateListView();
            //popluateImageViewList();



        }
    }//end AsychMerchItemTask

    public class AsyncDownloadImage extends AsyncTask<String,Void,Bitmap>{

        ImageView bmImage;

        //Bitmap bmImage;


        public AsyncDownloadImage(ImageView bmImage){
        //public AsyncDownloadImage(Bitmap bmImage){


                this.bmImage = bmImage;
        }


        @Override
        protected Bitmap doInBackground(String... urls) {

            String urldisplay = null;
            urldisplay = urls[0];


            //Bitmap merchViewBitMap = null;
            Bitmap merchViewBitMap = Bitmap.createBitmap(90,90,Bitmap.Config.ARGB_8888);


            try{

                InputStream in = new URL(urldisplay).openStream();
                merchViewBitMap = BitmapFactory.decodeStream(in);

                in.close();

            }catch(Exception e){
                System.out.println(String.format("Image Download Exception ==> %s", e.toString()));

            }

            return merchViewBitMap;

            //return true;
        }


        @Override
        protected void onPostExecute(Bitmap result) {

            super.onPostExecute(result);

            System.out.println(String.format("here after downloading...status is ==>%s", this.getStatus().toString()));
            System.out.println(String.format("here after downloading...merch count is ==>%d",merchItemList.size()));


            //populateListView();
            bmImage.setImageBitmap(result);

            //bmImage = result;

        }
    }

        //private void populateMerchItemList(Object jsonMerchObject) {
    private void populateMerchItemList(JSONArray jsonMerchArray) {
        //Map<String,ImageView> imageViewMap = new HashMap<String,ImageView>();

            //JSONObject jsonObject = (JSONObject)jsonMerchObject;

            merchItemList = ItemSingleton.getInstance().getArrayList();

        try {
            //JSONArray jsonArray = jsonObject.getJSONArray("");

            for(int i=0;i<jsonMerchArray.length();i++) {
                ImageView ivTemp = null;

                MerchItem tempMerchItem = new MerchItem();

                JSONObject jsonObjectMerchItem =  jsonMerchArray.getJSONObject(i);

                tempMerchItem.setMt_image_id(jsonObjectMerchItem.getString("mt_image_id"));

                System.out.println(String.format("mt_image_id ==> %s", tempMerchItem.getMt_image_id()));

                tempMerchItem.setMt_item_desc_long(jsonObjectMerchItem.getString("mt_item_desc_long"));
                tempMerchItem.setMt_item_desc_short(jsonObjectMerchItem.getString("mt_item_desc_short"));
                tempMerchItem.setMt_item_link(jsonObjectMerchItem.getString("mt_item_link"));
                tempMerchItem.setMt_item_num(jsonObjectMerchItem.getInt("mt_item_num"));
                tempMerchItem.setMt_item_price(jsonObjectMerchItem.getDouble("mt_item_price"));
                tempMerchItem.setMt_stock_qty(jsonObjectMerchItem.getInt("mt_stock_qty"));


                //ImageView tmpImageView = null;

                Bitmap tmpBitmap = Bitmap.createBitmap(90,90,Bitmap.Config.ARGB_8888);


                //String imageUrl = String.format("https://www.2checkout.com/va/public/render_image?image_id=%s", tempMerchItem.getMt_image_id());
                ///new AsyncDownloadImage(ivTemp).execute(imageUrl);

                //imageViewMap.put(tempMerchItem.getMt_image_id(),ivTemp);

                //tempMerchItem.setMt_imageview(tmpImageView);

                //tempMerchItem.
                //tempMerchItem.setMt_bitmap(tmpBitmap);

                merchItemList.add(tempMerchItem);

                /*
                tempMerchItem = new MerchItem();

                tempMerchItem.setMt_image_id("yyyy");
                tempMerchItem.setMt_item_desc_long("long description2");
                tempMerchItem.setMt_item_desc_short("short description2");
                tempMerchItem.setMt_item_link("http://truthuniversal.com/images/truthlogo.jpg");
                tempMerchItem.setMt_item_num(1000);
                tempMerchItem.setMt_item_price(10.00);
                tempMerchItem.setMt_stock_qty(0);

                merchItemList.add(tempMerchItem);
                */

            }//end for
        }catch (JSONException je){
            System.out.println("MERCH JSON EXCEPTION==>"+je);
        }

    }


    private void populateListView() {
        ArrayAdapter<MerchItem> merchItemArrayAdapter = new MyListAdapter();
        ListView list = (ListView)findViewById(R.id.merchListView);
        list.setAdapter(merchItemArrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String passItem = merchItemList.get(position).getMt_item_desc_short();
                //Toast.makeText(getApplication(),String.format("view==>%s, position ==> %s, id==> %s",view.toString(),position,id),Toast.LENGTH_SHORT).show();
                System.out.println(String.format("view==>%s, position ==> %s, id==> %s, passItem ==> %s",view.toString(),position,id,passItem));


                //Build Intent
                Intent intent = new Intent(getApplicationContext(),MerchItemDescriptionActivity.class);


                TextView tv1 = (TextView) view.findViewById(R.id.textView2);
                TextView tv2 = (TextView) view.findViewById(R.id.costView);
                ImageView iv1 = (ImageView) view.findViewById(R.id.imageView);


                System.out.println(String.format("Imageview id is ==> %d",R.id.imageView));

                iv1.buildDrawingCache();
                Bitmap imageViewImage = iv1.getDrawingCache();

                Bundle extras = new Bundle();
                extras.putParcelable("imagebitmap", imageViewImage);
                //extras.putParcelable("theView", view);

                intent.putExtras(extras);

                String message = tv1.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);


                String costViewString = tv2.getText().toString();
                intent.putExtra(ITEM_COST,costViewString);

                intent.putExtra(ITEM_LIST_POSITION,position);


                startActivity(intent);

            }
        });
    }

    private void popluateImageViewList(){

        for (MerchItem mItemList:merchItemList){
            ImageView ivTemp=null;


            String imageUrl = String.format("https://www.2checkout.com/va/public/render_image?image_id=%s", mItemList.getMt_image_id());
            new AsyncDownloadImage(ivTemp).execute(imageUrl);

            imageViewMap.put(mItemList.getMt_image_id(), ivTemp);

        }
    }

    private class MyListAdapter extends ArrayAdapter<MerchItem>
    {
        public MyListAdapter() {
            super(MerchActivity.this, R.layout.merch_item_list_view, merchItemList);

        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View merchItemView = convertView;
            ImageView merchImageView = null;

            ViewHolder viewHolder;

            if(merchItemView == null){

                merchItemView = getLayoutInflater().inflate(R.layout.merch_item_list_view, parent,false);

                viewHolder = new ViewHolder();

                viewHolder.imageview = (ImageView) merchItemView.findViewById(R.id.imageView);
                viewHolder.textView2 = (TextView) merchItemView.findViewById(R.id.textView2);
                viewHolder.textView3 = (TextView) merchItemView.findViewById(R.id.textView3);
                viewHolder.costView = (TextView) merchItemView.findViewById(R.id.costView);

                merchItemView.setTag(viewHolder);

            }
            else{
                viewHolder = (ViewHolder)merchItemView.getTag();

            }

            //Find Merch Item
            //System.out.println(String.format("Position == %d",position));

            //System.out.println(String.format("view==>%s, position ==> %s, id==> %d",viewHolder.textView2.getRootView().toString(),position,viewHolder.textView2.getId()));


            MerchItem currentMerchItem = merchItemList.get(position);


            String imageUrl = String.format("https://www.2checkout.com/va/public/render_image?image_id=%s", currentMerchItem.getMt_image_id());


            //new AsyncDownloadImage((ImageView)merchItemView.findViewById(R.id.imageView)).execute(String.format("https://www.2checkout.com/va/public/render_image?image_id=%s",currentMerchItem.getMt_image_id()));

            //AsyncTask<String, Void, Bitmap> dt = new AsyncDownloadImage((ImageView) merchItemView.findViewById(R.id.imageView)).execute(imageUrl);

            //new AsyncDownloadImage((ImageView) merchItemView.findViewById(R.id.imageView)).execute(imageUrl);





            Picasso.with(getApplicationContext()).load(imageUrl).into(viewHolder.imageview);



            //viewHolder.imageview = (ImageView) merchItemView.findViewById(R.id.imageView);
            //viewHolder.imageview = imageViewMap.get(currentMerchItem.getMt_image_id());
            viewHolder.textView2.setText(currentMerchItem.getMt_item_desc_short());
            viewHolder.textView3.setText(currentMerchItem.getMt_item_desc_short());
            viewHolder.costView.setText(String.format("%.2f",currentMerchItem.getMt_item_price()));

            /*
            TextView merchItemDescLong = (TextView) merchItemView.findViewById(R.id.textView2);
            merchItemDescLong.setText(currentMerchItem.getMt_item_desc_long());

            TextView merchItemDescShort = (TextView) merchItemView.findViewById(R.id.textView3);
            merchItemDescShort.setText(currentMerchItem.getMt_item_desc_short());



            ImageView merchImageViewFinal = (ImageView) merchItemView.findViewById(R.id.imageView);

            Bitmap bitmaptmp = currentMerchItem.getMt_bitmap();

            merchImageViewFinal.setImageBitmap(currentMerchItem.getMt_bitmap());
            */
            //Bitmap tmpImgView = currentMerchItem.getMt_imageview();
            //merchImageViewFinal.setImageBitmap(currentMerchItem.getMt_imageview());
            //dt.cancel(true);
            //System.out.println(String.format("download task status ==>%s",dt.getStatus().toString()));

            //Fill the View


            //return super.getView(position, convertView, parent);
            return merchItemView;

        }
    }//end MyListAdapter

    static class ViewHolder{
        public ImageView imageview;
        public TextView textView2;
        public TextView textView3;
        public TextView costView;

    }

    public Boolean alreadyDownloaded(String item_id){

        if(imageViewMap.get(item_id)==null) {
            System.out.println(String.format("ITEM ID NULL CHECK ==>%s",item_id));
            return false;
        }

        System.out.println(String.format("ITEM ID RETURNING TRUE -- ID ==>%s",item_id));


        return true;
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}//end Class
