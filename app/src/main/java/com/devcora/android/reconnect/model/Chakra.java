package com.devcora.android.reconnect.model;

/**
 * Created by william on 4/29/2018.
 */

public class Chakra {

    private int chakraIcon;
    private String chakraName;
    private int chakraColor;
    //info chakra
    private int chakraPlaceImg;
    private String chakraInfo;
    private String chakraPosition;

    public int getChakraIcon() {
        return chakraIcon;
    }

    public void setChakraIcon(int chakraIcon) {
        this.chakraIcon = chakraIcon;
    }

    public String getChakraName() {
        return chakraName;
    }

    public void setChakraName(String chakraName) {
        this.chakraName = chakraName;
    }

    public int getChakraColor() {
        return chakraColor;
    }

    public void setChakraColor(int chakraColor) {
        this.chakraColor = chakraColor;
    }

    public int getChakraPlaceImg() {
        return chakraPlaceImg;
    }

    public void setChakraPlaceImg(int chakraPlaceImg) {
        this.chakraPlaceImg = chakraPlaceImg;
    }

    public String getChakraInfo() {
        return chakraInfo;
    }

    public void setChakraInfo(String chakraInfo) {
        this.chakraInfo = chakraInfo;
    }

    public String getChakraPosition() {
        return chakraPosition;
    }

    public void setChakraPosition(String chakraPosition) {
        this.chakraPosition = chakraPosition;
    }

    public Chakra(int chakraIcon, String chakraName, int chakraColor, int chakraPlaceImg, String chakraInfo, String chakraPosition) {
        this.chakraIcon = chakraIcon;
        this.chakraName = chakraName;
        this.chakraColor = chakraColor;
        this.chakraPlaceImg = chakraPlaceImg;
        this.chakraInfo = chakraInfo;
        this.chakraPosition = chakraPosition;
    }
}
