package com.example.dtlp.utils;

import com.example.dtlp.R;
import com.example.dtlp.entity.ItemBean;

import java.util.ArrayList;
import java.util.List;




public final class ItemDataUtils {

    private ItemDataUtils() {
    }

    public static List<ItemBean> getItemBeans(){
        List<ItemBean> itemBeans=new ArrayList<>();
        itemBeans.add(new ItemBean(R.drawable.sidebar_information,"个人信息",false));
        itemBeans.add(new ItemBean(R.drawable.sidebar_integral,"任务",false));
        itemBeans.add(new ItemBean(R.drawable. sidebar_set,"设置",false));
        itemBeans.add(new ItemBean(R.drawable.sidabar_about,"关于",false));
        itemBeans.add(new ItemBean(R.drawable.sideabr_update,"检查更新",false));
        return  itemBeans;
    }

}
