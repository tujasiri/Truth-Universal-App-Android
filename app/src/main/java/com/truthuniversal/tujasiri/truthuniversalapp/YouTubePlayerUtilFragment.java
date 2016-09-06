package com.truthuniversal.tujasiri.truthuniversalapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by tujasiri on 12/19/15.
 */
//public class YouTubePlayerUtilFragment extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
//public class YouTubePlayerUtilFragment extends YouTubePlayerFragment implements YouTubePlayer.OnInitializedListener {
public class YouTubePlayerUtilFragment extends Fragment implements YouTubePlayer.OnInitializedListener {


    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    //private YouTubePlayerFragment youTubeViewFragment = YouTubePlayerFragment.newInstance();
    public YouTubePlayerFragment youTubeViewFragment = YouTubePlayerFragment.newInstance();
    //public YouTubePlayerSupportFragment youTubeViewFragment = YouTubePlayerSupportFragment.newInstance();
    private YouTubeItem youTubeItem;
    public View ytViewContainer;

    /*public YouTubePlayerUtil(YouTubeItem passedYouTubeItem){
        youTubeItem = passedYouTubeItem;
    }
    */


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //checkoutCartItemView = inflater.inflate(R.layout.checkout_cart_view,container,false);
        ytViewContainer = inflater.inflate(R.layout.youtube_view_layout,container,false);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int viewHeight = displayMetrics.heightPixels;
        int viewWidth = displayMetrics.widthPixels;
        int dimScale = viewWidth/8;


        final ImageView menu_button_iv = (ImageView)ytViewContainer.findViewById(R.id.back_to_video_menu);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_video_icon);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, dimScale, dimScale, true);

        menu_button_iv.setImageBitmap(bMapScaled);

        final ImageButton ytMenuButton = (android.widget.ImageButton) ytViewContainer.findViewById(R.id.back_to_video_menu);

        final Animation myAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.snackbar_in);

        ytMenuButton.setAnimation(myAnim);
        //ytMenuButton.startAnimation(myAnim);

        ytMenuButton.animate()
                   .setDuration(800);


    //protected void onCreate(Bundle savedInstanceState) {
     //   super.onCreate(savedInstanceState);
        //setContentView(R.layout.youtube_view_layout);

        //youTubeView = (YouTubePlayerView) ytViewContainer.findViewById(R.id.youtube_view);
        //youTubeView.initialize(Config.YOUTUBE_API_KEY, this);
        ytMenuButton.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new YouTubeFragment())
                        .commit();


            }

        });

        FragmentManager ytFragmentManager = getFragmentManager();
        ytFragmentManager.beginTransaction()
                .replace(R.id.youtube_content_frame, youTubeViewFragment)
                .commit();

        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //transaction.add(R.id.youtube_content_frame, youTubeViewFragment).commit();

        try {


            youTubeViewFragment.initialize(Config.YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
                    System.out.println("INITIALIZING!");

                    Bundle ytVidArguments=getArguments();
                    String videoId = ytVidArguments.getString(YouTubeFragment.VIDEO_ID);
                    System.out.println("videoId==>"+videoId);


                    if (!wasRestored) {
                        //String videoId = ytVidArguments.getString(YouTubeFragment.VIDEO_ID);
                        //System.out.println("videoId==>"+videoId);
                        player.cueVideo(videoId); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
                        //player.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
                    }
                }

                @Override
                public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
                    System.out.println("NOT INITIALIZING!");

                    if (errorReason.isUserRecoverableError()) {
                        errorReason.getErrorDialog(getActivity(), RECOVERY_REQUEST).show();
                    } else {
                        String error = String.format(getString(R.string.player_error), errorReason.toString());
                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch(Exception e){
            System.out.println("EXCEPTION==>"+e.toString());
        }

        return ytViewContainer;
    }

    @Override
    //public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {
    public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean wasRestored) {


        if (!wasRestored) {
            //String videoId = getIntent().getStringExtra(YouTubeActivity.VIDEO_ID);
            Bundle ytVidArguments = this.getArguments();

            String videoId = ytVidArguments.getString(YouTubeFragment.VIDEO_ID);
            player.cueVideo(videoId); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo

            //player.cueVideo("fhWaJi1Hsfo"); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(getActivity(), RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    //protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.YOUTUBE_API_KEY, this);
        }
    }

    protected Provider getYouTubePlayerProvider() {
        return youTubeView;
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
    








}
