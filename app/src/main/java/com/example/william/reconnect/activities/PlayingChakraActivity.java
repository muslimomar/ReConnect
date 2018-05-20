package com.example.william.reconnect.activities;

import android.app.Dialog;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayingChakraActivity extends AppCompatActivity {

    public static final String TAG = PlayingChakraActivity.class.getSimpleName();
    int position = 0;
    String chakraType = "";
    String musicType = "";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakra_playing);
        ButterKnife.bind(this);

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
                finish();
            }
        };
        handler.postDelayed(r, 5000);
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

        for (int i = 0; i < chakras.size(); i++) {
            if (chakraType.equalsIgnoreCase(chakras.get(i).getChakraName())) {
                position = i;
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

        dialogCenterLayout.setBackgroundColor(chakra.getChakraColor());
        chakraInfoTv.setText(chakra.getChakraInfo());
        chakraInfoPlaceIv.setImageResource(chakra.getChakraPlaceImg());

        chakraTitle.setText(chakra.getChakraName());
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
