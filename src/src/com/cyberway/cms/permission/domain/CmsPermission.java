package com.cyberway.cms.permission.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

import com.cyberway.cms.Constants;

public class CmsPermission implements DynaBean, Serializable {
	static class DynaClass extends BasicDynaClass
	{
		public DynaClass()
		{
			super(null, null, null);
		}

		public DynaClass(String name, Class dynaBeanClass)
		{
			super (name, dynaBeanClass, null);
		}
		
		@Override
		public DynaProperty[] getDynaProperties()
		{
			throw new UnsupportedOperationException ();
		}
		
		@Override
		public DynaProperty getDynaProperty(String name)
		{
			return new DynaProperty (name);
		}
	}
	
	private Long oid;
	private int roleType;
	private Long roleId;
	private String roleName;
	private Long objectId;

	private int objectType;
	private int setType;
	private int isExtends;
	private Long managerRole;//表示当前对象的角色　站点管理员，站点设计者
	private String managerName;//输出选择的对象
	private int permType;//0--表示用户去加权限，1--表示系统自动增加的
	private DynaClass _dynaClass = new DynaClass ();
	
	private List<CmsPermResource> permResources =new ArrayList<CmsPermResource>();

	public int getIsExtends() {
		return isExtends;
	}

	public void setIsExtends(int isExtends) {
		this.isExtends = isExtends;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}


	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public List<CmsPermResource> getPermResources() {
		return permResources;
	}

	public void setPermResources(List<CmsPermResource> permResources) {
		this.permResources = permResources;
	}
	
	class FindByNamePredicate implements Predicate
	{
		private String _name;
		
		public FindByNamePredicate (String name)
		{
			Validate.notNull(name);
			
			_name = name;
		}
		
		public boolean evaluate(Object obj)
		{
			return ObjectUtils.equals(_name, ((CmsPermResource)obj).getResourceCode());
		}
	}  		
	public Object get(String name)
	{
		if (BeanUtils.getPropertyDescriptor(this.getClass(), name) != null)
		{
			return new BeanWrapperImpl (this).getPropertyValue(name);
		}		
		else
		{
			CmsPermResource item = (CmsPermResource)CollectionUtils.find(getPermResources(), new FindByNamePredicate (name));
			String chechbox=null;
			if(item==null)				
				chechbox="<input type='checkbox' name='"+name+"' value='"+this.oid+"' onclick='checkOnePermession(this);' onChange='ECSideUtil.updateEditCell(this)'/>";//onChange='ECSideUtil.updateEditCell(this)'
			else				
				chechbox="<input type='checkbox' name='"+name+"' value='"+this.oid+"' checked onclick='checkOnePermession(this);' onChange='ECSideUtil.updateEditCell(this)'/>";
			return chechbox;
		}
	}
	/**
	 * 根据资源编码，获得对应的CmsPermResource对象
	 * @param name
	 * @return
	 */
	public CmsPermResource getCmsPermResourceByCode(String name){
		return (CmsPermResource)CollectionUtils.find(getPermResources(), new FindByNamePredicate (name));
	}
	/**
	 * 根据资源编码，删除指定对象
	 * @param name
	 */
	public void removeCmsPermResourceByCode(String name){
		CmsPermResource cpr=(CmsPermResource)CollectionUtils.find(getPermResources(), new FindByNamePredicate (name));
		if(cpr!=null)
			getPermResources().remove(cpr);
	}
	/**
	 * 根据资源编码，根据对象
	 * @param name
	 * @param cpr
	 */
	public void updateCmsPermResourceByCode(String name,CmsPermResource cpr){
		CmsPermResource item = (CmsPermResource)CollectionUtils.find(getPermResources(), new FindByNamePredicate (name));
		if (cpr == null)
		{
			if (item != null)
			{
				getPermResources().remove(item);
			}
		}
		else
		{
			if (item == null)
			{				
				getPermResources().add(cpr);
			}
			else
			{
				//先删除,再插入
				getPermResources().remove(item);
				getPermResources().add(cpr);
			}
			}
	}
	public void set(String name, Object value)
	{
		if (BeanUtils.getPropertyDescriptor(this.getClass(), name) != null)
		{
			new BeanWrapperImpl (this).setPropertyValue(name, value);
		}		
		else
		{
			CmsPermResource item = (CmsPermResource)CollectionUtils.find(getPermResources(), new FindByNamePredicate (name));
			if (value == null)
			{
				if (item != null)
				{
					getPermResources().remove(item);
				}
			}
			else
			{
				if (item == null)
				{
					item = new CmsPermResource ();
					item.setResourceCode(name);					
					item.setPermission(this);
					getPermResources().add(item);
				}
				else
				{
					//原来存在的，就不用管
					//item.set(value);
				}
			}
		}
	}
	
	public void set(String name, int index, Object value)
	{
		throw new UnsupportedOperationException ();
	}
	
	public void set(String name, String key, Object value)
	{
		throw new UnsupportedOperationException ();
	}
	public Object get(String name, int index)
	{
		throw new UnsupportedOperationException ();
	}
	
	public Object get(String name, String key)
	{
		throw new UnsupportedOperationException ();
	}
	public boolean contains(String name, String key)
	{
		return false;
	}
	
	public void remove(String s, String s1)
	{
		throw new UnsupportedOperationException ();
	}
	public DynaClass getDynaClass()
	{
		return _dynaClass;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getSetType() {
		return setType;
	}

	public void setSetType(int setType) {
		this.setType = setType;
	}

	public int getPermType() {
		return permType;
	}

	public void setPermType(int permType) {
		this.permType = permType;
	}

	public Long getManagerRole() {
		return managerRole;
	}

	public void setManagerRole(Long managerRole) {
		this.managerRole = managerRole;
	}

	public String getManagerName() {
		StringBuffer sb=new StringBuffer();
		//获得可选择权限类型
		//站点：管理员，设计者，编辑者
		//频道：栏目管理者，设计者
		//文档：编辑者，作者，读者
		String[] types=null;
		
		if(objectType==1){//站点
			types=new String[]{"设计者","管理员"};
			//types=new String[]{"管理员","设计者","编辑者"};
		}else if(objectType==2){//频道
			types=new String[]{"栏目管理者","栏目设计者"};
		}else if(objectType==3){//文档				
			types=new String[]{"编辑者","作者","读者"};
			
		}
		String checked=" selected='selected' ";
		if(types!=null){
			sb.append("<select name='managerName' id='"+this.oid+"' onchange='setRoleTypePermessions(this);'>");
			if(managerRole!=null)
				checked="";
			sb.append("<option value=''"+checked+"> </option>");
			for(int i=0;i<types.length;i++){
				//检测是否为已选中状态
				if(managerRole!=null && managerRole.intValue()==i)
					checked=" selected='selected' ";
				else
					checked=" ";
				if(!(objectType==3 && this.setType==3 && i==1))//去掉文档的作者 amway
				 sb.append("<option value='"+i+"' "+checked+">"+types[i]+"</option>");
			}
			
			sb.append("</select>");
		}		
		managerName=sb.toString();
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
}
