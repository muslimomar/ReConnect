package com.example.william.reconnect.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.reminder.AlarmScheduler;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.AdapterView.OnItemSelectedListener;
import static com.example.william.reconnect.util.Extras.CHAKRA_REMINDER_OBJECT;
import static com.example.william.reconnect.util.Extras.MANTRA_REMINDER_OBJECT;
import static com.example.william.reconnect.util.Extras.MUSIC_REMINDER_OBJECT;
import static com.example.william.reconnect.util.Extras.PREFS_NAME;
import static com.example.william.reconnect.util.Extras.RANDOM;

public class EditReminderActivity extends AppCompatActivity implements OnItemSelectedListener {
    public static String TAG = EditReminderActivity.class.getSimpleName();
    public static int meditationType = 0;
    long pickedTimeStamp;

    String selectedSoundType = "";
    String selectedMantraType = "";
    String selectedChakraType = "";

    MediaPlayer player;

    int startHour = -1;
    int endHour = -1;

    String soundPlaybackRb;

    Reminder receivedReminder;

    @BindView(R.id.music_playback_radio_group)
    RadioGroup musicPlaybackRadioGroup;
    @BindView(R.id.music_list_spinner)
    Spinner musicListSpinner;
    @BindView(R.id.notification_spinner)
    Spinner notifSpinner;
    @BindView(R.id.sound_music_rb)
    RadioButton soundMusicRb;
    @BindView(R.id.start_time_btn)
    Button startTimeBtn;
    @BindView(R.id.end_time_btn)
    Button endTimeBtn;
    //
    @BindView(R.id.chakra_playback_radio_group)
    RadioGroup chakraPlaybackRadioGroup;
    @BindView(R.id.chakra_list_spinner)
    Spinner chakraListSpinner;
    @BindView(R.id.custom_mantra_et)
    EditText customMantraEt;
    @BindView(R.id.mantra_playback_radio_group)
    RadioGroup mantraPlaybackRadioGroup;
    @BindView(R.id.mantra_first_spinner)
    Spinner mantraFirstSpinner;
    String[] musicSongsList = new String[]{"Jason Shaw Acoustuc Meditation", "Kevin MacLeod - Sovereign Quarter", "Kevin MacLeod Dream Culture", "Kevin Macleod Bathed in The Light[Good for Chakra],", "Kevin Macleod Windswept", "Kevin MacLeod Enchanted Journey", "Kevin MacLeod Smoother Moves", "Kevin MacLeod Meditation Impromptu", "Lee Rosevere Everywhere", "Lee Rosevere Betrayal", "Lee Rosevere We’ll figure it out together", "Lee Rosevere Not My Problem", "Ryan Andersen Day to Night",};
    String[] notifSongsList = new String[]{"Bell Tree", "Chinese Flute #1", "Chinese Flute #2", "Harp Sounds", "Mermaid Singing 2"};
    //
    @BindView(R.id.music_random_rb)
    RadioButton musicRandomRb;
    @BindView(R.id.music_specific_rb)
    RadioButton musicSpecificRb;
    @BindView(R.id.sound_notification_rb)
    RadioButton soundNotifRb;
    //
    @BindView(R.id.chakra_random_rb)
    RadioButton chakraRandomRb;
    @BindView(R.id.chakra_specific_rb)
    RadioButton chakraSpecificRb;
    @BindView(R.id.chakra_layout)
    LinearLayout chakraLayout;
    @BindView(R.id.mantra_custom_rb)
    RadioButton mantraCustomRb;
    @BindView(R.id.mantra_random_rb)
    RadioButton mantraRandomRb;
    @BindView(R.id.mantra_existing_rb)
    RadioButton mantraExistingRb;
    @BindView(R.id.mantra_second_spinner)
    Spinner mantraSecondSpinner;
    @BindView(R.id.mantra_spinner_layout)
    LinearLayout mantraSpinnerLayout;
    @BindView(R.id.mantra_layout)
    LinearLayout mantraLayout;
    @BindView(R.id.chakra_layout_view)
    View chakraLayoutView;
    @BindView(R.id.mantra_layout_view)
    View mantraLayoutView;
    @BindView(R.id.pick_a_time_layout)
    LinearLayout pickaTimeLayout;
    @BindView(R.id.music_preview_btn)
    ImageButton musicPreviewBtn;
    @BindView(R.id.notificiation_preview_btn)
    ImageButton notificationPreviewBtn;


    boolean isTimePickerClicked = false;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor sharedPrefsEditor;
    private boolean mReminderHasChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mReminderHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_reminder);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sharedPrefsEditor = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        prepareSpinners();
        adjustRadiosSpinners();
        mantraFirstSpinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        setMeditationType(intent);
        adjustReminderLayout();

        if (receivedReminder != null) {
            setupViewWithData();
        }

        pickaTimeLayout.setOnTouchListener(mTouchListener);
        musicListSpinner.setOnTouchListener(mTouchListener);
        mantraFirstSpinner.setOnTouchListener(mTouchListener);
        mantraSecondSpinner.setOnTouchListener(mTouchListener);
        chakraListSpinner.setOnTouchListener(mTouchListener);
        customMantraEt.setOnTouchListener(mTouchListener);

    }

    private void playSelectedMusic() {
        switch (selectedSoundType) {
            case "Jason Shaw Acoustuc Meditation":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.jason_shaw_acoustuc_meditation);
                player.start();
                break;
            case "Kevin MacLeod - Sovereign Quarter":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.kevin_macleod_sovereign_quarter);
                player.start();
                break;
            case "Kevin MacLeod Dream Culture":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.kevin_macleod_dream_culture);
                player.start();
                break;
            case "Kevin Macleod Bathed in The Light[Good for Chakra]":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.kevin_macleod_bathed_in_the_light);
                player.start();
                break;
            case "Kevin Macleod Windswept":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.kevin_macleod_windswept);
                player.start();
                break;
            case "Kevin MacLeod Enchanted Journey":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.kevin_macleod_enchanted_journey);
                player.start();
                break;
            case "Kevin MacLeod Smoother Move":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.kevin_macleod_smoother_move);
                player.start();
                break;
            case "Kevin MacLeod Meditation Impromptu":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.kevin_macleod_meditation_impromptu);
                player.start();
                break;
            case "Lee Rosevere Everywhere":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.lee_rosevere_everywhere);
                player.start();
                break;
            case "Lee Rosevere Betrayal":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.lee_rosevere_betrayal);
                player.start();
                break;
            case "Ryan Andersen Day to Night":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.ryan_andersen_day_to_night);
                player.start();
                break;
            case "Lee Rosevere We’ll figure it out together":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.lee_rosevere_well_figure_it_out_together);
                player.start();
                break;
            case "Lee Rosevere Not My Problem":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.lee_rosevere_not_my_problem);
                player.start();
                break;
            case "Bell Tree":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.bell_tree);
                player.start();
                break;
            case "Chinese Flute #1":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.flute_1);
                player.start();
                break;
            case "Chinese Flute #2":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.chinese_flute);
                player.start();
                break;
            case "Harp Sound Effects":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.harp_sound_effects);
                player.start();
                break;
            case "Mermaid Singing 2":
                player = MediaPlayer.create(EditReminderActivity.this, R.raw.mermaid_singing);
                player.start();
                break;
        }

        if(player!=null) {
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    musicPreviewBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    notificationPreviewBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }
            });
        }
    }

    private void setupViewWithData() {

        startTimeBtn.setText(getHourWithZero(receivedReminder.getPickedStartHours()));
        endTimeBtn.setText(getHourWithZero(receivedReminder.getPickedEndHours()));

        String soundPlaybackRb = receivedReminder.getSoundPlaybackRb();
        int chakraPlaybackRb = receivedReminder.getChakraPlaybackRb();
        int mantraPlaybackRb = receivedReminder.getMantraPlaybackRb();

        startHour = receivedReminder.getPickedStartHours();
        endHour = receivedReminder.getPickedEndHours();

        chakraPlaybackRadioGroup.check(chakraPlaybackRb);
        mantraPlaybackRadioGroup.check(mantraPlaybackRb);

        if (soundPlaybackRb.equalsIgnoreCase(soundMusicRb.getText().toString().trim())) {
            soundMusicRb.setChecked(true);

            if (selectedSoundType.equalsIgnoreCase(RANDOM)) {
                musicPlaybackRadioGroup.check(musicRandomRb.getId());
            } else {
                musicPlaybackRadioGroup.check(musicSpecificRb.getId());
                musicListSpinner.setSelection(receivedReminder.getMusicPlaybackSpinner());
            }
        } else if (soundPlaybackRb.equalsIgnoreCase(soundNotifRb.getText().toString().trim())) {
            soundNotifRb.setChecked(true);
            notifSpinner.setSelection(receivedReminder.getNotifPlaybackSpinner());
        }

        if (chakraPlaybackRb == chakraSpecificRb.getId()) {
            chakraListSpinner.setSelection(receivedReminder.getChakraSpinner());
        }

        if (mantraPlaybackRb == mantraExistingRb.getId()) {
            mantraFirstSpinner.setSelection(receivedReminder.getMantraFirstSpinner());
            mantraSecondSpinner.setSelection(receivedReminder.getMantraSecondSpinner());
        } else {
            customMantraEt.setText(receivedReminder.getMantraPlaybackType());
        }
    }

    private void setMeditationType(Intent intent) {
        meditationType = intent.getIntExtra("meditationType", -1);

        switch (meditationType) {
            case Reminder.TYPE_CHAKRA:
                setTitle("Edit Chakra");
                receivedReminder = getFromSharedPrefs(CHAKRA_REMINDER_OBJECT);
                if (receivedReminder == null) {
                    setTitle("Setup Chakra");
                }
                break;
            case Reminder.TYPE_MANTRA:
                setTitle("Edit Mantra");
                receivedReminder = getFromSharedPrefs(MANTRA_REMINDER_OBJECT);
                if (receivedReminder == null) {
                    setTitle("Setup Mantra");
                }
                break;
            case Reminder.TYPE_MUSIC:
                setTitle("Edit Music");
                receivedReminder = getFromSharedPrefs(MUSIC_REMINDER_OBJECT);
                if (receivedReminder == null) {
                    setTitle("Setup Music");
                }
        }
    }

    private void adjustRadiosSpinners() {
        // it's disabled by default
        notifSpinner.setEnabled(false);
        musicListSpinner.setEnabled(false);
        customMantraEt.setEnabled(false);
        mantraFirstSpinner.setEnabled(false);
        mantraSecondSpinner.setEnabled(false);
        chakraListSpinner.setEnabled(false);
        musicRandomRb.setEnabled(false);
        musicSpecificRb.setEnabled(false);

        soundNotifRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    hideButton(musicPreviewBtn);
                    showButton(notificationPreviewBtn);
                    soundPlaybackRb = soundNotifRb.getText().toString().trim();
                    notifSpinner.setEnabled(true);
                    musicRandomRb.setEnabled(false);
                    musicSpecificRb.setEnabled(false);
                    musicPlaybackRadioGroup.setEnabled(false);
                    musicPlaybackRadioGroup.clearCheck();
                    musicListSpinner.setEnabled(false);
                    soundMusicRb.setChecked(false);

                    selectedSoundType = notifSpinner.getItemAtPosition(0).toString();
                    notifSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedSoundType = notifSpinner.getItemAtPosition(i).toString();
                            if (player != null && player.isPlaying()) {
                                player.stop();
                                notificationPreviewBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });

        soundMusicRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    soundPlaybackRb = soundMusicRb.getText().toString().trim();
                    hideButton(notificationPreviewBtn);
                    notifSpinner.setEnabled(false);
                    musicRandomRb.setEnabled(true);
                    musicSpecificRb.setEnabled(true);
                    musicPlaybackRadioGroup.setEnabled(false);
                    musicListSpinner.setEnabled(false);
                    soundNotifRb.setChecked(false);
                }
            }
        });


        musicPlaybackRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mReminderHasChanged = true;
                if (i == musicRandomRb.getId()) {
                    selectedSoundType = RANDOM;
                    musicListSpinner.setEnabled(false);
                    hideButton(musicPreviewBtn);


                } else if (i == musicSpecificRb.getId()) {
                    if(!soundNotifRb.isChecked()) {
                        showButton(musicPreviewBtn);
                    }
                    selectedSoundType = musicListSpinner.getItemAtPosition(0).toString();
                    musicListSpinner.setEnabled(true);
                    selectedSoundType = musicListSpinner.getSelectedItem().toString();
                    musicListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedSoundType = musicListSpinner.getItemAtPosition(i).toString();
                            if (player != null && player.isPlaying()) {
                                player.stop();
                                musicPreviewBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });


        mantraPlaybackRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mReminderHasChanged = true;

                if (i == mantraCustomRb.getId()) {
                    customMantraEt.setEnabled(true);
                    mantraFirstSpinner.setEnabled(false);
                    mantraSecondSpinner.setEnabled(false);

                    customMantraEt.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            selectedMantraType = customMantraEt.getText().toString().trim();
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                } else if (i == mantraExistingRb.getId()) {
                    customMantraEt.setEnabled(false);
                    mantraFirstSpinner.setEnabled(true);
                    mantraSecondSpinner.setEnabled(true);

                    if (selectedMantraType != null) {
                        selectedMantraType = mantraSecondSpinner.getSelectedItem().toString();
                    }

                    mantraSecondSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            selectedMantraType = mantraSecondSpinner.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else if (i == mantraRandomRb.getId()) {
                    customMantraEt.setEnabled(false);
                    mantraFirstSpinner.setEnabled(false);
                    mantraSecondSpinner.setEnabled(false);

                    selectedMantraType = RANDOM;
                }
            }
        });

        chakraPlaybackRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mReminderHasChanged = true;

                //default value
                if (i == chakraSpecificRb.getId()) {

                    chakraListSpinner.setEnabled(true);
                    if (chakraListSpinner.getSelectedItem().toString() != null) {
                        selectedChakraType = chakraListSpinner.getSelectedItem().toString();
                    }
                    chakraListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedChakraType = chakraListSpinner.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } else if (i == chakraRandomRb.getId()) {
                    chakraListSpinner.setEnabled(false);
                    selectedChakraType = RANDOM;
                }
            }
        });


    }

    private void adjustReminderLayout() {
        switch (meditationType) {
            case Reminder.TYPE_CHAKRA:
                mantraLayout.setVisibility(View.GONE);
                chakraLayout.setVisibility(View.VISIBLE);
                mantraLayoutView.setVisibility(View.GONE);
                break;

            case Reminder.TYPE_MANTRA:
                mantraLayout.setVisibility(View.VISIBLE);
                chakraLayout.setVisibility(View.GONE);
                chakraLayoutView.setVisibility(View.GONE);
                break;
            case Reminder.TYPE_MUSIC:
                chakraLayoutView.setVisibility(View.GONE);
                chakraLayout.setVisibility(View.GONE);
                mantraLayout.setVisibility(View.GONE);
                mantraLayoutView.setVisibility(View.GONE);
        }
    }

    private void prepareSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, musicSongsList);
        musicListSpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter9 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, notifSongsList);
        notifSpinner.setAdapter(adapter9);

        adapter.getCount();

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.chakras_array));
        mantraFirstSpinner.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.chakras_array));
        chakraListSpinner.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.crown_mantras_array));
        mantraSecondSpinner.setAdapter(adapter4);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reminder_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.check_btn) {
            saveReminder();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            if (!mReminderHasChanged) {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }

            DialogInterface.OnClickListener discardButtonClickListener =
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            NavUtils.navigateUpFromSameTask(EditReminderActivity.this);
                        }
                    };


            showUnsavedChangesDialog(discardButtonClickListener);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void saveReminder() {

        if (startHour == -1) {
            Toast.makeText(this, "Please pick a starting time!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (endHour == -1) {
            Toast.makeText(this, "Please pick an ending time!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (startHour == endHour) {
            Toast.makeText(this, "Invalid time period!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (soundMusicRb.isChecked()) {
            if (!musicRandomRb.isChecked() && !musicSpecificRb.isChecked()) {
                Toast.makeText(this, "Please select a sound playback type!", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        if (selectedSoundType.isEmpty()) {
            Toast.makeText(this, "Please select music playback type!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (meditationType == Reminder.TYPE_MANTRA) {
            if (mantraPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select a Mantra type!", Toast.LENGTH_SHORT).show();
                return;
            } else if (mantraPlaybackRadioGroup.getCheckedRadioButtonId() == mantraCustomRb.getId()
                    && customMantraEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please write your custom Mantra!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedMantraType.isEmpty()) {
                Toast.makeText(this, "Please select a Mantra type!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add Chakra Day
        } else if (meditationType == Reminder.TYPE_CHAKRA) {
            // chakra add
            if (chakraPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select a Chakra type!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (selectedChakraType.isEmpty()) {
                Toast.makeText(this, "Please select a Chakra type!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // if in edit mode, and no time is selected (previous time will be selected which will
        // cause the alarm to trigger immediately if selected time was in past)

        ArrayList<Long> timeStamps = getAlarmTimestamps();
        ArrayList<Integer> requestCodes = generateRequestCodes(timeStamps);

        if (isAlarmAlreadySet()) {
            return;
        }

        if (receivedReminder != null) {
            new AlarmScheduler().cancelAlarms(this, receivedReminder.getRequestCode(), receivedReminder.getReminderType());
        }

        if (meditationType == Reminder.TYPE_CHAKRA) {

            Reminder chakraReminder = new Reminder(
                    Reminder.TYPE_CHAKRA,
                    selectedSoundType,
                    selectedMantraType,
                    selectedChakraType,
                    startHour,
                    endHour,
                    requestCodes,
                    soundPlaybackRb,
                    musicListSpinner.getSelectedItemPosition(),
                    notifSpinner.getSelectedItemPosition(),
                    mantraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    mantraFirstSpinner.getSelectedItemPosition(),
                    mantraSecondSpinner.getSelectedItemPosition(),
                    chakraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    chakraListSpinner.getSelectedItemPosition()
            );

            saveToSharedPrefs(chakraReminder, CHAKRA_REMINDER_OBJECT);

        } else if (meditationType == Reminder.TYPE_MANTRA) {
            Reminder mantraReminder = new Reminder(
                    Reminder.TYPE_MANTRA,
                    selectedSoundType,
                    selectedMantraType,
                    selectedChakraType,
                    startHour,
                    endHour,
                    requestCodes,
                    soundPlaybackRb,
                    musicListSpinner.getSelectedItemPosition(),
                    notifSpinner.getSelectedItemPosition(),
                    mantraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    mantraFirstSpinner.getSelectedItemPosition(),
                    mantraSecondSpinner.getSelectedItemPosition(),
                    chakraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    chakraListSpinner.getSelectedItemPosition()
            );

            saveToSharedPrefs(mantraReminder, MANTRA_REMINDER_OBJECT);
        } else {
            Reminder musicReminder = new Reminder(
                    Reminder.TYPE_MUSIC,
                    selectedSoundType,
                    "",
                    "",
                    startHour,
                    endHour,
                    requestCodes,
                    soundPlaybackRb,
                    musicListSpinner.getSelectedItemPosition(),
                    notifSpinner.getSelectedItemPosition(),
                    mantraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    mantraFirstSpinner.getSelectedItemPosition(),
                    mantraSecondSpinner.getSelectedItemPosition(),
                    chakraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    chakraListSpinner.getSelectedItemPosition()
            );
        }

        setAlarm(meditationType, requestCodes, timeStamps);

        Log.d("EditReminder", "saveReminder: request codes: " + requestCodes);
        Log.d("EditReminder", "saveReminder: time stamps: " + timeStamps);
        Log.d("EditReminder", "saveReminder: Reminder Type: " + meditationType);
        Log.d("DAMN selectedSoundType", selectedSoundType);

        finish();
    }

    private ArrayList<Long> getAlarmTimestamps() {
        ArrayList<Integer> hours = getTimePeriodHours(startHour, endHour);
        ArrayList<Long> timeStamps = new ArrayList<>();
        for (int i = 0; i < hours.size(); i++) {
            timeStamps.add(setupCalendar(hours.get(i)));
        }
        return timeStamps;
    }

    private void saveToSharedPrefs(Reminder reminder, String objectKey) {
        Gson gson = new Gson();
        String jsonObject = gson.toJson(reminder);
        sharedPrefsEditor.putString(objectKey, jsonObject);
        sharedPrefsEditor.commit();
    }

    private Reminder getFromSharedPrefs(String objectKey) {
        String jsonObject = sharedPrefs.getString(objectKey, "");
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, Reminder.class);
    }

    private boolean isAlarmAlreadySet() {
        // check if such an alarm already exists
        ArrayList<Integer> hours = getTimePeriodHours(startHour, endHour);
        // if both alarms are set then we can compare the time periods.

        Reminder reminder = null;
        switch (meditationType) {
            case Reminder.TYPE_CHAKRA:
                reminder = getFromSharedPrefs(CHAKRA_REMINDER_OBJECT);
                break;
            case Reminder.TYPE_MANTRA:
                reminder = getFromSharedPrefs(MANTRA_REMINDER_OBJECT);
                break;
            default:
                reminder = getFromSharedPrefs(MUSIC_REMINDER_OBJECT);
                break;
        }

        if (reminder != null) {
            int pickedStartHours = reminder.getPickedStartHours();
            int pickedEndHours = reminder.getPickedEndHours();

            ArrayList<Integer> reminderHours = getTimePeriodHours(pickedStartHours, pickedEndHours);
            if (checkSameHour(hours, reminderHours).size() > 0) {
                String startEndHours = getHourWithZero(pickedStartHours) + " - " + getHourWithZero(pickedEndHours);
                Toast.makeText(this, "Another reminder with the same time period exists! " + startEndHours, Toast.LENGTH_SHORT).show();
                return true;
            }

        }

        return false;
    }

    private void setAlarm(int reminderType, ArrayList<Integer> requestCodes, ArrayList<Long> timeStamps) {

        new AlarmScheduler().setRepeatAlarms(getApplicationContext(),
                timeStamps, AlarmManager.INTERVAL_DAY, requestCodes, reminderType);
    }

    private ArrayList<Integer> generateRequestCodes(ArrayList<Long> timeStamps) {

        ArrayList<Integer> requestCodes = new ArrayList<>();
        for (int i = 0; i < timeStamps.size(); i++) {
            int uniqueId = (int) System.currentTimeMillis();
            requestCodes.add(uniqueId + i);
        }
        return requestCodes;
    }

    private ArrayList<Integer> getTimePeriodHours(int sHour, int eHour) {

        ArrayList<Integer> hours = new ArrayList<>();

        if (eHour > sHour) {
            while (eHour >= sHour) {
                hours.add(sHour);
                sHour++;
            }
        } else if (sHour > eHour) {
            while (sHour >= eHour) {
                hours.add(eHour);
                eHour++;
            }
        } else if (sHour == eHour) {
            Toast.makeText(this, "please choose a valid time period!", Toast.LENGTH_SHORT).show();
        }

        return hours;
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {


        String sp1 = String.valueOf(mantraFirstSpinner.getSelectedItem());
        selectedChakraType = sp1;

        switch (sp1) {
            case "Crown":
                prepareMantrasList(R.array.crown_mantras_array);
                break;
            case "Third Eye":
                prepareMantrasList(R.array.third_eye_mantras_array);
                break;
            case "Throat":
                prepareMantrasList(R.array.throat_mantras_array);
                break;
            case "Heart":
                prepareMantrasList(R.array.heart_mantras_array);
                break;
            case "Sacral":
                prepareMantrasList(R.array.sacral_mantras_array);
                break;
            case "Solar Plexus":
                prepareMantrasList(R.array.solar_plexus_mantras_array);
                break;
            case "Root":
                prepareMantrasList(R.array.root_mantras_array);
                break;
        }


        if (receivedReminder != null) {
            mantraSecondSpinner.setSelection(receivedReminder.getMantraSecondSpinner());
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void prepareMantrasList(int mantraArray) {
        String[] list = new String[]{};
        list = getResources().getStringArray(mantraArray);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        mantraSecondSpinner.setAdapter(dataAdapter);

    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        if (mReminderHasChanged) {
            showUnsavedChangesDialog(discardButtonClickListener);
            return;
        }
        super.onBackPressed();
    }

    @OnClick(R.id.start_time_btn)
    public void startTimeBtn(View view) {
        setupStartHourDialog();
    }


    private long setupCalendar(int hour) {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE, 0);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        // check if selected time isn't in past (which causes alarm to trigger immediately)
        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }

        return calSet.getTimeInMillis();
    }

    private void setupStartHourDialog() {
        final Dialog d = new Dialog(this);
        d.setTitle("Hour Picker");
        d.setContentView(R.layout.hour_picker_custom_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(23);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHour = np.getValue();
                startTimeBtn.setText(getHourWithZero(startHour));
                d.dismiss();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        d.show();
    }

    private void setupEndHourDialog() {
        final Dialog d = new Dialog(this);
        d.setTitle("Hour Picker");
        d.setContentView(R.layout.hour_picker_custom_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(23);
        np.setMinValue(0);
        np.setWrapSelectorWheel(false);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endHour = np.getValue();
                endTimeBtn.setText(getHourWithZero(endHour));
                d.dismiss();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        d.show();
    }

    @OnClick(R.id.end_time_btn)
    public void endTimeBtn(View view) {
        setupEndHourDialog();
    }

    private String getHourWithZero(int hour) {
        return String.format("%02d", hour);
    }


    public ArrayList<Integer> checkSameHour(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        int same = 0;
        for (int i = 0; i <= list1.size() - 1; i++) {
            for (int j = 0; j <= list2.size() - 1; j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    same++;
                    break;
                }
            }
        }

        ArrayList<Integer> array = new ArrayList<>();
        int p = 0;
        for (int i = 0; i <= list1.size() - 1; i++) {
            for (int j = 0; j <= list2.size() - 1; j++) {
                if (list1.get(i).equals(list2.get(j))) {
                    array.add(list1.get(i));
                    Log.d("TAGGGGGG", "array[p] => " + array.get(p));
                    p++;
                    break;
                }
            }
        }
        return array;
    }

    @OnClick(R.id.music_preview_btn)
    public void setMusicPreviewBtn(View view) {
        if (player != null && player.isPlaying()) {
            player.stop();
            musicPreviewBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        } else {
            playSelectedMusic();
            musicPreviewBtn.setImageResource(R.drawable.ic_stop_black_24dp);
        }
    }

    @OnClick(R.id.notificiation_preview_btn)
    public void setNotificationPreviewBtn(View view) {
        if (player != null && player.isPlaying()) {
            player.stop();
            notificationPreviewBtn.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        } else {
            playSelectedMusic();
            notificationPreviewBtn.setImageResource(R.drawable.ic_stop_black_24dp);
        }

    }

    public void hideButton(ImageButton imageButton) {
        imageButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        imageButton.setVisibility(View.INVISIBLE);
        stopPlayer();
    }

    public void showButton(ImageButton imageButton) {
        imageButton.setVisibility(View.VISIBLE);
        stopPlayer();
    }

    public void stopPlayer() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.isPlaying()) {
            player.stop();
            player.release();
        }
    }


}

