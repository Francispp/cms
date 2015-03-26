package com.cyberway.component.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;


/**
 * com.cyberway.component.tag.PagerTag
 *
 * @author Janice Yang
 *
 * @createTime 2012-2-23 下午3:28:32 
 *
 * @Description:
 *
 */
public class PagerTag extends ComponentTagSupport {
	private String	_pageIndex;
	private String	_pageSize;
	private String	_recordCount;
	private String	_style;

	public void setPageIndex(String pageIndex) {
		_pageIndex = pageIndex;
	}

	public void setPageSize(String pageSize) {
		_pageSize = pageSize;
	}

	public void setRecordCount(String recordCount) {
		_recordCount = recordCount;
	}

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new Pager(stack);
	}

	@Override
	protected void populateParams() {
		super.populateParams();

		Pager component = (Pager) getComponent();

		component.setRecordCount((Integer) findValue(_recordCount, Integer.class));

		if (!StringUtils.isBlank(_pageSize)) {
			component.setPageSize((Integer) findValue(_pageSize, Integer.class));
		}
		if (!StringUtils.isBlank(_pageIndex)) {
			component.setPageIndex((Integer) findValue(_pageIndex, Integer.class));
		}
		component.setStyle(_style);
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

	public void setStyle(String style) {
		this._style = style;
	}
}
