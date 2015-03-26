package com.cyberway.cms.survey.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Questionnaire entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Questionnaire implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2856006777683765885L;
	
	private Long id;
	private String name;
	private String activated = "0";//是否可是否发布
	private Integer point;
	private Date createDate;
	private Date publishDate;
	private Date cutoffDate;
	private Long authorId;
	private String anonymity = "0";//是否可匿名
	private String remark;
	
	private String welcome;//欢迎词
	private String thanks;//欢迎词
	private Integer turnToPage=1;//提交后显示页
	
	private String styleFile;//文件名
	private String pic;//预览图
	
	private Long repetition;//多久可再投(分钟)
	
	//参与人数
	private int total;
	
	/**
	 * 所属站点
	 */
	private Long siteId;
	private String siteName;
	
	private List<Question> questions = new ArrayList<Question>();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPoint() {
		return this.point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Long getAuthorId() {
		return this.authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getActivated() {
		return activated;
	}

	public void setActivated(String activated) {
		this.activated = activated;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Date getCutoffDate() {
		return cutoffDate;
	}

	public void setCutoffDate(Date cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

	public String getAnonymity() {
		return anonymity;
	}

	public void setAnonymity(String anonymity) {
		this.anonymity = anonymity;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Questionnaire)
			return false;
		
		if(this.id == ((Questionnaire)obj).id)
			return true;
		else 
			return false;
	}

	public String getWelcome() {
		return welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public String getThanks() {
		return thanks;
	}

	public void setThanks(String thanks) {
		this.thanks = thanks;
	}

	public Integer getTurnToPage() {
		return turnToPage;
	}

	public void setTurnToPage(Integer turnToPage) {
		this.turnToPage = turnToPage;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public String getStyleFile() {
		return styleFile;
	}

	public void setStyleFile(String styleFile) {
		this.styleFile = styleFile;
	}

	public Long getRepetition() {
		return repetition;
	}

	public void setRepetition(Long repetition) {
		this.repetition = repetition;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}


}