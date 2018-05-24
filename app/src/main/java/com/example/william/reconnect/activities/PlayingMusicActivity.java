package com.example.william.reconnect.activities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.william.reconnect.R;
import com.example.william.reconnect.adapter.MusicListAdapter;
import com.example.william.reconnect.model.Music;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayingMusicActivity extends AppCompatActivity {

    Context context = this;


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

    MusicListAdapter mAdapter;
    ArrayList<Music> musicModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playing);
        ButterKnife.bind(this);


        initializeImages();
        setImageOpacity();
        rotateChakra();

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
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.info_btn:
                break;
            case R.id.music_btn:
                Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.music_list_dialog);
                ListView musicList = (ListView) dialog.findViewById(R.id.music_list);

                musicModels = new ArrayList<>();
                musicModels.add(new Music("Ahmad kaya", "07:2"));
                musicModels.add(new Music("Enrique Iglasias", "1:22"));
                musicModels.add(new Music("Justin Beiber", "01:26"));
                musicModels.add(new Music("Mahir Zain", "20:12"));
                musicModels.add(new Music("Rihanna", "04:43"));
                musicModels.add(new Music("Torn", "0:22"));
                musicModels.add(new Music("Chab Khalid", "02:31"));
                mAdapter = new MusicListAdapter(this, musicModels);
                musicList.setAdapter(mAdapter);
                dialog.show();
        }
    }
}
