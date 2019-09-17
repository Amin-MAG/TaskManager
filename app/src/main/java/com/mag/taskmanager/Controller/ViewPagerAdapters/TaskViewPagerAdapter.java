package com.mag.taskmanager.Controller.ViewPagerAdapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.mag.taskmanager.Model.TaskStatus;

import java.util.HashMap;

public class TaskViewPagerAdapter extends FragmentStatePagerAdapter {

    private HashMap<TaskStatus, Fragment> taskListFragments;

    public TaskViewPagerAdapter(FragmentManager fm, HashMap<TaskStatus, Fragment> taskListFragments) {
        super(fm);
        this.taskListFragments = taskListFragments;
    }

    @Override
    public Fragment getItem(int position) {
            TaskStatus status = TaskStatus.values()[position];
            return taskListFragments.get(status);
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