package com.truthuniversal.tujasiri.truthuniversalapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

        final ImageView social_iv = (ImageView)myView.findViewById(R.id.socialImageButton);
        Drawable igDrawable = getResources().getDrawable(R.drawable.instagram, null);
        social_iv.setImageDrawable(igDrawable);

        final ImageView social_iv2 = (ImageView)myView.findViewById(R.id.socialImageButton2);
        Drawable fbDrawable = getResources().getDrawable(R.drawable.facebook, null);
        social_iv2.setImageDrawable(fbDrawable);

        final ImageView social_iv3 = (ImageView)myView.findViewById(R.id.socialImageButton3);
        Drawable twitterDrawable = getResources().getDrawable(R.drawable.twitterx, null);
        social_iv3.setImageDrawable(twitterDrawable);

        final ImageView social_iv4 = (ImageView)myView.findViewById(R.id.socialImageButton4);
        Drawable scDrawable = getResources().getDrawable(R.drawable.soundcloud, null);
        social_iv4.setImageDrawable(scDrawable);

        final ImageView social_iv5 = (ImageView)myView.findViewById(R.id.socialImageButton5);
        Drawable tumblrDrawable = getResources().getDrawable(R.drawable.tumblr, null);
        social_iv5.setImageDrawable(tumblrDrawable);

        final ImageView social_iv6 = (ImageView)myView.findViewById(R.id.socialImageButton6);
        Drawable youtubeDrawable = getResources().getDrawable(R.drawable.youtube, null);
        social_iv6.setImageDrawable(youtubeDrawable);

        return myView;
    }


}
