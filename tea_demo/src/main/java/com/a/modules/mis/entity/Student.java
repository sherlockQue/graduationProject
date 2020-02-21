package com.a.modules.mis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("student")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 学号
	 */
	@TableId(value = "stu_id",type = IdType.INPUT)
	private String stuId;
	/**
	 * 
	 */
	private String username;
	/**
	 * 密码
	 */
	private String stuPsword;
	/**
	 * 盐
	 */
	private String salt;
	/**
	 * 年级：2016
	 */
	private String stuGrade;
	/**
	 * 专业：软件工程
	 */
	private String stuMajor;
	/**
	 * 班级
	 */
	private String stuClass;
	/**
	 * 
	 */
	private String stuPhone;
	/**
	 * 学院
	 */
	private String stuCollege;

	/**
	 * 设置：学号
	 */
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	/**
	 * 获取：学号
	 */
	public String getStuId() {
		return stuId;
	}
	/**
	 * 设置：
	 */
//	public void setStuName(String stuName) {
//		this.stuName = stuName;
//	}
//	/**
//	 * 获取：
//	 */
//	public String getStuName() {
//		return stuName;
//	}
	/**
	 * 设置：密码
	 */
	public void setStuPsword(String stuPsword) {
		this.stuPsword = stuPsword;
	}
	/**
	 * 获取：密码
	 */
	public String getStuPsword() {
		return stuPsword;
	}
	/**
	 * 设置：年级：2016
	 */
	public void setStuGrade(String stuGrade) {
		this.stuGrade = stuGrade;
	}
	/**
	 * 获取：年级：2016
	 */
	public String getStuGrade() {
		return stuGrade;
	}
	/**
	 * 设置：专业：软件工程
	 */
	public void setStuMajor(String stuMajor) {
		this.stuMajor = stuMajor;
	}
	/**
	 * 获取：专业：软件工程
	 */
	public String getStuMajor() {
		return stuMajor;
	}
	/**
	 * 设置：班级
	 */
	public void setStuClass(String stuClass) {
		this.stuClass = stuClass;
	}
	/**
	 * 获取：班级
	 */
	public String getStuClass() {
		return stuClass;
	}
	/**
	 * 设置：
	 */
	public void setStuPhone(String stuPhone) {
		this.stuPhone = stuPhone;
	}
	/**
	 * 获取：
	 */
	public String getStuPhone() {
		return stuPhone;
	}
	/**
	 * 设置：学院
	 */
	public void setStuCollege(String stuCollege) {
		this.stuCollege = stuCollege;
	}
	/**
	 * 获取：学院
	 */
	public String getStuCollege() {
		return stuCollege;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Student{" +
				"stuId='" + stuId + '\'' +
				", username='" + username + '\'' +
				", stuPsword='" + stuPsword + '\'' +
				", salt='" + salt + '\'' +
				", stuGrade='" + stuGrade + '\'' +
				", stuMajor='" + stuMajor + '\'' +
				", stuClass='" + stuClass + '\'' +
				", stuPhone='" + stuPhone + '\'' +
				", stuCollege='" + stuCollege + '\'' +
				'}';
	}
}
