package com.rdc.android.rdccloudstore.models;

/**
 * Created by BB on 12/14/16.
 */
public class CloudDirectory {
    private String name;
    private String resourceId;


    public CloudDirectory(String resourceId, String name){
        this.name = name;
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getName() {
        return name;
    }


}
