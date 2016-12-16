package com.rdc.android.rdccloudstore.models;


public class FileDirectory extends CloudDirectory {
    private String resourceUrl;

    public FileDirectory(String resourceId, String name,String resourceUrl){
        super( name,  resourceId);
        this.resourceUrl = resourceUrl;
    }
}
