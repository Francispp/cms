package com.cyberway.staticer.ftp;

import java.util.List;

/**
 * FTP客户端
 * 
 * @author helfen
 * 
 */
public interface FTPClient {
	void upload(String fileName, byte[] content);

	void upload(String fileName, byte[] content, boolean overwrite);

	void delete(String fileName);

	List<String> getList(String filePath);

	void deleteByPath(String filePath);

}
