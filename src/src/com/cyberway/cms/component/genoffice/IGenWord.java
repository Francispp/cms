package com.cyberway.cms.component.genoffice;

public interface IGenWord {
	/**
	 * 根据word模板生成word
	 * @param org POJO javaBean
	 * @param templateName word 模板名
	 * @return
	 */
	public String generateWord(Object org, String templateName);
}
