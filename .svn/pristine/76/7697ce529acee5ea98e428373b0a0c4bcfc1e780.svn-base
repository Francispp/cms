package com.cyberway.cms.exposed;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author caiw
 * 动态输出到html
 *
 */
public class HTMLJsUtil {
	private JspWriter _output;
	protected Log logger = LogFactory.getLog(getClass());

	private HTMLJsUtil(){// 禁止不带参数初始化此类
		
	}
	/**
	 *  初始化输出
	 * @param response
	 */
	public HTMLJsUtil(PageContext pc){
		_output=pc.getOut();
	}

	/**
	 * 输出对象
	 * @param b
	 */
	public void print(boolean b){
		try{
			 _output.print(b);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}
	/**
	 * 输出对象
	 * @param b
	 */
	public void print(char c){
		try{
			 _output.print(c);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}
	/**
	 * 输出对象
	 * @param b
	 */
	public void print(double d){
		try{
			 _output.print(d);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}	
	/**
	 * 输出对象
	 * @param b
	 */
	public void print(float f){
		try{
			 _output.print(f);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}	
	/**
	 * 输出对象
	 * @param b
	 */
	public void print(int i){
		try{
			 _output.print(i);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}	
	/**
	 * 输出对象
	 * @param b
	 */
	public void print(long l){
		try{
			 _output.print(l);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}
	/**
	 * 输出对象
	 * @param s
	 */
	public void print(String s){
		try{
		 if(s!=null)
		  _output.print(s);
        }catch(IOException ioe){
        	logger.error("输出对象出错！");
		    ioe.printStackTrace();	
		}
	}	
	/**
	 * 换行
	 * @param b
	 */
	public void println(){
		try{
			 _output.println();
	        }catch(IOException ioe){
	        	logger.error("换行出错！");
			    ioe.printStackTrace();	
			}
	}	
	/**
	 * 输出对象
	 * @param b
	 */
	public void println(boolean b){
		try{
			 _output.println(b);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}
	/**
	 * 输出对象
	 * @param b
	 */
	public void println(char c){
		try{
			 _output.println(c);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}
	/**
	 * 输出对象
	 * @param b
	 */
	public void println(double d){
		try{
			 _output.println(d);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}	
	/**
	 * 输出对象
	 * @param b
	 */
	public void println(float f){
		try{
			 _output.println(f);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}	
	/**
	 * 输出对象
	 * @param b
	 */
	public void println(int i){
		try{
			 _output.println(i);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}	
	/**
	 * 输出对象
	 * @param b
	 */
	public void println(long l){
		try{
			 _output.println(l);
	        }catch(IOException ioe){
	        	logger.error("输出对象出错！");
			    ioe.printStackTrace();	
			}
	}
	/**
	 * 输出对象
	 * @param s
	 */
	public void println(String s){
		try{
		 if(s!=null)
		  _output.println(s);
        }catch(IOException ioe){
        	logger.error("输出对象出错！");
		    ioe.printStackTrace();	
		}
	}	
}
