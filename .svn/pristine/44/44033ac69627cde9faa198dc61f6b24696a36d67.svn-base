package com.cyberway.cms.component.genoffice;

import java.lang.reflect.Field;
import java.util.Date;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.cyberway.core.utils.BeanUtils;
import com.cyberway.core.utils.UtilDateTime;
import com.cyberway.core.utils.property.DefaultProperty;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 此类用到jacobgen_0.10, 使用时确保jacobgen.dll在JAVA_HOME/bin或system32下
 * @author Administrator
 *
 */
public class GenWordForSimple implements IGenWord, ServletContextAware {
	private ServletContext servletContext;
	private ActiveXComponent _word = null;

	private boolean find(Dispatch selection, String text) {
		Dispatch find = Dispatch.call(selection, "Find").toDispatch();

		Dispatch.put(find, "Text", text);
		Dispatch.put(find, "Forward", "True");
		Dispatch.put(find, "Format", "True");
		Dispatch.put(find, "MatchCase", "True");
		Dispatch.put(find, "MatchWholeWord", "True");

		return Dispatch.call(find, "Execute").getBoolean();
	}

	private void replace(Dispatch selection, String text) {
		Dispatch.put(selection, "Text", text);
	}

	private void replaceAll(Dispatch selection, String oldText, String newText) {
		Dispatch.call(selection, "HomeKey", new Variant(6));

		while (find(selection, oldText)) {
			replace(selection, newText);
			Dispatch.call(selection, "MoveRight");
		}
	}

	/**
	 * 生成word，	调用此方法时，需要设置模板路径和生成word文件的路径，如
	 * 		setProperty("genoffice.template.path", "E:\\templeate\\");
	 * 		setProperty("genoffic.word.path", "E:\\templeate\\genpath\\");
	 * @param org POJO javaBean
	 * @param templateName word 模板名
	 * @return
	 */
	public synchronized String generateWord(Object org, String templateName) {
		templateName = getRealPath(DefaultProperty.getProperty("genoffice.template.path","/")) + "/" + templateName;
		Dispatch document = null;
		try {
			Field[] fields = org.getClass().getDeclaredFields();
			ComThread.InitSTA();// 初始化com的线程，非常重要！！使用结束后要调用 realease方法
			// Assign a local word object
			Dispatch wordObject = (Dispatch) _word.getObject();
			// Create a Dispatch Parameter to show the document that is opened
			Dispatch.put( wordObject, "Visible", new Variant(false));//new Variant(true)表示word应用程序可见
			// Instantiate the Documents Property
			Dispatch documents = _word.getProperty("Documents").toDispatch(); // documents表示word的所有文档窗口，（word是多文档应用程序）
			// Add a new word document, Current Active Document
			document = Dispatch.call(documents, "Open", templateName).toDispatch(); // 使用Add命令创建一个新文档，用Open命令可以打开一个现有文档

			// 替换正文内容
			Dispatch selection = null;//Dispatch.get(_word, "Selection").getDispatch();
			for (Field field : fields) {
				if (BeanUtils.getDeclaredProperty(org, field) instanceof Date) {
					replaceAll(selection, "/" + field.getName() + "/", UtilDateTime.toDateString((Date) BeanUtils
							.getDeclaredProperty(org, field)));
				} else if (BeanUtils.getDeclaredProperty(org, field) != null) {
					replaceAll(selection, "/" + field.getName() + "/", BeanUtils.getDeclaredProperty(org, field)
							.toString());
				}
			}

			// 生成word的路径
			String destP = getRealPath(DefaultProperty.getProperty("genoffic.word.path")) 
					  		+ org.getClass().getName() + UtilDateTime.toDateString(new Date()) + ".doc";
			Dispatch.call(document, "SaveAs", destP);
			return destP;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (document != null) {
				Dispatch.call(document, "Close", new Variant(0));
			}
			ComThread.Release();
		}
		return null;
	}

	public void setServletContext(ServletContext sc) {
		servletContext = sc;
	}

	private String getRealPath(String path) {
		//如果是绝对路径 则直接返回
		if (path.indexOf(":") < 0) {
			return servletContext.getRealPath(path);
		} else {
			return path;
		}
	}
	
	public void init(){
		// Instantiate objWord //Declare word object
		_word = new ActiveXComponent("Word.Application");
	}
	public void destroy(){
		if (_word != null) {
			Dispatch.call(_word, "Quit");
			_word.safeRelease();
		}
	}

	public static void main(String[] args) {
	}
}
