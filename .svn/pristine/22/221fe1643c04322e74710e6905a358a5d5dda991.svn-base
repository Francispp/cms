package com.cyberway.cms.internal.template.token;

import org.apache.commons.lang.StringUtils;

import com.cyberway.cms.Constants;
import com.cyberway.cms.internal.template.TemplateConstants;
import com.cyberway.cms.internal.template.TokenUtils;
import com.cyberway.cms.template.token.AttributeToken;
import com.cyberway.cms.template.token.StartElementToken;
import com.cyberway.common.utils.BodyBuilder;

public class MediaViewWriter extends ComponentWriter{
	public MediaViewWriter()
	{
		super("MediaView","div");
	}
	@Override
	protected boolean isAllowAttribute(AttributeToken token)
	{
		return false;
	}
	@Override
	protected void writeComponentStart(StartElementToken startElement)
	{

		String id = TokenUtils.getAttributeValue(startElement, getTemplate(), "id");
		String name = TokenUtils.getAttributeValue(startElement, getTemplate(), "_name");
		String mediaType = TokenUtils.getAttributeValue(startElement, getTemplate(), "_mediaType");
		String mediaWidth = TokenUtils.getAttributeValue(startElement, getTemplate(), "_mediaWidth");
		String mediaHeight = TokenUtils.getAttributeValue(startElement, getTemplate(), "_mediaHeight");
		
		String style = TokenUtils.getAttributeValue(startElement, getTemplate(), "_style");
		if(name == null)
		{
			name = "";
		}
		if (StringUtils.isBlank(id))
		{
			id = getIdAllocator().allocate(TokenUtils.getAttributeValue(startElement, getTemplate(), TemplateConstants.ComponentTypeAttribute));
		}
		
		/*
		getComponentIdStack().push(id);
		
		StringBuffer sb=new StringBuffer();
		String divid = getComponentIdStack().peek();
		getMarkupWriter().element("div","class",style);
		sb.append("<script src='${ctx}/common/attachment/attachment.js' type='text/javascript'></script>");
		sb.append("<script src='${ctx}/common/attachment/xml.js' type='text/javascript'></script>");
		//getMarkupWriter().writeRaw(sb.toString());
		
		getMarkupWriter().element(getElementName(), "id", "div"+divid);
		getMarkupWriter().end ();
		sb.append("<script language='javascript'>t_show('${id}','"+divid+"','"+name+"','"+mediaWidth+"','"+mediaHeight+"','"+mediaType+"');</script>");
		getMarkupWriter().writeRaw(sb.toString());
	*/
		getComponentIdStack().push(id);
		String divid = getComponentIdStack().peek();
		//getMarkupWriter().element("div","class",style);
		//getMarkupWriter().element(getElementName(), "id", "div"+divid);
		//getMarkupWriter().end ();
		
		BodyBuilder jsp = new BodyBuilder ();
		jsp.begin(); 
		jsp.addln("com.cyberway.common.media.album.service.MediaIntermediateService mediaIntermediateService = (com.cyberway.common.media.album.service.MediaIntermediateService)ServiceLocator.getBean (\"mediaIntermediateService\");");
		jsp.addln("request.setAttribute (\"_data\", mediaIntermediateService.findView (request.getParameter (\"id\"),\""+name+"\",\""+mediaType+"\"));" );
		jsp.end();
		
		getMarkupWriter().writeRaw("<%" + jsp.toString() + "%>");
		
		getMarkupWriter().element("ww:iterator", "value", "#request._data", "status", "rowstatus");
		
		getMarkupWriter().writeRaw("<ww:if test=\"format=='flv'\">");
		getMarkupWriter().writeRaw("<ww:if test=\"issue==0\">");
		
		getMarkupWriter().writeRaw("<object id='FlashID' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' height='"+mediaHeight+"' width='"+mediaWidth+"' >");
		getMarkupWriter().writeRaw("<param name='movie' value='"+Constants.MEDIA_BROADCAST+"' /> <param name='FlashVars' value='videoURL=<ww:property value='fullFilePath' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />'/> <param name='allowFullScreen' value='true' />");
		getMarkupWriter().writeRaw("<param name='quality' value='high' /> <param name='wmode' value='opaque' /> <param name='swfversion' value='6.0.65.0' />");
		getMarkupWriter().writeRaw("<embed src='"+Constants.MEDIA_BROADCAST+"' height='"+mediaHeight+"' width='"+mediaWidth+"' bgcolor='#000000' allowscriptaccess='always' allowfullscreen='true' flashvars='videoURL=<ww:property value='fullFilePath' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />'/>");
		getMarkupWriter().writeRaw(" <div id='Ipad'></div>");
		getMarkupWriter().writeRaw("</object>");
		getMarkupWriter().writeRaw("<script>y_ipad();</script>");
		
	//	getMarkupWriter().writeRaw("<object id='FlashID' height='"+mediaHeight+"' width='"+mediaWidth+"' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'><param name='movie' value='"+Constants.MEDIA_BROADCAST+"' /><param name='FlashVars' value='videoURL=<ww:property value='fullName' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />' /><param name='allowFullScreen' value='true' /><param name='quality' value='high' /><param name='wmode' value='opaque' /><param name='swfversion' value='6.0.65.0' /> <embed src='"+Constants.MEDIA_BROADCAST+"' height='"+mediaHeight+"' width='"+mediaWidth+"' bgcolor='#000000'allowscriptaccess='always'allowfullscreen='true'flashvars=videoURL=<ww:property value='fullName' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />&autostart=true'/></object>");
		getMarkupWriter().writeRaw("</ww:if>");
		
		getMarkupWriter().writeRaw("<ww:else>");
	//	getMarkupWriter().writeRaw("<object id='FlashID' height='"+mediaHeight+"' width='"+mediaWidth+"' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'><param name='movie' value='"+Constants.MEDIA_BROADCAST_REMOTE+"' /><param name='FlashVars' value='videoURL=<ww:property value='fullName' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />' /><param name='allowFullScreen' value='true' /><param name='quality' value='high' /><param name='wmode' value='opaque' /><param name='swfversion' value='6.0.65.0' /><embed src='"+Constants.MEDIA_BROADCAST_REMOTE+"' height='"+mediaHeight+"' width='"+mediaWidth+"' bgcolor='#000000'allowscriptaccess='always'allowfullscreen='true'flashvars=videoURL=<ww:property value='fullName' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />&autostart=true'/></object>");
	
		getMarkupWriter().writeRaw("<object id='FlashID' classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' height='"+mediaHeight+"' width='"+mediaWidth+"' >");
		getMarkupWriter().writeRaw("<param name='movie' value='"+Constants.MEDIA_BROADCAST_REMOTE+"' /> <param name='FlashVars' value='videoURL=<ww:property value='fullFilePath' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />'/> <param name='allowFullScreen' value='true' />");
		getMarkupWriter().writeRaw("<param name='quality' value='high' /> <param name='wmode' value='opaque' /> <param name='swfversion' value='6.0.65.0' />");
		getMarkupWriter().writeRaw("<embed src='"+Constants.MEDIA_BROADCAST_REMOTE+"' height='"+mediaHeight+"' width='"+mediaWidth+"' bgcolor='#000000' allowscriptaccess='always' allowfullscreen='true' flashvars='videoURL=<ww:property value='fullFilePath' />&portId=<ww:property value='serverURL' />&styled=<ww:property value='ishiddenMediaFile' />'/>");
		getMarkupWriter().writeRaw(" <div id='Ipad'></div>");
		getMarkupWriter().writeRaw("</object>");
		getMarkupWriter().writeRaw("<script>y_ipad();</script>");
		
		
		getMarkupWriter().writeRaw("</ww:else>");
		getMarkupWriter().writeRaw("</ww:if>");

		getMarkupWriter().writeRaw("<ww:elseif test=\"format=='wmv'\">");
		getMarkupWriter().writeRaw("<object classid='clsid:22D6F312-B0F6-11D0-94AB-0080C74C7E95' codebase='' id='WindowsMediaPlayer1' Width='"+mediaWidth+"' Height='"+mediaHeight+"' align='top'><param name='ShowStatusBar' value='1'><param name='Filename' value='<ww:property value='serverURL' /><ww:property value='fullFilePath' />'><param name='PlayCount' value='1'><param name='AutoStart' value='0'><param name='ClickToPlay' value='1'><param name='DisplaySize' value='1'><param name='EnableFullScreen Controls' value='1'><param name='ShowAudio Controls' value='1'><param name='ShowControls' value='1'><param name='ShowTracker' value='1'><param name='EnableContext Menu' value='1'><param name='ShowDisplay' value='0'></object>");
		getMarkupWriter().writeRaw("</ww:elseif>");
		
		
		getMarkupWriter().writeRaw("<ww:elseif test=\"format=='mp3'\">");

	//	getMarkupWriter().writeRaw("<object id='MediaPlayer1' height=\""+mediaHeight+"\" width=\""+mediaWidth+"\" classid='CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6' codebase='http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112' align='baseline' border='0' standby='Loading Microsoft Windows Media Player components…'  type='application/x-oleobject'> <param name='URL' value='<ww:property value='serverURL' /><ww:property value='fullName' />'> <param name='autoStart' value='true'> <param name='invokeURLs' value='false'> <param name='playCount' value='100'> <param name='defaultFrame' value='datawindow'> <EMBED  src='<ww:property value='serverURL' /><ww:property value='fullName' />' loop='-1'  height=\""+mediaHeight+"\" width=\""+mediaWidth+"\" type=audio/x-pn-realaudio-plugin controls='all' /></object>");
		
		getMarkupWriter().writeRaw("<object id='MediaPlayer1' height=\""+mediaHeight+"\" width=\""+mediaWidth+"\" classid='CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6'"); 
		getMarkupWriter().writeRaw("codebase='http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112'"); 
		getMarkupWriter().writeRaw("align='baseline' border='0' standby='Loading Microsoft Windows Media Player components…'"); 
		getMarkupWriter().writeRaw("type='application/x-oleobject'>"); 
		getMarkupWriter().writeRaw("<param name='URL' value='<ww:property value='serverURL' /><ww:property value='fullFilePath' />'>"); 
		getMarkupWriter().writeRaw("<param name='autoStart' value='true'> <param name='invokeURLs' value='false'><param name='playCount' value='100'><param name='defaultFrame' value='datawindow'>"); 
		getMarkupWriter().writeRaw("<embed src='<ww:property value='serverURL' /><ww:property value='fullFilePath' />' align='baseline' border='0' height=\""+mediaHeight+"\" width=\""+mediaWidth+"\" type='application/x-mplayer2'"); 
		getMarkupWriter().writeRaw(" pluginspage='' name='MediaPlayer1' showcontrols='1' showpositioncontrols='0' showaudiocontrols='1' showtracker='1' showdisplay='0' showstatusbar='1' autosize='0'"); 
		getMarkupWriter().writeRaw(" showgotobar='0' showcaptioning='0' autostart='1' autorewind='0' animationatstart='0' transparentatstart='0' allowscan='1' enablecontextmenu='1' clicktoplay='0' defaultframe='datawindow' invokeurls='0'></embed>");
		getMarkupWriter().writeRaw(" </object>");
			
		
		getMarkupWriter().writeRaw("</ww:elseif>");

		
		//getMarkupWriter().end();
	}
	
	@Override
	protected void writeComponentEnd()
	{
		super.writeComponentEnd();
	}


}
