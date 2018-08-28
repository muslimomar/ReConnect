package com.example.william.reconnect.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.model.SilenceModel;
import com.google.gson.Gson;

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.example.william.reconnect.util.Extras.PREFS_NAME;
import static com.example.william.reconnect.util.Extras.RANDOM;

public class PlayingMusicActivity extends AppCompatActivity {

    public static final String TAG = PlayingMusicActivity.class.getSimpleName();
    MediaPlayer player;
    @BindView(R.id.back_arrow_btn)
    ImageView backArrowBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.playing_icon_music)
    ImageView playingIcon;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    String musicType = "";
    int ONE_MIN_MS = 60000;
    Handler handler;
    Runnable r;
    Realm realm;
    long musicTimeSpent;
    long startTime;
    long endTime;
    private int[] rawRef = {R.raw.jason_shaw_acoustuc_meditation, R.raw.kevin_macleod_bathed_in_the_light, R.raw.kevin_macleod_dream_culture, R.raw.kevin_macleod_enchanted_journey, R.raw.kevin_macleod_meditation_impromptu, R.raw.kevin_macleod_smoother_move, R.raw.kevin_macleod_sovereign_quarter, R.raw.kevin_macleod_windswept, R.raw.lee_rosevere_betrayal, R.raw.lee_rosevere_everywhere, R.raw.lee_rosevere_not_my_problem, R.raw.ryan_andersen_day_to_night, R.raw.lee_rosevere_well_figure_it_out_together};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
        startTime = System.currentTimeMillis();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            musicType = bundle.getString("music_type");
            Log.d(TAG, "onCreate: " + musicType);
        }

        Random random = new Random();
        switch (musicType) {
            case "Jason Shaw Acoustuc Meditation":
                player = MediaPlayer.create(this, R.raw.jason_shaw_acoustuc_meditation);
                player.start();
                break;
            case "Kevin MacLeod - Sovereign Quarter":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_sovereign_quarter);
                player.start();
                break;
            case "Kevin MacLeod Dream Culture":
                player = MediaPlayer.create(this, R.raw.kevin_macleod_dream_culture);
                player.start();
                break;
            case "Kevin Macleod Bathed in Light":
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
            case "Random":
                player = MediaPlayer.create(this, rawRef[random.nextInt(rawRef.length)]);
                player.start();
                break;
            case "Bell Tree":
                player = MediaPlayer.create(this, R.raw.bell_tree);
                player.start();
                break;
            case "Chinese Flute #1":
                player = MediaPlayer.create(this, R.raw.flute_1);
                player.start();
                break;
            case "Chinese Flute #2":
                player = MediaPlayer.create(this, R.raw.chinese_flute);
                player.start();
                break;
            case "Harp Sound":
                player = MediaPlayer.create(this, R.raw.harp_sound_effects);
                player.start();
                break;
            case "Mermaid Singing":
                player = MediaPlayer.create(this, R.raw.mermaid_singing);
                player.start();
                break;
        }

        initializeImages();
        rotateChakra();
        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                endTime = System.currentTimeMillis();
                musicTimeSpent = startTime - endTime;
                int seconds = (int) (musicTimeSpent / 1000) % 60;
                int minutes = (int) ((musicTimeSpent / (1000 * 60)) % 60);
                int hours = (int) ((musicTimeSpent / (1000 * 60 * 60)) % 24);
                musicTimeSpent = musicTimeSpent / 1000;
                showFinishDialog();
                playingIcon.clearAnimation();

                if (!((Activity) PlayingMusicActivity.this).isFinishing()) {
                    showFinishDialog();
                }

            }
        };
        handler.postDelayed(r, ONE_MIN_MS);

    }
    private void initializeImages() {
        playingIcon.setImageResource(R.drawable.new_logo);
        backgroundGradient(relativeLayout);

    }

    private void rotateChakra() {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        playingIcon.startAnimation(rotation);
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

    @OnClick({R.id.back_arrow_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow_btn:
                showExitDialog();
                break;
        }
    }
    /* Show dialog when finished meditation */
    private void showFinishDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(PlayingMusicActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(PlayingMusicActivity.this);
        }
        builder.setTitle("Session Finished")
                .setMessage("Your meditation session has finished!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(PlayingMusicActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(false)
                .show();
    }

    /* Set Music Time Spent Fully working 27-05-2018 */
    private void writeToDB() {
        endTime = System.currentTimeMillis();
        musicTimeSpent = endTime - startTime;
        int seconds = (int) (musicTimeSpent / 1000) % 60;
        int minutes = (int) ((musicTimeSpent / (1000 * 60)) % 60);
        int hours = (int) ((musicTimeSpent / (1000 * 60 * 60)) % 24);
        musicTimeSpent = musicTimeSpent / 1000;
        realm.beginTransaction();
        SilenceModel silenceModel = realm.where(SilenceModel.class).findFirst();
        if (silenceModel != null) {
            // exists
            if (silenceModel.getMusicTimeSpent() != 0) {
                long time = silenceModel.getMusicTimeSpent();
                silenceModel.setMusicTimeSpent(time + musicTimeSpent);
                realm.copyToRealmOrUpdate(silenceModel);
            }
        } else {
            // first  time
            silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
            silenceModel.setMusicTimeSpent(musicTimeSpent);
            realm.copyToRealm(silenceModel);
        }

        realm.commitTransaction();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showExitDialog();
    }
    public void showExitDialog() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Exit Meditation")
                .setMessage("Are you sure you want to exit meditation session?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        handler.removeCallbacks(r);
                        if (player != null) {
                            player.stop();
                            player.release();
                            player = null;
                        }
                        writeToDB();
                        Intent intent = new Intent(PlayingMusicActivity.this, MeditationsActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}