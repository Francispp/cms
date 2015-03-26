package com.cyberway.staticer.gather;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.KeyValue;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.text.StrLookup;
import org.apache.commons.lang.text.StrSubstitutor;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;

import com.cyberway.core.utils.ServiceLocator;
import com.cyberway.staticer.Configuration;
import com.cyberway.staticer.cache.PageCache;
import com.cyberway.staticer.cache.PageKey;
import com.cyberway.staticer.util.StaticerUtils;
import com.opensymphony.util.GUID;

/**
 * @see PagePublisher
 * @author helfen
 *
 */
public class PagePublisherImpl implements PagePublisher
{	
	public static final String QUARTZ_GROUP = PagePublisherImpl.class.getName(); 
	public static final String CACHE_BEAN_NAME = "cms.staticer.cache";
	public static final String EVENT_LISTENER_BEAN_NAME = "cms.staticer.gatherEventListener";
		
	private PageCache _cache;
	private ThreadPoolExecutor _threadPoolExecutor;
	private Scheduler _scheduler;
	
	public void setCache(PageCache cache)
	{
		_cache = cache;
	}
	
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor)
	{
		_threadPoolExecutor = threadPoolExecutor;
	}
	
	public void setScheduler(Scheduler scheduler)
	{
		_scheduler = scheduler;
	}
	

	public void publish(final String url, final String role)
	{
		publish (url, role, false);
	}
	
	public void publish(final String url, final String role, final boolean justNested)
	{
		getPageResponse(url, role, true, new ResponseHandler()
		{
			public Object handleResponse(HttpClient httpClient, GetMethod response)
			{
				//System.out.println(IOUtils.toString(response.getEntity().getContent()));
				
				final JSONObject variables = new JSONObject();
				
				StrSubstitutor substitutor = new StrSubstitutor(new StrLookup()
				{
					@Override
					public String lookup(String arg0) 
					{
						String name = arg0.substring(0, arg0.indexOf("="));
						String body = arg0.substring(arg0.indexOf("=") + 1);
						
						variables.element(name, JSONArray.fromObject(body));
						
						return "";
					}
				}, Configuration.headerVarPrefix(), Configuration.headerVarSuffix(), '$');
				
				try 
				{
					substitutor.replace(response.getResponseBodyAsString());
				} 
				catch (IOException e1) 
				{
					throw new RuntimeException(e1);
				}
				
				/*for (Entry<String, Cookie> pair : (Set<Entry<String, Cookie>>)httpClient.getState().getCookiesMap().entrySet())
				｛
					Cookie cookie = pair.getValue();
					
					System.out.println(pair.getKey()+"---------header test---------------"+cookie.getValue());
					if (pair.getKey().startsWith(Configuration.headerVarPrefix()))
					{
						variables.element(pair.getKey().substring(Configuration.headerVarPrefix().length()), JSONArray.fromObject(cookie.getValue()));
					}
				} */
				
				final TreeNode rootNode = new TreeNode();
				
				buildTree(variables, rootNode, 0);
				
				if (!justNested)
				{
					if (_scheduler == null)
					{
						try{
						_threadPoolExecutor.execute(new GatherWorker(url, role, _cache));
						}catch(Exception e){
							e.printStackTrace();
						}
						}
					else
					{
						scheduleJob(url, role);
					}
				}

				visitNode(rootNode, new Closure()
				{
					public void execute(Object object)
					{
						TreeNode node = (TreeNode)object;
						
						if (node != rootNode && node.getChildren().isEmpty())
						{
							Map<String, String> map = new LinkedHashMap<String, String>();
							
							while (node.getParent() != null)
							{
								map.put((String)node.getData().getKey(), (String)node.getData().getValue());
								
								node = node.getParent();
							}
					
							if (_scheduler == null)
							{
								_threadPoolExecutor.execute(new GatherWorker(StaticerUtils.mergeQuery(url, map).toString(), role, _cache));
							}
							else
							{
								scheduleJob(StaticerUtils.mergeQuery(url, map).toString(), role);
							}
						}
					}
				});
				
				return null;
			}
		});
	}
	
	
	public void publish(final String baseURL, Map<String, String[]> variables, final String role)
	{
		publish (baseURL, variables, role, true);
	}
	

	/**
	 * 静态分发核心方法
	 * @author Dicky
	 * @time 2012-9-19 16:24:31
	 * @version 1.0
	 * @param baseURL
	 * @param variables
	 * @param role
	 * @param gatherNested
	 */
	public void publish(final String baseURL, Map<String, String[]> variables, final String role, final boolean gatherNested)
	{	
		final TreeNode rootNode = new TreeNode();
		
		buildTree(JSONObject.fromObject(variables), rootNode, 0); 
		
		visitNode(rootNode, new Closure()
		{
		
			public void execute(Object object)
			{
				TreeNode node = (TreeNode)object;
				
				if (node != rootNode && node.getChildren().isEmpty())
				{
					Map<String, String> map = new LinkedHashMap<String, String>();
					
					while (node.getParent() != null)
					{
						map.put((String)node.getData().getKey(), (String)node.getData().getValue());
						
						node = node.getParent();
					}
			
					if (_scheduler == null)
					{
						
						_threadPoolExecutor.execute(new GatherWorker(StaticerUtils.mergeQuery(baseURL, map).toString(), role, _cache));
					}
					else
					{
						scheduleJob(StaticerUtils.mergeQuery(baseURL, map).toString(), role);
					}
					
					if (gatherNested)
					{
						publish (StaticerUtils.mergeQuery(baseURL, map).toString(), role, true);
					}
				}
			}
		});
	}
	
	protected void scheduleJob (String url, String role)
	{
		JobDetail jobDetail = new JobDetail(GUID.generateFormattedGUID(), QUARTZ_GROUP, GatherJob.class);
		Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 0L);
		
		jobDetail.getJobDataMap().put("url", url);
		jobDetail.getJobDataMap().put("role", role);
		
		trigger.setName(GUID.generateFormattedGUID());
		trigger.setGroup(QUARTZ_GROUP);
		
		try
		{
			_scheduler.scheduleJob(jobDetail, trigger);
		}
		catch (SchedulerException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	protected static void getPageResponse (String url, String role, boolean forAnalysis, ResponseHandler responseHandler)
	{
		HttpClient httpClient = new HttpClient();
		
		try
		{
			GetMethod httpGet = new GetMethod(url);
			
			try
			{
				if (forAnalysis)
				{
					httpGet.addRequestHeader(Configuration.headerGahterPhase(), Boolean.TRUE.toString());
				}
				
				httpClient.executeMethod(httpGet);

				responseHandler.handleResponse(httpClient, httpGet);
			}
			finally
			{
				httpGet.releaseConnection();
			}
		}
		catch (IOException ex)
		{
			eventListener().onFailed(url, role);
		}
	}
	
	protected static void buildTree (JSONObject variables, TreeNode parent, int iKey)
	{
		if (variables.names().size() <= iKey) return;
		
		String key = variables.names().getString(iKey);
		for (String value : (List<String>)variables.getJSONArray(key))
		{
			parent.getChildren().add(new TreeNode (parent, key, value));
		}
	
		for (TreeNode node : parent.getChildren())
		{
			buildTree (variables, node, iKey + 1);
		}
	}
	
	protected static void visitNode (TreeNode node, Closure callback)
	{
		callback.execute(node);
		
		for (TreeNode child : node.getChildren())
		{
			visitNode (child, callback);
		}
	}
	
	protected static EventListener eventListener ()
	{
		return (EventListener)ServiceLocator.getBean(EVENT_LISTENER_BEAN_NAME);
	}
	
	static class TreeNode 
	{
		private TreeNode _parent;
		private List<TreeNode> _children = new ArrayList<TreeNode>();
        private KeyValue _data;
        
        public TreeNode()
		{
		}
        
        public TreeNode (TreeNode parent, String key, String value)
        {
        	_parent = parent;
        	_data = new DefaultKeyValue(key, value);
        }
        
        public TreeNode getParent()
		{
			return _parent;
		}
        
		public List<TreeNode> getChildren()
		{
			return _children;
		}

		public KeyValue getData()
		{
			return _data;
		}

		public void setData(KeyValue data)
		{
			_data = data;
		}
		
		@Override
		public String toString()
		{
			if (getData() == null)
			{
				return "ROOT";
			}
			else
			{
				return new ToStringBuilder (this).append("key", getData().getKey()).append("value", getData().getValue()).toString();
			}
		}
    }
	
	static class GatherWorker implements Runnable
	{
		private String _url;
		private String _role;
		private PageCache _cache;
		
		public GatherWorker(String url, String role, PageCache cache)
		{
			_url = url;
			_role = role;
			_cache = cache;
		}

		public void run()
		{
			getPageResponse(_url, _role, false, new ResponseHandler()
			{
				public Object handleResponse(HttpClient httpClient, GetMethod response)
				{
					URI uri = StaticerUtils.toURI(_url);
					
					if (ObjectUtils.equals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK)) 
					{
						try
						{
							_cache.put(new PageKey(uri.getHost(), uri.getPath(), StaticerUtils.parseQuery(uri.getQuery()), _role), response.getResponseBody());
							
							eventListener().onSuccessed(_url, _role);
						}
						catch (IllegalStateException ex)
						{
							eventListener().onFailed(_url, _role);
						}
						catch (IOException ex)
						{
							eventListener().onFailed(_url, _role);
						}
					}
					
					return null;
				}
			});
		}
	}
	
	public static class GatherJob implements Job
	{
		public void execute(JobExecutionContext ctx) throws JobExecutionException
		{
			String url = ctx.getMergedJobDataMap().getString("url");
			String role = ctx.getMergedJobDataMap().getString("role");
			
			try
			{
				new GatherWorker(url, role, (PageCache)ServiceLocator.getBean(CACHE_BEAN_NAME)).run();
			}
			catch (Exception ex)
			{
				throw new JobExecutionException(ex);
			}
		}
	}
	
	public static interface ResponseHandler
	{
		public Object handleResponse(HttpClient httpClient, GetMethod response);
	}
}
