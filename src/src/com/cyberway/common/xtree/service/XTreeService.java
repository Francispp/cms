package com.cyberway.common.xtree.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import com.cyberway.common.xtree.DefineTree;
import com.cyberway.common.xtree.XTree;
import com.cyberway.common.xtree.XTree.Node;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.StringUtil;

/**
 * 树的操作
 * @author caiw
 * @author libin
 *
 */
@SuppressWarnings("unchecked")
public class XTreeService extends HibernateEntityDao{
    public static final String DEFINE_XTREE_VARIABLE="DEFINE_XTREE_VARIABLE";

	public String getXTreeByParentNode(DefineTree defTree){
		if (defTree == null)
			return null;

		Long parentId = defTree.getParentNodeId();
		if (parentId == null)
			return null;
		logger.debug("\n parentid:"+parentId);
		
		// 树对象
		XTree xtree = new XTree();

		Object objNode = this.get(defTree.getPojoTree(),parentId);
		try {
			Object subObj = PropertyUtils.getProperty(objNode, defTree.getSubNodeSetPropertyName());
			if (subObj != null) {
				Set setSubObj = (Set) subObj;
				Iterator itaLabel = setSubObj.iterator();
				while (itaLabel.hasNext()) {
					Node node = xtree.initNode();
					Object objSubNode = itaLabel.next();
					String subNodeName = (String) PropertyUtils.getProperty(objSubNode, defTree.getNodePropertyName());
					node.setText(subNodeName);
					logger.debug("\n" + subNodeName);

					// 设置节点展开操作
					if (!StringUtil.isEmpty(defTree.getSrc())) {
						String strSrc = defTree.getSrc();
						strSrc = strSrc.replaceAll("&", "&amp;");
						strSrc = StringUtil.parseParam(strSrc, objSubNode);
						node.setSrc(strSrc);
					}

					// 设置节点单击事件
					if (!StringUtil.isEmpty(defTree.getAction())) {
						String strSrc = defTree.getAction();
						strSrc = strSrc.replaceAll("&", "&amp;");
						strSrc = StringUtil.parseParam(strSrc, objSubNode);
						node.setAction(strSrc);
					}
					// 设置节点按下鼠标事件
					if (!StringUtil.isEmpty(defTree.getNodeOnMouseup())) {
						String strOnMouseup = defTree.getNodeOnMouseup();
						strOnMouseup = strOnMouseup.replaceAll("&", "&amp;");
						strOnMouseup = StringUtil.parseParam(strOnMouseup, objSubNode);
						node.setOnMouseup(strOnMouseup);
					}
					//
					if (!StringUtil.isEmpty(defTree.getIcon())) {
						node.setIcon(defTree.getIcon());
					}

					//
					if (!StringUtil.isEmpty(defTree.getOpenIcon())) {
						node.setOpenIcon(defTree.getOpenIcon());
					}
					//是否带单选框
					if (!StringUtil.isEmpty(defTree.getRadio())) {
						node.setRadio(defTree.getRadio());
					}
					//是否带多选框
					if (!StringUtil.isEmpty(defTree.getCheckBox())) {
						node.setCheckBox(defTree.getCheckBox());
					}					
					xtree.addNode(node);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//throw e;
		}
		return xtree.getXmlTree();
	}


	/**
	 * 树集合转收缩树集合

	 * @param treeList
	 *            需要处理的数据集合
	 * @param treeClass
	 *            树节点的实体对象名称            
	 * @param idName
	 *            id名称
	 * @param parentName
	 *            父id名称
	 * @param tempParentName
	 *            自定义父ID名称,用于记录断层树的层次关系ID,需要在实体对象中预留该属性
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	public List buildTreeList(List treeList, Class treeClass, String idName,
			String parentName, String tempParentName)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		
		HashMap<String, List> saveFlagMap = new HashMap();
		Iterator<Object> treeIt = treeList.iterator();

		// 处理后的树的数据集合
		List list = new ArrayList();
		// 遍历过滤后的集合
		while (treeIt.hasNext()) {
			Object treeObject = treeIt.next();
			String id = BeanUtils.getProperty(treeObject, idName); // 得到结点
			String parentid = BeanUtils.getProperty(treeObject, parentName); // 得到父结点
			Iterator<Object> tempTreeIt = treeList.iterator();
			boolean flag = false;
			// 内遍历集合
			while (tempTreeIt.hasNext()) {
				Object tempObject = tempTreeIt.next();
				String tempId = BeanUtils.getProperty(tempObject, idName); // 得到结点ID
				// 父结点是否存在
				if (parentid != null && parentid.equals(tempId)) {
					flag = true;
					break;
				}
			}
			// 如果上级结点是否存在于过滤后的结点内
			if (flag) {
				BeanUtils.setProperty(treeObject, tempParentName, parentid);
			} else {
				boolean isExistParentFlag = false;
				String tempUpParentid = null; // 定义收缩父结点
				if (parentid == null) {
					isExistParentFlag = false;
				} else {
					List parentidList = null;
					boolean parentidFlag = false;
					for (Object obj : saveFlagMap.keySet()) {
						if (obj.equals(parentid)) {
							flag = true;
							parentidList = saveFlagMap.get(obj);
							break;
						}
					}
					if (!parentidFlag) {
						parentidList = new ArrayList();
						// 获取此结点的父结点集合
						this.getTreeNodeAllParent(new Long(id), parentName,
								treeClass, parentidList);
					}
					saveFlagMap.put(parentid, parentidList); // 给标志集合添加一个标志
					Iterator<String> parentidIt = parentidList.iterator();
					// 遍历父结点集合
					while (parentidIt.hasNext()) {
						isExistParentFlag = false;
						String tempParentid = parentidIt.next(); // 得到父结点集合中的父结点
						Iterator<Object> treeListIt = treeList.iterator();
						// 遍历过滤后的集合
						while (treeListIt.hasNext()) {
							Object tempTreeObject = treeListIt.next();
							tempUpParentid = BeanUtils.getProperty(
									tempTreeObject, idName); // 过滤后的父结点
							if (tempParentid.equals(tempUpParentid)) {
								isExistParentFlag = true;
								break;
							}
						}
						if (isExistParentFlag)
							break;
					}
				}
				// 是否非顶级结点
				if (isExistParentFlag) {
					BeanUtils.setProperty(treeObject, tempParentName,
							tempUpParentid);
				} else {
					BeanUtils.setProperty(treeObject, tempParentName, "0");
				}
			}
			list.add(treeObject);

		}
		
		return list;
	}

	/**
	 * 取此结点的所有父节点(按最近的顺序排列) A getTreeNodeAllParent function
	 * 
	 * @param id
	 *            结点ID
	 * @param parentName
	 *            结点父ID属性名
	 * @param treeClass
	 *            结点所属对象类
	 * @param list
	 *            保存结点父节点的集合(必需要初始化过)
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	@SuppressWarnings("unchecked")
	private void getTreeNodeAllParent(Long id, String parentName,
			Class treeClass, List list) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Object object;
		try {

			// 获得结点对象
			object = this.getSession().get(treeClass, id);
		} catch (Exception ex) {
			return;
		}

		// 获得父结点对象
		String parentid = BeanUtils.getProperty(object, parentName);
		if (parentid == null)
			return;
		// 记录父结点
		list.add(parentid);

		this.getTreeNodeAllParent(new Long(parentid), parentName, treeClass,
				list);
	}

	/**
	 * 例子:加载公共选择的位置列表方法(生成新的收缩树) A findCommTreePlace function
	 * 
	 * @param placetypeid
	 *            类型ID
	 * @param placetypename
	 *            类型名
	 * @param queryFlag
	 *            查询标志
	 * @param placeid
	 *            位置ID
	 * @param deptid
	 *            部门ID
	 * @param orgid
	 *            机构ID
	 * @param thisDeptid
	 *            当前部门ID
	 * @return 过滤后的集合
	 * @throws Exception
	 */
	// public List findCommTreePlace(Long placetypeid,String
	// placetypename,String queryFlag,Long placeid, Long deptid, Long orgid,
	// Long thisDeptid) throws Exception{
	// List treeList=this.findCommPlace(placetypeid,placetypename,
	// queryFlag,placeid, deptid, orgid, thisDeptid,null);
	// List list=new ArrayList();
	// String idName="oid";
	// String parentName="parentid";
	// String tempParentName="tempParentId";
	// Class treeClass=Place.class;
	// List list = this.buildTreeList(treeList,treeClass,idName, parentName, tempParentName);
	// return list;
	// }	
}
