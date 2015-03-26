package com.cyberway.common.visit.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cyberway.common.visit.domain.VcmsVisitCount;
import com.cyberway.common.visit.domain.VisitCount;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.utils.StringUtil;

/**
 * 视图实现访问记录查yqjservice
 * 
 * @author cj
 * 
 */
public class ViewVisitCountService extends HibernateEntityDao<VcmsVisitCount> {

	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// ---------------------------文档--------------------------------
	/**
	 * 文档访问量查询
	 * @author Dicky
	 * @time 2012-10-29 12:18:56
	 */
	@SuppressWarnings("unchecked")
	public List visitDocNumber(Long siteId, Long channelId, String startDate, String endDate) {
		String hql = "select channelId,siteName,title,channelName,count(*) as  visitNumber  from VcmsVisitCount where DOCID>0 and title<>'无文档' ";
			hql += " and siteId=" + siteId;
		if (channelId!=null) {
			hql += " and channelId=" + channelId;
		}
		// 查询条件日期
		// oracle
		if (!StringUtil.isEmpty(startDate)) {
			hql += " and visitDate>= '" + startDate + "'";
		}

		if (!StringUtil.isEmpty(endDate)) {
			hql += " and visitDate<= '" + endDate + "'";
		}
		hql += " group by channelId,siteName,channelName,title";
		List lists = find(hql);
		return lists;
	}

	/**
	 *分页查询文档访问次数(返回结果集)(按文档统计)
	 */
	@SuppressWarnings("unchecked")
	public List visitDocList(Long siteId, Long channelId, String startDate,
			String endDate, int pageNo, int pageSize) {
		List lists = new ArrayList();
		String sql = "select channelId,siteName,title,channelName,count(*) as  visitNumber  from VcmsVisitCount where DOCID>0 and title<>'无文档' ";
			sql += " and siteId=" + siteId;
		if (channelId!=null) {
			sql += " and channelId=" + channelId;
		}

		// 查询条件日期
		if (!StringUtil.isEmpty(startDate)) {
			sql += " and visitDate>= '" + startDate + "'";
		}

		if (!StringUtil.isEmpty(endDate)) {
			sql += " and visitDate<= '" + endDate + "'";
		}
		sql += " group by channelId,siteName,channelName,title";
		List listVisitCount = this.getSession().createQuery(sql)
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
		for (Object object : listVisitCount) {
			Object[] datas = (Object[]) object;
			VcmsVisitCount vcmsVisitCount = new VcmsVisitCount();
			vcmsVisitCount.setChannelId(datas[0] == null ? 0L : (Long
					.parseLong(datas[0].toString())));
			vcmsVisitCount.setSiteName(datas[1] == null ? "" : datas[1]
					.toString());
			vcmsVisitCount
					.setTitle(datas[2] == null ? "" : datas[2].toString());
			vcmsVisitCount.setChannelName(datas[3] == null ? "" : datas[3]
					.toString());
			vcmsVisitCount.setVisitNumber(datas[4] == null ? 0 : Integer
					.parseInt(datas[4].toString()));
			lists.add(vcmsVisitCount);
		}
		return lists;
	}

	/**
	 * 根据条件查询访问记录(按文档统计导出)
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Long> visitCountList(Long siteId, Long channelId,
			String startDate, String endDate) {
		DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
		dc.add(Restrictions.eq("siteId", siteId));

		if (channelId!=null) {
			dc.add(Restrictions.eq("channelId", channelId));
		}
		// 查询条件日期
		if (!StringUtil.isEmpty(startDate)) {
			try {
				dc.add(Restrictions.ge("visitDate", format.parse(startDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (!StringUtil.isEmpty(endDate)) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(format.parse(endDate));
				c.add(Calendar.DATE, 1);
				dc.add(Restrictions.lt("visitDate", c.getTime()));
			} catch (Exception e) {
			}
		}
		try {
			dc.setProjection(Projections.projectionList().add(Projections.groupProperty("channelId")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Long> list = getHibernateTemplate().findByCriteria(dc);
		return list;

	}

	// ------------------end 文档-------------------------

	// -----------------------站点--------------------------------

	/**
	 *分页查询文档访问次数(返回结果集)(按站点统计)
	 */
	@SuppressWarnings("unchecked")
	public List visitBySiteList(Long siteId, String startDate,
			String endDate, int pageNo, int pageSize) {
		List lists = new ArrayList();
		String sql = "select siteName,count(*) as  visitNumber from VcmsVisitCount where docId=0 and channelId=0 and siteId=" + siteId;
		// 查询条件日期
		// oracle
		if (!StringUtil.isEmpty(startDate)) {
			sql += " and visitDate>= '" + startDate + "'";
		}

		if (!StringUtil.isEmpty(endDate)) {
			sql += " and visitDate<= '" + endDate + "'";
		}
		sql += " group by siteName";
		List listVisitCount = this.getSession().createQuery(sql)
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
		for (Iterator iter = listVisitCount.iterator(); iter.hasNext();) {
			Object[] datas = (Object[]) iter.next();
			VcmsVisitCount vcmsVisitCount = new VcmsVisitCount();
			vcmsVisitCount.setSiteName(datas[0] == null ? "" : datas[0]
					.toString());
			vcmsVisitCount.setVisitNumber(datas[1] == null ? 0 : Integer
					.parseInt(datas[1].toString()));
			lists.add(vcmsVisitCount);
		}
		return lists;
	}

	/**
	 * 
	 * 站点访问量查询
	 * 
	 * @param siteId
	 * @param channelId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List visitBySiteNumber(Long siteId, String startDate,
			String endDate) {
		List lists = new ArrayList();
		String hql = "select siteName,count(*) as  visitNumber  from VcmsVisitCount where docId=0 and channelId=0 and siteId=" + siteId;
		// 查询条件日期
		// oracle
		if (!StringUtil.isEmpty(startDate)) {
			hql += " and visitDate>= '" + startDate + "'";
		}

		if (!StringUtil.isEmpty(endDate)) {
			hql += " and visitDate<= '" + endDate + "'";
		}
		hql += " group by siteName";
		lists = find(hql);
		return lists;
	}

	/**
	 * 根据条件查询访问记录(按站点统计导出)
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<Long> visitBySiteCountList(Long siteId, String startDate,
			String endDate) {
		DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
		dc.add(Restrictions.eq("siteId", siteId));
		// 查询条件日期
		if (!StringUtil.isEmpty(startDate)) {
			try {
				dc.add(Restrictions.ge("visitDate", format.parse(startDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (!StringUtil.isEmpty(endDate)) {
			Calendar c = Calendar.getInstance();
			try {
				c.setTime(format.parse(endDate));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			c.add(Calendar.DATE, 1);
			try {
				dc.add(Restrictions.lt("visitDate", format.parse(endDate)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		try {
			dc.setProjection(Projections.projectionList().add(
					Projections.groupProperty("channelId")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Long> list = getHibernateTemplate().findByCriteria(dc);
		return list;

	}

	// ---------------------------end 站点--------------------------

	// --------------------------频道----------------------------------

	/**
	 *分页查询文档访问次数(返回结果集)(按频道统计)
	 */
	@SuppressWarnings("unchecked")
	public List visitChannelList(Long siteId, Long channelId,
			String startDate, String endDate, int pageNo, int pageSize) {
		List lists = new ArrayList();
		String sql = "select channelId,siteName,channelName,count(*) as  visitNumber  from VcmsVisitCount where docId=0 and channelId>0 ";
			sql += " and siteId=" + siteId;
		if (channelId!=null) {
			sql += " and channelId=" + channelId;
		}
		// 查询条件日期
		// oracle
		if (!StringUtil.isEmpty(startDate)) {
			sql += " and visitDate >= '" + startDate + "'";
		}
		if (!StringUtil.isEmpty(endDate)) {
			sql += " and visitDate <= '" + endDate + "'";
		}
		sql += " group by channelId,siteName,channelName";
		List listVisitCount = this.getSession().createQuery(sql)
				.setFirstResult((pageNo - 1) * pageSize)
				.setMaxResults(pageSize).list();
		for (Iterator iter = listVisitCount.iterator(); iter.hasNext();) {
			Object[] datas = (Object[]) iter.next();
			VcmsVisitCount vcmsVisitCount = new VcmsVisitCount();
			vcmsVisitCount.setChannelId(datas[0] == null ? 0L : (Long
					.parseLong(datas[0].toString())));
			vcmsVisitCount.setSiteName(datas[1] == null ? "" : datas[1]
					.toString());
			vcmsVisitCount.setChannelName(datas[2] == null ? "" : datas[2]
					.toString());
			vcmsVisitCount.setVisitNumber(datas[3] == null ? 0 : Integer
					.parseInt(datas[3].toString()));
			lists.add(vcmsVisitCount);
		}
		return lists;
	}

	/**
	 * 
	 * 频道访问量查询
	 * @author Longer
	 * @param siteId
	 * @param channelId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List visitChannelNumber(Long siteId, Long channelId,
			String startDate, String endDate) {
		List lists = new ArrayList();
		String hql = "select channelId,siteName,channelName,count(*) as  visitNumber  from VcmsVisitCount where docId=0 and channelId>0 ";
			hql += " and siteId=" + siteId;
		if (channelId != null) {
			hql += " and channelId=" + channelId;
		}
		// 查询条件日期
		// oracle
		if (!StringUtil.isEmpty(startDate)) {
			hql += " and visitDate>= '" + startDate + "'";
		}
		if (!StringUtil.isEmpty(endDate)) {
			hql += " and visitDate<= '" + endDate + "'";
			;
		}
		hql += " group by channelId,siteName,channelName ";
		lists = find(hql);

		return lists;
	}

	// ---------------------end 频道-------------------------------

	/**
	 * 得到访问文档站点个数
	 */
	@SuppressWarnings("unchecked")
	public List<VisitCount> getSiteCount(Long siteId) {
		List<VisitCount> siteCount = new ArrayList();
		String sql = "select siteId,count(*) from VisitCount where siteId="
				+ siteId + " group by siteId";
		siteCount = find(sql);
		return siteCount;
	}

	/**
	 * 得到访问文档站点个数
	 */
	@SuppressWarnings("unchecked")
	public List getSiteCount0() {
		List siteCount = new ArrayList();
		String sql = "select siteId,count(*) from VisitCount group by siteId";
		siteCount = find(sql);
		return siteCount;
	}

	/**
	 * 文档的点击数
	 * 
	 * @param docId
	 *            文档id
	 * @return
	 */
	public int docVisitCount(String docId) {
		Criteria criteria = getEntityCriteria();
		criteria.add(Restrictions.eq("docId", docId));
		int totalCount = ((Integer) criteria.setProjection(
				Projections.rowCount()).uniqueResult());
		return totalCount;
	}
}
