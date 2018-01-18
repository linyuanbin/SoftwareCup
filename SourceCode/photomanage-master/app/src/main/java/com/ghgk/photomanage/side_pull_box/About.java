package com.ghgk.photomanage.side_pull_box;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ghgk.photomanage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 阳瑞 on 2017/4/4.
 */
public class About extends Activity {
    private ListView listView;
    private ArrayAdapter aadp;
    private List list = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_pull_box_about);
        listView = (ListView) findViewById(R.id.list);
        list.add("百度翻译");
        list.add("Hanlp中文解析");
        list.add("okhttp");
        list.add("gson");
        list.add("MobCommons");
        list.add("nineoldandroids");
        list.add("rxandroid");
        list.add("rxjava");
        list.add("materialrefeshlayout");
        list.add("smallchart");
        list.add("image++");
        aadp=new ArrayAdapter(this,R.layout.side_pull_box_about_text,list);
        listView.setAdapter(aadp);
    }
}
