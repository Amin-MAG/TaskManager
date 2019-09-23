package com.mag.taskmanager.Controller;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.mag.taskmanager.Model.Repository;

public class LoginActivity extends SingleFragmentActivity {



    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public Fragment getFragment() {
        return LoginFragment.newInstance();
    }

}
