package com.ghgk.photomanage.tap_fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.ghgk.photomanage.MainActivity;
import com.ghgk.photomanage.R;
import com.ghgk.photomanage.model.UploadGoodsBean;
import com.ghgk.photomanage.util.Config;
import com.ghgk.photomanage.util.DbTOPxUtil;
import com.ghgk.photomanage.util.UploadUtil;
import com.ghgk.photomanage.view.MyGridView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.spark.submitbutton.SubmitButton;
import com.zzti.fengyongge.imagepicker.PhotoPreviewActivity;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;
import com.zzti.fengyongge.imagepicker.model.PhotoModel;
import com.zzti.fengyongge.imagepicker.util.CommonUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class fragment_history extends Fragment {
    private static LayoutInflater inflater1;
    private ImageView add_IB;
    private MyGridView my_imgs_GV;
    private int screen_widthOffset;
    private ArrayList<UploadGoodsBean> img_uri = new ArrayList<UploadGoodsBean>();
    private List<PhotoModel>  single_photos = new ArrayList<PhotoModel>();
    GridImgAdapter gridImgsAdapter;
    private View view;
    private Button commit;
    private boolean iscorp;//判断是否重复
    private SubmitButton upload;
    private Map<String,String> photopaths=new HashMap<String,String>();
    private  String[] photopaths1=new String[20];
    private int photonumbers=0;
    private  int p=0;
    private  int photostart=0;
    private  String stringbutter="";
     //private String url2 ="http://192.168.0.104:8080/TotemDown/UploadShipServlet?username=linyuanbin&password=123456";
    private String url2 ="http://114.115.210.8:8090/TotemDown/UploadShipServlet?username=linyuanbin&password=123456";
    private  String photos;
    private  int number;//是否第一次添加图片
    private byte[] ss;
    String result="";
    OkHttpClient okHttpClient = new OkHttpClient();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
          view=inflater.inflate(R.layout.fragment_history, group, false);
         DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build();
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).defaultDisplayImageOptions(
                defaultOptions).build();
        ImageLoader.getInstance().init(config);
        Config.ScreenMap = Config.getScreenSize(getActivity(), getActivity());
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        number=0;
        screen_widthOffset = (display.getWidth() - (3* DbTOPxUtil.dip2px(getActivity(), 2)))/4;
        inflater1 = LayoutInflater.from(getActivity());
        my_imgs_GV= (MyGridView) view.findViewById(R.id.my_goods_GV);
        gridImgsAdapter = new GridImgAdapter();
        my_imgs_GV.setAdapter(gridImgsAdapter);
        img_uri.add(null);
        gridImgsAdapter.notifyDataSetChanged();
        return view;

    }


    class GridImgAdapter extends BaseAdapter implements ListAdapter {
        @Override
        public int getCount() {
            return img_uri.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = inflater1.inflate(R.layout.photo_activity_addstory_img_item, null);
            add_IB = (ImageView) convertView.findViewById(R.id.add_IB);
            //button
           //commit=(Button)getActivity().findViewById(R.id.commit);
            upload=(SubmitButton)getActivity().findViewById(R.id.upload_bt);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i=0; i<photonumbers; i++){
                        //利用线程 上传图片
                        new MyAsyncTask().execute(photopaths1[i],url2);//不用全局变量
                        Toast.makeText(getActivity(),"图片上传成功",Toast.LENGTH_LONG).show();
                    }
                    img_uri.clear();
                    img_uri.add(null);
                    single_photos.clear();
                    gridImgsAdapter.notifyDataSetChanged();
                    photonumbers=0;
                }
            });

            ImageView delete_IV = (ImageView) convertView.findViewById(R.id.delete_IV);
            AbsListView.LayoutParams param = new AbsListView.LayoutParams(screen_widthOffset, screen_widthOffset);
            convertView.setLayoutParams(param);
            if (img_uri.get(position) == null) {
                delete_IV.setVisibility(View.GONE);
                ImageLoader.getInstance().displayImage("drawable://" + R.drawable.iv_add_the_pic, add_IB);//图片异步加载
                add_IB.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        //问题之处
                        Log.i("--------","----onclick");
                        Intent intent = new Intent(getActivity(), PhotoSelectorActivity.class);
                        //界面跳转的动画
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("limit", 21 - (img_uri.size() - 0));//上传图片设定最大数量
                        startActivityForResult(intent, 0);
                    }
                });

            }

            //利用remove(） 进行移除相同照片
            else {
                ImageLoader.getInstance().displayImage("file://" + img_uri.get(position).getUrl(), add_IB);//图片异步加载
                delete_IV.setOnClickListener(new View.OnClickListener() {
                    private boolean is_addNull;
                    @Override
                    public void onClick(View arg0) {
                        is_addNull = true;
                        String img_url = img_uri.remove(position).getUrl();
                        single_photos.remove(position);
                        for (int i = 0; i < img_uri.size(); i++) {
                            if (img_uri.get(i) == null) {
                                 is_addNull = false;
                                continue;
                            }
                        }
                        if (is_addNull) {
                            img_uri.add(null);
                        }
                        gridImgsAdapter.notifyDataSetChanged();
                    }
                });
                add_IB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("photos",(Serializable)single_photos);
                        bundle.putInt("position", position);
                        bundle.putString("save","save");
                        CommonUtils.launchActivity(getActivity(), PhotoPreviewActivity.class, bundle);
                    }
                });

            }
            return convertView;
        }
        class ViewHolder {
            ImageView add_IB;
            ImageView delete_IV;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                //图片判断
                if (data != null) {
                    number++;
                    //path 图片地址
                    List<String> paths = (List<String>) data.getExtras().getSerializable("photos");
                    //转换成bitmap
                     if (img_uri.size() > 0) {
                        img_uri.remove(img_uri.size() - 1);
                    }
                    System.out.println("1111ssscscscscs"+paths.size()+"");
                    for (int i = 0; i < paths.size(); i++) {

                        int po = 0;
                        for (Map.Entry<String, String> entry : photopaths.entrySet()) {
                            if (!(entry.getKey().toString().equals(paths.get(i).toString()))) {
                                String a = entry.getKey().toString();
                                String b = paths.get(i).toString();
                                ++po;
                            }}
                            //判断图片是否重复
                            iscorp=true;
                            for (int k=0;k<img_uri.size();k++){
                                String ima_urlpath=img_uri.get(k).getUrl();
                                Bitmap imag_url= BitmapFactory.decodeFile(ima_urlpath);
                                Bitmap path_i= BitmapFactory.decodeFile(paths.get(i));
                              if (isEquals(imag_url,path_i)){
                                  iscorp=false;
                              }
                            }
                            if (po == photopaths.size()&&iscorp) {
                            img_uri.add(new UploadGoodsBean(paths.get(i), false));
                            String datas = paths.get(i);
                            photopaths.put(datas.trim(), ss + "");
                            photopaths1[i] = datas;
                            Log.i("pppppppptooooo", photopaths.toString() + "");
                                photonumbers++;
                                //for合并
                                PhotoModel photoModel = new PhotoModel();
                                photoModel.setOriginalPath(paths.get(i));
                                photoModel.setChecked(true);
                                single_photos.add(photoModel);
                        }
//                        else if(number==1&&!iscorp){
//                                PhotoModel photoModel = new PhotoModel();
//                                photoModel.setOriginalPath(paths.get(i));
//                                photoModel.setChecked(true);
//                                single_photos.add(photoModel);
//                            }
                        }

                        //--------------------------------------------
                   //上传参数 将点击进去图片显示何在同一个for
//                    for (int i = 0; i < paths.size(); i++) {
//
//                            PhotoModel photoModel = new PhotoModel();
//                             photoModel.setOriginalPath(paths.get(i));
//                            photoModel.setChecked(true);
//                            single_photos.add(photoModel);
//
//                    }
                    if (img_uri.size() < 100) {
                        img_uri.add(null);
                    }
                    gridImgsAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
//    public void setCommit(){
//           commit=(Button)getActivity().findViewById(R.id.commit);
//                commit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(),"kkkk",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
            public String bitmap2Bytes(Bitmap bm) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
             bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
             byte[] byteArray=baos.toByteArray();
             String imageString=new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
                return imageString;
         }


    public void CallHttp(Request request)

    {
        okhttp3.Call call1 = okHttpClient.newCall(request);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", " GET请求失败！！！");
                Log.i("info", " e  = "+ e .toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String res = response.body().string();
                Log.i("info", " GET请求成功！！！");
                Log.i("info", " GET请求成功！！！"+res);

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Gson gson = new Gson();
//                        Manager user = gson.fromJson(res, Manager.class);
////                        user.getState();
//                        if ( user.getState().equals("true"))
//                        {
////                            Log.i("info", "res  = " + res.toString());
//                            Toast.makeText(getActivity(), "登录成功！！", Toast.LENGTH_SHORT).show();
//                            Intent intent1 = new Intent(getActivity(), MainActivity_2.class);
//                            startActivity(intent1);
//                            getActivity().finish();
//                        }//得到的res为用户ID  保存到本地
//                        else
//                            Toast.makeText(getActivity(), "登录失败了哦！！！", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });
    }

    public byte[] image2byte(String path){
        byte[] data = null;
        FileInputStream input = null;
        try {
            input = new FileInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead =0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            String s=new String(data,"utf-8");
            Log.i("data-------------",s);
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
    public void photoupload(){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<photonumbers;i++){
            String aa="";
        }
        String po ="{\"state\":\"upload\",\"files\":["+stringBuilder.toString()+"]}".trim();
        Log.i("stringBuilder----111",po.toString());
        RequestBody requestBody1 = RequestBody
                .create(MediaType.parse("text/x-markdown; charset=utf-8"),po.trim());
        Request.Builder builder3 = new Request.Builder();
        Request request2 = builder3
                .url(MainActivity.ur)
                .post(requestBody1)
                .build();
        CallHttp(request2);

    }
///聪哥
    class MyAsyncTask extends AsyncTask<String,Void,String>{
    @Override
        protected String doInBackground(String... strings) {
            try {
                File file=new File(strings[0]);
                return UploadUtil.uploadFile(file,strings[1]);

            }catch (Exception ex){
                ex.printStackTrace();
            }
          //
            return "failed";
        }


    @Override
        protected void onPostExecute(String s) {
         //   Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
        }
    }
    //图片是否重复进行判断
    public boolean isEquals(Bitmap b1,Bitmap b2){
        //先判断宽高是否一致，不一致直接返回false
        if(b1.getWidth()==b2.getWidth()
                &&b1.getHeight()==b2.getHeight()){
            int xCount = b1.getWidth();
            int yCount = b1.getHeight();
            for(int x=0; x<xCount; x++){
                for(int y=0; y<yCount; y++){
                    if(b1.getPixel(x, y)!=b2.getPixel(x, y)){
                        //比较每个像素点颜色
                        Log.i("图片重复判断","不重复");
                        return false;
                    }
                }
            }
            Log.i("图片重复判断","重复");
            return true;
        }else{
            Log.i("图片重复判断","不重复");
            return false;
        }
    }
}


