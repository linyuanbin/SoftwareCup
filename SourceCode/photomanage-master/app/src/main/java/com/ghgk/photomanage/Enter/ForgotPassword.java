package com.ghgk.photomanage.Enter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ghgk.photomanage.Date.User;
import com.ghgk.photomanage.MainActivity;
import com.ghgk.photomanage.R;
import com.ghgk.photomanage.User.user;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



/**
 * Created by 毛钰铭 on 2017/3/23.
 */
public class ForgotPassword extends Activity {
    private EditText number,change_password,sure_change_password;
    private Button sure_modify;
    public static   String ur = "http://114.115.210.8:8090/TotemDown/managerServer?username=linyuanbin&password=123456";
    OkHttpClient okHttpClient = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        initview();
    }

    public void doClick(View view) throws  UnsupportedEncodingException
    {
        switch (view.getId())
        {
            case R.id.Sure_Modify:
                if (change_password.getText().toString().equals(sure_change_password.getText().toString()))
                {
                   flash2();
                }else
                {
                    Toast.makeText(ForgotPassword.this, "亲 两次密码不一样哦！！！", Toast.LENGTH_SHORT).show();
                }
               break;
        }
    }

    private void initview() {
        number = (EditText) findViewById(R.id.Number);
        change_password = (EditText) findViewById(R.id.Chang_Password);
        sure_change_password = (EditText) findViewById(R.id.Sure_Chang_Password);
        sure_modify = (Button) findViewById(R.id.Sure_Modify);
    }





    public void mofify()
    {
        String new_password = change_password.getText().toString();
        user user =new user();
        user.setValue("number",new_password);
//        user.update()
    }

public void flash2() throws UnsupportedEncodingException {
    String UserTel = number.getText().toString().trim();
    String UserPassword = sure_change_password.getText().toString().trim();
    String key = "{\"state\":\"changePassword\",\"mTel\":\"" + UserTel + "\",\"mPassword\":\"" + UserPassword + "\"}";
    URLDecoder.decode(key, "utf-8");
    RequestBody requestBody1 = RequestBody
            .create(MediaType.parse("text/plain; charset=utf-8"), key);
    Request.Builder builder3 = new Request.Builder();
    Request request2 = builder3
            .url(MainActivity.ur)
            .post(requestBody1)
            .build();
    okhttp3.Call call1 = okHttpClient.newCall(request2);
    call1.enqueue(new Callback() {
        @Override
        public void onFailure(okhttp3.Call call, IOException e) {
            Log.i("info", " GET请求失败！！！");
        }

        @Override
        public void onResponse(okhttp3.Call call, Response response) throws IOException {

            final String res = response.body().string();
            Log.i("infoo", " GET请求成功！！！");

            Log.i("infoo", "res = " + res);
            Gson gson = new Gson();
            final User user = gson.fromJson(res, User.class);

            if (user.getState().equals("true")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ForgotPassword.this, "修改成功！！！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

            }

        }
    });


}

    }