package com.example.william.reconnect.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.dialogs.SilenceWhy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SilenceChoseSign extends AppCompatActivity {

    @BindView(R.id.choose_sign)
    TextView chooseSign;
    @BindView(R.id.silence_btn_1)
    Button silenceBtn1;
    @BindView(R.id.silence_btn_2)
    Button silenceBtn2;
    @BindView(R.id.silence_btn_3)
    Button silenceBtn3;
    @BindView(R.id.silence_btn_4)
    Button silenceBtn4;
    @BindView(R.id.silence_btn_5)
    Button silenceBtn5;
    @BindView(R.id.silence_btn_6)
    Button silenceBtn6;
    @BindView(R.id.silence_btn_7)
    Button silenceBtn7;
    @BindView(R.id.silence_btn_8)
    Button silenceBtn8;
    @BindView(R.id.btn_create_sign)
    Button btnCreateSign;
    @BindView(R.id.btn_silence_info)
    Button btnSilenceInfo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_chose_sign);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.silence_btn_1)
    public void onSilenceBtn1Clicked() {
    }

    @OnClick(R.id.silence_btn_2)
    public void onSilenceBtn2Clicked() {
    }

    @OnClick(R.id.silence_btn_3)
    public void onSilenceBtn3Clicked() {
    }

    @OnClick(R.id.silence_btn_4)
    public void onSilenceBtn4Clicked() {
    }

    @OnClick(R.id.silence_btn_5)
    public void onSilenceBtn5Clicked() {
    }

    @OnClick(R.id.silence_btn_6)
    public void onSilenceBtn6Clicked() {
    }

    @OnClick(R.id.silence_btn_7)
    public void onSilenceBtn7Clicked() {
    }

    @OnClick(R.id.silence_btn_8)
    public void onSilenceBtn8Clicked() {
    }

    @OnClick(R.id.btn_create_sign)
    public void onBtnCreateSignClicked() {

    }

    @OnClick(R.id.btn_silence_info)
    public void onBtnSilenceInfoClicked() {
        SilenceWhy alert = new SilenceWhy();
        alert.showDialog(this, getString(R.string.whychoos));

    }
}
