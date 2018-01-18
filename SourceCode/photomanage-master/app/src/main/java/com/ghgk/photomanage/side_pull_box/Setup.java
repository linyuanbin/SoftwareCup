package com.ghgk.photomanage.side_pull_box;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ghgk.photomanage.Date.Qualify;
import com.ghgk.photomanage.MainActivity;
import com.ghgk.photomanage.R;
import com.ghgk.photomanage.start.loginActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阳瑞 on 2017/6/24.
 */
public class Setup extends Activity {
    private Button button1,button2,button3,button4,button5;
    private EditText editText1,editText2,editText3,editText4,editText5;
    OkHttpClient okHttpClient = new OkHttpClient();

    String hisnum="";
    String marknum="";
    String num = "";
    String states = "";



    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0:
                    String data = (String) msg.obj;
                    if (data.indexOf("<html>") != -1) {

                    } else {
                        Gson gson = new Gson();
                        Qualify user = gson.fromJson(data, Qualify.class);
                        if (user.getState().equals("true")) {
                            Toast.makeText(Setup.this, "修改成功！！！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Setup.this, "修改失败！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 1:
                    String data1 = (String) msg.obj;
                    if (data1.indexOf("<html>") != -1) {

                    } else {
                        Gson gson1 = new Gson();
                        Qualify user1 = gson1.fromJson(data1, Qualify.class);
                        if (user1.getState().equals("true")) {
                            Toast.makeText(Setup.this, "修改成功！！！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Setup.this, "修改失败！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 2:
                    String data2 = (String) msg.obj;
                    if (data2.indexOf("<html>") != -1) {

                    } else {
                        Gson gson2 = new Gson();
                        Qualify user2 = gson2.fromJson(data2, Qualify.class);
                        int hisnum = user2.getHisNum();
                        int marknum = user2.getMarkNum();
                        int num = user2.getNum();
                        String states = user2.getStates();
                        Log.i("number", "hisnum:= " + hisnum);
                        Log.i("number", "marknum:= " + marknum);
                        editText1.setText(hisnum + "");
                        editText2.setText(marknum + "");
                        editText3.setText(num+"");
                        editText4.setText(states);
                    }
                    break;
                case 3:
                    String data3 = (String) msg.obj;
                    if (data3.indexOf("<html>") != -1) {

                    } else {
                        Gson gson3 = new Gson();
                        Qualify user3 = gson3.fromJson(data3, Qualify.class);
                        if (user3.getState().equals("true")) {
                            Toast.makeText(Setup.this, "修改成功！！！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Setup.this, "修改失败！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case 4:
                    String data4 = (String) msg.obj;
                    if (data4.indexOf("<html>") != -1) {

                    } else {
                        Gson gson4 = new Gson();
                        Qualify user4 = gson4.fromJson(data4, Qualify.class);
                        if (user4.getState().equals("true")) {
                            Toast.makeText(Setup.this, "修改成功！！！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Setup.this, "修改失败！！！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_pull_box_set_up);
        initview();
        try {
            flash4();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void initview() {
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button4 = (Button) findViewById(R.id.button5);
        editText1 = (EditText) findViewById(R.id.edit1);
        editText2 = (EditText) findViewById(R.id.edit2);
        editText3 = (EditText) findViewById(R.id.edit3);
        editText4 = (EditText) findViewById(R.id.edit4);
        editText5= (EditText) findViewById(R.id.edit5);
    }

    public void doClick(View view) throws UnsupportedEncodingException {
        switch (view.getId())
        {
            case R.id.button1:
                dialog();
//                hisnum = editText1.getText().toString().trim();
//           if (isNumeric(hisnum))
//           {
//               flash2();
//           }else
//           {
//               Toast.makeText(Setup.this, "请输入数字哦！！！", Toast.LENGTH_SHORT).show();
//           }
                break;
            case R.id.button2:
                dialog1();
//                marknum = editText2.getText().toString().trim();
//                if (isNumeric(marknum))
//                {
//                    flash3();
//                }else
//                {
//                    Toast.makeText(Setup.this, "请输入数字哦！！！", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.button3:
                String res = "文件在"+ loginActivity.SDPATH +"dtlp/"+"photo"+".txt";
                editText5.setText(res);
//                Toast.makeText(Setup.this, res, Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                dialog2();
                break;
            case R.id.button5:

                dialog3();
                break;
        }
    }
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    public static boolean isNumeric_(String str)
    {
        Pattern pattern = Pattern.compile("^[A-Z]\\w{6}$");
        return pattern.matcher(str).matches();
    }
    public void flash2() throws UnsupportedEncodingException {

        String name1 = "setHisNum";
        Log.i("number", "hisnum:= "+hisnum);
        String key = "{\"state\":\""+name1+"\",\"hisNum\":\""+hisnum+"\"}";
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
                Log.i("info", " Push_GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " Push_GET请求成功！！！");

                Log.i("infoo", "Push_res = " + res);
                if (res.equals("") || res.equals(null)) {

                } else {
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 0;
                            msg.obj = res;
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                        }
                    };
                    mThread.start();
                }
            }
        });
    }
    public void flash3() throws UnsupportedEncodingException {

        String name1 = "setMarkNum";
        Log.i("number", "marknum:= "+marknum);
        String key = "{\"state\":\""+name1+"\",\"markNum\":\""+marknum+"\"}";
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
                Log.i("info", " Push_GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " Push_GET请求成功！！！");

                Log.i("infoo", "Push_res = " + res);
                if (res.equals("") || res.equals(null)) {

                } else {
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = res;
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                        }
                    };
                    mThread.start();
                }
            }
        });
    }
    public void flash4() throws UnsupportedEncodingException {

        String name1 = "requestQualify";
        String key = "{\"state\":\""+name1+"\"}";
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
                Log.i("info", " Push_GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " Push_GET请求成功！！！");

                Log.i("infoo", "Push_res = " + res);
                if (res.equals("") || res.equals(null)) {

                } else {
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 2;
                            msg.obj = res;
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                        }
                    };
                    mThread.start();
                }
            }
        });
    }
    public void flash5() throws UnsupportedEncodingException {

        String name1 = "setSimilarity";
        Log.i("number", "num:= "+num);
        String key = "{\"state\":\""+name1+"\",\"Num\":\""+num+"\"}";
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
                Log.i("info", " Push_GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " Push_GET请求成功！！！");

                Log.i("infoo", "Push_res = " + res);
                if (res.equals("") || res.equals(null)) {

                } else {
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 3;
                            msg.obj = res;
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                        }
                    };
                    mThread.start();
                }
            }
        });
    }
    public void flash6() throws UnsupportedEncodingException {

        String name1 = "setCheckId";
        Log.i("number", "num:= "+states);
        String key = "{\"state\":\""+name1+"\",\"states\":\""+states+"\"}";
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
                Log.i("info", " Push_GET请求失败！！！");
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("infoo", " Push_GET请求成功！！！");

                Log.i("infoo", "Push_res = " + res);
                if (res.equals("") || res.equals(null)) {

                } else {
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            msg.what = 4;
                            msg.obj = res;
                            handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
                        }
                    };
                    mThread.start();
                }
            }
        });
    }
    protected void dialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Setup.this);
        builder.setMessage("确认修改吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                hisnum = editText1.getText().toString().trim();
                if (isNumeric(hisnum)&&!hisnum.equals(""))
                {
                    try {
                        flash2();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(Setup.this, "请输入数字哦！！！", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
//                Setup.this.finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    protected void dialog1() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Setup.this);
        builder.setMessage("确认修改吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                marknum = editText2.getText().toString().trim();
                if (isNumeric(marknum)&&!marknum.equals(""))
                {
                    try {
                        flash3();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(Setup.this, "请输入数字哦！！！", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
//                Setup.this.finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    protected void dialog2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Setup.this);
        builder.setMessage("确认修改吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                num = editText3.getText().toString().trim();
                if (isNumeric(num)&&!num.equals(""))
                {
                    try {
                        flash5();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(Setup.this, "请输入数字哦！！！", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
//                Setup.this.finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    protected void dialog3() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Setup.this);
        builder.setMessage("确认修改吗？");

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                states = editText4.getText().toString().trim();
                if (!states.equals("")&&isNumeric_(states))
                {
                    try {
                        flash6();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    Toast.makeText(Setup.this, "请输入由大写字母开头，包含数字或者下划线的7位注册码", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
//                Setup.this.finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
