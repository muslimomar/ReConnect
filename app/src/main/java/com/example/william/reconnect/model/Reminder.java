package com.example.william.reconnect.model;

/**
 * Created by william on 5/11/2018.
 */

public class Reminder {
    public static final int TYPE_CHAKRA = 0;
    public static final int TYPE_MANTRA = 1;
    public static final int TYPE_MUSIC= 2;

    private int reminderType;
    private String hours;
    private String weekDays;
    private String music;

    public int getReminderType() {
        return reminderType;
    }

    public void setReminderType(int reminderType) {
        this.reminderType = reminderType;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(String weekDays) {
        this.weekDays = weekDays;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public Reminder(int reminderType, String hours, String weekDays, String music) {
        this.reminderType = reminderType;
        this.hours = hours;
        this.weekDays = weekDays;
        this.music = music;
    }
}
