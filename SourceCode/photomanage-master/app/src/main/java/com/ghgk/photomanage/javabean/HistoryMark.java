package com.ghgk.photomanage.javabean;

/**
 * Created by 阳瑞 on 2017/5/27.
 */
public class HistoryMark {

    private String PID;
    private String MarkName;
    private String PAddress;
    private String FinalMarkName=" ";

    public String getPID() {
        return PID;
    }
    public void setPID(String pID) {
        PID = pID;
    }
    public String getMarkName() {
        return MarkName;
    }
    public void setMarkName(String markName) {
        MarkName = markName;
    }
    public String getPAddress() {
        return PAddress;
    }
    public void setPAddress(String pAddress) {
        PAddress = pAddress;
    }
    public String getFinalMarkName() {
        return FinalMarkName;
    }
    public void setFinalMarkName(String finalMarkName) {
        FinalMarkName = finalMarkName;
    }


}
