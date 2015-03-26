var FileManager = function(containerId, channelId, docId, fieldName, fileType, _config){
	this.size = 0;
	this.container = document.getElementById(containerId);
	if(_config){
		this.config = _config;
		if(!this.config.multi){
			this.config.maxSize = 1;
		}
	} else {
		this.config = {};
	}
	this.files = [];
	this.urlParam = "?file.channelId="+channelId+"&file.docId="+docId+"&file.fieldName="+fieldName+"&file.fileType="+fileType;
	this.listFileUrl = "/cms/uploadFile!list.action" + this.urlParam;
	this.uploadFileUrl = "/cms/uploadFile!upload.action" + this.urlParam;
	this.deleteFileUrl = "/cms/uploadFile!delete.action" + this.urlParam;
	this.listFiles();
};

FileManager.prototype.listFiles = function(readonly){
	var self = this;
	self.loadFileList(function(fileList){
		self.clear();
		for(var i=0; i<fileList.length; i++){
			var file = fileList[i];
			if(self.config.showImage){
				file.domId = self.generateId();
				var span = document.createElement("span");
				span.id = file.domId;
				var img = document.createElement("img");
				img.src = file.url;
				if(self.config.imageHeight){
					img.height = self.config.imageHeight;
				}
				if(self.config.imageWidth){
					img.width = self.config.imageWidth;
				}
				span.appendChild(img);
				if(!readonly){
					span.appendChild(document.createElement("br"));
					var btn = document.createElement("input");
					btn.type = "button";
					btn.value = "删除";
					span.appendChild(btn);
				}
				self.container.appendChild(span);
			}
		}
	});
};

FileManager.prototype.addInput = function(){
	if(this.config.multi){
		
	}
};

FileManager.prototype.loadFileList = function(onSuccess, onFail){
	var req = getXMLHttpRequest();
	req.open("POST", this.listFileUrl, true);
	req.onreadystatechange = function(){
		if(req.readyState == 4){
			if(req.status == 200){
				var fileList = [];
				eval("fileList = " + req.responseText);
				onSuccess(fileList);
			} else if(onFail){
				onFail("上传出错");
			}
		}
	};
	req.send();
};

FileManager.prototype.clear = function(){
	this.container.innerHTML = "";
	this.size = 0;
};

FileManager.prototype.idStr = 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx';

FileManager.prototype.idMatcher = /[xy]/g;
FileManager.prototype.idMatcher.compile(FileManager.prototype.idMatcher);

FileManager.prototype.generateId = function(){
	return this.idStr.replace(this.idMatcher, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	});
};

function getXMLHttpRequest(){
	if(window.XMLHttpRequest){
		return new XMLHttpRequest();
	} else {
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
};