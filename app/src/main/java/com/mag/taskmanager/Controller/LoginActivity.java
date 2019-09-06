package com.mag.taskmanager.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.mag.taskmanager.Var.Contants;

public class LoginActivity extends SingleFragmentActivity {



    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public Fragment getFragment() {

        Intent intent = getIntent();
        String username = intent.getStringExtra(Contants.EXTRA_USERNAME);
        String password= intent.getStringExtra(Contants.EXTRA_PASSWORD);

        return LoginFragment.newInstance(username, password);
    }

}
