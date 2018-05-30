package com.example.william.reconnect.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Chakra;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.model.SilenceModel;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.example.william.reconnect.util.Extras.RANDOM;

public class PlayingChakraActivity extends AppCompatActivity {

/*
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String allchakraTimeSpent = "chakraTimeSpent";
    public static final String crownChakraTime = "crownChakraTime";
    public static final String thirdEyeChakraTime = "thirdEyeChakraTime";
    public static final String throatChakraTime = "throatChakraTime";
    public static final String heartChakraTime = "heartChakraTime";
    public static final String sacralChakraTime = "sacralChakraTime";
    public static final String solarPLexusChakraTime = "solarPLexusChakraTime";
    SharedPreferences sharedpreferences;
*/

    public static final String TAG = PlayingChakraActivity.class.getSimpleName();
    SilenceModel silenceModel;
    Handler handler;
    Runnable r;
    MediaPlayer player;
    long startTime;
    long endTime;
    long chakraTimeSpent;
    Realm realm;
    int position = 0;
    String chakraType;
    String musicType;
    ArrayList<Chakra> chakras = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.playing_icon)
    ImageView playingIconIv;
    @BindView(R.id.back_arrow_iv)
    ImageView backArrowIv;
    @BindView(R.id.info_iv)
    ImageView infoIv;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    int ONE_MIN_MS = 60000;
    private int[] rawRef = {R.raw.jason_shaw_acoustuc_meditation, R.raw.kevin_macleod_bathed_in_the_light, R.raw.kevin_macleod_dream_culture, R.raw.kevin_macleod_enchanted_journey, R.raw.kevin_macleod_meditation_impromptu, R.raw.kevin_macleod_smoother_move, R.raw.kevin_macleod_sovereign_quarter, R.raw.kevin_macleod_windswept, R.raw.lee_rosevere_betrayal, R.raw.lee_rosevere_everywhere, R.raw.lee_rosevere_not_my_problem, R.raw.ryan_andersen_day_to_night, R.raw.lee_rosevere_well_figure_it_out_together};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakra_playing);
        ButterKnife.bind(this);
        startTime = System.currentTimeMillis();
        realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        silenceModel = realm.where(SilenceModel.class).findFirst();
        realm.commitTransaction();

        prepareChakraList();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            chakraType = bundle.getString("chakra_type");
            musicType = bundle.getString("music_type");
            Log.d(TAG, "onCreate: " + chakraType);
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
        rotateChakra();

        handler = new Handler();
        r = new Runnable() {
            @Override
            public void run() {
                playingIconIv.clearAnimation();

                if (!((Activity) PlayingChakraActivity.this).isFinishing()) {
                    showFinishDialog();
                }

            }
        };
        handler.postDelayed(r, ONE_MIN_MS);

    }

    private void showFinishDialog() {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(PlayingChakraActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(PlayingChakraActivity.this);
        }
        builder.setTitle("Session Finished")
                .setMessage("Your meditation session has finished!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(PlayingChakraActivity.this, MainActivity.class));
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .setCancelable(false)
                .show();
    }

    private void prepareChakraList() {

        chakras.add(new Chakra(R.drawable.crown_img, "Crown", R.color.crown,
                R.drawable.crown, getResources().getString(R.string.crown_tips), getResources().getString(R.string.crown_position)));

        chakras.add(new Chakra(R.drawable.third_eye_img, "Third Eye", R.color.third_eye,
                R.drawable.third_eye, getResources().getString(R.string.third_eye_tips), getResources().getString(R.string.third_eye_position)));

        chakras.add(new Chakra(R.drawable.throat_img, "Throat", R.color.throat,
                R.drawable.throat, getResources().getString(R.string.throat_tips), getResources().getString(R.string.throat_position)));

        chakras.add(new Chakra(R.drawable.heart_img, "Heart", R.color.heart,
                R.drawable.heart, getResources().getString(R.string.heart_tips), getResources().getString(R.string.heart_position)));

        chakras.add(new Chakra(R.drawable.solar_img, "Solar Plexus", R.color.solar_plexus,
                R.drawable.solar_plexus, getResources().getString(R.string.solar_plexus_tips), getResources().getString(R.string.solar_plexus_position)));

        chakras.add(new Chakra(R.drawable.sacral_img, "Sacral", R.color.sacral,
                R.drawable.sacral, getResources().getString(R.string.sacral_tips), getResources().getString(R.string.sacral_position)));

        chakras.add(new Chakra(R.drawable.root_img, "Root", R.color.root
                , R.drawable.root, getResources().getString(R.string.root_tips), getResources().getString(R.string.root_position)));

    }


    private void initializeImages() {


        if (chakraType.equals(RANDOM)) {
            position = new Random().nextInt(chakras.size());
        } else {

            for (int i = 0; i < chakras.size(); i++) {
                if (chakraType.equalsIgnoreCase(chakras.get(i).getChakraName())) {
                    position = i;
                }
            }
        }

        playingIconIv.setImageResource(chakras.get(position).getChakraIcon());
        backgroundGradient(relativeLayout);

        // set Opacity
        //Drawable background3 = musicIv.getBackground();
        //background3.setAlpha(40);

    }

    private void rotateChakra() {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        playingIconIv.startAnimation(rotation);
    }


    @OnClick(R.id.back_arrow_iv)
    public void backArrowBtn(View view) {
        showExitDialog();
    }

    private void backgroundGradient(View v) {
        final View view = v;
        Drawable[] layers = new Drawable[1];

        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {
            @Override
            public Shader resize(int width, int height) {

                RadialGradient radialGradient = new RadialGradient(view.getWidth() / 2, view.getHeight() / 2, 350f,
                        getResources().getColor(android.R.color.white), getResources().getColor(chakras.get(position).getChakraColor()),
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

    @OnClick(R.id.info_iv)
    public void infoIvBtn(View view) {
        Chakra chakra = chakras.get(position);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.chakra_info_dialog);
        dialog.setCancelable(true);

        TextView chakraTitle = dialog.findViewById(R.id.chakra_title);
        Button okBtn = dialog.findViewById(R.id.ok_btn);
        TextView chakraInfoTv = dialog.findViewById(R.id.chakra_info_tv);
        ImageView chakraInfoPlaceIv = dialog.findViewById(R.id.chakra_place_iv);
        LinearLayout dialogCenterLayout = dialog.findViewById(R.id.dialog_center_layout);

        chakraTitle.setText(chakra.getChakraName());
        chakraInfoPlaceIv.setImageResource(chakra.getChakraPlaceImg());
        chakraInfoTv.setText(chakra.getChakraInfo());
        dialogCenterLayout.setBackgroundColor(getResources().getColor(chakra.getChakraColor()));
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MeditationsActivity.class);
                startActivity(intent);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


    /* Set Music Time Spent Fully working 27-05-2018 */
    private void writeToDB() {

        endTime = System.currentTimeMillis();
        chakraTimeSpent = endTime - startTime;
        int seconds = (int) (chakraTimeSpent / 1000) % 60;
        int minutes = (int) ((chakraTimeSpent / (1000 * 60)) % 60);
        int hours = (int) ((chakraTimeSpent / (1000 * 60 * 60)) % 24);
        chakraTimeSpent = chakraTimeSpent / 1000;

        realm.beginTransaction();

        if (silenceModel != null) {
            long time;

            switch (chakraType) {
                case "Crown":
                    time = silenceModel.getCrownChakraTimeSpent();
                    silenceModel.setCrownChakraTimeSpent(time + chakraTimeSpent);
                    break;
                case "Third Eye":
                    time = silenceModel.getThirdEyeChakraTimeSpent();
                    silenceModel.setThirdEyeChakraTimeSpent(time + chakraTimeSpent);
                    break;

                case "Throat":
                    time = silenceModel.getThroatChakraTimeSpent();
                    silenceModel.setThroatChakraTimeSpent(time + chakraTimeSpent);
                    break;

                case "Heart":
                    time = silenceModel.getHeartChakraTimeSpent();
                    silenceModel.setHeartChakraTimeSpent(time + chakraTimeSpent);
                    break;

                case "Sacral":
                    time = silenceModel.getSacralChakraTimespent();
                    silenceModel.setSacralChakraTimespent(time + chakraTimeSpent);
                    break;

                case "Solar Plexus":
                    time = silenceModel.getSolarPlexusChakraTimeSpent();
                    silenceModel.setSolarPlexusChakraTimeSpent(time + chakraTimeSpent);
                    break;

                case "Root":
                    time = silenceModel.getRootChakraTimeSpent();
                    silenceModel.setRootChakraTimeSpent(time + chakraTimeSpent);
                    break;

            }
            realm.copyToRealmOrUpdate(silenceModel);

        } else {
            // first  time
            switch (chakraType) {
                case "Crown":
                    // first
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setCrownChakraTimeSpent(chakraTimeSpent);

                case "Third Eye":
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setThirdEyeChakraTimeSpent(chakraTimeSpent);

                case "Throat":
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setThroatChakraTimeSpent(chakraTimeSpent);

                case "Heart":
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setHeartChakraTimeSpent(chakraTimeSpent);

                case "Sacral":
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setSacralChakraTimespent(chakraTimeSpent);

                case "Solar Plexus":
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setSolarPlexusChakraTimeSpent(chakraTimeSpent);

                case "Root":
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setRootChakraTimeSpent(chakraTimeSpent);

            }
            realm.copyToRealm(silenceModel);

        }
        realm.commitTransaction();


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
                        // continue with exit
                        handler.removeCallbacks(r);
                        if (player != null) {
                            player.stop();
                            player.release();
                            player = null;
                        }
                        writeToDB();
                        Intent intent = new Intent(PlayingChakraActivity.this, MeditationsActivity.class);
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
