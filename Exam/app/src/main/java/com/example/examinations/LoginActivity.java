package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class LoginActivity extends AppCompatActivity {
    Button examStart;
    EditText nameEt, stunoEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        examStart = findViewById(R.id.bt_exam_start);
        nameEt = findViewById(R.id.et_name);
        stunoEt = findViewById(R.id.et_stuno);


        examStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputname=nameEt.getText().toString();
                String inputstuno=stunoEt.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("user", 0).edit();
                editor.putString("name",inputname);
                editor.putString("stuno",inputstuno);
                editor.commit();
                Intent  intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }

}
