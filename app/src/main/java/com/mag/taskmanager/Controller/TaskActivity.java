package com.mag.taskmanager.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.UiUtil;
import com.mag.taskmanager.Var.Constants;
import com.mag.taskmanager.Var.Global;

public class TaskActivity extends SingleFragmentActivity {

    private final static String DELETE_ALL_TASK_FRAGMENT = "delete_all_task_fragment";
    public static final int DELAY_MILLIS = 2000;

    private FrameLayout mainFrame;
    private TextView usernameTextView, titileTextView;
    private TextInputLayout searchTextLayout;
    private TextInputEditText searchEditText;
    private ImageButton closeBtn;

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

//        mainFrame = findViewById(R.id.singleFragmentActivity_mainFrame);
        searchTextLayout = findViewById(R.id.userInfoFragment_searchLayout);
        searchEditText = findViewById(R.id.userInfoFragment_searchText);
        closeBtn = findViewById(R.id.userInfoFragment_close);
        titileTextView = findViewById(R.id.userInfoFragment_title);
        usernameTextView = findViewById(R.id.userInfoFragment_username);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTextLayout.setVisibility(View.GONE);
                closeBtn.setVisibility(View.GONE);
                usernameTextView.setVisibility(View.VISIBLE);
                titileTextView.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        android.widget.SearchView searchView = (SearchView)
//                 menu.findItem(R.id.mainMenu_search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainMenu_search:
                searchTextLayout.setVisibility(View.VISIBLE);
                closeBtn.setVisibility(View.VISIBLE);
                usernameTextView.setVisibility(View.GONE);
                titileTextView.setVisibility(View.GONE);
                return true;
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
