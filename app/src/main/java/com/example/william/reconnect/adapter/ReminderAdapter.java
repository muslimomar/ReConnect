package com.example.william.reconnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.example.william.reconnect.activities.ReminderActivity.TAG;


public class ReminderAdapter extends ArrayAdapter<Reminder> {

    private Realm realm;

    public ReminderAdapter(Context context, List<Reminder> reminders) {
        super(context, 0, reminders);
    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        realm = Realm.getDefaultInstance();

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.meditations_list_item, parent, false);
        }

        Reminder reminder = getItem(position);

        ImageView reminderIcon = listItemView.findViewById(R.id.reminder_icon);
        TextView reminderName = listItemView.findViewById(R.id.type_tv);
        TextView reminderHour = listItemView.findViewById(R.id.hour_tv);
        final TextView reminderDays = listItemView.findViewById(R.id.week_days_tv);
        final ImageView deleteBtn = listItemView.findViewById(R.id.delete_btn);
        deleteBtn.setTag(position);
        final View finalListItemView = listItemView;
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer index = (Integer) deleteBtn.getTag();
                Reminder deletedReminder = getItem(index);
                if (deletedReminder == null) {
                    return;
                }

                final String id = deletedReminder.getId();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        boolean isDeleted = realm.where(Reminder.class).equalTo("id", id).findAll().deleteAllFromRealm();
                        Log.d(TAG, "execute: " + isDeleted);
                    }
                });
                Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
                animation.setDuration(500);
                finalListItemView.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // disable the button to prevent the user from clicking on it, when the
                        // fading out is in progress (Because the item would've been already deleted in Realm, clicking it again will cause it to crash).
                        // plus to prevent repeating the animation (:
                        deleteBtn.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        remove(getItem(position));
                        deleteBtn.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });

        String weekDays = ArrayToOrderedString(reminder.getWeekDays());

        reminderHour.setText(reminder.getHours());
        reminderDays.setText(weekDays);

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

    private String ArrayToOrderedString(RealmList<String> weekDays) {
        String weekDaysString = "";

        for (int i = 0; i < weekDays.size(); i++) {
            weekDaysString += weekDays.get(i).substring(0, 3);
            if (i + 1 != weekDays.size()) {
                weekDaysString += ", ";
            }
        }
        return weekDaysString;
    }

    public void updateReminders(RealmResults results) {
        clear();
        addAll(new ArrayList<Reminder>(results));
        notifyDataSetChanged();
    }


}
