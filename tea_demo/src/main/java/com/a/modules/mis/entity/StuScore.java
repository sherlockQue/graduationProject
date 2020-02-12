package com.a.modules.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("stu_score")
public class StuScore implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 学号
	 */
	private String sStuid;
	/**
	 * 学年
	 */
	private String sGrade;
	/**
	 * 学期
	 */
	private Integer sTerm;
	/**
	 * 总分
	 */
	private Double sScore;

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
	 * 设置：学号
	 */
	public void setSStuid(String sStuid) {
		this.sStuid = sStuid;
	}
	/**
	 * 获取：学号
	 */
	public String getSStuid() {
		return sStuid;
	}
	/**
	 * 设置：学年
	 */
	public void setSGrade(String sGrade) {
		this.sGrade = sGrade;
	}
	/**
	 * 获取：学年
	 */
	public String getSGrade() {
		return sGrade;
	}
	/**
	 * 设置：学期
	 */
	public void setSTerm(Integer sTerm) {
		this.sTerm = sTerm;
	}
	/**
	 * 获取：学期
	 */
	public Integer getSTerm() {
		return sTerm;
	}
	/**
	 * 设置：总分
	 */
	public void setSScore(Double sScore) {
		this.sScore = sScore;
	}
	/**
	 * 获取：总分
	 */
	public Double getSScore() {
		return sScore;
	}

}
