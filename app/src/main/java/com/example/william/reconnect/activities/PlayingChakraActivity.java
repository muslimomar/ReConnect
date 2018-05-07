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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayingChakraActivity extends AppCompatActivity {
    int position;
    String jsonChakras;
    List<Chakra> chakraList;

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

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("position");
            jsonChakras = bundle.getString("chakra_array_list");
            Gson gson = new Gson();
            Type type = new TypeToken<List<Chakra>>() {
            }.getType();
            chakraList = gson.fromJson(jsonChakras, type);
        }

        initializeImages();
        setImageOpacity();
        rotateChakra();
    }


    private void initializeImages() {

        playingIconIv.setImageResource(chakraList.get(position).getChakraIcon());
        backgroundGradient(relativeLayout);

    }

    private void setImageOpacity() {
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
                        getResources().getColor(android.R.color.white), chakraList.get(position).getChakraColor(),
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
        Chakra chakra = chakraList.get(position);

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.info_dialog);
        dialog.setCancelable(true);

        TextView chakraTitle = dialog.findViewById(R.id.chakra_title);
        Button okBtn = dialog.findViewById(R.id.ok_btn);
        TextView chakraInfoPosition = dialog.findViewById(R.id.chakra_info_position);
        TextView chakraInfoTv = dialog.findViewById(R.id.chakra_info_tv);
        ImageView chakraInfoPlaceIv = dialog.findViewById(R.id.chakra_place_iv);
        LinearLayout dialogCenterLayout = dialog.findViewById(R.id.dialog_center_layout);

        dialogCenterLayout.setBackgroundColor(chakra.getChakraInfoColor());
        chakraInfoPosition.setText(chakra.getChakraPosition());
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
