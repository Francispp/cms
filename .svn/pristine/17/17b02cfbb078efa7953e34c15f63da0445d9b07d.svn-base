package com.cyberway.staticer.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ResponseFilter implements Filter
{
	public void init(FilterConfig arg0) throws ServletException 
	{
	}
	 private String getUrlAndParameters(HttpServletRequest hreq){
	    	
	    	Map parms =hreq.getParameterMap();
	    	Iterator it=parms.keySet().iterator();
	    	StringBuffer sb=new StringBuffer();
	    	while(it.hasNext()){//增加其他参数
	    		String parmname=(String)it.next();
	    		
	    			sb.append("&").append(parmname).append("=").append(hreq.getParameter(parmname));
	    	}
	    	return sb.toString();
	    }
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		/*String currentURL =request.getRequestURI().trim();
		if(currentURL.indexOf("docInfo!list.action") != -1)
		{
		ByteArrayOutputStream outstr = new ByteArrayOutputStream();
		GenericResponseWrapper responseWrapper = new GenericResponseWrapper (response, outstr);
		
		chain.doFilter(request, responseWrapper); 
		responseWrapper.flush();
		// 迁移 Header
		if (responseWrapper.getHeaders() != null && !responseWrapper.getHeaders().isEmpty())
		{
			List<Object[]> headers = IteratorUtils.toList(responseWrapper.getHeaders().iterator());
			for (Object[] header : headers)
			{
				final String name = (String)header[0];
				Object value = header[1];
				System.out.println(name+"---------set header---------------"+value);
				if (value != null && value instanceof Integer)
				{
					response.setIntHeader(name, (Integer)value);
				}
				else if (value != null && value instanceof Long)
				{
					response.setDateHeader(name, (Long)value);
				}
				else if (value != null && value instanceof String)
				{
					response.setHeader(name, (String)value);
				}
			}
		}

		// 迁移 Cookie
		if (responseWrapper.getCookies() != null && !responseWrapper.getCookies().isEmpty())
		{
			List cookies = IteratorUtils.toList(responseWrapper.getCookies().iterator());
			
			for (Cookie cookie : (List<Cookie>)cookies)
			{
				String name = cookie.getName();
				String value = cookie.getValue();

				System.out.println(name+"---------set header---------------"+value);
				response.addCookie(new Cookie (name, value));
			}
		}
		
		// 迁移内容类型
		response.setContentType(responseWrapper.getContentType());
		
		// 迁移内容长度
		response.setContentLength(outstr.size());

		// 迁移内空
		IOUtils.write(outstr.toByteArray(), response.getOutputStream());
		}
		else*/
			chain.doFilter(request, response);
	}
	
	public void destroy() 
	{	
	}
	
	static class GenericResponseWrapper extends HttpServletResponseWrapper implements Serializable
	{
		private static final long serialVersionUID = -5976708169031065498L;

		private int _statusCode = SC_OK;
		private int _contentLength;
		private String _contentType;
		private final List _headers = new ArrayList();
		private final List _cookies = new ArrayList();
		private ServletOutputStream _outputStream;
		private PrintWriter _writer;

		public GenericResponseWrapper(final HttpServletResponse response, final OutputStream outputStream)
		{
			super(response);
			
			_outputStream = new FilterServletOutputStream(outputStream);
		}

		public ServletOutputStream getOutputStream()
		{
			return _outputStream;
		}

		public void setStatus(final int code)
		{
			_statusCode = code;
			
			super.setStatus(code);
		}

		public void sendError(int i, String string) throws IOException
		{
			_statusCode = i;
			
			super.sendError(i, string);
		}

		public void sendError(int i) throws IOException
		{
			_statusCode = i;
			
			super.sendError(i);
		}

		public void sendRedirect(String string) throws IOException
		{
			_statusCode = HttpServletResponse.SC_MOVED_TEMPORARILY;
			
			super.sendRedirect(string);
		}

		public void setStatus(final int code, final String msg)
		{
			_statusCode = code;
			
			super.setStatus(code);
		}

		public int getStatus()
		{
			return _statusCode;
		}

		public void setContentLength(final int length)
		{
			_contentLength = length;
			
			super.setContentLength(length);
		}

		public int getContentLength()
		{
			return _contentLength;
		}

		public void setContentType(final String type)
		{
			_contentType = type;
			
			super.setContentType(type);
		}

		public String getContentType()
		{
			return _contentType;
		}

		public PrintWriter getWriter() throws IOException
		{
			if (_writer == null)
			{
				_writer = new PrintWriter(new OutputStreamWriter(_outputStream, getCharacterEncoding()), true);
			}
			
			return _writer;
		}

		public void addHeader(final String name, final String value)
		{
			_headers.add(new String[] { name, value });
			
			super.addHeader(name, value);
		}

		public void setHeader(final String name, final String value)
		{
			addHeader(name, value);
		}

		public Collection getHeaders()
		{
			return _headers;
		}

		public void addCookie(final Cookie cookie)
		{
			_cookies.add(cookie);
			
			super.addCookie(cookie);
		}

		public Collection getCookies()
		{
			return _cookies;
		}

		public void flushBuffer() throws IOException
		{
			flush();
			
			super.flushBuffer();
		}

		public void reset()
		{
			super.reset();
			
			_cookies.clear();
			_headers.clear();
			_statusCode = SC_OK;
			_contentType = null;
			_contentLength = 0;
		}

		public void resetBuffer()
		{
			super.resetBuffer();
		}

		public void flush() throws IOException
		{
			if (_writer != null)
			{
				_writer.flush();
			}
			
			_outputStream.flush();
		}

		public String encodeRedirectUrl(String s)
		{
			return super.encodeRedirectURL(s);
		}

		public String encodeUrl(String s)
		{
			return super.encodeURL(s);
		}

		static class FilterServletOutputStream extends ServletOutputStream
		{
			private OutputStream _outputStream;

			public FilterServletOutputStream(final OutputStream stream)
			{
				this._outputStream = stream;
			}

			public void write(final int b) throws IOException
			{
				_outputStream.write(b);
			}

			public void write(final byte[] b) throws IOException
			{
				_outputStream.write(b);
			}

			public void write(final byte[] b, final int off, final int len) throws IOException
			{
				_outputStream.write(b, off, len);
			}
		}
	}

}
