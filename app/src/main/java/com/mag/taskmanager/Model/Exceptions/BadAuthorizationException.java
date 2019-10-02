package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class BadAuthorizationException extends Exception {

    public static final String YOUR_USERNAME_OR_PASSWORD_IS_INCORRECT = "Your username or password is incorrect.";

    @Nullable
    @Override
    public String getMessage() {
        return YOUR_USERNAME_OR_PASSWORD_IS_INCORRECT;
    }

}
