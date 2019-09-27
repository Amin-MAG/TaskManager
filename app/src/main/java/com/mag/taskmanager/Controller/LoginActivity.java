package com.mag.taskmanager.Controller;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class LoginActivity extends SingleFragmentActivity {

    public static final String TAG_LOGIN_FRAGMENT = "tag_login_fragment";

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public Fragment getFragment() {
        return LoginFragment.newInstance();
    }

    public String getTagName() {
        return TAG_LOGIN_FRAGMENT;
    }

}
