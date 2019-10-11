package com.mag.taskmanager.Controller;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class UsersActivity extends SingleFragmentActivity {

    public static final String TAG_USERS_FRAGMENT = "tag_users_fragment";

    public static Intent newIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    @Override
    public Fragment getFragment() {
        return UsersFragment.newInstance();
    }

    @Override
    public String getTagName() {
        return TAG_USERS_FRAGMENT;
    }

}
