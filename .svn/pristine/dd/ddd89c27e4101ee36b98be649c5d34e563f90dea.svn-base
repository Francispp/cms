package com.cyberway.cms.component.function.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.cyberway.cms.component.function.domain.JSFunction;
import com.cyberway.core.dao.HibernateEntityDao;
import com.cyberway.core.exception.BizException;

public class JSFunctionService extends HibernateEntityDao<JSFunction> {

	public JSFunction getById(Long id) {
		return get(id);
	}

	/**
	 * 分类下拉列表
	 * 
	 * @return
	 */
	public String[] typeList() {
		return (String[]) this.createQuery("select distinct type from JSFunction").list().toArray( new String[] {});
	}

	public byte[] exportToExcel(Collection<JSFunction> data,
			Map<String, String> map) throws Exception {
		String[] fields = map.keySet().toArray(new String[] {});
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		int i = 0;
		for (JSFunction vo : data) {
			HSSFRow row = sheet.createRow((short) i);
			for (int j = 0; j < map.size(); j++) {
				if (i == 0) {// 表头
					HSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(map.get(fields[j]));
					if (j == map.size() - 1) {
						i++;
						j = 0;
						row = sheet.createRow((short) i);
					}
				}
				if (i != 0) {
					Method m = vo.getClass().getMethod(
							"get" + fields[j].substring(0, 1).toUpperCase()
									+ fields[j].substring(1));
					HSSFCell cell = row.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue((String) (m.invoke(vo)));
				}
			}
			i++;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		return out.toByteArray();
	}

	public Map<String, List<JSFunction>> importDataFormExcel(File inFile, Map<String , String> map) throws Exception {
		Map<String, List<JSFunction>> mess = new HashMap<String, List<JSFunction>>();
		String fields [] =null;
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(inFile));
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow title = sheet.getRow(0);
		fields = new String[title.getLastCellNum()+1];
		for(int i=0;i<=title.getLastCellNum();i++){
			Set<Entry<String, String>>  set = map.entrySet();
			HSSFCell titleCell = title.getCell((short)i);
			for(Iterator<Entry<String, String>> iterator = set.iterator();iterator.hasNext();){
				Entry<String, String> en = iterator.next();
				if(en.getValue().equals(titleCell.getStringCellValue().trim())){
					fields[i] = en.getKey();
					break;
				}
			}
		}
		
		ArrayList<JSFunction> list = new ArrayList<JSFunction>();
		ArrayList<JSFunction> list1 = new ArrayList<JSFunction>();
		for(int i=1;i<=sheet.getLastRowNum();i++){
			HSSFRow row = sheet.getRow(i);
			JSFunction vo = new JSFunction();
			for(int j=0;j<=row.getLastCellNum();j++){
				Method m = vo.getClass().getMethod(
						"set" + fields[j].substring(0, 1).toUpperCase()
								+ fields[j].substring(1),String.class);
				HSSFCell cell = row.getCell((short)j);
				if(cell!=null) m.invoke(vo,cell.getStringCellValue());
			}
				if(!isNotUnique(vo, "type,functionName"))
					list.add(vo);
				else
					list1.add(vo);
		}
		if(list1.size()==0){
			for(JSFunction temp:list){
				try {
					saveOrUpdate(temp);
				} catch (BizException e) {}
			}
		}
		//mess.put("success", list);
		mess.put("faild", list1);
		return mess;
	}

	public List<JSFunction> findByIds(List<Long> ids) {
		StringBuilder queryString = new StringBuilder("from JSFunction where oid in (");
		for (Long id : ids)
			queryString.append(id).append(", ");
		queryString.replace(queryString.length() - 2, queryString.length() - 1, "").append(")");
		return this.createQuery(queryString.toString()).list();
		// super.getHibernateTemplate().find(queryString.toString());
	}

	@Override
	public JSFunction saveOrUpdate(JSFunction vo)  {
		//检测是否在同一类型功能名称是否重名
		Boolean unique = this.isNotUnique(vo, "type,functionName");
		if(!unique) return super.saveOrUpdate(vo);
		else {
			throw new BizException("同一类型下JS功能名称不能重复！");
		}
	}
	
	

}
