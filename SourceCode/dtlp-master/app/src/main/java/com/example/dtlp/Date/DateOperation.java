package com.example.dtlp.Date;

import com.example.dtlp.MainActivity;
import com.example.dtlp.User.user;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//.User.user;
//        .MainActivity;

/**
 * Created by 阳瑞 on 2017/3/23.
 */
public class DateOperation {
    public static boolean FLAG;

    public String finduser(String key, String value)
    {
        BmobQuery<user> query = new BmobQuery<>();
        query.addWhereEqualTo(key,value);
         query.findObjects(new FindListener<user>() {
             @Override
             public void done(List<user> list, BmobException e) {
                 if (e == null)
                 {
                     for (user user : list)
                     {
                          MainActivity.PASSWORD = user.getPassword();
                         FLAG = true;

                     }
                 }
             }
         });

        return MainActivity.PASSWORD;
    }

}
