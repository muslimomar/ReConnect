package com.example.william.reconnect.model;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Mahmoud on 5/22/2018.
 */

public class SilenceModel extends RealmObject {




    @PrimaryKey
    String id;
    long silenceTimeSpent;
    long musicTimeSpent;
    long chakraTimeSpent;
    long mantraTimeSpent;

    public SilenceModel() {
    }

    public SilenceModel(String id, Long silenceTimeSpent, Long musicTimeSpent, Long chakraTimeSpent, Long mantraTimeSpent) {
        this.id = UUID.randomUUID().toString();
        this.silenceTimeSpent = silenceTimeSpent;
        this.musicTimeSpent = musicTimeSpent;
        this.chakraTimeSpent = chakraTimeSpent;
        this.mantraTimeSpent = mantraTimeSpent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSilenceTimeSpent() {
        return silenceTimeSpent;
    }

    public void setSilenceTimeSpent(Long silenceTimeSpent) {
        this.silenceTimeSpent = silenceTimeSpent;
    }

    public Long getMusicTimeSpent() {
        return musicTimeSpent;
    }

    public void setMusicTimeSpent(Long musicTimeSpent) {
        this.musicTimeSpent = musicTimeSpent;
    }

    public Long getChakraTimeSpent() {
        return chakraTimeSpent;
    }

    public void setChakraTimeSpent(Long chakraTimeSpent) {
        this.chakraTimeSpent = chakraTimeSpent;
    }

    public Long getMantraTimeSpent() {
        return mantraTimeSpent;
    }

    public void setMantraTimeSpent(Long mantraTimeSpent) {
        this.mantraTimeSpent = mantraTimeSpent;
    }
}


