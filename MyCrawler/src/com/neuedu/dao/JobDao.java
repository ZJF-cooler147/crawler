package com.neuedu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neuedu.po.Job;

public class JobDao {
	
	private Connection conn=null;
	private PreparedStatement stat=null;
	private ResultSet rs=null;
	/*
	 * 新增招聘信息
	 * @param jobList 所有的招聘信息的集合
	 * @return true 新增成功，false 新增失败
	 */
	public boolean insertJob(List<Job> jobList) {
		
		try {
			//1、加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			//2、定义url连接
			String url="jdbc:mysql://localhost:3306/creader?useUnicode=true&characterEncoding=utf8";
			
			//3、获取数据库连接
			conn=DriverManager.getConnection(url,"root","123456");
			System.out.println(conn);
			
			//4、获取PreparedStatement对象
			stat=conn.prepareStatement("insert into job(jobName,company,address,salary) values(?,?,?,?)");
			
			//5、绑定变量
			for(Job job:jobList) {
				stat.setString(1, job.getJobName());
				stat.setString(2, job.getCompany());
				stat.setString(3, job.getAddress());
				stat.setDouble(4, job.getSalary());
				
				stat.addBatch();//将当前数据添加批处理语句
			}
			
			//6、执行新增语句
			int[] results=stat.executeBatch();//批量处理所有的insert语句
			
			//7、处理结果
			for(int result:results) {
				if(result <= 0) {
					return false;
				}
			}
			return true;
			
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();//记录日志，发送邮件给管理员，抛出异常
		}catch(SQLException e) {
			e.printStackTrace();//记录日志，发送邮件给管理员，抛出异常
		}finally {
			//8、关闭资源
			//使用顺序Connection -> PreparedStatement
			//关闭顺序PreparedStatement -> Connection
			try {
				if(stat == null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn == null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return false;
	}
	
public List<Map<String,Object>> selectJobAvgSal() {
		
		try {
			//1、加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			//2、定义url连接
			String url="jdbc:mysql://localhost:3306/creader?useUnicode=true&characterEncoding=utf8";
			
			//3、获取数据库连接
			conn=DriverManager.getConnection(url,"root","123456");
			System.out.println(conn);
			
			//4、获取PreparedStatement对象
			stat=conn.prepareStatement("select jobName,avg(salary) avgSal,min(salary) minSal,max(salary) maxSal from job group by jobName");
			
			
			
			//6、执行新增语句
			rs=stat.executeQuery();
			
			//7、处理结果
			//保存所有数据的List集合
			List<Map<String,Object>> jobAvgSalList=new ArrayList<Map<String,Object>>();
			while(rs.next()) {
				//保存一行数据的map集合{jobName=java软件工程师，avgSal=9000}
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("jobName",rs.getString("jobName"));
				map.put("avgSal",rs.getString("avgSal"));
				
				//将当前数据保存到List集合
				jobAvgSalList.add(map);
			}
			return jobAvgSalList;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();//记录日志，发送邮件给管理员，抛出异常
		}catch(SQLException e) {
			e.printStackTrace();//记录日志，发送邮件给管理员，抛出异常
		}finally {
			//8、关闭资源
			//使用顺序Connection -> PreparedStatement -> ResultSet
			//关闭顺序ResultSet -> PreparedStatement -> Connection
			
			try {
				if(rs == null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(stat == null) {
					stat.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn == null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return null;
	}
}
