package com.mag.taskmanager.Model;

import com.mag.taskmanager.Model.DatabaseORM.GreenDaoApplication;
import com.mag.taskmanager.Model.Exceptions.BadAuthorizationException;

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


    public User getUserByUsername(String username) {
        return userDao.queryBuilder().where(UserDao.Properties.Username.eq(username)).unique();
    }

    public void addUser(User user) {
        userDao.insert(user);
    }


    public boolean checkAuthorization(String username, String password) throws BadAuthorizationException {

        User user = getUserByUsername(username);

        if (user == null || !user.getPassword().equals(password))
            throw new BadAuthorizationException();

        return true;
    }

    public List<Task> getTasks(String username, TaskStatus status) {
        return getTasks(username, status, null);
    }

    public List<Task> getTasks(String username, TaskStatus status, String search) {
        return taskDao.queryBuilder().where(TaskDao.Properties.Title.like("% " + search + "%")).list();
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
        taskDao.queryBuilder().where(TaskDao.Properties.UserRelatedId.eq(userId));
    }


}
