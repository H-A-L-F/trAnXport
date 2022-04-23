package com.example.tranxport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class OTPVerification extends AppCompatActivity {

    private EditText otpEt1, otpEt2, otpEt3, otpEt4;
    private TextView resendBtn;
    private AppCompatButton verifyBtn;

    private boolean resendEnabled = false;
    private int resendCd = 60;

    private int selectedETPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpEt1 = findViewById(R.id.ET_otp1);
        otpEt2 = findViewById(R.id.ET_otp2);
        otpEt3 = findViewById(R.id.ET_otp3);
        otpEt4 = findViewById(R.id.ET_otp4);

        resendBtn = findViewById(R.id.btn_resendCode);
        verifyBtn = findViewById(R.id.btn_verify);

        final TextView otpEmail = findViewById(R.id.txt_otpEmail);
        final TextView otpMobile = findViewById(R.id.txt_otpMobile);

        // get email and mobile using intent
        final String getEmail = getIntent().getStringExtra("email");
        final String getMobile = getIntent().getStringExtra("mobile");

        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpEt1.addTextChangedListener(textWatcher);
        otpEt2.addTextChangedListener(textWatcher);
        otpEt3.addTextChangedListener(textWatcher);
        otpEt4.addTextChangedListener(textWatcher);

        // by default open keyboard at otpEt1
        showKeyboard(otpEt1);

        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resendEnabled) {
                    // resend code

                    // reset cd
                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String generateOtp = otpEt1.getText().toString()+otpEt2.getText().toString()+otpEt3.getText().toString()+otpEt4.getText().toString();

                if(generateOtp.length() == 4) {
                    // otp verification
                }
            }
        });
    }

    private void showKeyboard(EditText otpEt) {
        otpEt.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpEt, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer() {
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#990000000"));

        new CountDownTimer(resendCd * 1000, 1000) {

            @Override
            public void onTick(long millisecuntilfinished) {
                resendBtn.setText("Resend Code (" + (millisecuntilfinished / 1000) + ")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.blue2));
            }
        }.start();
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length() > 0) {
                switch (selectedETPos) {
                    case 0: {
                        selectedETPos = 1;
                        showKeyboard(otpEt2);
                        break;
                    }
                    case 1: {
                        selectedETPos = 2;
                        showKeyboard(otpEt3);
                        break;
                    }
                    case 2: {
                        selectedETPos = 3;
                        showKeyboard(otpEt4);
                        break;
                    }
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_DEL) {
            switch (selectedETPos) {
                case 3: {
                    selectedETPos = 2;
                    showKeyboard(otpEt3);
                    break;
                }
                case 2: {
                    selectedETPos = 1;
                    showKeyboard(otpEt2);
                    break;
                }
                case 1: {
                    selectedETPos = 0;
                    showKeyboard(otpEt1);
                    break;
                }
            }

            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}