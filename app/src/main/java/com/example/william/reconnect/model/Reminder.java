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
    private String musicPlaybackType;
    private String mantraPlaybackType;
    private String chakraPlaybackTYpe;

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

    public Reminder(int reminderType, String hours, String weekDays, String musicPlaybackType, String mantraPlaybackType, String chakraPlaybackTYpe) {
        this.reminderType = reminderType;
        this.hours = hours;
        this.weekDays = weekDays;
        this.musicPlaybackType = musicPlaybackType;
        this.mantraPlaybackType = mantraPlaybackType;
        this.chakraPlaybackTYpe = chakraPlaybackTYpe;
    }

    public static Reminder createMusicReminder(int reminderType, String hours, String weekDays, String musicPlaybackType) {
        return new Reminder(reminderType, hours, weekDays, musicPlaybackType, "", "");
    }

    public static Reminder createMantraReminder(int reminderType, String hours, String weekDays, String musicPlaybackType, String mantraPlaybackType) {
        return new Reminder(reminderType, hours, weekDays, musicPlaybackType, mantraPlaybackType,"");
    }

    public static Reminder createChakraReminder(int reminderType, String hours, String weekDays, String musicPlaybackType, String chakraPlaybackTYpe) {
        return new Reminder(reminderType, hours, weekDays, musicPlaybackType, chakraPlaybackTYpe,"");
    }



}
