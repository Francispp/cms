package com.cyberway.cms.webservice.service;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.cyberway.cms.Constants;
import com.cyberway.core.utils.StringUtil;

/**
 * Lucene 索引文件同步
 * 
 * Frank
 */
public class LuceneSynchroismService {
	
	private static final Logger LOG = Logger.getLogger(LuceneSynchroismService.class);
	
	private ThreadPoolExecutor _threadPoolExecutor;
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor)
	{
		_threadPoolExecutor = threadPoolExecutor;
	}
	String luceneUrl = Constants.LUCENE_SYNCHROISM;

	public void documnetLucene(String documentIds,String type) {
		if (StringUtil.isEmpty(luceneUrl)||StringUtil.ifEqual("/", luceneUrl)) {
			return;
		} else if (luceneUrl.indexOf(";") > 0) {
			String[] caches = luceneUrl.split(";");
			for (final String str : caches) {
				_threadPoolExecutor.execute(new LuceneSynchroism(str,documentIds,type));
			}

		} else {
			_threadPoolExecutor.execute(new LuceneSynchroism(luceneUrl,documentIds,type));
		}
	}
	
	
	static class LuceneSynchroism implements Runnable
	{
		Service service=null;
		Call call=null;
		private String _luceneUrl;
		private String _documentIds;
		private String _type;
		
		public LuceneSynchroism(String luceneUrl, String documentIds, String type)
		{
			_luceneUrl = luceneUrl;
			_documentIds = documentIds;
			_type = type;
		}

		public void run()
		{
			try{
				service = new Service();
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new java.net.URL(
						_luceneUrl));
				call.setOperationName("documentLucene");
				call.addParameter("documentIds",
						org.apache.axis.encoding.XMLType.XSD_STRING,
						javax.xml.rpc.ParameterMode.IN);
				call.addParameter("type",
						org.apache.axis.encoding.XMLType.XSD_STRING,
						javax.xml.rpc.ParameterMode.IN);
				call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
				call.setUseSOAPAction(true);
				call.invoke(new Object[] { _documentIds, _type });
			}catch(Exception ex){
				LOG.error("-同步索引失败->docs:"+_documentIds+", type:"+_type, ex);
			}
		}
	}
}
