package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class EmptyFieldException extends Exception {

    public static final String FIELD_IS_EMPTY = "Field is empty.";

    @Nullable
    @Override
    public String getMessage() {
        return FIELD_IS_EMPTY;
    }
}
