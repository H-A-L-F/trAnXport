package com.example.tranxport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private boolean isPassVis = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText ETuname = findViewById(R.id.ET_username);
        final EditText ETpassword = findViewById(R.id.ET_password);
        final ImageView passVis = findViewById(R.id.ic_passVis);
        final TextView signUpBtn = findViewById(R.id.btn_signup);

        passVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPassVis) {
                    isPassVis = true;

                    ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passVis.setImageResource(R.drawable.passVisible);
                } else {
                    isPassVis = false;

                    ETpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passVis.setImageResource(R.drawable.passInvis);
                }

                //move cursor to last text
                ETpassword.setSelection(ETpassword.length());
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}