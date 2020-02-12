package com.a.modules.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("tuoproject")
public class Tuoproject implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String tuoId;
	/**
	 * 
	 */
	private String tuoMess;
	/**
	 * 
	 */
	private Integer tuoSource;
	/**
	 * 
	 */
	private String tuoType;

	/**
	 * 设置：
	 */
	public void setTuoId(String tuoId) {
		this.tuoId = tuoId;
	}
	/**
	 * 获取：
	 */
	public String getTuoId() {
		return tuoId;
	}
	/**
	 * 设置：
	 */
	public void setTuoMess(String tuoMess) {
		this.tuoMess = tuoMess;
	}
	/**
	 * 获取：
	 */
	public String getTuoMess() {
		return tuoMess;
	}
	/**
	 * 设置：
	 */
	public void setTuoSource(Integer tuoSource) {
		this.tuoSource = tuoSource;
	}
	/**
	 * 获取：
	 */
	public Integer getTuoSource() {
		return tuoSource;
	}
	/**
	 * 设置：
	 */
	public void setTuoType(String tuoType) {
		this.tuoType = tuoType;
	}
	/**
	 * 获取：
	 */
	public String getTuoType() {
		return tuoType;
	}

}
