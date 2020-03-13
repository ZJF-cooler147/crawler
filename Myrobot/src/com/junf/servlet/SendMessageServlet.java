package com.junf.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class SendMessageServlet
 */
@WebServlet("/SendMessageServlet")
public class SendMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public SendMessageServlet() {
       
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try { 
        String sendMessage=request.getParameter("sendMessage");
      //1.定义网址
		String url = "http://www.tuling123.com/openapi/api"
				+ "?key=bc363bd2b2dc42578667a3316a84ebab&info="
				+ URLEncoder.encode(sendMessage, "utf-8");
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
		//5编码问题
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/plain;charset=utf-8");
		//6响应回去
		PrintWriter writer=response.getWriter();
		writer.write(message);
		writer.flush();
		writer.close();
		
	}
	catch (UnsupportedEncodingException e) {
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