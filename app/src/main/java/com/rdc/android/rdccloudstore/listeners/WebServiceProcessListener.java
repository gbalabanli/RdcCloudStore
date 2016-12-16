package com.rdc.android.rdccloudstore.listeners;


public interface WebServiceProcessListener {
    public enum CallID{
        LOGIN,USER,ROOT_ID
    }

    void onRequestFinished(boolean isProper,CallID callID);
}
