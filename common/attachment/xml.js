function CreateXMLDOM()
{
	var objXMLDom
	try
	{
		objXMLDom=new ActiveXObject("MSXML2.DomDocument")
	}
	catch(e)
	{
		objXMLDom=new ActiveXObject("Microsoft.XMLDom")
	}
	return objXMLDom
	
}

function GetXMLValue(XMLString,FieldName)
{
	var objXMLDom=CreateXMLDOM()
	objXMLDom.async=false
	objXMLDom.loadXML(XMLString)
	var tempNodes=objXMLDom.getElementsByTagName(FieldName)
	if(tempNodes.length<=0)
		{return false}
	else
	{
		return tempNodes(0).childNodes(0).nodeValue
	}
}


function LoadData(Island,url)
{
	if (typeof(Island)=="undefined")
	{
		alert("参数错误!")
		return false
	}
	
	var tempobjdom = CreateXMLDOM()	//创建DOM对象
	var msg=""
	
	tempobjdom.async=false
	tempobjdom.preserveWhiteSpace=true
	tempobjdom.load(url)
	if (CheckError(tempobjdom))
	{
		msg=GetXMLValue(tempobjdom.xml,"MESSAGEDESC")
		if ((msg.length==0)||(msg==false))
		{

			Island.XMLDocument.async=false
			Island.XMLDocument.loadXML(tempobjdom.xml)
			return true

		}
		else
		{
			alert(msg)
		}
		return true
	}
	else
	{
		return false
	}

}


function WriteSelect(SelectID,IsMult,XMLIsland,strValue,strDisplay,initvalue,parent)
{
	
	j=document.all(SelectID).childNodes.length
	for(i=0;i<j;i++)
	{
		document.all(SelectID).removeChild(document.all(SelectID).childNodes[0])
	}
	if (! IsMult)
	{
		tempnode=document.createElement("<OPTION value=\"\"></OPTION>")
		tempnode.innerText=""
		document.all(SelectID).appendChild(tempnode)
	}


	if(XMLIsland.recordset!=null&&XMLIsland.recordset.recordcount!=0)
	{
		while(!XMLIsland.recordset.eof)
		{
			try{
				if (initvalue==XMLIsland.recordset(strValue))
					tempnode=document.createElement("<OPTION value=" + XMLIsland.recordset(strValue)+ " selected></OPTION>")
				else
					tempnode=document.createElement("<OPTION value=" + XMLIsland.recordset(strValue)+ "></OPTION>")

				if (parent=="0")
					tempnode.innerText=XMLIsland.recordset(strValue)+"-"+XMLIsland.recordset(strDisplay)
				else
					tempnode.innerText=XMLIsland.recordset(strDisplay)
					
				document.all(SelectID).appendChild(tempnode)

				XMLIsland.recordset.MoveNext()
			}catch(e)
			{
				return;
			}
		}
	}
}

function CheckError(objXMLDom)
{
	if(objXMLDom.documentElement==null){
		alert("连接出现错误！")								
		return false
	}
	return true;
}