package com.a.modules.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("college")
public class College implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 年级
	 */
	private String uGrade;
	/**
	 * 
	 */
	private String uCollege;
	/**
	 * 
	 */
	private String uMajor;
	/**
	 * 
	 */
	private String uClass;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：年级
	 */
	public void setUGrade(String uGrade) {
		this.uGrade = uGrade;
	}
	/**
	 * 获取：年级
	 */
	public String getUGrade() {
		return uGrade;
	}
	/**
	 * 设置：
	 */
	public void setUCollege(String uCollege) {
		this.uCollege = uCollege;
	}
	/**
	 * 获取：
	 */
	public String getUCollege() {
		return uCollege;
	}
	/**
	 * 设置：
	 */
	public void setUMajor(String uMajor) {
		this.uMajor = uMajor;
	}
	/**
	 * 获取：
	 */
	public String getUMajor() {
		return uMajor;
	}
	/**
	 * 设置：
	 */
	public void setUClass(String uClass) {
		this.uClass = uClass;
	}
	/**
	 * 获取：
	 */
	public String getUClass() {
		return uClass;
	}

	@Override
	public String toString() {
		return "College{" +
				"id=" + id +
				", uGrade='" + uGrade + '\'' +
				", uCollege='" + uCollege + '\'' +
				", uMajor='" + uMajor + '\'' +
				", uClass='" + uClass + '\'' +
				'}';
	}
}
