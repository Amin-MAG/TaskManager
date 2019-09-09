package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mag.taskmanager.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    public static AddTaskFragment newInstance() {

        Bundle args = new Bundle();

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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
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

        // Button (Add)

        String createText =  "Create";
        ForegroundColorSpan foregroundColorSpanCreateBtn = new ForegroundColorSpan(Color.parseColor(getResources().getString(R.color.black)));
        SpannableStringBuilder ssBuilderCreateBtn = new SpannableStringBuilder(createText);
        ssBuilderCreateBtn.setSpan(
                foregroundColorSpanCreateBtn,
                0,
                createText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(ssBuilderTitle)
                .setPositiveButton(ssBuilderCreateBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.task_app_dark))));


        return dialog;
    }

}
