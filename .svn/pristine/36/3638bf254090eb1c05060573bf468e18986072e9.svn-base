package com.cyberway.core.mail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 使用freemarker模板引擎的内容生成器
 *
 * @author caiw
 */
public class TemplateGenerator {
	protected static final Log log = LogFactory.getLog(TemplateGenerator.class);
	
    private String htmlFileDir;//生成的预览文件的路径
    
    private FreeMarkerConfigurer freeMarkerEngine = null;
    
	public void setHtmlFileDir(String htmlFileDir) {
		this.htmlFileDir = htmlFileDir;
	}
	public void setFreeMarkerEngine(FreeMarkerConfigurer freeMarkerEngine) {
		this.freeMarkerEngine = freeMarkerEngine;
	}
    /**
     * 根据模板和对应的参数生成静态文件.
     * 文件存放路径为当前webapp下面的1级子目录,在spring配置文件中指定
     * 如当前htmlFileName为1.html,htmlFileDir为html
     * 那么文件生成后的访问路径类似于:http://localhost:8080/rjms/html/1.html
     *
     * @param templateFileName 模板文件名
     * @param propMap          用于处理模板的属性Object映射
     * @param htmlFileName     要生成的文件名,例如 "1.htm"
     */
    public boolean generateHtmlFile(String templateFileName, Map propMap, String htmlFileName) {
        String sRootDir = getAbsolutePath();
        try {
            Template t = freeMarkerEngine.getConfiguration().getTemplate(templateFileName);
            //如果根路径存在,则递归创建子目录 
            creatDirs(sRootDir, htmlFileDir);
            File afile = new File(sRootDir + "/" + htmlFileDir + "/" + htmlFileName);
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), t.getEncoding()));
            t.process(propMap, out);
        }
        catch (TemplateException e) {
            log.error("Error while processing FreeMarker template " + templateFileName, e);
            return false;
        }
        catch (IOException e) {
            log.error(("Error while generate Static Html File " + htmlFileName), e);
            return false;
        }
        return true;
    }


    /**
     * 获取当前webapp绝对路径
     */
    private String getAbsolutePath() {
        File f = new File("");
        return f.getAbsolutePath();
    }

    /**
     * 创建多级目录
     *
     * @param aParentDir String
     * @param aSubDir    以 / 开头
     * @return boolean 是否成功
     */
    private boolean creatDirs(String aParentDir, String aSubDir) {
        File aFile = new File(aParentDir);
        if (aFile.exists()) {
            File aSubFile = new File(aParentDir + "/" + aSubDir);
            if (!aSubFile.exists()) {
                return aSubFile.mkdirs();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 根据模板文件和传入的属性生成内容.
     *
     * @param propMap 用于处理模板的属性Object映射
     * @param templateName 
     * @return 生成的内容
     */
    public String generateContent(Map propMap, String templateName) {
        String result = null;
        try {
            Template t = freeMarkerEngine.getConfiguration().getTemplate(templateName);
            result = FreeMarkerTemplateUtils.processTemplateIntoString(t, propMap);
        }
        catch (TemplateException e) {
            log.error("Error while processing FreeMarker template ", e);
            return null;
        } catch (FileNotFoundException e) {
            log.error("Error while open template file ", e);
            return null;
        }
        catch (IOException e) {
            log.error("Error while generate Content ", e);
            return null;
        }
        return result;
    }

}
