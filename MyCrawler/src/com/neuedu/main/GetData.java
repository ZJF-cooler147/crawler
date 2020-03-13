package com.neuedu.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.neuedu.dao.JobDao;
import com.neuedu.po.Job;

public class GetData {
	public static void main(String[] args) {
		
			//1.定义网址
			String url=
					"https://search.51job.com/list/030200,000000,0000,00,9,99,"
					+ "java%25E8%25BD%25AF%25E4%25BB%25B6%25E5%25B7%25A5%25E7%25A8%258B%25E5%25B8%2588,"
					+ "2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm="
					+ "99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate="
					+ "9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
			
			/**
			 * 2.根据地址获取整个HTML文档的所有内容
			 */
			
			String html=getHtml(url);
			System.out.println(html);
			/*
			 * 3.解析HTML文档，获取所有的招聘信息
			 */
			List<Job> jobList = parseHtml(html);
			
			//4.将所有招聘信息保存到数据库（MySQL，Oracle）
			/*for(Job job:jobList) {
				System.out.println(job.toString());
			}*/
			 //调用dao层方法完成新增到数据库功能
			JobDao dao=new JobDao();
			//boolean result = dao.insertJob(jobList);
			//System.out.println("新增总共"+jobList.size()+"行："+result);
			
			//5.根据指定的特征分析统计数据（可视化）
			List<Map<String,Object>> javaAvgSalList= dao.selectJobAvgSal();
			for(Map<String,Object> map:javaAvgSalList) {
				System.out.println(map);
			}
	}
	private static List<Job> parseHtml(String html) {
		//获取整个html的Document对象
		Document document= Jsoup.parse(html);
		
		//获取所有招聘信息的父节点div，id="resultList"
		
		Element div=document.getElementById("resultList");
		
		//获取所有的具体的招聘信息div class="el"
		Elements jobs=div.getElementsByClass("el");
		
		//定义一个集合保存所有的招聘信息
		List<Job> jobList=new ArrayList<Job>();
		
		//遍历获取所有的招聘信息
		for(int i=1;i<jobs.size();i++) {
			Element job = jobs.get(i);
			
//				System.out.println(job.html());
//				System.out.println("-----------------------");

			/*岗位名称class="t1"
			 * 将岗位中所有因为转换为大写
			 * */
			String jobName=job.getElementsByClass("t1").get(0).text().toUpperCase();//toUppercase(),将所有字母转换成大写
			jobName = getJobName(jobName);//统一规范岗位名称：JAVA,PYTHON,C#,PHP,C++,SCALA
			
			/*公司名称class="t2" */
			String company=job.getElementsByClass("t2").get(0).text();
			/*所在地区class="t3" */
			String address=job.getElementsByClass("t3").get(0).text();
			/*薪酬待遇class="t4" 
			 */
			String salary=job.getElementsByClass("t4").get(0).text();
			double doublesalary=getSalary(salary);
			
			//输出招聘信息
			//System.out.println(jobName+"\t"+company+"\t"+address+"\t"+salary+"\t"+doublesalary);
			
			//将当前的招聘信息封装到Job对象中
			Job tempJob=new Job(0,jobName,company,address,doublesalary);
			jobList.add(tempJob);
			
		}
		return jobList;
	}
	/*
	 * 根据（前/月）（万/月）等单位计算同一招聘信息的平均薪资
	 * @param salary
	 * return doublesalary
	 */
	private static Double getSalary(String salary) {
		/* 以千为单位 6-8千/月
		 * 以万为单位 0.9-1.1万/月
		 * */
		
		double doublesalary=0.0;
		//6-8千/月
		if(salary.contains("千/月")) {
			salary=salary.substring(0, salary.indexOf("千/月"));//6-8
			String begin=salary.substring(0, salary.indexOf("-"));//6
			String end=salary.substring(salary.indexOf("-")+1);//8
			doublesalary=(Double.valueOf(begin)+Double.valueOf(end))/2*1000;//（6+8）/2*1000=7000
			
		}
		
		
		//0.9-1.1万/月
		else if(salary.contains("万/月")) {
			salary=salary.substring(0, salary.indexOf("万/月"));//0.9-1.1
			String begin=salary.substring(0, salary.indexOf("-"));//0.9
			String end=salary.substring(salary.indexOf("-")+1);//1.1
			doublesalary=(Double.valueOf(begin)+Double.valueOf(end))/2*10000;//（0.9+1.1）/2*10000=10000
		}
		return doublesalary;
	}
	/*
	 * 统一规范命名岗位名称
	 * @param jobName 原始岗位名称
	 * @return 规范后的公司名称
	 */
	private static String getJobName(String jobName) {
		//技术方向：前端工程师，Java见习工程师，Java初级工程师，Java中级工程师，Java高级工程师，
		//非技术
		//JAVA
		if(jobName.contains("JAVA")) {
			if(jobName.contains("见习")||jobName.contains("实习")) {
				jobName="JAVA见习工程师";
			}
			else if(jobName.contains("初级")) {
				jobName="JAVA初级工程师";
			}
			else if(jobName.contains("中级")) {
				jobName="JAVA中级工程师";
			}
			else if(jobName.contains("高级")) {
				jobName="JAVA高级工程师";
			}
			else if(jobName.contains("架构")) {
				jobName="JAVA架构师";
			}
			else {
				jobName="JAVA软件工程师";
			}
		}
		//PYTHON
		else if(jobName.contains("PYTHON")) {
			
		}
		//PHP
		else if(jobName.contains("PHP")) {
			
		}
		//SCALA
		else if(jobName.contains("SCALA")) {
			
		}
		return jobName;
	}

	private static String getHtml(String url) {
		try {
			//创建URL对象
			URL wangzhi = new URL(url);
			//打开到前程无忧服务器的连接
			URLConnection connection=wangzhi.openConnection();
			//获取整个文档所有内容
			InputStream input=connection.getInputStream();//字节流
			InputStreamReader reader= new InputStreamReader(input,"GBK");//字符流
			BufferedReader br=new BufferedReader(reader);//一行一行读数据
	
	//			System.out.println(br.readLine());//第一行
	//			System.out.println(br.readLine());//第二行
	//			System.out.println(br.readLine());
	//			System.out.println(br.readLine());
			
			StringBuffer html=new StringBuffer();//保存整个html文档信息
			String line=null;
			while((line=br.readLine())!=null) {
				html.append(line);//将当前行数据拼接到html对象上
			}
			System.out.println(html);
			return html.toString();
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}return null;
	}
	
	
}
	
