package com.ghgk.photomanage.start;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.ghgk.photomanage.MainActivity;
import com.ghgk.photomanage.R;
import com.ghgk.photomanage.user_main.MainActivity_2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import okhttp3.OkHttpClient;

public class loginActivity extends Activity implements AnimationListener {

        private ImageView _ivWelcome;
    private SharedPreferences sp;
    private String isEnter = "";
    private String url = "http://192.168.0.18:8080/TotemDown/managerServer?username=linyuanbin&password=123456";
    OkHttpClient okHttpClient = new OkHttpClient();
    public static String SDPATH = "";//本地根目录

        @Override
           protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.start_the_animation);
//        _ivWelcome = (ImageView)findViewById(R.id.start_picture);
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_);
//        animation.setAnimationListener(this);
//        _ivWelcome.setAnimation(animation);
//            SDPATH = Environment.getExternalStorageDirectory() + "/";
//            File file = new File(SDPATH +"dtlp");
//            file.mkdir();
//            System.out.println("创建了文件夹");
//            File file1 = new File(SDPATH +"dtlp/"+"photo"+".txt");
//            try {
//                file1.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            File file2 = new File(SDPATH +"dtlp/"+"TLTTLI.txt");
//            try {
//                file2.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println("创建了文件夹");


            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
//            Toast.makeText(MainActivity.this, "未授权！！！", Toast.LENGTH_SHORT).show();
            }else
            {
                setContentView(R.layout.start_the_animation);
                _ivWelcome = (ImageView)findViewById(R.id.start_picture);
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_);
                animation.setAnimationListener(this);
                _ivWelcome.setAnimation(animation);
                SDPATH = Environment.getExternalStorageDirectory() + "/";
                File file = new File(SDPATH +"dtlp");
                file.mkdir();
                System.out.println("创建了文件夹");
                File file1 = new File(SDPATH +"dtlp/"+"photo"+".txt");
                try {
                    file1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File file2 = new File(SDPATH +"dtlp/"+"TLTTLI.txt");
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("创建了文件夹");

            }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setContentView(R.layout.start_the_animation);
                _ivWelcome = (ImageView)findViewById(R.id.start_picture);
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_);
                animation.setAnimationListener(this);
                _ivWelcome.setAnimation(animation);
                SDPATH = Environment.getExternalStorageDirectory() + "/";
                File file = new File(SDPATH +"dtlp");
                file.mkdir();
                System.out.println("创建了文件夹");
                File file1 = new File(SDPATH +"dtlp/"+"photo"+".txt");
                try {
                    file1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File file2 = new File(SDPATH +"dtlp/"+"TLTTLI.txt");
                try {
                    file2.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("创建了文件夹");

            }
            else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
//                    Toast.makeText(MainActivity.this,"没有权限,请开启对应权限",Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        }
    }

    @Override
public void onAnimationEnd(Animation animation) {
            sp = getSharedPreferences("login", Context.MODE_PRIVATE);
            isEnter = sp.getString("isEnter","");
            if (isEnter.equals("true"))
            {
                startActivity(new Intent(this,MainActivity_2.class));
                finish();
            }
            else
            {
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
            //读取文件 将userID读取出来赋值给uesrID
            File filename = new File(SDPATH +"dtlp/"+"TLTTLI_MANAGER.txt"); // 要读取以上路径的input。txt文件
            String userdata = "";//读出来的Json数据
            String userid = "";
            try {
                FileReader read = new FileReader(filename);
                StringBuffer sb = new StringBuffer();
                char ch[] = new char[1024];
                int d = read.read(ch);
                while(d!=-1){
                    String str = new String(ch,0,d);
                    sb.append(str);
                    d = read.read(ch);
                }
                userdata= sb.toString();
                System.out.print(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("mangerData", "mangerData: = " + userdata);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(userdata);
                userid=jsonObject.getJSONObject("mangerData").getString("mIdCard");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainActivity.mangerID = userid;
    }

        @Override
public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

@Override
public void onAnimationStart(Animation animation) {
// TODO Auto-generated method stub

  }
}

