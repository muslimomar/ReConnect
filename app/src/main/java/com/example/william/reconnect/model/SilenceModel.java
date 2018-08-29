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
    long mantraTimeSpent;
    long chakraTimeSpent;
    long crownChakraTimeSpent;
    long thirdEyeChakraTimeSpent;
    long throatChakraTimeSpent;
    long heartChakraTimeSpent;
    long sacralChakraTimespent;
    long solarPlexusChakraTimeSpent;
    long rootChakraTimeSpent;

    public SilenceModel() {
    }

    public SilenceModel(long chakraTimeSpent, long crownChakraTimeSpent, long thirdEyeChakraTimeSpent, long throatChakraTimeSpent, long heartChakraTimeSpent, long sacralChakraTimespent, long solarPlexusChakraTimeSpent, long rootChakraTimeSpent) {
        this.chakraTimeSpent = chakraTimeSpent;
        this.crownChakraTimeSpent = crownChakraTimeSpent;
        this.thirdEyeChakraTimeSpent = thirdEyeChakraTimeSpent;
        this.throatChakraTimeSpent = throatChakraTimeSpent;
        this.heartChakraTimeSpent = heartChakraTimeSpent;
        this.sacralChakraTimespent = sacralChakraTimespent;
        this.solarPlexusChakraTimeSpent = solarPlexusChakraTimeSpent;
        this.rootChakraTimeSpent = rootChakraTimeSpent;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSilenceTimeSpent() {
        return silenceTimeSpent;
    }

    public void setSilenceTimeSpent(long silenceTimeSpent) {
        this.silenceTimeSpent = silenceTimeSpent;
    }

    public long getMusicTimeSpent() {
        return musicTimeSpent;
    }

    public void setMusicTimeSpent(long musicTimeSpent) {
        this.musicTimeSpent = musicTimeSpent;
    }

    public long getMantraTimeSpent() {
        return mantraTimeSpent;
    }

    public void setMantraTimeSpent(long mantraTimeSpent) {
        this.mantraTimeSpent = mantraTimeSpent;
    }

    public long getChakraTimeSpent() {
        return chakraTimeSpent;
    }

    public void setChakraTimeSpent(long chakraTimeSpent) {
        this.chakraTimeSpent = chakraTimeSpent;
    }

    public long getCrownChakraTimeSpent() {
        return crownChakraTimeSpent;
    }

    public void setCrownChakraTimeSpent(long crownChakraTimeSpent) {
        this.crownChakraTimeSpent = crownChakraTimeSpent;
    }

    public long getThirdEyeChakraTimeSpent() {
        return thirdEyeChakraTimeSpent;
    }

    public void setThirdEyeChakraTimeSpent(long thirdEyeChakraTimeSpent) {
        this.thirdEyeChakraTimeSpent = thirdEyeChakraTimeSpent;
    }

    public long getThroatChakraTimeSpent() {
        return throatChakraTimeSpent;
    }

    public void setThroatChakraTimeSpent(long throatChakraTimeSpent) {
        this.throatChakraTimeSpent = throatChakraTimeSpent;
    }

    public long getHeartChakraTimeSpent() {
        return heartChakraTimeSpent;
    }

    public void setHeartChakraTimeSpent(long heartChakraTimeSpent) {
        this.heartChakraTimeSpent = heartChakraTimeSpent;
    }

    public long getSacralChakraTimespent() {
        return sacralChakraTimespent;
    }

    public void setSacralChakraTimespent(long sacralChakraTimespent) {
        this.sacralChakraTimespent = sacralChakraTimespent;
    }

    public long getSolarPlexusChakraTimeSpent() {
        return solarPlexusChakraTimeSpent;
    }

    public void setSolarPlexusChakraTimeSpent(long solarPlexusChakraTimeSpent) {
        this.solarPlexusChakraTimeSpent = solarPlexusChakraTimeSpent;
    }

    public long getRootChakraTimeSpent() {
        return rootChakraTimeSpent;
    }

    public void setRootChakraTimeSpent(long rootChakraTimeSpent) {
        this.rootChakraTimeSpent = rootChakraTimeSpent;
    }
}


