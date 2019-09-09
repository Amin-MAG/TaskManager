package com.mag.taskmanager.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.UiUtil;
import com.mag.taskmanager.Var.Constants;

public class TaskActivity extends AppCompatActivity {

    FrameLayout header, mainFrame;

    public static Intent newIntent(Context context, String username, String password) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(Constants.EXTRA_USERNAME, username);
        intent.putExtra(Constants.EXTRA_PASSWORD, password);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        FragmentManager fragmentManager = getSupportFragmentManager();

        header = findViewById(R.id.taskActivity_headerFrame);
        mainFrame = findViewById(R.id.taskActivity_mainFrame);

        UiUtil.changeFragment(fragmentManager, MainTaskPagerFragment.newInstance("amin"), mainFrame.getId());
        if (header != null)
            UiUtil.changeFragment(fragmentManager, UserInfoFragment.newInstance("amin"), header.getId());

    }

}
