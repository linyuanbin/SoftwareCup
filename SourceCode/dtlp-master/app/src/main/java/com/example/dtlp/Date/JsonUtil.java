package com.example.dtlp.Date;

import android.util.JsonReader;
import android.util.Log;

import java.io.StringReader;

/**
 * Created by 阳瑞 on 2017/3/4.
 */
public class JsonUtil {
    public static String UserID = "";
    public static String state = "";
    public void parseJson(String JsonData) {
        try {
            JsonReader reader = new JsonReader(new StringReader(JsonData));
//            reader.beginArray();
            while (reader.hasNext())
            {
                reader.beginObject();
                while (reader.hasNext())
                {
                    String data = reader.nextName();
                    Log.i("Json", "data = "+ data);
                    if (data.equals("UserID"))
                    {
                        Log.i("Json", "i = "+ 1);
//                        UserID = reader.nextString();
                        Log.i("Json", "UserID = "+ reader.nextString());
                    }
                      else  if (data.equals("state"))
                    {
                        state = reader.nextString();
                        Log.i("Json","state = "+reader.nextString());
                    }
                }
                reader.endObject();
            }
//            reader.endArray();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
