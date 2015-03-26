<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/template/public.inc"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=8" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="/css/base.css" rel="stylesheet"/>
	<link href="/css/konka.company.css" rel="stylesheet"/>
	<link href="/css/sitemap.css" rel="stylesheet"/>
	<title>网站地图</title>
	<script src="/common/cms_js/util.js" type="text/javascript"></script>
	<script type="text/javascript" src="/common/jquery/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="/scripts/konka.company.js"></script>
	<script src="/common/cms_js/cms.js" type="text/javascript" language="javascript"></script>
</head>
<body class="srpage">
<div class="kanKaBox">
    <div class="hrcontent">
    	<div class="sitemapBanner">
		</div>
        <div class="postconBox">
			<div class="postTitle">
				<img src="/images/sitemap_titel.jpg" alt="" width="157" height="32"/>
			</div>
			<div class="postMark">
				<span class="hr_now_page">网站地图</span>&nbsp;&nbsp;&lt;&nbsp;
				<a href="/">首页</a>
				<i class="curTopic_ico"></i>
			</div>
			<div class="clear"></div>
        </div>
		<div id="sitemap_content">
			<div class="sitemap_div">
				<div class="sitemap_titel">
					<h1><a target='<ww:property value="xflcp.target" />' href='<ww:property value="xflcp.link" />'><ww:property value="xflcp.name" /></a></h1>
				</div>
				<ww:iterator value="xflcp.subItems" id="xflcpItem" status="xflcpItemStatus">
					<ww:if test="#xflcpItemStatus.last">
					<ul style="width:45px;">
					</ww:if>
					<ww:else>
					<ul style="width:125px;">
					</ww:else>
					<li><a target='<ww:property value="#xflcpItem.target" />' href='<ww:property value="#xflcpItem.link" />' class="type"><ww:property value="#xflcpItem.name"/></a></li>
					<ww:iterator value="#xflcpItem.subItems" id="xflcpItemSub">
					<li><a target='<ww:property value="#xflcpItemSub.target" />' href='<ww:property value="#xflcpItemSub.link"/>'><ww:property value="#xflcpItemSub.name"/></a></li>
					</ww:iterator>
				</ul>
				</ww:iterator>
				<div class="clear"></div>
			</div>
			<div class="sitemap_div">
				<div class="sitemap_titel">
					<h1><a target='<ww:property value="sycp.target" />' href='<ww:property value="sycp.link" />'><ww:property value="sycp.name" /></a></h1>
				</div>
				<ww:iterator value="sycp.subItems" id="sycpItem">
				<ul style="width:125px;">
					<li><a target='<ww:property value="#sycpItem.target" />' href='<ww:property value="#sycpItem.link" />' class="type"><ww:property value="#sycpItem.name"/></a></li>
					<ww:iterator value="#sycpItem.subItems" id="sycpItemSub">
					<li><a target='<ww:property value="#sycpItemSub.target" />' href='<ww:property value="#sycpItemSub.link"/>'><ww:property value="#sycpItemSub.name"/></a></li>
					</ww:iterator>
				</ul>
				</ww:iterator>
				<div class="clear"></div>
			</div>
			<div class="sitemap_div">
				<ww:iterator value="hdzx.subItems" id="hdzxItem">
				<ul style="width:125px;">
					<div class="sitemap_titel">
						<h1><a target='<ww:property value="#hdzxItem.target" />' href='<ww:property value="#hdzxItem.link" />'><ww:property value="#hdzxItem.name" /></a></h1>
					</div>
					<ww:iterator value="#hdzxItem.subItems" id="hdzxItemSub">
					<li><a target='<ww:property value="#hdzxItemSub.target" />' href='<ww:property value="#hdzxItemSub.link"/>'><ww:property value="#hdzxItemSub.name"/></a></li>
					</ww:iterator>
				</ul>
				</ww:iterator>
				<div class="clear"></div>
			</div>
			<div class="sitemap_div">
				<div class="sitemap_titel">
					<h1><a target='<ww:property value="rlzy.target" />' href='<ww:property value="rlzy.link" />'><ww:property value="rlzy.name" /></a></h1>
				</div>
				<ww:iterator value="rlzy.subItems" id="rlzyItem">
				<ul style="width:125px;">
					<li><a target='<ww:property value="#rlzyItem.target" />' href='<ww:property value="#rlzyItem.link" />' class="type"><ww:property value="#rlzyItem.name"/></a></li>
					<ww:iterator value="#rlzyItem.subItems" id="rlzyItemSub">
					<li>
						<a target='<ww:property value="#rlzyItemSub.target" />' href='<ww:property value="#rlzyItemSub.link"/>' class="sub1"><ww:property value="#rlzyItemSub.name"/></a>
						<ul>
						<ww:iterator value="#rlzyItemSub.subItems" id="rlzyItemSubSub">
							<li><a target='<ww:property value="#rlzyItemSubSub.target" />' href='<ww:property value="#rlzyItemSubSub.link" />' class="sub2"><ww:property value="#rlzyItemSubSub.name"/></a></li>
						</ww:iterator>
						</ul>
					</li>
					</ww:iterator>
				</ul>
				</ww:iterator>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div>
		<div id="sitemap_footer">
			<p>相关链接： <a target="_blank" href="http://www.sasac.gov.cn/‎" class="sitemap_link3">国务院国有资产监督管理委员会</a>　<a target="_blank" href="http://www.csrc.gov.cn/‎" class="sitemap_link3">中国证券监督管理委员会</a>　<a target="_blank" href="http://www.szse.cn/‎" class="sitemap_link3">深圳证券交易所</a>　<a target="_blank" href="http://www.cninfo.com.cn/" class="sitemap_link3">巨潮资讯（中国证监会指定信息披露网站）</a></p>
			<p>版权所有 &copy; 1996-2014 Cyberway 赛百威信息科技　|　<a href="/lxwm" class="sitemap_link3">联系我们</a>　|　<a href="/flsm" class="sitemap_link3">法律声明</a>　|　<a href="/ysbh" class="sitemap_link3">隐私保护</a>　|　<a target="_blank" href="http://www.miibeian.gov.cn/" class="sitemap_link3">粤ICP备05059413</a></p>
		</div>
    </div>
</div>
</body>
</html>
