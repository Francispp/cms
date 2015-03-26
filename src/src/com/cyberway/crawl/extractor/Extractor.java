package com.cyberway.crawl.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.htmlparser.Parser;

public abstract class Extractor {

	protected static final String NEWLINE = "\r\n";
	
	static int count = 0;

	
	
	/**
	 * 表示所有结果的输出路径
	 */
	private String outputPath = "";

	/**
	 * 表示当前正在被处理的文件
	 */
	private String inuputFilePath;

	/**
	 * 表示当前所有被抓取的网页的镜象根目录 在Heritrix用mirror目录表示
	 */
	private String mirrorDir = "";

	/**
	 * 用于存放被处理过后的图片的目录
	 */
	private String imageDir = "";

	/**
	 * HTMLParser的实例
	 */
	private Parser parser;

	/**
	 * 对图片路径进行哈希的算法，这里采用MD5算法
	 */
	protected static final String HASH_ALGORITHM = "md5";

	/**
	 * 装载需要的网页文件
	 * 
	 */
	public void loadFile(String path) {
		try {
			parser = new Parser(path);
			inuputFilePath = path;
			parser.setEncoding("GBK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取输出的路径
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * 设置输出的路径
	 */
	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public Parser getParser() {
		return parser;
	}

	/**
	 * 使用正则来匹配并获得网页中的字符串
	 */
	protected String getProp(String pattern, String match, int index) {
		Pattern sp = Pattern.compile(pattern);
		Matcher matcher = sp.matcher(match);
		while (matcher.find()) {
			return matcher.group(index);
		}
		return null;
	}

	/**
	 * 抽象方法，用于供子类实现。 功能主要是解释网页文件
	 * 
	 */
	public abstract void extract(Element root) throws Exception;

	/**
	 * 获取正在处理的文件的路径
	 */
	public String getInuputFilePath() {
		return inuputFilePath;
	}

	public String getImageDir() {
		return imageDir;
	}

	public void setImageDir(String imageDir) {
		this.imageDir = imageDir;
	}

	public String getMirrorDir() {
		return mirrorDir;
	}

	public void setMirrorDir(String mirrorDir) {
		this.mirrorDir = mirrorDir;
	}

	public void setInuputFilePath(String inuputFilePath) {
		this.inuputFilePath = inuputFilePath;
	}

	public static void traverse(Extractor extractor, File path,Element root)
			throws Exception {
		if (path == null) {
			return;
		}

		if (path.isDirectory()) {
			String[] files = path.list();
			for (int i = 0; i < files.length; i++) {
				traverse(extractor, new File(path, files[i]),root);
			}
		} else {
			//if ((path.getAbsolutePath().endsWith(".asp") ||path.getAbsolutePath().endsWith(".html"))&& path.getAbsolutePath().indexOf("_") == -1) {
				System.out.println(path);
				count++;
				extractor.loadFile(path.getAbsolutePath());
				extractor.extract(root);
			//}
		}
	}
	/**
	 * 从mirror目录下拷贝文件至所设定的图片目录
	 * 
	 */
	protected boolean copyImage(String image_url, String new_image_file) {

		String dirs = image_url.substring(7);

		try {
			File file_in = new File(new File(mirrorDir), dirs);
			File file_out = new File(new File(imageDir), new_image_file);

			FileInputStream in1 = new FileInputStream(file_in);
			FileOutputStream out1 = new FileOutputStream(file_out);

			byte[] bytes = new byte[1024];
			int c;
			while ((c = in1.read(bytes)) != -1)
				out1.write(bytes, 0, c);
			in1.close();
			out1.close();
			return (true);
		} catch (Exception e) {
			e.printStackTrace();
			return (false);
		}
	}




}
