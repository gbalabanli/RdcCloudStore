package com.rdc.android.rdccloudstore;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.rdc.android.rdccloudstore.fragments.LoginFragment;
import com.rdc.android.rdccloudstore.utils.HttpRequestController;

public class MainActivity extends BaseActivity {

    HttpRequestController httpRequestController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        httpRequestController = new HttpRequestController();

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        addFragment(new LoginFragment(),false);
    }

    public HttpRequestController getHttpRequestController() {
        return httpRequestController;
    }
}
