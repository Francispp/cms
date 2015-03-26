package com.cyberway.weixin.test;
import net.sf.json.JSONObject;

import com.cyberway.weixin.api.MenuAPI;
import com.cyberway.weixin.business.menu.domain.Button;
import com.cyberway.weixin.business.menu.domain.CommonButton;
import com.cyberway.weixin.business.menu.domain.ComplexButton;
import com.cyberway.weixin.business.menu.domain.Menu;
import com.cyberway.weixin.util.AccessTokenUtil;
import com.cyberway.weixin.util.ConnectionUtil;
import com.cyberway.weixin.util.Constants;
/**
 * 
 * @com.cyberway.weixin.test.Test6
 * TODO :
 * @author yanruqian
 * @version 1.0.0
 * @createDate：2015年2月28日 下午4:51:23
 */
public class Test6 {

	 public static void main(String[] args) {    
		 // 调用接口获取access_token     
		String access_token = AccessTokenUtil.getAccessToken(Constants.CORPID,Constants.SECRET).getToken();
		 if (null != access_token) {           
			 // 调用接口创建菜单           
			 int result = createMenu(getMenu(),access_token);       
			 // 判断菜单创建结果         
			 if (0 == result) {      
				 System.out.println("菜单创建成功！");           
			 }else{          
				 System.out.println("菜单创建失败，错误码：" + result);      
			 }   
		 }
	 }
	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
	    int result = 0;
	   
	    // 拼装创建菜单的url
	    String url = MenuAPI.CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
	    // 将菜单对象转换成json字符串
	    String jsonMenu = JSONObject.fromObject(menu).toString();
	    // 调用接口创建菜单
	    JSONObject jsonObject = ConnectionUtil.getHttpRequest(url, "POST", jsonMenu);
	 
	    if (null != jsonObject) {
	        if (0 != jsonObject.getInt("errcode")) {
	            String error = String.format("创建菜单失败 errcode:{%s} errmsg:{%s}",jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				System.out.println(jsonObject+"-->"+error);
	        }else{
	        	System.out.println("创建成功 ："+jsonObject);
	        }
	    }
	 
	    return result;
	}
	private static Menu getMenu() {
        CommonButton btn11 = new CommonButton();
        btn11.setName("签到");
        btn11.setType("click");
        btn11.setKey("11");
 
        CommonButton btn12 = new CommonButton();
        btn12.setName("签退");
        btn12.setType("click");
        btn12.setKey("12");
 
        CommonButton btn21 = new CommonButton();
        btn21.setName("外勤签到/退");
        btn21.setType("click");
        btn21.setKey("21");
 
        CommonButton btn22 = new CommonButton();
        btn22.setName("外勤计划");
        btn22.setType("click");
        btn22.setKey("22");
 
 
        CommonButton btn31 = new CommonButton();
        btn31.setName("外勤记录");
        btn31.setType("click");
        btn31.setKey("31");
 
        CommonButton btn32 = new CommonButton();
        btn32.setName("考勤记录");
        btn32.setType("click");
        btn32.setKey("32");
 
 
        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("签到/退");
        mainBtn1.setSub_button(new CommonButton[] { btn11, btn12 });
 
        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("外勤");
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22 });
 
        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("记录查看");
        mainBtn3.setSub_button(new CommonButton[] { btn31, btn32 });
 
        /**
         * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         * 
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
        return menu;
        }
}
