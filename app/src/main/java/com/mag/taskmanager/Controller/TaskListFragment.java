package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Controller.RecyclerAdapters.TaskRecyclerAdapter;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListFragment extends Fragment {

    public static final int REQUEST_CODE_FOR_EDIT_DIALOG = 1005;
    public static final String EDIT_TASK_FRAGMENT = "edit_task_fragment";
    private TaskRecyclerAdapter taskRecyclerAdapter;
    private RecyclerView recyclerView;

    // Note
    private String username;
    private TaskStatus status;

    public static TaskListFragment newInstance(TaskStatus status, String username) {

        Bundle args = new Bundle();
        args.putSerializable("arg_status", status);
        args.putString("arg_username", username);

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    TaskListFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.status = (TaskStatus) getArguments().getSerializable("arg_status");
        this.username = getArguments().getString("arg_username");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.taskListFragment_recyclerview);
        taskRecyclerAdapter = new TaskRecyclerAdapter(Repository.getInstance().getUserByUsername(username).getTaskByStatus(status), new TaskRecyclerAdapter.OnItemClickListener() {
            @Override
            public void showEditDialog() {
                EditTaskFragment editTaskFragment = EditTaskFragment.newInstance(username);
                editTaskFragment.setTargetFragment(TaskListFragment.this, REQUEST_CODE_FOR_EDIT_DIALOG);
                editTaskFragment.show(getFragmentManager(), EDIT_TASK_FRAGMENT);
            }
        });

        recyclerView.setAdapter(taskRecyclerAdapter);

    }

    public void update() {
        taskRecyclerAdapter.setTasks(Repository.getInstance().getUserByUsername(username).getTaskByStatus(status));
        taskRecyclerAdapter.notifyDataSetChanged();
    }

}