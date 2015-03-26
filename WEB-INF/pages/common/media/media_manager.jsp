<%@ page contentType="text/html; charset=UTF-8"%>
<?xml version='1.0' encoding='UTF-8'?>

<tabbar  hrefmode="iframe">
    <row>
       <tab id="b0" width='80px' selected="1" href="/epb/unit!editView.action?id=${id}">企业详细</tab>
	   <tab id="b1" width='80px'  href="/epb/checkup!edit.action?unitId=${id}&amp;keys=1">企业年审</tab>
       <tab id="b2" width='80px' href="/epb/jsxmform!view.action?id=${param.jsxmId}">三同时报建</tab>
       <!--tab id="b2" width='80px'   href="${ctx }/epb/report!lstRegister.action?unitId=${id}">行政处罚</tab-->
	   <tab id="b3" width='80px'   href="${ctx }/epb/report!list.action?flowstate=8&amp;myTable_f_unitBianMa=${queryUnitCode}&amp;myTable_f_a=fa&amp;wordType=1">行政处罚</tab>
       <tab id="b4" width='80px'   href="${ctx}/epb/xqzg.action?flowstate=8&amp;myTable_f_unitId=${id}&amp;myTable_f_a=fa&amp;id=1">限期整改</tab>
       <tab id="b5" width='80px' href="${ctx }/epb/pollutioncharge!listByUnit.action?unitId=${id}&amp;id=1">排污收费情况</tab>       
       <tab id="b6" width='80px' href="${ctx}/epb/xfzf.action?flowstate=8&amp;myTable_f_unitid=${id}&amp;myTable_f_a=fa">信访执法</tab>
       <tab id="b7" width='80px' href="${ctx}/epb/xcjc!list.action?myTable_f_unitId=${id}&amp;myTable_f_a=fa&amp;templateCode=1">现场调查</tab>
       <tab id="b8" width='80px' href="${ctx}/epb/lipincallbak.action?myTable_f_unitCode=${queryUnitCode}&amp;myTable_f_a=fa&amp;id=1">废油脂</tab>
       <tab id="b9" width='80px' href="${ctx}/epb/nucleus.action?unitId=${id}">核辐射</tab>
       <tab id="b10" width='80px' href="${ctx}/epb/volatility.action?unitId=${id}">挥发性有机物</tab>
       
    </row>
</tabbar>
