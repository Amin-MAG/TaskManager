package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.google.android.material.button.MaterialButton;
import com.mag.taskmanager.Exception.EmptyFieldException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment {

    private DatePicker datePicker;
    private MaterialButton set;

    private Date date;

    public static DatePickerFragment newInstance(Date date) {

        Bundle args = new Bundle();
        args.putSerializable("arg_date", date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DatePickerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = (Date) getArguments().getSerializable("arg_date");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker, null, false);

        datePicker = view.findViewById(R.id.date_picker);
        set = view.findViewById(R.id.datepickerFragment_set);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.task_app_darkest))));


        final Fragment fragment = getTargetFragment();
        final Intent intent = new Intent();

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int year = datePicker.getYear();
                int monthOfYear = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();

                GregorianCalendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);

//                intent.putExtra("date_picker_result", new Date(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth()));
                intent.putExtra("date_picker_result", calendar.getTime());
                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                Log.d("someBug", "" + datePicker.getYear());
                dismiss();

            }
        });


        return dialog;
    }

}
