package com.ghgk.photomanage.tap_fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ghgk.photomanage.Date.User;
import com.ghgk.photomanage.MainActivity;
import com.ghgk.photomanage.R;
import com.google.gson.Gson;
import com.spark.submitbutton.SubmitButton;
import com.xdja.progressbarlibrary.NiceProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
 * Created by cool on 2017-03-28.
 */
public class fragment_push extends Fragment {
    private View view;
    private Button search;
    private Button updatainfo;
    private SubmitButton submitButton;//提交志愿者信息按钮
    private LinearLayout Userinfo;
    private EditText phoneoremail;
    private TextView XUsername;
    private TextView Xuserpassword;//签名
    private TextView XnickName;
    private TextView Xhobby;
    private TextView Xintegral;//积分
    public static final String POSITIVE_INTEGER_REGEXP = "^[0-9]*[1-9][0-9]*$";//正者int
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    public static final String REGEX_EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
   // private String url1 = "http://192.168.0.18:8080/TotemDown/managerServer?username=linyuanbin&password=123456";
 //  private String url1 = "http://114.115.210.8:8090/TotemDown/managerServer?username=linyuanbin&password=123456";


    OkHttpClient okHttpClient = new OkHttpClient();
    private Pattern pattern = Pattern.compile(REGEX_MOBILE);
    private Matcher matcher_phone;
    private Pattern pattern1 = Pattern.compile(REGEX_EMAIL);
    private Matcher matcher1_email;
    private Pattern pattern11 = Pattern.compile(POSITIVE_INTEGER_REGEXP);
    private Matcher matcher1_int;
    private String res="";
    private String res11;
    private TextView Xid;
    private String ID;
    private User user;
    private LinearLayout Lid;
    private LinearLayout Lusername;
    private LinearLayout Lpassword;
    private LinearLayout Lnickname;

    private LinearLayout Lhobby;
    private LinearLayout Lintegral;
    private  LinearLayout mission;
    private  EditText personal_information_name_text;
    private TextView userinfoone;
    private String ss;
    private NiceProgressBar progressbar1_work;
    private int userpushnumber;
    private int userpushcomplete;
    private float completetssk=0;
    private TextView userpushnumber_tv;
    private  TextView userpushcomplete_tv;




    private String res1 = "";
    Handler handle = new Handler() {

        public void handleMessage(Message msg) {
            System.out.println("111");
            String data = (String) msg.obj;


            if (data.indexOf("<html>") != -1) {//html 判断

            } else {
                Log.i("userstate-------", data);
                Gson gson = new Gson();
                user = gson.fromJson(data, User.class);
                Log.i("userstate-------", user.getState());
                res1 = user.getState();
                if (res1.equals("true")) {
                    Log.i("sdd", "正确");
                    Userinfo.setVisibility(Userinfo.VISIBLE);//解除隐藏跳转
                    mission.setVisibility(mission.VISIBLE);
                    User u1 = gson.fromJson(data, User.class);
                    XUsername.setText(u1.getUserName());
                    Xuserpassword.setText(u1.getUserPassword());
                    XnickName.setText(u1.getUserNickName() + "");
                    userpushnumber=u1.getTotal();
                    userpushcomplete=u1.getAccomplish();
                    completetssk= (userpushcomplete*100/(float)userpushnumber);
                    userpushnumber_tv=(TextView)getActivity().findViewById(R.id.userpushnumber);
                    userpushcomplete_tv=(TextView)getActivity().findViewById(R.id.userpushcomplete);
                    int userpush=userpushnumber-userpushcomplete;
                    userpushnumber_tv.setText("未完成量："+userpush);
                    userpushcomplete_tv.setText("已打标签量:"+userpushcomplete);

                    progressbar1_work.setTextMax(completetssk).showWithPercent(true).show();
                    Log.i("userpushnumber00000",userpushnumber+"");
                    Log.i("userpushnumber00000",completetssk+"");
                    Xhobby.setText(u1.getUserEmail() + "");
//                  Xintegral.setText(u1.getUserIntegral() + "");
                    ID = u1.getUserID();
                } else {
                    Toast.makeText(getActivity(), "对不起没有此用户!!", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        view = inflater.inflate(R.layout.fragment_push, group, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        search = (Button) getActivity().findViewById(R.id.search);
        Userinfo = (LinearLayout) getActivity().findViewById(R.id.userinfo);
        mission=(LinearLayout)getActivity().findViewById(R.id.mission);
       // updatainfo = (Button) getActivity().findViewById(R.id.updatainfo);
        submitButton= (SubmitButton) getActivity().findViewById(R.id.submit_bt);
        phoneoremail = (EditText) getActivity().findViewById(R.id.phoneoremail);
        XUsername = (TextView) getActivity().findViewById(R.id.Xusername);
        Lusername=(LinearLayout)getActivity().findViewById(R.id.Lusername);
        Lpassword=(LinearLayout)getActivity().findViewById(R.id.Lpassword);
        Lnickname=(LinearLayout)getActivity().findViewById(R.id.Lnickname);
        Lhobby=(LinearLayout)getActivity().findViewById(R.id.Lhobby);
//        Lintegral=(LinearLayout)getActivity().findViewById(R.id.Lintegral);
        progressbar1_work=(NiceProgressBar)getActivity().findViewById(R.id.progressbar1_work);

       // progressbar1_work.setTextMax(completetssk).setshowWithPercent(true).show();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

//        updatainfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Userinfo.setVisibility(Userinfo.GONE);
//              //  mission.setVisibility(mission.GONE);
//                updatainfo();
//                Toast.makeText(getActivity(),"信息已保存",Toast.LENGTH_SHORT).show();
//            }
//        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Userinfo.setVisibility(Userinfo.GONE);
               // mission.setVisibility(mission.GONE);
                updatainfo();
                Toast.makeText(getActivity(),"信息已保存",Toast.LENGTH_SHORT).show();
            }
        });

        Lusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showinfoname();
            }
        });
        Lpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinfopassword();
            }
        });

        Lnickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinfonickname();
            }
        });
        Lhobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showinfohobby();
            }
        });
//        Lintegral.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showinfointegral();
//            }
//        });
    }

    //查找信息
    private void Search() {
        XUsername = (TextView) getActivity().findViewById(R.id.Xusername);
        Xuserpassword = (TextView) getActivity().findViewById(R.id.Xuserpassword);
        XnickName = (TextView) getActivity().findViewById(R.id.XnickName);
        Xhobby = (TextView) getActivity().findViewById(R.id.Xhobby);
//        Xintegral = (TextView) getActivity().findViewById(R.id.integral);
        phoneoremail = (EditText) getActivity().findViewById(R.id.phoneoremail);
        String phone_email = phoneoremail.getText().toString();
        if (validatenumber(phone_email) || validateemail(phone_email)) {
            String post = "{\"state\":\"search\",\"UserTel\":\"" + phone_email + "\"}";
            RequestBody requestBody1 = RequestBody
                    .create(MediaType.parse("text/x-markdown; charset=utf-8"), post);
            Request.Builder builder3 = new Request.Builder();
            Request request2 = builder3
                    .url(MainActivity.ur)
                    .post(requestBody1)
                    .build();
            CallHttp(request2);
            Log.i("search", "post = " + post);
        } else {
            Toast.makeText(getActivity(), "请输入正确的电话号码或者是邮箱号", Toast.LENGTH_SHORT).show();
        }

    }

    //修改信息
    private void updatainfo() {
//        phoneoremail = (EditText) getActivity().findViewById(R.id.phoneoremail);
        XUsername = (TextView) getActivity().findViewById(R.id.Xusername);
        Xuserpassword = (TextView) getActivity().findViewById(R.id.Xuserpassword);
        XnickName = (TextView) getActivity().findViewById(R.id.XnickName);
        Xhobby = (TextView) getActivity().findViewById(R.id.Xhobby);
//        Xintegral = (TextView) getActivity().findViewById(R.id.integral);
        Xid = (TextView) getActivity().findViewById(R.id.id);
        String post = "";
        //String phone_email = phoneoremail.getText().toString();
//        Gson gson = new Gson();
//        Log.i("xiugai", "res = : " + res);
//        user = gson.fromJson(res, User.class);
//        user.setState("updateUser");
//        user.setUserID(ID.trim());
//        user.setUserName(XUsername.getText().toString().trim());
//        user.setUserEmail(Xhobby.getText().toString().trim());
//        user.setUserNickName(XnickName.getText().toString().trim());
//        user.setUserPassword(Xuserpassword.getText().toString().trim());
//////////////////////////////////////////
        try {
            JSONObject dataJson = new JSONObject(res);// 创建一个包含原始json串的json对象
//                JSONObject jsonObject1 = dataJson.getJSONObject("userData");
            dataJson.put("state","updateUser");
            dataJson.put("UserName",XUsername.getText().toString().trim() );
            dataJson.put("UserEmail",Xhobby.getText().toString().trim() );
            dataJson.put("UserNickName",XnickName.getText().toString().trim());//地址
            dataJson.put("UserPassword",Xuserpassword.getText().toString().trim());
            post=dataJson.toString();
//                ws = dataJson.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //////////////////////////////////////



//        Integer integral2=Integer.valueOf(Xintegral.getText().toString().trim());
//            user.setUserIntegral(integral2);
//        String post = gson.toJson(user);
//        Log.i("info", "post = : " + post);
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"), post);
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.ur)
                .post(requestBody1)
                .build();
        CallHttp11(request2);
    }

    public void CallHttp(Request request)

    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res = response.body().string();
                Log.i("info", " GET请求成功！！！");
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.obj = res;
                        handle.sendMessage(msg);
                    }
                };
                mThread.start();
            }

        });
    }

    public void CallHttp11(Request request)

    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res11 = response.body().string();
                Log.i("info", " GET请求成功！！！");

            }
        });
    }

    public boolean validatenumber(String number) {
        matcher_phone = pattern.matcher(number);
        return matcher_phone.matches();
    }

    public boolean validateemail(String email) {
        matcher1_email = pattern1.matcher(email);
        return matcher1_email.matches();
    }
    public boolean validateint(String i) {
        matcher1_int = pattern11.matcher(i);
        return matcher1_int.matches();
    }

    private void showinfoname() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.updateinfomation_mao,
                (ViewGroup) getActivity().findViewById(R.id.personal_information1));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        final  TextView Xusername1=(TextView)getActivity().findViewById(R.id.Xusername);
        TextView whatinfo1=(TextView)layout.findViewById(R.id.whatinfo1);
        TextView XXusername=(TextView)getActivity().findViewById(R.id.XXusername);
       whatinfo1.setText(XXusername.getText().toString());
         //personal_information1_text=(EditText)getActivity().findViewById(R.id.personal_information1_text);
      builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

               EditText personal_information1_text=(EditText)layout.findViewById(R.id.personal_information1_text);
                Xusername1.setText(personal_information1_text.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    private void showinfopassword() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.updateinfomation_mao,
                (ViewGroup) getActivity().findViewById(R.id.personal_information1));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        final  TextView Xuserpassword1=(TextView)getActivity().findViewById(R.id.Xuserpassword);
        TextView whatinfo1=(TextView)layout.findViewById(R.id.whatinfo1);
        TextView XXuserpassword1=(TextView)getActivity().findViewById(R.id.XXuserpassword);
        whatinfo1.setText(XXuserpassword1.getText().toString());
        //personal_information1_text=(EditText)getActivity().findViewById(R.id.personal_information1_text);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText personal_information1_text=(EditText)layout.findViewById(R.id.personal_information1_text);
                Xuserpassword1.setText(personal_information1_text.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    private void showinfonickname() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.updateinfomation_mao,
                (ViewGroup) getActivity().findViewById(R.id.personal_information1));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        final  TextView Xnickname=(TextView)getActivity().findViewById(R.id.XnickName);
        TextView whatinfo1=(TextView)layout.findViewById(R.id.whatinfo1);
        TextView XXnickname=(TextView)getActivity().findViewById(R.id.XXnickname);
        whatinfo1.setText(XXnickname.getText().toString());
        //personal_information1_text=(EditText)getActivity().findViewById(R.id.personal_information1_text);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText personal_information1_text=(EditText)layout.findViewById(R.id.personal_information1_text);
                Xnickname.setText(personal_information1_text.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }

    private void showinfohobby() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.updateinfomation_mao,
                (ViewGroup) getActivity().findViewById(R.id.personal_information1));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
        final  TextView Xhobby=(TextView)getActivity().findViewById(R.id.Xhobby);
        TextView whatinfo1=(TextView)layout.findViewById(R.id.whatinfo1);
        TextView XXhobby=(TextView)getActivity().findViewById(R.id.XXhobby);
        whatinfo1.setText(XXhobby.getText().toString());
        //personal_information1_text=(EditText)getActivity().findViewById(R.id.personal_information1_text);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                EditText personal_information1_text=(EditText)layout.findViewById(R.id.personal_information1_text);
                Xhobby.setText(personal_information1_text.getText().toString());
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
    private void showinfointegral() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.updateinfomation_mao,
                (ViewGroup) getActivity().findViewById(R.id.personal_information1));
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(layout);
//        final  TextView integral=(TextView)getActivity().findViewById(R.id.integral);
        TextView whatinfo1=(TextView)layout.findViewById(R.id.whatinfo1);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText personal_information1_text=(EditText)layout.findViewById(R.id.personal_information1_text);

                if (validateint(personal_information1_text.getText().toString())){
//                    integral.setText(personal_information1_text.getText().toString());
            }
                else {
                    Toast.makeText(getActivity(),"请输入正整数",Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.show();
    }
}

