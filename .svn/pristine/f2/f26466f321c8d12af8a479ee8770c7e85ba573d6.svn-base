package com.cyberway.core.reports.service;

import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.cyberway.core.dao.HibernateGenericDao;
import com.cyberway.core.exception.BizException;
import com.cyberway.core.utils.PropsUtil;


/**
 * @author caiw
 * 
 */
public class ReportsService extends HibernateGenericDao {

	private static final long serialVersionUID = 1403920944860345668L;

	public final static String JASPER_REPORTS_DESIGN_EXTENSION = ".jrxml";
	public final static String COMPILED_JASPER_REPORTS_EXTENSION = ".jasper";
	public final static String REPORT_TYPE_EXCEL = "xls";
	public final static String REPORT_TYPE_WORD = "rtf";
	public final static String REPORT_TYPE_PDF = "pdf";
	public final static String REPORT_TYPE_XML = "xml";
	public final static String REPORT_TYPE_GRAPHICS2D = "2d";
	public final static String REPORT_TYPE_TEXT = "text";
	public final static String REPORT_TYPE_CSV = "csv";
	public final static String REPORT_TYPE_HTML = "html";

	public JasperPrint getReport(File modelFile, Map parameters)
			throws BizException {
		JasperPrint jasperPrint = null;
		try {
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(modelFile);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, getConnection());
		} catch (JRException e) {
			throw new BizException("报表错误！", e);
		}
		return jasperPrint;
	}

	public JasperPrint getReport(URL url, Map parameters) throws BizException {
		JasperPrint jasperPrint = null;
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, getConnection());
		} catch (JRException e) {
			throw new BizException("报表错误！", e);
		}
		return jasperPrint;
	}

	public JasperPrint getReport(String path, Map parameters) throws BizException {
		JasperPrint jasperPrint = null;
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(path);
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parameters, getConnection());
		} catch (JRException e) {
			throw new BizException("报表错误！", e);
		}
		return jasperPrint;
	}

	private JRExporter getExporter(String outputType, String reportName) {
		JRExporter exporter = null;
		if ("html".equals(outputType)) {
			String removeEmptyRows = PropsUtil.getInstance().getProperty(
					"report.htmlExportOptions.removeEmptySpaceBetweenRows");
			exporter = new JRHtmlExporter();
			exporter.setParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					new Boolean(false));
			if (removeEmptyRows != null
					&& "true".equalsIgnoreCase(removeEmptyRows)) {
				exporter
						.setParameter(
								JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								new Boolean(true));
			}
			String imageUrl = "image?image=";
			String imagePath = PropsUtil.getInstance().getProperty(
					"report.tmpDir");
			if (!imagePath.endsWith(File.separator))
				imagePath = imagePath + File.separator;
			imagePath = imagePath + reportName + File.separator;
			File imageDir = new File(imagePath);
			imageDir.mkdirs();
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR, imageDir);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, imageUrl
					+ reportName + "/");
			exporter.setParameter(
					JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
					new Boolean(true));
		} else if ("pdf".equals(outputType)) {
			exporter = new JRPdfExporter();
		} else if ("xls".equals(outputType)) {
			exporter = new JRXlsExporter();
		} else if ("csv".equals(outputType)) {
			exporter = new JRCsvExporter();
		} else if ("xml".equals(outputType)) {
			exporter = new JRXmlExporter();
		} else if ("rtf".equals(outputType)) {
			exporter = new JRRtfExporter();
		} else if ("text".equals(outputType)) {
			exporter = new JRTextExporter();
		} else if ("2d".equals(outputType)) {
			try {
				exporter = new JRGraphics2DExporter();
			} catch (JRException e) {
				e.printStackTrace();
			}
		}
		return exporter;
	}

	public boolean executeReport(String reportOutputType, File sourceFile,
			Map parameters, OutputStream outputStream) {
		String reportBaseName = sourceFile.getName();
		int extensionIdx = reportBaseName.lastIndexOf("."); //$NON-NLS-1$
		if (extensionIdx > 0)
			reportBaseName = reportBaseName.substring(0, extensionIdx);
		String compiledReportPath = "";
		String reportDefinitionPath = sourceFile.getPath();
		if (sourceFile.getPath().endsWith(COMPILED_JASPER_REPORTS_EXTENSION)) {
			compiledReportPath = reportDefinitionPath;
		} else {
			// Assume its a .jrxml
			if (!sourceFile.exists()) {
				logger.error("报表模版不存在！");
				return false;
			}
			String reportDirectory = sourceFile.getParent();
			if (!reportDirectory.endsWith(File.separator)) {
				reportDirectory = reportDirectory + File.separator;
			}
			compiledReportPath = reportDirectory + reportBaseName
					+ COMPILED_JASPER_REPORTS_EXTENSION;
			File compiledReportFile = new File(compiledReportPath);

			if (!compiledReportFile.exists()
					|| sourceFile.lastModified() > compiledReportFile
							.lastModified()) {
				// Use the jdt compiler
				System.setProperty("org.xml.sax.driver",
						"org.apache.xerces.parsers.SAXParser");
				System.setProperty("jasper.reports.compiler.class",
						"net.sf.jasperreports.engine.design.JRJdtCompiler");
				// Compile the report design
				try {
					JasperCompileManager.compileReportToFile(
							reportDefinitionPath, compiledReportPath);
					logger.debug("编译报表模版：" + compiledReportPath);
				} catch (JRException jre) {
					logger.error(jre);
					return false;
				}

			}
		}

		// See if we have the require runtime report parameters
		// Any non-system parameter in the report design that is marked
		Map reportParameters = new HashMap();

		JasperReport jrreport = null;
		try {
			jrreport = (JasperReport) JRLoader.loadObject(compiledReportPath);
		} catch (JRException jre) {
			logger.debug("报表加载错误!");
			return false;
		}
		JRParameter[] jrparams = jrreport.getParameters();

		for (int i = 0; i < jrparams.length; i++) {
			JRParameter param = jrparams[i];
			if (param.isSystemDefined())
				continue;
			String parameterName = param.getName();
			String parameterValue = null;
			if (parameters.containsKey(parameterName)) {
				parameterValue = parameters.get(parameterName).toString();
			}
			if (parameterValue != null && parameterValue.length() != 0) {
				reportParameters.put(parameterName, parameterValue);
			}
		}


		Connection conn = getConnection();
		if (conn == null)
			return false;
		try {
			// Fill the report
			JasperPrint jrprint = JasperFillManager.fillReport(
					compiledReportPath, reportParameters, conn);

			// Get a configure exporter for the desired output format.
			JRExporter exporter = getExporter(reportOutputType, reportBaseName);

			// Set the filled report and output stream.
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jrprint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					outputStream);
			// Go!
			exporter.exportReport();
		} catch (JRException jre) {
			logger.error(jre);
			return false;
		}
		return true;
	}
}
