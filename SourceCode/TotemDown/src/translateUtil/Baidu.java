package translateUtil;
import java.util.List;
import java.util.Map;

public class Baidu{
	String from,to;
    List trans_result;
    
    public List getTrans_result() {
		return trans_result;
	}
	public void setTrans_result(List trans_result) {
		this.trans_result = trans_result;
	}
	public class Da{
        String dst,src;

        public String getDst() {
            return dst;
        }

        public void setDst(String dst) {
            this.dst = dst;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
        
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public List getData() {
        return trans_result;
    }
    public void setData(List data) {
        this.trans_result = data;
    }
}
