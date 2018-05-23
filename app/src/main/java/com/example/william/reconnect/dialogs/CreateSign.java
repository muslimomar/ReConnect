package com.example.william.reconnect.dialogs;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.william.reconnect.R;
import com.example.william.reconnect.activities.SilencePlaying;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Mahmoud on 5/11/2018.
 */

public class CreateSign {


    @BindView(R.id.edit_custom_sign)
    EditText editCustomSign;
    @BindView(R.id.btn_dialog)
    Button btnDialog;
    String customSign;



    @OnClick(R.id.btn_dialog)
    public void onViewClicked() {
    }
}
