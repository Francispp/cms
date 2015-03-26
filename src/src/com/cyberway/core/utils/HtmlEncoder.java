/*
 * Created on 2005-6-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cyberway.core.utils;

/**
 * @author lan
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HtmlEncoder {

    private static final String[] htmlCode = new String[256];

    static {
                for (int i = 0; i < 10; i++) {
                        htmlCode[i] = "&#00" + i + ";";
                }

                for (int i = 10; i < 32; i++) {
                        htmlCode[i] = "&#0" + i + ";";
                }

                for (int i = 32; i < 128; i++) {
                        htmlCode[i] = String.valueOf((char)i);
                }

                // Special characters
//                htmlCode['\n'] = "<BR>";
                htmlCode['\"'] = "&quot;"; // double quote
                htmlCode['&'] = "&amp;"; // ampersand
                htmlCode['<'] = "&lt;"; // lower than
                htmlCode['>'] = "&gt;"; // greater than

                for (int i = 128; i < 256; i++) {
                        htmlCode[i] = "&#" + i + ";";
                }
    }

    /**
     * <p>Encode the given text into html.</p>
     *
     * @param string the text to encode
     * @return the encoded string
     *
     */
    public static String encode(String string) {
                int n = string.length();
                char character;
                StringBuffer buffer = new StringBuffer();
        // loop over all the characters of the String.
                for (int i = 0; i < n; i++) {
                        character = string.charAt(i);
                        // the Htmlcode of these characters are added to a StringBuffer one by one
                        try {
                                buffer.append(htmlCode[character]);
                        }
                        catch(ArrayIndexOutOfBoundsException aioobe) {
                                buffer.append(character);
                        }
            }
        return buffer.toString();
    }
    
    /**
     * @param str
     * @return
     */
    public static String TransactSQLInjection(String str)
    {
          return str.replaceAll(".*([';]+|(--)+).*", " ");
    }
 
    /**
     * 解决参数Cross-Site Scripting
     * @param str
     * @return
     */
    public static String HTMLEncode(String str){
    	if(str!=null){
    		String str1= HtmlEncoder.encode(str);
    		//str1=HtmlEncoder.TransactSQLInjection(str1);
    		return str1;
    	}
    	return "";
    }
     
	public static void main(String[] args)throws Exception {
    	 /*String str=HtmlEncoder.encode("timeCreated%3csCrIpT%3ealert(66582)%3c%2fsCrIpT%3e");
    	 str=HtmlEncoder.TransactSQLInjection(str);*/
    		 String str=com.cyberway.core.utils.HtmlEncoder.HTMLEncode("timeCreated%3csCrIpT%3ealert(66582)%3c%2fsCrIpT%3e");
    	 System.out.print(str);
    }
}

