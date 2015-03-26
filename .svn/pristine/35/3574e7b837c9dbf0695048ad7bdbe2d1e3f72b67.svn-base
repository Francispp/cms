package com.cyberway.common.base.jscall;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cyberway.core.objects.Loginer;
import com.cyberway.core.objects.UserFrame;
import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.utils.ajax.BuffaloService;

import freemarker.template.Configuration;

/**
 * 基础JS调用方法
 * @author caiw
 *
 */
public class BasicCall extends BuffaloService {
	private static Configuration cfg;
	private Log logger = LogFactory.getLog(getClass());
/*	private ViewService view;

	public BasicCall() throws IOException {
		view = (ViewService) BizAgent.getService(ViewService.class);
	}*/

	
	/**
	 * JS远程调用service
	 * @param clazName
	 * @param methodName
	 * @param args
	 * @return
	 */
	public Object callService(String clazName,String methodName,Object[] args){
		Object rt = null;
		if(StringUtil.isEmpty(clazName)||StringUtil.isEmpty(methodName)){
			logger.debug("接口或方法不存在"+clazName+"."+methodName);
			return rt;
		}
		try{
			//Class interfaceclass=Class.forName(clazName);
			Object bs=ServiceLocator.getBean(clazName);
			Class interfaceclass=bs.getClass();
			//BizService bs=BizAgent.getService(interfaceclass);
			Class[] types=new Class[args.length];
			for(int i=0;i<args.length;i++){
				types[i]=args[i].getClass();
				//传入用户信息
				if(args[i]!=null&&args[i].getClass()==String.class&&((String)args[i]).equalsIgnoreCase("{userinfo}")){
					Loginer loginer = (Loginer) this.getRequest().getSession().getAttribute(
							Loginer.LOGININFO_SESSION);					
					args[i]=loginer;
					types[i]=UserFrame.class;
				}
				
				logger.info(i+":types is "+types[i].getName());
			}
			Method method=interfaceclass.getMethod(methodName,types);
			logger.debug("method:"+method.getName());
			Object obj=method.invoke(bs,args);
			if(obj instanceof Map)
				rt =(Map)obj;
			else if(obj instanceof Boolean)
				rt =obj.toString();
				else{
			    if(obj!=null)
			    	logger.debug("result is types:"+obj.getClass().getName()+"___"+obj);
				rt =obj;
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		return rt;
	}
	/**
	 * 取得Select框的值及名字
	 * @return
	 */
	public String getSelectValueNames(String interfacename,String methodname,Object[] args){
		Map results=null;		
		results=(Map)callService(interfacename,methodname,args);
		if(results==null)
			return "";
		return getAddSelectString(results);
	}
	/**
	 * 取得增加select的字符串
	 * @param selects
	 * @return
	 */
	private static String getAddSelectString(Map selects){
		StringBuffer buf=new StringBuffer();
		if(!selects.isEmpty()){
			Iterator it=selects.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry me=(Map.Entry)it.next();
				buf.append("Obj.add(new Option(\"").append(me.getValue())
				   .append("\",\"").append(me.getKey()).append("\"));");				
			}
			}
		System.out.println("+++:"+buf.toString());
		return buf.toString();
	}	
	/**
	 * 删除�yi一批对象，oid之间用�逗号隔开
	 * @param pojoName
	 * @param oids
	 * @return
	 */
/*	public boolean delectObjs(String pojoName,String oids){
		try {
			BizUtilServices bs = (BizUtilServices) BizAgent
					.getService(BizUtilServices.class);
			String[] soids = oids.split(",");
			List list = new ArrayList();
			for (int i = 0; i < soids.length; i++)
				list.add(new Long(soids[i]));
			return bs.deleteBizObjects(Class.forName(pojoName), list);
		} catch (Exception e) {
			Log.error(e);
			return false;
		}
	}*/
}
