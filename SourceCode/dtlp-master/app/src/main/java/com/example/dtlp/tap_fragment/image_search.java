package com.example.dtlp.tap_fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dtlp.R;
import com.example.dtlp.tap_fragment.fragment_push_image.utility.DiskLruCache;
import com.example.dtlp.tap_fragment.fragment_push_image.utility.DiskLruCacheUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLConnection;

/**
 * Created by 阳瑞 on 2017/5/30.
 */
public class image_search extends Activity {
    public static String Label;
    String ID;
    private String imageurl;
    PictureTagLayout pictureTagLayout;
    private TextView textView;
    private ImageButton like,image_search;
    Bitmap bitmap;
    private boolean flag = true;
    private String image;
    private  String search = "";
    private ProgressDialog progress;


    ////////////////
    private DiskLruCacheUtil mDiskLruCacheUtil;
    private DiskLruCache mDiskLruCache;
    private LruCache<String, Bitmap> mLruCache;
    ////////////////////

    Handler handle = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {

            Bitmap bitmap = (Bitmap) msg.obj;
            Drawable drawable =new BitmapDrawable(bitmap);
//            Drawable drawable =new BitmapDrawable();
            pictureTagLayout.setBackground(drawable);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_main_image_dothelable);

        pictureTagLayout = (PictureTagLayout) findViewById(R.id.tupian);
        textView = (TextView) findViewById(R.id.tvPictureTagLabel);

        like = (ImageButton) findViewById(R.id.like);
        image_search = (ImageButton) findViewById(R.id.image_search);
        progress=new ProgressDialog(this);
//        progress.setIcon(R.drawable.ic_launcher);
        progress.setTitle("提示信息");
        progress.setMessage("正在下载，请稍候...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);


        Label = "";
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据
        image=bundle.getString("image");
        ID=bundle.getString("ID");
        imageurl = image;
        Log.i("BBBBaaa", " ima = " + image);
        Log.i("BBBBaaa", " ID = " + ID);


//        Thread mThread = new Thread() {
//            @Override
//            public void run() {
//                Message msg = new Message();
//                Bitmap bmp = getURLimage(image);
//                msg.obj = bmp;
//                System.out.println("000");
////                        }
//                handle.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
//            }
//        };
//        mThread.start();
//        Log.i("DDDDDDD", "textImage = " + Label);
        ////////////////
        mDiskLruCacheUtil = new DiskLruCacheUtil(image_search.this);
        mDiskLruCache = mDiskLruCacheUtil.doOpen();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
        ///////////////

        ///////////////////////////

        if (pictureTagLayout == null || image == null) {
            return;
        }
        // 从内存中获取数据
        Bitmap bitmap = mLruCache.get(image);
        // 如果内存中有数据
        if (bitmap != null) {
            Drawable drawable =new BitmapDrawable(bitmap);
            pictureTagLayout.setBackground(drawable);
            return;
        }
        // 从缓存文件中获取数据DiskUrlCache
        bitmap = mDiskLruCacheUtil.doGet(image);
        if (bitmap != null) {
            Log.i("info", "bitmap = " + bitmap);
            Drawable drawable =new BitmapDrawable(bitmap);
            pictureTagLayout.setBackground(drawable);
            if (mLruCache.get(image) == null) {
                // 把缓存文件中的数据加入内存中
                mLruCache.put(image, bitmap);
            }
            return;
        }
        ///////////////////////////

    }
    public void doClick(View view)
    {
        switch (view.getId())
        {
            case R.id.like:
                if (flag) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    // 设置参数
                    builder.setTitle("是否收藏该图片")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {// 积极

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // TODO Auto-generated method stub

                                    like.setBackgroundResource(R.drawable.like);


                                    new MyAsyncTask().execute(image);


//                                    Toast.makeText(getBaseContext(), "图片保存", Toast.LENGTH_SHORT).show();
//                                    Thread mThread = new Thread() {
//                                        @Override
//                                        public void run() {
//                                            Message msg = new Message();
//                                            Bitmap bmp = getURLimage(image);
//                                            msg.obj = bmp;
//                                            System.out.println("000");
////                        }
//                                            handler.sendMessage(msg); //新建线程加载图片信息，发送到消息队列中
//                                        }
//                                    };
//                                    mThread.start();


                                    flag = false;
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {// 消极

                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    builder.create().show();
                }else
                {

                }
                break;
            case R.id.image_search:
//                finsh();
                Intent intent = new Intent(image_search.this,loading_animation.class);
                intent.putExtra("image",image);
                setResult(00,intent);
//                finish();
                startActivity(intent);

                break;
        }

    }
//    public Bitmap getURLimage(String url) {
//        Bitmap bmp = null;
//        try {
//            URL myurl = new URL(url);
//            // 获得连接
//            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
//            conn.setConnectTimeout(6000);//设置超时
//            conn.setDoInput(true);
//            conn.setUseCaches(false);//不缓存
//            conn.connect();
//            InputStream is = conn.getInputStream();//获得图片的数据流
//            bmp = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bmp;
//    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        mIntent.putExtra("a",Label);
        mIntent.putExtra("b",ID);
        mIntent.putExtra("c",imageurl);
//        mIntent.putExtra("change02", "2000");
        // 设置结果，并进行传送
        this.setResult(0, mIntent);
        super.onBackPressed();
    }

    //    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK){
//            moveTaskToBack(true);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
    class MyAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        //执行异步任务（doInBackground）之前执行，并且在ui线程中执行
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//        if(image!=null){
//            image.setVisibility(View.GONE);
//        }
            //开始下载 对话框进度条显示
            progress.show();
            progress.setProgress(0);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // TODO Auto-generated method stub
            //params是一个可变长的数组 在这里我们只传进来了一个url
            String url=params[0];
            Bitmap bitmap=null;
            URLConnection connection;
            InputStream is;//用于获取数据的输入流
            ByteArrayOutputStream bos;//可以捕获内存缓冲区的数据，转换成字节数组。
            int len;
            float count=0,total;//count为图片已经下载的大小 total为总大小
            try {
                //获取网络连接对象
                connection=(URLConnection) new java.net.URL(url).openConnection();
                //获取当前页面的总长度
                total=(int)connection.getContentLength();
                //获取输入流
                is=connection.getInputStream();
                bos=new ByteArrayOutputStream();
                byte []data=new byte[1024];
                while((len=is.read(data))!=-1){
                    count+=len;
                    bos.write(data,0,len);
                    //调用publishProgress公布进度,最后onProgressUpdate方法将被执行
                    publishProgress((int)(count/total*100));
                    //为了显示出进度 人为休眠0.5秒
//                Thread.sleep(500);
                }
                bitmap= BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.toByteArray().length);
                is.close();
                bos.close();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
            return bitmap;
        }
        //在ui线程中执行 可以操作ui
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // TODO Auto-generated method stub
            super.onPostExecute(bitmap);
            //下载完成 对话框进度条隐藏

            String imageName = System.currentTimeMillis() + ".jpg";
            MediaStore.Images.Media.insertImage(image_search.this.getApplicationContext().getContentResolver(), bitmap, imageName, "牙医助理");


            progress.cancel();


            Toast.makeText(image_search.this, "图片保存成功", Toast.LENGTH_SHORT).show();


//            image.setImageBitmap(bitmap);
//            image.setVisibility(View.VISIBLE);
        }

        /*
         * 在doInBackground方法中已经调用publishProgress方法 更新任务的执行进度后
         * 调用这个方法 实现进度条的更新
         * */
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            progress.setProgress(values[0]);
        }
    }
}
