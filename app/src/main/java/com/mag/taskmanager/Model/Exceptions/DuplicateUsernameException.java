package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class DuplicateUsernameException extends Exception {

    public static final String THIS_USER_ALREADY_EXISTS = "This user already exists.";

    @Nullable
    @Override
    public String getMessage() {
        return THIS_USER_ALREADY_EXISTS;
    }

}
