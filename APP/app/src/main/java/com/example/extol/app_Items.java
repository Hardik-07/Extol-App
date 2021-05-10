package com.example.extol;

public class app_Items {

    private String mIcon_url;
    private String mAppName;
    private String mAppUrl;
    private String mScore;

    public app_Items(String icon_url, String app_name_data,String app_url, String score ){
        mIcon_url =icon_url;
        mAppName = app_name_data;
        mAppUrl = app_url;
        mScore = score;
    }




    //getter and setter methods
    public String get_mIcon_url() {
        return mIcon_url;
    }
    public String get_mAppName() {
        return mAppName;
    }
    public String get_mAppUrl() { return mAppUrl; }
    public String get_mScore() { return mScore; }
}
