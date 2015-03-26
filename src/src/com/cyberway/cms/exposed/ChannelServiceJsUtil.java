package com.cyberway.cms.exposed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.form.object.BaseDocument;
import com.cyberway.core.utils.ServiceLocator;
/**
 * @author caiw	
 * 频道对外接口
 *
 */
public class ChannelServiceJsUtil {
 
	private ChannelManagerService channelManagerService;
	
	private DocumentCommonService documentCommonService;
	
	List<Channel> parentlist = null;

	public DocumentCommonService getDocumentCommonService() {
		return documentCommonService;
	}

	public void setDocumentCommonService(DocumentCommonService documentCommonService) {
		this.documentCommonService = documentCommonService;
	}

	public ChannelManagerService getChannelManagerService() {
		return channelManagerService;
	}

	public void setChannelManagerService(ChannelManagerService channelManagerService) {
		this.channelManagerService = channelManagerService;
	}
	/**
	 * 获得频道对象
	 * @param channelid
	 * @return
	 */
	public Channel getChannel(Long channelid){
		return channelManagerService.getChannelFromCache(channelid);
	}
	public Channel getChannel(String channelid){
		return channelManagerService.getChannelFromCache(Long.valueOf(channelid));
	}
	
	
	//根据父id得到所有的子频道
	public List getChannlInfoByChannlId(String cid){
		System.out.println("###################################################################");
		if(cid==null){
			 return new ArrayList();
		}
        
        List list=selectChildlist(Long.valueOf(cid));
       
	    return list;
	}
/**
	 * 递归查询子级
	**/
	public List selectChildlist(Long cid)
	{
		List<Channel> clist=new ArrayList<Channel>();
		ChannelManagerService channelManagerService = (ChannelManagerService)ServiceLocator.getBean ("channelManagerService");
		//根据父id账号 找到
		
		List<Channel> list = channelManagerService.findByParent(cid);
		System.out.println(list);
		if(list.size()>0){
			for(int i=0;i<list.size();i++)
			{
				Channel c=list.get(i);
				
				clist.addAll(selectChildlist(c.getId()));
			}
			
		}else{
			clist.add(channelManagerService.getById(cid));
			return clist;
		}
		
		return clist;
		
	}
	

	

	
	
	/**
	 * 通过channelid或者docid获取CHANNEL
	 */
	public Channel getChannel(String channelid,String docid){
		Channel channel = new Channel();
		if(StringUtils.isNotEmpty(channelid))
		{
			channel = channelManagerService.getChannelFromCache(Long.valueOf(channelid));
		}
		else if(StringUtils.isNotEmpty(docid))
		{
			try{
				BaseDocument document = (BaseDocument)documentCommonService.getDocument(Long.valueOf(docid));
				
				if(document!=null)
				{
				Long channel_id = document.getChannel().getId();
				channel = channelManagerService.getSiteCache().getChannelFromCach(channel_id);
				}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		}
	return channel;
	}
	/**
	 * 获得指定站点下所有频道
	 * @param siteid
	 * @return
	 */
	public List<Channel> getChannelsBySite(Long siteid){
		return channelManagerService.getChannelsBySite(siteid);
	}	
	/**
	 * 获取当前导航
	 * @param channelid
	 * @return
	 */
	public String getSuperChannels(String channelid,String docid)
	{
		Channel channel = null;
		if(StringUtils.isNotEmpty(channelid))
		{
			channel = channelManagerService.get(channelid);
		}
		else if(StringUtils.isNotEmpty(docid))
		{
			try{
			BaseDocument document = (BaseDocument)documentCommonService.getDocument(Long.valueOf(docid));
			
			if(document!=null)
			{
			Long channel_id = document.getChannel().getId();
			channel = channelManagerService.getSiteCache().getChannelFromCach(channel_id);
			}
			}catch(Exception e)
			{
				e.printStackTrace();
				return "";
			}
		}
		if(channel == null)
		{
			return "";
		}
		//String navigation = "<a href='/'>首页</a>  -> ";
		String navigation = "首页  -> ";
		List channelList = new ArrayList();
		parentlist = new ArrayList<Channel>();
		channelList = this.getParent(channelManagerService.getSiteCache().getAllChannels(), channel);
		for(int i=channelList.size()-1; i>=0;i--)
		{
			Channel channelNext = (Channel) channelList.get(i);
			if(channelNext != null)
			//navigation += "<a href='/"+channelNext.getChannelPath()+"'>"+channelNext.getName()+"</a> -> ";
			navigation += channelNext.getName()+" -> ";
		}
		//navigation += "<a href='/"+channel.getChannelPath()+"'>"+channel.getName()+"</a>";
		navigation += channel.getName();
		return navigation;
	}
	/**
	 * 
	 * 判别是否带URL
	 * @param channelid
	 * @param docid
	 * @param isURL
	 * @return
	 */
	public String getSuperChannels(String channelid,String docid,Boolean isURL)
	{
		Channel channel = null;
		if(StringUtils.isNotEmpty(channelid))
		{
			channel = channelManagerService.getSiteCache().getChannelFromCach(Long.parseLong(channelid));
		}
		else if(StringUtils.isNotEmpty(docid))
		{
			try{
			BaseDocument document = (BaseDocument)documentCommonService.getDocument(Long.valueOf(docid));
			
			if(document!=null)
			{
			Long channel_id = document.getChannel().getId();
			channel = channelManagerService.getSiteCache().getChannelFromCach(channel_id);
			}
			}catch(Exception e)
			{
				e.printStackTrace();
				return "";
			}
		}
		if(channel == null)
		{
			return "";
		}
		 String navigation = (isURL)? "<a href='/' class='indexlink'>首页</a>  -> " : "首页  -> ";
		List channelList = new ArrayList();
		parentlist = new ArrayList<Channel>();
		channelList = this.getParent(channelManagerService.getSiteCache().getAllChannels(), channel);
		for(int i=channelList.size()-1; i>=0;i--)
		{
			Channel channelNext = (Channel) channelList.get(i);
			if(channelNext != null)
			navigation += (isURL)? "<a href='/"+channelNext.getChannelPath()+"'>"+channelNext.getName()+"</a> -> " :channelNext.getName()+" -> ";
		}
		navigation += (isURL)? "<a href='/"+channel.getChannelPath()+"' class='currlink'>"+channel.getName()+"</a>" : channel.getName();
		return navigation;
	}
	
	 /** 
	  * 判断首页连接的写法
	  * @param channelid
	  * @param docid
	  * @param isURL 
	  * @param target
	  * @param num
	  * @return
	  */
	public String getSuperChannels(String channelid,String docid,Boolean isURL,String leveltarget)
	{
		Channel channel = null;
		if(StringUtils.isNotEmpty(channelid))
		{
			channel = channelManagerService.getSiteCache().getChannelFromCach(Long.parseLong(channelid));
		}
		else if(StringUtils.isNotEmpty(docid))
		{
			try{
			BaseDocument document = (BaseDocument)documentCommonService.getDocument(Long.valueOf(docid));
			
			if(document!=null)
			{
			Long channel_id = document.getChannel().getId();
			channel = channelManagerService.getSiteCache().getChannelFromCach(channel_id);
			}
			}catch(Exception e)
			{
				e.printStackTrace();
				return "";
			}
		}
		if(channel == null)
		{
			return "";
		} 
		
		//获取频道目标信息
		String [] leveltargets = leveltarget.trim().split(","); 
		Map<String,String[]> leveltargetmap = new HashMap<String,String[]> ();
		for (String lt : leveltargets) {
			String[] temp = lt.split("-");
			leveltargetmap.put(temp[0], temp);
		}  
		
		String navigation = "";
		//频道集合中. 是否包含首页
		 
		navigation = leveltargetmap.containsKey("0") ? getNavigation(isURL,leveltargetmap.get("0"),"首页","/") : (isURL)? "<a href='/'>首页</a>  -> " : "首页  ->";
		
		
		List channelList = new ArrayList();
		parentlist = new ArrayList<Channel>();
		channelList = this.getParent(channelManagerService.getSiteCache().getAllChannels(), channel);
		//反转  (频道-目标-父级数) 集合信息 
		for(int i=channelList.size()-1, j=1 ; i>=0;i--, j++)
		{  
			Channel channelNext = (Channel) channelList.get(i);
			if(channelNext != null && channelNext.getSummaryTemplate() != null){
				if(leveltargetmap.containsKey(""+j)){
					navigation += getNavigation(isURL,leveltargetmap.get(j+""),channelNext.getName(),channelNext.getChannelPath());
				}else{
					navigation += (isURL)? "<a href='/"+channelNext.getChannelPath()+"'>"+channelNext.getName()+"</a> -> " :channelNext.getName()+" -> ";
				}
			}else{
				navigation += channelNext.getName()+" -> ";
			}
		}
		navigation += (isURL)? "<a href='/"+channel.getChannelPath()+"'>"+channel.getName()+"</a>" : channel.getName();
		return navigation;
	}
	
	/**
	 * 获取 当前频道的 导航
	 * @param isURL 是否添加连接
	 * @param ltn  频道. 目标 父级数
	 * @param name  频道名称
	 * @param path  连接
	 * @return
	 */
	private String getNavigation(boolean isURL, String [] ltn,String name, String path){
		String subNavigation ="";
		if(isURL){   
			 if(ltn[1].equals("parentn")){
				 String num = ltn.length > 2 ? ltn[2] : null;
				 int n = !StringUtils.isNotEmpty(num) ? 0 : Integer.valueOf(num);
				 subNavigation = "<a href=\"javascript: window";
				 for(int i=0; i<n; i++){
					 subNavigation +=".parent";
				 }
				 subNavigation +=".location.href = '"+(path.trim().indexOf("/") != 0 ? "/"+path : path)+"'; window.document.getElementByTagNames('body')[0].innerHTML='';  \">"+name+"</a>  -> ";
			 }else 
				 subNavigation = "<a href='/' target='"+ltn[1]+"'>"+name+"</a>  -> ";
		}else 
			subNavigation = name+" -> "; 
		
		return subNavigation;
	}
	
	
	/**
	 * 获取当前频道的所有上级频道
	 * @param allChannel
	 * @param channelVO
	 * @return
	 */
	public List getParent(List allChannel,Channel channelVO)
	{ 
		Iterator it = allChannel.iterator();  
		while(it.hasNext())
		{
			Channel channel = (Channel)it.next(); 
 
			if(channelVO.getParent() != null && channelVO.getParent().getId() != null && channel.getId().longValue() == channelVO.getParent().getId().longValue())
			{
				parentlist.add(channel);
				getParent(allChannel,channel);
				
			}
		}
		return parentlist; 
	}

	public void setParentlist(List<Channel> parentlist) {
		this.parentlist = parentlist;
	}
	
}
