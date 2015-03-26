package com.cyberway.weixin.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyberway.weixin.encrypt.AesException;
import com.cyberway.weixin.encrypt.WXBizMsgCrypt;
import com.cyberway.weixin.util.Constants;
/**
 * 
 * @com.cyberway.weixin.servlet.CallbackServlet
 * TODO : 开启回调模式
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015-2-4 下午04:01:55
 */
public class CallbackServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4440739483644821986L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 微信加密签名  
        String msg_signature = request.getParameter("msg_signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
        // 打印请求地址
        System.out.println("request=" + request.getRequestURL());  
        // 流
        PrintWriter out = response.getWriter();  
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        String result = null;  
        try {  
        	WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(Constants.TOKEN,Constants.encodingAESKey,Constants.CORPID);  
        	// 验证URL函数
        	result = wxcpt.VerifyURL(msg_signature, timestamp, nonce, echostr);  
        } catch (AesException e) {  
            e.printStackTrace();  
        }  
        if (result == null) {  
        	// result为空，赋予token
        	result = Constants.TOKEN;
        }
        // 拼接请求参数
        String str = msg_signature+" "+timestamp+" "+nonce+" "+echostr;
        // 打印参数+地址+result
        System.out.println("Exception:"+result+" "+ request.getRequestURL()+" "+"FourParames:"+str);
        out.print(result);
        out.close();  
        out = null;  
	}
}
