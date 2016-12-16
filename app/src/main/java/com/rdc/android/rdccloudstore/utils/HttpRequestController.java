package com.rdc.android.rdccloudstore.utils;


import android.os.AsyncTask;
import android.util.Log;

import com.rdc.android.rdccloudstore.listeners.DirectoryListFinishedListener;
import com.rdc.android.rdccloudstore.listeners.WebServiceProcessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class HttpRequestController {

    private boolean DEBUG = true;
    private String TAG = "httprequestcontroller";
    Session session = new Session();
    private WebServiceProcessListener webServiceProcessListener;
    private DirectoryListFinishedListener directoryListFinishedListener;

    public void loginRequest(){
        //send Http Post request to "http://url.com/b.c" in background  using AsyncTask

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                JSONObject res = null;
                try {
                    if (DEBUG)
                        Log.d(TAG, "HEADERS AND BODY" + getSession().LOGIN_HEADER_AUT + " " + getSession().LOGIN_HEADER_CONTENT + " " + getSession().LOGIN_URL + " " + getSession().getLoginBody());

                    res = httpPostHelper(getSession().LOGIN_URL, getSession().getLoginBody(), getSession().LOGIN_HEADER_AUT, getSession().LOGIN_HEADER_CONTENT);

                } catch (Exception e) {
                    //Log.e(TAG,e.getMessage());
                    webServiceProcessListener.onRequestFinished(false,WebServiceProcessListener.CallID.LOGIN);
                }
                if (DEBUG) Log.d(TAG, "RESPONSE:" + res);
                String accessToken = "";
                try {
                    accessToken = res.getString("access_token");
                    getSession().ACCESS_TOKEN = accessToken;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (res != null && !accessToken.isEmpty()) {
                    webServiceProcessListener.onRequestFinished(true, WebServiceProcessListener.CallID.LOGIN);
                } else {
                    webServiceProcessListener.onRequestFinished(false,WebServiceProcessListener.CallID.LOGIN);
                }
                return null;
            }
            protected void onPostExecute(String result) {
                //do something with response
            }
        }.execute();
    }

    public void userInfoRequest(){

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                JSONObject res = httpGetHelper(getSession().USER_URL,getSession().getAccessTokenHeader());
                String cloudServiceUrl="";
                try {
                    cloudServiceUrl = res.getString("CLOUD_SERVICE_URL");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(res!=null && !cloudServiceUrl.isEmpty())
                {
                    getSession().CLOUD_SERVICE_URL = cloudServiceUrl;

                }
                return null;
            }
            protected void onPostExecute(String result) {
                //do something with response
            }
        }.execute();
    }

    public void rootFolderIDRequest(){
        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                JSONObject res = httpGetHelper(getSession().ROOT_URL,getSession().getAccessTokenHeader());
                String rootID="";
                try {
                    rootID = res.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(res!=null && !rootID.isEmpty())
                {
                    getSession().ROOT_ID = rootID;
                    webServiceProcessListener.onRequestFinished(true, WebServiceProcessListener.CallID.ROOT_ID);
                }
                return null;
            }
            protected void onPostExecute(String result) {
                //do something with response
            }
        }.execute();
    }

    public void rootFolderDirRequest(final String requestedID){
        new AsyncTask<Void, Void, JSONArray>(){
            protected JSONArray doInBackground(Void[] params) {
                JSONObject res = httpGetHelper(getSession().getUserRootUrl(requestedID), getSession().getAccessTokenHeader());
                JSONArray directories = null;
                try {
                    directories = res.getJSONArray("items");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(res!=null && directories.length() >0)
                {
                    return directories;
                }
                return null;
            }
            protected void onPostExecute(JSONArray result) {
                //do something with response
                directoryListFinishedListener.listingFinished(result);
            }
        }.execute();

    }

    public String getImageUrl(){
        return null;
    }

    public JSONObject httpPostHelper(String URL, String sendDataJson,String... header){
        JSONObject res = null;
        try {
            res = new HttpRequest(URL).withHeaders(header).prepare(HttpRequest.Method.POST).withData(sendDataJson).sendAndReadJSON();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public JSONObject httpGetHelper(String URL, String... header){
        JSONObject res = null;
        try {
            res = new HttpRequest(URL).withHeaders(header).prepare(HttpRequest.Method.GET).sendAndReadJSON();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }

    public Session getSession(){
        return session;
    }

    public void setWebServiceProcessListener(WebServiceProcessListener webServiceProcessListener){
        this.webServiceProcessListener = webServiceProcessListener;
    }

    public void setDirectoryListFinishedListener(DirectoryListFinishedListener directoryListFinishedListener) {
        this.directoryListFinishedListener = directoryListFinishedListener;
    }
}
