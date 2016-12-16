package com.rdc.android.rdccloudstore.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rdc.android.rdccloudstore.R;
import com.rdc.android.rdccloudstore.models.CloudDirectory;

public class PreviewFragment extends Fragment {
    private CloudDirectory cloudDirectory;

    public PreviewFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("PreviewFragment","clicked object title:"+cloudDirectory.getName());
        return inflater.inflate(R.layout.fragment_preview, container, false);
    }

    public void setClickedCloudDirectory(CloudDirectory cloudDirectory){
        this.cloudDirectory = cloudDirectory;
    }
}
