var global_ab = {};
global_ab.init = 
{
	
	
	indexPage_fn : function()
	{
		/*
		 *  @forPage  : index
		 *  @function : check the window resize, adjust the baseFrame of width and height
		 */
		var baseFrame_header = document.getElementById("baseFrame-header");
		var baseFrame_content = document.getElementById("baseFrame-content");
		var baseFrame_footer = document.getElementById("baseFrame-footer");
		var baseFrame_con_left = document.getElementById("BFC_left");
		var baseFrame_con_LI = document.getElementById("baseFrame-part1");
		var baseFrame_con_RI = document.getElementById("switchToTree");
		var baseFrame_con_LL = document.getElementById("frameLL");
		var baseFrame_con_RR = document.getElementById("frameRR");
		var baseFrame_con_LC = document.getElementById("LL_close");
		var baseFrame_con_RC = document.getElementById("RR_close");
		var baseFrame_con_LS = document.getElementById("LL_show");
		var baseFrame_con_RS = document.getElementById("RR_show");
		var baseFrame_minW = 1000;        //set the minWidth
		
		function checkBaseFrameSize()
		{		
			if(document.documentElement.scrollWidth<=baseFrame_minW)
			{
				baseFrame_header.style.width = baseFrame_minW + "px";
				baseFrame_content.style.width = baseFrame_minW + "px";
				baseFrame_footer.style.width = baseFrame_minW + "px";
			}
			else
			{
				baseFrame_header.style.width = "";
				baseFrame_content.style.width = "";
				baseFrame_footer.style.width = "";
			};
		
			baseFrame_content.style.height = (document.documentElement.clientHeight - 121)+"px";
			baseFrame_con_LI.style.height = (document.documentElement.clientHeight - 121 - 14)+"px";
			baseFrame_con_RI.style.height = (document.documentElement.clientHeight - 121 - 14)+"px";
		};
		
		function main_change()
		{
			if(!navigator.userAgent.match("MSIE 6.0"))
			{
				switch(true)
				{
					case baseFrame_con_LS.style.display=="none" && baseFrame_con_LL.style.display=="" &&
						 baseFrame_con_RS.style.display=="none" && baseFrame_con_RR.style.display=="" :
						document.getElementById("main_frame").parentNode.style.marginLeft = "271px";
						break;
					case baseFrame_con_LS.style.display=="none" && baseFrame_con_LL.style.display=="" &&
						 baseFrame_con_RS.style.display=="" && baseFrame_con_RR.style.display=="none" :
						document.getElementById("main_frame").parentNode.style.marginLeft = "133px";
						break;
					case baseFrame_con_LS.style.display=="" && baseFrame_con_LL.style.display=="none" &&
						 baseFrame_con_RS.style.display=="none" && baseFrame_con_RR.style.display=="" :
						document.getElementById("main_frame").parentNode.style.marginLeft = "170px";
						break;
					case baseFrame_con_LS.style.display=="" && baseFrame_con_LL.style.display=="none" &&
						 baseFrame_con_RS.style.display=="" && baseFrame_con_RR.style.display=="none" :
						document.getElementById("main_frame").parentNode.style.marginLeft = "32px";
						break;
					case baseFrame_con_LS.style.display=="none" && baseFrame_con_LL.style.display=="" &&
						 baseFrame_con_RS.style.display=="none" && baseFrame_con_RR.style.display=="none" :
						document.getElementById("main_frame").parentNode.style.marginLeft = "117px";
						break;
					case baseFrame_con_LS.style.display=="" && baseFrame_con_LL.style.display=="none" &&
						 baseFrame_con_RS.style.display=="none" && baseFrame_con_RR.style.display=="none" :
						document.getElementById("main_frame").parentNode.style.marginLeft = "16px";
						break;
				};
			};
		};
		
		baseFrame_con_LS.onclick = function()
		{
			baseFrame_con_LL.style.display = "";
			baseFrame_con_LS.style.display = "none";
			main_change();
		};
		
		baseFrame_con_LC.onclick = function()
		{
			baseFrame_con_LL.style.display = "none";
			baseFrame_con_LS.style.display = "";
			main_change();
		};
		
		baseFrame_con_RS.onclick = function()
		{
			baseFrame_con_RR.style.display = "";
			baseFrame_con_RS.style.display = "none";
			baseFrame_con_left.style.cssText = "";
			main_change();
		};
		
		baseFrame_con_RC.onclick = function()
		{
			baseFrame_con_RR.style.display = "none";
			baseFrame_con_RS.style.display = "";
			baseFrame_con_left.style.border = "none";
			main_change();
		};
				
		if(!!document.all)
		{
			window.attachEvent("onload",checkBaseFrameSize);
			window.attachEvent("onresize",checkBaseFrameSize);
		}
		else
		{
			window.addEventListener("load",checkBaseFrameSize,false);
			window.addEventListener("resize",checkBaseFrameSize,false);
		};
	},
	
	loginPage_fn : function()
	{
		/*
		 *  @forPage  : login
		 *  @function : for ie6, mouseover and mouseout of input
		 */
		if(navigator.userAgent.match("MSIE 6.0"))
		{
			var button = document.getElementById("loginSubmit");
			
			function btnMouseover()
			{
				document.getElementById("loginSubmit").style.cssText = "background:url(/images/btn_login%20.jpg) 0 -32px no-repeat;";
			};
			function btnMouseout()
			{
				document.getElementById("loginSubmit").style.cssText = "";
			};
			
			button.attachEvent("onmouseover",btnMouseover);
			button.attachEvent("onmouseout",btnMouseout);
		};
	},
	
	media_configurationPage_fn : function()
	{
		var btnArr = [],
			divs = document.getElementsByTagName("div"),
			divLen = divs.length;
		
		for(var i=0;i<divLen;i++)
		{
			if(divs.item(i).className.match("artEdit-btn-bx_ab"))
			{
				btnArr.push(divs.item(i));
			};
		};
		
		function rest()
		{
			for(var i=0;i<btnArr.length;i++)
			{
				btnArr[i].className = "artEdit-btn_ab artEdit-btn-bx_ab artEdit-btn-bx_hb";
				btnArr[i].getElementsByTagName("a").item(0).className = "artEdit-btn-in_ab artEdit-btn-bx-in_ab";
			};
		};
		
		for(var i=0;i<btnArr.length;i++)
		{
			btnArr[i].onclick = function()
			{
				rest();
				this.className = "artEdit-btn_ab artEdit-btn-bx_ab artEdit-btn-bx-cur_ab";
				this.getElementsByTagName("a").item(0).className = "artEdit-btn-in_ab artEdit-btn-bx-in_ab artEdit-btn-bx-in_cur_ab";
			};
		};
		rest();
	},
	
	media_imgPage_fn : function()
	{
		var checkBoxArr = [],
			checkBoxes = document.getElementsByTagName("input"),
			checkBoxLen = checkBoxes.length;
		
		for(var i=0;i<checkBoxLen;i++)
		{
			if(checkBoxes.item(i).type=="checkbox")
			{
				checkBoxArr.push(checkBoxes.item(i));
			};
		};
		
		checkBoxArr[0].onclick = function()
		{
			if(checkBoxArr[0].checked==true)
			{
				for(var i=1;i<checkBoxArr.length;i++)
				{
					checkBoxArr[i].checked = true;
				};
			}
			else
			{
				for(var i=1;i<checkBoxArr.length;i++)
				{
					checkBoxArr[i].checked = false;
				};
			};
		};
	},
	
	btnAct_forIe6 : function(objTag)
	{
		/*
		 *  @forPage  : *
		 *  @function : for ie6, mouseover and mouseout of elements
		 */
		if(navigator.userAgent.match("MSIE 6.0"))
		{
			var elements = document.getElementsByTagName(objTag),
				elementLen = elements.length;
			
			for(var i=0;i<elementLen;i++)
			{
				if(elements.item(i).className.match("artEdit-btn_ab") || elements.item(i).className.match("artEdit-btn-ex_ab"))
				{
					elements.item(i).onmouseover = function()
					{
						if(this.className.match("artEdit-btn_ab") && this.className.match("artEdit-btn-ex_ab"))
						{
							this.style.background = "url(/images/bg_btn.gif) 0 -154px no-repeat";
						}
						else if(this.className.match("artEdit-btn-bx_ab"))
						{
							this.style.background = "url(/images/bg_btn.gif) 0 -262px no-repeat";
						}
						else if(this.className.match("artEdit-btn_ab"))
						{
							this.style.background = "url(/images/bg_btn.gif) 0 -54px no-repeat";
						};
					};
					elements.item(i).onmouseout = function()
					{
						if(this.className.match("artEdit-btn_ab"))
						{
							this.style.cssText = "";
						};
					};
				};
			};
		};
	},
	
	popwindowPage_fn : function(handlerSetPopWinIndexOrName)
	{
		/*
		 *  @forPage  : *(in "popwindow" file)
		 *  @function : for ie6 , mouseover and mouseout of input ( sumbit btn and cancel btn )
		 */
		var topDoc = window.top.document,
			iframes = topDoc.getElementsByTagName("iframe"),
			iframeLen = iframes.length,
			//bg = (handlerSetPopWinIndexOrName==undefined) ? topDoc.getElementById("newPopWindow_bg_ab") : topDoc.getElementById("newPopWindow_bg_ab"+handlerSetPopWinIndexOrName) ,
			box = (handlerSetPopWinIndexOrName==undefined) ? topDoc.getElementById("newPopWindow_box_ab") : topDoc.getElementById("newPopWindow_box_ab"+handlerSetPopWinIndexOrName) ,
			//win = (handlerSetPopWinIndexOrName==undefined) ? topDoc.getElementById("newPopWindow_win_ab") : topDoc.getElementById("newPopWindow_win_ab"+handlerSetPopWinIndexOrName) ,
			form = document.getElementsByTagName("form").item(0);		
		
		if(navigator.userAgent.match("MSIE 6.0"))
		{
			var inputs = document.getElementsByTagName("input"),
				inputLen = inputs.length;
	
			for(var i=0;i<inputLen;i++)
			{
				if(inputs.item(i).className.match("pwSubmit_ab"))
				{
					inputs.item(i).onmouseover = function()
					{
						this.className = "pwSubmit_ab pwSubmit-hover_ab";
					};
					inputs.item(i).onmouseout = function()
					{
						this.className = "pwSubmit_ab";
					};
				}
				else if(inputs.item(i).className.match("pwCancel_ab"))
				{
					inputs.item(i).onmouseover = function()
					{
						this.className = "pwCancel_ab pwCancel-hover_ab";
					};
					inputs.item(i).onmouseout = function()
					{
						this.className = "pwCancel_ab";
					};
				};
			};
		};
		
		function checkOnloadAndChangeSize()
		{
			box.style.width = form.offsetWidth+"px";
			box.style.height = form.offsetHeight+"px";
			box.style.marginLeft = (-parseInt(form.offsetWidth/2))+"px";
			box.style.marginTop = (-parseInt(form.offsetHeight/2))+"px";
			box.style.visibility = "visible";
		};
		
		if(!!document.all)
		{
			window.attachEvent("onload",checkBaseFrameSize);
			window.attachEvent("onresize",checkBaseFrameSize);
		}
		else
		{
			window.addEventListener("load",checkBaseFrameSize,false);
			window.addEventListener("resize",checkBaseFrameSize,false);
		};
	},
	
	swichPage_fn : function()
	{
		/*
		 *  @forPage  : swich
		 *  @function : make element of in parentPage, calling "switchToTree", and adjust element of "mainField"
		 */
		document.getElementById("leftbar").onclick = function()
		{
			window.parent.document.getElementById("switchToTree").src = "tree.html";
			window.parent.document.getElementById("switchToTree").width = "153px";
			if(!navigator.userAgent.match("MSIE 6.0"))
			{
				window.parent.document.getElementById("main_frame").parentNode.style.marginLeft = "271px";
			};
		};
	},
	treePage_fn : function()
	{
		/*
		 *  @forPage  : tree
		 *  @function : make element of in parentPage, calling "switchToTree", and adjust element of "mainField"
		 */
		document.getElementById("toCloseSwitch").onclick = function()
		{
			window.parent.document.getElementById("switchToTree").src = "swich.html";
			window.parent.document.getElementById("switchToTree").width = "14px";
			if(!navigator.userAgent.match("MSIE 6.0"))
			{
				window.parent.document.getElementById("main_frame").parentNode.style.marginLeft = "132px";
			}
		};
	},
	
	loginPage_fn : function()
	{
		/*
		 *  @forPage  : login
		 *  @function : for ie6, mouseover and mouseout of input
		 */
		if(navigator.userAgent.match("MSIE 6.0"))
		{
			var button = document.getElementById("loginSubmit");
			
			function btnMouseover()
			{
				document.getElementById("loginSubmit").style.cssText = "background:url(/images/btn_login%20.jpg) 0 -32px no-repeat;";
			};
			function btnMouseout()
			{
				document.getElementById("loginSubmit").style.cssText = "";
			};
			
			button.attachEvent("onmouseover",btnMouseover);
			button.attachEvent("onmouseout",btnMouseout);
		};
	},
	
	btnAct_forIe6 : function(objTag)
	{
		/*
		 *  @forPage  : *
		 *  @function : for ie6, mouseover and mouseout of elements
		 */
		if(navigator.userAgent.match("MSIE 6.0"))
		{
			var elements = document.getElementsByTagName(objTag);
			var elementLen = elements.length;
			
			for(var i=0;i<elementLen;i++)
			{
				elements.item(i).onmouseover = function()
				{
					if(this.className.match("artEdit-btn_ab") && this.className.match("artEdit-btn-ex_ab"))
					{
						this.style.background = "url(/images/bg_btn.gif) 0 -154px no-repeat";
					}
					else if(this.className.match("artEdit-btn_ab"))
					{
						this.style.background = "url(/images/bg_btn.gif) 0 -54px no-repeat";
					}
				};
				elements.item(i).onmouseout = function()
				{
					if(this.className.match("artEdit-btn_ab"))
					{
						this.style.cssText = "";
					}
				};
			};
		};
	},
	
	popwindowPage_fn : function()
	{
		if(navigator.userAgent.match("MSIE 6.0"))
		{
			var inputs = document.getElementsByTagName("input");
			var inputLen = inputs.length;
	
			for(var i=0;i<inputLen;i++)
			{
				if(inputs.item(i).className.match("pwSubmit_ab"))
				{
					inputs.item(i).onmouseover = function()
					{
						this.className = "pwSubmit_ab pwSubmit-hover_ab";
					};
					inputs.item(i).onmouseout = function()
					{
						this.className = "pwSubmit_ab";
					};
				}
				else if(inputs.item(i).className.match("pwCancel_ab"))
				{
					inputs.item(i).onmouseover = function()
					{
						this.className = "pwCancel_ab pwCancel-hover_ab";
					};
					inputs.item(i).onmouseout = function()
					{
						this.className = "pwCancel_ab";
					};
				};
			};
		};
	},
	
	selecticoPage_fn : function()
	{
		var uls = document.getElementsByTagName("ul");
		var ulLen = uls.length;
		var icoUl, icoLis, icoLen, icoCurrent;
		
		for(var i=0;i<ulLen;i++)
		{
			if(uls.item(i).className.match("pw-con-icos_ab"))
			{
				icoUl = uls.item(i);
				break;
			};
		};
		
		icoLis = icoUl.getElementsByTagName("li");
		icoLen = icoLis.length;
		
		function icoLisRest()
		{
			for(var x=0;x<icoLen;x++)
			{
				if(icoLis.item(x).className.match("pw-con-icos-item_ab"))
				{
					icoLis.item(x).className = "pw-con-icos-item_ab";
				};
			};
		};
		
		for(var y=0;y<icoLen;y++)
		{
			icoLis.item(y).onclick = function()
			{
				icoLisRest();
				this.className = "pw-con-icos-item_ab pw-con-icos-item-cur_ab";
				icoCurrent = this;
			};
			if(navigator.userAgent.match("MSIE 6.0") && icoLis.item(y).className!="fn-clear")
			{
				icoLis.item(y).onmouseover = function()
				{
					if(this!=icoCurrent)
					{
						this.className = "pw-con-icos-item_ab pw-con-icos-item-cur_ab";
					};
				};
				icoLis.item(y).onmouseout = function()
				{
					if(this!=icoCurrent)
					{
						this.className = "pw-con-icos-item_ab";
					};
				};
			};
		};
	},
	
	table_fn : function()
	{
		function index(self,obj)
		{
			for(var i=0;i<obj.length;i++)
			{
				if(obj[i]==self)
				{
					return i;
				};
			};
		};
		
		var myTab = document.getElementsByTagName("table").item(0);
		var checkboxs = myTab.getElementsByTagName("input");
		var checkboxsLen = checkboxs.length;
		var trs = myTab.getElementsByTagName("tr");
		var trLen = trs.length;
		
		for(var x=0;x<checkboxsLen;x++)
		{
			checkboxs.item(x).onclick = function()
			{
				var i = index(this,checkboxs);
				
				if(this.checked)
				{
					trs.item(i).className = "myTab-hasChecked_ab"
					trs.item(i).style.backgroundColor = "";
				}
				else
				{
					trs.item(i).className = ""
				};
			};
			trs.item(x).onmouseover = function()
			{
				if(!this.className.match("myTab-hasChecked_ab"))
				{
					this.style.backgroundColor = "#f4f4f4";
				};
			};
			trs.item(x).onmouseout = function()
			{
				if(!this.className.match("myTab-hasChecked_ab"))
				{
					this.style.backgroundColor = "";
				};
			};
		};
		
		checkboxs.item(0).onclick = function()
		{
			if(this.checked)
			{
				for(var x=1;x<checkboxsLen;x++)
				{
					checkboxs.item(x).checked = true;
					trs.item(x).className = "myTab-hasChecked_ab";
				};
			}
			else
			{
				for(var x=1;x<checkboxsLen;x++)
				{
					checkboxs.item(x).checked = false;
					trs.item(x).className = "";
				};
			};
		};
	}
};

global_ab.fn = 
{
	changeBaseFrameContent : function(leftPartURL,midPartURL,rightPartURL)
	{
				/*
				 *  @forPage  : *
				 *  @function : change each part URL and resize the baseFrame
				 */
				 
					var baseFrame_con_left =  window.top.document.getElementById("BFC_left");
					var baseFrame_con_right =  window.top.document.getElementById("main_frame").parentNode;
					var baseFrame_con_LI =  window.top.document.getElementById("baseFrame-part1");
					var baseFrame_con_RI =  window.top.document.getElementById("switchToTree");
					var baseFrame_con_MI =  window.top.document.getElementById("main_frame");
					var baseFrame_con_LL =  window.top.document.getElementById("frameLL");
					var baseFrame_con_RR =  window.top.document.getElementById("frameRR");
					var baseFrame_con_LC =  window.top.document.getElementById("LL_close");
					var baseFrame_con_RC =  window.top.document.getElementById("RR_close");
					var baseFrame_con_LS =  window.top.document.getElementById("LL_show");
					var baseFrame_con_RS =  window.top.document.getElementById("RR_show");
				
				if(leftPartURL != "")
				{
					baseFrame_con_LI.src = leftPartURL;
				};
				
				if(midPartURL == "")
				{
					baseFrame_con_left.style.borderRight = "none";
					baseFrame_con_LL.style.display = "";
					baseFrame_con_LS.style.display = "none";
					baseFrame_con_RR.style.display = "none";
					baseFrame_con_RS.style.display = "none";
					if(!navigator.userAgent.match("MSIE 6.0"))
					{
						baseFrame_con_right.style.marginLeft = "117px";
					};
					baseFrame_con_RI.src = midPartURL;
				}
				else if(midPartURL != baseFrame_con_RI.src)
				{
					baseFrame_con_left.style.borderRight = "1px solid #a1a1a1";
					baseFrame_con_LL.style.display = "";
					baseFrame_con_LS.style.display = "none";
					baseFrame_con_RR.style.display = "";
					baseFrame_con_RS.style.display = "none";
					if(!navigator.userAgent.match("MSIE 6.0"))
					{
						baseFrame_con_right.style.marginLeft = "271px";
					};
					baseFrame_con_RI.src = midPartURL;
				};
				
				baseFrame_con_MI.src = rightPartURL;
			},
				
			popWindow :
			{
				/*
				 *  @forPage  : *
				 *  @function : for popwindow
				 */
				bg : "",
				box : "",
				boxWidth : "400px",
				boxHeight : "400px",
				boxURL : "popwindow_popW.html",
				win : "",
				hasMoving : false,
				moveInId : "",
				moveOutId : "",
				createPopWindow : function()
				{
					var myWin = global_ab.fn.popWindow;
					
					myWin.bg = window.top.document.createElement("div");
					myWin.bg.id = "newPopWindow_bg_ab";
					myWin.bg.style.width = "100%";
					myWin.bg.style.height = "100%";
					myWin.bg.style.backgroundColor = "#000";
					myWin.bg.style.position = "absolute";
					myWin.bg.style.left = "0";
					myWin.bg.style.top = "0";
					if(!!document.all)
					{
						myWin.bg.style.filter = "alpha(opacity=50)";
					}
					else
					{
						myWin.bg.style.opacity = "0.5";
					}
					
					myWin.box = window.top.document.createElement("div");
					myWin.box.id = "newPopWindow_box_ab";
					myWin.box.style.width = myWin.boxWidth;
					myWin.box.style.height = myWin.boxHeight;
					myWin.box.style.position = "absolute";
					myWin.box.style.left = "0%";
					myWin.box.style.top = "50%";
					myWin.box.style.marginLeft = (-parseInt(parseInt(myWin.boxWidth)/2))+"px";
					myWin.box.style.marginTop = (-parseInt(parseInt(myWin.boxHeight)/2))+"px";
					
					myWin.win = window.top.document.createElement("iframe");
					myWin.win.id = "newPopWindow_win_ab";
					myWin.win.setAttribute("width","100%");
					myWin.win.setAttribute("height","100%");
					myWin.win.setAttribute("frameborder","0");
					myWin.win.setAttribute("allowtransparency","true");
					myWin.win.setAttribute("scrolling","auto");
					myWin.win.setAttribute("position","absolute");
					myWin.win.setAttribute("z-index","-1");
					myWin.win.setAttribute("src",myWin.boxURL);	
				},
				addPopWindow : function(url,width,height,hasMove)
				{
					try{
						parent.FunSelectDisabled(true);
					}catch(e){} 

					var myWin = global_ab.fn.popWindow;
					var winBody = window.top.document.body;
					
					myWin.boxWidth = width;
					myWin.boxHeight = height;
					myWin.boxURL = (!!url)?url:myWin.boxURL;
					myWin.createPopWindow();
					
					winBody.appendChild(myWin.bg);
					winBody.appendChild(myWin.box);
					myWin.box.appendChild(myWin.win);
					
					myWin.hasMoving = hasMove;
					myWin.moveIn();
				},
				moveIn : function()
				{
					var myWin = global_ab.fn.popWindow;
					if(myWin.hasMoving==true && myWin.box.style.left!="50%")
					{
						myWin.box.style.left = (parseInt(myWin.box.style.left)+2)+"%";
					}
					else if(myWin.hasMoving==false)
					{
						myWin.box.style.left = "50%";
					}
					if(myWin.box.style.left!="50%")
					{
						myWin.moveInId = setTimeout(myWin.moveIn,15);
					}
					else
					{
						clearTimeout(myWin.moveInId);
					}
				},
				moveOut : function()
				{
					var myWin = global_ab.fn.popWindow;
					var winBody = window.top.document.body;
					var winBox = window.top.document.getElementById("newPopWindow_box_ab");
					var winBg = window.top.document.getElementById("newPopWindow_bg_ab");
					
					if(myWin.hasMoving==true && winBox.style.left!="100%")
					{
						winBox.style.left = (parseInt(winBox.style.left)+2)+"%";
						myWin.moveOutId = setTimeout(myWin.moveOut,15);
					}
					else if(myWin.hasMoving==false || winBox.style.left=="100%")
					{
						clearTimeout(myWin.moveOutId);
						winBody.removeChild(winBg);
						winBody.removeChild(winBox);
					};
				},
				removePopWindow : function(hasMove)
				{
					try{
						parent.FunSelectDisabled(false);
					}catch(e){} 

					var myWin = global_ab.fn.popWindow;
					myWin.hasMoving = hasMove;
					myWin.moveOut();
				}
			},

	table_fn : function(tabIndexOrTableId)
	{
		/*
		 *  @forPage  : *
		 *  @function : table with checkbox 
		 */
		var myTab;
		switch(typeof tabIndexOrTableId)
		{
			case "number" :
				myTab = document.getElementsByTagName("table").item(tabIndexOrTableId);
				break;
			case "string" :
				myTab = document.getElementById(tabIndexOrTableId);
				break;
			case "undefined" :
				myTab = document.getElementsByTagName("table").item(0);
				break;
		};
		
		var checkboxs = myTab.getElementsByTagName("input");
		var checkboxsLen = checkboxs.length;
		var trs = myTab.getElementsByTagName("tr");
		var trLen = trs.length;

		function index(self,obj)
		{
			for(var i=0;i<obj.length;i++)
			{
				if(obj[i]==self)
				{
					return i;
				};
			};
		};

		for(var x=0;x<checkboxsLen;x++)
		{
			checkboxs.item(x).onclick = function()
			{
				var i = index(this,checkboxs);
				
				if(this.checked)
				{
					trs.item(i).className = "myTab-hasChecked_ab";
					trs.item(i).style.backgroundColor = "";
				}
				else
				{
					trs.item(i).className = "";
				};
			};
			trs.item(x).onmouseover = function()
			{
				if(!this.className.match("myTab-hasChecked_ab"))
				{
					this.style.backgroundColor = "#f4f4f4";
				};
			};
			trs.item(x).onmouseout = function()
			{
				if(!this.className.match("myTab-hasChecked_ab"))
				{
					this.style.backgroundColor = "";
				};
			};
		};
		
		checkboxs.item(0).onclick = function()
		{
			if(this.checked)
			{
				for(var x=1;x<checkboxsLen;x++)
				{
					checkboxs.item(x).checked = true;
					trs.item(x).className = "myTab-hasChecked_ab";
				};
			}
			else
			{
				for(var x=1;x<checkboxsLen;x++)
				{
					checkboxs.item(x).checked = false;
					trs.item(x).className = "";
				};
			};
		};
	},
	
	tableWidthList_fn : function(tabIndexOrTableId)
	{
		/*
		 *  @forPage  : *
		 *  @function : table with up(-) and down(+) lists
		 */
		var tab;
		switch(typeof tabIndexOrTableId)
		{
			case "number" :
				tab = document.getElementsByTagName("table").item(tabIndexOrTableId);
				break;
			case "string" :
				tab = document.getElementById(tabIndexOrTableId);
				break;
			case "undefined" :
				tab = document.getElementsByTagName("table").item(0);
				break;
		};
		
		var trs = tab.getElementsByTagName("tr");
		var trLen = trs.length;
		var imgs = tab.getElementsByTagName("img");
		var imgLen = imgs.length;
		var infoArr = [];
		
		for(var i=0;i<trLen;i++)
		{
			var imgCur = trs.item(i).getElementsByTagName("img").item(0);
			var checkBoxCur = trs.item(i).getElementsByTagName("input").item(0);
			
			if(imgCur.className.match("list-btn_ab"))
			{
				infoArr.push(trs.item(i),imgCur,i,checkBoxCur);
			};
		};
		infoArr.push({},{},trLen);
		
		function allTrClose()
		{
			for(var i=1;i<trLen;i++)
			{
				var imgCur = trs.item(i).getElementsByTagName("img").item(0);
				if(!imgCur.className.match("list-btn_ab"))
				{
					trs.item(i).style.display = "none";
				};
			};
		};
		
		function listClose(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).style.display = "none";
			};
		};
		
		function listShow(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).style.display = "";
			};
		};
		
		function AllChecked(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).getElementsByTagName("input").item(0).checked = true;
				trs.item(y).className = "myTab-hasChecked_ab";
			};
		};
		
		function AllunChecked(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).getElementsByTagName("input").item(0).checked = false;
				trs.item(y).className = "";
			};
		};
		
		allTrClose();
		
		for(var x=0;x<infoArr.length-4;x+=4)
		{
			infoArr[x+1].onclick = function()
			{
				if(this.className.match("ico-175_ab"))
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+1])
						{
							listClose(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.className = "ico_ab ico-176_ab list-btn_ab";
					this.src = "/images/cybercms/ico_176_in.gif";
				}
				else if(this.className.match("ico-176_ab"))
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+1])
						{
							listShow(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.className = "ico_ab ico-175_ab list-btn_ab";
					this.src = "/images/cybercms/ico_175_out.gif";
				};
			};
			infoArr[x+3].onclick = function()
			{
				if(!!this.checked)
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+3])
						{
							AllChecked(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.parentNode.parentNode.className = "myTab-hasChecked_ab";
					this.parentNode.parentNode.style.cssText = "background-color:#e7fedd";
				}
				else
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+3])
						{
							AllunChecked(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.parentNode.parentNode.className = "";
					this.parentNode.parentNode.style.cssText = "";
				};
			};
		};
	},
	
	tableWidthList_fn1 : function(tabIndexOrTableId)
	{
		/*
		 *  @forPage  : *
		 *  @function : table with up(-) and down(+) lists
		 */
		var tab;
		switch(typeof tabIndexOrTableId)
		{
			case "number" :
				tab = document.getElementsByTagName("table").item(tabIndexOrTableId);
				break;
			case "string" :
				tab = document.getElementById(tabIndexOrTableId);
				break;
			case "undefined" :
				tab = document.getElementsByTagName("table").item(0);
				break;
		};
		
		var trs = tab.getElementsByTagName("tr");
		var trLen = trs.length;
		var imgs = tab.getElementsByTagName("img");
		var imgLen = imgs.length;
		var infoArr = [];
		
		for(var i=0;i<trLen;i++)
		{
			var imgCur = trs.item(i).getElementsByTagName("img").item(0);
			var checkBoxCur = trs.item(i).getElementsByTagName("input").item(0);
			
			if(imgCur.className.match("list-btn_ab"))
			{
				infoArr.push(trs.item(i),imgCur,i,checkBoxCur);
			};
		};
		infoArr.push({},{},trLen);
		
		function allTrClose()
		{
			for(var i=1;i<trLen;i++)
			{
				var imgCur = trs.item(i).getElementsByTagName("img").item(0);
				if(!imgCur.className.match("list-btn_ab"))
				{
					trs.item(i).style.display = "none";
				};
			};
		};
		
		function listClose(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).style.display = "none";
			};
		};
		
		function listShow(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).style.display = "";
			};
		};
		
		function AllChecked(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).getElementsByTagName("input").item(0).checked = true;
				trs.item(y).className = "myTab-hasChecked_ab";
			};
		};
		
		function AllunChecked(startIndex,endIndex)
		{
			for(var y=startIndex+1;y<endIndex;y++)
			{
				trs.item(y).getElementsByTagName("input").item(0).checked = false;
				trs.item(y).className = "";
			};
		};
		
		allTrClose();
		
		for(var x=0;x<infoArr.length-4;x+=4)
		{
			infoArr[x+1].onclick = function()
			{
				if(this.className.match("ico-175_ab"))
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+1])
						{
							listClose(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.className = "ico_ab ico-176_ab list-btn_ab";
					this.src = "/images/cybercms/ico_176_in.gif";
				}
				else if(this.className.match("ico-176_ab"))
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+1])
						{
							listShow(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.className = "ico_ab ico-175_ab list-btn_ab";
					this.src = "/images/cybercms/ico_175_out.gif";
				};
			};
			/*infoArr[x+3].onclick = function()
			{
				if(!!this.checked)
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+3])
						{
							AllChecked(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.parentNode.parentNode.className = "myTab-hasChecked_ab";
					this.parentNode.parentNode.style.cssText = "background-color:#e7fedd";
				}
				else
				{
					for(var i=0;i<infoArr.length-4;i+=4)
					{
						if(this==infoArr[i+3])
						{
							AllunChecked(infoArr[i+2],infoArr[i+6]);
						};
					};
					this.parentNode.parentNode.className = "";
					this.parentNode.parentNode.style.cssText = "";
				};
			};*/
		};
	}
}