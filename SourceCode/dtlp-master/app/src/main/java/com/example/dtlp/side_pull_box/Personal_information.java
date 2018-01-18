package com.example.dtlp.side_pull_box;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.MainActivity;
import com.example.dtlp.R;
import com.example.dtlp.side_pull_box.view1.SelectAddressDialog;
import com.example.dtlp.side_pull_box.view1.myinterface.SelectAddressInterface;
import com.example.dtlp.start.loginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 阳瑞 on 2017/3/29.
 */
public class Personal_information extends Activity implements SelectAddressInterface{

    private LinearLayout nickname,sex,birthday,major,where,email,number;
    private TextView sex_,nickname_,birthday_,major_,where_,email_,number_,name;
    private EditText personal_information_name_text;

    public static final String REGEX_EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
    private Pattern pattern = Pattern.compile(REGEX_EMAIL);
    private Matcher matcher;

    private SelectAddressDialog dialog;


    OkHttpClient okHttpClient = new OkHttpClient();

    String bir = "";
    String whe ="";
    String se = "";
    String nick = "";
    String maj = "";
    String ema = "";
    boolean isCheckOn = false;
    File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_pull_box_personal_information);
        initview();
        display();
    }

    private void display() {
        //从本地用户文件中将数据读取出来
        String userdata = "";//读出来的Json数据
        String nickname = "";
        String major = "";
        String email = "";
        String birthday = "";
        String sex = "";
        String number="";
        String where="";
        File filename = new File(loginActivity.SDPATH + "dtlp/" + MainActivity.UserID + "/" + MainActivity.UserID + ".txt"); // 要读取以上路径的input。txt文件
        Log.i("userdata", "userdata: = " + filename);
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
        Log.i("userdata", "userdata: = " + userdata);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(userdata);
            nickname=jsonObject.getJSONObject("userData").getString("UserName");
//            major=jsonObject.getJSONObject("userData").getString("UserMajor");
//            sex=jsonObject.getJSONObject("userData").getString("UserSex");
//            number=jsonObject.getJSONObject("userData").getString("UserTel");
//            birthday=jsonObject.getJSONObject("userData").getString("UserBirthday");
//            email=jsonObject.getJSONObject("userData").getString("UserEmail");
//            where=jsonObject.getJSONObject("userData").getString("UserNickName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(userdata);
            major=jsonObject.getJSONObject("userData").getString("UserMajor");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(userdata);
            sex=jsonObject.getJSONObject("userData").getString("UserSex");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(userdata);
            number=jsonObject.getJSONObject("userData").getString("UserTel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(userdata);
            birthday=jsonObject.getJSONObject("userData").getString("UserBirthday");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(userdata);
            email=jsonObject.getJSONObject("userData").getString("UserEmail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObject = new JSONObject(userdata);
            where=jsonObject.getJSONObject("userData").getString("UserNickName");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        nickname_.setText(nickname);
        major_.setText(major);
        if (email.equals("")||email.equals(null)) {
            email_.setText("未填写");
        }else
        {
            email_.setText(email);
        }
        if (birthday.equals("")||birthday.equals(null)) {
            birthday_.setText("未填写");
        }else
        {
            birthday_.setText(birthday);
        }
        if (where.equals("")||where.equals(null)) {
            where_.setText("未填写");
        }else
        {
            where_.setText(where);
        }
        sex_.setText(sex);
        number_.setText(number);

    }
    private void  TP()
    {


//        String post1 ="{\"state\":\"update\",\"UserID\":\"Thu Apr 27 18:42:02 CST 20170uv4k\"," +
//                "\"UserNickName\":\""+nick+"\",\"UserSex\":\""+se + "\"," +
//                "\"UserBirthday\":\""+bir + "\"" +
//                ",\"UserMajor\":\""+maj+"\",\"UserEmail\":\""+ema+"\"}";

        //将修改好的用户信息发送给服务器端
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    filename));// 读取原始json文件
            StringBuilder user = new StringBuilder();
            String s = "";
            while ((s = br.readLine()) != null) {
                user.append(s);
            }
            try {
                JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                jsonObject1.put("state","update");

                RequestBody requestBody2 = RequestBody
                        .create(MediaType.parse("text/x-markdown; charset=utf-8"),jsonObject1.toString());
                Request.Builder builder2 = new Request.Builder();
                Request request2 = builder2
                        .url(MainActivity.URL)
                        .post(requestBody2)
                        .build();
                CallHttp(request2);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            br.close();


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

//        RequestBody requestBody2 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post1);
//        Request.Builder builder2 = new Request.Builder();
//        Request request2 = builder2
//                .url(MainActivity.URL)
//                .post(requestBody2)
//                .build();
//        CallHttp(request2);



    }

    private void initview() {
        nickname = (LinearLayout) findViewById(R.id.nickName);
        sex = (LinearLayout) findViewById(R.id.sex);
        birthday = (LinearLayout) findViewById(R.id.birthday);
        major = (LinearLayout) findViewById(R.id.major);
        where = (LinearLayout) findViewById(R.id.where);
        email = (LinearLayout) findViewById(R.id.email);
        number = (LinearLayout) findViewById(R.id.number);

        where_ = (TextView) findViewById(R.id.where_);
        sex_ = (TextView) findViewById(R.id.sex_);
        nickname_ = (TextView) findViewById(R.id.nickName_);
        birthday_ = (TextView) findViewById(R.id.birthday_);
        major_ = (TextView) findViewById(R.id.major_);
        email_ = (TextView) findViewById(R.id.email_);
        number_ = (TextView) findViewById(R.id.number_);
        name = (TextView) findViewById(R.id.name);
    }

    public void doClick(View view) {

        switch (view.getId())
        {
            case R.id.nickName:
                personal_information_name_Dialog();
                break;
            case R.id.sex:
                personal_information_sex_Dialog();
                break;
            case R.id.birthday:
                final Date date = new Date();
                new DatePickerDialog(Personal_information.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        isCheckOn = true;
                        date.setYear(year-1900);
                        date.setMonth(monthOfYear);
                        date.setDate(dayOfMonth);
                        bir = String.format("%tF",date);
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(
                                    filename));// 读取原始json文件
                            StringBuilder user = new StringBuilder();
                            String s = "";
                            String ws = "";
                            while ((s = br.readLine()) != null) {
                                user.append(s);
                            }
                            try {
                                JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                                jsonObject1.put("UserBirthday",bir );
                                ws = dataJson.toString();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            BufferedWriter bw = new BufferedWriter(new FileWriter(
                                    filename));// 输出新的json文件
                            bw.write(ws);
                            bw.flush();
                            br.close();
                            bw.close();

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        birthday_.setText(String.format("%d-%d-%d",year,monthOfYear+1,dayOfMonth));
                    }
                },2000,1,2).show();
                break;
            case R.id.major:
                personal_information_major_Dialog();
                break;
            case R.id.where:
                if (dialog == null) {
                    dialog = new SelectAddressDialog(Personal_information.this,
                            Personal_information.this,SelectAddressDialog.STYLE_TWO,null);
                }
                dialog.showDialog();
                break;
            case R.id.email:
                    personal_information_email_Dialog();
                break;
        }
    }


    @Override
    public void setAreaString(String area) {
        isCheckOn = true;
        where_.setText(area);
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    filename));// 读取原始json文件
            StringBuilder user = new StringBuilder();
            String s = "";
            String ws = "";
            while ((s = br.readLine()) != null) {
                user.append(s);
            }
            try {
                JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                jsonObject1.put("UserNickName",area );
                ws = dataJson.toString();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    filename));// 输出新的json文件
            bw.write(ws);
            bw.flush();
            br.close();
            bw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        whe = area;
//        String post ="{\"where\":\""+area+"\"}";
//        RequestBody requestBody1 = RequestBody
//                .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//        Request.Builder builder3 = new Request.Builder();
//        Request request2 = builder3
//                .url(url1+"postString")
//                .post(requestBody1)
//                .build();
//        CallHttp(request2);
//        SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor  = msharedPreferences.edit();
//        editor.putString("where",area);
//        editor.commit();
    }
    /**
     * 显示单选对话框
     *
     * @param
     */
    public void personal_information_sex_Dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final String[] items = new String[]{"男", "女",};
         final String[] sex1 = new String[1];
        builder.setSingleChoiceItems(items, 2, new DialogInterface.OnClickListener() {/*设置单选条件的点击事件*/
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    sex1[0] = items[which];
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if (!sex1[0].toString().equals("")) {

                    se = sex1[0];
//                    String post ="{\"state\":\"update\",\"UserID\":\"Thu Apr 27 18:42:02 CST 20170uv4k\",\"sex\":\""+sex1[0]+"\"}";
//                    RequestBody requestBody1 = RequestBody
//                            .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                    Request.Builder builder3 = new Request.Builder();
//                    Request request2 = builder3
//                            .url(url1)
//                            .post(requestBody1)
//                            .build();
//                    CallHttp(request2);

//                    SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor  = msharedPreferences.edit();
//                    editor.putString("sex",sex1[0]);
//                    editor.commit();
                    //当用户修改了自己的信息时 同时也对保存在手机上的文件进行修改
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(
                                filename));// 读取原始json文件
                        StringBuilder user = new StringBuilder();
                        String s = "";
                        String ws = "";
                        while ((s = br.readLine()) != null) {
                            user.append(s);
                        }
                        try {
                            JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                            JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                            jsonObject1.put("UserSex",sex1[0] );
                            ws = dataJson.toString();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter(
                                filename));// 输出新的json文件
                        bw.write(ws);
                        bw.flush();
                        br.close();
                        bw.close();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }



                    sex_.setText(sex1[0]);
                    isCheckOn = true;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void personal_information_name_Dialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.side_pull_box_personal_information_name,
                (ViewGroup) findViewById(R.id.personal_information_name));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText personal_information_name_text = (EditText) layout.findViewById(R.id.personal_information_name_text);
                if (!personal_information_name_text.getText().toString().equals(""))
                {
                    String name = personal_information_name_text.getText().toString();
                    if (!validateName(name))
                    {
                        Toast.makeText(Personal_information.this, "亲 名字太长了哦！！！", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        nick = personal_information_name_text.getText().toString();
//                        String post ="{\"nickname\":\""+nickname+"\"}";
//                        RequestBody requestBody1 = RequestBody
//                                     .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                        Request.Builder builder3 = new Request.Builder();
//                        Request request2 = builder3
//                              .url(url1+"postString")
//                              .post(requestBody1)
//                              .build();
//                        CallHttp(request2);

//                        SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = msharedPreferences.edit();
//                        editor.putString("nickname", personal_information_name_text.getText().toString());
//                        editor.commit();

                        //当用户修改了自己的信息时 同时也对保存在手机上的文件进行修改
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(
                                    filename));// 读取原始json文件
                            StringBuilder user = new StringBuilder();
                            String s = "";
                            String ws = "";
                            while ((s = br.readLine()) != null) {
                                user.append(s);
                            }
                            try {
                                Log.i("kkkkkkkkkk", " user.toString() = " + user.toString());
                                JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                                Log.i("kkkkkkkkkk", " dataJson = " + dataJson.toString());
                                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                                jsonObject1.put("UserName",personal_information_name_text.getText().toString() );
                                ws = dataJson.toString();
                                Log.i("kkkkkkkkkk", " ws = " + ws);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            BufferedWriter bw = new BufferedWriter(new FileWriter(
                                    filename));// 输出新的json文件
                            bw.write(ws);
                            bw.flush();
                            br.close();
                            bw.close();

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        nickname_.setText(personal_information_name_text.getText().toString());
                        isCheckOn = true;
                    }
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void personal_information_major_Dialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.side_pull_box_personal_information_major,
                (ViewGroup) findViewById(R.id.personal_information_major));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText personal_information_major_text = (EditText) layout.findViewById(R.id.personal_information_major_text);
                if (!personal_information_major_text.getText().toString().equals("")) {

                    maj = personal_information_major_text.getText().toString();
//                    String post ="{\"major\":\""+maj+"\"}";
//                    RequestBody requestBody1 = RequestBody
//                            .create(MediaType.parse("text/x-markdown; charset=utf-8"),post);
//                    Request.Builder builder3 = new Request.Builder();
//                    Request request2 = builder3
//                            .url(url1+"postString")
//                            .post(requestBody1)
//                            .build();
//                    CallHttp(request2);

//                    SharedPreferences msharedPreferences = getSharedPreferences("Personal_Information", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor  = msharedPreferences.edit();
//                    editor.putString("major",personal_information_major_text.getText().toString());
//                    editor.commit();

                    //当用户修改了自己的信息时 同时也对保存在手机上的文件进行修改
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(
                                filename));// 读取原始json文件
                        StringBuilder user = new StringBuilder();
                        String s = "";
                        String ws = "";
                        while ((s = br.readLine()) != null) {
                            user.append(s);
                        }
                        try {
                            JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                            JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                            jsonObject1.put("UserMajor",personal_information_major_text.getText().toString() );
                            ws = dataJson.toString();
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        BufferedWriter bw = new BufferedWriter(new FileWriter(
                                filename));// 输出新的json文件
                        bw.write(ws);
                        bw.flush();
                        br.close();
                        bw.close();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    major_.setText(personal_information_major_text.getText().toString());
                    isCheckOn = true;
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    public void personal_information_email_Dialog()
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.side_pull_box_personal_information_email,
                (ViewGroup) findViewById(R.id.personal_information_email));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText personal_information_email_text = (EditText) layout.findViewById(R.id.personal_information_email_text);
                if (!personal_information_email_text.getText().toString().equals("")) {
                    String email =personal_information_email_text.getText().toString();
                    if (!validateEmail(email))
                    {
                        Toast.makeText(Personal_information.this, "亲 请输入正确的邮箱格式哦！！！", Toast.LENGTH_SHORT).show();
                    }else {

                         ema = personal_information_email_text.getText().toString();
                        //当用户修改了自己的信息时 同时也对保存在手机上的文件进行修改
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(
                                    filename));// 读取原始json文件
                            StringBuilder user = new StringBuilder();
                            String s = "";
                            String ws = "";
                            while ((s = br.readLine()) != null) {
                                user.append(s);
                            }
                            try {
                                JSONObject dataJson = new JSONObject(user.toString());// 创建一个包含原始json串的json对象
                                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
                                jsonObject1.put("UserEmail",personal_information_email_text.getText().toString() );
                                ws = dataJson.toString();
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            BufferedWriter bw = new BufferedWriter(new FileWriter(
                                    filename));// 输出新的json文件
                            bw.write(ws);
                            bw.flush();
                            br.close();
                            bw.close();

                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        email_.setText(personal_information_email_text.getText().toString());
                        isCheckOn = true;
                    }
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void CallHttp(Request request)

    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = "  + e .toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("info", " GET请求成功！！！");
                Log.i("info", " res = "+ res);

//                runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
////                        Conten.setText(res);
//                }
//            });
        }
        });
    }
    public boolean validateEmail(String email)
    {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean validateName(String name)
    {
        return name.length()<7;
    }


    @Override
    protected void onDestroy() {
        if (isCheckOn)
        {
            TP();
            Log.i("info", "post = ");
        }
        super.onDestroy();
    }
}
