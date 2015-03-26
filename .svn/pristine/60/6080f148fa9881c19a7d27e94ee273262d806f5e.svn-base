package com.cyberway.cms.site.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.*;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.ServiceLocator;

/**
 * 站点公用操作的service
 * @author caiw
 *
 */
public class SiteCommonService extends HibernateEntityDao{

	private Map<Long,Long> docClickCount=new HashMap();//文档的点击数
	private Map<Long,Long> channelClickCount=new HashMap();//频道的点击数
	private Map<Long,Long> siteClickCount=new HashMap();//站点的访问数
	public final static int SITE_TYPE=1;
	public final static int CHANNEL_TYPE=2;
	public final static int DOC_TYPE=3;
	public final static int CHANNEL_DOC_TYPE=4;
	public final static int SITE_DOC_TYPE=5;
	
	/**
	 * 获得指定类型的点击数
	 * @param id
	 * @param type
	 * @return
	 */
	public Long getClickCount(Long id,int type){

		Long count=0l;
		if(id==null)//若为空，返回为0
			return count;		
		if(type==SITE_TYPE){//获得站点点击率
			count=((CmsSite)get(CmsSite.class, id)).getNumberOfClick();
			if(count==null)
				count=0l;
			if(siteClickCount.containsKey(id))
				count+=siteClickCount.get(id);
		}
		if(type==CHANNEL_TYPE){//获得频道点击率
			count=((Channel)get(Channel.class, id)).getNumberOfClick();
			if(count==null)
				count=0l;
			if(channelClickCount.containsKey(id))
				count+=channelClickCount.get(id);
		}
		if(type==DOC_TYPE){//获得文档点击率
			count=((CmsBaseDocument)get(CmsBaseDocument.class, id)).getNumberOfClick();
			if(count==null)
				count=0l;
			if(docClickCount.containsKey(id))
				count+=docClickCount.get(id);
		}	
		if(type==CHANNEL_DOC_TYPE){//获得频道下文档点击率总和   
			ChannelManagerService service=(ChannelManagerService) ServiceLocator.getBean("channelManagerService");
			//设置子频道
			List childChannels = new ArrayList ();
			childChannels = service.getChild(id,childChannels); 
			
			List<Long> vals = new ArrayList<Long>();
			vals.add(id);
			String args = "?"; 
			
			for (Object channel : childChannels) {
				args+=",?";
				vals.add(((Channel)channel).getId());
			}
			 
			List  list = this.find("SELECT SUM(numberOfClick) from CmsBaseDocument WHERE channel.id in("+args+")",vals.toArray()); 
			
			if(list.size()<=0)
				count=0l;  
		    count+= list.get(0) == null ? 0l : (Long)list.get(0);
		}	
		if(type==SITE_DOC_TYPE){//获得所有文档点击率总和
			List  list = this.find("SELECT SUM(numberOfClick) from CmsBaseDocument WHERE site.id = ?", new Object []{id}); 
			if(list.size()<=0)
				count=0l;  
			count+= list.get(0) == null ? 0l : (Long)list.get(0);
		}	
		return count;
	}
	/**
	 * 增加站点、频道、文档的点击数
	 * @param id
	 * @param type
	 */
	public void updateClickCount(Long id,int type){
		Long count=0l;
		if(type==SITE_TYPE){//站点点击数更新
			if(siteClickCount.containsKey(id))
				count=siteClickCount.get(id);
			
			count++;
			siteClickCount.put(id, count);	
			if(count.longValue()>=Constants.NUMBER_MAX_CLICK_COUNT)
			  updateSiteClickCount(id);
		}
		if(type==CHANNEL_TYPE){//频道点击数更新
			if(channelClickCount.containsKey(id))
				count=channelClickCount.get(id);
			count++;
			channelClickCount.put(id, count);
			if(count.longValue()>=Constants.NUMBER_MAX_CLICK_COUNT)
				  updateChannelClickCount(id);
		}
		if(type==DOC_TYPE){//文档点击数更新
			if(docClickCount.containsKey(id))
				count=docClickCount.get(id);
			count++;
			docClickCount.put(id, count);	
			if(count.longValue()>=Constants.NUMBER_MAX_CLICK_COUNT)
				updateDocumentClickCount(id);
		}
		//达到设置最大点击数时，更新到数据库中
		/*if(count.longValue()>=Constants.NUMBER_MAX_CLICK_COUNT){
			
		}*/
	} 
	/**
	 * 更新站点点击率
	 */
	private synchronized void updateSiteClickCount(Long id){
		String sql="UPDATE CMS_SITE SET NUMBEROFCLICK = NUMBEROFCLICK +? WHERE (OID = ?)";		
		executeSql(sql,id,siteClickCount.get(id));
		siteClickCount.remove(id);//重新计算
	}
	
	/**
	 * 频道点击率
	 * @param id
	 */
	private synchronized void updateChannelClickCount(Long id){
		String sql="UPDATE CMS_CHANNEL SET numberOfClick = numberOfClick +? WHERE (ID = ?)";		
		executeSql(sql,id,channelClickCount.get(id));
		channelClickCount.remove(id);//重新计算
	}
	/**
	 * 更新文档点击率
	 * @param id
	 */
	private synchronized void updateDocumentClickCount(Long id){
		String sql="UPDATE CMS_BASE_DOCUMENT SET numberOfClick = numberOfClick +? WHERE (ID = ?)";		
		executeSql(sql,id,docClickCount.get(id));
		docClickCount.remove(id);//重新计算
	}		
	/**
	 * 执行指定的sql
	 * @param sql
	 */
	private void executeSql(String sql,Long id,Long clickCount){
	    PreparedStatement statement = null;
		Connection con=getConnection();		
		try{
		  statement = con.prepareStatement(sql);
	      statement.setLong(1, clickCount);
	      statement.setLong(2, id);
	      statement.executeUpdate();
	     // siteClickCount.put(id, 0L);//重新计算
		} catch (SQLException e) {
		       e.printStackTrace();
		      } finally {
		    	  try{
		        statement.close();
		        con.close();
		    	  }catch(Exception e){}
		      }
	}
}
