package com.mag.taskmanager.Var;

public class Global {
    private static String onlineUsername;

    public static String getOnlineUsername() {
        return onlineUsername;
    }

    public static void setOnlineUsername(String onlineUsername) {
        Global.onlineUsername = onlineUsername;
    }

}
