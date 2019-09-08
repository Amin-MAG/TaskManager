package com.mag.taskmanager.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.Constants;

public class TaskActivity extends AppCompatActivity {

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



    }

}
