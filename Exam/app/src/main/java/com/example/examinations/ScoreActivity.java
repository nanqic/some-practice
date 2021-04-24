package com.example.examinations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ScoreActivity extends AppCompatActivity {
    ListView resList;
    TextView nameTv;
    String[] showResult = new String[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        SharedPreferences sp = ScoreActivity.this.getSharedPreferences("user",0);
        String name = sp.getString("name","");
        Intent intent = this.getIntent();
        boolean[] results = intent.getBooleanArrayExtra("results");
        resList = findViewById(R.id.result_list);
        nameTv = findViewById(R.id.name_tv);
        int i=0,j = 0;
        for (boolean res : results){
            if(res){
                j += 5;
                showResult[i]= "第"+(i+1)+"题 ----------------- √";
            }else {
                showResult[i] = "第"+(i+1)+"题 ----------------- ×";
            }
            i++;
        }
        nameTv.setText(name+"同学, 你的得分为: "+j+"分");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, showResult);
        resList.setAdapter(adapter);

    }
}
