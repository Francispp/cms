package com.cyberway.core.utils.ajax;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caucho.burlap.io.BurlapInput;
import com.caucho.burlap.io.BurlapOutput;
import com.caucho.burlap.server.BurlapSkeleton;


/**
 * Buffalo Service Servlet, the central servlet for the Buffalo Service.
 * 
 * @author caiw
 * @version 2.0
 */
public class BuffaloServiceServlet extends HttpServlet {

	private static final long serialVersionUID = 5433065110640447935L;

	private static final String BUFFALO_SERVICE_KEY = "net.buffalo.service.BUFFALO_SERVICE";
		
	private Object _service;

	private BurlapSkeleton _skeleton;

	public String getServletInfo() {
		return "Buffalo Service Servlet";
	}

	/**
	 * Initialize the service, including the service object.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public synchronized void service(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (!req.getMethod().equals("POST")) {
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ajax Requires POST");
			res.setContentType("text/html");
			return;
		}
		
		String serviceName = request.getParameter(Buffalo.SERVICE_NAME_ID);
		String serviceClazz = getServiceClass(serviceName);

		try {
			Class claz = Class.forName(serviceClazz);
			_service = claz.newInstance();

			((BuffaloService) _service).init(getServletContext(), request);
			_skeleton = new BurlapSkeleton(_service,_service.getClass());
		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		InputStream is = request.getInputStream();
		OutputStream os = response.getOutputStream();

		BurlapInput in = new BurlapInput(is);
		BurlapOutput out = new BurlapOutput(os) {
			public void startReply() throws IOException {
				print("<?xml version=\"1.0\" encoding=\"utf-8\"?><burlap:reply xmlns:burlap=\"http://www.amowa.net/buffalo/\">");
			}
		};

		try {
			_skeleton.invoke(in, out);
		} catch (Throwable e) {
			throw new ServletException(e);
		}
	}

	/**
	 * Get the service class name from the buffalo config file by the service
	 * name.
	 * 
	 * @param serviceName
	 *            the service name
	 * @return the service class name
	 * 
	 * @throws IOException
	 */
	private String getServiceClass(String serviceName) throws IOException {
		ServiceRepository repo = null;
		if (getServletContext().getAttribute(BUFFALO_SERVICE_KEY) == null) {
			repo = BuffaloUtils.populateRepository();
			getServletContext().setAttribute(BUFFALO_SERVICE_KEY, repo);
		} else {
			repo = (ServiceRepository) (getServletContext().getAttribute(BUFFALO_SERVICE_KEY));
		}

		return repo.getService(serviceName);
	}
}