package com.cyberway.component.tag;

import java.io.PrintWriter;
import java.io.Writer;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.components.Component;

import com.cyberway.common.internal.MarkupWriterImpl;
import com.cyberway.common.service.MarkupWriter;
import com.cyberway.core.utils.StringUtil;
import com.opensymphony.xwork2.util.ValueStack;


/**
 * com.cyberway.component.tag.Pager
 *
 * @author Janice Yang
 *
 * @createTime 2012-2-23 下午3:28:41 
 *
 * @Description:
 *
 */
public class Pager extends Component {
	private int	   _pageIndex	         = 1;
	private int	   _pageSize	         = 10;
	private int	   _recordCount;
	private int	   _maxDisplayPageNumber	= 10;
	private String	_style;

	public Pager(ValueStack stack) {
		super(stack);
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}

		if (pageIndex > getPageCount()) {
			pageIndex = getPageCount();
		}

		_pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 0) {
			pageSize = 0;
		}

		_pageSize = pageSize;
	}

	public void setRecordCount(int recordCount) {
		if (recordCount < 0) {
			recordCount = 0;
		}

		_recordCount = recordCount;
	}

	public void setMaxDisplayPageNumber(int maxDisplayPageNumber) {
		_maxDisplayPageNumber = maxDisplayPageNumber;
	}

	public int getPageCount() {
		int recordCount = Math.max(0, _recordCount);
		int pageSize = Math.max(1, _pageSize);
		int pageCount = 0;

		if (recordCount > 0) {
			if (pageSize >= recordCount) {
				pageCount = 1;
			} else {
				if ((recordCount % pageSize) > 0) {
					pageCount = (recordCount / pageSize) + 1;
				} else {
					pageCount = recordCount / pageSize;
				}
			}
		}

		return pageCount;
	}

	public int getFirstDisplayPageNumber() {
		int first = _maxDisplayPageNumber % 2 > 0 ? _maxDisplayPageNumber / 2 : (_maxDisplayPageNumber / 2) - 1;
		if (_pageIndex + first > getPageCount()) {
			first = (_pageIndex - ((_pageIndex + first) - getPageCount())) - first;
			if ((_maxDisplayPageNumber % 2) == 0) {
				first--;
			}
		} else {
			first = _pageIndex - first;
		}

		first = Math.max(1, first);

		return first;
	}

	public int getLastDisplayPageNumber() {
		int last = (getFirstDisplayPageNumber() - 1) + _maxDisplayPageNumber;

		if (last > getPageCount()) {
			last = getPageCount();
		}

		return last;
	}

	@Override
	public boolean end(Writer out, String body) {
		String css_pix = "list_";
		if (!StringUtil.isEmpty(_style)) {
			css_pix = _style;
		}
		if (getPageCount() > 1) {
			MarkupWriter writer = new MarkupWriterImpl();
			String fieldId = RandomStringUtils.randomNumeric(10);
			
			
			writer.element("div", "class", "pag-box");
			//writer.element("input", "type", "hidden", "id", fieldId, "name", "myTable_p", "value", _pageIndex);
			//writer.end();
			writer.element("div", "class", "pag");
			writer.element("div", "class", "pag-left");
			writer.write("第" + _pageIndex + "页  共" + getPageCount() + "页  跳转到 ");
			writer.element("input", "type", "text","id", fieldId, "name", "myTable_p", "value", _pageIndex);
			writer.end();
			writer.end();
			writer.element("div", "class", "pag-right");
			writer.element("ul");
			writer.element("li");
			writer.element("a", "href", String.format("javascript:gotoPage1(%s,'%s');", 1, fieldId));
			writer.write("首页");
			writer.end();
			writer.end();
			if (_pageIndex > 1) {
				writer.element("li");
				writer.element("a", "href", String.format("javascript:gotoPage1(%s,'%s');", (_pageIndex - 1), fieldId));
				writer.write("上一页");
				writer.end();
				writer.end();
			}
			int _index = 1;// 显示页的起始数
			int vCount = 5;// 显示数
			if (getPageCount() > vCount) {// 总数大于显示数
				if (_pageIndex + 5 <= getPageCount()) {
					_index = _pageIndex;
				} else {// 显示最后5页
					_index = getPageCount() - vCount + 1;
				}
				if (_index < 1)
					_index = 1;
			}
			// 输出后5页
			for (int i = 0; i < vCount && _index + i <= getPageCount(); i++) {
				if ((_index + i) == _pageIndex) {
					writer.element("li", "id", "hover");
					writer.element("a", "href", "#");
					writer.write(String.valueOf(_index + i));
					writer.end();
					writer.end();
				} else {
					writer.element("li");
					writer.element("a", "href", String.format("javascript:gotoPage1(%s,'%s');", (_index + i), fieldId));
					writer.write(String.valueOf(_index + i));
					writer.end();
					writer.end();
				}
			}
			if (_pageIndex + 1 <= getPageCount()) {
				writer.element("li");
				writer.element("a", "href", String.format("javascript:gotoPage1(%s,'%s');", (_pageIndex + 1), fieldId));
				
				writer.write("下一页");
				writer.end();
				writer.end();
			}
			writer.element("li");
			writer.element("a", "href", String.format("javascript:gotoPage1(%s,'%s');", getPageCount(), fieldId));
			writer.write("末页");
			writer.end();
			writer.end();
			writer.end();
			writer.end();
			writer.end();
			writer.end();
			
			writer.toMarkup(new PrintWriter(out, true));
		}

		return super.end(out, "");
	}

	public String getStyle() {
		return _style;
	}

	public void setStyle(String style) {
		this._style = style;
	}
}
