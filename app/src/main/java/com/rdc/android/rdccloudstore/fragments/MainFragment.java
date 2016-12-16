package com.rdc.android.rdccloudstore.fragments;

import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rdc.android.rdccloudstore.MainActivity;
import com.rdc.android.rdccloudstore.R;
import com.rdc.android.rdccloudstore.adapters.FolderAndFileAdapter;
import com.rdc.android.rdccloudstore.listeners.DirectoryListFinishedListener;
import com.rdc.android.rdccloudstore.listeners.OnRowClickListener;
import com.rdc.android.rdccloudstore.listeners.WebServiceProcessListener;
import com.rdc.android.rdccloudstore.models.CloudDirectory;
import com.rdc.android.rdccloudstore.utils.HttpRequestController;

import android.support.v4.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FolderAndFileAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainActivity mainActivity;
    private String TAG = "MAINFRAGMENT";

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mainActivity = (MainActivity) getActivity();

        final HttpRequestController httpRequestController = mainActivity.getHttpRequestController();
        httpRequestController.userInfoRequest();
        httpRequestController.rootFolderIDRequest();
        final ArrayList<CloudDirectory> cloudDirectories = new ArrayList<CloudDirectory>();

        httpRequestController.setWebServiceProcessListener(new WebServiceProcessListener() {
            @Override
            public void onRequestFinished(boolean isProper, CallID callID) {
                if(isProper && CallID.USER == callID){

                }
                else if(isProper && CallID.ROOT_ID == callID){
                    httpRequestController.setDirectoryListFinishedListener(new DirectoryListFinishedListener() {
                        @Override
                        public void listingFinished(JSONArray jsonArray) {
                            Log.d(TAG,jsonArray.toString());
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject item = null;
                                try {
                                    item = jsonArray.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String name = null;
                                String resourceId = null;
                                String type = null;
                                try {
                                    name = item.getString("name");
                                    type = item.getString("type");
                                    resourceId = item.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                CloudDirectory cloudDirectory = new CloudDirectory(resourceId,name);
                                cloudDirectories.add(cloudDirectory);
                                mAdapter.notifyDataSetChanged();

                            }


                        }
                    });
                    httpRequestController.rootFolderDirRequest(mainActivity.getHttpRequestController().getSession().ROOT_ID);
                }

            }
        });

        mAdapter = new FolderAndFileAdapter(cloudDirectories);
        mAdapter.setOnRowClickedListener(new OnRowClickListener() {
            @Override
            public void onRowClicked(int position, Object object) {
                PreviewFragment previewFragment = new PreviewFragment();
                previewFragment.setClickedCloudDirectory((CloudDirectory) object);
                ((MainActivity) getActivity()).addFragment(previewFragment,false);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
}

