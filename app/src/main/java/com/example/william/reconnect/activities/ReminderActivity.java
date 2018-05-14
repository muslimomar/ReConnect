package com.example.william.reconnect.activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
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

import com.dpro.widgets.WeekdaysPicker;
import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.widget.AdapterView.OnItemSelectedListener;

public class ReminderActivity extends AppCompatActivity implements OnItemSelectedListener {
    public static String TAG = ReminderActivity.class.getSimpleName();
    public static String meditationType = "";
    String pickedTime = "";
    String selectedMusicType = "";
    String selectedMantraType = "";
    String selectedChakraType = "";
    List<String> selectedDays;
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
    String[] songs = new String[]{"Eminem - Not Afraid", "Ahmet Kaya", "Halit Bilgic", "Ferhat Tunc"};
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
    @BindView(R.id.day_picker)
    WeekdaysPicker dayPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakra_reminder);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            meditationType = intent.getStringExtra("meditationType");

            switch (meditationType) {
                case "chakra":
                    setTitle("Add Chakra");
                    break;
                case "mantra":
                    setTitle("Add Mantra");
                    break;
                case "music":
                    setTitle("Add Music");
                    break;
            }
        }

        prepareSpinners();
        mantraFirstSpinner.setOnItemSelectedListener(this);

        adjustRadiosSpinners();
        adjustReminderLayout();


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
                if (i == musicRandomRb.getId()) {
                    musicListSpinner.setEnabled(false);
                    selectedMusicType = "random";
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
                    selectedMusicType = "no_music";
                }

            }
        });

        mantraPlaybackRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
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
                            selectedMantraType = mantraSecondSpinner.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        });

        chakraPlaybackRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
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

    }

    private void adjustReminderLayout() {
        switch (meditationType) {
            case "chakra":
                mantraLayout.setVisibility(View.GONE);
                chakraLayout.setVisibility(View.VISIBLE);
                mantraLayoutView.setVisibility(View.GONE);
                musicNoMusicRb.setVisibility(View.VISIBLE);
                break;
            case "mantra":
                mantraLayout.setVisibility(View.VISIBLE);
                chakraLayout.setVisibility(View.GONE);
                musicNoMusicRb.setVisibility(View.VISIBLE);
                chakraLayoutView.setVisibility(View.GONE);

                break;
            case "music":
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
            addReminder();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);

    }

    private void addReminder() {

        if (pickedTime.isEmpty()) {
            Toast.makeText(this, "Please pick a time!", Toast.LENGTH_SHORT).show();
            return;
        }

        selectedDays = dayPicker.getSelectedDaysText();

        if (musicPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select music playback type!", Toast.LENGTH_SHORT).show();
            return;
        }


        switch (meditationType) {
            case "chakra":
                addChakraReminder();
                break;
            case "mantra":
                addMantraReminder();
                break;
            case "music":
                Intent intent = new Intent(this, MeditationsActivity.class);
                Reminder musicReminder = Reminder.createMusicReminder(Reminder.TYPE_MUSIC, pickedTime, selectedDays.toString(), selectedMusicType);
                String musicReminderJson = (new Gson().toJson(musicReminder));
                intent.putExtra("reminder_json", musicReminderJson);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }


    }

    private void addChakraReminder() {
        if (chakraPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a Chakra type!", Toast.LENGTH_SHORT).show();
            return;
        }

        Reminder chakraReminder = Reminder.createChakraReminder(Reminder.TYPE_CHAKRA, pickedTime, selectedDays.toString(), selectedMusicType, selectedChakraType);

        Intent intent = new Intent(this, MeditationsActivity.class);
        String chakraReminderJson = (new Gson().toJson(chakraReminder));
        intent.putExtra("reminder_json", chakraReminderJson);
        setResult(RESULT_OK, intent);
        finish();

    }

    private void addMantraReminder() {

        if (mantraPlaybackRadioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a Mantra type!", Toast.LENGTH_SHORT).show();
            return;
        } else if (mantraPlaybackRadioGroup.getCheckedRadioButtonId() == mantraCustomRb.getId()
                && customMantraEt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please write your custom Mantra!", Toast.LENGTH_SHORT).show();
            return;
        }

        Reminder mantraReminder = Reminder.createMantraReminder(Reminder.TYPE_MANTRA, pickedTime, selectedDays.toString(), selectedMusicType, selectedMantraType);

        Intent intent = new Intent(this, MeditationsActivity.class);
        String mantraReminderJson = (new Gson().toJson(mantraReminder));
        intent.putExtra("reminder_json", mantraReminderJson);
        setResult(RESULT_OK, intent);
        finish();


    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {

        String sp1 = String.valueOf(mantraFirstSpinner.getSelectedItem());

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
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ReminderActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String formattedTime = String.format("%02d:%02d", i, i1);
                        timeTv.setText(formattedTime);
                        pickedTime = formattedTime;
                    }
                }, hour, minute, true); // 24 hour time
        mTimePicker.setTitle("Select a time");
        mTimePicker.show();
    }

}

