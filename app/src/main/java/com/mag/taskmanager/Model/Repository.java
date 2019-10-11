package com.mag.taskmanager.Model;

import android.content.Context;

import com.mag.taskmanager.Model.DatabaseORM.GreenDaoApplication;
import com.mag.taskmanager.Model.Exceptions.BadAuthorizationException;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.util.List;

public class Repository {

    private static Repository instance;

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }


    // Repository

    private Repository() {
        daoSession = GreenDaoApplication.getInstance().getDaoSession();
        taskDao = daoSession.getTaskDao();
        userDao = daoSession.getUserDao();
    }


    // Database

    private DaoSession daoSession;
    private UserDao userDao;
    private TaskDao taskDao;

    public List<User> getUsers() {
        return userDao.queryBuilder().where(UserDao.Properties.IsAdmin.notEq(true)).list();
    }

    public User getUserByUsername(String username) {
        return userDao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique();
    }

    public void addUser(User user) {
        userDao.insert(user);
    }

    public void deleteAllTaskAndUser(long id) {
        taskDao.queryBuilder().where(TaskDao.Properties.UserRelatedId.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
        userDao.queryBuilder().where(UserDao.Properties.Id.eq(id)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

    public boolean checkAuthorization(String username, String password) throws BadAuthorizationException {

        User user = getUserByUsername(username);

        if (user == null || !user.getPassword().equals(password))
            throw new BadAuthorizationException();

        return true;
    }

    //


    public File getPhotoFileName(Task task, Context context) {
        return new File(context.getFilesDir(), task.getPhotoFilename());
    }


    //

    public Task getTask(Long taskId) {
        return taskDao.queryBuilder().where(TaskDao.Properties.TaskId.eq(taskId)).unique();
    }

    public long getTasksCount(Long userId) {
        return taskDao.queryBuilder().where(TaskDao.Properties.UserRelatedId.eq(userId)).count();
    }

    public List<Task> getTasks(Long userId, TaskStatus status) {
        return getTasks(userId, status, null);
    }

    public List<Task> getTasks(TaskStatus status, String search) {

        QueryBuilder<Task> queryBuilder = taskDao.queryBuilder();

        return
                search == null ?
                        queryBuilder.where(
                                TaskDao.Properties.TaskStatus.eq(status.getIndex()))
                                .list()
                        :
                        queryBuilder.where(
                                TaskDao.Properties.TaskStatus.eq(status.getIndex()),
                                queryBuilder.or(TaskDao.Properties.Title.like("%" + search + "%"), TaskDao.Properties.Description.like("%" + search + "%")))
                                .list();

    }

    public List<Task> getTasks(Long userId, TaskStatus status, String search) {

        QueryBuilder<Task> queryBuilder = taskDao.queryBuilder();

        return
                search == null ?
                        queryBuilder.where(
                                TaskDao.Properties.UserRelatedId.eq(userId),
                                TaskDao.Properties.TaskStatus.eq(status.getIndex()))
                                .list()
                        :
                        queryBuilder.where(
                                TaskDao.Properties.UserRelatedId.eq(userId),
                                TaskDao.Properties.TaskStatus.eq(status.getIndex()),
                                queryBuilder.or(TaskDao.Properties.Title.like("%" + search + "%"), TaskDao.Properties.Description.like("%" + search + "%")))
                                .list();

    }

    public void addTaskForUser(Task task) {
        taskDao.insert(task);
    }

    public void deleteTaskForUser(Task task) {
        taskDao.delete(task);
    }

    public void updateTaskForUser(Task task) {
        taskDao.update(task);
    }

    public void clearTasksForUser(Long userId) {
        taskDao.queryBuilder().where(TaskDao.Properties.UserRelatedId.eq(userId)).buildDelete().executeDeleteWithoutDetachingEntities();
    }

}
