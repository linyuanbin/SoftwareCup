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
        	System.out.println("wִ�е�ǰʱ��"+formatter.format(Calendar.getInstance().getTime()));
             //������д��Ҫִ�е�����
        	//��ÿ���ٳ�1�㶨ʱִ��
        	UpdateFirstMark.SetFirstMark();//��δ��ʼ����ͼƬ��ʼ����ǩ  |||||||||||||
        	
        	DetermineFinalMark.WriteFinalMark();//����һ�齫�����������ձ�ǩ��ͼƬ�������ձ�ǩ
        	//SuggesterUtil.setSuggester();
//        	new SuggesterUtil();  //8.12
            System.out.println("wִ�е�ǰʱ��"+formatter.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            System.out.println("-------------��ʼ����ǩ�쳣 ��ȷ�����ձ�ǩʧ��--------------");
        }
    }
     
}    