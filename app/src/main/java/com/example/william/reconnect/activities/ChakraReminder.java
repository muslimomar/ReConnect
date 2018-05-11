package com.example.william.reconnect.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.william.reconnect.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner;
import io.apptik.widget.multiselectspinner.ExpandableMultiSelectSpinner;

public class ChakraReminder extends AppCompatActivity {

    public static String TAG = ChakraReminder.class.getSimpleName();

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
    @BindView(R.id.mantra_list_spinner)
    Spinner mantraListSpinner;

    String[] songs = new String[]{"Eminem - Not Afraid", "Ahmet Kaya", "Halit Bilgic", "Ferhat Tunc"};
    String[] mantras = new String[]{"Eminem - Not Afraid", "Ahmet Kaya", "Halit Bilgic", "Ferhat Tunc"};
    String[] chakras = new String[]{"Eminem - Not Afraid", "Ahmet Kaya", "Halit Bilgic", "Ferhat Tunc"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakra_reminder);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        prepareSpinners();

        ExpandableMultiSelectSpinner multiSelectSpinner6 = (ExpandableMultiSelectSpinner) findViewById(R.id.multi_spinner);
        LinkedHashMap<String, List<String>> items = new LinkedHashMap<>();
        ArrayList<String> items1 = new ArrayList<>();
        items1.add("A");
        items1.add("B");
        items1.add("C");
        items1.add("D");
        items1.add("E");
        ArrayList<String> items2 = new ArrayList<>();
        items2.add("1");
        items2.add("2");
        items2.add("3");
        items2.add("4");
        items2.add("5");

        items.put("Abc", items1);
        items.put("123", items2);
        multiSelectSpinner6.setItems(items)
                .setAllCheckedText("All types")
                .setAllUncheckedText("none selected")
                .setMaxSelectedItems(1)
                .setTitle("Select Types from Groups")
                .setListener(new BaseMultiSelectSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {
                        Log.d(TAG, "selected: " + selected);
                    }
                });
    }

    private void prepareSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, songs);
        musicListSpinner.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reminder_menu, menu);
        return true;
    }
}
