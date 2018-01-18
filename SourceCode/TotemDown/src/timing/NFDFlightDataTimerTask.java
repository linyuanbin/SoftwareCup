package timing;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;

import action.DetermineFinalMark;
import action.UpdateFirstMark;
import util.SuggesterUtil;

public class NFDFlightDataTimerTask extends TimerTask {
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void run() {
        try {
        	System.out.println("w执行当前时间"+formatter.format(Calendar.getInstance().getTime()));
             //在这里写你要执行的内容
        	//在每天临晨1点定时执行
        	UpdateFirstMark.SetFirstMark();//将未初始化的图片初始化标签  |||||||||||||
        	
        	DetermineFinalMark.WriteFinalMark();//遍历一遍将符合设置最终标签的图片设置最终标签
        	//SuggesterUtil.setSuggester();
//        	new SuggesterUtil();  //8.12
            System.out.println("w执行当前时间"+formatter.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            System.out.println("-------------初始化标签异常 或确定最终标签失败--------------");
        }
    }
     
}    