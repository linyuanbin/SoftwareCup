package com.ghgk.photomanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class feed_single_item extends AppCompatActivity {
    private ImageButton button;
    Boolean b=false;
    private  CheckBox ask_cc;
    private  Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_single_item);
       intent=getIntent();
        Bundle bundle=intent.getExtras();
        String name=bundle.getString("name");
        String feed=bundle.getString("feed");
        Boolean ask=bundle.getBoolean("ask");
        ShimmerTextView tt_name=(ShimmerTextView)findViewById(R.id.tt_name);
        tt_name.setText(name+"");
        ShimmerTextView tt_feed=(ShimmerTextView)findViewById(R.id.tt_feed);
        tt_feed.setText("内容："+feed);
        Shimmer shimmer = new Shimmer();
        shimmer.start(tt_name);
        shimmer.start(tt_feed);

         ask_cc=(CheckBox)findViewById(R.id.cb_ask);
        ask_cc.setChecked(ask);


        button=(ImageButton)findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b=ask_cc.isChecked();
                intent.putExtra("ischeck",b);
                setResult(0,intent);
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        b=ask_cc.isChecked();
        intent.putExtra("ischeck",b);
        setResult(0,intent);
//                Intent intent1=new Intent(single_feed_sovle.this,main_search.class);
//                startActivity(intent1);
        finish();
        super.onBackPressed();
    }
    }

