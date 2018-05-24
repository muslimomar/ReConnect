package com.example.william.reconnect.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.activities.PlayingMusicActivity;
import com.example.william.reconnect.adapter.MusicListAdapter;
import com.example.william.reconnect.model.Music;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Mahmoud on 5/23/2018.
 */

public class MusicList extends Dialog {


    ListView musicList;
    MusicListAdapter mAdapter;
    ArrayList<Music> musicModels;

    public MusicList(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }
}