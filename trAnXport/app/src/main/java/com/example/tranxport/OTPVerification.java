package com.example.tranxport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.SmsManager;
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

    private String code;
    private boolean resendEnabled = false;
    private int resendCd = 60;

    private int selectedETPos = 0;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(OTPVerification.this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

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

        code = generateCode();
        sendSMS(code);
        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resendEnabled) {
                    // resend code
                    code = generateCode();
                    sendSMS(code);

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
                    if(generateOtp.equals(code)) success();
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
        resendBtn.setTextColor(Color.parseColor("#99000000"));

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

    public void success() {
        insertUser();
        startActivity(new Intent(OTPVerification.this, MainActivity.class));
    }

    public void insertUser() {
        SQLiteDatabase db = DBOpenHelper.getInstance(this).getWritableDatabase();
        ContentValues cv = new ContentValues();
        sp = getSharedPreferences("MySp", MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        cv.put(DBOpenHelper.USERNAME, sp.getString("username", null));
        cv.put(DBOpenHelper.EMAIL, sp.getString("email", null));
        cv.put(DBOpenHelper.PHONENUMBER, sp.getString("mobile", null));
        cv.put(DBOpenHelper.PASSWORD, sp.getString("password", null));
        db.insert(DBOpenHelper.ACCOUNT, null, cv);
    }

    public String generateCode() {
        int a1 = (int) (Math.random() * 10);
        int a2 = (int) (Math.random() * 10);
        int a3 = (int) (Math.random() * 10);
        int a4 = (int) (Math.random() * 10);

        String a = Integer.toString(a1) + Integer.toString(a2) + Integer.toString(a3) + Integer.toString(a4);

        return a;
    }

    public void sendSMS(String message) {
        String phoneNum = "08977628421";

        SmsManager mySmsManager = SmsManager.getDefault();
        mySmsManager.sendTextMessage(phoneNum, null, message, null, null);
    }
}