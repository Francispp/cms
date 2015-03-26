package com.cyberway.issue.crawler.replace;

import com.cyberway.issue.io.RecordingInputStream;
import com.cyberway.issue.net.UURI;

public abstract class Replace {
	public Replace()
	{
		
	}
	public abstract String Replace(RecordingInputStream recis,UURI uuri) throws Exception;

}
