package www.junf.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

import net.sf.json.JSONObject;

public class Test {
	public static void main(String[] args) {
		
		
		
		try {
			
			//0.获取用户的内容
			Scanner scanner=new Scanner(System.in);
				
			//1.定义网址
			String url = "http://www.tuling123.com/openapi/api"
					+ "?key=bc363bd2b2dc42578667a3316a84ebab&info="
					+ URLEncoder.encode(scanner.nextLine(), "utf-8");
			//2.给机器人发送消息
			URL wangzhi=new URL(url);
			URLConnection connection=wangzhi.openConnection();
			//3.接收机器人回复的消息
			InputStream input=connection.getInputStream();//字节流 
			InputStreamReader reader=new InputStreamReader(input);//字符流
			BufferedReader br=new BufferedReader(reader);
			//4.输出机器人回复的内容
			String message=br.readLine();
			
			JSONObject JSONObj=JSONObject.fromObject(message);
			message=(String)JSONObj.get("text");
			System.out.println("你的机器人对你说： "+message);
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
