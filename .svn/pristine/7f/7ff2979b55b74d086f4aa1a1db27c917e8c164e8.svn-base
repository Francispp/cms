package com.cyberway.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cyberway.common.domain.CoreSystemLog;
import com.cyberway.core.Constants;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.objects.Loginer;

/**
 * 系统日志操作service
 * @author caiw
 *
 */
public class SystemLogService extends HibernateEntityDao<CoreSystemLog>{
	public static final String LATELY_URL="LATELY_URL";
	private List logs=new ArrayList();
	private static int count=new Integer(Constants.DEFAULT_LOG_CONMIT_COUNT);
	private final static String[] AllowAccess= new String[]{"login!login.acion","export","import","save","update","delete","!del"};
	
	public static CoreSystemLog getSystemLog(HttpServletRequest request){
		CoreSystemLog log = null;
		if(request!=null){
			String url = request.getRequestURL().toString();
			if(isAllowAccess(url)){
				log=new CoreSystemLog();
				log.setAccesstime(new Date());
				log.setIpaddress(request.getRemoteAddr());
				log.setAccessurl(url);
				HttpSession session=request.getSession();
				if(session!=null){
					String l_url=(String)session.getAttribute(LATELY_URL);
					if(l_url!=null&&l_url.equals(log.getAccessurl()))
					  return null;
					//session.setAttribute("LATELY_URL", log.getAccessurl());
					Object obj=session.getAttribute(Loginer.LOGININFO_SESSION);
					if(obj!=null){
						log.setAccessid(((Loginer)obj).getUserid());
						log.setAccessname(((Loginer)obj).getUsername());
						log.setPcode(((Loginer)obj).getPortal().getPortalcode());
					}
				}
				log.setRemark("");
			}
		}
		return log;
	}
	
	private static boolean isAllowAccess(String url){
		for (int i = 0; i < AllowAccess.length; i++) {
			if(url.indexOf(AllowAccess[i])>0){
				return true;
			}
		}
		return false;
	}
	/**
	 * 插入日志
	 * @param log
	 */
	public synchronized void insertSystemLog(CoreSystemLog log){
		logs.add(log);
		if(logs.size()>=count){
			List list=logs;
			//logs.clear();
			submitLog(list);
			//logger.info("list is size:"+logs.size());
			logs=new ArrayList();
		}			
	}
	
	public void deleteAll(){
		//getHibernateTemplate().
		//this.getSessionFactory().
	}
	/**
	 * 日志达到数量，就自动提交
	 */
	private synchronized void submitLog(List list){		
		logger.info("list is size:"+list.size());
		this.saveOrUpdateAll(list);		
	}
	/**
	 * 获得指定用户的访问日志
	 * @param userid
	 * @return
	 */
/*	public List getSystemLogByUser(Long userid){
		List list=find("from CoreSystemLog where accessid=? order by accesstime desc", userid);
		
		return list;
	}*/
	
	/* (non-Javadoc)
	 * @see com.cyberway.core.dao.HibernateEntityDao#findECPage(java.util.Map, org.extremecomponents.table.limit.Limit, com.cyberway.core.dao.support.CriteriaSetup)
	 */
/*	public Page findECPage(Map filterMap, Limit limit,CriteriaSetup criteriaSetup) throws Exception {
		if(!limit.getSort().isSorted()){
		if(criteriaSetup==null)
			criteriaSetup=new CriteriaSetup();
		Order order=Order.desc("accesstime");
		criteriaSetup.setInOrder(order);
		logger.info("按默认的方式排序! accesstime desc");
		}else
			logger.info("order by :"+limit.getSort().toString());
		return super.findECPage(filterMap, limit,criteriaSetup);
	}	*/
}
