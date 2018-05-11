package com.example.william.reconnect.model;

/**
 * Created by william on 5/9/2018.
 */

public class ChakraUsage {
    private int chakraIcon;
    private String chakraName;
    private String chakraSpentTime;
    private int chakraPbColor;

    public ChakraUsage(int chakraIcon, String chakraName, String chakraSpentTime, int chakraPbColor) {
        this.chakraIcon = chakraIcon;
        this.chakraName = chakraName;
        this.chakraSpentTime = chakraSpentTime;
        this.chakraPbColor = chakraPbColor;
    }

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

    public String getChakraSpentTime() {
        return chakraSpentTime;
    }

    public void setChakraSpentTime(String chakraSpentTime) {
        this.chakraSpentTime = chakraSpentTime;
    }

    public int getChakraPbColor() {
        return chakraPbColor;
    }

    public void setChakraPbColor(int chakraPbColor) {
        this.chakraPbColor = chakraPbColor;
    }

}
