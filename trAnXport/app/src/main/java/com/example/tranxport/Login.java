package com.example.tranxport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private boolean isPassVis;

    int id;
    String username;
    String email;
    String phoneNumber;
    String password;
    boolean canLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText ETuname = findViewById(R.id.ET_username);
        final EditText ETpassword = findViewById(R.id.ET_password);
        final ImageView passVis = findViewById(R.id.ic_passVis);
        final TextView signUpBtn = findViewById(R.id.btn_signup);
        final AppCompatButton signInBtn = findViewById(R.id.btn_signIn);

        isPassVis = false;

        ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passVis.setImageResource(R.drawable.pass_hide);

        passVis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPassVis) {
                    isPassVis = true;

                    ETpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passVis.setImageResource(R.drawable.pass_show);
                } else {
                    isPassVis = false;

                    ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passVis.setImageResource(R.drawable.pass_hide);
                }

                //move cursor to last text
                ETpassword.setSelection(ETpassword.length());
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = ETuname.getText().toString();
                final String password = ETpassword.getText().toString();
                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Credentials Cannot Be Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase db = DBOpenHelper.getInstance(Login.this).getReadableDatabase();
                    Cursor cursor = db.query(
                            DBOpenHelper.ACCOUNT,
                            new String[] {
                                    DBOpenHelper.ID,
                                    DBOpenHelper.USERNAME,
                                    DBOpenHelper.EMAIL,
                                    DBOpenHelper.PHONENUMBER,
                                    DBOpenHelper.PASSWORD,
                            },
                            null,
                            null,
                            null,
                            null,
                            null
                    );

                    if(cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        do {
                            if((cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.USERNAME)).equals(username)) &&
                                    cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.PASSWORD)).equals(password)) {
                                id = cursor.getInt(cursor.getColumnIndexOrThrow(DBOpenHelper.ID));
                                email = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.EMAIL));
                                phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(DBOpenHelper.PHONENUMBER));
                                canLogin = true;
                                break;
                            }
                        } while(cursor.moveToNext());
                    }

                    if(canLogin) {
                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(Login.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
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