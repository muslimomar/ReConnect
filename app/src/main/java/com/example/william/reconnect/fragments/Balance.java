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
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;


public class Balance extends Fragment {
    @BindView(R.id.chakra_time_spent_iv)
    TextView chakraTimeSpentIv;
    @BindView(R.id.mantra_time_spent_iv)
    TextView mantraTimeSpentIv;
    @BindView(R.id.music_time_spent_iv)
    TextView musicTimeSpentIv;
    @BindView(R.id.silence_time_spent_iv)
    TextView silenceTimeSpentIv;
    Unbinder unbinder;
    Cursor mCursor;
    @BindView(R.id.silence_spent_iv)
    TextView silenceSpentIv;
    Realm realm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        unbinder = ButterKnife.bind(this, view);
        realm = Realm.getDefaultInstance();
        getSilenceTimeData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    // Get Silence Time Spent
    public void getSilenceTimeData() {
        SilenceModel person = realm.where(SilenceModel.class).findFirst();
        if (person != null) {
            // Get the timespent on Silence Day.
            Long data = person.getSilenceTimeSpent();

            // Convert from seconds to time format
            long hours = data / 3600;
            long minutes = (data % 3600) / 60;
            long seconds = data % 60;
            silenceSpentIv.setText( hours +  " Hours " + minutes + " min " + seconds + " sec");
        }
    }

    @OnClick(R.id.silence_spent_iv)
    public void onViewClicked() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
