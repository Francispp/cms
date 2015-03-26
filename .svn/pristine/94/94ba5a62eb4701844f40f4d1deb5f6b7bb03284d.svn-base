//显示选择窗口
function showframe(title,querystr) {
  var pth = window.location.pathname;
  var pos1 = pth.lastIndexOf("/");
  var pos2 = pth.lastIndexOf("\\");
  var pos = Math.max(pos1, pos2);

  querystr.title = title;
  if (querystr.url!=null){
    if (!(querystr.url.indexOf("/")==0 || querystr.url.indexOf("\\")==0))
      querystr.url = pth.substring(0,pos)+"/"+querystr.url;
  }
  else {
    if (!(querystr.indexOf("/")==0 || querystr.indexOf("\\")==0))
      querystr = pth.substring(0,pos)+"/"+querystr;
  }
  return window.showModalDialog('/frame.jsp?title=' + title,querystr,'font-size:9pt;dialogWidth:' + wx + ';dialogHeight:' + wy + ';status:no;scroll=no;');
}

function addAttachment(docid,node){
  wx='600px';
  wy='350px';
  var furl = '/epb/attachment!add.action?docId='+docid+'&node='+node;
  var rs = showframe('上传附件',furl);
	if(rs&&rs!='undefined'){
		var oNewNode = document.createElement("LI");
		attachementul.appendChild(oNewNode);
		oNewNode.innerHTML=rs;
	}
}   
//删除附件
function removeAttachment(obj, oid){
if(!confirm('您确定要删除吗？')){return;}
if(!oid||oid=='')return ;
var url = '/epb/attachment!delete.action?keys='+oid;
 new Ajax.Request(url, { method: 'post',
             parameters: "",// myform
             onSuccess: function(transport) {
                 obj.parentElement.removeChild(obj);
                 //$(obj.parentElement).remove(obj);
             },
             onFailure: function(transport){alert('错误');}
           });
}
//编辑附件
function editAttachment(obj, oid){
	if(!oid||oid=='')return ;
	wx='600px';
	wy='350px';
	var rs = showframe('编辑附件','/epb/attachment!edit.action?id='+oid);
	if(rs&&rs!='undefined'){
		var oNewNode = document.createElement("LI");
		//attachementul.appendChild(oNewNode);
		attachementul.insertBefore(oNewNode,obj);
		attachementul.removeChild(obj);
		oNewNode.innerHTML=rs;
	}
}
