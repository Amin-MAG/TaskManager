package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.mag.taskmanager.Exception.EmptyFieldException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.UiUtil;
import com.mag.taskmanager.Var.Constants;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    TextInputEditText title, description;
    MaterialButton cancel, create, date, time;

    public static AddTaskFragment newInstance(String username) {

        Bundle args = new Bundle();
        args.putString("arg_username", username);

        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public AddTaskFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_task, null, false);

        // Title

        String titleText = "Add Task";
        ForegroundColorSpan foregroundColorSpanTtile = new ForegroundColorSpan(Color.parseColor(getResources().getString(R.color.task_app_white)));
        SpannableStringBuilder ssBuilderTitle = new SpannableStringBuilder(titleText);
        ssBuilderTitle.setSpan(
                foregroundColorSpanTtile,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Dialog Box

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(ssBuilderTitle)
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.task_app_dark))));

        title = view.findViewById(R.id.addTaskFragment_title);
        description = view.findViewById(R.id.addTaskFragment_description);
        date = view.findViewById(R.id.addTaskFragment_dateBtn);
        time = view.findViewById(R.id.addTaskFragment_timeBtn);
        create = view.findViewById(R.id.addTaskFragment_create);
        cancel = view.findViewById(R.id.addTaskFragment_cancel);

        final Fragment fragment = getTargetFragment();
        final Intent intent = new Intent();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskTitle = title.getText().toString();
                String taskDescription = description.getText().toString();
                Date taskDate = new Date();


                try {

                    if (taskTitle.equals(Constants.EMPTY_STRING) || taskDescription.equals(Constants.EMPTY_STRING))
                        throw new EmptyFieldException();

                    Repository.getInstance().getUserByUsername(getArguments().getString("arg_username")).addTask(new Task(taskTitle, taskDescription, taskDate, TaskStatus.TODO));
                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);

                } catch (EmptyFieldException e) {
                    intent.putExtra("dialog_error",e.getMessage() );
                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
                } finally {
                    dismiss();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("dialog_error", getResources().getString(R.string.cancel_add_item));
                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
                dismiss();
            }
        });


        return dialog;
    }

}
