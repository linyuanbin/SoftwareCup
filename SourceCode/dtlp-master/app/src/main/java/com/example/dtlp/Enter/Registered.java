package com.example.dtlp.Enter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.start.loginActivity;
import com.example.dtlp.user_main.MainActivity_2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.Bmob;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//.User.user;

/**
 * Created by 阳瑞 on 2017/3/19.
 */
public class Registered extends Activity {


    private EditText name,password,job,conpassword,number;
    private RadioButton man,woman;
    private Button registered_;
    private ImageView registered_picture;

    private String KEY = "31704cfaa4b17e8f90c7dac59dbec4dd";

    private static final String MOBILE_PATTERN = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
    private static final String PASSWORD_PATTERN = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

    OkHttpClient okHttpClient = new OkHttpClient();

    private Pattern pattern = Pattern.compile(MOBILE_PATTERN);
    private Matcher matcher;

    private Pattern pattern1 = Pattern.compile(PASSWORD_PATTERN);
    private Matcher matcher1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered);
        Bmob.initialize(this,KEY);

//        loginActivity.db = openOrCreateDatabase("User.db",MODE_PRIVATE,null);

        initView();

    }
    public void doClick(View view)
    {
        switch (view.getId())
        {
            case R.id.registered_:
                if (name.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 昵称没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (password.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 密码没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (conpassword.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 确认密码没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!password.getText().toString().equals(conpassword.getText().toString()))
                {
                    Toast.makeText(Registered.this, "亲 两次密码不对哦", Toast.LENGTH_SHORT).show();
                }
                else if (number.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 电话号码没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (job.getText().toString().equals(""))
                {
                    Toast.makeText(Registered.this, "亲 职业没填哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!(man.isChecked()||woman.isChecked()))
                {
                    Toast.makeText(Registered.this, "亲 性别没选哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    String num = number.getText().toString();
                    String pass = password.getText().toString();
                    if (!validatenumber(num))
                    {
                        Toast.makeText(Registered.this, "亲 电话号码输入不对哦！！", Toast.LENGTH_SHORT).show();

                    }else  if (!validatepassword(pass))
                    {
                        Toast.makeText(Registered.this, "亲 请输入由英文和数字组成的密码哦！！", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        registered();
                    }
                }
                break;
            case R.id.registered_picture:
//                type = 1;
//                uploadHeadImage();
                break;
        }

    }
    private void initView(){
        name = (EditText) findViewById(R.id.Name);
        password = (EditText) findViewById(R.id.Password);
        conpassword = (EditText) findViewById(R.id.ConPassword);
        job = (EditText) findViewById(R.id.Job);
        number = (EditText) findViewById(R.id.Number);
        man = (RadioButton) findViewById(R.id.man);
        woman = (RadioButton) findViewById(R.id.woman);
        registered_ = (Button) findViewById(R.id.registered_);
        registered_picture = (ImageView) findViewById(R.id.registered_picture);

    }
    public void registered()
    {
//        user u = new user();
//        String sex = "";
//        if (man.isChecked())
//        {
//            sex = man.getText().toString();
//        }else if (woman.isChecked())
//        {
//            sex = woman.getText().toString();
//        }
//
//        u.setName(name.getText().toString());
//        u.setPassword(password.getText().toString());
//        u.setNumber(number.getText().toString());
//        u.setJob(job.getText().toString());
//        u.setSex(sex);
//        u.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if(e==null){
//                    Toast.makeText(Registered.this, "注册成功！ 欢迎使用！！", Toast.LENGTH_SHORT).show();
//                    Intent intent1 = new Intent(Registered.this,MainActivity_2.class);
//                    startActivity(intent1);
//                    Registered.this.finish();
//                    Log.i("info", "添加数据成功，返回objectId为："+s);
//                }else{
//                    Log.i("info", "创建数据失败：" + e.getMessage());
//                }
//            }
//        });



        String na = name.getText().toString();
        String pa = password.getText().toString();
        String num = number.getText().toString();
        String jo = job.getText().toString();
        String  se ="";
        if (man.isChecked())
        {
            se = man.getText().toString();
        }else if (woman.isChecked())
        {
            se = woman.getText().toString();
        }
        String post ="{\"state\":\"register\",\"UserName\":\""+na+"\",\"UserPassword\":\""+pa + "\",\"UserTel\":\""+num + "\"" +
                ",\"UserMajor\":\""+jo+"\",\"UserSex\":\""+se+"\"}";
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.URL)
                .post(requestBody1)
                .build();
        CallHttp(request2);
    }

    public void CallHttp(Request request)

    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("info", " GET请求成功！！！");
                String state="";
                Log.i("info", " res = " + res);

//                final Gson gson = new Gson();
//                final User user = gson.fromJson(res, User.class);
//                 MainActivity.UserID = user.getUserID();


                try {
                    JSONObject jsonObject = new JSONObject(res);
                    MainActivity.UserID=jsonObject.getJSONObject("userData").getString("UserID");
                    state = jsonObject.getJSONObject("userData").getString("state");

                    Log.i("json", "UserID = " + MainActivity.UserID);
                    Log.i("json", "state = " + state);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //在dtlp文件夹下载在创建一个由用户ID命名的文件夹 用来保存此用户的信息
                File file1 = null;
                if (!MainActivity.UserID.equals("")) {
                    File file = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID);
                    file.mkdir();
                    System.out.println("创建了文件夹");

                    //在由用户ID命名的文件夹下创建一个TXT格式的文件来保存数据
                    file1 = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt");
                    try {
                        file1.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println("创建了文件");
                }
                final File finalFile2 = new File(loginActivity.SDPATH +"dtlp/"+"TLTTLI.txt");
                final File finalFile1 = file1;
                final String finalState = state;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("info", "res  = " + res.toString());
//                        Gson gson = new Gson();
//                        User user = gson.fromJson(res, User.class);//解析服务器端传过来的值UserID和state
                        if (finalState.equals("true"))
                        {
                            //将服务器传回的用户信息写入到文件中
                            BufferedWriter out = null;
                            try {
                                out = new BufferedWriter(new FileWriter(finalFile1));
                                out.write(res); // \r\n即为换行
                                out.flush(); // 把缓存区内容压入文件
                                out.close(); // 最后记得关闭文件
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //将服务器传回的用户信息写入到TLTTLI文件中
                            BufferedWriter out1 = null;
                            try {
                                out1 = new BufferedWriter(new FileWriter(finalFile2));
                                out1.write(res); // \r\n即为换行
                                out1.flush(); // 把缓存区内容压入文件
                                out1.close(); // 最后记得关闭文件
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

//                            Log.i("info", "res  = " + res.toString());
                            Toast.makeText(Registered.this, "注册成功！ 欢迎使用！！", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(Registered.this,MainActivity_2.class);
                            startActivity(intent1);
                            Registered.this.finish();
                        }//得到的res为用户ID  保存到本地
                        else
                            Toast.makeText(Registered.this, "手机号已被注册了哦！！！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public boolean validatenumber(String number)
    {
        matcher = pattern.matcher(number);
        return matcher.matches();
    }
    public boolean validatepassword(String pass)
    {
        matcher1 = pattern1.matcher(pass);
        return matcher1.matches();
    }

    }


