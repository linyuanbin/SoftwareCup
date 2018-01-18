package com.ghgk.photomanage.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ghgk.photomanage.R;

import java.util.List;
import java.util.Map;

/**
 * Created by cool on 2017-06-12.
 */
public class mmyapdter extends BaseAdapter {



        private List<Map<String,Object>> data;
        private LayoutInflater layoutInflater;
        private Context context;
        public mmyapdter(Context context, List<Map<String,Object>>data){
            this.context=context;
            this.data=data;
            this.layoutInflater=LayoutInflater.from(context);
        }
        public final class Zujian{
            public TextView name;
            public TextView feed;
            public CheckBox ask;

        }

        public  int getCount(){
            return  data.size();

        }
        public Object getItem(int position){
            return data.get(position);
        }
        public long getItemId(int position){
            return  position;
        }
        public View getView(int position, View view, ViewGroup viewGroup){
            Zujian zujian=null;
            if(view==null){
                zujian=new Zujian();
                view=layoutInflater.inflate(R.layout.array_item_feed,null);
                zujian.name=(TextView)view.findViewById(R.id.name);
                zujian.feed=(TextView)view.findViewById(R.id.feed);
                zujian.ask=(CheckBox)view.findViewById(R.id.ask);
                view.setTag(zujian);
            }
            else {
                zujian=(Zujian)view.getTag();
            }
            zujian.name.setText((String)data.get(position).get("name"));
            zujian.feed.setText((String)data.get(position).get("feed"));
            zujian.ask.setChecked((Boolean)data.get(position).get("ask"));
            return  view;

        }
    }


