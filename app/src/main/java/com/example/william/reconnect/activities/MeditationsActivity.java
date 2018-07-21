package com.example.william.reconnect.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.util.Extras;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.william.reconnect.util.Extras.MUSIC_RB;
import static com.example.william.reconnect.util.Extras.PREFS_NAME;

public class MeditationsActivity extends AppCompatActivity {

    public static String TAG = MeditationsActivity.class.getSimpleName();
    @BindView(R.id.mantra_title_tv)
    TextView mantraTitleTv;
    @BindView(R.id.mantra_time_period_tv)
    TextView mantraTimePeriodTv;
    @BindView(R.id.mantra_text_tv)
    TextView mantraTextTv;
    @BindView(R.id.mantra_top_layout)
    RelativeLayout mantraTopLayout;
    @BindView(R.id.divider_mantra)
    View dividerMantra;
    @BindView(R.id.mantra_edit_btn)
    Button mantraEditBtn;
    @BindView(R.id.mantra_preview_btn)
    Button mantraPreviewBtn;
    @BindView(R.id.mantra_reminder_card_view)
    CardView mantraReminderCardView;
    @BindView(R.id.chakra_title_tv)
    TextView chakraTitleTv;
    @BindView(R.id.chakra_time_period_tv)
    TextView chakraTimePeriodTv;
    @BindView(R.id.chakra_text_tv)
    TextView chakraTextTv;
    @BindView(R.id.chakra_top_layout)
    RelativeLayout chakraTopLayout;
    @BindView(R.id.divider_chakra)
    View dividerChakra;
    @BindView(R.id.chakra_edit_btn)
    Button chakraEditBtn;
    @BindView(R.id.chakra_preview_btn)
    Button chakraPreviewBtn;
    @BindView(R.id.chakra_reminder_card_view)
    CardView chakraReminderCardView;
    @BindView(R.id.chakra_card_view_layout)
    RelativeLayout chakraCdLayout;
    @BindView(R.id.mantra_card_view_layout)
    RelativeLayout mantraCdLayout;
    @BindView(R.id.setup_chakra_prefs_btn)
    Button setupChakraButton;
    @BindView(R.id.setup_mantra_prefs_btn)
    Button setupMantraButton;
    @BindView(R.id.setup_sound_prefs_btn)
    Button setupSoundPrefsBtn;
    @BindView(R.id.music_title_tv)
    TextView musicTitleTv;
    @BindView(R.id.music_time_period_tv)
    TextView musicTimePeriodTv;
    @BindView(R.id.music_text_tv)
    TextView musicTextTv;
    @BindView(R.id.music_icon_iv)
    ImageView musicIconIv;
    @BindView(R.id.sound_top_layout)
    RelativeLayout soundTopLayout;
    @BindView(R.id.divider_music)
    View dividerMusic;
    @BindView(R.id.music_edit_btn)
    Button musicEditBtn;
    @BindView(R.id.music_preview_btn)
    Button musicPreviewBtn;
    @BindView(R.id.sound_card_view_layout)
    RelativeLayout soundCardViewLayout;
    @BindView(R.id.music_reminder_card_view)
    CardView musicReminderCardView;
    private SharedPreferences sharedPrefs;
    private Reminder mantraReminder;
    private Reminder chakraReminder;
    private Reminder musicReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditations);
        ButterKnife.bind(this);

        configureActionbar();

    }

    private void setupViewsWithReminders() {
        if (mantraReminder != null) {
            if (mantraReminder.getSoundPlaybackRb().equalsIgnoreCase(MUSIC_RB)) {
                mantraTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_music_18dp, 0);
            } else {
                mantraTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_notif_18dp, 0);
            }
            mantraTimePeriodTv.setText(getHourWithZero(mantraReminder.getPickedStartHours()) + " - " + getHourWithZero(mantraReminder.getPickedEndHours()) + " H");
            mantraTextTv.setText(mantraReminder.getMantraPlaybackType());

        }

        if (chakraReminder != null) {
            if (chakraReminder.getSoundPlaybackRb().equalsIgnoreCase(MUSIC_RB)) {
                chakraTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_music_18dp, 0);
            } else {
                chakraTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_notif_18dp, 0);
            }
            chakraTimePeriodTv.setText(getHourWithZero(chakraReminder.getPickedStartHours()) + " - " + getHourWithZero(chakraReminder.getPickedEndHours()) + " H");
            chakraTextTv.setText(chakraReminder.getChakraPlaybackTYpe());
        }

        if (musicReminder != null) {
            if (musicReminder.getSoundPlaybackRb().equalsIgnoreCase(MUSIC_RB)) {
                musicTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_music_18dp, 0);
            } else {
                musicTitleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_notif_18dp, 0);
            }
            musicTimePeriodTv.setText(getHourWithZero(musicReminder.getPickedStartHours()) + " - " + getHourWithZero(musicReminder.getPickedEndHours()) + " H");
            musicTextTv.setText(musicReminder.getChakraPlaybackTYpe());
        }


    }

    private String getHourWithZero(int hour) {
        return String.format("%02d", hour);
    }

    private void getRemindersFromSharedPrefs() {
        sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String chakraObj = sharedPrefs.getString(Extras.CHAKRA_REMINDER_OBJECT, "");
        String mantraObj = sharedPrefs.getString(Extras.MANTRA_REMINDER_OBJECT, "");
        String musicObj = sharedPrefs.getString(Extras.MUSIC_REMINDER_OBJECT, "");
        Gson gson = new Gson();
        chakraReminder = gson.fromJson(chakraObj, Reminder.class);
        mantraReminder = gson.fromJson(mantraObj, Reminder.class);
        musicReminder = gson.fromJson(musicObj, Reminder.class);
    }

    private void configureActionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }
        setTitle("Meditations");

    }

    @OnClick(R.id.mantra_edit_btn)
    public void setMantraEditBtn(View view) {
        goToAddReminder(Reminder.TYPE_MANTRA);
    }

    @OnClick(R.id.chakra_edit_btn)
    public void setChakraEditBtn(View view) {
        goToAddReminder(Reminder.TYPE_CHAKRA);
    }

    @OnClick(R.id.music_edit_btn)
    public void setMusicEditBtn(View view) {
        goToAddReminder(Reminder.TYPE_MUSIC);
    }

    @OnClick(R.id.mantra_preview_btn)
    public void setMantraPreviewBtn(View view) {
        goToMantraPlaying();
    }

    @OnClick(R.id.chakra_preview_btn)
    public void setChakraPreviewBtn(View view) {
        goToChakraPlaying();
    }

    @OnClick(R.id.music_preview_btn)
    public void setMusicPreviewBtn(View view) {
        goToMusicPlaying();
    }

    private void goToChakraPlaying() {
        Intent intent = new Intent(this, PlayingChakraActivity.class);
        if (chakraReminder != null) {
            intent.putExtra("music_type", chakraReminder.getSoundPlaybackType());
            intent.putExtra("chakra_type", chakraReminder.getChakraPlaybackTYpe());
        }
        startActivity(intent);
    }

    private void goToMantraPlaying() {
        Intent intent = new Intent(this, MantraPlayingActivity.class);
        if (mantraReminder != null) {
            intent.putExtra("mantra_type", mantraReminder.getMantraPlaybackType());
            intent.putExtra("music_type", mantraReminder.getSoundPlaybackType());
        }
        startActivity(intent);
    }

    private void goToMusicPlaying() {
        Intent intent = new Intent(this, PlayingMusicActivity.class);
        if (mantraReminder != null) {
            intent.putExtra("music_type", mantraReminder.getSoundPlaybackType());
        }
        startActivity(intent);
    }

    private void goToAddReminder(int type) {
        Intent intent = new Intent(this, EditReminderActivity.class);
        intent.putExtra("meditationType", type);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        getRemindersFromSharedPrefs();
        displayProperLayout();

        setupViewsWithReminders();
    }

    private void displayProperLayout() {
        if (chakraReminder == null) {
            setupChakraButton.setVisibility(View.VISIBLE);
            chakraCdLayout.setVisibility(View.GONE);
        } else if (chakraReminder != null) {
            setupChakraButton.setVisibility(View.GONE);
            chakraCdLayout.setVisibility(View.VISIBLE);
        }

        if (mantraReminder == null) {
            setupMantraButton.setVisibility(View.VISIBLE);
            mantraCdLayout.setVisibility(View.GONE);
        } else if (mantraReminder != null) {
            setupMantraButton.setVisibility(View.GONE);
            mantraCdLayout.setVisibility(View.VISIBLE);
        }


        //TODO
        if (mantraReminder == null) {
            setupSoundPrefsBtn.setVisibility(View.VISIBLE);
            soundCardViewLayout.setVisibility(View.GONE);
        } else if (mantraReminder != null) {
            setupSoundPrefsBtn.setVisibility(View.GONE);
            soundCardViewLayout.setVisibility(View.VISIBLE);
        }

    }

    @OnClick(R.id.setup_mantra_prefs_btn)
    public void setSetupMantraButton(View view) {
        goToAddReminder(Reminder.TYPE_MANTRA);
    }

    @OnClick(R.id.setup_chakra_prefs_btn)
    public void setSetupChakraButton(View view) {
        goToAddReminder(Reminder.TYPE_CHAKRA);
    }

    @OnClick(R.id.setup_sound_prefs_btn)
    public void setSetupSoundPrefsBtn(View view) {
        goToAddReminder(Reminder.TYPE_MUSIC);
    }


}