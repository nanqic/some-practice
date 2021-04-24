package com.example.examinations.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.examinations.LoginActivity;
import com.example.examinations.MainActivity;
import com.example.examinations.MyApplication;
import com.example.examinations.bean.ResultBean;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PrepareData {
    static Context mContext = MyApplication.getAppContext();
//    public static MyDBHelper dbHelper = new MyDBHelper(mContext);

//    String path = "/data/data/" + mContext.getPackageName() + "/databases/";


    public static List<ResultBean> loadData() {


        //写入测试数据
//        ContentValues values = new ContentValues();
//        String val ="{\"title\":\"1.关于Java编译，下面哪一个正确（）（选择一项）\",\"opa\":\"A.Java程序经编译后产生machine code\",\"opb\":\"B.Java程序经编译后会生产byte code\",\"opc\":\"C.Java程序经编译后会产生DLL\",\"opd\":\"D.以上都不正确\",\"result\":\"C\"}";
//        values.put("content", val);
//        db.insert("qs_info", null, values);

        copyDatabase("qs_data.db");
        File file = new File(mContext.getFilesDir(), "qs_data.db");
        SQLiteDatabase db = SQLiteDatabase.openDatabase(file.getAbsolutePath(),
                null,SQLiteDatabase.OPEN_READONLY);
        // 查询题目
        List<ResultBean> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from qs_info", null);
        list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex("content"));
            ResultBean dataBean = new Gson().fromJson(content, ResultBean.class);
            list.add(dataBean);
        }
        cursor.close();
        db.close();
        return list;
    }
    private static void copyDatabase(String dbname){
        final File file = new File(mContext.getFilesDir(), dbname);
        if(!file.exists()){
            AssetManager assets = mContext.getAssets();// 从该目录下复制
            FileOutputStream fos = null;
            InputStream is = null;
            try {
                // 得到输入流
                is = assets.open(dbname);
                // 读写
                fos = new FileOutputStream(file);
                // 缓冲区
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = is.read(b)) != -1){
                    fos.write(b, 0, len);
                }
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                try{
                    is.close();
                    fos.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
