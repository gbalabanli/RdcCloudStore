package com.rdc.android.rdccloudstore.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rdc.android.rdccloudstore.BaseActivity;
import com.rdc.android.rdccloudstore.MainActivity;
import com.rdc.android.rdccloudstore.R;
import com.rdc.android.rdccloudstore.listeners.WebServiceProcessListener;
import com.rdc.android.rdccloudstore.utils.Session;

public class LoginFragment extends Fragment {
    private String TAG ="LOGIN_FRAGMENT";
    private MainActivity activity;
    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button submitButton = (Button)view.findViewById(R.id.button_login);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = ((EditText)activity.findViewById(R.id.edit_username)).getText().toString();
                String password = ((EditText)activity.findViewById(R.id.edit_password)).getText().toString();
                Log.d(TAG,"USERNAME:"+username);
                activity.getHttpRequestController().getSession().USER_NAME = "username="+username ;
                activity.getHttpRequestController().getSession().PASSWORD  = "password="+password ;
                activity.getHttpRequestController().setWebServiceProcessListener(new WebServiceProcessListener() {
                    @Override
                    public void onRequestFinished(boolean isProper, CallID callID) {
                        if (callID == CallID.LOGIN) {
                            if (isProper) {
                                ((BaseActivity) getActivity()).addFragment(new MainFragment(), false);
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.something_wrong), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                activity.getHttpRequestController().loginRequest();


            }
        });
        return view;
    }

}
