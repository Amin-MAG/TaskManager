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
import com.mag.taskmanager.Model.Exception.DuplicateUsernameException;
import com.mag.taskmanager.Model.Exception.EmptyFieldException;
import com.mag.taskmanager.Model.Exception.ShortPasswordException;
import com.mag.taskmanager.Model.Exception.WrongRepeatedPasswordException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.User;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.*;
import com.mag.taskmanager.Var.*;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    public static final String REGISTRATION_COMPLETED = "Registration completed";

    private LinearLayout mainLayout;
    private TextInputEditText usernameEditText, passwordEditText, passwordRepeatEditText;
    private MaterialButton submitBtn;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLayout = view.findViewById(R.id.registerFragment_mainLayout);
        usernameEditText = view.findViewById(R.id.registerFragment_username);
        passwordEditText = view.findViewById(R.id.registerFragment_password);
        passwordRepeatEditText = view.findViewById(R.id.registerFragment_passwordR);
        submitBtn = view.findViewById(R.id.registerFragment_registerBtn);


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {

                String usernameText = usernameEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();
                String passwordRepeatText = passwordRepeatEditText.getText().toString();

                try {

                    if (usernameText.equals(Constants.EMPTY_STRING) || passwordText.equals(Constants.EMPTY_STRING) || passwordRepeatText.equals(Constants.EMPTY_STRING))
                        throw new EmptyFieldException();
                    if (Repository.getInstance().getUserByUsername(usernameText) != null)
                        throw new DuplicateUsernameException();
                    if (!passwordRepeatText.equals(passwordText))
                        throw new WrongRepeatedPasswordException();
                    if (passwordText.length() < 5)
                        throw new ShortPasswordException();

                    Repository.getInstance().addUser(new User(usernameText, passwordText, new ArrayList<Task>()));

                    UiUtil.showSnackbar(mainLayout,REGISTRATION_COMPLETED, getResources().getString(R.color.task_app_green_dark));

                    Constants.TIME_HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getActivity().finish();
                        }
                    }, 2000);

                } catch (EmptyFieldException e) {
                    UiUtil.showSnackbar(mainLayout,e.getMessage(), getResources().getString(R.color.task_app_red));
                } catch (ShortPasswordException e) {
                    UiUtil.showSnackbar(mainLayout,e.getMessage(), getResources().getString(R.color.task_app_red));
                } catch (WrongRepeatedPasswordException e) {
                    UiUtil. showSnackbar(mainLayout,e.getMessage(), getResources().getString(R.color.task_app_red));
                } catch (DuplicateUsernameException e) {
                    UiUtil.showSnackbar(mainLayout,e.getMessage(), getResources().getString(R.color.task_app_red));
                }


            }
        });


    }


}
