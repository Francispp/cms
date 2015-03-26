package com.cyberway.common.sitemap.domain;

import java.util.List;

public class SitemapItem {
	private String name;
	private String link;
	private String target;
	private List<SitemapItem> subItems;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public List<SitemapItem> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<SitemapItem> subItems) {
		this.subItems = subItems;
	}

}
