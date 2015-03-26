<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${webuser == null}">
	<script type="text/javascript">
window.location.href = "/webuser!login.action?fromUrl=" + window.location;
</script>
</c:if>
<script type="text/javascript" src="/scripts/ajaxfileupload.js"></script>
<div class="ps_leftSide">
	<script type="text/javascript">
function ajaxPicUpload()
	{
		jQuery("#loading")
		.ajaxStart(function(){
			jQuery(this).show();
		})
		.ajaxComplete(function(){
			jQuery(this).hide();
		});
		
		var filePath = jQuery('#ufile').val().toLowerCase();
		var fileType = filePath.substring(filePath.lastIndexOf('.'));
		if('.jpg.jpeg.gif.png'.indexOf(fileType) == -1){
			alert('只能上传jpg,jpeg,gif,png格式图片!');
			return false;
		}
		
		jQuery.ajaxFileUpload
		(
			{
				url:'/webuser!uploadPic.action?domain.oid=${webuser != null ? webuser.oid : ''}&_='+new Date().toString(),
				secureuri:false,
				fileElementId:'ufile',
				dataType:'json',
				success: function (ret)
				{
					jQuery('#userPic').attr('src',ret.uploadDir);
					jQuery('#domain_headerPic').val(ret.uploadDir);
				}
			}
		)
		return false;
	}
	
	jQuery(function(){
		jQuery(".ps_leftSide").css('height',jQuery(".ps_con").height());
	});
</script>
	<p class="ps_title">
		个人中心
	</p>
	<form id="picForm" action="/webuser!uploadPic.action" method="post"
		enctype="multipart/form-data">
		<div class="ps_Info">
			<p class="ps_name">
				${webuser != null ? webuser.name : '请先登录'}
			</p>
			<p class="ps_avatar">
				<img id="userPic"
					src="${webuser.headerPic != null ? webuser.headerPic : '/images/none_pic.jpg'}"
					alt="" class="avatarPic" width="126" height="108" />
			</p>
			<p style="margin-left: 50px" class="ps_pic_btn">
				<c:if test="${webuser != null}">
					<a class="ps_csPic"
						style="position: relative; display: block; width: 64px; height: 25px;">
						<span style="line-height: 25px; margin-left: 8px;">${webuser.headerPic != null ? '更换图片' : '上传图片'}</span> <input id="ufile" type="file"
							onchange="return ajaxPicUpload();" name="ufile"
							style="width: 64px; opacity: 0; filter: alpha(opacity = 0); position: absolute; left: 0;" />
						<img id="loading" style="display: none; margin-left: 20px"
							src="/images/loading.gif" /> </a>
				</c:if>
			</p>
		</div>
	</form>
</div>