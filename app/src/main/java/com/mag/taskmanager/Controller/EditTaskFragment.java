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
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mag.taskmanager.Model.Exception.EmptyFieldException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.Constants;

import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTaskFragment extends DialogFragment {

    public static final String ARG_USERNAME = "arg_username";
    public static final String ARG_TASK = "arg_task";
    public static final int REQUEST_CODE_FOR_DATE_PICKER = 1001;
    public static final int REQUEST_CODE_FOR_TIME_PICKER = 1002;
    public static final String EDIT_TASK_FRAGMENT_DATE_PICKER = "edit_task_fragment_date_picker";
    public static final String EDIT_TASK_FRAGMENT_TIME_PICKER = "edit_task_fragment_time_picker";
    public static final String DIALOG_ERROR = "dialog_error";
    public static final String HAS_ERROR = "has_error";
    public static final String DATE_PICKER_RESULT = "date_picker_result";
    public static final String TIME_PICKER_RESULT = "time_picker_result";
    public static final String ACTION_STRING = "action_string";
    public static final String DELETE_TASK = "delete_task";
    public static final String ACTION_STRING1 = "action_string";
    public static final String EDIT_TASK = "edit_task";

    private TextInputEditText title, description;
    private MaterialButton edit, delete, cancel, date, time;
    private HashMap<TaskStatus, RadioButton> radioButtons = new HashMap<>();

    private Task selectedTask;
    private Date selectedDate;

    public static EditTaskFragment newInstance(String username, Task task) {

        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putSerializable(ARG_TASK, task);

        EditTaskFragment fragment = new EditTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public EditTaskFragment() {
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

        if (resultCode == Activity.RESULT_OK) {
            date.setText(Constants.DATE_FORMAT.format(selectedDate));
            date.setText(Constants.DATE_FORMAT.format(selectedDate));
            time.setText(Constants.CLOCK_FORMAT.format(selectedDate));
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedTask = (Task) getArguments().getSerializable(ARG_TASK);
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
        radioButtons.put(TaskStatus.TODO,  (RadioButton) view.findViewById(R.id.edtiFragment_radioBtn_todo));
        radioButtons.put(TaskStatus.DOING,  (RadioButton) view.findViewById(R.id.edtiFragment_radioBtn_doing));
        radioButtons.put(TaskStatus.DONE,  (RadioButton) view.findViewById(R.id.edtiFragment_radioBtn_done));

        title.setText(selectedTask.getTitle());
        description.setText(selectedTask.getDescription());
        date.setText(Constants.DATE_FORMAT.format(selectedTask.getDate()));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(selectedTask.getDate());
                datePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_FOR_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), EDIT_TASK_FRAGMENT_DATE_PICKER);
            }
        });


        time.setText(Constants.CLOCK_FORMAT.format(selectedTask.getDate()));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(selectedTask.getDate());
                timePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_FOR_TIME_PICKER);
                timePickerFragment.show(getFragmentManager(), EDIT_TASK_FRAGMENT_TIME_PICKER);
            }
        });


        final Fragment fragment = getTargetFragment();
        final Intent intent = new Intent();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String taskTitle = title.getText().toString();
                String taskDescription = description.getText().toString();
                Date taskDate = selectedDate;
                TaskStatus taskStatus = TaskStatus.TODO;

                try {
                    if (taskTitle.equals(Constants.EMPTY_STRING) || taskDescription.equals(Constants.EMPTY_STRING))
                        throw new EmptyFieldException();

                    selectedTask.setTitle(taskTitle);
                    selectedTask.setDescription(taskDescription);
                    selectedTask.setDate(taskDate);
                    for (TaskStatus key : radioButtons.keySet()) {
                        if (radioButtons.get(key).isChecked()){
                            selectedTask.setTaskStatus(key);
                            break;
                        }
                    }

                } catch (EmptyFieldException e) {
                    intent.putExtra(DIALOG_ERROR, e.getMessage());
                    intent.putExtra(HAS_ERROR, 1);
                } finally {
                    intent.putExtra(ACTION_STRING1, EDIT_TASK);
                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Repository.getInstance().getUserByUsername(username).deleteTaskWithId(selectedTask.getTaskId());
                intent.putExtra(ACTION_STRING, DELETE_TASK);
                intent.putExtra(HAS_ERROR, 0);
                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();

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
