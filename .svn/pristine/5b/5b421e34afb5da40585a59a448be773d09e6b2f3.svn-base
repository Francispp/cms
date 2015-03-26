package com.cyberway.core.objects;

import java.io.Serializable;
import java.util.List;

import com.cyberway.core.utils.StringUtil;

/**
 * 门户信息
 * @author caiw
 *
 */
public class Portal implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3256440313546683697L;

	/**
	 * 
	 */
	public static final String PORTALINFO_SESSION="portal";
	
	private String portalid;
	private String cname;//中文名称
	private String portalcode;//站点代码
	private String deptids;
	private List style;//样式列表
	private Style curStyle;//当前样式
	private Integer styleid;//当前样式序号
	
	public String getCname() {
		return cname;
	}
	

	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public List getStyle() {
		return style;
	}

	public void setStyle(List style) {
		this.style = style;
	}
	
	public Style getStyleInstance(){
		return new Style();
	}

	public Style getCurStyle() {
		if(styleid==null) styleid=new Integer(0);
		if(style!=null) 
			return (Style)style.get(styleid.intValue());
		
		return curStyle;
	}
	
	public void setCurStyle(Style curStyle) {
		this.curStyle = curStyle;
	}
	
	public Integer getStyleid() {
		return styleid;
	}
	
	public void setStyleid(Integer styleid) {
		this.styleid = styleid;
	}
	

	/**
	 * @author libin
	 */
	public class Style {
		private String css;
		private String imgpath;
		private String template;
		
		public String getCss() {
			return css;
		}
		
		public void setCss(String css) {
			this.css = css;
		}
		
		public String getImgpath() {
			return imgpath;
		}
		
		public void setImgpath(String imgpath) {
			this.imgpath = imgpath;
		}
		
		public String getTemplate() {
			return template;
		}
		
		public void setTemplate(String template) {
			this.template = template;
		}
		
	}


	public String getPortalcode() {
		return portalcode;
	}


	public void setPortalcode(String portalcode) {
		this.portalcode = portalcode;
	}


	public String getDeptids() {
		return deptids;
	}


	public void setDeptids(String deptids) {
		this.deptids = deptids;
	}	
	//取得按门户编号过滤的条件
    public String getPortalWhereByCode(String fieldName){
    	String where="";
    	//若为空或为*,不加限制条件
    	if(StringUtil.isEmpty(portalcode)||portalcode.endsWith("*"))
    		return where;
    	
    	if(!StringUtil.isEmpty(fieldName)){
    		where=" and "+fieldName+"='"+portalcode.trim()+"' ";
    	}else
    		where=" and pcode='"+portalcode.trim()+"' ";
    	return where;
    }
	//取得按门户编号过滤的条件
    public String getPortalWhereByCode(){
    	return getPortalWhereByCode("pcode");
    }


	public String getPortalid() {
		return portalid;
	}


	public void setPortalid(String portalid) {
		this.portalid = portalid;
	}   
}
