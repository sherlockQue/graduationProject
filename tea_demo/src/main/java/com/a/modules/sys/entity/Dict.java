package com.a.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("dict")
public class Dict implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String grade;
	/**
	 * 
	 */
	private Integer term;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * 获取：
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * 设置：
	 */
	public void setTerm(Integer term) {
		this.term = term;
	}
	/**
	 * 获取：
	 */
	public Integer getTerm() {
		return term;
	}

	@Override
	public String toString() {
		return "Dict{" +
				"id=" + id +
				", grade='" + grade + '\'' +
				", term=" + term +
				'}';
	}
}
