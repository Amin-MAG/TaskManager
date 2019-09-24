package com.mag.taskmanager.Var;

public class Global {

    private static String onlineUsername;
    private static String onlineUserId;

    public static String getOnlineUsername() {
        return onlineUsername;
    }

    public static void setOnlineUsername(String onlineUsername) {
        Global.onlineUsername = onlineUsername;
    }

    public static String getOnlineUserID() {
        return onlineUserId;
    }

    public static void setOnlineUserID(String onlineUsername) {
        Global.onlineUserId = onlineUsername;
    }


}
