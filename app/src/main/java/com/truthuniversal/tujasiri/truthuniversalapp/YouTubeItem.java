package com.truthuniversal.tujasiri.truthuniversalapp;

/**
 * Created by tujasiri on 12/14/15.
 */
public class YouTubeItem {

    private String video_description="";
    private String video_id="";
    private String video_thumbnail_url="";
    private String video_title="";

    public YouTubeItem(){

        String video_description="";
        String video_id="";
        String video_thumbnail_url="";
        String video_title="";

        this.video_description = video_description;
        this.video_id = video_id;
        this.video_thumbnail_url = video_thumbnail_url;
        this.video_title = video_title;


    }//end constructor

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getVideo_thumbnail_url() {
        return video_thumbnail_url;
    }

    public void setVideo_thumbnail_url(String video_thumbnail_url) {
        this.video_thumbnail_url = video_thumbnail_url;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }
}
