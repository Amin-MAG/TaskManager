package com.mag.taskmanager.Controller.RecyclerAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.*;

import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskRecycleHolder> {

    private OnItemClickListener listener;
    private List<Task> tasks;

    public TaskRecyclerAdapter(List<Task> tasks, OnItemClickListener listener)  {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Activity activity = (Activity) parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.layout_task, parent, false);
        return new TaskRecycleHolder(view);
    }

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

        private ImageView imageView;
        private TextView taskTitle, taskDate;

        public TaskRecycleHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.taskLayout_image);
            taskTitle = itemView.findViewById(R.id.taskLayout_title);
            taskDate = itemView.findViewById(R.id.taskLayout_date);

        }

        public  void bind(final  Task task) {

            taskTitle.setText(task.getTitle());
            taskDate.setText(Constants.TIME_FORMAT.format(task.getDate()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.showEditDialog(task);
                }
            });

        }

    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public  interface OnItemClickListener {
        void showEditDialog(Task task);
    }

}
