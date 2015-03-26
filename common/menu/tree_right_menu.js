  function RightMenu(sMenuName)
  { 
	 if (sMenuName){this.MenuName=sMenuName;}
    this.AddExtendMenu=AddExtendMenu;
    this.AddItem=AddItem;
    this.GetMenu=GetMenu;
    this.HideAll=HideAll;
    this.I_OnMouseOver=I_OnMouseOver;
    this.I_OnMouseOut=I_OnMouseOut;
    this.I_OnMouseUp=I_OnMouseUp;
    this.P_OnMouseOver=P_OnMouseOver;
    this.P_OnMouseOut=P_OnMouseOut;
	
    eval("A_"+this.MenuName+" = new Array();")
    HTMLstr  = "";
    HTMLstr += "<!-- RightButton PopMenu -->\n";
    HTMLstr += "\n";
    HTMLstr += "<!-- PopMenu Starts -->\n";
    HTMLstr += "<div id='E_"+this.MenuName+"' class='rm_div'>\n";
                        // rbpm = right button pop menu
    HTMLstr += "<table width='100%' border='0' cellspacing='0'>\n";
    HTMLstr += "<tr><td  width='100%' style='padding: 0' valign='bottom'>\n";
    HTMLstr += "<table width='100%' border='0' cellspacing='0'>\n";
    HTMLstr += "<!-- Insert A Extend Menu or Item On Here For E_"+this.MenuName+" -->\n";
    HTMLstr += "</table></td></tr></table>\n";
    HTMLstr += "</div>\n";
    HTMLstr += "<!-- Insert A Extend_Menu Area on Here For E_"+this.MenuName+" -->";
    HTMLstr += "\n";
    HTMLstr += "<!-- PopMenu Ends -->\n";
  }
  function AddExtendMenu(id,img,wh,name,parent,sMenuName)
  {
    var TempStr = "";

    eval("A_"+parent+".length++");
    eval("A_"+parent+"[A_"+parent+".length-1] = id");  // ?????????????????????????????????ID????????????
    TempStr += "<div id='E_"+id+"' class='rm_div'>\n";
    TempStr += "<table width='100%' border='0' cellspacing='0'>\n";
    TempStr += "<!-- Insert A Extend Menu or Item On Here For E_"+id+" -->";
    TempStr += "</table>\n";
    TempStr += "</div>\n";
    TempStr += "<!-- Insert A Extend_Menu Area on Here For E_"+id+" -->";
    TempStr += "<!-- Insert A Extend_Menu Area on Here For E_"+parent+" -->";
    HTMLstr = HTMLstr.replace("<!-- Insert A Extend_Menu Area on Here For E_"+parent+" -->",TempStr);
    
    eval("A_"+id+" = new Array()");
    TempStr  = "";
    TempStr += "<!-- Extend Item : P_"+id+" -->\n";
    TempStr += "<tr id='P_"+id+"' class='out'";
    TempStr += " onmouseover='P_OnMouseOver(\""+id+"\",\""+parent+"\""+"\",\""+sMenuName+"\")'";
    TempStr += " onmouseout='P_OnMouseOut(\""+id+"\",\""+parent+"\")'";
    TempStr += " onmouseup=window.event.cancelBubble=true;";
    TempStr += " onclick=window.event.cancelBubble=true;";
    TempStr += "><td nowrap>";
    TempStr += "<font face='Wingdings' style='font-size:18px'>0</font> "+name+"  </td><td style='font-family: webdings; text-align: ;'>4";
    TempStr += "</td></tr>\n";
    TempStr += "<!-- Insert A Extend Menu or Item On Here For E_"+parent+" -->";
    HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_"+parent+" -->",TempStr);
   
  }
  function AddItem(id,img,wh,name,parent,location)
  {
    var TempStr = "";
    var ItemStr = "<!-- ITEM : I_"+id+" -->";
    if(id == "sperator")
    {
      TempStr += ItemStr+"\n";
      TempStr += "<tr class='out' onclick='window.event.cancelBubble=true;' onmouseup='window.event.cancelBubble=true;'><td colspan='2' height='1'></td></tr>";
      TempStr += "<!-- Insert A Extend Menu or Item On Here For E_"+parent+" -->";
      HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_"+parent+" -->",TempStr);
      return;
    }
    if(HTMLstr.indexOf(ItemStr) != -1)
    {
      alert("I_"+id+"already exist!");
      return;
    }
    if(img==''){
    	img="&nbsp;&nbsp;&nbsp;";
    }else{
    	img="<img src='"+img+"'>";
    }
    TempStr += ItemStr+"\n";
    TempStr += "<tr id='I_"+id+"' class='out'";
    TempStr += " onmouseover='I_OnMouseOver(\""+id+"\",\""+parent+"\",\""+parent+"\")'";
    TempStr += " onmouseout='I_OnMouseOut(\""+id+"\")'";
    TempStr += " onclick='window.event.cancelBubble=true;'";
    if(location == null)
      TempStr += " onmouseup='I_OnMouseUp(\""+id+"\",\""+parent+"\",null)'";
    else
      TempStr += " onmouseup='I_OnMouseUp(\""+id+"\",\""+parent+"\",\""+location+"\")'";
    TempStr += "><td  style='BACKGROUND-COLOR: #eeeeee;'>";
    TempStr += img;
    TempStr += "</td><td nowrap>";
    TempStr +="<font face='Wingdings' style='font-size:18px'>"+wh+"</font> "+ name+" ";
    TempStr += "&nbsp;</td><td></td></tr>\n";
    TempStr += "<!-- Insert A Extend Menu or Item On Here For E_"+parent+" -->";
    HTMLstr = HTMLstr.replace("<!-- Insert A Extend Menu or Item On Here For E_"+parent+" -->",TempStr);
  }
  function GetMenu()
  {
    return HTMLstr;
  }
  function I_OnMouseOver(id,parent,sMenuName)
  {
    var Item;
    if(parent != sMenuName)
    {
      var ParentItem;
      ParentItem = eval("P_"+parent);
      ParentItem.className="over";
    }
    Item = eval("I_"+id);
    Item.className="over";
    HideAll(parent,1);
  }
  function I_OnMouseOut(id)
  {
    var Item;
    Item = eval("I_"+id);
    Item.className="out";
  }
  function I_OnMouseUp(id,parent,location)
  {
    var ParentMenu;
    window.event.cancelBubble=true;
    OnClick();
    ParentMenu = eval("E_"+parent);
    ParentMenu.display="none";
    if(location == null)
      eval("Do_"+id+"()");
    else
	 eval(location);
      //window.open(location);
  }
  function P_OnMouseOver(id,parent,sMenuName)
  {
    var Item;
    var Extend;
    var Parent;
    if(parent != sMenuName)
    {
      var ParentItem;
      ParentItem = eval("P_"+parent);
      ParentItem.className="over";
    }
    HideAll(parent,1);
    Item = eval("P_"+id);
    Extend = eval("E_"+id);
    Parent = eval("E_"+parent);
    Item.className="over";
    Extend.style.display="block";
    Extend.style.posLeft=document.body.scrollLeft+Parent.offsetLeft+Parent.offsetWidth-4;
    if(Extend.style.posLeft+Extend.offsetWidth > document.body.scrollLeft+document.body.clientWidth)
        Extend.style.posLeft=Extend.style.posLeft-Parent.offsetWidth-Extend.offsetWidth+8;
    if(Extend.style.posLeft < 0) Extend.style.posLeft=document.body.scrollLeft+Parent.offsetLeft+Parent.offsetWidth;
    Extend.style.posTop=Parent.offsetTop+Item.offsetTop+1;
    if(Extend.style.posTop+Extend.offsetHeight > document.body.scrollTop+document.body.clientHeight)
      Extend.style.posTop=document.body.scrollTop+document.body.clientHeight-Extend.offsetHeight;
    if(Extend.style.posTop < 0) Extend.style.posTop=0;
  }
  function P_OnMouseOut(id,parent)
  {
  }
  function HideAll(id,flag)
  {
    var Area;
    var Temp;
    var i;
    if(!flag)
    {
      Temp = eval("E_"+id);
      Temp.style.display="none";
    }
    Area = eval("A_"+id);
    if(Area.length)
    {
      for(i=0; i < Area.length; i++)
      {
        HideAll(Area[i],0);
        Temp = eval("E_"+Area[i]);
        Temp.style.display="none";
        Temp = eval("P_"+Area[i]);
        Temp.className="out";
      }
    }
  }

  function OnMouseUp(sMenuName)
  { 
    if(window.event.button == 2)
    {
      var PopMenu;
      PopMenu = eval("E_"+sMenuName);	  
      //HideAll(sMenuName,0);
	  closeAllMenu();
	 
      PopMenu.style.display="block";
      PopMenu.style.posLeft=document.body.scrollLeft+window.event.clientX;
      PopMenu.style.posTop=document.body.scrollTop+window.event.clientY;
      if(PopMenu.style.posLeft+PopMenu.offsetWidth > document.body.scrollLeft+document.body.clientWidth)
        PopMenu.style.posLeft=document.body.scrollLeft+document.body.clientWidth-PopMenu.offsetWidth;
      if(PopMenu.style.posLeft < 0) PopMenu.style.posLeft=0;
      if(PopMenu.style.posTop+PopMenu.offsetHeight > document.body.scrollTop+document.body.clientHeight)
        PopMenu.style.posTop=document.body.scrollTop+document.body.clientHeight-PopMenu.offsetHeight;
      if(PopMenu.style.posTop < 0) PopMenu.style.posTop=0;
    }
  }
  function OnMouseUpExt(sMenuName)
  { 
   // if(window.event.button == 2)
   // {
      var PopMenu;
      PopMenu = eval("E_"+sMenuName);	  
      //HideAll(sMenuName,0);
	  closeAllMenu();
	 
      PopMenu.style.display="block";
      PopMenu.style.posLeft=document.body.scrollLeft+window.event.clientX;
      PopMenu.style.posTop=document.body.scrollTop+window.event.clientY;
      if(PopMenu.style.posLeft+PopMenu.offsetWidth > document.body.scrollLeft+document.body.clientWidth)
        PopMenu.style.posLeft=document.body.scrollLeft+document.body.clientWidth-PopMenu.offsetWidth;
      if(PopMenu.style.posLeft < 0) PopMenu.style.posLeft=0;
      if(PopMenu.style.posTop+PopMenu.offsetHeight > document.body.scrollTop+document.body.clientHeight)
        PopMenu.style.posTop=document.body.scrollTop+document.body.clientHeight-PopMenu.offsetHeight;
      if(PopMenu.style.posTop < 0) PopMenu.style.posTop=0;
    //}
  }  
  /*function OnClick()
  {
    HideAll("rbpm",0);
  }*/
