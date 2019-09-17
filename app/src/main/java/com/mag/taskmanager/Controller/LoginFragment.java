package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mag.taskmanager.Model.Exception.BadAuthorizationException;
import com.mag.taskmanager.Model.Exception.EmptyFieldException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.*;
import com.mag.taskmanager.Var.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public static final String LOGIN_SUCCESSFULLY = "Login Successfully.";

    private LinearLayout mainLayout;
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


        mainLayout = view.findViewById(R.id.loginFragment_mainLayout);
        usernameEditText = view.findViewById(R.id.loginFragment_username);
        passwordEditText = view.findViewById(R.id.loginFragment_password);
        loginBtn = view.findViewById(R.id.loginFragment_loginBtn);
        registerBtn = view.findViewById(R.id.loginFragment_registerBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                final String username = usernameEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                try {

                    if (username.equals(Constants.EMPTY_STRING) || password.equals(Constants.EMPTY_STRING))
                        throw new EmptyFieldException();

                    if (Repository.getInstance().checkAuthorization(username, password)) {

                        UiUtil.showSnackbar(mainLayout, LOGIN_SUCCESSFULLY, getResources().getString(R.color.task_app_green_dark));
                        Global.setOnlineUsername(username);
                        Constants.TIME_HANDLER.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(TaskActivity.newIntent(getActivity()));
                            }
                        }, 2000);

                    }

                } catch (EmptyFieldException e) {
                    UiUtil.showSnackbar(mainLayout, e.getMessage(), getResources().getString(R.color.task_app_red));
                } catch (BadAuthorizationException e) {
                    UiUtil.showSnackbar(mainLayout, e.getMessage(), getResources().getString(R.color.task_app_red));
                }

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
