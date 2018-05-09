package com.example.william.reconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.william.reconnect.activities.MainActivity;
import com.example.william.reconnect.activities.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    private FirebaseAuth mAuth;


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.signup_btn)
    Button signupBtn;
    @BindView(R.id.forgot)
    TextView forgot;
    @BindView(R.id.textView)
    TextView textView;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        mAuth = FirebaseAuth.getInstance();


    }


    //Register user method
    private void registerUser() {

        final String userMail = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        //If mail is empty
        if (userMail.isEmpty()) {

            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        //If it's not a real email
        if (!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()) {
            editTextEmail.setError("Please enter a valid Email!");
            editTextEmail.requestFocus();
            return;
        }

        //If pass is empty
        if (password.isEmpty()) {
            editTextPassword.setError("Password is requried!");
            editTextPassword.requestFocus();
            return;
        }


        //Check password length as Firebase restrict to 6 chars
        if (password.length() < 6) {
            editTextPassword.setError("Should be 6 or more!");
            editTextPassword.requestFocus();
            return;
        }

        //Load progressbar
        progressbar.setVisibility(View.VISIBLE);



        mAuth.createUserWithEmailAndPassword(userMail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //Hide progressbar
                progressbar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                    String user_id = mAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                    //Map to post all values to database at same time
                    Map newPost = new HashMap();
                    newPost.put("email", userMail);
                    newPost.put("password", password);
                    current_user_db.setValue(newPost);
                    Toast.makeText(LoginActivity.this, "Register Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {


                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                        Toast.makeText(LoginActivity.this, "Email is registered!", Toast.LENGTH_SHORT).show();
                    } else {


                        //Get the exception code
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });


    }


    //Login the user method
    private void loginUser() {

        String userMail = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (userMail.isEmpty()) {

            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()) {
            editTextEmail.setError("Please enter a valid Email!");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is requried!");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Should be 6 or more!");
            editTextPassword.requestFocus();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(userMail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressbar.setVisibility(View.GONE);
                pref = LoginActivity.this.getSharedPreferences("MyPref", 0);
                SharedPreferences.Editor editor = pref.edit();

                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    editor.putBoolean("IS_LOGIN", true);
                    editor.commit();


                } else {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick({R.id.login_btn, R.id.signup_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                loginUser();
                break;
            case R.id.signup_btn:
                registerUser();
                break;
        }
    }
}
