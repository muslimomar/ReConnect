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
import android.widget.Toast;

import com.example.william.reconnect.R;
import com.example.william.reconnect.model.Reminder;
import com.example.william.reconnect.reminder.AlarmScheduler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

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
    Realm realm;
    String selectedMsg = "";
    long selectedTimeStamp;
    int selectedHours;
    int selectedMins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silence_chose_sign);
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            selectedTimeStamp = bundle.getLong("timestamp", 0);
            selectedHours = bundle.getInt("hours", 0);
            selectedMins = bundle.getInt("mins", 0);
        }


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


        }

        saveSilenceDay(selectedMsg);

        btnSilenceInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                silenceWhy(SilenceChoseSign.this, getString(R.string.whychoos));
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
                saveSilenceDay(typedText);
                mainActivityRedirecter();
                /* Sending typedText to PlayingSilenceActivity Activity */


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


    public void saveSilenceDay(String selectedMsg) {
        realm.beginTransaction();
        Reminder reminder = realm.where(Reminder.class).equalTo("reminderType", Reminder.TYPE_SILENCE).findFirst();
        if (reminder != null) {
            // EDIT existing
            reminder.setSilenceMessage(selectedMsg);
            reminder.setPickedHours(selectedHours);
            reminder.setPickedMinutes(selectedMins);
            reminder.setAlarmTimestamp(selectedTimeStamp);

            setAlarm(selectedTimeStamp, reminder.getId(), reminder.getRequestCode());

        } else {
            // New
            int requestCode = (int) System.currentTimeMillis();
            reminder = new Reminder(Reminder.TYPE_SILENCE, selectedMsg, selectedHours, selectedMins, selectedTimeStamp, requestCode);
            setAlarm(selectedTimeStamp, reminder.getId(), requestCode);
        }

        realm.copyToRealmOrUpdate(reminder);
        realm.commitTransaction();
    }

    private void setAlarm(long selectedTimeStamp, String id, int requestCode) {
        new AlarmScheduler().setAlarm(this, selectedTimeStamp, id, requestCode);

    }

    public void mainActivityRedirecter() {
        Toast.makeText(this, "Silence day set!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SilenceChoseSign.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}




/*
  @OnClick(R.id.silence_btn_1) public void onSilenceBtn1Clicked() {
 * Resources res = getResources(); //assuming in an activity for example, otherwise you can provide a context.
 * String[] shoppingItems = res.getStringArray(R.array.seized_item);
 * String silence_sign_1 = shoppingItems[0];
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
 * intent.putExtra("sign_1", silence_sign_1);
 * startActivity(intent);
 * <p>
 * <p>
 * }
 * @OnClick(R.id.silence_btn_2) public void onSilenceBtn2Clicked() {
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_3) public void onSilenceBtn3Clicked() {
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_4) public void onSilenceBtn4Clicked() {
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_5) public void onSilenceBtn5Clicked() {
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_6) public void onSilenceBtn6Clicked() {
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_7) public void onSilenceBtn7Clicked() {
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
 * startActivity(intent);
 * }
 * @OnClick(R.id.silence_btn_8) public void onSilenceBtn8Clicked() {
 * Intent intent = new Intent(this, PlayingSilenceActivity.class);
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
 * //Sending typedText to PlayingSilenceActivity Activity
 * final String typedText = editCustomSign.getText().toString();
 * Intent intent = new Intent(SilenceChoseSign.this, PlayingSilenceActivity.class);
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


