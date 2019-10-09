package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.Global;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteAllFragment extends DialogFragment {

    private MaterialButton yesBtn, noBtn;

    public static DeleteAllFragment newInstance() {

        Bundle args = new Bundle();

        DeleteAllFragment fragment = new DeleteAllFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DeleteAllFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delete_all, container, false);
    }

    @SuppressLint("ResourceType")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_delete_all, null, false);

        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor(getResources().getString(R.color.task_app_dark))));


        yesBtn = view.findViewById(R.id.deleteAllFragment_yes);
        noBtn = view.findViewById(R.id.deleteAllFragment_no);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Repository.getInstance().clearTasksForUser(Global.getOnlineUserID());
                dismiss();
                startActivity(TaskActivity.newIntent(getActivity()));
                getActivity().finish();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        return dialog;
    }

}
