package com.devcora.android.reconnect.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.devcora.android.reconnect.R;
import com.devcora.android.reconnect.activities.MeditationsActivity;
import com.devcora.android.reconnect.activities.SilenceDayFirstActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Home extends Fragment {
    @BindView(R.id.welcome_tv)
    TextView welcomeTv;
    @BindView(R.id.begin_btn)
    Button beginBtn;
    @BindView(R.id.want_to_silence_tv)
    TextView wantToSilenceTv;
    @BindView(R.id.silence_day)
    TextView silenceDay;
    Unbinder unbinder;
    @BindView(R.id.logo)
    ImageView logo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.begin_btn)
    public void beginBtn(View view) {

        startActivity(new Intent(getActivity(), MeditationsActivity.class));
    }

    @OnClick(R.id.want_to_silence_tv)
    public void silenceTv(View view) {

        // activity to silence
    }

    @OnClick(R.id.silence_day)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), SilenceDayFirstActivity.class);
        startActivity(intent);
    }
}
