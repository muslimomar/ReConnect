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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

    long startTime;
    long endTime;
    long chakraTimeSpent;
    Realm realm;
    public static final String TAG = PlayingChakraActivity.class.getSimpleName();
    int position = 0;
    String chakraType;
    String musicType;
    ArrayList<Chakra> chakras = new ArrayList<>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.playing_icon)
    ImageView playingIconIv;
    @BindView(R.id.music_iv)
    ImageView musicIv;
    @BindView(R.id.back_arrow_iv)
    ImageView backArrowIv;
    @BindView(R.id.info_iv)
    ImageView infoIv;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;
    int ONE_MIN_MS = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakra_playing);
        ButterKnife.bind(this);
        startTime = System.currentTimeMillis();
        realm = Realm.getDefaultInstance();
        prepareChakraList();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            chakraType = bundle.getString("chakra_type");
            musicType = bundle.getString("music_type");
        }
        initializeImages();
        rotateChakra();

        Handler handler = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                // TODO:  Stop music
                writeToDB();
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
        Drawable background3 = musicIv.getBackground();
        background3.setAlpha(40);

    }

    private void rotateChakra() {
        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        playingIconIv.startAnimation(rotation);
    }


    @OnClick(R.id.back_arrow_iv)
    public void backArrowBtn(View view) {
        NavUtils.navigateUpFromSameTask(this);
        writeToDB();
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
                Intent intent = new Intent(this, MainActivity.class);
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
        chakraTimeSpent = chakraTimeSpent / 1000 + 1;


        realm.beginTransaction();
        SilenceModel silenceModel = realm.where(SilenceModel.class).findFirst();

        switch (chakraType) {
            case "Crown":
                if (silenceModel != null) {
                    // exists
                    if (silenceModel.getCrownChakraTimeSpent() != 0) {
                        long time = silenceModel.getCrownChakraTimeSpent();
                        silenceModel.setCrownChakraTimeSpent(time + chakraTimeSpent);
                        realm.copyToRealmOrUpdate(silenceModel);
                    }
                } else {
                    // first  time
                    silenceModel = realm.createObject(SilenceModel.class, UUID.randomUUID().toString());
                    silenceModel.setCrownChakraTimeSpent(chakraTimeSpent);
                    realm.copyToRealm(silenceModel);
                }


            case "Third Eye":

            case "Throat":

            case "Heart":

            case "Sacral":

            case "Solar Plexus":

            case "Root":

        }

        realm.commitTransaction();

    }


}
