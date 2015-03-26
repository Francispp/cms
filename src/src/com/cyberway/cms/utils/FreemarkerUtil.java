package com.cyberway.cms.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.cyberway.cms.Constants;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtil {
	private static Configuration cfg;
	
	private static void init(){
		cfg = new Configuration();
		try {
			cfg.setDirectoryForTemplateLoading(getTemplateFolder());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Template getTemplate(String name){
		try {
			if(cfg==null){
				init();
			}
			Template temp = cfg.getTemplate(name);
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean printToFile(String templateName,Map<String,Object> root,File outFile){
		FileWriter out = null;
		try {
			out = new FileWriter(outFile);
	        Template temp = getTemplate(templateName);
	        temp.process(root, out);
	        return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				if(out!=null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	private static File getTemplateFolder(){
		String fullpath = "";
		if(Constants.IS_REALPATH)
		{
			fullpath = Constants.FREEMARKER_TEMPLATE_FOLDER;
		}
		else
		{
			fullpath = ServletActionContext.getServletContext().getRealPath(Constants.FREEMARKER_TEMPLATE_FOLDER);
		}
		File file = new File(fullpath);
		return file;
	}
}
