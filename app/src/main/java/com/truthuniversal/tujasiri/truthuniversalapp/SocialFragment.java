package com.truthuniversal.tujasiri.truthuniversalapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

//import android.support.v4.app.Fragment;

/**
 * Created by tujasiri on 7/1/2016.
 */
@SuppressWarnings("NewApi")
public class SocialFragment extends Fragment {

    private List<NewsItem> newsItemList = new ArrayList<NewsItem>();

    View myView;






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.social_layout,container,false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int viewHeight = displayMetrics.heightPixels;
        int viewWidth = displayMetrics.widthPixels;


        final ImageView social_iv = (ImageView)myView.findViewById(R.id.socialImageButton);
        Drawable igDrawable = getResources().getDrawable(R.drawable.instagram, null);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(),R.drawable.instagram);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap,viewWidth/4,viewWidth/4,true);

        social_iv.setImageBitmap(bMapScaled);

        //social_iv.setImageDrawable(igDrawable);





        final ImageView social_iv2 = (ImageView)myView.findViewById(R.id.socialImageButton2);
        Drawable fbDrawable = getResources().getDrawable(R.drawable.facebook, null);

        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.facebook);
        bMapScaled = Bitmap.createScaledBitmap(bMap,viewWidth/4,viewWidth/4,true);

        social_iv2.setImageBitmap(bMapScaled);
       // social_iv2.setImageDrawable(fbDrawable);

        final ImageView social_iv3 = (ImageView)myView.findViewById(R.id.socialImageButton3);
        Drawable twitterDrawable = getResources().getDrawable(R.drawable.twitterx, null);

        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.twitterx);
        bMapScaled = Bitmap.createScaledBitmap(bMap,viewWidth/4,viewWidth/4,true);

        social_iv3.setImageBitmap(bMapScaled);
        //social_iv3.setImageDrawable(twitterDrawable);

        final ImageView social_iv4 = (ImageView)myView.findViewById(R.id.socialImageButton4);
        Drawable scDrawable = getResources().getDrawable(R.drawable.soundcloud, null);

        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.soundcloud);
        bMapScaled = Bitmap.createScaledBitmap(bMap,viewWidth/4,viewWidth/4,true);

        social_iv4.setImageBitmap(bMapScaled);
        //social_iv4.setImageDrawable(scDrawable);

        final ImageView social_iv5 = (ImageView)myView.findViewById(R.id.socialImageButton5);
        Drawable tumblrDrawable = getResources().getDrawable(R.drawable.tumblr, null);

        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.tumblr);
        bMapScaled = Bitmap.createScaledBitmap(bMap,viewWidth/4,viewWidth/4,true);

        social_iv5.setImageBitmap(bMapScaled);
        //social_iv5.setImageDrawable(tumblrDrawable);

        final ImageView social_iv6 = (ImageView)myView.findViewById(R.id.socialImageButton6);
        Drawable youtubeDrawable = getResources().getDrawable(R.drawable.youtube, null);

        bMap = BitmapFactory.decodeResource(getResources(),R.drawable.youtube);
        bMapScaled = Bitmap.createScaledBitmap(bMap,viewWidth/4,viewWidth/4,true);

        social_iv6.setImageBitmap(bMapScaled);
        //social_iv6.setImageDrawable(youtubeDrawable);

        final ImageButton igButton = (android.widget.ImageButton) myView.findViewById(R.id.socialImageButton);
        final ImageButton fbButton = (android.widget.ImageButton) myView.findViewById(R.id.socialImageButton2);
        final ImageButton twitterButton = (android.widget.ImageButton) myView.findViewById(R.id.socialImageButton3);
        final ImageButton soundcloudButton = (android.widget.ImageButton) myView.findViewById(R.id.socialImageButton4);
        final ImageButton tumblrButton = (android.widget.ImageButton) myView.findViewById(R.id.socialImageButton5);
        final ImageButton youTubeButton = (android.widget.ImageButton) myView.findViewById(R.id.socialImageButton6);

        igButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/truthuniversal");
                String igUrl = "http://instagram.com/truthuniversal";
                Intent igIntent = new Intent(Intent.ACTION_VIEW, uri);

                igIntent.setPackage("com.instagram.android");

                launchSocMeIntent(igUrl,igIntent);
            }
        });

        fbButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String fbUrl = "http://facebook.com/truthuniversal";
                Uri uri = Uri.parse("fb://facewebmodal/f?href="+fbUrl);
                Intent fbIntent = new Intent(Intent.ACTION_VIEW, uri);
                fbIntent.setPackage("com.facebook.katana");

                launchSocMeIntent(fbUrl,fbIntent);
            }
        });

        twitterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Uri uri = Uri.parse("twitter://user?user_id=17585312");
                String twitterUrl = "https://twitter.com/truthuniversal";
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, uri);
                twitterIntent.setPackage("com.twitter.android");

                launchSocMeIntent(twitterUrl,twitterIntent);

            }
        });

        soundcloudButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String scUrl = "http://soundcloud.com/truthuniversal";
                Uri uri = Uri.parse("soundcloud://users/433425");
                Intent scIntent = new Intent(Intent.ACTION_VIEW, uri);
                scIntent.setPackage("com.soundcloud.android");

                launchSocMeIntent(scUrl,scIntent);
            }
        });

        tumblrButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String tumblrUrl = "https://www.tumblr.com/blog/truthuniversal";
                Uri uri = Uri.parse("tumblr://x-callback-url/blog?blogName=truthuniversal");
                Intent tumblrIntent = new Intent(Intent.ACTION_VIEW, uri);
                tumblrIntent.setPackage("com.tumblr");

                launchSocMeIntent(tumblrUrl,tumblrIntent);
            }
        });

        youTubeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String ytUrl = "http://youtube.com/user/truthuniversal";
                Uri uri = Uri.parse(ytUrl);
                Intent ytIntent = new Intent(Intent.ACTION_VIEW, uri);
                ytIntent.setPackage("com.facebook.katana");

                launchSocMeIntent(ytUrl,ytIntent);
            }
        });


        return myView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int viewHeight = displayMetrics.heightPixels;
        int viewWidth = displayMetrics.widthPixels;

        System.out.println("width=="+viewWidth);

        myView.findViewById(R.id.socialImageButton).getLayoutParams().width = (viewWidth/4);
        myView.findViewById(R.id.socialImageButton).getLayoutParams().height = (viewWidth/4);


    }

    public void launchSocMeIntent(String market_uri,Intent socMeIntent){

        try {
            startActivity(socMeIntent);
            System.out.println("xxxxx");
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(market_uri)));
        }

    }





}
