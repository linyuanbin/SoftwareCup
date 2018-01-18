package com.ghgk.photomanage.utils;

import com.ghgk.photomanage.R;
import com.ghgk.photomanage.entity.ItemBean;

import java.util.ArrayList;
import java.util.List;


/**
 * 当前类注释:
 * ProjectName：DragHelper4QQ
 * Author:<a href="http://www.cniao5.com">菜鸟窝</a>
 * Description：
 * 菜鸟窝是一个只专注做Android开发技能的在线学习平台，课程以实战项目为主，对课程与服务”吹毛求疵”般的要求，
 * 打造极致课程，是菜鸟窝不变的承诺
 */
public final class ItemDataUtils {

    private ItemDataUtils() {
    }

    public static List<ItemBean> getItemBeans(){
        List<ItemBean> itemBeans=new ArrayList<>();
        itemBeans.add(new ItemBean(R.drawable.sidebar_information,"个人信息",false));
//        itemBeans.add(new ItemBean(R.drawable.sidebar_integral,"积分",false));
        itemBeans.add(new ItemBean(R.drawable. sidebar_set,"设置",false));
        itemBeans.add(new ItemBean(R.drawable.sidabar_about,"关于",false));
        itemBeans.add(new ItemBean(R.drawable.sideabr_update,"检查更新",false));
        return  itemBeans;
    }

}
