package com.mag.taskmanager.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mag.taskmanager.R;

import com.mag.taskmanager.Util.*;
import com.mag.taskmanager.Var.*;

public class TaskActivity extends SingleFragmentActivity {

    private final static String DELETE_ALL_TASK_FRAGMENT = "delete_all_task_fragment";
    public static final int DELAY_MILLIS = 2000;

    private FrameLayout mainFrame;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TaskActivity.class);
        return intent;
    }

    @Override
    public Fragment getFragment() {
        return MainTaskPagerFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.fragment_user_info);
        ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.userInfoFragment_username)).setText(Global.getOnlineUsername());

        mainFrame = findViewById(R.id.singleFragmentActivity_mainFrame);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenu_delete:
                DeleteAllFragment deleteAllFragment = DeleteAllFragment.newInstance();
                deleteAllFragment.show(getSupportFragmentManager(), DELETE_ALL_TASK_FRAGMENT);
                return true;
            case R.id.mainMenu_logout:
                Runnable updater = new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                };
                UiUtil.showSnackbar(mainFrame, getResources().getString(R.string.logout), getResources().getString(R.color.task_app_red));
                Global.setOnlineUserID(null);
                Global.setOnlineUsername(null);
                Constants.TIME_HANDLER.postDelayed(updater, DELAY_MILLIS);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
