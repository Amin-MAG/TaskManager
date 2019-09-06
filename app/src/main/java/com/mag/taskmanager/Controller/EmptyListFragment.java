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
public class EmptyListFragment extends Fragment {


    public EmptyListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_empty_list, container, false);
    }

}
