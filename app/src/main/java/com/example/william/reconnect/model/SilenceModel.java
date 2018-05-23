package com.example.william.reconnect.model;

import io.realm.RealmObject;

/**
 * Created by Mahmoud on 5/22/2018.
 */

public class SilenceModel extends RealmObject {


    Long timespent;

    public SilenceModel() {
    }

    public SilenceModel(Long timespent) {
        this.timespent = timespent;
    }


    public Long getTimespent() {
        return timespent;
    }

    public void setTimespent(Long timespent) {
        this.timespent = timespent;
    }
}


