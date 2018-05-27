package com.example.william.reconnect.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.SilenceModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;


public class Balance extends Fragment {
    @BindView(R.id.chakra_time_spent_iv)
    TextView chakraTimeSpentIv;
    @BindView(R.id.silence_time_spent_iv)
    TextView silenceTimeSpentIv;
    Unbinder unbinder;
    Cursor mCursor;
    @BindView(R.id.silence_spent_iv)
    TextView silenceSpentIv;
    Realm realm;
    @BindView(R.id.music_time_spent_iv)
    TextView musicTimeSpentIv;
    @BindView(R.id.music_spent_iv)
    TextView musicTxt;
    @BindView(R.id.mantra_time_spent_iv)
    TextView mantraTimeSpentIv;
    @BindView(R.id.mantra_spent_iv)
    TextView mantraSpentIv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        getSilenceTimeData();
        getMusicTimeData();
        getMantraTimeSpent();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    // Get Silence Time Spent Fully working 27-05-2018
    public void getSilenceTimeData() {
        realm.beginTransaction();
        SilenceModel person = realm.where(SilenceModel.class).findFirst();

        realm.commitTransaction();
        if (person != null) {
            // Get the timespent on Silence Day.
            Long data = person.getSilenceTimeSpent();

            // Convert from seconds to time format
            long hours = data / 3600;
            long minutes = (data % 3600) / 60;
            long seconds = data % 60;
            silenceSpentIv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
        }
    }


    // Get Silence Music Spent Fully working 27-05-2018
    public void getMusicTimeData() {
        realm.beginTransaction();
        SilenceModel person = realm.where(SilenceModel.class).findFirst();

        realm.commitTransaction();
        if (person != null) {
            // Get the timespent on Silence Day.
            Long data = person.getMusicTimeSpent();

            // Convert from seconds to time format
            long hours = data / 3600;
            long minutes = (data % 3600) / 60;
            long seconds = data % 60;
            musicTxt.setText(hours + " Hours " + minutes + " min " + seconds + " sec");


        }
    }


    // Get Mantra TimeSpent Fully working 27-05-2018
    public void getMantraTimeSpent() {
        realm.beginTransaction();
        SilenceModel person = realm.where(SilenceModel.class).findFirst();

        realm.commitTransaction();
        if (person != null) {
            // Get the timespent on Silence Day.
            Long data = person.getMantraTimeSpent();

            // Convert from seconds to time format
            long hours = data / 3600;
            long minutes = (data % 3600) / 60;
            long seconds = data % 60;
            mantraSpentIv.setText(hours + " Hours " + minutes + " min " + seconds + " sec");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}
