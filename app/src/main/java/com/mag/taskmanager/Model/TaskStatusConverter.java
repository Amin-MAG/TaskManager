package com.mag.taskmanager.Model;

import org.greenrobot.greendao.converter.PropertyConverter;

class TaskStatusConverter implements PropertyConverter<TaskStatus, Integer> {

    @Override
    public TaskStatus convertToEntityProperty(Integer databaseValue) {
        for (TaskStatus taskStatus : TaskStatus.values())
            if (databaseValue == taskStatus.getIndex())
                return taskStatus;
        return null;
    }

    @Override
    public Integer convertToDatabaseValue(TaskStatus entityProperty) {
        return entityProperty.getIndex();
    }

}
