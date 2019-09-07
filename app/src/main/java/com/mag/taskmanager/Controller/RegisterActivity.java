package com.mag.taskmanager.Controller;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mag.taskmanager.R;

public class RegisterActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
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
