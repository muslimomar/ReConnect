package com.example.william.reconnect.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.activities.SilenceDay;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Home extends Fragment {
    @BindView(R.id.begin_btn)
    Button beginBtn;
    @BindView(R.id.want_to_silence_tv)
    TextView wantToSilenceTv;
    @BindView(R.id.silence_day)
    TextView silenceDay;
    @BindView(R.id.bottom_container)
    LinearLayout bottomContainer;
    Unbinder unbinder;

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

    @OnClick(R.id.silence_day)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), SilenceDay.class);
        startActivity(intent);     }
}
