// Reminder Adapter


package com.example.william.reconnect.adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
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
import com.example.william.reconnect.activities.MantraPlayingActivity;
import com.example.william.reconnect.activities.PlayingChakraActivity;
import com.example.william.reconnect.activities.PlayingMusicActivity;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.reminder.AlarmScheduler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
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

        String formattedTime = String.format("%02d:%02d", reminder.getPickedHours(), reminder.getPickedMinutes());
        holder.reminderHour.setText(formattedTime);

        holder.repeatTv.setText(getRepeatType(reminder));
        holder.musicTv.setText(reminder.getMusicPlaybackType());

        holder.deleteBtn.setTag(position);
        final View finalContentView = contentView;
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int position = (Integer) holder.deleteBtn.getTag();

                deleteItem(position, finalContentView, holder.deleteBtn);

            }
        });

        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = setIntent(reminder);
                getContext().startActivity(intent);
            }
        });


        return contentView;
    }

    private String getRepeatType(Reminder reminder) {
        String repeatType = "";
        if (reminder.getRepeatType() == AlarmManager.INTERVAL_HOUR) {
            repeatType = "Every Hour";
        } else if (reminder.getRepeatType() == AlarmManager.INTERVAL_DAY) {
            repeatType = "Every Day";
        } else if (reminder.getRepeatType() == AlarmManager.INTERVAL_DAY * 7) {
            repeatType = "Every Week";
        }
        return repeatType;
    }

    private void deleteItem(int position, View contentView, final ImageView deleteBtn) {

        Reminder deletedReminder = getItem(position);

        if (deletedReminder == null) {
            Toast.makeText(getContext(), "Error in deleting!", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = deletedReminder.getId();
        new AlarmScheduler().cancelAlarm(getContext(), id, deletedReminder.getRequestCode());

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

    public void updateReminders(RealmResults results) {
        clear();
        addAll(new ArrayList<Reminder>(results));
        notifyDataSetChanged();
    }

    private Intent setIntent(Reminder reminder) {
        Intent intent = null;
        // intent to Chakra Activity
        if (reminder.getReminderType() == Reminder.TYPE_CHAKRA) {
            intent = new Intent(getContext(), PlayingChakraActivity.class);
            intent.putExtra("music_type", reminder.getMusicPlaybackType());
            intent.putExtra("chakra_type", reminder.getChakraPlaybackTYpe());

        }
        // intent to Mantra Activity
        if (reminder.getReminderType() == Reminder.TYPE_MANTRA) {
            intent = new Intent(getContext(), MantraPlayingActivity.class);
            intent.putExtra("mantra_type",reminder.getMantraPlaybackType());

        }
        // intent to Music Activity
        if (reminder.getReminderType() == Reminder.TYPE_MUSIC) {
            intent = new Intent(getContext(), PlayingMusicActivity.class);
            intent.putExtra("music_type", reminder.getMusicPlaybackType());
        }
        return intent;

    }

    public static class ViewHolder {

        @BindView(R.id.reminder_icon)
        ImageView reminderIcon;
        @BindView(R.id.type_tv)
        TextView reminderType;
        @BindView(R.id.hour_tv)
        TextView reminderHour;
        @BindView(R.id.repeat_tv)
        TextView repeatTv;
        @BindView(R.id.delete_btn)
        ImageView deleteBtn;
        @BindView(R.id.play_btn)
        ImageView playBtn;
        @BindView(R.id.music_tv)
        TextView musicTv;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
