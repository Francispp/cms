package com.cyberway.cms.tags.components;

import java.io.PrintWriter;
import java.io.Writer;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.components.Component;

import com.cyberway.common.internal.MarkupWriterImpl;
import com.cyberway.common.service.MarkupWriter;
import com.opensymphony.xwork2.util.ValueStack;



public class TablePager extends Component
{
	private int _pageIndex = 1;
	private int _pageSize = 10;
	private int _recordCount;
	private int _maxDisplayPageNumber = 10;
	private String _style;
	private String _pageType;

	public TablePager(ValueStack stack)
	{
		super(stack);
	}
	
	public void setPageIndex(int pageIndex)
	{
		if (pageIndex < 1)
		{
			pageIndex = 1;
		}
		
		if (pageIndex > getPageCount())
		{
			pageIndex = getPageCount();
		}
		
		_pageIndex = pageIndex;
	}
	
	public void setPageSize(int pageSize)
	{
		if (pageSize < 0)
		{
			pageSize = 0;
		}
		
		_pageSize = pageSize;
	}

	public void setRecordCount(int recordCount)
	{
		if (recordCount < 0)
		{
			recordCount = 0;
		}
		
		_recordCount = recordCount;
	}

	public void setMaxDisplayPageNumber(int maxDisplayPageNumber)
	{
		_maxDisplayPageNumber = maxDisplayPageNumber;
	}
	
	public int getPageCount ()
	{
		int recordCount = Math.max(0, _recordCount);
		int pageSize = Math.max(1, _pageSize);
		int pageCount = 0;

		if (recordCount > 0)
		{
			if (pageSize >= recordCount)
			{
				pageCount = 1;
			}
			else
			{
				if ((recordCount % pageSize) > 0)
				{
					pageCount = (recordCount / pageSize) + 1;
				}
				else
				{
					pageCount = recordCount / pageSize;
				}
			}
		}

		return pageCount;
	}
	
	public int getFirstDisplayPageNumber ()
	{
		int first = _maxDisplayPageNumber % 2 > 0 ? _maxDisplayPageNumber / 2 : (_maxDisplayPageNumber / 2) - 1;
		if (_pageIndex + first > getPageCount())
		{
			first = (_pageIndex - ((_pageIndex + first) - getPageCount())) - first;
			if ((_maxDisplayPageNumber % 2) == 0)
			{
				first--;
			}
		}
		else
		{
			first = _pageIndex - first;
		}

		first = Math.max (1, first);
		
		return first;
	}
	
	public int getLastDisplayPageNumber ()
	{
		int last = (getFirstDisplayPageNumber() - 1) + _maxDisplayPageNumber;
		
		if (last > getPageCount())
		{
			last = getPageCount();
		}
		
		return last;
	}
	
	@Override
	public boolean end(Writer out, String body)
	{
		if (getPageCount() > 1)
		{
			MarkupWriter writer = new MarkupWriterImpl ();
			String fieldId = RandomStringUtils.randomNumeric(10);
			/** 康佳默认翻页
			 <div class="pageBox">
            	<a href="#" class="firstBtn">首页</a>
                <a href="#" class="preBtn">上一页</a>
                <span class="pagesItem pageCur">1</span>
                <a href="#" class="pagesItem">2</a>
        		<a href="#" class="pagesItem">3</a>
                <a href="#" class="pagesItem">4</a>
        		<a href="#" class="pagesItem">5</a>
       			<a href="#" class="nextBtn">下一页</a>
                <a href="#" class="lastBtn">末页</a>
            </div>
			 */
			if("product".equals(_pageType)){
				writer.element("div",  "class", "curPage");
				writer.element("input", "type", "hidden", "id", fieldId, "name", "pageIndex", "value", _pageIndex);
				writer.end();
				writer.element("ul");
				
				int _index=1;//显示页的起始数
				int vCount=getPageCount();//显示数
				
				//输出后5页
				for(int i=0;i<vCount;i++){
					if((_index+i)==_pageIndex){
						writer.element("li");
						writer.element("a", "class", "hover", "href",  String.format("javascript:gotoPage(%s,'%s');", (_index+i), fieldId));
						//writer.write(String.valueOf(_index+i));
						writer.end();
						writer.end();
						
					}else{
						writer.element("li");
						writer.element("a", "href",  String.format("javascript:gotoPage(%s,'%s');", (_index+i), fieldId));
						//writer.write(String.valueOf(_index+i));
						writer.end();
						writer.end();
						
					}
				}
				writer.end();
				writer.end();
			}else{
				//默认风格
				writer.element("div",  "class", "pageBox");
				writer.element("input", "type", "hidden", "id", fieldId, "name", "pageIndex", "value", _pageIndex);
				writer.end();
				if (_pageIndex > 1 ){
					writer.element("a", "class", "firstBtn", "href",  String.format("javascript:gotoPage(%s,'%s');", 1, fieldId));
					writer.write("首页");
					writer.end();
					writer.write("\n");
					writer.element("a", "class", "preBtn", "href",  String.format("javascript:gotoPage(%s,'%s');", (_pageIndex-1), fieldId));
					writer.write("上一页");
					writer.end();
					writer.write("\n");
				}
				int _index=1;//显示页的起始数
				int vCount=5;//显示数
				if(getPageCount()>vCount){//总数大于显示数
					if(_pageIndex+3<=getPageCount()){
						_index=_pageIndex;
					}else{//显示最后5页
						_index=getPageCount()-vCount+1;
					}
					if(_index<1)
						_index=1;
				}
				//输出后5页
				for(int i=0;i<vCount && _index+i<=getPageCount();i++){
					if((_index+i)==_pageIndex){
						writer.element("span", "class", "pagesItem pageCur");
						writer.write(String.valueOf(_index+i));
						writer.end();
						writer.write("\n");
					}else{
						writer.element("a", "class", "pagesItem", "href",  String.format("javascript:gotoPage(%s,'%s');", (_index+i), fieldId));
						writer.write(String.valueOf(_index+i));
						writer.end();
						writer.write("\n");
					}
				}
				if (_pageIndex +1<= getPageCount()){
					writer.element("a", "class", "nextBtn", "href",  String.format("javascript:gotoPage(%s,'%s');", (_pageIndex +1), fieldId));
					writer.write("下一页");
					writer.end();
					writer.write("\n");
					writer.element("a", "class", "lastBtn", "href",  String.format("javascript:gotoPage(%s,'%s');", getPageCount(), fieldId));
					writer.write("尾页");
					writer.end();
					writer.write("\n");
				}
				//输出
				/*writer.element("span");
				writer.write(" 共"+_recordCount+"条，共"+getPageCount()+"页");
				writer.end();*/
				
				writer.end();
			}
			writer.toMarkup(new PrintWriter (out, true));
		}
		return super.end(out, "");
	}

	public String getStyle() {
		return _style;
	}

	public void setStyle(String style) {
		this._style = style;
	}

	public String getPageType() {
		return _pageType;
	}

	public void setPageType(String type) {
		_pageType = type;
	}
	
	
}
