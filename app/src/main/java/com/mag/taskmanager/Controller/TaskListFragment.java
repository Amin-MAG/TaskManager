package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.RecyclerAdapters.TaskRecyclerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    RecyclerView recyclerView;

    public static TaskListFragment newInstance(TaskStatus status, String username) {

        Bundle args = new Bundle();
        args.putSerializable("arg_status", status);
        args.putString("arg_username", username);

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TaskListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        TaskStatus status = (TaskStatus) bundle.getSerializable("arg_status");
        String username = bundle.getString("arg_username");

        recyclerView = view.findViewById(R.id.taskListFragment_recyclerview);
        recyclerView.setAdapter(new TaskRecyclerAdapter(Repository.getInstance().getUserByUsername(username).getTaskByStatus(status)));

        switch (status) {
            case TODO:
                recyclerView.setBackgroundColor(Color.parseColor(getResources().getString(R.color.task_app_red)));
                break;
            case DOING:
                recyclerView.setBackgroundColor(Color.parseColor(getResources().getString(R.color.task_app_yellow)));
                break;
            case DONE:
                recyclerView.setBackgroundColor(Color.parseColor(getResources().getString(R.color.task_app_green_dark)));
                break;
            default:
                break;

        }

    }
}
