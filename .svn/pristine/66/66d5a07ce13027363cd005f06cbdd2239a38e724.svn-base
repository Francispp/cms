package com.cyberway.cms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.cyberway.cms.Constants;
import com.cyberway.core.utils.FileUtil;
import com.cyberway.core.utils.StringUtil;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @author caiw
 * Office文件公用类
 *
 */
public class OfficeFileUtil {

	/**
	 * 将Word文件的内容，转换成txt
	 * @param paths
	 * @param filename
	 * @return
	 */
	public static String wordFileToTxt(String paths,String filename){
		String content=null;
		 System.out.println("当前正在转换word......");
		 //打印当前目录路径
		 //System.out.println(paths);
		 //打印doc文件名
		 //System.out.println(filename.substring(0, (filename.length() - 4)));
		 ActiveXComponent app=null;
		 try 
		 {		 
		  app = new ActiveXComponent("Word.Application");//启动word
		 
		 String docpath = paths + filename;
		 String htmlpath = paths + filename.substring(0, (filename.length() - 4))+".txt";
		 
		 String inFile = docpath;
		 //要转换的word文件
		 String tpFile = htmlpath;
		 //HTML文件

		 boolean flag = false;
		 

		 app.setProperty("Visible", new Variant(false));
		 //设置word不可见


		 Dispatch docs = app.getProperty("Documents").toDispatch();
		 Dispatch doc = Dispatch.invoke(docs,"Open", Dispatch.Method, new Object[]{inFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();
		 //打开word文件
		 Dispatch.invoke(doc,"SaveAs", Dispatch.Method, new Object[]{tpFile,new Variant(7)}, new int[1]);//new Variant(7)转换成txt,new Variant(8)转换成html
		 //作为html格式保存到临时文件
		 Variant f = new Variant(false);
		 Dispatch.call(doc, "Close", f);
		 flag = true;
		 content=readFile(htmlpath);
		 } 
		 catch (Exception e) 
		 {
		 e.printStackTrace();
		 } 
		 finally 
		 {
		 if(app!=null)
		    app.invoke("Quit", new Variant[] {});
		 }
		 System.out.println("转化完毕！");
		 System.out.println(content);
		return content;
	}
	/**
	 * excel文件的内容转换成txt
	 * @param paths
	 * @param filename
	 * @return
	 */
	public static String excelFileToTxt(String paths,String filename){
		String content=null;
		 System.out.println("当前正在转换excel......");
		 //打印当前目录路径
		 System.out.println(paths);
		 //打印doc文件名
		 System.out.println(filename.substring(0, (filename.length() - 4)));
		 
		 ActiveXComponent app = new ActiveXComponent("Excel.Application");//启动word
		 
		 String docpath = paths + filename;
		 String htmlpath = paths + filename.substring(0, (filename.length() - 4));
		 
		 String inFile = docpath;
		 //要转换的word文件
		 String tpFile = htmlpath;
		 //HTML文件

		 boolean flag = false;
		 
		 try 
		 {
		 app.setProperty("Visible", new Variant(false));
		 //设置word不可见


		 Dispatch docs = app.getProperty("Workbooks").toDispatch();
		 Dispatch doc = Dispatch.invoke(docs,"Open", Dispatch.Method, new Object[]{inFile,new Variant(false), new Variant(true)}, new int[1]).toDispatch();
		 //打开word文件
		 Dispatch.invoke(doc,"SaveAs", Dispatch.Method, new Object[]{tpFile,new Variant(7)}, new int[1]);//new Variant(7)转换成txt,new Variant(8)转换成html
		 //作为html格式保存到临时文件
		 Variant f = new Variant(false);
		 Dispatch.call(doc, "Close", f);
		 flag = true;
		 content=readFile(htmlpath);
		 } 
		 catch (Exception e) 
		 {
		 e.printStackTrace();
		 } 
		 finally 
		 {
		 app.invoke("Quit", new Variant[] {});
		 }
		 System.out.println("转化完毕！");
		 System.out.println(content);
		return content;
	}	
	/**
	 * read the content from a file;
	 * 
	 * @param output
	 * @param content
	 * @throws Exception
	 */
	public static String readFile(String input) throws Exception {
		char[] buffer = new char[4096];
		int len = 0;
		StringBuffer content = new StringBuffer(4096);

/*		if (ENCODING == null)
			ENCODING = PropsUtil.ENCODING;*/
		InputStreamReader fr = null;
		BufferedReader br = null;
		try {
			fr = new InputStreamReader(new FileInputStream(input));
			br = new BufferedReader(fr);
			while ((len = br.read(buffer)) > -1) {
				content.append(buffer, 0, len);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (br != null)
				br.close();
			if (fr != null)
				fr.close();
		}
		return content.toString();
	}
	/**
	 * 更新
	 * @param officefilepath
	 * @param output
	 * @return
	 */
	public static boolean updateOfficeFile(String officefilepath,String output){
		 //解决amway 集群问题
		 if(Constants.IS_REALPATH){
			try{
			 FileUtil.update(Constants.ABSOLUTE_PATH+officefilepath,output);
			}catch(Exception e){
				e.printStackTrace();
			}
			}
		 return true;
	}
	/**
	 * 用于同步更新office文件夹的方法
	 * @param docid
	 * @param output
	 * @return
	 */
	public static boolean updateOfficeFileByDocId(String docid,String outpath){
		 //解决amway 集群问题
		 if(Constants.IS_REALPATH){
			try{
				docid=StringUtil.replaceAll(docid, "/", "");
				outpath=StringUtil.replaceAll(outpath, "\\", "/");
				CopyDir(Constants.ABSOLUTE_PATH+Constants.INFO_OFFICE_PATH+"/"+docid,outpath+Constants.INFO_OFFICE_PATH+"/"+docid);
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			}		
		 return true;
	}
	/**
	 * 同步更新目录
	 * @param sourcedir
	 * @param destdir
	 * @throws Exception
	 */
	public static void CopyDir(String sourcedir, String destdir)
			throws Exception {
		File dest = new File(destdir);
		File source = new File(sourcedir);

		String[] files = source.list();
		try {
			FileUtil.makehome(destdir);
		} catch (Exception ex) {
			throw new Exception("CopyDir:" + ex.getMessage());
		}
        if(files==null || files.length==0)
        	return ;
		for (int i = 0; i < files.length; i++) {
			String sourcefile = source + File.separator + files[i];
			String destfile = dest + File.separator + files[i];
			File temp = new File(sourcefile);
			if (temp.isFile()) {
				try {
					FileUtil.update(sourcefile, destfile);
				} catch (Exception ex) {
					throw new Exception("CopyDir:" + ex.getMessage());
				}
			}
		}
	}	
}
