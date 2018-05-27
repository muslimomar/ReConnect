package com.example.william.reconnect.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.activities.PlayingMusicActivity;
import com.example.william.reconnect.model.Music;
import com.example.william.reconnect.model.SilenceModel;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 5/23/2018.
 */

public class MusicListAdapter extends ArrayAdapter<Music> {

    public MusicListAdapter(Context context, ArrayList<Music> androidFlavors) {
        super(context, 0, androidFlavors);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.music_list_item, parent,false);
        }
        // Get the {@link AndroidFlavor} object located at this position in the list
        Music currentWord = getItem(position);

        TextView musicTitle = (TextView) listItemView.findViewById(R.id.music_title);
        TextView songLength = (TextView) listItemView.findViewById(R.id.music_length);

        musicTitle.setText(currentWord.getMusictTitle());
        songLength.setText(currentWord.getSongLength());

        return listItemView;
    }

}
