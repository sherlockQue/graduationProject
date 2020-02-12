package com.a.modules.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("appealitem")
public class Appealitem implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long apId;
	/**
	 * 学号
	 */
	private String stuId;
	/**
	 * 素拓认证项
	 */
	private Long stId;
	/**
	 * 
	 */
	private String apReason;
	/**
	 * 申请时间
	 */
	private LocalDateTime apApplytime;
	/**
	 * 状态，0/1/2/3，未申诉/申诉中/申诉通过/申诉未通过
	 */
	private Integer apStatus;
	/**
	 * 负责人id
	 */
	private String apChargeid;
	/**
	 * 处理时间
	 */
	private LocalDateTime apDealtime;
	/**
	 * 处理原因
	 */
	private String apDealreason;

	/**
	 * 设置：
	 */
	public void setApId(Long apId) {
		this.apId = apId;
	}
	/**
	 * 获取：
	 */
	public Long getApId() {
		return apId;
	}
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
	 * 设置：素拓认证项
	 */
	public void setStId(Long stId) {
		this.stId = stId;
	}
	/**
	 * 获取：素拓认证项
	 */
	public Long getStId() {
		return stId;
	}
	/**
	 * 设置：
	 */
	public void setApReason(String apReason) {
		this.apReason = apReason;
	}
	/**
	 * 获取：
	 */
	public String getApReason() {
		return apReason;
	}
	/**
	 * 设置：申请时间
	 */
	public void setApApplytime(LocalDateTime apApplytime) {
		this.apApplytime = apApplytime;
	}
	/**
	 * 获取：申请时间
	 */
	public LocalDateTime getApApplytime() {
		return apApplytime;
	}
	/**
	 * 设置：状态，0/1/2/3，未申诉/申诉中/申诉通过/申诉未通过
	 */
	public void setApStatus(Integer apStatus) {
		this.apStatus = apStatus;
	}
	/**
	 * 获取：状态，0/1/2/3，未申诉/申诉中/申诉通过/申诉未通过
	 */
	public Integer getApStatus() {
		return apStatus;
	}
	/**
	 * 设置：负责人id
	 */
	public void setApChargeid(String apChargeid) {
		this.apChargeid = apChargeid;
	}
	/**
	 * 获取：负责人id
	 */
	public String getApChargeid() {
		return apChargeid;
	}
	/**
	 * 设置：处理时间
	 */
	public void setApDealtime(LocalDateTime apDealtime) {
		this.apDealtime = apDealtime;
	}
	/**
	 * 获取：处理时间
	 */
	public LocalDateTime getApDealtime() {
		return apDealtime;
	}
	/**
	 * 设置：处理原因
	 */
	public void setApDealreason(String apDealreason) {
		this.apDealreason = apDealreason;
	}
	/**
	 * 获取：处理原因
	 */
	public String getApDealreason() {
		return apDealreason;
	}

}
