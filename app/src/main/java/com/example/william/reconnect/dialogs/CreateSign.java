package com.example.william.reconnect.dialogs;

import android.widget.Button;
import android.widget.EditText;

import com.example.william.reconnect.R;

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
