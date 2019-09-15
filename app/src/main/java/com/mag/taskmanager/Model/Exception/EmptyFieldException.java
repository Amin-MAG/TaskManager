package com.mag.taskmanager.Model.Exception;

import androidx.annotation.Nullable;

public class EmptyFieldException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return "Field is empty.";
    }
}
