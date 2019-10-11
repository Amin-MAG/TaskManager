package com.mag.taskmanager.Controller.Adapters;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.User;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Var.Constants;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserRecycleHolder> {

    private Activity activity;
    private List<User> users;

    public UserRecyclerAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserRecycleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        activity = (Activity) parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.layout_user, parent, false);
        return new UserRecycleHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull UserRecycleHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserRecycleHolder extends RecyclerView.ViewHolder {

        private TextView username, userDate, numberOfTasks;

        public UserRecycleHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userLayout_username);
            userDate = itemView.findViewById(R.id.userLayout_date);
            numberOfTasks = itemView.findViewById(R.id.userLayout_tasks);

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void bind(final User user) {

            username.setText(user.getUsername());
            userDate.setText("Start : " + Constants.DATE_FORMAT.format(user.getDate()));
            numberOfTasks.setText("Tasks Count : " + Repository.getInstance().getTasksCount(user.getId()));

        }

    }

}
