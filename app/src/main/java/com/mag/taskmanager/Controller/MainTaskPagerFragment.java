package com.mag.taskmanager.Controller;


import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainTaskPagerFragment extends Fragment {

    TabLayout statusTabLayout;
    ViewPager taskViewPager;


    public static MainTaskPagerFragment newInstance(String username) {

        Bundle args = new Bundle();
        args.putString("arg_username",username);

        MainTaskPagerFragment fragment = new MainTaskPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MainTaskPagerFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_task_pager, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        final String username = bundle.getString("arg_username");


        // Tab Layout

        statusTabLayout = view.findViewById(R.id.pagerFragment_statusTabLayout);

        statusTabLayout.addTab(statusTabLayout.newTab().setText("To Do"));
        statusTabLayout.addTab(statusTabLayout.newTab().setText("Doing"));
        statusTabLayout.getTabAt(1).select();
        statusTabLayout.addTab(statusTabLayout.newTab().setText("Done"));;

        statusTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                taskViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }

        });

        // View Pager

        taskViewPager = view.findViewById(R.id.pagerFragment_viewPager);
        taskViewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return TaskListFragment.newInstance(TaskStatus.values()[position], username);
            }

            @Override
            public int getCount() {
                return 3;
            }

        });
        taskViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
//                Log.d("Something", String.valueOf(position));
                statusTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
        taskViewPager.setCurrentItem(1);

    }


}
