package com.cyberway.issue.crawler.replace;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.httpclient.URIException;

import com.cyberway.issue.crawler.datamodel.CrawlURI;
import com.cyberway.issue.crawler.extractor.ExtractorCSS;
import com.cyberway.issue.crawler.extractor.ExtractorJS;
import com.cyberway.issue.crawler.extractor.Link;
import com.cyberway.issue.crawler.framework.CrawlController;
import com.cyberway.issue.crawler.util.URLFilter;
import com.cyberway.issue.io.RecordingInputStream;
import com.cyberway.issue.io.ReplayInputStream;
import com.cyberway.issue.net.LaxURLCodec;
import com.cyberway.issue.net.UURI;
import com.cyberway.issue.net.UURIFactory;
import com.cyberway.issue.util.ArchiveUtils;
import com.cyberway.issue.util.DevUtils;
import com.cyberway.issue.util.TextUtils;

public class ReplaceURL extends Replace implements Serializable {
	public ReplaceURL() {

	}

	private static final ThreadLocal<Map<String, Matcher>> TL_MATCHER_MAP = new ThreadLocal<Map<String, Matcher>>() {
		protected Map<String, Matcher> initialValue() {
			return new HashMap<String, Matcher>(50);
		}
	};

	protected byte[] drainBuffer = new byte[16 * 1024];

	private static final int MAX_ATTR_NAME_LENGTH = 1024; // 1K;
	static final String A = "a";
	

	private static final int MAX_ELEMENT_LENGTH = 1024;

	static final int MAX_ATTR_VAL_LENGTH = 16384;// 16K;

	 static final String EACH_ATTRIBUTE_EXTRACTOR =
	      "(?is)\\b((href)|(action)|(on\\w*)" // 1, 2, 3, 4 
	     +"|((?:src)|(?:value)|(?:lowsrc)|(?:background)|(?:cite)|(?:longdesc)" // ...
	     +"|(?:usemap)|(?:profile)|(?:datasrc))" // 5
	     +"|(codebase)|((?:classid)|(?:data))|(archive)|(code)" // 6, 7, 8, 9
	     +"|(value)|(style)|(method)" // 10, 11, 12
	     +"|([-\\w]{1,"+MAX_ATTR_NAME_LENGTH+"}))" // 13
	     +"\\s*=\\s*"
	     +"(?:(?:\"(.{0,"+MAX_ATTR_VAL_LENGTH+"}?)(?:\"|$))" // 14
	     +"|(?:'(.{0,"+MAX_ATTR_VAL_LENGTH+"}?)(?:'|$))" // 15
	     +"|(\\S{1,"+MAX_ATTR_VAL_LENGTH+"}))"; // 16
	 
	 static final String HREF_JAVASCRIPT = "(javaScript:(.*)/(.*))";
	 static final String URL_JAVASCRIPT = "('/(.*)')";

	 static final String RELEVANT_TAG_EXTRACTOR =
         "(?is)<(?:((script[^>]*+)>.*?</script)" + // 1, 2
         "|((style[^>]*+)>.*?</style)" + // 3, 4
         "|(((meta)|(?:\\w{1,"+MAX_ELEMENT_LENGTH+"}))\\s+[^>]*+)" + // 5, 6, 7
         "|(!--.*?--))>"; // 8 
	  static final String LIKELY_URI_PATH ="(\\.{0,2}[^\\.\\n\\r\\s\"']*(\\.[^\\.\\n\\r\\s\"']+)+)";
	  static final String JAVASCRIPT_STRING_EXTRACTOR = "(\\\\{0,8}+(?:\"|\'))(\\S{0,"+UURI.MAX_URL_LENGTH+"}?)(?:\\1)";
	  static final String STRING_URI_DETECTOR ="(?:\\w|[\\.]{0,2}/)[\\S&&[^<>]]*(?:\\.|/)[\\S&&[^<>]]*(?:\\w|/)";
		    static final String CLASSEXT =".class";
		    static final String APPLET = "applet";
		    static final String BASE = "base";
		    static final String LINK = "link";
		    static final String FRAME = "frame";
		    static final String IFRAME = "iframe";
		    static final String AMP = "&";
		    static final String ESCAPED_AMP = "&amp;";
		    static final String WHITESPACE = "\\s";

	public String Replace(RecordingInputStream recis, UURI uuri)
			throws Exception {
		ReplayInputStream inputStream = recis.getContentReplayInputStream();

		/*BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		CharSequence cs = new String(buffer.toString());*/
		byte[] bytes = getBytes(inputStream);
		//inputStream.read(drainBuffer);
		CharSequence cs = new String(bytes, "utf-8");
		Matcher tags = TextUtils.getMatcher(RELEVANT_TAG_EXTRACTOR, cs);
		StringBuffer tagsb = new StringBuffer();
		while (tags.find()) {
			if (tags.start(5) > 0) {
				int start5 = tags.start(5);
				int end5 = tags.end(5);
				int start6 = tags.start(6);
				int end6 = tags.end(6);
				CharSequence element = cs.subSequence(start6, end6);
				final String elementStr = element.toString();
				CharSequence cs1 = cs.subSequence(start5, end5);
				Matcher attr1 = TextUtils.getMatcher(EACH_ATTRIBUTE_EXTRACTOR,
						cs1);
				String str = "";
				while (attr1.find()) {
					URLFilter urlFilter = new URLFilter();
					int valueGroup = (attr1.start(14) > -1) ? 14 : (attr1
							.start(15) > -1) ? 15 : 16;
					int start = attr1.start(valueGroup);
					int end = attr1.end(valueGroup);
					CharSequence value = cs1.subSequence(start, end);

					value = TextUtils.unescapeHtml(value);
					if (attr1.start(2) > -1 && elementStr.toLowerCase().equals(A)) {
						
						Matcher attr2 = TextUtils.getMatcher(HREF_JAVASCRIPT,value);
						if(attr2.find())
						{
							if(attr2.start(1) > -1)
							{
								Matcher attr3 = TextUtils.getMatcher(URL_JAVASCRIPT,attr2.group(1));
								if(attr3.find())
								{
									if(attr2.start(1) > -1)
									  value = attr3.group(1);
								}
							}
							
						}
						// HREF
					//String newURL = urlFilter.ExtracetUrl(value.toString(), uuri);
					//str = tags.group().replace(value.toString().replaceAll("'", ""), newURL);
						str = tags.group();
						break;
					}
					else if (attr1.start(5) > -1 && value.toString().startsWith("images/"))
					{
						str = tags.group().replace(value.toString(), "/"+value.toString());
						System.out.println(str+"---------");
						break;
					}
					else
					{
						str = tags.group();
					}
				}
				tags.appendReplacement(tagsb, str);
			}
			/*else if(tags.start(1) > 0) {
				int start = tags.start(1);
                int end = tags.end(1);
                String str = processScript(cs.subSequence(start, end),tags.end(2) - start);
                tags.appendReplacement(tagsb, str);
			}*/
		}
		if (tagsb.length() > 0) {
			tags.appendTail(tagsb);
			System.out.println(tagsb);
		}
		return tagsb.toString();
	}
	protected String processScript(CharSequence sequence,int endOfOpenTag) {
       
      //  processGeneralTag(sequence.subSequence(0,6),sequence.subSequence(0,endOfOpenTag));
        
        return sequence.subSequence(0,endOfOpenTag)+considerStrings(sequence.subSequence(endOfOpenTag, sequence.length()));
    }
	protected void processGeneralTag(CharSequence element,CharSequence cs) 
	{
		System.out.println(cs.toString()+"-----------javascript--------");
		 Matcher attr = TextUtils.getMatcher(EACH_ATTRIBUTE_EXTRACTOR, cs);
		    String codebase = null;
	        ArrayList<String> resources = null;
	        CharSequence action = null;
	        CharSequence actionContext = null;
	        CharSequence method = null; 
	        final String elementStr = element.toString();
	        while (attr.find()) {
	        	 int valueGroup =(attr.start(14) > -1) ? 14 : (attr.start(15) > -1) ? 15 : 16;
	             int start = attr.start(valueGroup);
	             int end = attr.end(valueGroup);
	             CharSequence value = cs.subSequence(start, end);
	             value = TextUtils.unescapeHtml(value);
	             if (attr.start(2) > -1) {
	            	 // HREF
	                 CharSequence context =Link.elementContext(element, attr.group(2));
	                System.out.println(value+"------------href----");
	             }
	             else if (attr.start(3) > -1) {
	            	 System.out.println(value+"---------ACTION-------");
	             }
	             else if (attr.start(4) > -1) {
	            	 System.out.println(value+"---------on-------");
	             }
	             else if (attr.start(5) > -1) {
	            	 System.out.println(value+"---------SRC-------");
	             }
	             else
	             {
	            	 System.out.println(value+"---------other-------");
	             }
	        }
		 
	}
	 public static String considerStrings(CharSequence cs) {
		 long foundLinks = 0;
		 String replaceStr = cs.toString();
	        Matcher strings =TextUtils.getMatcher(JAVASCRIPT_STRING_EXTRACTOR, cs);
	        while(strings.find()) {
	        	CharSequence subsequence =cs.subSequence(strings.start(2), strings.end(2));
	            Matcher uri =TextUtils.getMatcher(STRING_URI_DETECTOR, subsequence);
	            if(uri.matches()) {
	            	String string = uri.group();
	                string = speculativeFixup(string);
	                replaceStr.replace(string, "www.qq.com");
	                System.out.println(replaceStr+"--------script-----------"+string);
	            }
	        }
		return replaceStr; 
	 }
	 public static String speculativeFixup(String string) {
	        String retVal = string;
	        retVal = TextUtils.replaceAll(ESCAPED_AMP, retVal, AMP);
	        Matcher m = TextUtils.getMatcher("(?i)^https?%3A.*",retVal); 
	        if(m.matches()) {
	            try {
	                retVal = LaxURLCodec.DEFAULT.decode(retVal);
	            } catch (DecoderException e) {
	               e.printStackTrace();
	            }
	        }
	       /* TextUtils.recycleMatcher(m);
	        m = TextUtils.getMatcher("^[^\\./:\\s%]+\\.[^/:\\s%]+\\.([^\\./:\\s%]+)(/.*|)$", retVal);
	        if(m.matches()) {
	            if(ArchiveUtils.isTld(m.group(1))) { 
	                String schemePlus = "http://";       
	                // if on exact same host preserve scheme (eg https)
	                try {
	                    if (retVal.startsWith(curi.getUURI().getHost())) {
	                        schemePlus = curi.getUURI().getScheme() + "://";
	                    }
	                } catch (URIException e) {
	                    // error retrieving source host - ignore it
	                }
	                retVal = schemePlus + retVal; 
	            }
	        }*/
	        TextUtils.recycleMatcher(m);
	        
	        return retVal; 
	    }
	
	private InputStream StringToInputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	public static byte[] getBytes(InputStream is) throws Exception {
		byte[] data = null;

		Collection chunks = new ArrayList();
		byte[] buffer = new byte[1024 * 1000];
		int read = -1;
		int size = 0;

		while ((read = is.read(buffer)) != -1) {
			if (read > 0) {
				byte[] chunk = new byte[read];
				System.arraycopy(buffer, 0, chunk, 0, read);
				chunks.add(chunk);
				size += chunk.length;
			}
		}

		if (size > 0) {
			ByteArrayOutputStream bos = null;
			try {
				bos = new ByteArrayOutputStream(size);
				for (Iterator itr = chunks.iterator(); itr.hasNext();) {
					byte[] chunk = (byte[]) itr.next();
					bos.write(chunk);
				}
				data = bos.toByteArray();
			} finally {
				if (bos != null) {
					bos.close();
				}
			}
		}
		return data;
	}

}
