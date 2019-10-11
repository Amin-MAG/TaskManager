package com.mag.taskmanager.Controller;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mag.taskmanager.Model.Exceptions.EmptyFieldException;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.Model.TaskStatus;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.PictureUtils;
import com.mag.taskmanager.Var.Constants;
import com.mag.taskmanager.Var.Global;

import java.io.File;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddTaskFragment extends DialogFragment {

    public static final int ONE_DAY_MILI_SECONDS = 24 * 60 * 60 * 1000;
    public static final int REQUEST_CODE_FOR_DATE_PICKER = 1001;
    public static final int REQUEST_CODE_FOR_TIME_PICKER = 1002;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1005;
    public static final String ADD_TASK_FRAGMENT_DATE_PICKER = "add_task_fragment_date_picker";
    public static final String ADD_TASK_FRAGMENT_TIME_PICKER = "add_task_fragment_time_picker";
    public static final String DIALOG_ERROR = "dialog_error";
    public static final String HAS_ERROR = "has_error";
    public static final String DATE_PICKER_RESULT = "date_picker_result";
    public static final String TIME_PICKER_RESULT = "time_picker_result";
    public static final String FILE_PROVIDER_AUTHORITY = "com.mag.taskmanager.fileProvider";


    private TextInputEditText title, description;
    private MaterialButton cancel, create, date, time, setPiv;

    private Intent cameraIntent;
    private Uri photoUri;
    private File photoFile;

    private Date selectedDate;
    private Task addedTask;

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

            case REQUEST_CODE_CAPTURE_IMAGE:

                getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (PictureUtils.getScaleBitmap(photoFile.getAbsolutePath(), 512, 512) != null) {
                    addedTask.setImagePath(photoFile.getPath());
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
        selectedDate = new Date(System.currentTimeMillis());
        addedTask = new Task();
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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


        findItems(view);

        setText();

        setEvents();

        return dialog;
    }

    private void setText() {
        time.setText(Constants.CLOCK_FORMAT.format(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS)));
        date.setText(Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS)));
    }

    private void findItems(View view) {
        title = view.findViewById(R.id.addTaskFragment_title);
        description = view.findViewById(R.id.addTaskFragment_description);
        date = view.findViewById(R.id.addTaskFragment_dateBtn);
        time = view.findViewById(R.id.addTaskFragment_timeBtn);
        create = view.findViewById(R.id.addTaskFragment_create);
        setPiv = view.findViewById(R.id.addTaskFragment_setPic);
        cancel = view.findViewById(R.id.addTaskFragment_cancel);
    }

    private void setEvents() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS));
                datePickerFragment.setTargetFragment(AddTaskFragment.this, REQUEST_CODE_FOR_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), ADD_TASK_FRAGMENT_DATE_PICKER);
            }
        });


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFragment timePickerFragment = TimePickerFragment.newInstance(new Date(System.currentTimeMillis() + ONE_DAY_MILI_SECONDS));
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

                    addedTask.setUserRelatedId(Global.getOnlineUserID());
                    addedTask.setTitle(taskTitle);
                    addedTask.setDescription(taskDescription);
                    addedTask.setDate(taskDate);
                    addedTask.setTaskStatus(TaskStatus.TODO);

                    Repository.getInstance().addTaskForUser(addedTask);
                } catch (EmptyFieldException e) {
                    intent.putExtra(DIALOG_ERROR, e.getMessage());
                    intent.putExtra(HAS_ERROR, 1);
                } finally {
                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }

            }
        });

        setPiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photoFile = Repository.getInstance().getPhotoFileName(addedTask, getContext());
                photoUri = FileProvider.getUriForFile(getContext(), FILE_PROVIDER_AUTHORITY, photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                PictureUtils.grantCameraPermission(photoUri, getActivity(), cameraIntent);
                startActivityForResult(cameraIntent, REQUEST_CODE_CAPTURE_IMAGE);

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, intent);
                dismiss();
            }
        });

    }


}
