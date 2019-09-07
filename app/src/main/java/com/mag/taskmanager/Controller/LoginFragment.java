package com.mag.taskmanager.Controller;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mag.taskmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {


    private MaterialButton loginBtn, registerBtn;
    private TextInputEditText usernameEditText, passwordEditText;

    public static LoginFragment newInstance() {
        
        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }
    

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        usernameEditText = view.findViewById(R.id.loginFragment_username);
        passwordEditText = view.findViewById(R.id.loginFragment_password);
        loginBtn = view.findViewById(R.id.loginFragment_loginBtn);
        registerBtn = view.findViewById(R.id.loginFragment_registerBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterActivity.newIntent(getActivity()));
            }
        });

    }


}
