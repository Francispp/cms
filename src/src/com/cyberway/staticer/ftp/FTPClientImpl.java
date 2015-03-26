package com.cyberway.staticer.ftp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * @see FTPClient
 * @author helfen
 * 
 */
public class FTPClientImpl implements FTPClient {
	private Long	      _ftpId;
	private String	      _host;
	private int	          _port	= 21;
	private String	      _username;
	private String	      _password;
	private boolean	      _ssh;
	private JSch	      _jsch	= new JSch();
	private EventListener	_eventListener;

	public String	      defaultRoot;
	public String	      staticPath;
	public String	      wordCenterPath;
	public String	      movementPath;
	public String	      otherCenterPath;
	public String	      htmlCenterPath;
	public String	      attachmentpath;
	public String	      otherImagePath;
	public String	      otherFlashPath;
	public String	      lucenePath;

	public FTPClientImpl() {
		super();
	}

	public FTPClientImpl(Long ftpId, String host, int port, String username, String password, boolean ssh) {
		super();
		this._ftpId = ftpId;
		this._host = host;
		this._port = port;
		this._username = username;
		this._password = password;
		this._ssh = ssh;
	}

	public void upload(String fileName, byte[] content) {
		upload(fileName, content, true);
	}

	public void upload(String fileName, final byte[] content, final boolean overwrite) {
		final String fileName1 = StringUtils.replace(fileName, "\\", "/");
		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						mkdir(channel, FilenameUtils.getFullPath(fileName1));

						OutputStream outstr = channel.put(fileName1);
						IOUtils.write(content, outstr);
						IOUtils.closeQuietly(outstr);

						if (_eventListener != null) {
							_eventListener.onUploadSuccessed(fileName1, content);
						}
					} catch (Exception ex) {
						if (_eventListener != null) {
							_eventListener.onUploadFailed(fileName1, content);
						}

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
							outstr.write(content);

							outstr.flush();
							outstr.close();

							if (ftpClient.completePendingCommand()) {
								if (_eventListener != null) {
									_eventListener.onUploadSuccessed(fileName1, content);
								}
							} else {
								if (_eventListener != null) {
									_eventListener.onUploadFailed(fileName1, content);
								}
							}
						}
					} catch (Exception ex) {
						if (_eventListener != null) {
							_eventListener.onUploadFailed(fileName1, content);
						}

						throw new RuntimeException(ex);
					}
				}
			});
		}
	}

	public void delete(String fileName) {
		final String fileName1 = StringUtils.replace(fileName, "\\", "/");

		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						channel.rm(fileName1);

						if (_eventListener != null) {
							_eventListener.onDeleteSuccessed(fileName1);
						}
					} catch (Exception ex) {
						if (_eventListener != null) {
							_eventListener.onDeleteFailed(fileName1);
						}

						throw new RuntimeException(ex);
					}
				}
			});
		} else {
			execute(new Callback() {
				public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					try {
						ftpClient.dele(fileName1);

						if (_eventListener != null) {
							_eventListener.onDeleteSuccessed(fileName1);
						}
					} catch (Exception ex) {
						if (_eventListener != null) {
							_eventListener.onDeleteFailed(fileName1);
						}

						throw new RuntimeException(ex);
					}
				}
			});
		}
	}

	protected void execute(Callback callback) {
		org.apache.commons.net.ftp.FTPClient ftpClient = null;
		try {
			ftpClient = new org.apache.commons.net.ftp.FTPClient();

			ftpClient.connect(_host, _port);
			ftpClient.login(_username, _password);

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

	public List<String> getList(String filePath) {
		final List<String> list = new ArrayList<String>();
		final String fileName1 = StringUtils.replace(filePath, "\\", "/");
		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						// channel.rmdir(fileName1);
						// 如果上面API不行就用以下方式、it.next() 中 的数据待测试

						List<LsEntry> l = channel.ls(fileName1);
						for (int i = 0; i < l.size(); i++) {
							LsEntry file = l.get(i);
							System.out.println(file.getFilename());
							list.add(file.getFilename());
						}

					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		} else {
			execute(new Callback() {
				public void doInFtpClient(org.apache.commons.net.ftp.FTPClient ftpClient) throws IOException {
					try {
						FTPFile[] ftpFiles = ftpClient.listFiles(fileName1);
						for (FTPFile ftpFile : ftpFiles) {
							list.add(ftpFile.getName());
						}
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}
		return list;
	}

	public void deleteByPath(String filePath) {
		final String fileName1 = StringUtils.replace(filePath, "\\", "/");
		if (isSsh()) {
			execute(new SSHCallback() {
				public void doInChannel(Session session, ChannelSftp channel) throws IOException, SftpException {
					try {
						// sftp根据指定文件夹删除下面的文件
						String ftpPath = channel.realpath(fileName1);
						System.out.println(ftpPath);
						Vector<LsEntry> l = channel.ls(ftpPath);
						for (LsEntry lsEntry : l) {
							if (lsEntry.getAttrs().isDir()) {
								if (!lsEntry.getFilename().equals("..")) {
									if (!lsEntry.getFilename().equals(".")) {
										deleteByPath(fileName1 + File.separator + lsEntry.getFilename());
									}
								}
							} else {
								delete(fileName1 + File.separator + lsEntry.getFilename());
							}
						}
					} catch (Exception ex) {
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
									deleteByPath(fileName1 + File.separator + ftpFiles[i].getName());
								} else {
									delete(fileName1 + File.separator + ftpFiles[i].getName());
								}
							}
						}
					} catch (Exception ex) {
						throw new RuntimeException(ex);
					}
				}
			});
		}
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

	public void setEventListener(EventListener eventListener) {
		_eventListener = eventListener;
	}

	public String getDefaultRoot() {
		return defaultRoot;
	}

	public void setDefaultRoot(String defaultRoot) {
		this.defaultRoot = defaultRoot;
	}

	public String getStaticPath() {
		return staticPath;
	}

	public void setStaticPath(String staticPath) {
		this.staticPath = staticPath;
	}

	public String getWordCenterPath() {
		return wordCenterPath;
	}

	public void setWordCenterPath(String wordCenterPath) {
		this.wordCenterPath = wordCenterPath;
	}

	public String getMovementPath() {
		return movementPath;
	}

	public void setMovementPath(String movementPath) {
		this.movementPath = movementPath;
	}

	public String getLucenePath() {
		return lucenePath;
	}

	public void setLucenePath(String lucenePath) {
		this.lucenePath = lucenePath;
	}

	public String getOtherCenterPath() {
		return otherCenterPath;
	}

	public void setOtherCenterPath(String otherCenterPath) {
		this.otherCenterPath = otherCenterPath;
	}

	public String getHtmlCenterPath() {
		return htmlCenterPath;
	}

	public void setHtmlCenterPath(String htmlCenterPath) {
		this.htmlCenterPath = htmlCenterPath;
	}

	public String getAttachmentpath() {
		return attachmentpath;
	}

	public void setAttachmentpath(String attachmentpath) {
		this.attachmentpath = attachmentpath;
	}

	public String getOtherImagePath() {
		return otherImagePath;
	}

	public void setOtherImagePath(String otherImagePath) {
		this.otherImagePath = otherImagePath;
	}

	public String getOtherFlashPath() {
		return otherFlashPath;
	}

	public void setOtherFlashPath(String otherFlashPath) {
		this.otherFlashPath = otherFlashPath;
	}
}