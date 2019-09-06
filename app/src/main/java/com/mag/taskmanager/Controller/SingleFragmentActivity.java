package com.mag.taskmanager.Controller;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.UiUtil;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.singleFragmentActivity_mainFrame);

        if (fragment == null)  UiUtil.changeFragment(fragmentManager, getFragment(),R.id.singleFragmentActivity_mainFrame,false);

    }



}
