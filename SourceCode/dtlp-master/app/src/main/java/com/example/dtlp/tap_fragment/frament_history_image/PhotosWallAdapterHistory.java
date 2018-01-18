package com.example.dtlp.tap_fragment.frament_history_image;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dtlp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 阳瑞 on 2017/5/29.
 */
public class PhotosWallAdapterHistory extends BaseAdapter {
    private Context context;
    private List<String> imageUrlList = new ArrayList<>();
    private List<String> imageUrl = new ArrayList<>();
    private List<String> imagelabel = new ArrayList<>();

    /**
     * 记录每个子项的高度
     */
    private int mItemHeight = 0;

//    public ImageLoader_history mImageLoader_history;
//    public ImageLoader mImageLoader;
    public ImageLoader_history mImageLoader_history;

    public PhotosWallAdapterHistory(Context context, List<Map<String,String>> data, AbsListView absListView) {
//        Iterator it = data.entrySet().iterator();
//        while (it.hasNext())
//        {
//            Map.Entry en = (Map.Entry) it.next();
//            String key = (String) en.getKey();
//            imageUrl.add(key);
//            String value = (String) en.getValue();
//            imagelabel.add(value);
////            Log.i("Map", "key =  " + key +"Value = " + value);
//        }

        for (int i = 0;i<data.size();i++)
        {
           Map<String,String> map =  data.get(i);
            for(Map.Entry<String, String> entry:map.entrySet()){
//                            startActivity(intent);
                String key = entry.getKey();
                imageUrl.add(key);
                String value = entry.getValue();
                imagelabel.add(value);
//                System.out.println("The array is:" + entry.getKey() + "= " + entry.getValue());
            }
        }

        this.imageUrlList = imageUrl;
        this.context = context;
//        mImageLoader = new ImageLoader(context,absListView);
        mImageLoader_history = new ImageLoader_history(context, absListView);
    }

    @Override
    public int getCount() {
        if (imageUrlList != null) {
            for (int i = 0 ; i<imageUrlList.size();i++)
            Log.i("imageUrlList", "imageUrlList: = " + imageUrlList.get(i));
            return imageUrlList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return imageUrlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String label = imagelabel.get(position);
        String url = imageUrlList.get(position);
        System.out.println(position + "-url: " + url);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemHeight, mItemHeight);
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.user_main_history_image, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.history_photo);
            holder.label = (TextView) convertView.findViewById(R.id.history_label);
            holder.iv.setLayoutParams(layoutParams);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iv.setTag(url);
        // 设置默认的图片
        holder.iv.setImageResource(R.drawable.empty_photo);
        holder.label.setText(label);
        // 加载数据
//        mImageLoader.loadBitmaps(holder.iv,url);
        mImageLoader_history.loadBitmaps(holder.iv, url);
        return convertView;
    }

    private class ViewHolder {
        ImageView iv;
        TextView label;
    }
    public void setData(List list1,List list2)
    {
        this.imageUrlList=list1;
        this.imagelabel=list2;
    }

    /**
     * 设置item子项的大小
     */
    public void setItemSize(int edgeLength) {
        mItemHeight = edgeLength;
//        mImageLoader.setItemLength(edgeLength);
        mImageLoader_history.setItemLength(edgeLength);
    }

}
