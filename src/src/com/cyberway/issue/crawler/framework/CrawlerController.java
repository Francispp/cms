package com.cyberway.issue.crawler.framework;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanException;
import javax.management.ReflectionException;

import com.cyberway.issue.Constants;
import com.cyberway.issue.crawler.datamodel.CrawlOrder;
import com.cyberway.issue.crawler.datamodel.ServerCache;
import com.cyberway.issue.crawler.framework.exceptions.FatalConfigurationException;
import com.cyberway.issue.util.ArchiveUtils;
import com.cyberway.issue.util.Reporter;
import com.cyberway.issue.util.bdbje.EnhancedEnvironment;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentConfig;

public class CrawlerController implements Serializable, Reporter {
	private static final long serialVersionUID = 1L;

	private transient File disk; // 设定开始抓取后保存文件信息的目录结构

	private transient CrawlScope scope;

	private transient ProcessorChainList processorChains;

	private transient Frontier frontier;

	private transient ToePool toePool;

	private transient ServerCache serverCache;

	public final static String PROCESSORS_REPORT = "processors";

	public final static String MANIFEST_REPORT = "manifest";

	protected final static String[] REPORTS = { PROCESSORS_REPORT,
			MANIFEST_REPORT };

	private transient EnhancedEnvironment bdbEnvironment = null;

	private transient File stateDisk;

	public CrawlerController() {
		super();

	}

	public void initialize() {
		try {
			if (disk == null) {
				setupDisk();
				setupBdb();
				//setupCrawlModules();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*private void setupCrawlModules() throws FatalConfigurationException,
			AttributeNotFoundException, MBeanException, ReflectionException {
		if (scope == null) {
			scope = (CrawlScope) order.getAttribute(CrawlScope.ATTR_NAME);
			scope.initialize(this);
		}
		try {
			this.serverCache = new ServerCache(this);
		} catch (Exception e) {
			throw new FatalConfigurationException("Unable to"
					+ " initialize frontier (Failed setup of ServerCache) " + e);
		}

		if (this.frontier == null) {
			this.frontier = (Frontier) order.getAttribute(Frontier.ATTR_NAME);
			try {
				frontier.initialize(this);
				frontier.pause(); // Pause until begun
				// Run recovery if recoverPath points to a file (If it points
				// to a directory, its a checkpoint recovery).
				// TODO: make recover path relative to job root dir.
				if (!isCheckpointRecover()) {
					runFrontierRecover((String) order
							.getAttribute(CrawlOrder.ATTR_RECOVER_PATH));
				}
			} catch (IOException e) {
				throw new FatalConfigurationException(
						"unable to initialize frontier: " + e);
			}
		}

		// Setup processors
		if (processorChains == null) {
			processorChains = new ProcessorChainList(order);
		}
	}*/

	public void singleLineReportTo(PrintWriter writer) {
	}

	public void reportTo(PrintWriter writer) {

	}

	public void reportTo(String name, PrintWriter writer) {

	}

	public String[] getReports() {
		return REPORTS;
	}

	public String singleLineLegend() {
		return "nothing";
	}

	public String singleLineReport() {
		return ArchiveUtils.singleLineReport(this);
	}

	private void setupDisk() throws AttributeNotFoundException {
		String diskPath = Constants.DISK_PATH;
		this.disk = new File(diskPath);
		this.disk.mkdirs();
		this.stateDisk = getSettingsDir(CrawlOrder.ATTR_STATE_PATH);
	}

	private void setupBdb() throws FatalConfigurationException,
			AttributeNotFoundException {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		int bdbCachePercent = Constants.ATTR_BDB_CACHE_PERCENT;
		if (bdbCachePercent > 0) {
			envConfig.setCachePercent(bdbCachePercent);
		}
		envConfig.setLockTimeout(5000000); // 5 seconds
		try {
			this.bdbEnvironment = new EnhancedEnvironment(getStateDisk(),
					envConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
			throw new FatalConfigurationException(e.getMessage());
		}
	}

	public EnhancedEnvironment getBdbEnvironment() {
		return this.bdbEnvironment;
	}

	public File getStateDisk() {
		return stateDisk;
	}

	public File getSettingsDir(String key) throws AttributeNotFoundException {
		String path = Constants.STATE_DISK_PATH;
		File f = new File(path);
		if (!f.isAbsolute()) {
			f = new File(disk.getPath(), path);
		}
		if (!f.exists()) {
			f.mkdirs();
		}
		return f;
	}
    public CrawlScope getScope() {
        return scope;
    }

}
