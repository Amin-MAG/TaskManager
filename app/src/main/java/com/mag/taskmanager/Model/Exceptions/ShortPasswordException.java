package com.mag.taskmanager.Model.Exceptions;

import androidx.annotation.Nullable;

public class ShortPasswordException extends Exception {

    public static final String PASSWORD_SHOULD_BE_MORE_THAN_4_NUMBERS = "Password should be more than 4 numbers.";

    @Nullable
    @Override
    public String getMessage() {
        return PASSWORD_SHOULD_BE_MORE_THAN_4_NUMBERS;
    }

}
