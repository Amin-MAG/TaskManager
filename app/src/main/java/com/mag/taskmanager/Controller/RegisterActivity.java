package com.mag.taskmanager.Controller;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.mag.taskmanager.R;
import com.mag.taskmanager.RegisterFragment;

public class RegisterActivity extends SingleFragmentActivity {

    @Override
    public Fragment getFragment() {
        return RegisterFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_fragment);
    }

}
