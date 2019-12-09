<%@ page import="com.kass.bus.KBus, com.kass.util.*, com.kass.commons.utils.*, com.kass.publics.*" contentType="text/html; charset=utf-8"%>
<%
//上传的相关参数:
String Tx_ParentPath = JspTools.getPara(request, "Tx_ParentPath")==null ? "" : JspTools.getPara(request, "Tx_ParentPath");
String Tx_FilterFileTypes = JspTools.getPara(request, "Tx_FilterFileTypes")==null ? "" : JspTools.getPara(request, "Tx_FilterFileTypes");
String B_IsOneModel = JspTools.getPara(request, "B_IsOneModel")==null ? "" : JspTools.getPara(request, "B_IsOneModel");

String s_IsPublicFile = JspTools.getPara(request,"B_IsPublicFile")==null?"":JspTools.getPara(request,"B_IsPublicFile");
String s_IsSystemFile = JspTools.getPara(request,"B_IsSystemFile")==null?"":JspTools.getPara(request,"B_IsSystemFile");
String s_IsPersonalFile = JspTools.getPara(request,"B_IsPersonalFile")==null?"":JspTools.getPara(request,"B_IsPersonalFile");
String Tx_Callback = JspTools.getPara(request,"Tx_Callback")==null?"":JspTools.getPara(request,"Tx_Callback");
String Tx_ErrorCallback = JspTools.getPara(request,"Tx_ErrorCallback")==null?"":JspTools.getPara(request,"Tx_ErrorCallback");
boolean B_IsPublicFile = false;
boolean B_IsPersonalFile = false;
boolean B_IsSystemFile = false;
if("true".equalsIgnoreCase(s_IsSystemFile)){
	B_IsSystemFile = true;
}else if("true".equalsIgnoreCase(s_IsPersonalFile)){
	B_IsPersonalFile = true;
}else{
	B_IsPublicFile = true;
}

//上传文件的当前文件获取状态类型
boolean canCreateVersion = B_IsPublicFile && (Boolean)KBus.invoke("VersionManageModel" , "canCreateVersion" , new Object[]{Tx_ParentPath});	
boolean canWriteVersion = B_IsPublicFile &&  (Boolean)KBus.invoke("VersionManageModel" , "canWriteVersion" , new Object[]{Tx_ParentPath});
%>
<!DOCTYPE html>
<html>
<head>
	<script type="text/javascript" src="/kass/common_js/Global.js"></script>
	<script type="text/javascript" src="/kass/common_js/swfobject.js?v=<%=GobalConstants.Version_Release_Time%>"></script>
	<script src='/kass/common_js/Timer.js'></script>
</head>
<body style="display:none;">

<!-- 有待最终递交保存的各参数 -->
<input class='input' type='hidden' id='Tx_CategoryProps' name='Tx_CategoryProps' value=""><!-- 分类及其分类扩展属性信息列表(cate#@#x1=v1#@#x2=v2##@@##cate2#@#x1=v2) -->
<input class='input' type='hidden' id='Tx_FileProps' name='Tx_FileProps' value=""><!-- 文件扩展属性信息列表。格式为 属性1#@#值1##@@##属性2#@#值2 -->
<input class="input" type='hidden' id='Tx_ParentPath' name='Tx_ParentPath' value=""><!-- 上级文件夹路径 -->
<input class="input" type='hidden' id='Tx_TotalFileCount' name='Tx_TotalFileCount' value=""><!-- 有待上传的文件数量 -->
<input class="input" type='hidden' id='Tx_CurrentFileIndex' name='Tx_CurrentFileIndex' value=""><!-- 当前正在处理的文件--编号(从0开始) -->
<input class="input" type='hidden' id='Tx_CurrentFilePath' name='Tx_CurrentFilePath' value=""><!-- 当前正在处理的文件--文件名称 -->
<input class="input" type='hidden' id='Tx_CurrentTokenId' name='Tx_CurrentTokenId' value=""><!-- 当前正在处理的文件--文件传输令牌ID -->

<!-- 进度条信息DIV -->
<div id='ProgressDiv' style='display:none;  width:100%; height:100%;'></div>

<!-- 页面布局 -->
<table id='MainTable' width=100% border=0 cellspacing=0 cellpadding=0 >
	
	<!-- 上传文件的内容TD -->
	<tr >
		<td colspan=2 width=100% height=0 id='TD_Content'>
			<div style="position:relative;width:100%;height:100%;">
				<iframe id='ContentIframe' frameborder='no' scrolling='no' src="" style='width:100%; height:100%;display:none;'></iframe>
			</div>
		</td>
	</tr>
	<!-- 文件相关属性的TD -->
	<%if(KBus.hasModel("CategoryModel") && !JspTools.IsKassGuanJia(request)){%>
	<tr >
		<td colspan=2 width=100%>
			<div id='TD_Prop' style='width:100%;height:0px;position:relative;'>
				<iframe id='PropIframe' frameborder='no' scrolling='no' src="" style='width:100%;height:100%;display:none;'></iframe>
			</div>
		</td>
	</tr>
	<%}%>
	<!-- 保存按钮TD -->
	<tr ><td colspan=2 width=100% valign=top>
		<div class="Div_Button_Container">
			<button class="btn btn_action" id='Btn_save' class='action' onclick="doSave();"><%=KBus.getLang("v.upload")%></button>
			<button class="btn btn_default" onclick="doClosePage();"><%=KBus.getLang("v.cancel")%></button>
		</div>
		<br>
	</td></tr>
</table>
</body>
</html>
<script>
var Tx_ParentPath = "<%=Tx_ParentPath%>";
var B_IsSystemFile = <%=B_IsSystemFile %>;


/** 初始化，加载内容页面 iframe ***/
$(document).ready(function(){
	if( !canMultiUpload( ) ) {
		var url = new KUrl("/kass/common_upload/one/page_main.jsp");
		url.put("Tx_ParentPath",Tx_ParentPath);
		url.put("B_IsPublicFile",<%=B_IsPublicFile%>);	
		url.put("B_IsPersonalFile",<%=B_IsPersonalFile%>);	
		url.put("B_IsSystemFile",<%=B_IsSystemFile%>);
		url.put("Tx_Callback","<%=Tx_Callback%>");	
		url.put("Tx_ErrorCallback","<%=Tx_ErrorCallback%>");
		window.location.replace( url.toString() );
	}else{
		KWin.Dialog_SetTitle(window,"<%=KBus.getLang("v.uploadfile")%>");
		var url = new KUrl("/kass/common_upload/multi/iframe_upload_form.jsp");
		url.put("Tx_ParentPath",Tx_ParentPath);	
		url.put("B_IsPublicFile",<%=B_IsPublicFile%>);	
		url.put("B_IsPersonalFile",<%=B_IsPersonalFile%>);	
		url.put("B_IsSystemFile",<%=B_IsSystemFile%>);
		url.put("Tx_FilterFileTypes","<%=Tx_FilterFileTypes%>");
		url.put("B_IsOneModel","<%=B_IsOneModel%>");		
		url.put("Tx_Callback","<%=Tx_Callback%>");
		url.put("Tx_ErrorCallback","<%=Tx_ErrorCallback%>");		
		$("#ContentIframe").attr("src", url.toString());
		$("body").show( ).find("#ContentIframe").show();
		
	}
});
/*
* 是否能够使用多文件上传
*/
function canMultiUpload( ){
	// IE 10 以下, Flash 检查
	var OB_BrowerInfo = checkBrowser( );
	if( OB_BrowerInfo && OB_BrowerInfo.ie && parseInt( OB_BrowerInfo.ie ) < 10 ){
		var OB_FlashInfo = checkFlash( );
		return !( !OB_FlashInfo || !OB_FlashInfo.installed || OB_FlashInfo.version <= 11 );
	}
	return true;
};

/** (该函数供其他页面调用)子页面-上传文件表单页面加载完毕后，调用该函数来调整该页面的高度 **/
function JS_AdjustContentHeight(contentHeight){
	$('#TD_Content').css("height", contentHeight);
	KWin.Dialog_SetSize(window, 580, $('#MainTable').height()+40);
}
</script>

<script>
/**********************************  分类及扩展属性有关的函数  ****************************/
/** 初始化，加载 分类及扩展属性页面 iframe ***/
$(document).ready(function(){
	<%if(B_IsPublicFile && KBus.hasModel("CategoryModel") && !JspTools.IsKassGuanJia(request)){%>
		doReloadPropIframe(Tx_ParentPath);
	<%}%>
});
/*** 更改有待上传到的目标上级文件夹 ***/
function doSelectParentFold(){
	KWin.OpenSelectOneFold(window, 'doAfterSelectParentFold()');
}
function doAfterSelectParentFold(){	
	var fold = KWin.GetResultOfSelectOne();
	Tx_ParentPath = fold;
	doReloadPropIframe(fold);
}
/** 重新加载PROP区域内容 ***/
var Tx_OldParentPath = "";
function doReloadPropIframe(newparentpath){
	<%if(!KBus.hasModel("CategoryModel") || !B_IsPublicFile  || JspTools.IsKassGuanJia(request)){%>
		return;
	<%}%>
	if(Tx_OldParentPath==newparentpath)
		return;
	Tx_OldParentPath = newparentpath;
	
	var url = new KUrl("/kass/units/category/common/page_set_categories_props_oncreatefile.jsp");
	url.put("Tx_ParentPath", newparentpath );
	url.put("B_IsSetOnFile", "true");
	url.put("Tx_ColumnWidth", "25%");
	url.put("Tx_AdjustHeightJs", "JS_AdjustPropHeight()");	
	$("#PropIframe").attr("src",url.toString());	
}
/** (该函数供其他页面调用)选择了文件分类后，调用该函数来调整该页面的高度 **/
function JS_AdjustPropHeight(){
	$("#PropIframe").show();
	var iframeWin = document.getElementById("PropIframe").contentWindow;	
	var iframeHeight = iframeWin.getTotalHeight();
	$('#TD_Prop').css("height", iframeHeight);
	if(iframeHeight>0){
		$('#TD_Prop').css("height", iframeHeight);
		KWin.Dialog_SetSize(window, KWin.Dialog_GetWidth(window), $('#MainTable').height()+40);
	}else{
		$("#PropIframe").hide();
		KWin.Dialog_SetSize(window, KWin.Dialog_GetWidth(window), $('#MainTable').height()+40);
	}
}
</script>

<script>
var IframeWin_Content = null; 
function doSave(){
	if( KWin.isKassYunguanjia( ) && !B_IsSystemFile ){
		try{ 
			IframeWin_Content = document.getElementById("ContentIframe").contentWindow;
			IframeWin_Content.doUploadAsClient( Tx_ParentPath );
		}catch(e){}
	}else{
		Flag_ApplyToAll = false;
		var parentpath = Tx_ParentPath;		
		try{ 
			IframeWin_Content = document.getElementById("ContentIframe").contentWindow; 
		}catch(e){}
		var list = IframeWin_Content.JS_GetExistFile(parentpath);	
		if(list.length>0 ){		
			doConfirmOf(list);
		}else{
			doSave2();
		}	
	}
}

var Flag_ApplyToAll = false;
function fn_select_applytoall(){
	Flag_ApplyToAll = true;
}
var clickIndex = -1;
function doConfirmOf(list){
	if(list.length<=0){
		doSave2();
		return;
	}
	var relativePath = list.shift();
	var msg = "<div><%=KBus.getLang("v.existfile")%>: <%=Tx_ParentPath.length()>1?Tx_ParentPath:""%>"+relativePath+"</div>";
	var BtnArray_Labels = [
		"<%=KBus.getLang("v.cancel")%>",
		"<%=KBus.getLang("v.skip")%>",
		"<%=KBus.getLang("v.overwritefile")%>",
		"<%=KBus.getLang("v.createnewversion")%>"
	];
	var f1 = function(){
		$("#Btn_save").attr("disabled",false);
		clickIndex = 0;
	}
	var f2 = function(){
		clickIndex = 1;
		IframeWin_Content.JS_SetExistFileSkip(relativePath);
		doConfirmOf(list);		
	};	
	var f3 = function(){
		clickIndex = 2;
		IframeWin_Content.JS_SetExistFileOverwrite(relativePath);
		doConfirmOf(list);
	};	
	var f4 = function(){
		clickIndex = 3;
		IframeWin_Content.JS_SetExistFileCreateVersion(relativePath);
		doConfirmOf(list);
	};	
	var BtnArray_Fns = [f1,f2,f3,f4];
	if(Flag_ApplyToAll){
		if(clickIndex==1){
			f2();
		}else if(clickIndex==2){
			f3();
		}else if(clickIndex==3){
			f4();
		}
	}else{				
		KWin.SelectOperation( msg, BtnArray_Labels, BtnArray_Fns, fn_select_applytoall);
	}
} 
/*** 保存操作 ****/
function doSave2(){	
	$("#Btn_save").attr("disabled",false);
	/*** 0.准备工作 ****/
	var IframeWin_Content = null;
	try{ IframeWin_Content = document.getElementById("ContentIframe").contentWindow; }catch(e){}
	var Tx_CategoryProps = "";
	var Tx_FileProps = "";
	<%if(B_IsPublicFile){%>
		var IframeWin_Prop = null;
		try{ IframeWin_Prop = document.getElementById("PropIframe").contentWindow; }catch(e){}
		/*** 1.1获取表单数据--扩展属性相关 ****/
		var propobj = document.getElementById("PropIframe");
		if( IframeWin_Prop ){	
			if( !IframeWin_Prop.checkData() ){
				return false;
			}
			$('#Tx_CategoryProps').val( IframeWin_Prop.getCategoryProperty() );
			$('#Tx_FileProps').val( IframeWin_Prop.getFileProperty() );		
		}
		Tx_CategoryProps = $('#Tx_CategoryProps').val();
		Tx_FileProps = $('#Tx_FileProps').val();
	<%}%>
	
	/*** 1获取表单数据 ****/
	//var parentpath = $('#Input_ParentPath').val();
	//$('#Tx_ParentPath').val( parentpath );
	var parentpath = Tx_ParentPath;
	var filecount = IframeWin_Content.JS_GetFileCount();
	if(filecount<=0){
		KWin.Tips_Fail("<%=KBus.getLang("m.000035")%>");	/*** m.000035=请选择本机上的某个文件 **/
		return;
	}
	$("#Btn_save").attr("disabled",true);
	$("#Btn_save").addClass( "disabled" );
	//IframeWin_Content.DoUploadLaunch();
	IframeWin_Content.DoUploadLaunch(parentpath, Tx_CategoryProps, Tx_FileProps);
	return;
}

/** 复制成功 ***/
function doUploadOver(B_IsRefreshParent){
	var info = "<%=KBus.getLang("v.operationok")%>";
	var url = new KUrl("/kass/common_dialog/dialog.jsp");
	url.put("B_IsRefreshParent",B_IsRefreshParent);
	url.put("Tx_Info",info);
	window.location = url.toString();
}
var haveFileUploadOk = false; /**** 子页面有上传文件成功的标记为TRUE，用于取消后刷新 ***/
function doClosePage(){
	if(!haveFileUploadOk){
		KWin.Dialog_Close(window);
	}else{
		var parentWindow = KWin.Dialog_GetParent(window);
		KWin.Dialog_Close(window);
		parentWindow.location.reload();
	}
}

</script>
