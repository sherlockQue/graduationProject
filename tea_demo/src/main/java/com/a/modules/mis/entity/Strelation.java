package com.a.modules.mis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 
 * 
 */

@TableName("strelation")
public class Strelation implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long stId;
	/**
	 * 
	 */
	private String stuId;
	/**
	 * 
	 */
	private String tuoId;
	/**
	 * 项目内容
	 */
	private String stMessage;
	/**
	 * 图片
	 */
	private String stImg;
	/**
	 * 年级
	 */
	private String stGrade;
	/**
	 * 学期
	 */
	private Integer stTerm;
	/**
	 * 活动时间
	 */
	private String stActime;
	/**
	 * 创建时间
	 */
	private LocalDateTime stApplytime;
	/**
	 * 负责人
	 */
	private Long stCheckone;
	/**
	 * 状态  0/1/2  待审核/通过/未通过
	 */
	private Integer stOneStatus;
	/**
	 * 审核时间
	 */
	private LocalDateTime stOneTime;
	/**
	 * 
	 */
	private Long stChecktwo;
	/**
	 * 
	 */
	private Integer stTwoStatus;
	/**
	 * 
	 */
	private LocalDateTime stTwoTime;

	/**
	 * 设置：
	 */
	public void setStId(Long stId) {
		this.stId = stId;
	}
	/**
	 * 获取：
	 */
	public Long getStId() {
		return stId;
	}
	/**
	 * 设置：
	 */
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	/**
	 * 获取：
	 */
	public String getStuId() {
		return stuId;
	}
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
	 * 设置：项目内容
	 */
	public void setStMessage(String stMessage) {
		this.stMessage = stMessage;
	}
	/**
	 * 获取：项目内容
	 */
	public String getStMessage() {
		return stMessage;
	}
	/**
	 * 设置：图片
	 */
	public void setStImg(String stImg) {
		this.stImg = stImg;
	}
	/**
	 * 获取：图片
	 */
	public String getStImg() {
		return stImg;
	}
	/**
	 * 设置：年级
	 */
	public void setStGrade(String stGrade) {
		this.stGrade = stGrade;
	}
	/**
	 * 获取：年级
	 */
	public String getStGrade() {
		return stGrade;
	}
	/**
	 * 设置：学期
	 */
	public void setStTerm(Integer stTerm) {
		this.stTerm = stTerm;
	}
	/**
	 * 获取：学期
	 */
	public Integer getStTerm() {
		return stTerm;
	}
	/**
	 * 设置：活动时间
	 */
	public void setStActime(String stActime) {
		this.stActime = stActime;
	}
	/**
	 * 获取：活动时间
	 */
	public String getStActime() {
		return stActime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setStApplytime(LocalDateTime stApplytime) {
		this.stApplytime = stApplytime;
	}
	/**
	 * 获取：创建时间
	 */
	public LocalDateTime getStApplytime() {
		return stApplytime;
	}
	/**
	 * 设置：负责人
	 */
	public void setStCheckone(Long stCheckone) {
		this.stCheckone = stCheckone;
	}
	/**
	 * 获取：负责人
	 */
	public Long getStCheckone() {
		return stCheckone;
	}
	/**
	 * 设置：状态  0/1/2  待审核/通过/未通过
	 */
	public void setStOneStatus(Integer stOneStatus) {
		this.stOneStatus = stOneStatus;
	}
	/**
	 * 获取：状态  0/1/2  待审核/通过/未通过
	 */
	public Integer getStOneStatus() {
		return stOneStatus;
	}
	/**
	 * 设置：审核时间
	 */
	public void setStOneTime(LocalDateTime stOneTime) {
		this.stOneTime = stOneTime;
	}
	/**
	 * 获取：审核时间
	 */
	public LocalDateTime getStOneTime() {
		return stOneTime;
	}
	/**
	 * 设置：
	 */
	public void setStChecktwo(Long stChecktwo) {
		this.stChecktwo = stChecktwo;
	}
	/**
	 * 获取：
	 */
	public Long getStChecktwo() {
		return stChecktwo;
	}
	/**
	 * 设置：
	 */
	public void setStTwoStatus(Integer stTwoStatus) {
		this.stTwoStatus = stTwoStatus;
	}
	/**
	 * 获取：
	 */
	public Integer getStTwoStatus() {
		return stTwoStatus;
	}
	/**
	 * 设置：
	 */
	public void setStTwoTime(LocalDateTime stTwoTime) {
		this.stTwoTime = stTwoTime;
	}
	/**
	 * 获取：
	 */
	public LocalDateTime getStTwoTime() {
		return stTwoTime;
	}

	@Override
	public String toString() {
		return "Strelation{" +
				"stId=" + stId +
				", stuId='" + stuId + '\'' +
				", tuoId='" + tuoId + '\'' +
				", stMessage='" + stMessage + '\'' +
				", stImg='" + stImg + '\'' +
				", stGrade='" + stGrade + '\'' +
				", stTerm=" + stTerm +
				", stActime='" + stActime + '\'' +
				", stApplytime=" + stApplytime +
				", stCheckone='" + stCheckone + '\'' +
				", stOneStatus=" + stOneStatus +
				", stOneTime=" + stOneTime +
				", stChecktwo='" + stChecktwo + '\'' +
				", stTwoStatus=" + stTwoStatus +
				", stTwoTime=" + stTwoTime +
				'}';
	}
}
