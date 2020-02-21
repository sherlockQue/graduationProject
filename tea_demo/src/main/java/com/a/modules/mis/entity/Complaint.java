package com.a.modules.mis.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("complaint")
public class Complaint implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 学号
	 */
	private String stuId;
	/**
	 * 素拓认证项
	 */
	private Long stId;
	/**
	 * 年度
	 */
	private String cpGrade;
	/**
	 * 学期
	 */
	private Integer cpTerm;
	/**
	 * 申诉原因
	 */
	private String cpReason;
	/**
	 * 申请时间
	 */
	private LocalDateTime cpApplytime;
	/**
	 * 状态，0/1/2/3，未申诉/申诉中/申诉通过/申诉未通过
	 */
	private Integer cpStatus;
	/**
	 * 负责人id
	 */
	private Long cpChargeid;
	/**
	 * 处理时间
	 */
	private LocalDateTime cpDealtime;
	/**
	 * 处理原因
	 */
	private String cpDealreason;

	@TableField(exist = false)
	private Strelation strelation;


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
	 * 设置：年度
	 */
	public void setCpGrade(String cpGrade) {
		this.cpGrade = cpGrade;
	}
	/**
	 * 获取：年度
	 */
	public String getCpGrade() {
		return cpGrade;
	}
	/**
	 * 设置：学期
	 */
	public void setCpTerm(Integer cpTerm) {
		this.cpTerm = cpTerm;
	}
	/**
	 * 获取：学期
	 */
	public Integer getCpTerm() {
		return cpTerm;
	}
	/**
	 * 设置：申诉原因
	 */
	public void setCpReason(String cpReason) {
		this.cpReason = cpReason;
	}
	/**
	 * 获取：申诉原因
	 */
	public String getCpReason() {
		return cpReason;
	}
	/**
	 * 设置：申请时间
	 */
	public void setCpApplytime(LocalDateTime cpApplytime) {
		this.cpApplytime = cpApplytime;
	}
	/**
	 * 获取：申请时间
	 */
	public LocalDateTime getCpApplytime() {
		return cpApplytime;
	}
	/**
	 * 设置：状态，0/1/2/3，未申诉/申诉中/申诉通过/申诉未通过
	 */
	public void setCpStatus(Integer cpStatus) {
		this.cpStatus = cpStatus;
	}
	/**
	 * 获取：状态，0/1/2/3，未申诉/申诉中/申诉通过/申诉未通过
	 */
	public Integer getCpStatus() {
		return cpStatus;
	}
	/**
	 * 设置：负责人id
	 */
	public void setCpChargeid(Long cpChargeid) {
		this.cpChargeid = cpChargeid;
	}
	/**
	 * 获取：负责人id
	 */
	public Long getCpChargeid() {
		return cpChargeid;
	}
	/**
	 * 设置：处理时间
	 */
	public void setCpDealtime(LocalDateTime cpDealtime) {
		this.cpDealtime = cpDealtime;
	}
	/**
	 * 获取：处理时间
	 */
	public LocalDateTime getCpDealtime() {
		return cpDealtime;
	}
	/**
	 * 设置：处理原因
	 */
	public void setCpDealreason(String cpDealreason) {
		this.cpDealreason = cpDealreason;
	}
	/**
	 * 获取：处理原因
	 */
	public String getCpDealreason() {
		return cpDealreason;
	}

	public Strelation getStrelation() {
		return strelation;
	}

	public void setStrelation(Strelation strelation) {
		this.strelation = strelation;
	}
}
