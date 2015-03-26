package com.cyberway.common.fileDownload;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyberway.cms.Constants;




public class FileDownload extends HttpServlet {

	static final private String CONTENT_TYPE = "application/octet-stream; charset=GBK";
	private static Map<String, String> TYPES = null;

	//Process the HTTP Get request
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	public void doProcess(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream os = null;
		try {
			BlobFileObject bfo = (BlobFileObject) request
					.getAttribute(Constants.DOWNLOAD_BLOB_FILE_OBJECT);
			String fileName = bfo.getFilename();
			String fileType = null;
			try {
				fileType = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
			} catch (Exception e) {
				fileType = "*";
			}
			byte[] fileContent = (byte[]) bfo.getContent();
			response.reset();
			response.resetBuffer();
			String charset = response.getCharacterEncoding();
//			System.out.println("charset->"+charset);

			byte[] temp = fileName.getBytes("GBK");
			fileName = new String(temp, charset);

			//System.out.println("response.getCharacterEncoding()->"+response.getCharacterEncoding());
			//System.out.println("Locale.getDefault()->"+Locale.getDefault());
			

			response.addHeader("Content-Disposition","attachment;filename=" + fileName);

//				System.out.println("fileType->"+fileType);
			//response.setContentType(fileType);
			String contentType = TYPES.get(fileType);
			response.setContentType(contentType+";?charset=GBK");
//			log("ContentType->"+contentType);
			int len = fileContent.length;
			response.setContentLength(len);
			
			os = response.getOutputStream();
			
						
			os.write(fileContent, 0, len);

//			OutputStream fs = new FileOutputStream("D:\\java\\word.doc");
//			fs.write(fileContent);
//			fs.close();
			
			response.flushBuffer();
			
		} catch (Exception ex) {
		    ex.printStackTrace();
		    response.sendError(403);
		} finally {
			os.close();
		}
		
	}
	public void init() throws ServletException {
		if(TYPES == null){
		TYPES = new HashMap<String, String>();
		TYPES.put("*","application/octet-stream");
		TYPES.put(".001","application/x-001");
		TYPES.put(".301","application/x-301");
		TYPES.put(".323","text/h323");
		TYPES.put(".906","application/x-906");
		TYPES.put(".907","drawing/907");
		TYPES.put(".a11","application/x-a11");
		TYPES.put(".acp","audio/x-mei-aac");
		TYPES.put(".ai","application/postscript");
		TYPES.put(".aif","audio/aiff");
		TYPES.put(".aifc","audio/aiff");
		TYPES.put(".aiff","audio/aiff");
		TYPES.put(".anv","application/x-anv");
		TYPES.put(".asa","text/asa");
		TYPES.put(".asf","video/x-ms-asf");
		TYPES.put(".asp","text/asp");
		TYPES.put(".asx","video/x-ms-asf");
		TYPES.put(".au","audio/basic");
		TYPES.put(".avi","video/avi");
		TYPES.put(".awf","application/vnd.adobe.workflow");
		TYPES.put(".biz","text/xml");
		TYPES.put(".bmp","application/x-bmp");
		TYPES.put(".bot","application/x-bot");
		TYPES.put(".c4t","application/x-c4t");
		TYPES.put(".c90","application/x-c90");
		TYPES.put(".cal","application/x-cals");
		TYPES.put(".cat","application/vnd.ms-pki.seccat");
		TYPES.put(".cdf","application/x-netcdf");
		TYPES.put(".cdr","application/x-cdr");
		TYPES.put(".cel","application/x-cel");
		TYPES.put(".cer","application/x-x509-ca-cert");
		TYPES.put(".cg4","application/x-g4");
		TYPES.put(".cgm","application/x-cgm");
		TYPES.put(".cit","application/x-cit");
		TYPES.put(".class","java/*");
		TYPES.put(".cml","text/xml");//:客户端浏览器按XML格式进行解析文档 
		TYPES.put(".cmp","application/x-cmp");
		TYPES.put(".cmx","application/x-cmx");
		TYPES.put(".cot","application/x-cot");
		TYPES.put(".crl","application/pkix-crl");
		TYPES.put(".crt","application/x-x509-ca-cert");
		TYPES.put(".csi","application/x-csi");
		TYPES.put(".css","text/css");//:客户端浏览器按CSS格式进行解析文档 
		TYPES.put(".cut","application/x-cut");
		TYPES.put(".dbf","application/x-dbf");
		TYPES.put(".dbm","application/x-dbm");
		TYPES.put(".dbx","application/x-dbx");
		TYPES.put(".dcd","text/xml");//:客户端浏览器按XML格式进行解析文档 
		TYPES.put(".dcx","application/x-dcx");
		TYPES.put(".der","application/x-x509-ca-cert");
		TYPES.put(".dgn","application/x-dgn");
		TYPES.put(".dib","application/x-dib");
		TYPES.put(".dll","application/x-msdownload");
		TYPES.put(".doc","application/msword");
		TYPES.put(".dot","application/msword");
		TYPES.put(".drw","application/x-drw");
		TYPES.put(".dtd","text/xml");//:客户端浏览器按XML格式进行解析文档 
		TYPES.put(".dwf","Model/vnd.dwf");
		TYPES.put(".dwf","application/x-dwf");
		TYPES.put(".dwg","application/x-dwg");
		TYPES.put(".dxb","application/x-dxb");
		TYPES.put(".dxf","application/x-dxf");
		TYPES.put(".edn","application/vnd.adobe.edn");
		TYPES.put(".emf","application/x-emf");
		TYPES.put(".eml","message/rfc822");
		TYPES.put(".ent","text/xml");//:客户端浏览器按XML格式进行解析文档 
		TYPES.put(".epi","application/x-epi");
		TYPES.put(".eps","application/x-ps");
		TYPES.put(".eps","application/postscript");
		TYPES.put(".etd","application/x-ebx");
		TYPES.put(".exe","application/x-msdownload");
		TYPES.put(".fax","image/fax");
		TYPES.put(".fdf","application/vnd.fdf");
		TYPES.put(".fif","application/fractals");
		TYPES.put(".fo","text/xml");//:客户端浏览器按XML格式进行解析文档 
		TYPES.put(".frm","application/x-frm");
		TYPES.put(".g4","application/x-g4");
		TYPES.put(".gbr","application/x-gbr");
		TYPES.put(".gcd","application/x-gcd");
		TYPES.put(".gif","image/gif");
		TYPES.put(".gl2","application/x-gl2");
		TYPES.put(".gp4","application/x-gp4");
		TYPES.put(".hgl","application/x-hgl");
		TYPES.put(".hmr","application/x-hmr");
		TYPES.put(".hpg","application/x-hpgl");
		TYPES.put(".hpl","application/x-hpl");
		TYPES.put(".hqx","application/mac-binhex40");
		TYPES.put(".hrf","application/x-hrf");
		TYPES.put(".hta","application/hta");
		TYPES.put(".htc","text/x-component");
		TYPES.put(".htm","text/html");//:客户端浏览器按超文本格式进行解析文档 
		TYPES.put(".html","text/html");//:客户端浏览器按超文本格式进行解析文档 
		TYPES.put(".htt","text/webviewhtml");
		TYPES.put(".htx","text/html");//:客户端浏览器按超文本格式进行解析文档 
		TYPES.put(".icb","application/x-icb");
		TYPES.put(".ico","image/x-icon");
		TYPES.put(".ico","application/x-ico");
		TYPES.put(".iff","application/x-iff");
		TYPES.put(".ig4","application/x-g4");
		TYPES.put(".igs","application/x-igs");
		TYPES.put(".iii","application/x-iphone");
		TYPES.put(".img","application/x-img");
		TYPES.put(".ins","application/x-internet-signup");
		TYPES.put(".isp","application/x-internet-signup");
		TYPES.put(".IVF","video/x-ivf");
		TYPES.put(".java","java/*");
		TYPES.put(".jfif","image/jpeg");
		TYPES.put(".jpe","image/jpeg");
		TYPES.put(".jpe","application/x-jpe");
		TYPES.put(".jpeg","image/jpeg");
		TYPES.put(".jpg","image/jpeg");
		TYPES.put(".jpg","application/x-jpg");
		TYPES.put(".js","application/x-javascript");
		TYPES.put(".jsp","text/html");//:客户端浏览器按超文本格式进行解析文档 
		TYPES.put(".la1","audio/x-liquid-file");
		TYPES.put(".lar","application/x-laplayer-reg");
		TYPES.put(".latex","application/x-latex");
		TYPES.put(".lavs","audio/x-liquid-secure");
		TYPES.put(".lbm","application/x-lbm");
		TYPES.put(".lmsff","audio/x-la-lms");
		TYPES.put(".ls","application/x-javascript");
		TYPES.put(".ltr","application/x-ltr");
		TYPES.put(".m1v","video/x-mpeg");
		TYPES.put(".m2v","video/x-mpeg");
		TYPES.put(".m3u","audio/mpegurl");
		TYPES.put(".m4e","video/mpeg4");
		TYPES.put(".mac","application/x-mac");
		TYPES.put(".man","application/x-troff-man");
		TYPES.put(".math","text/xml");
		TYPES.put(".mdb","application/msaccess");
		TYPES.put(".mdb","application/x-mdb");
		TYPES.put(".mfp","application/x-shockwave-flash");
		TYPES.put(".mht","message/rfc822");
		TYPES.put(".mhtml","message/rfc822");
		TYPES.put(".mi","application/x-mi");
		TYPES.put(".mid","audio/mid");
		TYPES.put(".midi","audio/mid");
		TYPES.put(".mil","application/x-mil");
		TYPES.put(".mml","text/xml");
		TYPES.put(".mnd","audio/x-musicnet-download");
		TYPES.put(".mns","audio/x-musicnet-stream");
		TYPES.put(".mocha","application/x-javascript");
		TYPES.put(".movie","video/x-sgi-movie");
		TYPES.put(".mp1","audio/mp1");
		TYPES.put(".mp2","audio/mp2");
		TYPES.put(".mp2v","video/mpeg");
		TYPES.put(".mp3","audio/mp3");
		TYPES.put(".mp4","video/mpeg4");
		TYPES.put(".mpa","video/x-mpg");
		TYPES.put(".mpd","application/vnd.ms-project");
		TYPES.put(".mpe","video/x-mpeg");
		TYPES.put(".mpeg","video/mpg");
		TYPES.put(".mpg","video/mpg");
		TYPES.put(".mpga","audio/rn-mpeg");
		TYPES.put(".mpp","application/vnd.ms-project");
		TYPES.put(".mps","video/x-mpeg");
		TYPES.put(".mpt","application/vnd.ms-project");
		TYPES.put(".mpv","video/mpg");
		TYPES.put(".mpv2","video/mpeg");
		TYPES.put(".mpw","application/vnd.ms-project");
		TYPES.put(".mpx","application/vnd.ms-project");
		TYPES.put(".mtx","text/xml");
		TYPES.put(".mxp","application/x-mmxp");
		TYPES.put(".net","image/pnetvue");
		TYPES.put(".nrf","application/x-nrf");
		TYPES.put(".nws","message/rfc822");
		TYPES.put(".odc","text/x-ms-odc");
		TYPES.put(".out","application/x-out");
		TYPES.put(".p10","application/pkcs10");
		TYPES.put(".p12","application/x-pkcs12");
		TYPES.put(".p7b","application/x-pkcs7-certificates");
		TYPES.put(".p7c","application/pkcs7-mime");
		TYPES.put(".p7m","application/pkcs7-mime");
		TYPES.put(".p7r","application/x-pkcs7-certreqresp");
		TYPES.put(".p7s","application/pkcs7-signature");
		TYPES.put(".pc5","application/x-pc5");
		TYPES.put(".pci","application/x-pci");
		TYPES.put(".pcl","application/x-pcl");
		TYPES.put(".pcx","application/x-pcx");
		TYPES.put(".pdf","application/pdf");//:客户端浏览器按PDF格式进行解析文档 
		TYPES.put(".pdf","application/pdf");
		TYPES.put(".pdx","application/vnd.adobe.pdx");
		TYPES.put(".pfx","application/x-pkcs12");
		TYPES.put(".pgl","application/x-pgl");
		TYPES.put(".pic","application/x-pic");
		TYPES.put(".pko","application/vnd.ms-pki.pko");
		TYPES.put(".pl","application/x-perl");
		TYPES.put(".plg","text/html");
		TYPES.put(".pls","audio/scpls");
		TYPES.put(".plt","application/x-plt");
		TYPES.put(".png","image/png");
		TYPES.put(".png","application/x-png");
		TYPES.put(".pot","application/vnd.ms-powerpoint");
		TYPES.put(".ppa","application/vnd.ms-powerpoint");
		TYPES.put(".ppm","application/x-ppm");
		TYPES.put(".pps","application/vnd.ms-powerpoint");
		TYPES.put(".ppt","application/vnd.ms-powerpoint");
		TYPES.put(".ppt","application/x-ppt");
		TYPES.put(".pr","application/x-pr");
		TYPES.put(".prf","application/pics-rules");
		TYPES.put(".prn","application/x-prn");
		TYPES.put(".prt","application/x-prt");
		TYPES.put(".ps","application/x-ps");
		TYPES.put(".ps","application/postscript");
		TYPES.put(".ptn","application/x-ptn");
		TYPES.put(".pwz","application/vnd.ms-powerpoint");
		TYPES.put(".r3t","text/vnd.rn-realtext3d");
		TYPES.put(".ra","audio/vnd.rn-realaudio");
		TYPES.put(".ram","audio/x-pn-realaudio");
		TYPES.put(".ras","application/x-ras");
		TYPES.put(".rat","application/rat-file");
		TYPES.put(".rar","application/x-rar-compressed");
		TYPES.put(".rdf","text/xml");
		TYPES.put(".rec","application/vnd.rn-recording");
		TYPES.put(".red","application/x-red");
		TYPES.put(".rgb","application/x-rgb");
		TYPES.put(".rjs","application/vnd.rn-realsystem-rjs");
		TYPES.put(".rjt","application/vnd.rn-realsystem-rjt");
		TYPES.put(".rlc","application/x-rlc");
		TYPES.put(".rle","application/x-rle");
		TYPES.put(".rm","application/vnd.rn-realmedia");
		TYPES.put(".rmf","application/vnd.adobe.rmf");
		TYPES.put(".rmi","audio/mid");
		TYPES.put(".rmj","application/vnd.rn-realsystem-rmj");
		TYPES.put(".rmm","audio/x-pn-realaudio");
		TYPES.put(".rmp","application/vnd.rn-rn_music_package");
		TYPES.put(".rms","application/vnd.rn-realmedia-secure");
		TYPES.put(".rmvb","application/vnd.rn-realmedia-vbr");
		TYPES.put(".rmx","application/vnd.rn-realsystem-rmx");
		TYPES.put(".rnx","application/vnd.rn-realplayer");
		TYPES.put(".rp","image/vnd.rn-realpix");
		TYPES.put(".rpm","audio/x-pn-realaudio-plugin");
		TYPES.put(".rsml","application/vnd.rn-rsml");
		TYPES.put(".rt","text/vnd.rn-realtext");
		TYPES.put(".rtf","application/msword");
		TYPES.put(".rtf","application/x-rtf");
		TYPES.put(".rv","video/vnd.rn-realvideo");
		TYPES.put(".sam","application/x-sam");
		TYPES.put(".sat","application/x-sat");
		TYPES.put(".sdp","application/sdp");
		TYPES.put(".sdw","application/x-sdw");
		TYPES.put(".sit","application/x-stuffit");
		TYPES.put(".slb","application/x-slb");
		TYPES.put(".sld","application/x-sld");
		TYPES.put(".slk","drawing/x-slk");
		TYPES.put(".smi","application/smil");
		TYPES.put(".smil","application/smil");
		TYPES.put(".smk","application/x-smk");
		TYPES.put(".snd","audio/basic");
		TYPES.put(".sol","text/plain");
		TYPES.put(".sor","text/plain");
		TYPES.put(".spc","application/x-pkcs7-certificates");
		TYPES.put(".spl","application/futuresplash");
		TYPES.put(".spp","text/xml");
		TYPES.put(".ssm","application/streamingmedia");
		TYPES.put(".sst","application/vnd.ms-pki.certstore");
		TYPES.put(".stl","application/vnd.ms-pki.stl");
		TYPES.put(".stm","text/html");
		TYPES.put(".sty","application/x-sty");
		TYPES.put(".svg","text/xml");
		TYPES.put(".swf","application/x-shockwave-flash");//:客户端浏览器按 Flash 格式进行解析文档 
		TYPES.put(".tdf","application/x-tdf");
		TYPES.put(".tg4","application/x-tg4");
		TYPES.put(".tga","application/x-tga");
		TYPES.put(".tif","image/tiff");
		TYPES.put(".tif","application/x-tif");
		TYPES.put(".tiff","image/tiff");
		TYPES.put(".tld","text/xml");
		TYPES.put(".top","drawing/x-top");
		TYPES.put(".torrent","application/x-bittorrent");
		TYPES.put(".tsd","text/xml");
		TYPES.put(".txt","text/plain");//:客户端浏览器按 纯文本 格式进行解析文档 
		TYPES.put(".uin","application/x-icq");
		TYPES.put(".uls","text/iuls");
		TYPES.put(".vcf","text/x-vcard");
		TYPES.put(".vda","application/x-vda");
		TYPES.put(".vdx","application/vnd.visio");
		TYPES.put(".vml","text/xml");
		TYPES.put(".vpg","application/x-vpeg005");
		TYPES.put(".vsd","application/vnd.visio");
		TYPES.put(".vsd","application/x-vsd");
		TYPES.put(".vss","application/vnd.visio");
		TYPES.put(".vst","application/vnd.visio");
		TYPES.put(".vst","application/x-vst");
		TYPES.put(".vsw","application/vnd.visio");
		TYPES.put(".vsx","application/vnd.visio");
		TYPES.put(".vtx","application/vnd.visio");
		TYPES.put(".vxml","text/xml");
		TYPES.put(".wav","audio/wav");
		TYPES.put(".wax","audio/x-ms-wax");
		TYPES.put(".wb1","application/x-wb1");
		TYPES.put(".wb2","application/x-wb2");
		TYPES.put(".wb3","application/x-wb3");
		TYPES.put(".wbmp","image/vnd.wap.wbmp");
		TYPES.put(".wiz","application/msword");
		TYPES.put(".wk3","application/x-wk3");
		TYPES.put(".wk4","application/x-wk4");
		TYPES.put(".wkq","application/x-wkq");
		TYPES.put(".wks","application/x-wks");
		TYPES.put(".wm","video/x-ms-wm");
		TYPES.put(".wma","audio/x-ms-wma");
		TYPES.put(".wmd","application/x-ms-wmd");
		TYPES.put(".wmf","application/x-wmf");
		TYPES.put(".wml","text/vnd.wap.wml");
		TYPES.put(".wmv","video/x-ms-wmv");
		TYPES.put(".wmx","video/x-ms-wmx");
		TYPES.put(".wmz","application/x-ms-wmz");
		TYPES.put(".wp6","application/x-wp6");
		TYPES.put(".wpd","application/x-wpd");
		TYPES.put(".wpg","application/x-wpg");
		TYPES.put(".wpl","application/vnd.ms-wpl");
		TYPES.put(".wq1","application/x-wq1");
		TYPES.put(".wr1","application/x-wr1");
		TYPES.put(".wri","application/x-wri");
		TYPES.put(".wrk","application/x-wrk");
		TYPES.put(".ws","application/x-ws");
		TYPES.put(".ws2","application/x-ws");
		TYPES.put(".wsc","text/scriptlet");
		TYPES.put(".wsdl","text/xml");
		TYPES.put(".wvx","video/x-ms-wvx");
		TYPES.put(".xdp","application/vnd.adobe.xdp");
		TYPES.put(".xdr","text/xml");
		TYPES.put(".xfd","application/vnd.adobe.xfd");
		TYPES.put(".xfdf","application/vnd.adobe.xfdf");
		TYPES.put(".xhtml","text/html");
		TYPES.put(".xls","application/vnd.ms-excel");
		TYPES.put(".xls","application/x-xls");
		TYPES.put(".xlw","application/x-xlw");
		TYPES.put(".xml","text/xml");
		TYPES.put(".xpl","audio/scpls");
		TYPES.put(".xq","text/xml");
		TYPES.put(".xql","text/xml");
		TYPES.put(".xquery","text/xml");
		TYPES.put(".xsd","text/xml");
		TYPES.put(".xsl","text/xml");
		TYPES.put(".xslt","text/xml");
		TYPES.put(".xwd","application/x-xwd");
		TYPES.put(".x_b","application/x-x_b");
		TYPES.put(".x_t","application/x-x_t");
		}
	}

	//Clean up resources
	public void destroy() {
	}
}
