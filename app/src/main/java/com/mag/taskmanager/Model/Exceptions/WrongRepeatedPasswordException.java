package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class WrongRepeatedPasswordException extends Exception {


    @Nullable
    @Override
    public String getMessage() {
        return "The repeated password does not match.";
    }

}
