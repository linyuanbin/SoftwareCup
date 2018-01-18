package com.ghgk.photomanage.Date;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ghgk.photomanage.R;
import com.ghgk.photomanage.utility.ImageLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class PhotosWallAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageUrlList = new ArrayList<>();
    private List<String> imageUrl = new ArrayList<>();
    private List<String> imagelabel = new ArrayList<>();

    /**
     * 记录每个子项的高度
     */
    private int mItemHeight = 0;

    public ImageLoader mImageLoader;

    public PhotosWallAdapter(Context context, Map<String,String> data, AbsListView absListView) {
        Iterator it = data.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry en = (Map.Entry) it.next();
            String key = (String) en.getKey();
            imageUrl.add(key);
            String value = (String) en.getValue();
            imagelabel.add(value);
//     Log.i("Map", "key =  " + key +"Value = " + value);
        }
        this.imageUrlList = imageUrl;
      //  this.imagelabel=
        this.context = context;
       mImageLoader = new ImageLoader(context, absListView);
    }

    public  void SetData(List fhistoryImageUrl,List fhistoryMark){
        //imageUrlList.clear();
     //   imagelabel.clear();
        this.imageUrlList=fhistoryImageUrl;
        this.imagelabel=fhistoryMark;

    }
    @Override
    public int getCount() {
        if (imageUrlList != null) {
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
            convertView = View.inflate(context, R.layout.photo_result_label, null);
            holder.iv = (ImageView) convertView.findViewById(R.id.photo_resutl);
            holder.label = (TextView) convertView.findViewById(R.id.label_resutl);
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
        mImageLoader.loadBitmaps(holder.iv, url);
        return convertView;
    }

    private class ViewHolder {
        ImageView iv;
        TextView label;
    }

    /**
     * 设置item子项的大小
     */
    public void setItemSize(int edgeLength) {

        mItemHeight = edgeLength;
        mImageLoader.setItemLength(edgeLength);
    }
}