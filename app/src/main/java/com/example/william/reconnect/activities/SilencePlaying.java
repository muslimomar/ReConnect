package com.example.william.reconnect.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.SilenceModel;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class SilencePlaying extends AppCompatActivity {

    @BindView(R.id.silence_playing_screen)
    RelativeLayout silencePlayingScreen;
    @BindView(R.id.silence_day_sign)
    TextView silenceDaySign;
    @BindView(R.id.counter)
    TextView counter;
    @BindView(R.id.btn_stop_silence)
    Button btnStopSilence;
    @BindView(R.id.btn_start_silence)
    Button btnStartSilence;
    @BindView(R.id.countUp)
    TextView countUp;
    @BindView(R.id.silence_sign_text)
    TextView silenceSignText;
    Realm realm;

    long silenceSpentTime;
    long startTime2;
    long endTime2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_playing);
        ButterKnife.bind(this);
        backgroundGradient(silencePlayingScreen);
        realm = Realm.getDefaultInstance();
        btnStopSilence.setEnabled(false);

        /* Getting silenceSignText value from Custom Sign Dialog */

        Intent intent = getIntent();
        String text = intent.getExtras().getString("sign", "sendSign");
        String text2 = intent.getExtras().getString("silence", "typedtext");


        /* Check the particular sent value and setText according to it */
        if (intent.hasExtra("sign")) {
            silenceSignText.setText(text);
        } else {
            silenceSignText.setText(text2);
        }
    }


    /* Gradient for background */
    private void backgroundGradient(View v) {
        final View view = v;
        Drawable[] layers = new Drawable[1];

        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                RadialGradient radialGradient = new RadialGradient(view.getWidth() / 2, view.getHeight() / 2, 900f,
                        getResources().getColor(android.R.color.white), Color.parseColor("#aeaaaa"),
                        Shader.TileMode.CLAMP);

                return radialGradient;

            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        p.setCornerRadii(new float[]{5, 5, 5, 5, 0, 0, 0, 0});
        layers[0] = (Drawable) p;

        LayerDrawable composite = new LayerDrawable(layers);
        view.setBackgroundDrawable(composite);

    }

    @OnClick(R.id.btn_stop_silence)
    public void onBtnStopSilenceClicked() {
        //    timer.cancel();

        showTimeSpentDialog(SilencePlaying.this, "Hello");

    }

    @OnClick(R.id.btn_start_silence)
    public void onBtnStartSilenceClicked() {
        btnStopSilence.setEnabled(true);
        startTime2 = System.currentTimeMillis();
        timer.start();
        Toast.makeText(this, "Silence Day Started!", Toast.LENGTH_SHORT).show();
    }

    long totalSeconds = 86400;
    long intervalSeconds = 1;
    long startTime;
    long elapsedSeconds;

    CountDownTimer timer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {

        public void onTick(long millisUntilFinished) {

            startTime = SystemClock.elapsedRealtime();
            String text = String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millisUntilFinished), TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
            countUp.setText(text);
        }

        public void onFinish() {
            //TODO code onFinish Silence countdown
            Toast.makeText(SilencePlaying.this, "Silence Day Finished!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SilencePlaying.this, MainActivity.class);
            startActivity(intent);
            showTimeSpentDialog(SilencePlaying.this, "hello");
        }

    };

    public void showTimeSpentDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.silence_time_spent_dialog);

        Button okBtn = dialog.findViewById(R.id.okBtn);
        TextView silenceTimeSpentTxt = dialog.findViewById(R.id.silence_time_spent_txt);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SilencePlaying.this, MainActivity.class);
                startActivity(intent);


            }
        });

        //
        //**  Get Elapsed Time
        //

        endTime2 = System.currentTimeMillis();
        long timeSpend = endTime2 - startTime2;
        timer.cancel();
        int seconds = (int) (timeSpend / 1000) % 60;
        int minutes = (int) ((timeSpend / (1000 * 60)) % 60);
        int hours = (int) ((timeSpend / (1000 * 60 * 60)) % 24);
        silenceSpentTime = timeSpend / 1000 + 1;
        // Write timespent to Database
        writeToDB();
        silenceTimeSpentTxt.setText(String.valueOf((silenceSpentTime) + " Seconds"));
        Toast.makeText(this, "You stopped being silence!", Toast.LENGTH_SHORT).show();


        dialog.show();
    }

    private void writeToDB() {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                /* Creating the timespent or update it */
                SilenceModel user = bgRealm.where(SilenceModel.class).findFirst();
                if (user == null) {
                    user = bgRealm.createObject(SilenceModel.class);
                    user.setTimespent(silenceSpentTime);
                } else {
                    user.setTimespent(silenceSpentTime);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                /* Transaction was a success. */
                Toast.makeText(SilencePlaying.this, "Saving Data to Realm Success", Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                /* Transaction failed and was automatically canceled. */
                Toast.makeText(SilencePlaying.this, "Error saving data to Realm!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

