package com.cyberway.staticer.ftp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.cyberway.core.utils.ServiceLocator;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * com.cyberway.staticer.ftp.SiteFTPClientImpl
 * 
 * @author Janice Yang
 * 
 * @createTime 2011-12-27 下午3:16:15
 * 
 * @Description:
 * 
 */
public class SiteFTPClientImpl implements SiteFTPClient {
	private Long	           _ftpId;
	private String	           _host;
	private int	               _port	                 = 21;
	private String	           _username;
	private String	           _password;
	private boolean	           _ssh;
	private JSch	           _jsch	                 = new JSch();

	public static final String	EVENT_LISTENER_BEAN_NAME	= "siteEventListener";
	private static Logger _log = Logger.getLogger(SiteFTPClientImpl.class);

	public SiteFTPClientImpl() {
		super();
	}

	public SiteFTPClientImpl(Long ftpId, String host, int port, String username, String password, boolean ssh) {
		super();
		this._ftpId = ftpId;
		this._host = host;
		this._port = port;
		this._username = username;
		this._password = password;
		this._ssh = ssh;
	}

	protected static EventListener eventListener() {
		return (EventListener) ServiceLocator.getBean(EVENT_LISTENER_BEAN_NAME);
	}

	public void setHost(String host) {
		_host = host;
	}

	public void setPort(int port) {
		_port = port;
	}

	public boolean isSsh() {
		return _ssh;
	}

	public void setSsh(boolean ssh) {
		_ssh = ssh;
	}

	public void setUsername(String username) {
		_username = username;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void upload(Long siteId, String siteResourceType, String fileName, File file) {
		upload(siteId, siteResourceType, fileName, file, true);
	}

	public void upload(final Long siteId, final String siteResourceType, String ftpFilePath, final File file, final boolean overwrite) {
		final String fileName1 = StringUtils.replace(ftpFilePath, "\\", "/");

		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						mkdir(channel, FilenameUtils.getFullPath(fileName1));

						OutputStream outstr = channel.put(fileName1);
						IOUtils.write(FileUtils.readFileToByteArray(file), outstr);
						IOUtils.closeQuietly(outstr);

						eventListener().onUploadSuccessed(null, _ftpId, siteId, siteResourceType, fileName1, file);
					} catch (Exception ex) {
						eventListener().onUploadFailed(null, _ftpId, siteId, siteResourceType, fileName1, file);
						_log.error("-分发失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		} else {
			execute(new Callback() {
				public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					try {
						ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
						ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

						mkdir(ftpClient, FilenameUtils.getFullPath(fileName1));

						if (overwrite || ftpClient.retrieveFileStream(fileName1) == null) {
							OutputStream outstr = ftpClient
							        .storeFileStream(new String(fileName1.getBytes(), ftpClient.getControlEncoding()));
							outstr.write(FileUtils.readFileToByteArray(file));

							outstr.flush();
							outstr.close();

							if (ftpClient.completePendingCommand()) {
								eventListener().onUploadSuccessed(null, _ftpId, siteId, siteResourceType, fileName1, file);
							} else {
								eventListener().onUploadFailed(null, _ftpId, siteId, siteResourceType, fileName1, file);
							}
						}
					} catch (Exception ex) {
						eventListener().onUploadFailed(null, _ftpId, siteId, siteResourceType, fileName1, file);
						_log.error("-分发失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		}
	}

	public void delete(final Long siteId, final String siteResourceType, String ftpFilePath, final String localFilePath) {
		final String fileName1 = StringUtils.replace(ftpFilePath, "\\", "/");

		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						channel.rm(fileName1);
						
						eventListener().onDeleteSuccessed(null, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
					} catch (Exception ex) {
						eventListener().onDeleteFailed(null, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
						_log.error("-删除失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		} else {
			execute(new Callback() {
				public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					try {
						ftpClient.dele(fileName1);
						
						eventListener().onDeleteSuccessed(null, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
					} catch (Exception ex) {
						eventListener().onDeleteFailed(null, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
						_log.error("-删除失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		}
	}
	
	public void deleteByPath(final Long siteId, final String siteResourceType, String filePath, final String localFilePath) {
		final String fileName1 = StringUtils.replace(filePath, "\\", "/");
		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						// sftp根据指定文件夹删除下面的文件
						String ftpPath = channel.realpath(fileName1);
						Vector<LsEntry> l = channel.ls(ftpPath);
						for (LsEntry lsEntry : l) {
							if (lsEntry.getAttrs().isDir()) {
								if (!lsEntry.getFilename().equals("..")) {
									if (!lsEntry.getFilename().equals(".")) {
										deleteByPath(siteId, siteResourceType, fileName1 + File.separator + lsEntry.getFilename(), localFilePath);
									}
								}
							} else {
								delete(siteId, siteResourceType, fileName1 + File.separator + lsEntry.getFilename(), localFilePath);
							}
						}
					} catch (Exception ex) {
						//_log.error("-删除目录失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		} else {
			execute(new Callback() {
				public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					try {
						FTPFile[] ftpFiles = ftpClient.listFiles(fileName1);
						if (ftpFiles != null) {
							for (int i = 0; i < ftpFiles.length; i++) {
								if (ftpFiles[i] == null) {
									return;
								}
								if (ftpFiles[i].isDirectory()) {
									deleteByPath(siteId, siteResourceType, fileName1 + File.separator + ftpFiles[i].getName(), localFilePath);
								} else {
									delete(siteId, siteResourceType, fileName1 + File.separator + ftpFiles[i].getName(), localFilePath);
								}
							}
						}
					} catch (Exception ex) {
						_log.error("-删除目录失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		}
	}

	public void upload(Long ftpLogId, final Long siteId, final String siteResourceType, String fileName, File file) {
		upload(ftpLogId, siteId, siteResourceType, fileName, file, true);
	}

	public void upload(final Long ftpLogId, final Long siteId, final String siteResourceType, String ftpFilePath, final File file,
	        final boolean overwrite) {
		final String fileName1 = StringUtils.replace(ftpFilePath, "\\", "/");

		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						mkdir(channel, FilenameUtils.getFullPath(fileName1));

						OutputStream outstr = channel.put(fileName1);
						IOUtils.write(FileUtils.readFileToByteArray(file), outstr);
						IOUtils.closeQuietly(outstr);

						eventListener().onUploadSuccessed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, file);
					} catch (Exception ex) {
						eventListener().onUploadFailed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, file);
						_log.error("-上传失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		} else {
			execute(new Callback() {
				public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					try {
						ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
						ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

						mkdir(ftpClient, FilenameUtils.getFullPath(fileName1));

						if (overwrite || ftpClient.retrieveFileStream(fileName1) == null) {
							OutputStream outstr = ftpClient
							        .storeFileStream(new String(fileName1.getBytes(), ftpClient.getControlEncoding()));
							outstr.write(FileUtils.readFileToByteArray(file));

							outstr.flush();
							outstr.close();

							if (ftpClient.completePendingCommand()) {
								eventListener().onUploadSuccessed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, file);
							} else {
								eventListener().onUploadFailed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, file);
							}
						}
					} catch (Exception ex) {
						eventListener().onUploadFailed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, file);
						_log.error("-上传失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		}
	}

	public void delete(final Long ftpLogId, final Long siteId, final String siteResourceType, String ftpFilePath, final String localFilePath) {
		final String fileName1 = StringUtils.replace(ftpFilePath, "\\", "/");

		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						channel.rm(fileName1);

						eventListener().onDeleteSuccessed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
					} catch (Exception ex) {
						eventListener().onDeleteFailed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
						_log.error("-删除失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		} else {
			execute(new Callback() {
				public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					try {
						ftpClient.dele(fileName1);

						eventListener().onDeleteSuccessed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
					} catch (Exception ex) {
						eventListener().onDeleteFailed(ftpLogId, _ftpId, siteId, siteResourceType, fileName1, localFilePath);
						_log.error("-删除失败->"+fileName1, ex);
						throw new RuntimeException(ex);
					}
				}
			});
		}
	}
	
	public boolean checkFtpConnect() {
		if (isSsh()) {
			try {
				execute(new SSHCallback() {
					public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					}
				});
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			try {
				execute(new Callback() {
					public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					}
				});
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	protected void execute(Callback callback) {
		org.apache.commons.net.ftp.FTPClient ftpClient = null;
		try {
			ftpClient = new org.apache.commons.net.ftp.FTPClient();

			ftpClient.connect(_host, _port);
			boolean flag = ftpClient.login(_username, _password);
			
			if(!flag){
				throw new RuntimeException("连接不上服务器!");
			}
			
			callback.doInFtpClient(ftpClient);

			ftpClient.disconnect();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (ftpClient != null) {
				try {
					ftpClient.disconnect();
				} catch (Exception ex) {
				}
				;
			}
		}
	}

	protected void execute(SSHCallback callback) {
		Session session = null;
		Channel channel = null;

		try {
			session = _jsch.getSession(_username, _host, _port);
			session.setPassword(_password);

			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			session.setConfig(sshConfig);

			session.connect();

			channel = session.openChannel("sftp");
			channel.connect();

			callback.doInChannel(session, (ChannelSftp) channel);
		} catch (JSchException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (SftpException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (channel != null) {
				channel.disconnect();
			}
			if (session != null) {
				session.disconnect();
			}
		}
	}

	protected void mkdir(org.apache.commons.net.ftp.FTPClient ftpClient, String dir) throws UnsupportedEncodingException, IOException {
		dir = StringUtils.replace(dir, "\\", "/");
		String[] dirPartList = StringUtils.split(dir, "/");
		dir = "/";

		for (String filePart : dirPartList) {
			dir = StringUtils.replace(FilenameUtils.concat(dir, filePart), "\\", "/");

			if (!ftpClient.changeWorkingDirectory(dir)) {
				ftpClient.mkd(new String(FilenameUtils.getFullPath(dir + "/").getBytes(), ftpClient.getControlEncoding()));
			}
		}
	}

	protected void mkdir(ChannelSftp channel, String dir) throws SftpException {
		dir = StringUtils.replace(dir, "\\", "/");
		String[] dirPartList = StringUtils.split(dir, "/");
		dir = "/";

		for (String filePart : dirPartList) {
			dir = StringUtils.replace(FilenameUtils.concat(dir, filePart), "\\", "/");

			try {
				channel.get(FilenameUtils.getFullPath(dir + "/"));
			} catch (SftpException ex) {
				channel.mkdir(FilenameUtils.getFullPath(dir + "/"));
			}
		}
	}

	static interface Callback {
		void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException;
	}

	static interface SSHCallback {
		void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException;
	}
	
	public Long getFtpId() {
		return _ftpId;
	}

	public void setFtpId(Long ftpId) {
		this._ftpId = ftpId;
	}
}
