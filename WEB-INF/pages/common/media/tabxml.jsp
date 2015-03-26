<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>
<%@ include file="/common/taglibs.inc"%>
<tabbar  hrefmode="iframe">
    <row>
       <tab id="b1" width='100px' selected="1" href="#">图片浏览</tab>
       <tab id="b2" width='100px' href="#">图片上传</tab>
       <tab id="b4" width='100px' href="${ctx}/base/album.action">视频浏览</tab>       
       <tab id="b3" width='100px' href="#">视频上传</tab>
    </row>
</tabbar>
