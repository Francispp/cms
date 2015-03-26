package com.cyberway.staticer.cache.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.cyberway.staticer.Configuration;
import com.cyberway.staticer.cache.EventListener;
import com.cyberway.staticer.cache.PageCache;
import com.cyberway.staticer.cache.PageKey;

/**
 * 页面缓存的文件储存实现
 * @author helfen
 *
 */
public class FileCache implements PageCache
{
	/**
	 * 文件储存根目录<br />
	 * 如果是 "/" 起始则表示以WEB根目录为起始
	 */
	public String _storePath;
	
	/**
	 * 文件储存根目录<br />
	 * 自己动根据 storePath 转换成文件系统文件对象
	 */
	private File _storeDir;
	
	/**
	 * 事件监听器
	 */
	private EventListener _eventListener;
	
	public void setStorePath(String storePath)
	{
		_storePath = storePath;
	}
	
	public void setEventListener(EventListener eventListener)
	{
		_eventListener = eventListener;
	}
	
	protected File getStoreDir()
	{
		if (_storeDir == null)
		{
			if (_storePath.startsWith("/"))
			{
				_storeDir = new File (Configuration.servletContext().getRealPath(_storePath));
			}
			else
			{
				_storeDir = new File (_storePath);
			}
		}
		
		return _storeDir;
	}
	
	public Set<PageKey> keySet()
	{
		Set<PageKey> retVal = new HashSet<PageKey>();

		for (File pageFile : (Collection<File>)FileUtils.listFiles(getStoreDir(), null, true))
		{
			String host = null;
			StringBuilder path = new StringBuilder();
			String role = StringUtils.substringAfterLast(pageFile.getName(), "^");
			Map<String, String> parameters = new HashMap<String, String> ();
			
			File parent = pageFile;
			
			do
			{
				parent = parent.getParentFile();
				
				if (ObjectUtils.equals(parent.getParentFile(), getStoreDir()))
				{
					host = parent.getName();
				}
				else
				{
					path.insert(0, String.format("/%s", parent.getName()));
				}
			}
			while (host == null);
			
			path.append("/" + StringUtils.substringBefore(pageFile.getName(), Configuration.paramSep()));
 
			for (String pair : StringUtils.split(StringUtils.substringBetween(pageFile.getName(), Configuration.paramSep(), Configuration.roleSep()), Configuration.paramSep()))
			{
				parameters.put(
						StringUtils.substringBefore(pair, Configuration.paramNameValSep()), 
						StringUtils.substringAfter(pair, Configuration.paramNameValSep()));
			}
			
			retVal.add(new PageKey(host, path.toString(), parameters, role));
		}
		
		return retVal;
	}
	
	public boolean contains(PageKey key)
	{
		File pageFile = resolvePageFile(key);
		
		return pageFile.exists() && pageFile.canRead();
	}

	public byte[] get(PageKey key)
	{
		File file = resolvePageFile(key);
		
		if (file.exists())
		{
			FileInputStream fileStream = null;
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			
			try
			{
				fileStream = new FileInputStream(file);
				
				IOUtils.copy(fileStream, byteStream);
			}
			catch (IOException ex)
			{
				throw new RuntimeException(ex);
			}
			finally
			{
				IOUtils.closeQuietly(fileStream);
			}
			
			return byteStream.toByteArray();
		}
		
		return null;
	}

	public void put(PageKey key, byte[] pageData)
	{
		File file = resolvePageFile(key);
		
		try
		{
			if (!file.getParentFile().exists())
			{
				FileUtils.forceMkdir(file.getParentFile());
			}
			
			FileUtils.writeByteArrayToFile(file, pageData);
			
			_eventListener.afterSave(key, pageData);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	public void remove(PageKey key)
	{
		File file = resolvePageFile(key);
		
		try
		{
			FileUtils.forceDelete(file);
			
			_eventListener.afterRemove(key);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	protected File resolvePageFile (PageKey key)
	{
		StringBuilder fileName = new StringBuilder(String.format("%s%s%s%s", 
				getStoreDir().getAbsolutePath(), 
				File.separator,
				key.getHost(),
				File.separator));
		
		if (key.getParameters().containsKey(Configuration.paramChannelId()) && key.getParameters().containsKey(Configuration.documentId ()))
		{
			fileName.append(Configuration.channelFolderPrefix() + key.getParameters().get(Configuration.paramChannelId()) + File.separator);
			fileName.append(Configuration.documentFolderPrefix() + key.getParameters().get(Configuration.documentId()) + File.separator);
			fileName.append(StringUtils.substringAfterLast(key.getPath(), "/"));
		}
		else if (key.getParameters().containsKey(Configuration.paramTemplateId()))
		{
			if (key.getParameters().containsKey(Configuration.paramChannelId()))
			{
				fileName.append(Configuration.channelFolderPrefix() + key.getParameters().get(Configuration.paramChannelId()) + File.separator);
			}
			
			fileName.append(Configuration.templateFolderPrefix() + key.getParameters().get(Configuration.paramTemplateId()) + File.separator);
			fileName.append(Configuration.roleFolderPrefix() + key.getRole() + File.separator);
			fileName.append(StringUtils.substringAfterLast(key.getPath(), "/"));
		}
		else
		{
			fileName.append(StringUtils.replace(key.getPath(), "/", File.separator));
		}
		
		Iterator<Entry<String, String>> iterator = key.getParameters().entrySet().iterator();
		while (iterator.hasNext())
		{
			Entry<String, String> pair = iterator.next();
			
			if (!pair.getKey().startsWith(Configuration.flagParamPrefix()))
			{
				fileName.append(String.format("%s%s%s%s", Configuration.paramSep(), pair.getKey(), Configuration.paramNameValSep(), pair.getValue()));
			}
		}
		
		fileName.append(String.format("%s%s", Configuration.roleSep(), key.getRole()));
		
		return new File (fileName.toString());
	}

	public String get_storePath() {
		return _storePath;
	}

	public void set_storePath(String _storePath) {
		this._storePath = _storePath;
	}
}
