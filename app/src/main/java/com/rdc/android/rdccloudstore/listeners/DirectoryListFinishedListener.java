package com.rdc.android.rdccloudstore.listeners;

import org.json.JSONArray;

/**
 * Created by BB on 12/16/16.
 */
public interface DirectoryListFinishedListener {
    void listingFinished(JSONArray jsonArray);
}
