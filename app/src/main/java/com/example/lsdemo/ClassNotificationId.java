package com.example.lsdemo;

import androidx.annotation.NonNull;

public class ClassNotificationId {

    public String notificationId;

    public <T extends ClassNotificationId> T withId(@NonNull final String id) {
        this.notificationId = id;
        return (T) this;
    }
}