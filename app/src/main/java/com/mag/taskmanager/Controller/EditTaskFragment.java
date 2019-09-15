package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mag.taskmanager.Model.Exception.EmptyFieldException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.Constants;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTaskFragment extends DialogFragment {

    public static final String ARG_USERNAME = "arg_username";

    private TextInputEditText title, description;
    private MaterialButton edit, delete, cancel, date, time;

    private Date selectedDate;


    public static EditTaskFragment newInstance(String username) {

        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);

        EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditTaskFragment() {
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQUEST_CODE_FOR_DATE_PICKER:
//                if (resultCode == Activity.RESULT_OK) {
//                    Date sTime = (Date) data.getSerializableExtra(DATE_PICKER_RESULT);
//                    selectedDate.setDate(sTime.getDate());
//                    selectedDate.setMonth(sTime.getMonth());
//                    selectedDate.setYear(sTime.getYear());
//                }
//                break;
//            case REQUEST_CODE_FOR_TIME_PICKER:
//                if (resultCode == Activity.RESULT_OK) {
//                    Date sTime = (Date) data.getSerializableExtra(TIME_PICKER_RESULT);
//                    selectedDate.setHours(sTime.getHours());
//                    selectedDate.setMinutes(sTime.getMinutes());
//                }
//                break;
//            default:
//                break;
//        }
//
//        if (resultCode == Activity.RESULT_OK){
//            date.setText(Constants.DATE_FORMAT.format(selectedDate));
//            date.setText(Constants.DATE_FORMAT.format(selectedDate));
//            time.setText(Constants.CLOCK_FORMAT.format(selectedDate));
//        }
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedDate = new Date(System.currentTimeMillis());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_task, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_task, null, false);


        final String username = getArguments().getString(ARG_USERNAME);

        // Title

        String titleText = getString(R.string.edit_task);
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


        title = view.findViewById(R.id.editTaskFragment_title);
        description = view.findViewById(R.id.editTaskFragment_description);
        date = view.findViewById(R.id.editTaskFragment_dateBtn);
        time = view.findViewById(R.id.editTaskFragment_timeBtn);
        edit = view.findViewById(R.id.editTaskFragment_edit);
        cancel = view.findViewById(R.id.editTaskFragment_cancel);
        delete = view.findViewById(R.id.editTaskFragment_delete);


//        date.setText(Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS)));
//        date.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS));
//                datePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_FOR_DATE_PICKER);
//                datePickerFragment.show(getFragmentManager(), ADD_TASK_FRAGMENT_DATE_PICKER);
//            }
//        });
//
//
//        time.setText(Constants.CLOCK_FORMAT.format(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS)));
//        time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TimePickerFragment timePickerFragment= TimePickerFragment.newInstance(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS));
//                timePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_FOR_TIME_PICKER);
//                timePickerFragment.show(getFragmentManager(), ADD_TASK_FRAGMENT_TIME_PICKER);
//            }
//        });


        final Fragment fragment = getTargetFragment();
        final Intent intent = new Intent();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
