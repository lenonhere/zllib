package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import com.common.jasper.DesignBuilder;
import com.common.jasper.WebDataSource;
import com.common.jasper.ZLHtmlExporter;

public class PrintServlet extends HttpServlet {

	protected JasperPrint compileToFill(HttpServletRequest request)
			throws JRException {
		DesignBuilder builder = (DesignBuilder) request
				.getAttribute("design.template");
		List captions = (List) request.getAttribute("caption.list");
		List results = (List) request.getAttribute("result.list");
		Map parameters = (Map) request.getAttribute("parameter.map");

		JRDataSource dataSource = null;
		if (results == null || results.size() == 0) {
			dataSource = new JREmptyDataSource();
		} else {
			dataSource = new WebDataSource(results);
		}

		JasperReport report = builder.build(parameters, captions, results);

		return JasperFillManager.fillReport(report, parameters, dataSource);
	}

	protected void exportToHtml(HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException {

		JasperPrint jasperPrint = compileToFill(request);

		response.setContentType("text/html");

		ZLHtmlExporter exporter = new ZLHtmlExporter();

		Map imagesMap = new HashMap();
		request.getSession().setAttribute("IMAGES_MAP", imagesMap);

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
				response.getWriter());
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
				"<br style=\"page-break-before:always\">");

		exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
				request.getContextPath() + "/images/");

		exporter.exportReport();
	}

	protected void exportToPdf(HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException {
		JasperPrint jasperPrint = compileToFill(request);

		response.setContentType("application/pdf");

		JRPdfExporter exporter = new JRPdfExporter();

		ServletOutputStream ouputStream = response.getOutputStream();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, ouputStream);
		exporter.exportReport();
	}

	protected void batchExportToHtml(HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException {

		List allBuilders = (List) request.getAttribute("design.template");
		List allCaptions = (List) request.getAttribute("caption.list");
		List allResults = (List) request.getAttribute("result.list");
		List allParameters = (List) request.getAttribute("parameter.map");

		response.setContentType("text/html");

		PrintWriter writer = response.getWriter();

		ZLHtmlExporter exporter = new ZLHtmlExporter();

		Map imagesMap = new HashMap();
		request.getSession().setAttribute("IMAGES_MAP", imagesMap);
		exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, writer);
		exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
				"<br style='page-break-before:always'>");
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
		exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
				request.getContextPath() + "/images/");

		int min = allCaptions.size();

		for (int i = 0; i < min; i++) {
			DesignBuilder builder = (DesignBuilder) allBuilders.get(i);
			List captions = (List) allCaptions.get(i);
			List results = (List) allResults.get(i);
			Map parameters = (Map) allParameters.get(i);

			JasperPrint jasperPrint = batchCompileToFill(builder, captions,
					results, parameters);

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.exportReport();

			if (i < min - 1) { // avoid a last blank page
				writer.write("<br style='page-break-before:always'>");
			}
		}
	}

	protected JasperPrint batchCompileToFill(DesignBuilder builder,
			List captions, List results, Map parameters) throws JRException {

		JRDataSource dataSource = null;
		if (results == null || results.size() == 0) {
			dataSource = new JREmptyDataSource();
		} else {
			dataSource = new WebDataSource(results);
		}

		JasperReport report = builder.build(parameters, captions, results);

		return JasperFillManager.fillReport(report, parameters, dataSource);
	}

	protected void batchExportToPdf(HttpServletRequest request,
			HttpServletResponse response) throws JRException, IOException {
		throw new UnsupportedOperationException("batchExportToPdf");
	}

	public void service(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		Object exportFormat = request.getAttribute("exportFormat");

		Object designList = request.getAttribute("design.more");

		try {
			if ("true".equalsIgnoreCase((String) designList)) {
				if ("html".equalsIgnoreCase((String) exportFormat)) {
					batchExportToHtml(request, response);
				} else { // if ("pdf".equalsIgnoreCase( (String) exportFormat))
					batchExportToPdf(request, response);
				}
				return;
			}

			if ("html".equalsIgnoreCase((String) exportFormat)) {
				exportToHtml(request, response);
			} else { // if ("pdf".equalsIgnoreCase( (String) exportFormat))
				exportToPdf(request, response);
			}
		} catch (JRException e) {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>JasperReports - Web Application Sample</title>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
			out.println("</head>");
			out.println("<body bgcolor=\"white\">");
			out.println("<span class=\"bnew\">生成报表时发生错误，请将以下错误信息发送给管理员，谢谢!</span>");
			out.println("<pre>");
			e.printStackTrace(out);
			out.println("</pre>");
			out.println("</body>");
			out.println("</html>");
		}
	}
}
