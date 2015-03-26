package com.cyberway.staticer.ftp;


public class SFTPClientImpl
{
	/*private String _host;
	private int _port = 990;
	private String _username;
	private String _password;
	private Properties _config = new Properties();
	private Session _session;
	private ChannelSftp _channel;
	
	public void setHost(String host)
	{
		_host = host;
	}
	
	public void setPort(int port)
	{
		_port = port;
	}
	
	public void setUsername(String username)
	{
		_username = username;
	}
	
	public void setPassword(String password)
	{
		_password = password;
	}
	
	public void setConfig(Properties config)
	{
		_config = config;
	}
	
	public void upload (final String fileName, final byte[] content)
	{
		execute(new Callback()
		{
			@Override
			public void doInChannel(ChannelSftp channel) throws SftpException, IOException
			{
				channel.cd(directory);
				
				channel.put(new ByteArrayInputStream(content), fileName);
			}
		});
	}
	
	@Override
	public void delete(String fileName)
	{
		execute (new Callback()
		{
			@Override
			public void doInChannel(ChannelSftp channel) throws SftpException, IOException
			{
				channel.rm
			}
		});
	}
	
	protected void execute (Callback callback)
	{
		try
		{
			if (_session == null)
			{
				JSch jsch = new JSch();
				_session = jsch.getSession(_username, _host, _port);
				_session.setPassword(_password);
				_session.setConfig(_config);
			}
			
			if (!_session.isConnected())
			{
				_session.connect();
				_channel = (ChannelSftp) _session.openChannel("sftp");
			}
			
			if (!_channel.isConnected())
			{
				_channel.connect();
			}
			
			callback.doInChannel(_channel);
		}
		catch (JSchException ex)
		{
			throw new RuntimeException(ex);
		}
		catch (SftpException ex)
		{
			throw new RuntimeException(ex);
		}
		catch (IOException ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	static interface Callback 
	{
		void doInChannel (ChannelSftp channel) throws SftpException, IOException;
	}*/
}
