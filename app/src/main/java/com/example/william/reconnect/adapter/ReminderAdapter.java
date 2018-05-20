package com.example.william.reconnect.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.reminder.AlarmScheduler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ReminderAdapter extends ArrayAdapter<Reminder> {

    private Realm realm;

    public ReminderAdapter(@NonNull Activity context) {
        super(context, 0, new ArrayList<Reminder>());
    }

    @Override
    public View getView(final int position, View contentView, ViewGroup parent) {

        realm = Realm.getDefaultInstance();

        final ViewHolder holder;
        if (contentView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            contentView = inflater.inflate(R.layout.meditations_list_item, parent, false);
            holder = new ViewHolder(contentView);
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        final Reminder reminder = getItem(position);

        setReminderType(reminder, holder.reminderIcon, holder.reminderType);
        holder.reminderHour.setText(reminder.getHours());

        String weekDays = ArrayToOrderedString(reminder.getWeekDays());
        holder.reminderDays.setText(weekDays);

        holder.deleteBtn.setTag(position);
        final View finalContentView = contentView;
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = (Integer) holder.deleteBtn.getTag();

                deleteItem(position, finalContentView, holder.deleteBtn);

            }
        });


        return contentView;
    }

    private void deleteItem(int position, View contentView, final ImageView deleteBtn) {

        Reminder deletedReminder = getItem(position);

        if (deletedReminder == null) {
            Toast.makeText(getContext(), "Error in deleting!", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = deletedReminder.getId();
        new AlarmScheduler().cancelAlarm(getContext(), id);


        realm.beginTransaction();
        realm.where(Reminder.class).equalTo("id", id).findFirst().deleteFromRealm();
        realm.commitTransaction();

        Animation animation = AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out);
        animation.setDuration(600);
        contentView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                deleteBtn.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                updateReminders(realm.where(Reminder.class).findAll());
                deleteBtn.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void setReminderType(Reminder reminder, ImageView reminderIcon, TextView reminderType) {
        switch (reminder.getReminderType()) {
            case Reminder.TYPE_CHAKRA:
                reminderIcon.setImageResource(R.drawable.ic_chakra_large);
                reminderType.setText("Chakra Day");
                break;
            case Reminder.TYPE_MANTRA:
                reminderIcon.setImageResource(R.drawable.ic_mantra_large);
                reminderType.setText("Mantra Day");
                break;
            case Reminder.TYPE_MUSIC:
                reminderIcon.setImageResource(R.drawable.ic_music_large);
                reminderType.setText("Music Day");
                break;
        }

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

    public static class ViewHolder {

        @BindView(R.id.reminder_icon)
        ImageView reminderIcon;
        @BindView(R.id.type_tv)
        TextView reminderType;
        @BindView(R.id.hour_tv)
        TextView reminderHour;
        @BindView(R.id.week_days_tv)
        TextView reminderDays;
        @BindView(R.id.delete_btn)
        ImageView deleteBtn;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
