package com.mag.taskmanager.Controller.ViewPagerAdapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mag.taskmanager.Model.TaskStatus;

import java.util.HashMap;

public class TaskViewPagerAdapter extends FragmentStatePagerAdapter {

    private String username;
    private HashMap<TaskStatus, Fragment> taskListFragments;

    public TaskViewPagerAdapter(FragmentManager fm, String username, HashMap<TaskStatus, Fragment> taskListFragments) {
        super(fm);
        this.username = username;
        this.taskListFragments = taskListFragments;
    }

    @Override
    public Fragment getItem(int position) {
//        if (Repository.getInstance().getUserByUsername(username).getTaskByStatus(TaskStatus.values()[position]).size() != 0) {
            TaskStatus status = TaskStatus.values()[position];
            return taskListFragments.get(status);
//        } else
//            return EmptyListFragment.newInstance();
    }

    @Override
    public int getCount() {
        return taskListFragments.size();
    }

    public void setTaskListFragments(HashMap<TaskStatus, Fragment> taskListFragments) {
        this.taskListFragments = taskListFragments;
    }

    public HashMap<TaskStatus, Fragment> getTaskListFragments() {
        return taskListFragments;
    }
}