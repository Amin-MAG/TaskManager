package com.mag.taskmanager.Model;


import com.mag.taskmanager.Var.Global;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;

@Entity
public class Task {

    @Id(autoincrement = true)
    private Long taskId;
    private Long userRelatedId;
    @ToOne(joinProperty = "userRelatedId")
    private User user;
    private String title;
    private String description;
    private Date date;
    private String imagePath;
    @Convert(converter = TaskStatusConverter.class, columnType = Integer.class)
    private TaskStatus taskStatus;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1469429066)
    private transient TaskDao myDao;

    @Generated(hash = 613331817)
    public Task(Long taskId, Long userRelatedId, String title, String description, Date date,
            String imagePath, TaskStatus taskStatus) {
        this.taskId = taskId;
        this.userRelatedId = userRelatedId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.imagePath = imagePath;
        this.taskStatus = taskStatus;
    }

    @Generated(hash = 733837707)
    public Task() {
    }

    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;

    public String getPhotoFilename() {
        return "IMG_" + Global.getOnlineUsername() + "_" + System.nanoTime() + ".jpg";
    }

    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserRelatedId() {
        return this.userRelatedId;
    }

    public void setUserRelatedId(Long userRelatedId) {
        this.userRelatedId = userRelatedId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public TaskStatus getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1543765668)
    public User getUser() {
        Long __key = this.userRelatedId;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1124643752)
    public void setUser(User user) {
        synchronized (this) {
            this.user = user;
            userRelatedId = user == null ? null : user.getId();
            user__resolvedKey = userRelatedId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1442741304)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTaskDao() : null;
    }

}
