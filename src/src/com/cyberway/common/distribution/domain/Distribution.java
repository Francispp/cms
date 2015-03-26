package com.cyberway.common.distribution.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 分发记录表
 * @author taoz
 *
 * 2011-10-30下午02:44:21
 */
public class Distribution implements Serializable {

    /** identifier field */
    private Long disId;

    private String disName;
    
    private Date disTime;
    
    private String disProple;
    /**
     * 分发类型  1,文档2,频道3,站点
     */
    private Integer disType;
    /**
     * 分发是否成功  0失败 1成功
     */
    private Integer status;
	public Long getDisId() {
		return disId;
	}
	public void setDisId(Long disId) {
		this.disId = disId;
	}
	public String getDisName() {
		return disName;
	}
	public void setDisName(String disName) {
		this.disName = disName;
	}
	public Date getDisTime() {
		return disTime;
	}
	public void setDisTime(Date disTime) {
		this.disTime = disTime;
	}
	public String getDisProple() {
		return disProple;
	}
	public void setDisProple(String disProple) {
		this.disProple = disProple;
	}
	public Integer getDisType() {
		return disType;
	}
	public void setDisType(Integer disType) {
		this.disType = disType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
    
    
}
