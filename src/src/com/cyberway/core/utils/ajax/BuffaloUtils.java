package com.cyberway.core.utils.ajax;

import java.net.URL;

import org.apache.commons.jxpath.Container;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.xml.DocumentContainer;
import org.springframework.util.StringUtils;



/**
 * 
 * @author caiw
 *
 */
public class BuffaloUtils {
    
    /**
     * Populate the service to a service repository.
     * @param prop
     * @return
     */
    public static ServiceRepository populateRepository() {
        ServiceRepository repo = ServiceRepository.getDefaultRepository();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource("sysconfig.xml");
		Container c = new DocumentContainer(url);
		JXPathContext context = JXPathContext.newContext(c);
		
		String[] it = StringUtils.tokenizeToStringArray((String) context.getValue("config/jscall"),"\n");
		try {
			for (int i=0;i<it.length;i++) {
				String[] caller = it[i].trim().split("=");
				String jsname = caller[0];
				String classname = caller[1];
				repo.registerService(jsname, Class.forName(classname));
				//Log.info("注册一个js远程调用方法成功：js类名-"+jsname+" java类名-"+classname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
 
        return repo;
    }
}
