package com.mag.taskmanager.Controller;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.taskmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    public static TaskListFragment newInstance(String username, String password) {

        Bundle args = new Bundle();
        args.putString("arg_username", username);
        args.putString("arg_password", password);

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

}
