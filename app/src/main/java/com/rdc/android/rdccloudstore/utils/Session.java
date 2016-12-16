package com.rdc.android.rdccloudstore.utils;


public class Session {

    public String ACCESS_TOKEN = "";

    public String CLOUD_SERVICE_URL = "";

    public String ROOT_ID = "";

    public  String USER_NAME = "";
    public  String PASSWORD  = "";

    public String CLIENT_ID = "client_id=safran_app";
    public String CLIENT_SECRET = "client_secret=turan";
    public String GRANT_TYPE = "grant_type=password";
    public String DEVICE_ID="device_id=my_id";
    public String DEVICE_NAME= "device_name=hiphon";
    public String DEVICE_TYPE = "device_type=MOBILE";
    public String REMEMBER = "remember=true";

    //URLS
    public String API_URL= "http://85.111.18.53:8080";
    public String LOGIN_URL = API_URL + "/oauth/token";
    public String USER_URL = API_URL + "/api/users/me";
    public String ROOT_URL = API_URL + "/api/resource/root";
    public String DIRECTORY_LIST_URL = "";
    public String IMAGE_GET_URL = "";

    //HEADERS
    public String LOGIN_HEADER_AUT = "Authorization: Basic c2FmcmFuX2FwcDpzYWZyYW5fc2VjcmV0";
    public String LOGIN_HEADER_CONTENT = "Content-Type: application/x-www-form-urlencoded";

    private String LOGIN_BODY;

    public Session(){
    }

    public String getLoginBody(){
        LOGIN_BODY= CLIENT_ID + "&"+ CLIENT_SECRET + "&" + USER_NAME + "&" + PASSWORD + "&" + GRANT_TYPE + "&" + DEVICE_ID + "&" + DEVICE_NAME + "&" + DEVICE_TYPE + "&" + REMEMBER;
        return LOGIN_BODY;
    }

    public String getUserRootUrl(String ID){
        return API_URL + "/api/resource/"+ ID + "/children?page=0&amp;size=20";
    }

    public String getAccessTokenHeader(){
        return "Authorization:Bearer" + ACCESS_TOKEN;
    }

    public String getPreviewResourceURL(String storedPath,String name){
        return CLOUD_SERVICE_URL + storedPath + "?filename"+name +"&auth=" + ACCESS_TOKEN;
    }
}
