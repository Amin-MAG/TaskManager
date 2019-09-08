package com.mag.taskmanager.Controller;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainTaskPagerFragment extends Fragment {

    ViewPager viewPager;


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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle bundle = getArguments();
        final String username = bundle.getString("arg_username");

        viewPager = view.findViewById(R.id.pagerFragment_viewPager);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return TaskListFragment.newInstance(TaskStatus.values()[position], username);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        viewPager.setCurrentItem(1);

    }


}
