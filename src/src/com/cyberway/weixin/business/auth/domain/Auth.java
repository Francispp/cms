package com.cyberway.weixin.business.auth.domain;

import java.util.Date;

import com.cyberway.weixin.business.department.domain.Department;

/**
 * 
 * @com.cyberway.weixin.business.auth.domain.Auth
 * TODO : 企业授权类
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月12日 下午4:16:13
 */
public class Auth {

	private Long id;
	/**
	 *授权方企业号id 
	 */
	private String corpId;
	/**
	 * 授权方企业号名
	 */
	private String corpName;
	/**
	 * 授权方企业号类型，认证号：verified, 注册号：unverified，体验号：test  
	 */
	private String corpType;
	/**
	 * 永久授权码
	 */
	private String  permanentCode;
	/**
	 * 授权方企业号圆形头像
	 */
	private String corp_round_logo_url;
	/**
	 * 授权方企业号方形头像
	 */
	private String corp_square_logo_url;
	/**
	 * 授权方企业号用户规模
	 */
	private Integer corp_user_max;
	/**
	 * 授权方企业号应用规模
	 */
	private Integer corp_agent_max;
	/**
	 * 授权方企业号二维码
	 */
	private String corp_wxqrcode;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 授权信息
	 */
	private AuthDetails authDetails;
	/**
	 * 授权的通讯录部门 
	 */
	private Department departmet;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getCorpType() {
		return corpType;
	}
	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}
	public String getPermanentCode() {
		return permanentCode;
	}
	public void setPermanentCode(String permanentCode) {
		this.permanentCode = permanentCode;
	}
	public String getCorp_round_logo_url() {
		return corp_round_logo_url;
	}
	public void setCorp_round_logo_url(String corp_round_logo_url) {
		this.corp_round_logo_url = corp_round_logo_url;
	}
	public String getCorp_square_logo_url() {
		return corp_square_logo_url;
	}
	public void setCorp_square_logo_url(String corp_square_logo_url) {
		this.corp_square_logo_url = corp_square_logo_url;
	}
	public Integer getCorp_user_max() {
		return corp_user_max;
	}
	public void setCorp_user_max(Integer corp_user_max) {
		this.corp_user_max = corp_user_max;
	}
	public Integer getCorp_agent_max() {
		return corp_agent_max;
	}
	public void setCorp_agent_max(Integer corp_agent_max) {
		this.corp_agent_max = corp_agent_max;
	}
	public String getCorp_wxqrcode() {
		return corp_wxqrcode;
	}
	public void setCorp_wxqrcode(String corp_wxqrcode) {
		this.corp_wxqrcode = corp_wxqrcode;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public AuthDetails getAuthDetails() {
		return authDetails;
	}
	public void setAuthDetails(AuthDetails authDetails) {
		this.authDetails = authDetails;
	}
	public Department getDepartmet() {
		return departmet;
	}
	public void setDepartmet(Department departmet) {
		this.departmet = departmet;
	}
	
}
