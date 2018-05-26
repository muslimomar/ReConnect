package com.example.william.reconnect.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.reminder.AlarmScheduler;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static android.widget.AdapterView.OnItemSelectedListener;
import static com.example.william.reconnect.util.Extras.EXTRA_ID;
import static com.example.william.reconnect.util.Extras.NO_MUSIC;
import static com.example.william.reconnect.util.Extras.RANDOM;

public class AddReminderActivity extends AppCompatActivity implements OnItemSelectedListener {
    public static String TAG = AddReminderActivity.class.getSimpleName();
    public static int meditationType = 0;
    long pickedTimeStamp;
    String pickedHoursMins = "";
    int pickedHours;
    int pickedMinutes;
    String selectedMusicType = "";
    String selectedMantraType = "";
    String selectedChakraType = "";
    @BindView(R.id.music_playback_radio_group)
    RadioGroup musicPlaybackRadioGroup;
    @BindView(R.id.music_list_spinner)
    Spinner musicListSpinner;
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
    String[] songs = new String[]{"random","Jason Shaw Acoustuc Meditation", "Kevin MacLeod - Sovereign Quarter", "Kevin MacLeod Dream Culture", "Kevin Macleod Bathed in The Light[Good for Chakra],","Kevin Macleod Windswept","Kevin MacLeod  Enchanted Journey","Kevin MacLeod Smoother Moves","Kevin MacLeod Meditation Impromptu","Lee Rosevere Everywhere","Lee Rosevere Betrayal","Lee Rosevere We’ll figure it out together","Lee Rosevere Not My Problem","Ryan Andersen Day to Night",};
    @BindView(R.id.pick_a_time_layout)
    LinearLayout pickATimeLayout;
    @BindView(R.id.days_layout)
    LinearLayout daysLayout;
    @BindView(R.id.music_no_music_rb)
    RadioButton musicNoMusicRb;
    @BindView(R.id.music_random_rb)
    RadioButton musicRandomRb;
    @BindView(R.id.music_specific_rb)
    RadioButton musicSpecificRb;
    @BindView(R.id.music_layout)
    LinearLayout musicLayout;
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
    @BindView(R.id.time_tv)
    TextView timeTv;
    Realm realm;
    String receivedId;
    long alarmInterval;
    Reminder receivedReminder;
    int requestCode;
    @BindView(R.id.every_hour_rb)
    RadioButton everyHourRb;
    @BindView(R.id.every_day_rb)
    RadioButton everyDayRb;
    @BindView(R.id.every_week_rb)
    RadioButton everyWeekRb;
    @BindView(R.id.repeat_radio_group)
    RadioGroup repeatRadioGroup;
    boolean isTimePickerClicked = false;
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
        setContentView(R.layout.activity_add_reminder);
        ButterKnife.bind(this);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        realm = Realm.getDefaultInstance();

        prepareSpinners();
        adjustRadiosSpinners();
        mantraFirstSpinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        receivedId = intent.getExtras().getString(EXTRA_ID);

        if (receivedId != null) {
            setTitle("Edit Reminder");
            setupViewWithData();
        }

        setMeditationType(intent);
        adjustReminderLayout();

        pickATimeLayout.setOnTouchListener(mTouchListener);
        musicListSpinner.setOnTouchListener(mTouchListener);
        mantraFirstSpinner.setOnTouchListener(mTouchListener);
        mantraSecondSpinner.setOnTouchListener(mTouchListener);
        chakraListSpinner.setOnTouchListener(mTouchListener);
        customMantraEt.setOnTouchListener(mTouchListener);


    }

    private void setupViewWithData() {

        realm.beginTransaction();
        receivedReminder = realm.where(Reminder.class).equalTo("id", receivedId).findFirst();
        realm.commitTransaction();

        String formattedTime = String.format("%02d:%02d", receivedReminder.getPickedHours(), receivedReminder.getPickedMinutes());
        timeTv.setText(formattedTime);
        pickedHours = receivedReminder.getPickedHours();
        pickedMinutes = receivedReminder.getPickedMinutes();
        pickedTimeStamp = receivedReminder.getAlarmTimestamp();

        pickedHoursMins = formattedTime;

        int musicPlaybackRb = receivedReminder.getMusicPlaybackRb();
        int chakraPlaybackRb = receivedReminder.getChakraPlaybackRb();
        int mantraPlaybackRb = receivedReminder.getMantraPlaybackRb();

        musicPlaybackRadioGroup.check(musicPlaybackRb);
        chakraPlaybackRadioGroup.check(chakraPlaybackRb);
        mantraPlaybackRadioGroup.check(mantraPlaybackRb);
        repeatRadioGroup.check(receivedReminder.getRepeatRb());

        if (musicPlaybackRb == musicSpecificRb.getId()) {
            musicListSpinner.setSelection(receivedReminder.getMusicPlaybackSpinner());
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

        if (meditationType == -1) {
            meditationType = receivedReminder.getReminderType();
        }

        switch (meditationType) {
            case Reminder.TYPE_CHAKRA:
                setTitle("Add Chakra");
                break;
            case Reminder.TYPE_MANTRA:
                setTitle("Add Mantra");
                break;
            case Reminder.TYPE_MUSIC:
                setTitle("Add Music");
                break;
        }


    }


    private void adjustRadiosSpinners() {
        // it's disabled by default
        musicListSpinner.setEnabled(false);
        customMantraEt.setEnabled(false);
        mantraFirstSpinner.setEnabled(false);
        mantraSecondSpinner.setEnabled(false);
        chakraListSpinner.setEnabled(false);

        musicPlaybackRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mReminderHasChanged = true;
                if (i == musicRandomRb.getId()) {
                    musicListSpinner.setEnabled(false);
                    selectedMusicType = RANDOM;

                } else if (i == musicSpecificRb.getId()) {
                    musicListSpinner.setEnabled(true);
                    selectedMusicType = musicListSpinner.getSelectedItem().toString();
                    musicListSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedMusicType = musicListSpinner.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else if (i == musicNoMusicRb.getId()) {
                    musicListSpinner.setEnabled(false);
                    selectedMusicType = NO_MUSIC;
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
                    mantraSecondSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if (receivedReminder != null) {
                                mantraSecondSpinner.setSelection(receivedReminder.getMantraSecondSpinner());
                            }
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
                if (i == chakraSpecificRb.getId()) {
                    chakraListSpinner.setEnabled(true);
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
                    selectedChakraType = "random";
                }
            }
        });

        repeatRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == everyHourRb.getId()) {
                    alarmInterval = AlarmManager.INTERVAL_HOUR;
                } else if (i == everyDayRb.getId()) {
                    alarmInterval = AlarmManager.INTERVAL_DAY;
                } else if (i == everyWeekRb.getId()) {
                    alarmInterval = AlarmManager.INTERVAL_DAY * 7;
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
                musicNoMusicRb.setVisibility(View.VISIBLE);
                break;
            case Reminder.TYPE_MANTRA:
                mantraLayout.setVisibility(View.VISIBLE);
                chakraLayout.setVisibility(View.GONE);
                musicNoMusicRb.setVisibility(View.VISIBLE);
                chakraLayoutView.setVisibility(View.GONE);

                break;
            case Reminder.TYPE_MUSIC:
                mantraLayout.setVisibility(View.GONE);
                chakraLayout.setVisibility(View.GONE);
                musicNoMusicRb.setVisibility(View.GONE);
                chakraLayoutView.setVisibility(View.GONE);
                mantraLayoutView.setVisibility(View.GONE);
                break;
        }
    }

    private void prepareSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, songs);
        musicListSpinner.setAdapter(adapter);

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
                            NavUtils.navigateUpFromSameTask(AddReminderActivity.this);
                        }
                    };


            showUnsavedChangesDialog(discardButtonClickListener);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveReminder() {

        if (pickedHoursMins.isEmpty()) {
            Toast.makeText(this, "Please pick a time!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (repeatRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select repeat type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (musicPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select music playback type!", Toast.LENGTH_SHORT).show();
            return;
        }


        // Add Mantra Day
        if (meditationType == Reminder.TYPE_MANTRA) {
            if (mantraPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select a Mantra type!", Toast.LENGTH_SHORT).show();
                return;
            } else if (mantraPlaybackRadioGroup.getCheckedRadioButtonId() == mantraCustomRb.getId()
                    && customMantraEt.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please write your custom Mantra!", Toast.LENGTH_SHORT).show();
                return;
            }
            // Add Chakra Day
        } else if (meditationType == Reminder.TYPE_CHAKRA) {
            // chakra add
            if (chakraPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select a Chakra type!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // if in edit mode get requestCode from Realm
        if (receivedId != null) {
            requestCode = receivedReminder.getRequestCode();
        } else {
            requestCode = (int) System.currentTimeMillis();
        }

        // if in edit mode, and no time is selected (previous time will be selected which will
        // cause the alarm to trigger immediately if selected time was in past)
        if (receivedId != null) {
            if (!isTimePickerClicked) {
                isSelectedTimeInPast();
            }
        }




        realm.beginTransaction();


        if(isAlarmAlreadySet()) {


            realm.commitTransaction();
            return;
        }


        if (receivedId != null) {
            // edit mode
            // Add to Realm
            Reminder toEditReminder = realm.where(Reminder.class).equalTo("id", receivedId).findFirst();
            toEditReminder.setReminderType(meditationType);
            toEditReminder.setMusicPlaybackType(selectedMusicType);
            toEditReminder.setMantraPlaybackType(selectedMantraType);
            toEditReminder.setChakraPlaybackTYpe(selectedChakraType);
            toEditReminder.setPickedHours(pickedHours);
            toEditReminder.setPickedMinutes(pickedMinutes);
            toEditReminder.setAlarmTimestamp(pickedTimeStamp);
            toEditReminder.setRepeatType(alarmInterval);
            // preferences
            toEditReminder.setMusicPlaybackRb(musicPlaybackRadioGroup.getCheckedRadioButtonId());
            toEditReminder.setMusicPlaybackSpinner(musicListSpinner.getSelectedItemPosition());
            toEditReminder.setMantraPlaybackRb(mantraPlaybackRadioGroup.getCheckedRadioButtonId());
            toEditReminder.setMantraFirstSpinner(mantraFirstSpinner.getSelectedItemPosition());
            toEditReminder.setMantraSecondSpinner(mantraSecondSpinner.getSelectedItemPosition());
            toEditReminder.setChakraPlaybackRb(chakraPlaybackRadioGroup.getCheckedRadioButtonId());
            toEditReminder.setChakraSpinner(chakraListSpinner.getSelectedItemPosition());
            toEditReminder.setRepeatRb(repeatRadioGroup.getCheckedRadioButtonId());
            realm.copyToRealmOrUpdate(toEditReminder);

            setAlarm(toEditReminder.getId());

        } else {
            // add mode
            // Add to Realm
            Reminder reminder = new Reminder(
                    meditationType,
                    selectedMusicType,
                    selectedMantraType,
                    selectedChakraType,
                    pickedHours,
                    pickedMinutes,
                    pickedTimeStamp,
                    alarmInterval,
                    // preferences
                    musicPlaybackRadioGroup.getCheckedRadioButtonId(),
                    musicListSpinner.getSelectedItemPosition(),
                    mantraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    mantraFirstSpinner.getSelectedItemPosition(),
                    mantraSecondSpinner.getSelectedItemPosition(),
                    chakraPlaybackRadioGroup.getCheckedRadioButtonId(),
                    chakraListSpinner.getSelectedItemPosition(),
                    requestCode,
                    repeatRadioGroup.getCheckedRadioButtonId()
            );
            realm.copyToRealm(reminder);

            setAlarm(reminder.getId());
        }
        realm.commitTransaction();

        finish();
    }

    private boolean isAlarmAlreadySet() {
        // check if such an alarm already exists

        Reminder reminder = realm.where(Reminder.class)
                .equalTo("pickedHours", pickedHours)
                .equalTo("pickedMinutes", pickedMinutes)
                .findFirst();



        if(reminder == null) {
            return false;
        }else{

            // make sure we're not comparing @pickedhours with the edited item from realm's time
            if(receivedId != null) {
                if(receivedReminder.getPickedHours() == pickedHours && receivedReminder.getPickedMinutes() == pickedMinutes) {
                    return false;
                }
            }

            Toast.makeText(this, "Another reminder with the same time exists!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void isSelectedTimeInPast() {
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, receivedReminder.getPickedHours());
        calSet.set(Calendar.MINUTE, receivedReminder.getPickedMinutes());
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        // if in past increment one day
        if (calSet.compareTo(calNow) <= 0) {
            calSet.add(Calendar.DATE, 1);
        }

        pickedTimeStamp = calSet.getTimeInMillis();
    }

    private void setAlarm(String id) {
        new AlarmScheduler().setRepeatAlarm(getApplicationContext(), pickedTimeStamp, id, alarmInterval, requestCode);
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

    @OnClick(R.id.pick_a_time_layout)
    public void pickTimeBtn(View view) {
        final Calendar mCalendar = Calendar.getInstance();
        final int hour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = mCalendar.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddReminderActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        isTimePickerClicked = true;

                        String formattedTime = String.format("%02d:%02d", i, i1);
                        timeTv.setText(formattedTime);
                        pickedHoursMins = formattedTime;
                        pickedHours = i;
                        pickedMinutes = i1;

                        Calendar calNow = Calendar.getInstance();
                        Calendar calSet = (Calendar) calNow.clone();

                        calSet.set(Calendar.HOUR_OF_DAY, i);
                        calSet.set(Calendar.MINUTE, i1);
                        calSet.set(Calendar.SECOND, 0);
                        calSet.set(Calendar.MILLISECOND, 0);

                        // make sure we're not in past (cause the alarm to trigger immediately)
                        if (calSet.compareTo(calNow) <= 0) {
                            calSet.add(Calendar.DATE, 1);
                        }


                        pickedTimeStamp = calSet.getTimeInMillis();
                    }

                }, hour, minute, true); // 24 hour time
        mTimePicker.setTitle("Select a time");
        mTimePicker.show();


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


}

