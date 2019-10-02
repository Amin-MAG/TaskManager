package com.mag.taskmanager.Controller.Adapters;

import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.*;

import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskRecycleHolder> {

    private Activity activity;
    private OnItemClickListener listener;
    private List<Task> tasks;

    public TaskRecyclerAdapter(List<Task> tasks, OnItemClickListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.layout_task, parent, false);
        return new TaskRecycleHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull TaskRecycleHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskRecycleHolder extends RecyclerView.ViewHolder {

        public static final String EN_ALPHA_0 = "en_alpha0";
        public static final String EN_ALPHA = "en_alpha";
        public static final String DRAWABLE = "drawable";
        public static final int TWO_DIGIT = 10;
        public static final int NUMBER_OF_ALPHABET = 27;
        public static final int SPECEFIC_ASCI_CODE_OF_A = 96;

        private ImageView imageView;
        private TextView taskTitle, taskDate;

        public TaskRecycleHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.taskLayout_image);
            taskTitle = itemView.findViewById(R.id.taskLayout_title);
            taskDate = itemView.findViewById(R.id.taskLayout_date);

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void bind(final Task task) {

            taskTitle.setText(task.getTitle());
            taskDate.setText(Constants.TIME_FORMAT.format(task.getDate()));

            setImage(task.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.showEditDialog(task);
                }
            });

        }

        private void setImage(String titile) {

            String buttonFileName;
            int buttonFileNum = (int) titile.toLowerCase().charAt(0) - SPECEFIC_ASCI_CODE_OF_A;
            if (buttonFileNum < TWO_DIGIT) buttonFileName = EN_ALPHA_0 + buttonFileNum;
            else buttonFileName = EN_ALPHA + buttonFileNum;
            if (buttonFileNum < NUMBER_OF_ALPHABET && buttonFileNum > 0) {
                int resID = activity.getResources().getIdentifier(buttonFileName, DRAWABLE, activity.getPackageName());
                imageView.setImageResource(resID);
            } else
                imageView.setImageResource(R.drawable.alpha);

        }

    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public interface OnItemClickListener {
        void showEditDialog(Task task);
    }

}
