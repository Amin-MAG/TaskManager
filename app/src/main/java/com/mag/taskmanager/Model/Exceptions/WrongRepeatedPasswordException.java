package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class WrongRepeatedPasswordException extends Exception {


    public static final String THE_REPEATED_PASSWORD_DOES_NOT_MATCH = "The repeated password does not match.";

    @Nullable
    @Override
    public String getMessage() {
        return THE_REPEATED_PASSWORD_DOES_NOT_MATCH;
    }

}
