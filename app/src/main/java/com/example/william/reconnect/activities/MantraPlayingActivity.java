package com.example.william.reconnect.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.model.SilenceModel;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;


public class MantraPlayingActivity extends AppCompatActivity {


    long startTime;
    long endTime;
    long mantraTimeSpent;
    String musicType;
    MediaPlayer player;
    Realm realm;
    int ONE_MIN_MS = 60000;
    @BindView(R.id.mantra_msg)
    TextView mantraMsg;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    private int[] rawRef = {R.raw.jason_shaw_acoustuc_meditation, R.raw.kevin_macleod_bathed_in_the_light, R.raw.kevin_macleod_dream_culture, R.raw.kevin_macleod_enchanted_journey, R.raw.kevin_macleod_meditation_impromptu, R.raw.kevin_macleod_smoother_move, R.raw.kevin_macleod_sovereign_quarter, R.raw.kevin_macleod_windswept, R.raw.lee_rosevere_betrayal, R.raw.lee_rosevere_everywhere, R.raw.lee_rosevere_not_my_problem, R.raw.ryan_andersen_day_to_night, R.raw.lee_rosevere_well_figure_it_out_together};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_playing);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        startTime = System.currentTimeMillis();

        realm = Realm.getDefaultInstance();
        startTime = System.currentTimeMillis();
        initializeImages();


        Intent intent = getIntent();
        String mantraType = intent.getExtras().getString("mantra_type");
        mantraMsg.setText(mantraType);

      /*
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            musicType = bundle.getString("music_type_mantra");
        }

        Random random = new Random();


        switch (musicType) {
            case "Jason Shaw Acoustuc Meditation":
                player = MediaPlayer.create(this, R.raw.jason_shaw_acoustuc_meditation);
                player.start();
            case "Kevin MacLeod - Sovereign Quarter":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_sovereign_quarter);
                player.start();
                break;
            case "Kevin MacLeod Dream Culture":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_dream_culture);
                player.start();
                break;
            case "Kevin Macleod Bathed in The Light[Good for Chakra]":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_bathed_in_the_light);
                player.start();
                break;
            case "Kevin Macleod Windswept":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_windswept);
                player.start();
                break;
            case "Kevin MacLeod Enchanted Journey":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_enchanted_journey);
                player.start();
                break;
            case "Kevin MacLeod Smoother Move":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_smoother_move);
                player.start();
                break;
            case "Kevin MacLeod Meditation Impromptu":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_meditation_impromptu);
                player.start();
                break;
            case "Lee Rosevere Everywhere":
                player = MediaPlayer.create(this, R.raw.lee_rosevere_everywhere);
                player.start();
                break;
            case "Lee Rosevere Betrayal":
                player = MediaPlayer.create(this, R.raw.lee_rosevere_betrayal);
                player.start();
                break;
            case "Ryan Andersen Day to Night":
                player = MediaPlayer.create(this, R.raw.ryan_andersen_day_to_night);
                player.start();
                break;
            case "Lee Rosevere We’ll figure it out together":
                player = MediaPlayer.create(this, R.raw.lee_rosevere_well_figure_it_out_together);
                player.start();
                break;
            case "Lee Rosevere Not My Problem":
                player = MediaPlayer.create(this, R.raw.lee_rosevere_not_my_problem);
                player.start();
                break;
           /* case "Random":player = MediaPlayer.create(this,rawRef[random.nextInt(rawRef.length)]);
            player.start();
            break;


        }

        Reminder reminder = realm.where(Reminder.class).findFirst();
        if (reminder != null) {
            // Get the Music Playback Type.
            String musicType = reminder.getMusicPlaybackType();
            if (musicType.equals("Random")) {
                player = MediaPlayer.create(this, rawRef[random.nextInt(rawRef.length)]);
                player.start();
            }
        }

        */
        initializeImages();

        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // TODO:  Stop music
                writeToDB();

                if (!((Activity) MantraPlayingActivity.this).isFinishing()) {
                    showFinishDialog();
                }

            }
        };
        handler.postDelayed(r, ONE_MIN_MS);


    }

    private void initializeImages() {
        backgroundGradient(relativeLayout);

    }


    /* Gradient for background */
    private void backgroundGradient(View v) {
        final View view = v;
        Drawable[] layers = new Drawable[1];

        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {
                RadialGradient radialGradient = new RadialGradient(view.getWidth() / 2, view.getHeight() / 2, 650f,
                        getResources().getColor(android.R.color.white), Color.parseColor("#e2231a"),
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

    /* Show dialog when finished meditation */
    private void showFinishDialog() {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MantraPlayingActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MantraPlayingActivity.this);
        }
        builder.setTitle("Session Finished")
                .setMessage("Your meditation session has finished!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MantraPlayingActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(false)
                .show();
    }


    @OnClick({R.id.back_arrow_btn, R.id.mantra_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow_btn:
                writeToDB();

                //player.stop();
                //player.release();
                writeToDB();
                NavUtils.navigateUpFromSameTask(this);
                break;
        }
    }


    /* Set Music Time Spent Fully working 27-05-2018 */
    private void writeToDB() {

        endTime = System.currentTimeMillis();
        mantraTimeSpent = endTime - startTime;
        int seconds = (int) (mantraTimeSpent / 1000) % 60;
        int minutes = (int) ((mantraTimeSpent / (1000 * 60)) % 60);
        int hours = (int) ((mantraTimeSpent / (1000 * 60 * 60)) % 24);
        mantraTimeSpent = mantraTimeSpent / 1000 + 1;
        realm.beginTransaction();
        SilenceModel silenceModel = realm.where(SilenceModel.class).findFirst();
        if (silenceModel != null) {
            // exists
            if (silenceModel.getMantraTimeSpent() != null) {
                long time = silenceModel.getMantraTimeSpent();
                silenceModel.setMantraTimeSpent(time + mantraTimeSpent);
                realm.copyToRealmOrUpdate(silenceModel);
            }
        } else {
            // first  time
            silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
            silenceModel.setMantraTimeSpent(mantraTimeSpent);
            realm.copyToRealm(silenceModel);
        }

        Log.d("spenty","spenty is : "+ mantraTimeSpent);

        realm.commitTransaction();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        writeToDB();
        //player.stop();
        //player.release();
    }
}