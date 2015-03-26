package com.cyberway.common.dept.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.cyberway.common.base.objects.Constants;
import com.cyberway.common.domain.CoreDept;
import com.cyberway.common.domain.CoreOrg;
import com.cyberway.common.service.BizUtilService;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.BeanUtil;
import com.cyberway.core.utils.StringUtil;

public class DeptManagerService extends HibernateEntityDao<CoreDept> {
	private BizUtilService bizUtilService;	//公用业务操作类
 /**
  *检测指定部门是否能删除
 * @param ids
 * @return
 */
public boolean isCanDeleteDept(List<Long> ids){
	 boolean isLast=true;
 	if(ids!=null){
 		
		for (Long id : ids){
         //若下面有子部门，则不能删除
			List subDepts=find("from CoreDept where pdeptid=?",new Object[]{id});
			if(subDepts!=null&&subDepts.size()>0)
				return false;
         // 若该部门下有用户，则不能删除
			CoreDept dept=this.get(id);
			if(dept!=null&&dept.getCoreUsers().size()>0)
				return false;
			
			List list = find("select oid from WebUser where deptid=?", id);
			if(list.size()>0){
				return false;
			}
		}
		
		
	}
	 return isLast;
 }
 /**
  * 返回指定部门及上级部门
 * @param deptid
 * @return
 * @throws Exception
 */
public Map getSuperDepts(Long deptid)throws Exception{
	 //Map depts=new TreeMap(); 
	 if(deptid==null)
		 return new TreeMap();
	 CoreDept dept=this.get(deptid);
	 if(dept!=null){
		 Map depts=null;
		 if(dept.getPdeptid()!=null){
			 depts=this.getSuperDepts(dept.getPdeptid());
			 
		 }else{
			 depts=new TreeMap();
		 }
		 depts.put(dept.getDeptid(), dept);
		 return depts;
	 }	 	 
	 throw new Exception("部门id:"+deptid+"不存在！");
 }
/**
 * 粘贴指定部门到某部门下
 * @param deptid
 * @param superid
 * @param type
 * @return
 * @throws Exception
 */
public boolean pasteDeptTo(String deptid_str,String  superid_str,Integer type)throws Exception{
	boolean succ=false;
	if(StringUtil.isEmpty(deptid_str)||StringUtil.isEmpty(superid_str)||type==null)
		return succ;
	Long deptid=new Long(deptid_str);
	Long superid=null;
	CoreOrg corePortal=null;
	if(StringUtil.isNumber(superid_str)){//指定父部门id
	 superid=new Long(superid_str);
	 corePortal=this.get(superid).getCoreOrg();
	}else{//指定机构
		if(!StringUtil.isEmpty(superid_str))
			corePortal=(CoreOrg)this.get(CoreOrg.class, new Long(superid_str.substring(2)));
	}
	CoreDept dept=getObject(deptid);
	if(type.intValue()==1){//剪切
		
	 dept.setPdeptid(superid);
	 dept.setCoreOrg(corePortal);
	 this.saveOrUpdate(dept);
	 succ=true;
	}else if(type.intValue()==0){//复制

		succ=copyDepts(dept,superid,corePortal);
	}
	return succ;
}
//复制部门（包括其下级部门）
private boolean copyDepts(CoreDept dept,Long pdeptid,CoreOrg corePortal)throws Exception{
	if(dept==null)
		 return false;
	CoreDept new_dept=new CoreDept();
	BeanUtil.updateObject(dept,new_dept);
	new_dept.setDeptid(null);
	new_dept.setPdeptid(pdeptid);
	new_dept.setCoreOrg(corePortal);
	//若复制到相同节点下，增加标志位
    if(pdeptid!=null&&dept.getPdeptid()!=null
    		&&pdeptid.longValue()==dept.getPdeptid().longValue()){
		  String rname=dept.getDeptname();
		  if(!StringUtil.isEmpty(rname))
			rname+=Constants.COPY_OBJECT_NAME_ADD_SIGN;
		  else
			rname=Constants.COPY_OBJECT_NAME_ADD_SIGN;
		  new_dept.setDeptname(rname);//增加复制时的标志	
	     }	
	this.save(new_dept);
	Set subDepts=dept.getSubDepts();
	if(subDepts!=null){
		Iterator it=subDepts.iterator();
		while(it.hasNext()){
			CoreDept temp=(CoreDept)it.next();
			copyDepts(temp,new_dept.getDeptid(),corePortal);
		}		
	}
	return true;	
}

public void setBizUtilService(BizUtilService bizUtilService) {
	this.bizUtilService = bizUtilService;
}
}
