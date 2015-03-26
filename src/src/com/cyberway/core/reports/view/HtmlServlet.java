package com.cyberway.core.reports.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

/**
 * @author caiw
 *
 */
public class HtmlServlet extends BaseHttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6946949811915374024L;

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		List jasperPrintList = getJasperPrintList(request);

		if (jasperPrintList == null) {
			throw new ServletException(
					"No JasperPrint documents found on the HTTP session.");
		}

		Boolean isBuffered = Boolean.valueOf(request
				.getParameter(BUFFERED_OUTPUT_REQUEST_PARAMETER));
		if (isBuffered.booleanValue()) {
			JRHtmlExporter exporter = new JRHtmlExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					jasperPrintList);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
			"image?image=");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			

			try {
				exporter.exportReport();
			} catch (JRException e) {
				throw new ServletException(e);
			}

			byte[] bytes = baos.toByteArray();

			if (bytes != null && bytes.length > 0) {
				response.setContentLength(bytes.length);
				ServletOutputStream ouputStream = response.getOutputStream();

				try {
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
				} finally {
					if (ouputStream != null) {
						try {
							ouputStream.close();
						} catch (IOException ex) {
						}
					}
				}
			}
		} else {
			JRHtmlExporter exporter = new JRHtmlExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
					jasperPrintList);

			OutputStream ouputStream = response.getOutputStream();
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
			"image?image=");
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					ouputStream);
			
			try {
				exporter.exportReport();
			} catch (JRException e) {
				throw new ServletException(e);
			} finally {
				if (ouputStream != null) {
					try {
						ouputStream.close();
					} catch (IOException ex) {
					}
				}
			}
		}
	}

}
