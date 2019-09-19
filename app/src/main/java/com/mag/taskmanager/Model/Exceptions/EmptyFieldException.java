package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class EmptyFieldException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "Field is empty.";
    }
}
