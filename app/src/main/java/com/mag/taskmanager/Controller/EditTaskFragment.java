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
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
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
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTaskFragment extends DialogFragment {

    private static final String ARG_TASK = "arg_task";
    private static final int REQUEST_CODE_FOR_DATE_PICKER = 1001;
    private static final int REQUEST_CODE_FOR_TIME_PICKER = 1002;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 1005;
    private static final String EDIT_TASK_FRAGMENT_DATE_PICKER = "edit_task_fragment_date_picker";
    private static final String EDIT_TASK_FRAGMENT_TIME_PICKER = "edit_task_fragment_time_picker";
    private static final String DIALOG_ERROR = "dialog_error";
    private static final String HAS_ERROR = "has_error";
    private static final String DATE_PICKER_RESULT = "date_picker_result";
    private static final String TIME_PICKER_RESULT = "time_picker_result";
    private static final String ACTION_STRING = "action_string";
    private static final String DELETE_TASK = "delete_task";
    private static final String EDIT_TASK = "edit_task";
    public static final String FILE_PROVIDER_AUTHORITY = "com.mag.taskmanager.fileProvider";


    private TextInputEditText title, description;
    private MaterialButton edit, delete, cancel, date, time, share, changeImage;
    private HashMap<TaskStatus, RadioButton> radioButtons = new HashMap<>();

    private Intent cameraIntent;
    private Uri photoUri;
    private File photoFile;

    private Task selectedTask;
    private Date selectedDate;

    public static EditTaskFragment newInstance(Task task) {

        Bundle args = new Bundle();
        args.putLong(ARG_TASK, task.getTaskId());

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

            case REQUEST_CODE_CAPTURE_IMAGE:

                getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (PictureUtils.getScaleBitmap(photoFile.getAbsolutePath(), 512, 512) != null) {
                    selectedTask.setImagePath(photoFile.getPath());
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
        selectedTask = Repository.getInstance().getTask(getArguments().getLong(ARG_TASK));
        selectedDate = new Date(System.currentTimeMillis());
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_task, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_task, null, false);

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


        findItems(view);

        setText();

        setEvents();


        return dialog;
    }

    private void setEvents() {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(selectedTask.getDate());
                datePickerFragment.setTargetFragment(EditTaskFragment.this, REQUEST_CODE_FOR_DATE_PICKER);
                datePickerFragment.show(getFragmentManager(), EDIT_TASK_FRAGMENT_DATE_PICKER);
            }
        });


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

                try {
                    if (taskTitle.equals(Constants.EMPTY_STRING) || taskDescription.equals(Constants.EMPTY_STRING))
                        throw new EmptyFieldException();

                    selectedTask.setTitle(taskTitle);
                    selectedTask.setDescription(taskDescription);
                    selectedTask.setDate(taskDate);
                    for (TaskStatus key : radioButtons.keySet()) {
                        if (radioButtons.get(key).isChecked()) {
                            selectedTask.setTaskStatus(key);
                            break;
                        }
                    }

                    Repository.getInstance().updateTaskForUser(selectedTask);

                } catch (EmptyFieldException e) {
                    intent.putExtra(DIALOG_ERROR, e.getMessage());
                    intent.putExtra(HAS_ERROR, 1);
                } finally {
                    intent.putExtra(ACTION_STRING, EDIT_TASK);
                    fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                    dismiss();
                }

            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(getTaskReport())
                        .getIntent();

                if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null)
                    startActivity(shareIntent);

                dismiss();

            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photoFile = Repository.getInstance().getPhotoFileName(selectedTask, getContext());
                photoUri = FileProvider.getUriForFile(getContext(), FILE_PROVIDER_AUTHORITY, photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                PictureUtils.grantCameraPermission(photoUri, getActivity(), cameraIntent);
                startActivityForResult(cameraIntent, REQUEST_CODE_CAPTURE_IMAGE);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Repository.getInstance().deleteTaskForUser(selectedTask);
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

    }

    @SuppressLint("StringFormatMatches")
    private String getTaskReport() {

        return getString(R.string.share_template,
                new String[]{
                        Global.getOnlineUsername(),
                        selectedTask.getTitle(),
                        selectedTask.getDescription(),
                        Constants.TIME_FORMAT.format(selectedTask.getDate())
                }
        );

    }

    private void setText() {
        title.setText(selectedTask.getTitle());
        description.setText(selectedTask.getDescription());
        time.setText(Constants.CLOCK_FORMAT.format(selectedTask.getDate()));
        date.setText(Constants.DATE_FORMAT.format(selectedTask.getDate()));
    }

    private void findItems(View view) {
        title = view.findViewById(R.id.editTaskFragment_title);
        description = view.findViewById(R.id.editTaskFragment_description);
        date = view.findViewById(R.id.editTaskFragment_dateBtn);
        time = view.findViewById(R.id.editTaskFragment_timeBtn);
        edit = view.findViewById(R.id.editTaskFragment_edit);
        cancel = view.findViewById(R.id.editTaskFragment_cancel);
        delete = view.findViewById(R.id.editTaskFragment_delete);
        share = view.findViewById(R.id.editTaskFragment_share);
        changeImage = view.findViewById(R.id.editTaskFragment_changeImage);
        radioButtons.put(TaskStatus.TODO, (RadioButton) view.findViewById(R.id.edtiFragment_radioBtn_todo));
        radioButtons.put(TaskStatus.DOING, (RadioButton) view.findViewById(R.id.edtiFragment_radioBtn_doing));
        radioButtons.put(TaskStatus.DONE, (RadioButton) view.findViewById(R.id.edtiFragment_radioBtn_done));
    }

}
