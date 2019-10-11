package com.mag.taskmanager.Controller.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.mag.taskmanager.Controller.UsersActivity;
import com.mag.taskmanager.Model.Repository;
import com.mag.taskmanager.Model.User;
import com.mag.taskmanager.R;
import com.mag.taskmanager.Util.UiUtil;
import com.mag.taskmanager.Var.Constants;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserRecycleHolder> {

    private Activity activity;
    private List<User> users;
    private FrameLayout mainLayout;

    public UserRecyclerAdapter(List<User> users,FrameLayout mainLayout) {
        this.users = users;
        this.mainLayout = mainLayout;
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

        public static final String TASKS_COUNT = "Tasks Count : ";
        public static final String START = "Start : ";

        private TextView username, userDate, numberOfTasks;
        private MaterialButton deleteBtn;

        public UserRecycleHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.userLayout_username);
            userDate = itemView.findViewById(R.id.userLayout_date);
            numberOfTasks = itemView.findViewById(R.id.userLayout_tasks);
            deleteBtn = itemView.findViewById(R.id.userLayout_delete);

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        public void bind(final User user) {

            username.setText(user.getUsername());
            userDate.setText(START + Constants.DATE_FORMAT.format(user.getDate()));
            numberOfTasks.setText(TASKS_COUNT + Repository.getInstance().getTasksCount(user.getId()));

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View view) {
                    Repository.getInstance().deleteAllTaskAndUser(user.getId());
                    UiUtil.showSnackbar(mainLayout, activity.getResources().getString(R.string.successfully_deleted), activity.getResources().getString(R.color.task_app_green_dark));
                    setUsers(Repository.getInstance().getUsers());
                    notifyDataSetChanged();
                }
            });

        }

    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
