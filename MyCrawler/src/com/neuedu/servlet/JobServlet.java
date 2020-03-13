package com.neuedu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.neuedu.dao.JobDao;

/**
 * Servlet implementation class JobServlet
 */
@WebServlet("/JobServlet")
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1、调用service层方法，获取数据------暂且省略
		/*
		 * 1、直接调用dao层方法，获取数据
		 */
		JobDao dao=new JobDao();
		List<Map<String,Object>> jobAvgSalList=dao.selectJobAvgSal();
		/*for(Map<String,Object> map:jobAvgSalList) {
			System.out.println(map);
		}*/
		
		/*
		 * 2、将jobName与avgSal拆分成两组集合，分别保存
		 */
		//定义一个集合保存jobName
		List<String> jobNameList=new ArrayList<String>();

		//定义一个集合保存avgSal
		List<String> avgSalList=new ArrayList<String>();
		
		//(jobName=JAVA中级工程师，avgSal=9500.0000)
		for(Map<String,Object> jobAvgSal:jobAvgSalList) {
			//JAVA中级工程师
			jobNameList.add((String)jobAvgSal.get("jobName"));
			//9500.0000
			avgSalList.add((String)jobAvgSal.get("avgSal"));
		}
		
		/*
		 * 3、将所有的岗位信息jobNameList与薪资信息avgSalList保存到同一集合
		 */
		Map<String,List> returnMap=new HashMap<String,List>();
		returnMap.put("jobName", jobNameList);
		returnMap.put("avgSal", avgSalList);
		
		/*
		 * 4、将map集合转换为Json格式的字符串
		 */
		Gson gson=new Gson();
		String json=gson.toJson(returnMap);
		/*
		*5、将结果响应回浏览器界面
		*/
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");

		PrintWriter writer=response.getWriter();
		writer.write(json);
		writer.flush();
		writer.close();
	}

}
