package com.cyberway.cms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.AppletTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

/**
 * 解析html
 * @author caiw
 *
 */
public class ParseHtmlUtil {

	/**
	 * 解析普通文本节点.
	 * 
	 * @param content
	 *            html内容
	 * @throws ParserException
	 */
	public static String parseText(String content) throws ParserException {
		Parser myParser;
		Node[] nodes = null;
		StringBuffer sb = new StringBuffer();
		myParser = Parser.createParser(content, null);

		// nodes = myParser.extractAllNodesThatAre(TextNode.class); //exception
		// could be thrown here
		nodes = (myParser.extractAllNodesThatMatch(new NodeClassFilter(
				TextNode.class))).toNodeArray();
		for (int i = 0; i < nodes.length; i++) {
			Node anode = (Node) nodes[i];

			if (anode instanceof TextNode) {
				TextNode textnode = (TextNode) nodes[i];
				String line = textnode.toPlainTextString().trim();
				if (line.equals("") || line.startsWith("<!--"))
					continue;
				// line=line.replace("&nbsp;", " ");
				sb.append(line.replace("&nbsp;", " "));
				// System.out.println(line.replace("&nbsp;", " "));
			}
		}
		return sb.toString();
	}

	/**
	 * 得到普通文本和链接的内容.
	 * 
	 * 使用了过滤条件.
	 */
	public static void parseHtmltoTextLink(String content) throws ParserException {
		Parser myParser;
		NodeList nodeList = null;

		myParser = Parser.createParser(content, "GBK");

		NodeFilter textFilter = new NodeClassFilter(TextNode.class);
		NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);

		// 暂时不处理 meta
		// NodeFilter metaFilter = new NodeClassFilter(MetaTag.class);

		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { textFilter, linkFilter });

		nodeList = myParser.parse(lastFilter);

		Node[] nodes = nodeList.toNodeArray();

		for (int i = 0; i < nodes.length; i++) {
			Node anode = (Node) nodes[i];

			String line = "";
			if (anode instanceof TextNode) {
				TextNode textnode = (TextNode) anode;
				// line = textnode.toPlainTextString().trim();
				line = textnode.getText();
			} else if (anode instanceof LinkTag) {
				LinkTag linknode = (LinkTag) anode;

				line = linknode.getLink();
				// @todo 过滤jsp标签:可以自己实现这个函数
				// line = StringFunc.replace(line, "<%.*%>", "");
			} else if (anode instanceof AppletTag) {
				AppletTag appletnode = (AppletTag) anode;
				line = appletnode.getAppletClass() + "     "
						+ appletnode.getArchive();
			} else if (anode instanceof ImageTag) {
				ImageTag imagenode = (ImageTag) anode;
				line = imagenode.getImageURL();
			}

			if (isTrimEmpty(line))
				continue;

			// System.out.println(line);
		}
	}

	/**
	 * 去掉左右空格后字符串是否为空
	 * 
	 * @param astr
	 *            String
	 * @return boolean
	 */
	public static boolean isTrimEmpty(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		}
		if (isBlank(astr.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串是否为空:null或者长度为0.
	 * 
	 * @param astr
	 *            源字符串.
	 * @return boolean
	 */
	public static boolean isBlank(String astr) {
		if ((null == astr) || (astr.length() == 0)) {
			return true;
		} else {
			return false;
		}
	}
    /**   
	 *   读取一个文件到字符串里.   
	 *     
	 *   @param   sFileName     文件名   
	 *   @param   sEncode       String   
	 *   @return   文件内容   
	 */
	public static String readTextFile(String sFileName, String sEncode) {
		StringBuffer sbStr = new StringBuffer();
		try {
			File ff = new File(sFileName);
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					ff), sEncode);
			BufferedReader ins = new BufferedReader(read);

			String dataLine = "";
			while ((dataLine = ins.readLine()) != null) {
				sbStr.append(dataLine);
				sbStr.append("\r\n");
			}
			ins.close();
		} catch (Exception e) {
			//LogMan.error("read   Text   File   Error",   e);   
		}
		return sbStr.toString();
	} 	
}
