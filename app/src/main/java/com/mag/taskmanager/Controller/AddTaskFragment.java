package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mag.taskmanager.Model.Exceptions.EmptyFieldException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.*;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    public static final int ONE_DAY_MILI_SECONDS = 24 * 60 * 60 * 1000;
    public static final int REQUEST_CODE_FOR_DATE_PICKER = 1001;
    public static final int REQUEST_CODE_FOR_TIME_PICKER = 1002;
    public static final String ADD_TASK_FRAGMENT_DATE_PICKER = "add_task_fragment_date_picker";
    public static final String ADD_TASK_FRAGMENT_TIME_PICKER = "add_task_fragment_time_picker";
    public static final String DIALOG_ERROR = "dialog_error";
    public static final String HAS_ERROR = "has_error";
    public static final String DATE_PICKER_RESULT = "date_picker_result";
    public static final String TIME_PICKER_RESULT = "time_picker_result";

    private TextInputEditText title, description;
    private MaterialButton cancel, create, date, time;

    private Date selectedDate;

    public static AddTaskFragment newInstance() {

        Bundle args = new Bundle();

        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public AddTaskFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_FOR_DATE_PICKER:
                if (resultCode == Activity.RESULT_OK) {
                    Date sTime = (Date) data.getSerializableExtra(DATE_PICKER_RESULT);
                    selectedDate.setDate(sTime.getDate());
                    selectedDate.setMonth(sTime.getMonth());
                    selectedDate.setYear(sTime.getYear());
                }
                break;
            case REQUEST_CODE_FOR_TIME_PICKER:
                if (resultCode == Activity.RESULT_OK) {
                    Date sTime = (Date) data.getSerializableExtra(TIME_PICKER_RESULT);
                    selectedDate.setHours(sTime.getHours());
                    selectedDate.setMinutes(sTime.getMinutes());
                }
                break;
            default:
                break;
        }

        if (resultCode == Activity.RESULT_OK){
            date.setText(Constants.DATE_FORMAT.format(selectedDate));
            date.setText(Constants.DATE_FORMAT.format(selectedDate));
            time.setText(Constants.CLOCK_FORMAT.format(selectedDate));
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDate = new Date(System.currentTimeMillis());
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

        String titleText = getString(R.string.add_task);
        ForegroundColorSpan foregroundColorSpanTtile = new ForegroundColorSpan(Color.parseColor(getResources().getString(R.color.task_app_white)));
        SpannableStringBuilder titleString = new SpannableStringBuilder(titleText);
        titleString.setSpan(
                foregroundColorSpanTtile,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        // Dialog Box

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(titleString)
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.task_app_dark))));


        title = view.findViewById(R.id.addTaskFragment_title);
        description = view.findViewById(R.id.addTaskFragment_description);
        date = view.findViewById(R.id.addTaskFragment_dateBtn);
        time = view.findViewById(R.id.addTaskFragment_timeBtn);
        create = view.findViewById(R.id.addTaskFragment_create);
        cancel = view.findViewById(R.id.addTaskFragment_cancel);


        date.setText(Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS)));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS));
                datePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_FOR_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), ADD_TASK_FRAGMENT_DATE_PICKER);
            }
        });


        time.setText(Constants.CLOCK_FORMAT.format(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS)));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment= TimePickerFragment.newInstance(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS));
                timePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_FOR_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), ADD_TASK_FRAGMENT_TIME_PICKER);
            }
        });


        final Fragment fragment = getTargetFragment();
        final Intent intent = new Intent();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskTitle = title.getText().toString();
                String taskDescription = description.getText().toString();
                Date taskDate = selectedDate;

                try {
                    if (taskTitle.equals(Constants.EMPTY_STRING) || taskDescription.equals(Constants.EMPTY_STRING))
                        throw new EmptyFieldException();
                    Repository.getInstance().getUserByUsername(Global.getOnlineUsername()).addTask(new Task(taskTitle, taskDescription, taskDate, TaskStatus.TODO));
                } catch (EmptyFieldException e) {
                    intent.putExtra(DIALOG_ERROR,e.getMessage() );
                    intent.putExtra(HAS_ERROR,1 );
                } finally {
                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
                dismiss();
            }
        });

        return dialog;
    }

}
