/*
 * Created on Dec 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cyberway.issue.crawler.frontier;

import com.cyberway.issue.crawler.datamodel.CrawlURI;

/**
 * CostAssignmentPolicy considering all URIs costless -- essentially
 * disabling budgetting features.
 * 
 * @author gojomo
 */
public class ZeroCostAssignmentPolicy extends CostAssignmentPolicy {

    /* (non-Javadoc)
     * @see com.cyberway.issue.crawler.frontier.CostAssignmentPolicy#costOf(com.cyberway.issue.crawler.datamodel.CrawlURI)
     */
    public int costOf(CrawlURI curi) {
        return 0;
    }

}
