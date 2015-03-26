package com.cyberway.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.cyberway.cms.Constants;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class FtpUtil {
	public static void sendFileToCms(String filePath,String sendPath,String sendName){
		sendPath = Constants.CMS_FTP_BASE_PATH+sendPath;
		int port = Integer.valueOf(Constants.CMS_FTP_PORT);
		sendFileByFtp(Constants.CMS_FTP_HOST,port,Constants.CMS_FTP_USERNAME,Constants.CMS_FTP_PASSWORD,filePath,sendPath,sendName);
	}
	
	public static boolean sendFileByFtp(String host,int port,String username,String password,String filePath,String sendPath,String sendName){
		FTPClient ftp = new FTPClient();
		int replyCode;
		FileInputStream in=null;
		try {
			ftp.connect(host, port);
			ftp.login(username, password);
			replyCode = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("FTP登录失败");
				return false;
			}
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.makeDirectory(sendPath);
			ftp.changeWorkingDirectory(sendPath);
			in = new FileInputStream(filePath);
			boolean result = ftp.storeFile(sendName, in);
			return result;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(ftp.isConnected()){
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static boolean sendFileBySftp(String host,int port,String username,String password,String filePath,String sendPath){
		ChannelSftp channel = getSftpChannel(host,port,username,password);
		if(channel!=null){
			try {
				channel.put(filePath, sendPath, ChannelSftp.OVERWRITE);
				channel.quit();
				return true;
			} catch (SftpException e) {
				e.printStackTrace();
			} finally {
				disconnectSftpChannel(channel);
			}
		}
		return false;
	}
	
	private static ChannelSftp getSftpChannel(String host,int port,String username,String password){
		Session session = null;
		Channel channel = null;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(username, host, port);
			session.setPassword(password);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			return (ChannelSftp)channel;
		} catch (JSchException e) {
			e.printStackTrace();
			if(channel!=null && channel.isConnected()){
				channel.disconnect();
			}
			if(session!=null && session.isConnected()){
				session.disconnect();
			}
		}
		return null;
	}
	
	private static void disconnectSftpChannel(ChannelSftp channel){
		if(channel!=null){
			try {
				Session session = channel.getSession();
				if(channel.isConnected()){
					channel.disconnect();
				}
				if(session.isConnected()){
					session.disconnect();
				}
			} catch (JSchException e) {
				e.printStackTrace();
			}
		}
	}
}
