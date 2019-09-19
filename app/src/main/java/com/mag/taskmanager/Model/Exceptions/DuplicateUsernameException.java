package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class DuplicateUsernameException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "This user already exists.";
    }

}
