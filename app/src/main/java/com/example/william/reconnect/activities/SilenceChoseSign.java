package com.example.william.reconnect.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.william.reconnect.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SilenceChoseSign extends AppCompatActivity {

    @BindView(R.id.choose_sign)
    TextView chooseSign;
    @BindView(R.id.silence_btn_1)
    Button silenceBtn1;
    @BindView(R.id.silence_btn_2)
    Button silenceBtn2;
    @BindView(R.id.silence_btn_3)
    Button silenceBtn3;
    @BindView(R.id.silence_btn_4)
    Button silenceBtn4;
    @BindView(R.id.silence_btn_5)
    Button silenceBtn5;
    @BindView(R.id.silence_btn_6)
    Button silenceBtn6;
    @BindView(R.id.silence_btn_7)
    Button silenceBtn7;
    @BindView(R.id.silence_btn_8)
    Button silenceBtn8;
    @BindView(R.id.btn_create_sign)
    Button btnCreateSign;
    @BindView(R.id.btn_silence_info)
    Button btnSilenceInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_chose_sign);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.silence_btn_1, R.id.silence_btn_2, R.id.silence_btn_3, R.id.silence_btn_4, R.id.silence_btn_5, R.id.silence_btn_6, R.id.silence_btn_7, R.id.silence_btn_8})
    public void onViewClicked(View view) {

        Resources res = getResources(); //assuming in an activity for example, otherwise you can provide a context.
        String[] shoppingItems = res.getStringArray(R.array.seized_item);

        String sendSign = "";

        /**  String silence_sign_1 = shoppingItems[0];// Kanyon Istanbu
         String silence_sign_2 = shoppingItems[1];// Kanyon Istanbu
         String silence_sign_3 = shoppingItems[2];// Kanyon Istanbu
         String silence_sign_4 = shoppingItems[3];// Kanyon Istanbu
         String silence_sign_5 = shoppingItems[4];// Kanyon Istanbu
         String silence_sign_6 = shoppingItems[5];// Kanyon Istanbu
         String silence_sign_7 = shoppingItems[6];// Kanyon Istanbu
         String silence_sign_8 = shoppingItems[7];// Kanyon Istanbu

         **/


        switch (view.getId()) {
            case R.id.silence_btn_1:
                sendSign = shoppingItems[0];
                Intent intent = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent.putExtra("sign", sendSign);
                startActivity(intent);
                break;
            case R.id.silence_btn_2:
                sendSign = shoppingItems[1];
                Intent intent1 = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent1.putExtra("sign", sendSign);
                startActivity(intent1);
                break;
            case R.id.silence_btn_3:
                sendSign = shoppingItems[2];
                Intent intent2 = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent2.putExtra("sign", sendSign);
                startActivity(intent2);
                break;
            case R.id.silence_btn_4:
                sendSign = shoppingItems[3];
                Intent intent3 = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent3.putExtra("sign", sendSign);
                startActivity(intent3);
                break;
            case R.id.silence_btn_5:
                sendSign = shoppingItems[4];
                Intent intent4 = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent4.putExtra("sign", sendSign);
                startActivity(intent4);
                break;
            case R.id.silence_btn_6:
                sendSign = shoppingItems[5];
                Intent intent5 = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent5.putExtra("sign", sendSign);
                startActivity(intent5);
                break;
            case R.id.silence_btn_7:
                sendSign = shoppingItems[6];
                Intent intent6 = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent6.putExtra("sign", sendSign);
                startActivity(intent6);
                break;
            case R.id.silence_btn_8:
                sendSign = shoppingItems[7];
                Intent intent7 = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent7.putExtra("sign", sendSign);
                startActivity(intent7);
                break;


        }
        btnSilenceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                silenceWhy(SilenceChoseSign.this,getString(R.string.whychoos));
            }
        });

    }



        public void showDialog(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.create_sign_silence_dialog);


        TextView text = dialog.findViewById(R.id.text_dialog);

        final EditText editCustomSign = dialog.findViewById(R.id.edit_custom_sign);

        final String typedText = editCustomSign.getText().toString();
        final Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);


        /* TextWatcher to check if edittext is not empty */
        editCustomSign.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                dialogButton.setEnabled(false);
                dialogButton.setBackgroundResource(R.drawable.btn_rect);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    dialogButton.setBackgroundResource(R.drawable.btn_rect_green);
                    dialogButton.setEnabled(true);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (typedText.isEmpty()) {
                    editCustomSign.setError("Please write");
                    editCustomSign.requestFocus();
                }

                dialog.dismiss();
                final String typedText = editCustomSign.getText().toString();
                Intent intent = new Intent(SilenceChoseSign.this, SilencePlaying.class);
                intent.putExtra("silence", typedText);
                Log.d("hooooooooo", "onCreate: " + typedText);
                startActivity(intent);

                /* Sending typedText to SilencePlaying Activity */


            }

        });

        dialog.show();


    }


    public void silenceWhy(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.silence_why_choose_sign);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    @OnClick(R.id.btn_create_sign)
    public void onViewClicked() {
        showDialog(this, "Create Custom Sign");
    }


}




/*
  @OnClick(R.id.silence_btn_1) public void onSilenceBtn1Clicked() {
 * Resources res = getResources(); //assuming in an activity for example, otherwise you can provide a context.
 * String[] shoppingItems = res.getStringArray(R.array.seized_item);
 * String silence_sign_1 = shoppingItems[0];
 * Intent intent = new Intent(this, SilencePlaying.class);
 * intent.putExtra("sign_1", silence_sign_1);
 * startActivity(intent);
 * <p>
 * <p>
 * }
 * @OnClick(R.id.silence_btn_2) public void onSilenceBtn2Clicked() {
 * Intent intent = new Intent(this, SilencePlaying.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_3) public void onSilenceBtn3Clicked() {
 * Intent intent = new Intent(this, SilencePlaying.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_4) public void onSilenceBtn4Clicked() {
 * Intent intent = new Intent(this, SilencePlaying.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_5) public void onSilenceBtn5Clicked() {
 * Intent intent = new Intent(this, SilencePlaying.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_6) public void onSilenceBtn6Clicked() {
 * Intent intent = new Intent(this, SilencePlaying.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_7) public void onSilenceBtn7Clicked() {
 * Intent intent = new Intent(this, SilencePlaying.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_8) public void onSilenceBtn8Clicked() {
 * Intent intent = new Intent(this, SilencePlaying.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.btn_create_sign) public void onBtnCreateSignClicked() {
 * showDialog(this, "Create Custom Sign");
 * <p>
 * }
 * @OnClick(R.id.btn_silence_info) public void onBtnSilenceInfoClicked() {
 * SilenceWhy alert = new SilenceWhy();
 * alert.showDialog(this, getString(R.string.whychoos));
 * <p>
 * }
 * <p>
 * public void showDialog(Activity activity, String msg) {
 * final Dialog dialog = new Dialog(activity);
 * dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
 * dialog.setCancelable(false);
 * dialog.setContentView(R.layout.create_sign_silence_dialog);
 * <p>
 * TextView text =  dialog.findViewById(R.id.text_dialog);
 * <p>
 * final EditText editCustomSign = dialog.findViewById(R.id.edit_custom_sign);
 * final String typedText = editCustomSign.getText().toString();
 * //        text.setText(typedText);
 * <p>
 * Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
 * dialogButton.setOnClickListener(new View.OnClickListener() {
 * @Override public void onClick(View v) {
 * dialog.dismiss();
 * <p>
 * //Sending typedText to SilencePlaying Activity
 * final String typedText = editCustomSign.getText().toString();
 * Intent intent = new Intent(SilenceChoseSign.this, SilencePlaying.class);
 * intent.putExtra("silence", typedText);
 * Log.d("hooooooooo", "onCreate: " + typedText);
 * startActivity(intent);
 * <p>
 * }
 * });
 * <p>
 * dialog.show();
 * <p>
 * <p>
 * }
 * <p>
 * }
 */


