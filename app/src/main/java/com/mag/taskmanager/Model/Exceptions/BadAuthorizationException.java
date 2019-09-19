package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class BadAuthorizationException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "Your username or password is incorrect.";
    }

}
