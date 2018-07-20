package com.example.william.reconnect.model;

import java.util.ArrayList;

/**
 * Created by william on 5/15/2018.
 */

public class Reminder {
    public static final int TYPE_CHAKRA = 0;
    public static final int TYPE_MANTRA = 1;
    public static final int TYPE_MUSIC = 2;

    private int reminderType;
    private String soundPlaybackType;
    private String mantraPlaybackType;
    private String chakraPlaybackTYpe;
    private int pickedStartHours;
    private int pickedEndHours;
    private ArrayList<Integer> requestCode;

    // preferences
    private String soundPlaybackRb;
    private int musicPlaybackSpinner;
    private int notifPlaybackSpinner;
    //mantra
    private int mantraPlaybackRb;
    private int mantraFirstSpinner;
    private int mantraSecondSpinner;
    //chakra
    private int chakraPlaybackRb;
    private int chakraSpinner;

    public Reminder(int reminderType, String soundPlaybackType, String mantraPlaybackType, String chakraPlaybackTYpe,
                    int pickedStartHours, int pickedEndHours, ArrayList<Integer> requestCode, String soundPlaybackRb,
                    int musicPlaybackSpinner, int notifPlaybackSpinner, int mantraPlaybackRb, int mantraFirstSpinner,
                    int mantraSecondSpinner, int chakraPlaybackRb, int chakraSpinner) {
        this.reminderType = reminderType;
        this.soundPlaybackType = soundPlaybackType;
        this.mantraPlaybackType = mantraPlaybackType;
        this.chakraPlaybackTYpe = chakraPlaybackTYpe;
        this.pickedStartHours = pickedStartHours;
        this.pickedEndHours = pickedEndHours;
        this.requestCode = requestCode;
        this.soundPlaybackRb = soundPlaybackRb;
        this.musicPlaybackSpinner = musicPlaybackSpinner;
        this.notifPlaybackSpinner = notifPlaybackSpinner;
        this.mantraPlaybackRb = mantraPlaybackRb;
        this.mantraFirstSpinner = mantraFirstSpinner;
        this.mantraSecondSpinner = mantraSecondSpinner;
        this.chakraPlaybackRb = chakraPlaybackRb;
        this.chakraSpinner = chakraSpinner;
    }

    public int getReminderType() {
        return reminderType;
    }

    public void setReminderType(int reminderType) {
        this.reminderType = reminderType;
    }

    public String getSoundPlaybackType() {
        return soundPlaybackType;
    }

    public void setSoundPlaybackType(String soundPlaybackType) {
        this.soundPlaybackType = soundPlaybackType;
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

    public int getPickedStartHours() {
        return pickedStartHours;
    }

    public void setPickedStartHours(int pickedStartHours) {
        this.pickedStartHours = pickedStartHours;
    }

    public int getPickedEndHours() {
        return pickedEndHours;
    }

    public void setPickedEndHours(int pickedEndHours) {
        this.pickedEndHours = pickedEndHours;
    }

    public ArrayList<Integer> getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(ArrayList<Integer> requestCode) {
        this.requestCode = requestCode;
    }

    public String getSoundPlaybackRb() {
        return soundPlaybackRb;
    }

    public void setSoundPlaybackRb(String soundPlaybackRb) {
        this.soundPlaybackRb = soundPlaybackRb;
    }

    public int getMusicPlaybackSpinner() {
        return musicPlaybackSpinner;
    }

    public void setMusicPlaybackSpinner(int musicPlaybackSpinner) {
        this.musicPlaybackSpinner = musicPlaybackSpinner;
    }

    public int getNotifPlaybackSpinner() {
        return notifPlaybackSpinner;
    }

    public void setNotifPlaybackSpinner(int notifPlaybackSpinner) {
        this.notifPlaybackSpinner = notifPlaybackSpinner;
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
}



