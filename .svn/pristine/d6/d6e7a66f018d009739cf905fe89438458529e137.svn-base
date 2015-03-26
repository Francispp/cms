/*
 * 创建日期 2007-5-16
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package com.cyberway.common;

/**
 * DB2方言类，继承Hibernate的方言类，重写getLimitString方法，修改不可用with ur的问题 <br>
 * @author 王瑛<br>
 * @version Version 1.00<br>
 */
public class DB2Dialect extends org.hibernate.dialect.DB2Dialect{
	public DB2Dialect(){
		
	}

	/**
	 * 重写方法，解决hibnernate不能用with ur的问题
	 */
	public String getLimitString(String sql, boolean hasOffset) {
		StringBuffer rownumber = new StringBuffer(50)
			.append(" rownumber() over(");
		int orderByIndex = sql.toLowerCase().indexOf("order by");
		
		int withUrIndex = sql.toLowerCase().indexOf("with ur");
		
		//如果sql有with ur,则去掉
		if(withUrIndex > 0){
			sql = sql.substring(0,withUrIndex);
		}
		
		if (orderByIndex>0) rownumber.append( sql.substring(orderByIndex) );
			
		rownumber.append(") as row_,");
		StringBuffer pagingSelect = new StringBuffer( sql.length()+100 )
			.append("select * from ( ")
			.append(sql)
			.insert( getAfterSelectInsertPoint(sql), rownumber.toString() )
			.append(" ) as temp_ where row_ ");
		if (hasOffset) {
			pagingSelect.append("between ?+1 and ?");
		}
		else {
			pagingSelect.append("<= ?");
		}
		
		//if(withUrIndex > 0){
			pagingSelect.append(" with ur ");
		//}
		return pagingSelect.toString();
	}
	
	
	private static int getAfterSelectInsertPoint(String sql) {
		return 16 + ( sql.startsWith("select distinct") ? 15 : 6 );
	}
}
