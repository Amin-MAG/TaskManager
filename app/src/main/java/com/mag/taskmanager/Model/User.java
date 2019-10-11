package com.mag.taskmanager.Model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;

@Entity
public class User {

    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String username;
    private String password;
    private Date date;
    private boolean isAdmin;

    @Generated(hash = 1275843927)
    public User(Long id, String username, String password, Date date,
            boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.date = date;
        this.isAdmin = isAdmin;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
