package com.example.lsdemo;

import androidx.annotation.NonNull;

public class ClassReportId {

    public String reporId;

    public <T extends ClassReportId> T withId(@NonNull final String id) {
        this.reporId = id;
        return (T) this;
    }
}
