package com.cyberway.cms.exposed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cyberway.common.area.service.AreaService;
import com.cyberway.common.commoninfo.service.CommonInfoService;
import com.cyberway.common.domain.CoreArea;
import com.cyberway.common.domain.CoreCommonInfo;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreRole;
import com.cyberway.common.domain.CoreUser;
import com.cyberway.common.user.service.UserManagerService;
import com.cyberway.core.utils.ServiceLocator;


/**
 * @author caiw
 * ＣＭＳ公共资料对外接口
 *
 */
public class CommonServiceJsUtil {
	
	/**
	 * 根据用户id，获得用户对象
	 * @param userid
	 * @return
	 */
	public CoreUser getUser(Long userid){
		if(userid==null)
			return null;
		UserManagerService userService=(UserManagerService)ServiceLocator.getBean("userManagerService");
		return userService.getUserFromCache(userid.toString());
	}

	/**按类型获得常用信息
	 * @param type
	 * @return
	 */
	public Map getCommonInfos(String type){
		if(type==null)
			return new HashMap();
		Map minfos=new TreeMap();
		CommonInfoService infoService=(CommonInfoService)ServiceLocator.getBean("commonInfoService");
		List<CoreCommonInfo> infos=infoService.getCoreCommonInfos(type);
		if(infos!=null){
			for(CoreCommonInfo info:infos)
				minfos.put(info.getCode(), info.getContent());
		}
		return minfos;
	}
	public List getCommonInfosByType(String type){
		if(type==null)
			return new ArrayList();
		CommonInfoService infoService=(CommonInfoService)ServiceLocator.getBean("commonInfoService");
		List<CoreCommonInfo> infos=infoService.getCoreCommonInfos(type);
		return infos;
	}
	
	/**
	 * 根据市来查找下面的县
	 * @param id
	 * @return
	 */
	public List<CoreArea> getCounties(String id){
		if(id==null)
			return new ArrayList<CoreArea>();
		AreaService areaService=(AreaService)ServiceLocator.getBean("areaService");
		return areaService.getCounties(id);
	}
	
	/**
	 * 根据省来查找下面的市
	 * @param id
	 * @return
	 */
	public List<CoreArea> getCities(String id){
		if(id==null)
			return new ArrayList<CoreArea>();
		AreaService areaService=(AreaService)ServiceLocator.getBean("areaService");
		return areaService.getCities(id);
	}
	
	/**
	 * 查找省
	 * @param id
	 * @return
	 */
	public List<CoreArea> getProvinces(){
		AreaService areaService=(AreaService)ServiceLocator.getBean("areaService");
		return areaService.getProvinces();
	}
	
	/**
	 * 地区对象
	 * @param id
	 * @return
	 */
	public CoreArea get(String id){
		AreaService areaService=(AreaService)ServiceLocator.getBean("areaService");
		return areaService.get(id);
	}
	
	/**
	 * 获得指定部门对象
	 * @param deptid
	 * @return
	 */
	public CoreDept getDept(Long deptid){
		
		return null;
	}
	/**
	 * 获得指定角色对角
	 * @param roleid
	 * @return
	 */
	public CoreRole getRole(Long roleid){
		return null;
	}
}
