package com.mag.taskmanager.Exception;

import androidx.annotation.Nullable;

public class ShortPasswordException extends Exception {

    @Nullable
    @Override
    public String getMessage() {
        return  "Password should be more than 4 numbers.";
    }

}
