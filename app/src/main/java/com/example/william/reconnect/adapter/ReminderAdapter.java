package com.example.william.reconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;

import java.util.List;

/**
 * Created by william on 5/11/2018.
 */

public class ReminderAdapter extends ArrayAdapter<Reminder> {

    public ReminderAdapter(Context context, List<Reminder> reminders) {
        super(context, 0, reminders);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.meditations_list_item, parent, false);
        }

        Reminder reminder = getItem(position);

        ImageView reminderIcon = listItemView.findViewById(R.id.reminder_icon);
        TextView reminderName = listItemView.findViewById(R.id.type_tv);
        TextView reminderHour = listItemView.findViewById(R.id.hour_tv);
        TextView reminderDays = listItemView.findViewById(R.id.week_days_tv);
        TextView reminderMusic = listItemView.findViewById(R.id.music_tv);

        reminderHour.setText(reminder.getHours());
        reminderDays.setText(reminder.getWeekDays());
        reminderMusic.setText(reminder.getMusic());

        switch (reminder.getReminderType()) {
            case Reminder.TYPE_CHAKRA:
                reminderIcon.setImageResource(R.drawable.ic_chakra_large);
                reminderName.setText("Chakra Day");
                break;
            case Reminder.TYPE_MANTRA:
                reminderIcon.setImageResource(R.drawable.ic_mantra_large);
                reminderName.setText("Mantra Day");
                break;
            case Reminder.TYPE_MUSIC:
                reminderIcon.setImageResource(R.drawable.ic_music_large);
                reminderName.setText("Music Day");
                break;
        }

        return listItemView;
    }
}
