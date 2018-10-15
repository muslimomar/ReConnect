package com.devcora.android.reconnect.model;

/**
 * Created by Mahmoud on 5/23/2018.
 */

public class Music {
    String musictTitle;
    String songLength;

    public Music() {
    }

    public Music(String musictTitle, String songLength) {
        this.musictTitle = musictTitle;
        this.songLength = songLength;
    }

    public String getMusictTitle() {
        return musictTitle;
    }

    public void setMusictTitle(String musictTitle) {
        this.musictTitle = musictTitle;
    }

    public String getSongLength() {
        return songLength;
    }

    public void setSongLength(String songLength) {
        this.songLength = songLength;
    }
}
