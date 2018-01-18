package getFirstMarkUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServlet;

public class AIMark {

	// http://apicn.imageplusplus.com/analyze?api_key=5955858fb07cbd8a9df3824f973aad8a&api_secret=2063b10602539e44bf31b91ae7f04998&url=Image_url
	// StringBuilder urlPath=new StringBuilder();
	
//	private static String ur = "http://apicn.imageplusplus.com/analyze?" + "api_key=5955858fb07cbd8a9df3824f973aad8a&"
//			+ "api_secret=2063b10602539e44bf31b91ae7f04998&" + "url=";

	public static String getFirstMark(String pAddress) throws MalformedURLException {
		String ur = "http://apicn.imageplusplus.com/analyze?" + "api_key=5955858fb07cbd8a9df3824f973aad8a&"
				+ "api_secret=2063b10602539e44bf31b91ae7f04998&" + "url=";
		ur = ur + pAddress;
		String firstMark = "";
		try {
			InputStream in = new URL(ur.trim()).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			firstMark = jsonText;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}

		return firstMark;
	}

	private static String readAll(BufferedReader br) throws IOException { //将缓冲流中的字符读到字符串中
		StringBuilder sb = new StringBuilder();
		String cp;
		while ((cp = br.readLine()) != null) {
			System.out.println(cp);
			sb.append(cp);
		}
		return sb.toString();
	}
	
	

}