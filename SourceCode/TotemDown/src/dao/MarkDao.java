package dao;

import model.Mark;

public interface MarkDao {
	 public boolean insertIntoMark(String UserId,String PID,String MarkName);
	 public boolean insertMark(Mark m);
	 public boolean deleteOnMark(String TabId);
	 public boolean updateMark(Mark m);
	 public boolean updateMarkName(String TabId,String markName);
	 public Mark getMark(String TabId);
	 

}
