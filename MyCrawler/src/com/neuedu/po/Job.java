package com.neuedu.po;

//招聘信息封装，PO类
public class Job {
	//主键（编号）
	private int id;
	//岗位名称
	private String jobName;
	//公司名称
	private String company;
	//地址
	private String address;
	//薪酬待遇
	private double salary;
	
	public Job() {
		super();
	}
	public Job(int id, String jobName, String company, String address, double salary) {
		super();
		this.id = id;
		this.jobName = jobName;
		this.company = company;
		this.address = address;
		this.salary = salary;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	@Override
	public String toString() {
		return "Job [id=" + id + ", jobName=" + jobName + ", company=" + company + ", address=" + address
				+ ", salary=" + salary + "]";
	}
	
	
}
