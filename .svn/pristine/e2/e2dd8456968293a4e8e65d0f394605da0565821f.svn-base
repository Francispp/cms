package com.cyberway.common.lucene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.store.Directory;

import com.cyberway.cms.Constants;
import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.permission.service.CmsPermissionService;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.core.objects.Loginer;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.opensymphony.xwork2.ActionContext;

public class SearchService
{
	private Directory _directory;
	private Analyzer _analyzer = new StandardAnalyzer ();
	private  static Logger _log = Logger.getLogger(SearchService.class);
	
	public Directory getDirectory()
	{
		return _directory;
	}

	public void setDirectory(Directory directory)
	{
		try { _directory.close(); } catch (Exception e) {}
		
		_directory = directory;
	}

	public Analyzer getAnalyzer()
	{
		return _analyzer;
	}

	public void setAnalyzer(Analyzer analyzer)
	{
		_analyzer = analyzer;
	}

	public Collection<Document> searchAll ()
	{
		IndexReader reader = null;
		List<Document> result = new ArrayList<Document> ();
		
		try
		{
			reader = IndexReader.open(getDirectory());
			for (int index = 0; index < reader.maxDoc(); index++)
			{
				try
				{
					result.add(reader.document(index));
				}
				catch (Exception e) {}
			}
			
			return result;
		}
		catch (Exception e)
		{
			throw new RuntimeException (e);
		}
		finally
		{
			try { reader.close(); } catch (Exception e) {}
		}
	}
	
	/**
	 * 根据频道ID进行全文搜索
	 */
	public Collection<Document> search (String siteId,String queryString,String keyword, Long channelId)
	{
		if(queryString.indexOf("channel")==-1){
			List<Long> list_ = new ArrayList<Long>();
			ChannelManagerService channelManagerService=(ChannelManagerService) ServiceLocator.getBean("channelManagerService");
			Channel ch = channelManagerService.getChannelFromCache(channelId);
			initChildChannelIds(Long.parseLong(siteId), ch, list_);
			StringBuffer buffer = new StringBuffer(" AND (");
			for (int i = 0; i < list_.size(); i++) {
				Long l = list_.get(i);
				if(i>0){
					buffer.append(" OR ");
				}
				buffer.append("channel:").append(l);
				
			}
			buffer.append(")");
			queryString += buffer.toString();
		}
		return search(siteId, queryString, keyword);
	}
	
	/**
	 * 获取所以子频道ID
	 * @param siteId
	 * @param channelId
	 * @param list_
	 */
	private void initChildChannelIds(Long siteId, Channel ch, List<Long> list_){
		if(new Long(1).equals(ch.getIsSearch())){
			list_.add(ch.getId());
		}
		ChannelManagerService channelManagerService=(ChannelManagerService) ServiceLocator.getBean("channelManagerService");
		List<Channel> list0 = channelManagerService.findByParentInSameSite(siteId, ch.getId());
		for (Channel channel : list0) {
			initChildChannelIds(siteId, channel, list_);
		}
	}
	
	/**
	 * 搜索指定站点下的满足条件的内容
	 * @param queryString
	 * @return
	 */
	public Collection<Document> search (String siteId,String queryString,String keyword)
	{
		IndexSearcher searcher = null;
		try
		{
			searcher = new IndexSearcher (getDirectory()); 
			List<Document> result = new ArrayList<Document> ();
			//未指定站点
			if(StringUtils.isNotBlank(keyword)){
				//在was 6下不用 tomcat下需转换
				//_log.info("未转码:"+keyword);
				if(isMessyCode(keyword)){
					keyword=StringUtil.toUTF8(keyword);
				}
				//_log.info("转码后:"+keyword);
				keyword=queryString+" AND "+keyword;
			}else{
				keyword=queryString;
			}
			MultiFieldQueryParser parser = null;
			
			_log.info(keyword);
			
			parser=new MultiFieldQueryParser (new String[] { "title", "body", "filebody"}, getAnalyzer());
			SortField sortF = new SortField("id", SortField.STRING, true);
			Sort sort = new Sort(sortF);
			Hits hits = searcher.search(parser.parse(keyword),sort);
			//增加权限过滤　amway
			Loginer loginer =(Loginer)ActionContext.getContext().getSession().get(com.cyberway.core.Constants.USER_IN_SESSION);
			boolean isopen = false;
			CmsPermissionService permService=(CmsPermissionService)ServiceLocator.getBean("cmsPermissionService");
			SiteManagerService siteService=(SiteManagerService)ServiceLocator.getBean("siteManagerService");
			if(StringUtils.isNotEmpty(siteId))
			{
				CmsSite site = siteService.getSiteFromCache(Long.valueOf(siteId));
				isopen = (site.getIsLogined()!=null && site.getIsLogined()==1) ? false : true;
				if(!isopen && loginer==null){
					return result;
				}
			}
			Iterator iterator = hits.iterator();
			while (iterator.hasNext())
			{
				Hit hit = (Hit)iterator.next();
				Document doc=hit.getDocument();
				String docid=doc.get("id");
				String title=doc.get("title");
				if(!StringUtil.isEmpty(docid)&&StringUtil.ifEqual(doc.get("issued"),"5")&& !StringUtil.isEmpty(title)){
					//检测权限
					if(isopen || permService.haveThePermission(loginer, "CMS_DOCUMENT_VIEW",Constants.DOCUMENT_TYPE, new Long(docid))){
						 result.add(doc);
					}				
				}
			}
			return result;
		}
		catch (Exception e)
		{
			throw new RuntimeException (e);
		}
		finally
		{
			try { searcher.close(); } catch (Exception e) {}
		}
	}	
	/**
	 * 搜索指定关键字的
	 * @param queryString
	 * @return
	 */
	public Collection<Document> search (String queryString)
	{
		IndexSearcher searcher = null;
		
		try
		{
			searcher = new IndexSearcher (getDirectory()); 
			List<Document> result = new ArrayList<Document> ();
			MultiFieldQueryParser parser = new MultiFieldQueryParser (new String[] { "title", "body", "authorCname", "timeCreated" }, getAnalyzer());
			Hits hits = searcher.search(parser.parse(queryString));
			
			Iterator iterator = hits.iterator();
			while (iterator.hasNext())
			{
				Hit hit = (Hit)iterator.next();
				result.add(hit.getDocument());
			}
			
			return result;
		}
		catch (Exception e)
		{
			throw new RuntimeException (e);
		}
		finally
		{
			try { searcher.close(); } catch (Exception e) {}
		}
	}
	
	

	  /*在不同的操作系统中或环境中存在中文乱码，此方法判断是否为乱码---此方法经过测试中【二】字将不做乱码处理*/
	  /*
	   public static boolean isMessyCode(String strName) {  
	    Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");  
	    Matcher m = p.matcher(strName);  
	    String after = m.replaceAll("");  
	    String temp = after.replaceAll("\\p{P}", "");  
	    char[] ch = temp.trim().toCharArray();  
	    float chLength = ch.length;  
	    float count = 0;  
	    for (int i = 0; i < ch.length; i++) {  
	      char c = ch[i];  
	      if (!Character.isLetterOrDigit(c)) {  
	  
	        if (!isChinese(c)) {  
	          count = count + 1;  
	          System.out.print(c);  
	        }  
	      }  
	    }  
	    float result = count / chLength;  
	    if (result > 0.4) {  
	      return true;  
	    } else {  
	      return false;  
	    }  
	  }  
	  */
	  /*在不同的操作系统中或环境中存在中文乱码，此方法判断是否为乱码*/
	  public static boolean isMessyCode(String strName) {
		          char[] ch = strName.toCharArray();
		          for (int i = 0; i < ch.length; i++) {
		              char c = ch[i];
		              if (isChinese(c) == true) {
		                  return false;
		              } else {
		                  return true;
		              }
		          }
		          return false;
		    
		      }   

	  
  	 public static boolean isChinese(char c) {  
   	    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
   	    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
   	        || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
   	        || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
   	        || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
   	        || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
   	        || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
   	      return true;  
   	    }  
   	    return false;  
   	  }  
}
