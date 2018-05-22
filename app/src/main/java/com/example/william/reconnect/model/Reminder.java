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

    // preferences
    private int musicPlaybackRb;
    private int musicPlaybackSpinner;

    private int mantraPlaybackRb;
    private int mantraFirstSpinner;
    private int mantraSecondSpinner;

    private int chakraPlaybackRb;
    private int chakraSpinner;

    private RealmList<Integer> weekDaysInt = new RealmList<>();
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

    public int getMusicPlaybackRb() {
        return musicPlaybackRb;
    }

    public void setMusicPlaybackRb(int musicPlaybackRb) {
        this.musicPlaybackRb = musicPlaybackRb;
    }

    public int getMusicPlaybackSpinner() {
        return musicPlaybackSpinner;
    }

    public void setMusicPlaybackSpinner(int musicPlaybackSpinner) {
        this.musicPlaybackSpinner = musicPlaybackSpinner;
    }

    public int getMantraPlaybackRb() {
        return mantraPlaybackRb;
    }

    public void setMantraPlaybackRb(int mantraPlaybackRb) {
        this.mantraPlaybackRb = mantraPlaybackRb;
    }

    public int getMantraFirstSpinner() {
        return mantraFirstSpinner;
    }

    public void setMantraFirstSpinner(int mantraFirstSpinner) {
        this.mantraFirstSpinner = mantraFirstSpinner;
    }

    public int getMantraSecondSpinner() {
        return mantraSecondSpinner;
    }

    public void setMantraSecondSpinner(int mantraSecondSpinner) {
        this.mantraSecondSpinner = mantraSecondSpinner;
    }

    public int getChakraPlaybackRb() {
        return chakraPlaybackRb;
    }

    public void setChakraPlaybackRb(int chakraPlaybackRb) {
        this.chakraPlaybackRb = chakraPlaybackRb;
    }

    public int getChakraSpinner() {
        return chakraSpinner;
    }

    public void setChakraSpinner(int chakraSpinner) {
        this.chakraSpinner = chakraSpinner;
    }

    public RealmList<Integer> getWeekDaysInt() {
        return weekDaysInt;
    }

    public void setWeekDaysInt(RealmList<Integer> weekDaysInt) {
        this.weekDaysInt = weekDaysInt;
    }

    public RealmList<String> getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(RealmList<String> weekDays) {
        this.weekDays = weekDays;
    }

    public Reminder(int reminderType, String musicPlaybackType, String mantraPlaybackType, String chakraPlaybackTYpe,
                    String hours, RealmList<String> weekDays, int musicPlaybackRb, int musicPlaybackSpinner, int mantraPlaybackRb,
                    int mantraFirstSpinner, int mantraSecondSpinner, int chakraPlaybackRb, int chakraSpinner,
                    RealmList<Integer> weekDaysInt) {
        this.id = UUID.randomUUID().toString();
        this.reminderType = reminderType;
        this.musicPlaybackType = musicPlaybackType;
        this.mantraPlaybackType = mantraPlaybackType;
        this.chakraPlaybackTYpe = chakraPlaybackTYpe;
        this.hours = hours;
        this.musicPlaybackRb = musicPlaybackRb;
        this.musicPlaybackSpinner = musicPlaybackSpinner;
        this.mantraPlaybackRb = mantraPlaybackRb;
        this.mantraFirstSpinner = mantraFirstSpinner;
        this.mantraSecondSpinner = mantraSecondSpinner;
        this.chakraPlaybackRb = chakraPlaybackRb;
        this.chakraSpinner = chakraSpinner;
        this.weekDaysInt = weekDaysInt;
        this.weekDays = weekDays;
    }
}


