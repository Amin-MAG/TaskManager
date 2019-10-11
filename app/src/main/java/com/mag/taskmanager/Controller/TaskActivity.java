package com.mag.taskmanager.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.AudioRecord;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.UiUtil;
import com.mag.taskmanager.Var.Constants;
import com.mag.taskmanager.Var.Global;

public class TaskActivity extends SingleFragmentActivity {

    public static final int FADE_DELAY = 150;
    public static final int SHOW_DELAY = 300;
    public static final String TAG_MAIN_TASK_PAGER_FRAGMENT = "tag_main_task_pager_fragment";
    private Animation fadeOutAnim;
    private Animation fadeInAnim;

    private final static String DELETE_ALL_TASK_FRAGMENT = "delete_all_task_fragment";
    public static final int DELAY_MILLIS = 2000;

    private FrameLayout mainFrame;
    private TextView usernameTextView, titileTextView;
    private TextInputLayout searchTextLayout;
    private TextInputEditText searchEditText;
    private ImageButton closeBtn;

    public static Intent newIntent(Context context) {
        return new Intent(context, TaskActivity.class);
    }

    @Override
    public Fragment getFragment() {

        MainTaskPagerFragment.setCallBack(new MainTaskPagerFragment.MainTaskPagerCallBack() {
            @Override
            public String getSearchText() {
                return searchEditText.getText().toString();
            }
        });

        return MainTaskPagerFragment.newInstance();
    }

    @Override
    public String getTagName() {
        return TAG_MAIN_TASK_PAGER_FRAGMENT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Test //

//        Global.setOnlineUsername("amin");
//        Global.setOnlineUserID(Repository.getInstance().getUserByUsername("amin").getId());
//        Repository.getInstance();

        //****//

        fadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.fragment_user_info);
        ((TextView) getSupportActionBar().getCustomView().findViewById(R.id.userInfoFragment_username)).setText(Global.getOnlineUsername());

        findItems();

        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                for (TaskStatus taskStatus : ((MainTaskPagerFragment) getSupportFragmentManager().findFragmentByTag(TAG_MAIN_TASK_PAGER_FRAGMENT)).getTaskListFragments().keySet()) {

                    ((TaskListFragment)
                            ((MainTaskPagerFragment)
                                    getSupportFragmentManager()
                                            .findFragmentByTag(TAG_MAIN_TASK_PAGER_FRAGMENT))
                                    .getTaskListFragments()
                                    .get(taskStatus)).update();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Runnable hideSearchBar = new Runnable() {
                    @Override
                    public void run() {
                        titileTextView.startAnimation(fadeInAnim);
                        titileTextView.startAnimation(fadeInAnim);

                        searchEditText.setText(Constants.EMPTY_STRING);
                        closeBtn.setVisibility(View.GONE);
                        searchTextLayout.setVisibility(View.GONE);
                    }
                };

                Runnable showNotes = new Runnable() {
                    @Override
                    public void run() {
                        usernameTextView.setVisibility(View.VISIBLE);
                        titileTextView.setVisibility(View.VISIBLE);
                    }
                };

                closeBtn.startAnimation(fadeOutAnim);

                Constants.TIME_HANDLER.postDelayed(hideSearchBar, FADE_DELAY);
                Constants.TIME_HANDLER.postDelayed(showNotes, SHOW_DELAY);

            }
        });

    }

    private void findItems() {
        mainFrame = findViewById(R.id.singleFragmentActivity_mainFrame);
        searchTextLayout = findViewById(R.id.userInfoFragment_searchLayout);
        searchEditText = findViewById(R.id.userInfoFragment_searchText);
        closeBtn = findViewById(R.id.userInfoFragment_close);
        titileTextView = findViewById(R.id.userInfoFragment_title);
        usernameTextView = findViewById(R.id.userInfoFragment_username);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        if (Repository.getInstance().getUserByUsername(Global.getOnlineUsername()).getIsAdmin()) {
            menu.findItem(R.id.mainMenu_delete).setVisible(false);
        }

        if (!Repository.getInstance().getUserByUsername(Global.getOnlineUsername()).getIsAdmin()) {
            menu.findItem(R.id.mainMenu_users).setVisible(false);
        }

        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mainMenu_search:

                Runnable hideNotes = new Runnable() {
                    @Override
                    public void run() {
                        usernameTextView.setVisibility(View.GONE);
                        titileTextView.setVisibility(View.GONE);
                    }
                };


                Runnable showSearchBar = new Runnable() {
                    @Override
                    public void run() {
                        searchTextLayout.setVisibility(View.VISIBLE);
                        closeBtn.setVisibility(View.VISIBLE);
                        searchEditText.requestFocus();

                    }
                };

                Constants.TIME_HANDLER.postDelayed(hideNotes, FADE_DELAY);
                Constants.TIME_HANDLER.postDelayed(showSearchBar, SHOW_DELAY);

                return true;
            case R.id.mainMenu_users:

                startActivity(UsersActivity.newIntent(this));

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
