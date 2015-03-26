package com.cyberway.common.visit.view;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.ecside.util.RequestUtils;

import com.cyberway.cms.channel.service.ChannelManagerService;
import com.cyberway.cms.document.service.DocumentCommonService;
import com.cyberway.cms.domain.Channel;
import com.cyberway.cms.domain.CmsSite;
import com.cyberway.cms.site.service.SiteManagerService;
import com.cyberway.common.visit.domain.VcmsVisitCount;
import com.cyberway.common.visit.domain.VisitCount;
import com.cyberway.common.visit.service.ViewVisitCountService;
import com.cyberway.core.dao.EntityDao;
import com.cyberway.core.dao.support.Page;
import com.cyberway.core.utils.StringUtil;
import com.cyberway.core.web.BaseBizController;

public class ViewVisitCountController extends BaseBizController<VcmsVisitCount> {

	/**
	 * @author Dicky
	 * @time 2012-10-29 11:50:32
	 * @version 1.0
	 */
	private static final long serialVersionUID = -5404109535833053583L;

	public String getVisittype() {
		return visittype;
	}

	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}

	ViewVisitCountService service = (ViewVisitCountService) this
			.getServiceById("viewVisitCountService");
	SiteManagerService siteService = (SiteManagerService) this
			.getServiceById("siteManagerService");
	ChannelManagerService channelService = (ChannelManagerService) this
			.getServiceById("channelManagerService");
	DocumentCommonService documentCommonService = (DocumentCommonService) this
			.getServiceById("documentCommonService");

	private Long channelId;

	private String channelName;

	private String startdate;

	private String enddate;

	private String type;

	private String visittype;// 统计类型

	@Override
	protected EntityDao<VcmsVisitCount> getService() {
		return service;
	}

	public String execute() throws Exception {
		return "execute";
	}

	/**
	 * 按文档查询统计(文档统计)
	 * 
	 * @author Dicky
	 * @time 2012-10-29 11:35:28
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String list() throws Exception {
		Long siteId = getLoginerSiteId();

		int pageNo = RequestUtils.getPageNo(getHttpServletRequest());
		List<VisitCount> allList = service
				.visitDocList(
						siteId,
						channelId,
						startdate,
						enddate,
						pageNo,
						RequestUtils
								.getCurrentRowsDisplayed(getHttpServletRequest()) == 0 ? Page.DEFAULT_PAGE_SIZE
								: RequestUtils
										.getCurrentRowsDisplayed(getHttpServletRequest()));
		int countNumber = service.visitDocNumber(siteId, channelId, startdate,
				enddate).size();
		int totalRows = RequestUtils.getTotalRowsFromRequest(
				getHttpServletRequest(), tableId);
		if (totalRows < 0) {
			totalRows = countNumber;
		}
		RequestUtils
				.getLimit(
						getHttpServletRequest(),
						tableId,
						totalRows,
						RequestUtils
								.getCurrentRowsDisplayed(getHttpServletRequest()) == 0 ? Page.DEFAULT_PAGE_SIZE
								: RequestUtils
										.getCurrentRowsDisplayed(getHttpServletRequest()));
		this.setItems(allList);
		RequestUtils.setTotalRows(getHttpServletRequest(), countNumber);
		return "frame";
	}

	/**
	 * 按频道查询统计
	 * 
	 * @author Dicky
	 * @time 2012-10-29 14:58:25
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listbychanel() throws Exception {
		Long siteId = getLoginerSiteId();

		int pageNo = RequestUtils.getPageNo(getHttpServletRequest());
		List<VisitCount> allList = service
				.visitChannelList(
						siteId,
						channelId,
						startdate,
						enddate,
						pageNo,
						RequestUtils
								.getCurrentRowsDisplayed(getHttpServletRequest()) == 0 ? Page.DEFAULT_PAGE_SIZE
								: RequestUtils
										.getCurrentRowsDisplayed(getHttpServletRequest()));
		int countNumber = service.visitChannelNumber(siteId, channelId,startdate, enddate).size();

		int totalRows = RequestUtils.getTotalRowsFromRequest(
				getHttpServletRequest(), tableId);
		if (totalRows < 0) {
			totalRows = countNumber;
		}
		RequestUtils
				.getLimit(
						getHttpServletRequest(),
						tableId,
						totalRows,
						RequestUtils
								.getCurrentRowsDisplayed(getHttpServletRequest()) == 0 ? Page.DEFAULT_PAGE_SIZE
								: RequestUtils
										.getCurrentRowsDisplayed(getHttpServletRequest()));
		this.setItems(allList);
		RequestUtils.setTotalRows(getHttpServletRequest(), countNumber);
		return "listbychanel";
	}

	/**
	 * 按站点查询统计
	 * 
	 * @author Dicky
	 * @time 2012-10-29 14:57:48
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String listbysite() throws Exception {
		Long siteId = getLoginerSiteId();
		int pageNo = RequestUtils.getPageNo(getHttpServletRequest());
		List<VisitCount> allList = service
				.visitBySiteList(
						siteId,
						startdate,
						enddate,
						pageNo,
						RequestUtils
								.getCurrentRowsDisplayed(getHttpServletRequest()) == 0 ? Page.DEFAULT_PAGE_SIZE
								: RequestUtils
										.getCurrentRowsDisplayed(getHttpServletRequest()));
		int countNumber = service.visitBySiteNumber(siteId, startdate, enddate)
				.size();
		int totalRows = RequestUtils.getTotalRowsFromRequest(
				getHttpServletRequest(), tableId);
		if (totalRows < 0) {
			totalRows = countNumber;
		}
		RequestUtils
				.getLimit(
						getHttpServletRequest(),
						tableId,
						totalRows,
						RequestUtils
								.getCurrentRowsDisplayed(getHttpServletRequest()) == 0 ? Page.DEFAULT_PAGE_SIZE
								: RequestUtils
										.getCurrentRowsDisplayed(getHttpServletRequest()));
		this.setItems(allList);
		RequestUtils.setTotalRows(getHttpServletRequest(), countNumber);
		return "listbysite";
	}

	/**
	 * @author Dicky
	 * @time 2012-10-29 14:56:19
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String selectList() throws Exception {
		String siteId = getHttpServletRequest().getParameter("siteId");
		List<CmsSite> siteList = siteService.getAll();
		getHttpServletRequest().setAttribute("siteId", siteId);
		getHttpServletRequest().setAttribute("siteList", siteList);
		return "list";
	}

	/**
	 * 首次进入页面
	 * 
	 * @author Dicky
	 * @time 2012-10-29 14:55:50
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String frame() throws Exception {
		String siteId = getHttpServletRequest().getParameter("siteId");
		getHttpServletRequest().setAttribute("siteId", siteId);
		getHttpServletRequest().setAttribute("visittype", "0");
		return "frame";
	}

	/**
	 * 文档的点击数
	 * 
	 * @throws Exception
	 */
	public void docVisitCount() throws Exception {
		String docId = StringUtils
				.trimToNull((String) getParameterValue("docId"));
		Validate.notNull(docId, "documentId is null!");

		HttpServletResponse response = (HttpServletResponse) getActionContext()
				.get(StrutsStatics.HTTP_RESPONSE);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(service.docVisitCount(docId) + "");
		writer.close();
	}

	/**
	 * 导出统计报表
	 * @author Dicky
	 * @time 2012-10-29 15:31:04
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String outupExc() throws Exception {
		// 站点id
		Long siteId = getLoginerSiteId();

		List<Long> channelIdlist = new ArrayList<Long>();

		if (type.equalsIgnoreCase("3")) {// 按站点统计
			channelIdlist = service.visitBySiteCountList(siteId, startdate, enddate);
		} else if (type.equalsIgnoreCase("2")) {// 按频道统计
			channelIdlist = service.visitCountList(siteId, channelId, startdate, enddate);
		} else {
			channelIdlist = service.visitCountList(siteId, channelId, startdate, enddate);
		}

		if (channelIdlist.size() == 0) {
			this.addActionMessage("没有要查找的数据");
			return "list";
		} else {
			HSSFWorkbook workbook = null;
			CmsSite site = siteService.getSiteFromCache(siteId);
			if (type.equalsIgnoreCase("3")) {
				if (siteId != null) {
					workbook = getSiteWorkbook(site, startdate, enddate);
				}
			} else if (type.equalsIgnoreCase("2")) {
				workbook = getChannelWorkbook(site, channelIdlist, startdate, enddate);
			} else {
				workbook = getWorkbook(site, channelIdlist, startdate, enddate);
			}

			if (workbook != null) {
				HttpServletResponse response = ServletActionContext.getResponse();
				// 取得输出流
				OutputStream out = response.getOutputStream();
				// 清空输出流
				response.reset();
				// 设置响应头和下载保存的文件名
				response.setHeader("content-disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode("文档访问统计_"+System.currentTimeMillis(), "utf-8")
								+ ".xls");
				// 定义输出类型
				response.setContentType("APPLICATION/msexcel");
				workbook.write(out);

				out.flush();
				out.close();
				// 这一行非常关键，否则在实际中有可能出现莫名其妙的问题！！！
				response.flushBuffer();// 强行将响应缓存中的内容发送到目的地
			} else {
				this.addActionError("导出失败");
			}
		}

		return NONE;
	}

	/**
	 *  按文档导出访问量
	 * @author Dicky
	 * @time 2012-10-29 15:30:11
	 * @version 1.0
	 * @param site
	 * @param channelIdlist
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HSSFWorkbook getWorkbook(CmsSite site, List<Long> channelIdlist,
			String startdate, String enddate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		HSSFCellStyle setBorder = workbook.createCellStyle();
		// 设置背景色：
		// setBorder.setFillForegroundColor(13);// 设置背景色
		// setBorder.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置居中:
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		// 显示搜索时间条件
		HSSFRow row = sheet.createRow(0); // 创建第1行，也就是输出表头
		HSSFCell cell;
		String headTime = "";
		if (!StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			headTime = startdate + "至" + enddate + "之间";
		}
		if (StringUtil.isEmpty(startdate) && StringUtil.isEmpty(enddate)) {
			headTime = "全部";
		}
		if (StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			headTime = enddate + "之前的数据";
		}
		if (StringUtil.isEmpty(enddate) && !StringUtil.isEmpty(startdate)) {
			headTime = startdate + "之后的数据";
		}

		// 第一行
		cell = row.createCell(0);
		cell.setCellStyle(setBorder);
		cell.setCellValue(headTime);

		List<Map<Long, List>> allList = new ArrayList<Map<Long, List>>();

		for (int i = 0; i < channelIdlist.size(); i++) {
			Long channelId_ = channelIdlist.get(i);
			if (channelId_ != null) {
				List docList = service.visitDocNumber(site.getOid(), channelId_, startdate, enddate);
				if (docList.size() > 0) {
					Map<Long, List> map = new HashMap<Long, List>();
					map.put(channelId_, docList);
					allList.add(map);
				}
			}
		}
		// 最高级频道
		int max = 0;
		// 第二行 列头
		for (Map<Long, List> map : allList) {
			for (Long key : map.keySet()) {
				int level = getlevel(1, key);
				if (level > max) {
					max = level;
				}
			}
		}

		// 第二行 列头
		row = sheet.createRow(1);
		cell = row.createCell(0); // 创建第i列
		
		sheet.setColumnWidth(0, 9766); // 第一个参数代表列id(从j开始),
														// 第2个参数代表宽度值
		cell.setCellStyle(setBorder);
		cell.setCellValue("站点名称");
		for (int j = 1; j <= max; j++) {
			cell = row.createCell(j); // 创建第i列
			sheet.setColumnWidth(j, 5766); // 第一个参数代表列id(从j开始),
															// 第2个参数代表宽度值
			
			cell.setCellStyle(setBorder);
			cell.setCellValue(j + "级频道");
		}
		cell = row.createCell((max + 1));
		sheet.setColumnWidth((max + 1), 15766); //第一个参数代表列id(从0开始
																// ),第2个参数代表宽度值
		
		cell.setCellStyle(setBorder);
		cell.setCellValue("文档名称");

		cell = row.createCell((max + 2));
		sheet.setColumnWidth((max + 2), 7766); //第一个参数代表列id(从0开始
																// ),第2个参数代表宽度值
		
		cell.setCellStyle(setBorder);
		cell.setCellValue("访问量(人次)");

		// 将得到的数据写入excel
		int temp = 0;
		// 站点合并单元格变量
		int sitesTemp = 2;
		// 合并数 及每个站点下面文档个数
		int mergeTemp = 0;
		// 文档访问总数
		int sumCount = 0;

		// 核心list
		List docList = new ArrayList();
		// 站点
		Long siteId = site.getOid();
		// 频道
		// 频道合并单元格变量
//		int chansTemp = 2;
		for (int j = 0; j < channelIdlist.size(); j++) {
			Long cId = channelIdlist.get(j);
			if (cId != null && siteId != null) {
//				int clevel = getlevel(1, cId);
				docList = service.visitDocNumber(site.getOid(), cId, startdate,enddate);
				// 合并频道
//				for (int cl = 1; cl <= clevel; cl++) {
//					sheet.addMergedRegion(new Region(chansTemp,
//							(cl), chansTemp + docList.size() - 1,
//							(cl)));
//				}
//				chansTemp = chansTemp + docList.size();

				for (int k = 0; k < docList.size(); k++) {
					Object[] res = (Object[]) docList.get(k);
					int level = getlevel(1, Long.valueOf(res[0].toString()));
					Long.valueOf(res[0].toString());

					row = sheet.createRow(temp + 2);
					// 第一列 站点名称
					cell = row.createCell(0);
					
					cell.setCellStyle(setBorder);
					cell.setCellValue(res[1].toString());

					// 频道
					Long chaId = Long.valueOf(res[0].toString());
					Channel chan = channelService.get(chaId);

					if (chan.getParent() != null) {

						List<String> nameList = new ArrayList();
						exportChannelName(chan.getParent().getId(),
								nameList);
						for (int m = 0; m < nameList.size(); m++) {
							cell = row.createCell((level - m - 1));// 创建第j列
							
							cell.setCellStyle(setBorder);
							cell.setCellValue(nameList.get(m));

						}
						cell = row.createCell(level);// 创建第j列
						
						cell.setCellStyle(setBorder);
						cell.setCellValue(chan.getName());
					} else {
						cell = row.createCell(level);// 创建第j列
						
						cell.setCellStyle(setBorder);
						cell.setCellValue(chan.getName());

					}

					// 文档名称
					cell = row.createCell((max + 1));
					
					cell.setCellStyle(setBorder);
					if (res[2] == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(res[2].toString());
					}

					// 访问量
					cell = row.createCell((max + 2));
					
					cell.setCellStyle(setBorder);
					cell.setCellValue(Integer.valueOf(res[4].toString()));
					sumCount = sumCount
							+ Integer.valueOf(res[4].toString());
					temp++;
				}
				mergeTemp = mergeTemp + docList.size();
			}
		}
		sheet.addMergedRegion(new CellRangeAddress(sitesTemp, sitesTemp
				+ mergeTemp - 1, 0,0));

		// 最后一行
		sheet.addMergedRegion(new CellRangeAddress((mergeTemp + 2),
				(mergeTemp + 2), 1, (max + 1)));
		row = sheet.createRow(mergeTemp + 2);
		cell = row.createCell(0);
		
		cell.setCellStyle(setBorder);
		cell.setCellValue("总数(时间段)");

		cell = row.createCell((max + 2));
		
		cell.setCellStyle(setBorder);
		cell.setCellValue(sumCount);
		return workbook;

	}

	/**
	 * 按频道导出文档访问量
	 * 
	 * @author Dicky
	 * @time 2012-10-29 14:41:07
	 * @version 1.0
	 * @param siteid
	 * @param channelIdlist
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HSSFWorkbook getChannelWorkbook(CmsSite site,
			List<Long> channelIdlist, String startdate, String enddate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		HSSFCellStyle setBorder = workbook.createCellStyle();
		// 设置背景色：
		// setBorder.setFillForegroundColor(13);// 设置背景色
		// setBorder.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置居中:
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		// 显示搜索时间条件
		HSSFRow row = sheet.createRow(0); // 创建第1行，也就是输出表头
		HSSFCell cell;
		String headTime = "";
		if (!StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			headTime = startdate + "至" + enddate + "之间";
		}
		if (StringUtil.isEmpty(startdate) && StringUtil.isEmpty(enddate)) {
			headTime = "全部";
		}
		if (StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			headTime = enddate + "之前的数据";
		}
		if (StringUtil.isEmpty(enddate) && !StringUtil.isEmpty(startdate)) {
			headTime = startdate + "之后的数据";
		}

		// 第一行
		cell = row.createCell(0);
		
		cell.setCellStyle(setBorder);
		cell.setCellValue(headTime);

		List<Map<Long, List>> allList = new ArrayList<Map<Long, List>>();

		for (int i = 0; i < channelIdlist.size(); i++) {
			Long channelId_ = channelIdlist.get(i);
			if (channelId_ != null) {
				List docList = service.visitChannelNumber(site.getOid(), channelId_, startdate, enddate);
				if (docList.size() > 0) {
					Map<Long, List> map = new HashMap<Long, List>();
					map.put(channelId_, docList);
					allList.add(map);
				}
			}
		}
		// 最高级频道
		int max = 0;
		// 第二行 列头
		for (Map<Long, List> map : allList) {
			for (Long key : map.keySet()) {
				int level = getlevel(1, key);
				if (level > max) {
					max = level;
				}
			}
		}

		// 第二行 列头
		row = sheet.createRow(1);
		cell = row.createCell(0); // 创建第i列
		
		sheet.setColumnWidth(0, 9766); // 第一个参数代表列id(从j开始),
														// 第2个参数代表宽度值
		cell.setCellStyle(setBorder);
		cell.setCellValue("站点名称");
		for (int j = 1; j <= max; j++) {
			cell = row.createCell(j); // 创建第i列
			sheet.setColumnWidth(j, 5766); // 第一个参数代表列id(从j开始),
															// 第2个参数代表宽度值
			
			cell.setCellStyle(setBorder);
			cell.setCellValue(j + "级频道");
		}

		cell = row.createCell((max + 1));
		sheet.setColumnWidth((max + 1), 7766); //第一个参数代表列id(从0开始
																// ),第2个参数代表宽度值
		
		cell.setCellStyle(setBorder);
		cell.setCellValue("访问量(人次)");

		// 将得到的数据写入excel
		int temp = 0;
		// 站点合并单元格变量
		int sitesTemp = 2;
		// 合并数 及每个站点下面文档个数
		int mergeTemp = 0;
		// 文档访问总数
		int sumCount = 0;

		// 核心list
		List docList = new ArrayList();
		// 站点
		Long siteId = site.getOid();
		// 频道
		// 频道合并单元格变量
//		int chansTemp = 2;
		for (int j = 0; j < channelIdlist.size(); j++) {
			Long cId = channelIdlist.get(j);
//				int clevel = getlevel(1, cId);
			docList = service.visitChannelNumber(siteId, cId, startdate, enddate);
			// 合并频道
//				for (int cl = 1; cl <= clevel; cl++) {
//					sheet.addMergedRegion(new Region(chansTemp,
//							(cl), chansTemp + docList.size() - 1,
//							(cl)));
//				}
//				chansTemp = chansTemp + docList.size();

			for (int k = 0; k < docList.size(); k++) {
				Object[] res = (Object[]) docList.get(k);
				int level = getlevel(1, Long.valueOf(res[0].toString()));
				Long.valueOf(res[0].toString());

				row = sheet.createRow(temp + 2);
				// 第一列 站点名称
				cell = row.createCell(0);
				
				cell.setCellStyle(setBorder);
				cell.setCellValue(res[1].toString());

				// 频道
				Long chaId = Long.valueOf(res[0].toString());
				Channel chan = channelService.getChannelFromCache(chaId);

				if (chan.getParent() != null) {

					List<String> nameList = new ArrayList();
					exportChannelName(chan.getParent().getId(), nameList);
					for (int m = 0; m < nameList.size(); m++) {
						cell = row.createCell((level - m - 1));// 创建第j列
						
						cell.setCellStyle(setBorder);
						cell.setCellValue(nameList.get(m));

					}
					cell = row.createCell(level);// 创建第j列
					
					cell.setCellStyle(setBorder);
					cell.setCellValue(chan.getName());
				} else {
					cell = row.createCell(level);// 创建第j列
					
					cell.setCellStyle(setBorder);
					cell.setCellValue(chan.getName());

				}
				// 访问量
				cell = row.createCell((max + 1));
				
				cell.setCellStyle(setBorder);
				cell.setCellValue(Integer.valueOf(res[3].toString()));
				sumCount = sumCount + Integer.valueOf(res[3].toString());
				temp++;
			}
			mergeTemp = mergeTemp + docList.size();
		}
		sheet.addMergedRegion(new CellRangeAddress(sitesTemp, sitesTemp
				+ mergeTemp - 1, (0), (0)));

		// 最后一行
		// sheet.addMergedRegion(new Region((mergeTemp + 2), 2,
		// (mergeTemp + 2), (max+1)));
		row = sheet.createRow(mergeTemp + 2);
		cell = row.createCell(0);
		
		cell.setCellStyle(setBorder);
		cell.setCellValue("总数(时间段)");

		cell = row.createCell((max + 1));
		
		cell.setCellStyle(setBorder);
		cell.setCellValue(sumCount);
		return workbook;

	}

	// 按站点进行导出
	@SuppressWarnings("unchecked")
	private HSSFWorkbook getSiteWorkbook(CmsSite site, String startdate,
			String enddate) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		HSSFCellStyle setBorder = workbook.createCellStyle();
		// 设置背景色：
		// setBorder.setFillForegroundColor(13);// 设置背景色
		// setBorder.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置居中:
		setBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		// 显示搜索时间条件
		HSSFRow row = sheet.createRow(0); // 创建第1行，也就是输出表头
		HSSFCell cell;
		String headTime = "";
		if (!StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			headTime = startdate + "至" + enddate + "之间";
		}
		if (StringUtil.isEmpty(startdate) && StringUtil.isEmpty(enddate)) {
			headTime = "全部";
		}
		if (StringUtil.isEmpty(startdate) && !StringUtil.isEmpty(enddate)) {
			headTime = enddate + "之前的数据";
		}
		if (StringUtil.isEmpty(enddate) && !StringUtil.isEmpty(startdate)) {
			headTime = startdate + "之后的数据";
		}

		// 第一行
		cell = row.createCell(0);
		
		cell.setCellStyle(setBorder);
		cell.setCellValue(headTime);

		@SuppressWarnings("unused")
		List<Map<Long, List>> allList = new ArrayList<Map<Long, List>>();

		List docList = service.visitBySiteNumber(site.getOid(), startdate, enddate);
		// 最高级频道
		int max = 1;
		// 第二行 列头
		row = sheet.createRow(1);
		cell = row.createCell(0); // 创建第i列
		
		sheet.setColumnWidth(0, 9766); // 第一个参数代表列id(从j开始),
														// 第2个参数代表宽度值
		cell.setCellStyle(setBorder);
		cell.setCellValue("站点名称");

		cell = row.createCell(max + 2);
		sheet.setColumnWidth((max + 2), 7766); //第一个参数代表列id(从0开始
																// ),第2个参数代表宽度值
		
		cell.setCellStyle(setBorder);
		cell.setCellValue("访问量(人次)");

		// 将得到的数据写入excel
		int temp = 0;
		// 站点合并单元格变量
		@SuppressWarnings("unused")
		int sitesTemp = 2;
		// 合并数 及每个站点下面文档个数
		int mergeTemp = 0;
		List siteCount = service.visitBySiteNumber(site.getOid(), startdate, enddate);
		// 文档访问总数
		int sumCount = 0;
		// 站点
		for (int i = 0; i < siteCount.size(); i++) {
			Object[] object = (Object[]) siteCount.get(i);
			row = sheet.createRow(temp + 2);
			// 第一列 站点名称
			cell = row.createCell(0);
			
			cell.setCellStyle(setBorder);
			cell.setCellValue(object[0].toString());
			// 第二列访问量
			cell = row.createCell((max + 2));
			
			cell.setCellStyle(setBorder);

			cell.setCellValue(Integer.valueOf(object[1].toString()));
			sumCount = sumCount + Integer.valueOf(object[1].toString());
			temp++;

		}
		mergeTemp = mergeTemp + docList.size();

		// 最后一行
//		sheet.addMergedRegion(new Region((mergeTemp + 2), 1,
//				(mergeTemp + 2), (max + 1)));
		row = sheet.createRow(mergeTemp + 2);
		cell = row.createCell(0);
		
		cell.setCellStyle(setBorder);
		cell.setCellValue("总数(时间段)");

		cell = row.createCell(max + 2);
		
		cell.setCellStyle(setBorder);
		cell.setCellValue(sumCount);
		return workbook;
	}

	public List<String> exportChannelName(Long id, List<String> nameList) {
		Channel channel = channelService.get(id);
		nameList.add(channel.getName());
		if (channel.getParent() != null) {
			exportChannelName(channel.getParent().getId(), nameList);
			return nameList;
		} else {
			return nameList;
		}
	}

	// 选择频道
	List<CmsSite> sites;

	public List<CmsSite> getSites() {
		return sites;
	}

	public void setSites(List<CmsSite> sites) {
		this.sites = sites;
	}

	/**
	 * 递归得到 当前得到的频道 是多少级
	 * @author Dicky
	 * @time 2012-10-29 15:12:27
	 * @version 1.0
	 * @param level
	 * @param cid
	 * @return
	 */
	public int getlevel(int level, Long cid) {
		int i = level;
		Channel channel = channelService.getChannelFromCache(cid);
		if (channel != null && channel.getParent() != null) {
			i = getlevel(level + 1, channel.getParent().getId());
		} else {
			return i;
		}
		return i;
	}

	/**
	 * 频道(栏目)选择树
	 * @author Dicky
	 * @time 2012-10-29 15:10:46
	 * @version 1.0
	 * @return
	 * @throws Exception
	 */
	public String selectChannelTree() throws Exception {
		List<CmsSite> allsite = new ArrayList<CmsSite>();
		allsite.add(siteService.getSiteFromCache(getLoginerSiteId()));
		setSites(allsite);
		return "selectChannel";
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		if (channelName == null && channelId != null) {
			Channel channel = channelService.getChannelFromCache(channelId);
			channelName = channel.getName();
		}
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
