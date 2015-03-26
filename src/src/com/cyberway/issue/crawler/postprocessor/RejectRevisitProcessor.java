package com.cyberway.issue.crawler.postprocessor;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.cyberway.issue.crawler.datamodel.CrawlURI;
import com.cyberway.issue.crawler.framework.Processor;
import com.cyberway.issue.crawler.frontier.AdaptiveRevisitAttributeConstants;

/**
 * Set a URI to not be revisited by the ARFrontier. This only makes sense when
 * using the ARFrontier and a decide-rule chain granting only selected access to
 * this processor. This is the opposite of the AcceptRevisitProcessor class.
 * 
 * @author mzsanford
 */
public class RejectRevisitProcessor extends Processor implements
        AdaptiveRevisitAttributeConstants {
    private static final long serialVersionUID = 4310432303089418844L;

    private static final Logger logger = Logger
            .getLogger(RejectRevisitProcessor.class.getName());

    public RejectRevisitProcessor(String name) {
        super(name, "Set a URI to not be revisited by the ARFrontier.");
    }

    @Override
    protected void initialTasks() {
        CrawlURI.addAlistPersistentMember(A_DISCARD_REVISIT);
    }

    @Override
    protected void innerProcess(CrawlURI curi) throws InterruptedException {
        if (curi != null) {
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("Adding DISCARD_REVISIT=true to Crawl URI: "
                        + curi.getUURI().toString());
            }
            curi.putObject(A_DISCARD_REVISIT, Boolean.TRUE);
        }
    }

}
