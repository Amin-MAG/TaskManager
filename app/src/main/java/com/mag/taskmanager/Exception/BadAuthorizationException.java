package com.mag.taskmanager.Exception;

import androidx.annotation.Nullable;

public class BadAuthorizationException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "Your username or password is incorrect.";
    }

}
