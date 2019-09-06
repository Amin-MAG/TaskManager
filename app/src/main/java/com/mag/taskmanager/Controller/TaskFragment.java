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
public class TaskFragment extends Fragment {

    public static TaskFragment newInstance(String username, String password) {
        
        Bundle args = new Bundle();
        args.putString("arg_username", username);
        args.putString("arg_password", password);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskFragment() {
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

}
