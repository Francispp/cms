package com.cyberway.staticer.util;

/**
 * 页面采集
 * 
 * @author Frank
 * 
 */
public class TagGatherUtils {
	/**
	 * 列表
	 */
	public static String getListTag() {
		StringBuilder sb = new StringBuilder();
		sb.append("<%{");
		sb.append("Page listPage=(Page)request.getAttribute (\"_data\");");
		sb.append("int listDataSize=Integer.parseInt(String.valueOf(request.getAttribute (\"_data_size\")));");
		sb.append("int listTotalPage =listDataSize%listPage.getPageSize()==0?listDataSize+listPage.getPageSize():listDataSize/listPage.getPageSize()+1;");
		sb.append("if(VariableCollector.isGatherPhase(request))");
		sb.append("{");
		sb.append("VariableCollector listCollector = new VariableCollector(response);");
		sb.append("List<String> listValues = new ArrayList<String>();");
		sb.append("for (int pageNo = 1; pageNo <= listTotalPage; pageNo++)");
		sb.append("{");
		sb.append("listValues.add(String.valueOf(pageNo));");
		sb.append("}");
		sb.append("listCollector.collect(\"pageIndex\", (String[])listValues.toArray(new String[listValues.size()]));");
		sb.append("}");
		sb.append("}%>");
		return sb.toString();
	}

	/**
	 * 首页列表
	 */
	public static String getIndexListTag(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append("<%");
		sb.append("Page indexPage=(Page)request.getAttribute (\"_data"+id+"\");");
		sb.append("int indexDataSize=Integer.parseInt(String.valueOf(indexPage.getTotalCount()));");
		sb.append("int indexTotalPage =indexDataSize%indexPage.getPageSize()==0?indexDataSize/indexPage.getPageSize():indexDataSize/indexPage.getPageSize()+1;");
		sb.append("if(VariableCollector.isGatherPhase(request))");
		sb.append("{");
		sb.append("VariableCollector indexCollector = new VariableCollector(response);");
		sb.append("List<String> indexValues = new ArrayList<String>();");
		sb.append("for (int pageNo = 1; pageNo <= indexTotalPage; pageNo++)");
		sb.append("{");
		sb.append("indexValues.add(String.valueOf(pageNo));");
		sb.append("}");
		sb.append("indexCollector.collect(\"pageIndex\", (String[])indexValues.toArray(new String[indexValues.size()]));");
		sb.append("}");
		sb.append("%>");
		return sb.toString();
	}
}
