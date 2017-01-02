package com.truthuniversal.tujasiri.truthuniversalapp;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tujasiri on 12/26/2016.
 */

@SuppressWarnings("NewApi")
public class NewsDetailFragment extends Fragment {

    View newsDetailView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        newsDetailView = inflater.inflate(R.layout.news_detail_layout, container, false);

        final Button backToNewsButton = (Button) newsDetailView.findViewById(R.id.backToNewsButton);

        backToNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManagerNews = getFragmentManager();

                fragmentManagerNews.beginTransaction()
                        .replace(R.id.content_frame, new NewsFragment())
                        .commit();

            }
        });

        Bundle bundle = this.getArguments();
        Bitmap bmp = (Bitmap) bundle.getParcelable("imagebitmap");
        String nArticle = bundle.getString(NewsFragment.EXTRA_ARTICLE);
        String nArticleTitle = bundle.getString(NewsFragment.EXTRA_ARTICLE_TITLE);

        TextView tvNewsDetailTitle = (TextView)newsDetailView.findViewById(R.id.news_article_detail_title)  ;
        tvNewsDetailTitle.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvNewsDetailTitle.setText(nArticleTitle);

        ImageView ivNewsDetail = (ImageView)newsDetailView.findViewById(R.id.news_article_detail_image);
        ivNewsDetail.setImageBitmap(bmp);

        TextView tvNewsDetail = (TextView)newsDetailView.findViewById(R.id.news_article_detail_text)  ;
        tvNewsDetail.setText(nArticle);

        return newsDetailView;

    }
}
