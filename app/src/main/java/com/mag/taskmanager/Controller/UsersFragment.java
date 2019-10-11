package com.mag.taskmanager.Controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.taskmanager.Controller.Adapters.UserRecyclerAdapter;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.R;

public class UsersFragment extends Fragment {

    private RecyclerView userRecycler;

    public static UsersFragment newInstance() {
        
        Bundle args = new Bundle();
        
        UsersFragment fragment = new UsersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public UsersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserRecyclerAdapter userRecyclerAdapter = new UserRecyclerAdapter(Repository.getInstance().getUsers(), (FrameLayout) view.findViewById(R.id.usersFragment_mainFrame));
        userRecycler = view.findViewById(R.id.usersFragment_recycler);
        userRecycler.setAdapter(userRecyclerAdapter);

    }
}
