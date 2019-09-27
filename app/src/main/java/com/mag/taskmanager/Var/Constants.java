package com.mag.taskmanager.Var;


import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mag.taskmanager.R;

import java.text.SimpleDateFormat;

public class Constants {

    public static final Handler TIME_HANDLER = new Handler();

    public static final String EMPTY_STRING = "";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat CLOCK_FORMAT = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy/MM/dd  HH:mm");

}
