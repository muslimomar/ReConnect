package com.example.william.reconnect.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
    String selectedMsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_chose_sign);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.silence_btn_1, R.id.silence_btn_2, R.id.silence_btn_3, R.id.silence_btn_4, R.id.silence_btn_5, R.id.silence_btn_6, R.id.silence_btn_7, R.id.silence_btn_8, R.id.btn_silence_info})
    public void onViewClicked(View view) {

        Resources res = getResources(); //assuming in an activity for example, otherwise you can provide a context.
        String[] shoppingItems = res.getStringArray(R.array.seized_item);

        String sendSign = "";

        switch (view.getId()) {

            case R.id.silence_btn_1:
                sendSign = shoppingItems[0];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.silence_btn_2:
                sendSign = shoppingItems[1];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.silence_btn_3:
                sendSign = shoppingItems[2];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.silence_btn_4:
                sendSign = shoppingItems[3];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.silence_btn_5:
                sendSign = shoppingItems[4];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.silence_btn_6:
                sendSign = shoppingItems[5];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.silence_btn_7:
                sendSign = shoppingItems[6];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.silence_btn_8:
                sendSign = shoppingItems[7];
                selectedMsg = sendSign;
                mainActivityRedirecter();
                break;
            case R.id.btn_silence_info:
                silenceWhy(this, getString(R.string.whychoos));
                break;
        }

        saveSilenceDay(selectedMsg);
    }
    private void mainActivityRedirecter() {
        Intent intent = new Intent(SilenceChoseSign.this, PlayingSilenceActivity.class);
        intent.putExtra("sign", selectedMsg);
        startActivity(intent);
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
                saveSilenceDay(typedText);
                /* Sending typedText to PlayingSilenceActivity Activity */
                selectedMsg = editCustomSign.getText().toString();
                mainActivityRedirecter();
            }

        });

        dialog.show();
    }

    public void silenceWhy(Activity activity, String msg) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.silence_why_choose_sign);
        final Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog_why);
        TextView textWhy = dialog.findViewById(R.id.text_dialog_why);
        textWhy.setText(msg);
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

    public void saveSilenceDay(String selectedMsg) {
    }
}