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

import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class PlayingMusicActivity extends AppCompatActivity {

    MediaPlayer player;
    @BindView(R.id.back_arrow_btn)
    ImageView backArrowBtn;
    @BindView(R.id.info_btn)
    ImageView infoBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.playing_icon)
    ImageView playingIcon;
    @BindView(R.id.music_btn)
    ImageView musicBtn;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    String musicType = "";
    int ONE_MIN_MS = 60000;
    private int[] rawRef = {R.raw.jason_shaw_acoustuc_meditation, R.raw.kevin_macleod_bathed_in_the_light, R.raw.kevin_macleod_dream_culture, R.raw.kevin_macleod_enchanted_journey, R.raw.kevin_macleod_meditation_impromptu, R.raw.kevin_macleod_smoother_move, R.raw.kevin_macleod_sovereign_quarter, R.raw.kevin_macleod_windswept, R.raw.lee_rosevere_betrayal, R.raw.lee_rosevere_everywhere, R.raw.lee_rosevere_not_my_problem, R.raw.ryan_andersen_day_to_night, R.raw.lee_rosevere_well_figure_it_out_together};
    Realm realm;
    long musicTimeSpent;
    long startTime;
    long endTime;


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
*/

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

        initializeImages();
        setImageOpacity();
        rotateChakra();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // TODO:  Stop music
                endTime = System.currentTimeMillis();
                musicTimeSpent = startTime - endTime;
                //player.release();
                player = null;
                playingIcon.clearAnimation();
                writeToDB();

                if (!((Activity) PlayingMusicActivity.this).isFinishing()) {
                    showFinishDialog();
                }

            }
        };
        handler.postDelayed(r, ONE_MIN_MS);

    }


    private void initializeImages() {
        playingIcon.setImageResource(R.drawable.re_connect_logo);
        backgroundGradient(relativeLayout);

    }

    private void setImageOpacity() {
        Drawable background3 = musicBtn.getBackground();
        background3.setAlpha(40);
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


    @OnClick({R.id.back_arrow_btn, R.id.info_btn, R.id.music_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_arrow_btn:
                player.stop();
                player.release();
                writeToDB();
                Log.d("TimeSpent", "musictime : " + musicTimeSpent);
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.info_btn:
                break;
            case R.id.music_btn:
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
        musicTimeSpent = musicTimeSpent / 1000 + 1;
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
        writeToDB();
        player.stop();
        player.release();
    }

}
