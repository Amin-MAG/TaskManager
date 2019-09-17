package com.mag.taskmanager.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mag.taskmanager.DeleteAllFragment;
import com.mag.taskmanager.R;

import com.mag.taskmanager.Util.*;
import com.mag.taskmanager.Var.*;

public class TaskActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_FOR_DELETE_DIALOG = 1010;
    private final static String DELETE_ALL_TASK_FRAGMENT = "delete_all_task_fragment";


    private FrameLayout mainFrame;

    public static Intent newIntent(Context context, String username) {
        Intent intent = new Intent(context, TaskActivity.class);
        intent.putExtra(Constants.EXTRA_USERNAME, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.fragment_user_info);

//        Intent intent = getIntent();
//        String username = intent.getStringExtra(Constants.EXTRA_USERNAME);
        final String username = "amin";

        FragmentManager fragmentManager = getSupportFragmentManager();

        mainFrame = findViewById(R.id.taskActivity_mainFrame);

        UiUtil.changeFragment(fragmentManager, MainTaskPagerFragment.newInstance(username), mainFrame.getId());

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
                DeleteAllFragment deleteAllFragment = DeleteAllFragment.newInstance(getIntent().getStringExtra("extra_username"));
//                deleteAllFragment.setTargetFragment(TaskActivity.this, REQUEST_CODE_FOR_DELETE_DIALOG);
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
//                Repository.getInstance().setActiveUser(null);
                Constants.TIME_HANDLER.postDelayed(updater, 2000);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
