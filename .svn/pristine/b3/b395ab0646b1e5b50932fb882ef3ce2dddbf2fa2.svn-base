//将LEFT SELECT标签 值 单个或多个 转移到 RIGHT SELECT标签到中
  function move_beeline(left,right){ 
    var addflag; 
	if(left && right){
		for(var i = left.options.length-1 ; i >=0 ; i--){//循环左框所有值对象 
			var addflag = true;
			if(left.options[i].selected){//判断值是否被选中 
			  //添加未存在在右边列表的信息
			  if(addflag){       
				 right.options[right.options.length] = new Option(left.options[i].text,left.options[i].value); 
				 left.options[i].removeNode(true);
			  }
			}
		 }
	  }
    }
   
   //将LEFT SELECT标签 值 全部 转移到 RIGHT SELECT标签到中
   function move_all(left,right) {
     if(left && right){
	 	for (var i=0; i<left.options.length; i++)
	 	right.options.add(new Option(left.options[i].text, left.options[i].value));
	 	left.options.length = 0;
	 }
   }
   
   //将SELECT 标签 OPTIONS 上移
   function move_up(selTag){  
	for (var i=0; i<selTag.options.length; i++){
	  if(selTag.options[i].selected && i>0){
	     var op = new Option(selTag.options[i].text,selTag.options[i].value)
		 selTag.options[i] = new Option(selTag.options[i-1].text,selTag.options[i-1].value);
		 selTag.options[i-1] = op;
		 selTag.options[i-1].selected = true;
		  return false;
	   }
	 }
    }
	
	 //将SELECT 标签 OPTIONS 下移
	function move_down(selTag){  
	for (var i=0; i<selTag.options.length; i++){
	  if(selTag.options[i].selected && i <selTag.options.length-1){
	     var op = new Option(selTag.options[i].text,selTag.options[i].value)
		 selTag.options[i] = new Option(selTag.options[i+1].text,selTag.options[i+1].value);
		 selTag.options[i+1] = op;
		 selTag.options[i+1].selected = true;
		 return false;
	   }
	 }
    } 