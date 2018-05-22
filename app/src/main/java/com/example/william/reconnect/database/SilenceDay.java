package com.example.william.reconnect.database;

/**
 * Created by Mahmoud on 5/21/2018.
 */

public class SilenceDay {
    private String id;
    private String timeSpent;

    public SilenceDay(String id, String timeSpent) {
        this.id = id;
        this.timeSpent = timeSpent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }
}
