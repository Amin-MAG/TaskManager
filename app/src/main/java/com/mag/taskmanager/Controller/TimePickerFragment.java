package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.button.MaterialButton;
import com.mag.taskmanager.R;

import java.sql.Time;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    private TimePicker timePicker;
    private MaterialButton set;

    private Date date;


    public static TimePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable("arg_time", date);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public TimePickerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = (Date) getArguments().getSerializable("arg_date");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time_picker, container, false);
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker, null, false);

        timePicker = view.findViewById(R.id.time_picker);
        set = view.findViewById(R.id.timepickerFragment_set);

        // Title

        String titleText = "Time Picker";
        ForegroundColorSpan foregroundColorSpanTtile = new ForegroundColorSpan(Color.parseColor(getResources().getString(R.color.task_app_white)));
        SpannableStringBuilder titleString = new SpannableStringBuilder(titleText);
        titleString.setSpan(
                foregroundColorSpanTtile,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(titleString)
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.task_app_dark))));


        final Fragment fragment = getTargetFragment();
        final Intent intent = new Intent();

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

            }
        });


        return dialog;
    }


}
