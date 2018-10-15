package com.devcora.android.reconnect.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.text.Html;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.devcora.android.reconnect.R;
import com.devcora.android.reconnect.model.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class SilenceDayFirstActivity extends AppCompatActivity {

    public static final String TAG = SilenceDayFirstActivity.class.getSimpleName();
    @BindView(R.id.silence_day_txt)
    TextView silenceDayTxt;
    @BindView(R.id.what_silence)
    TextView whatSilence;
    @BindView(R.id.more_info)
    TextView moreInfo;
    @BindView(R.id.see_guidelines)
    TextView seeGuidelines;
    @BindView(R.id.pick_day_btn)
    Button pickDayBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_day);
        ButterKnife.bind(this);

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick( R.id.see_guidelines)
    public void onViewClicked(View view) {
                showGuideLines(this, "msg");
    }

    @OnClick(R.id.pick_day_btn)
    public void onViewClicked() {
        startActivity(new Intent(this, SilenceChoseSign.class));
        finish();
    }

    public void showGuideLines(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.silence_guidelines_dialog);
        TextView instructionsTxt = dialog.findViewById(R.id.second_textView);
        instructionsTxt.setText(Html.fromHtml(
                "<br/><b>Silence/Non-talking Day</b>\n" +
                "Talking burns energy and takes us away from ourselves, whilst being silent collects energy and is a step towards quietening the mind. \n" +
                "<br/>Choose a less busy day to practice Silence/Non-Talking. You can still do the usual things but without added talking. Let people know in advance: ask them not to start conversations and only communicate if necessary. You can show people your ‘Silence’ sign.\n" +
                "<br/>Ask people to join you. Doing this can enhance a relationship and in groups, collectively raises the energetic vibration. \n" +
                "<br/><br/>Benefits of Silence:<br/><br/>\n" +
                "•\tHeightened awareness <br/>\n" +
                "•\tMore processing of thoughts & feelings<br/>\n" +
                "•\tEmotional balance and stability<br/>\n" +
                "•\tEnhanced stress tolerance<br/>\n" +
                "•\tGreater focused attention<br/>\n" +
                "•\tMind over matter: discipline & control<br/>\n" +
                "•\tReserve of vital energy (Chi/Ki)<br/>\n" +
                "•\tDeeper relational connections<br/>\n" +
                "•\tShines a light on one’s inner world\n" +
                "\n" +
                "\n"));
        instructionsTxt.setTextIsSelectable(true);
        dialog.show();
    }
}
