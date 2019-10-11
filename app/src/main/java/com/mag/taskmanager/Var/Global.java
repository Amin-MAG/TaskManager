package com.mag.taskmanager.Var;

public class Global {

    private static String onlineUsername;
    private static Long onlineUserId;


    public static String getOnlineUsername() {
        return onlineUsername;
    }

    public static void setOnlineUsername(String onlineUsername) {
        Global.onlineUsername = onlineUsername;
    }

    public static Long getOnlineUserID() {
        return onlineUserId;
    }

    public static void setOnlineUserID(Long onlineUsername) {
        Global.onlineUserId = onlineUsername;
    }


}
