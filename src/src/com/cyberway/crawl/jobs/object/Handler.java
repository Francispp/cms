package com.cyberway.crawl.jobs.object;

import java.io.Serializable;

public class Handler implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7708805132868357321L;
	
	private String metaName;
	
	private String recovery;
	
	private String jobDescription;
	private String seeds;
	private boolean profile;
	private String jobUID;

	public String getJobUID() {
		return jobUID;
	}

	public void setJobUID(String jobUID) {
		this.jobUID = jobUID;
	}

	public boolean isProfile() {
		return profile;
	}

	public void setProfile(boolean profile) {
		this.profile = profile;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getRecovery() {
		return recovery;
	}

	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}

	public String getSeeds() {
		return seeds;
	}

	public void setSeeds(String seeds) {
		this.seeds = seeds;
	}

	public String getMetaName() {
		return metaName;
	}

	public void setMetaName(String metaName) {
		this.metaName = metaName;
	}

}
