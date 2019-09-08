package com.mag.taskmanager.RecyclerAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.taskmanager.Model.Task;
import com.mag.taskmanager.R;

import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.TaskRecycleHolder> {

    private List<Task> tasks;

    public TaskRecyclerAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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

        ImageView imageView;
        TextView taskTitle, taskDate;

        public TaskRecycleHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.taskLayout_image);
            taskTitle = itemView.findViewById(R.id.taskLayout_title);
            taskDate = itemView.findViewById(R.id.taskLayout_date);

        }

        public  void bind(final  Task task) {

            taskTitle.setText(task.getTitle());
            taskDate.setText(task.getDate().toString());

        }

    }


}
