package com.example.examinations;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.examinations.bean.ResultBean;
import com.example.examinations.db.PrepareData;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // 定义控件
    TextView titleTv, qsnumberTv,qstitleTv;
    ImageView prevIv, nextIv;
    private int pageIndex=1;
    Button submit;
    TextView qsnameTv;
    private static RadioGroup qsRadioGroup;
    private static LinearLayout cbLayout;
    RadioButton rbA, rbB, rbC, rbD;
    CheckBox cbA, cbB, cbC, cbD;
    Chronometer ch;
    private List<ResultBean> resultBeans;
    private String selected;
    private String title,opa,opb,opc,opd,result;
    private boolean[] results = new boolean[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        initView();
        //设置开始计时时间
        ch.setBase(SystemClock.elapsedRealtime() );
        //启动计时器
        ch.start();
        // 准备数据
        final List<ResultBean> list = PrepareData.loadData();
        // 渲染数据
        showData(list.get(0));
        // 监听单项选择
        qsRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (qsRadioGroup.indexOfChild(group.findViewById(checkedId))){
                    case 0:
                        selected = "A";
                        break;
                    case 1:
                        selected = "B";
                        break;
                    case 2:
                        selected = "C";
                        break;
                    case 3:
                        selected = "D";
                        break;
                }
            }
        });
        // 获取用户多选结果 下一页按钮
        nextIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbA.isChecked()&&(!cbB.isChecked()&&!cbC.isChecked()&&!cbD.isChecked())) {selected = "A";}else
                if(cbB.isChecked()&&(!cbA.isChecked()&&!cbC.isChecked()&&!cbD.isChecked())) selected = "B";else
                if(cbC.isChecked()&&(!cbB.isChecked()&&!cbA.isChecked()&&!cbD.isChecked())) selected = "C";else
                if(cbD.isChecked()&&(!cbB.isChecked()&&!cbC.isChecked()&&!cbA.isChecked())) selected = "D";else
                if(cbA.isChecked()&&cbB.isChecked()&&(!cbC.isChecked()&&!cbD.isChecked())) selected = "AB";else
                if(cbA.isChecked()&&cbC.isChecked()&&(!cbB.isChecked()&&!cbD.isChecked())) selected = "AC";else
                if(cbA.isChecked()&&cbD.isChecked()&&(!cbC.isChecked()&&!cbB.isChecked())) selected = "AD";else
                if(cbA.isChecked()&&cbB.isChecked()&&cbC.isChecked()&&(!cbD.isChecked())) selected = "ABC";else
                if(cbA.isChecked()&&cbB.isChecked()&&cbC.isChecked()&&cbD.isChecked()) selected = "ABCD";else
                if(cbA.isChecked()&&cbB.isChecked()&&cbD.isChecked()&&(!cbC.isChecked())) selected = "ABD";else
                if(cbA.isChecked()&&cbC.isChecked()&&cbD.isChecked()&&(!cbB.isChecked())) selected = "ACD";else
                if(cbB.isChecked()&&cbC.isChecked()&&(!cbA.isChecked()&&!cbD.isChecked())) selected = "BC";else
                if(cbB.isChecked()&&cbD.isChecked()&&(!cbC.isChecked()&&!cbA.isChecked())) selected = "BD";else
                if(cbB.isChecked()&&cbC.isChecked()&&cbD.isChecked()) selected = "BCD";else
                if(cbC.isChecked()&&cbD.isChecked()&&(!cbA.isChecked()&&!cbB.isChecked())) selected = "CD";
                if ((selected.equals(result))) {
                    results[pageIndex - 1] = true;
                } else {
                    results[pageIndex - 1] = false;
                }
                if (pageIndex<=19) {
                    if(pageIndex<=10){
                        qsRadioGroup.clearCheck();
                    }else {
                        cbA.setChecked(false);
                        cbB.setChecked(false);
                        cbC.setChecked(false);
                        cbD.setChecked(false);
                    }
                    pageIndex++;
                    selected = "";
                    showData(list.get(pageIndex-1));
                }else {
                    Toast.makeText(MainActivity.this,"已经是最后一题!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        prevIv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(pageIndex>1) {
                    pageIndex--;
                    showData(list.get(pageIndex-1));
                }else{
                    Toast.makeText(MainActivity.this,"已经是第一题!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 监听提交按钮
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
                intent.putExtra("results", results);
                if (pageIndex<20){
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("交卷提示")
                            .setMessage("还有未完成的题目, 确定交卷?")
                            .setCancelable(false)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    nextIv.performClick();
                                    startActivity(intent);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                } else {
                    nextIv.performClick();
                    startActivity(intent);
                }
            }
        });
        //为Chronomter绑定事件监听器
        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //如果计时超过了30分钟
                if ( SystemClock.elapsedRealtime() - ch.getBase() > 3 * 60 * 1000) {
                    ch.stop();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("交卷提示")//设置标题
                            .setMessage("时间到, 将自动交卷!")//设置内容
                            .setCancelable(false)//设置是否可以点击对话框以外的地方消失
                            .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    submit.performClick();
                                }
                            });
                    android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
    }

    private void initView() {
        titleTv = findViewById(R.id.title_tv);
        qsnumberTv = findViewById(R.id.qsnumber_tv);
        prevIv = findViewById(R.id.prev_iv);
        nextIv = findViewById(R.id.next_iv);
        submit = findViewById(R.id.submit_bt);
        qsnameTv = findViewById(R.id.qsname_tv);
        qsRadioGroup = findViewById(R.id.qs_rg);
        rbA = findViewById(R.id.rb_A);
        rbB = findViewById(R.id.rb_B);
        rbC = findViewById(R.id.rb_C);
        rbD = findViewById(R.id.rb_D);
        cbLayout = findViewById(R.id.cb_layout);
        cbA = findViewById(R.id.cb_A);
        cbB = findViewById(R.id.cb_B);
        cbC = findViewById(R.id.cb_C);
        cbD = findViewById(R.id.cb_D);
        ch = findViewById(R.id.timer_ch);
    }
    public void showData(ResultBean bean) {
        title = bean.getTitle();
        opa = bean.getOpa();
        opb = bean.getOpb();
        opc = bean.getOpc();
        opd = bean.getOpd();
        result = bean.getResult().trim();
        qsnumberTv.setText(pageIndex+"/20");

        qsnameTv.setText(title);
        if(pageIndex<=10){
            titleTv.setText("单选题");
            rbA.setText(opa);
            rbB.setText(opb);
            rbC.setText(opc);
            rbD.setText(opd);
        }else{
            qsRadioGroup.setVisibility(View.GONE);
            cbLayout.setVisibility(View.VISIBLE);
            titleTv.setText("多选题");
            cbA.setText(opa);
            cbB.setText(opb);
            cbC.setText(opc);
            cbD.setText(opd);
        }
    }
}
