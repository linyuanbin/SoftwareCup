package model;

public class HistoryMark {

	private String PID;
	private String MarkName;
	private String PAddress;
	private String FinalMarkName="";
	private String state;
	
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
