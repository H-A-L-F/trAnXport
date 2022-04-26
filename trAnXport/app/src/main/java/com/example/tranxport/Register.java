package com.example.tranxport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    private boolean isPassVis = false;
    private boolean isConfPassVis = false;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText ETUsername = findViewById(R.id.ET_username);
        final EditText ETemail = findViewById(R.id.ET_email);
        final EditText ETmobile = findViewById(R.id.ET_mobile);

        final EditText ETpassword = findViewById(R.id.ET_password);
        final EditText ETconfPassword = findViewById(R.id.ET_confPassword);
        final ImageView passIcon = findViewById(R.id.ic_passVis);
        final ImageView confPassIcon = findViewById(R.id.ic_confPassVis);

        final AppCompatButton signUpBtn = findViewById(R.id.btn_signup);
        final TextView signInBtn = findViewById(R.id.btn_signIn);

        isPassVis = false;

        ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passIcon.setImageResource(R.drawable.pass_hide);

        isConfPassVis = false;

        ETconfPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confPassIcon.setImageResource(R.drawable.pass_hide);

        passIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPassVis) {
                    isPassVis = true;

                    ETpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passIcon.setImageResource(R.drawable.pass_show);
                } else {
                    isPassVis = false;

                    ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passIcon.setImageResource(R.drawable.pass_hide);
                }

                //move cursor to last text
                ETpassword.setSelection(ETpassword.length());
            }
        });

        confPassIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConfPassVis) {
                    isConfPassVis = true;

                    ETconfPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    confPassIcon.setImageResource(R.drawable.pass_show);
                } else {
                    isConfPassVis = false;

                    ETconfPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confPassIcon.setImageResource(R.drawable.pass_hide);
                }


                //move cursor to last text
                ETconfPassword.setSelection(ETconfPassword.length());
            }
        });



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getUsername = ETUsername.getText().toString();
                final String getPass = ETpassword.getText().toString();
                final String getMobileTxt = ETmobile.getText().toString();
                final String getEmailTxt = ETemail.getText().toString();

                // save to shared preferences
                saveUser(getUsername, getEmailTxt, getPass, getMobileTxt);

                // Open OTP Verification page and passing the data
                Intent intent = new Intent(Register.this, OTPVerification.class);
                intent.putExtra("mobile", getUsername);
                intent.putExtra("email", getPass);
                startActivity(intent);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }

    private void saveUser(String uname, String email, String password, String mobile) {
        sp = getSharedPreferences("MySp", MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString("username", uname);
        spEditor.putString("email", email);
        spEditor.putString("password", password);
        spEditor.putString("mobile", mobile);
        spEditor.apply();
    }
}