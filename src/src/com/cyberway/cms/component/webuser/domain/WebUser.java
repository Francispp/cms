package com.cyberway.cms.component.webuser.domain;

// default package

import java.util.Date;

/**
 * WebUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class WebUser implements java.io.Serializable {
	
	public final static String WEB_USER_IN_SESSION = "webuser";
	
	private Long oid;
	private String name;
	private String nickname;//昵称
	private String loginname;
	private String loginpwd;
	private String uloginpwd;//是否修改密码
	private String state;
	private String sex;
	private String email;
	private String phone;
	private String mobilephone;//手机
	private String country;//国家
	private String province;//省份
	private String city;//城市
	private Long recommendid;
	private String weixinhao;//微信号
	private String address;//详细地址
	private Long groupid;
	private String groupname;
	private String zhengjian;//证件号码
	private String position;//职位
	private String education;//教育程度
	private String married;//婚否
	private String family;//家庭人数
	private String income;//家庭收入
	private String children;//子女数量
	private String houseType;//房屋类型
	private Integer room;//室
	private Integer ting;//厅
	private String ownerType;//房屋拥有
	private String houseArea;//房屋面积
	private String habit;//爱好
	private String favorite;//喜欢的电视节目
	private Date createtime;
	private Date logintime;
	private Date birthday;//生日
	private String headerPic;//头像路径
	private String qq;
	private String emailPwdUUId;//发送uuId到用户邮箱
	private Date emailSentDate;//是否验证通过
	private Boolean approved;
	private Long siteId;
	private String siteName;
	private String zhengjianprovince;
	private String juzhuprovince;
	private String juzhucity;
	private Boolean isInternal;
	

	private Long deptid; //部门ID
    private String deptname; //部门名称
    private String empcode; //员工号
    
    private String jobLevel; //职位级别
    private String jobLevelName; //职级名称
    
	
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getLoginpwd() {
		return loginpwd;
	}
	public void setLoginpwd(String loginpwd) {
		this.loginpwd = loginpwd;
	}
	public String getUloginpwd() {
		return uloginpwd;
	}
	public void setUloginpwd(String uloginpwd) {
		this.uloginpwd = uloginpwd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Long getRecommendid() {
		return recommendid;
	}
	public void setRecommendid(Long recommendid) {
		this.recommendid = recommendid;
	}
	public String getWeixinhao() {
		return weixinhao;
	}
	public void setWeixinhao(String weixinhao) {
		this.weixinhao = weixinhao;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getZhengjian() {
		return zhengjian;
	}
	public void setZhengjian(String zhengjian) {
		this.zhengjian = zhengjian;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMarried() {
		return married;
	}
	public void setMarried(String married) {
		this.married = married;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public Integer getRoom() {
		return room;
	}
	public void setRoom(Integer room) {
		this.room = room;
	}
	public Integer getTing() {
		return ting;
	}
	public void setTing(Integer ting) {
		this.ting = ting;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getHabit() {
		return habit;
	}
	public void setHabit(String habit) {
		this.habit = habit;
	}
	public String getFavorite() {
		return favorite;
	}
	public void setFavorite(String favorite) {
		this.favorite = favorite;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getLogintime() {
		return logintime;
	}
	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getHeaderPic() {
		return headerPic;
	}
	public void setHeaderPic(String headerPic) {
		this.headerPic = headerPic;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEmailPwdUUId() {
		return emailPwdUUId;
	}
	public void setEmailPwdUUId(String emailPwdUUId) {
		this.emailPwdUUId = emailPwdUUId;
	}
	public Date getEmailSentDate() {
		return emailSentDate;
	}
	public void setEmailSentDate(Date emailSentDate) {
		this.emailSentDate = emailSentDate;
	}
	public Boolean getApproved() {
		return approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	public String getHouseArea() {
		return houseArea;
	}
	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getZhengjianprovince() {
		return zhengjianprovince;
	}
	public void setZhengjianprovince(String zhengjianprovince) {
		this.zhengjianprovince = zhengjianprovince;
	}
	public String getJuzhuprovince() {
		return juzhuprovince;
	}
	public void setJuzhuprovince(String juzhuprovince) {
		this.juzhuprovince = juzhuprovince;
	}
	public String getJuzhucity() {
		return juzhucity;
	}
	public void setJuzhucity(String juzhucity) {
		this.juzhucity = juzhucity;
	}
	public Boolean getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(Boolean isInternal) {
		this.isInternal = isInternal;
	}
	public Long getDeptid() {
		return deptid;
	}
	public void setDeptid(Long deptid) {
		this.deptid = deptid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public String getEmpcode() {
		return empcode;
	}
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}
	public String getJobLevel() {
		return jobLevel;
	}
	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}
	public String getJobLevelName() {
		return jobLevelName;
	}
	public void setJobLevelName(String jobLevelName) {
		this.jobLevelName = jobLevelName;
	}
}