package com.mag.taskmanager.Model.Database;


public class TaskManagerDBSchema {

    public static final String NAME = "task_manager.db";


    // Tables

    public static final class Users {

        public static final String NAME = "users";

        public static final class Cols {

            public static final String _ID = "_id";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";

        }

    }

    public static final class Tasks {

        public static final String NAME = "tasks";

        public static final class Cols {

            public static final String _ID = "_id";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String DATE = "date";
            public static final String STATUS = "status";

        }

    }

    public static final class TaskManager {

        public static final String NAME = "task_manager";

        public static final class Cols {

            public static final String _ID = "_id";
            public static final String USER_ID = "user_id";
            public static final String TASK_ID = "task_id";

        }

    }
}
