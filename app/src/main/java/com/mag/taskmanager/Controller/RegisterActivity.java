package com.mag.taskmanager.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.mag.taskmanager.R;

public class RegisterActivity extends SingleFragmentActivity {

    public static final String TAG_REGISTER_FRAGMENT = "tag_register_fragment";

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    public String getTagName() {
        return TAG_REGISTER_FRAGMENT;
    }

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
