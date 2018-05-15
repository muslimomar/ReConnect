package com.example.william.reconnect.model;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by william on 5/15/2018.
 */

public class Reminder extends RealmObject {
    public static final int TYPE_CHAKRA = 0;
    public static final int TYPE_MANTRA = 1;
    public static final int TYPE_MUSIC = 2;


    @PrimaryKey
    private String id;
    private int reminderType;
    private String musicPlaybackType;
    private String mantraPlaybackType;
    private String chakraPlaybackTYpe;
    private String hours;
    private RealmList<String> weekDays = new RealmList<>();

    public Reminder() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReminderType() {
        return reminderType;
    }

    public void setReminderType(int reminderType) {
        this.reminderType = reminderType;
    }

    public String getMusicPlaybackType() {
        return musicPlaybackType;
    }

    public void setMusicPlaybackType(String musicPlaybackType) {
        this.musicPlaybackType = musicPlaybackType;
    }

    public String getMantraPlaybackType() {
        return mantraPlaybackType;
    }

    public void setMantraPlaybackType(String mantraPlaybackType) {
        this.mantraPlaybackType = mantraPlaybackType;
    }

    public String getChakraPlaybackTYpe() {
        return chakraPlaybackTYpe;
    }

    public void setChakraPlaybackTYpe(String chakraPlaybackTYpe) {
        this.chakraPlaybackTYpe = chakraPlaybackTYpe;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public RealmList<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(RealmList<String> weekDays) {
        this.weekDays = weekDays;
    }

    public Reminder(int reminderType, String musicPlaybackType, String mantraPlaybackType, String chakraPlaybackTYpe, String hours, RealmList<String> weekDays) {
        this.id = UUID.randomUUID().toString();
        this.reminderType = reminderType;
        this.musicPlaybackType = musicPlaybackType;
        this.mantraPlaybackType = mantraPlaybackType;
        this.chakraPlaybackTYpe = chakraPlaybackTYpe;
        this.hours = hours;
        this.weekDays = weekDays;
    }

}


