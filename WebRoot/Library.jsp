<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.TreeMap"%>
<html>

<head>
<meta charset="utf-8">
<link rel="stylesheet" href="css/mystyle.css" type="text/css" />
<link href="css/xxd2.css" rel="stylesheet" type="text/css" />
<link href="css/cebianlan.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/popwindow.css" media="screen"
	type="text/css" />
<script type="text/javascript" src="js/popwindow.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>

<script type="text/javascript">

	//点击列表
	function itemOnclick(id, nameinput, categoryinput) {
		document.getElementById('menuName').innerHTML = "<input type='text' class=\"input\" value='"+nameinput+"' id=\"nameinput\"/>";
		document.getElementById('libraryId').value = id;

		initIAM(id);
		initsp(id);
		initIAT(id);

	}
	
	//获得下拉框内容
	function test() {
		var obj = document.getElementById("selectType"); //定位id
		var index = obj.selectedIndex; // 选中索引
		var text = obj.options[index].text; // 选中文本
		window.location.href = "libraryAction!selectOfCategory?category="
				+ text;
	}
	
	
	function savesp() {
		var spdesc = document.getElementById("spdesc").value;
		var spsize = document.getElementById("spsize").value;
		var id = document.getElementById("libraryId").value;
		var f = document.getElementById("splist");
		f.innerHTML = "";
		$
				.ajax({
					url : "sizeandpriceAction!addsp",
					data : "spsize=" + spsize + "&spdesc=" + spdesc + "&id="
							+ id,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.sps) {
							var tmp = result.sps[i];
							if (i == 0) {
								f.innerHTML = "<div class=\"list_box_item\"><input type='text'  class=\"size_cell_1\" value='"+tmp.sizeName+"'/><input  type='text' class=\"size_cell_2\" value='"+tmp.sizePrice+"'/><div class=\"size_cell_3\">"
										+ tmp.id
										+ "</div><div class=\"size_cell_4\"><img src=\"images/menu.png\" /></div>";
							} else {
								f.innerHTML = f.innerHTML
										+ "<div class=\"list_box_item\"><input type='text'  class=\"size_cell_1\" value='"+tmp.sizeName+"'/><input  type='text' class=\"size_cell_2\" value='"+tmp.sizePrice+"'/><div class=\"size_cell_3\">"
										+ tmp.id
										+ "</div><div class=\"size_cell_4\"><img src=\"images/menu.png\" /></div><div class=\"subtract\"><img src=\"images/subtract.png\" onclick='subtract("
										+ tmp.id + "," + tmp.itemId
										+ ")' /></div>";
							}
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"spcreate\"></div>";
					}
				});
	}
	function subtract(id, itemid) {
		var f = document.getElementById("splist");
		$
				.ajax({
					url : "sizeandpriceAction!subsp",
					data : "spid=" + id + "&itemid=" + itemid,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.sps) {
							var tmp = result.sps[i];
							if (i == 0) {
								f.innerHTML = "<div class=\"list_box_item\"><input type='text'  class=\"size_cell_1\" value='"+tmp.sizeName+"'/><input  type='text' class=\"size_cell_2\" value='"+tmp.sizePrice+"'/><div class=\"size_cell_3\">"
										+ tmp.id
										+ "</div><div class=\"size_cell_4\"><img src=\"images/menu.png\" /></div>";
							} else {
								f.innerHTML = f.innerHTML
										+ "<div class=\"list_box_item\"><input type='text'  class=\"size_cell_1\" value='"+tmp.sizeName+"'/><input  type='text' class=\"size_cell_2\" value='"+tmp.sizePrice+"'/><div class=\"size_cell_3\">"
										+ tmp.id
										+ "</div><div class=\"size_cell_4\"><img src=\"images/menu.png\" /></div><div class=\"subtract\"><img src=\"images/subtract.png\" onclick='subtract("
										+ tmp.id + ")' /></div>";
							}
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"spcreate\"></div>";
					}
				});
	}

	function initsp(itemid) {
		var f = document.getElementById("splist");
		f.innerHTML = "";
		$
				.ajax({
					url : "sizeandpriceAction!initsp",
					data : "id=" + itemid,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.sps) {
							var tmp = result.sps[i];
							if (i == 0) {
								f.innerHTML = "<div class=\"list_box_item\"><input type='text'  class=\"size_cell_1\" value='"+tmp.sizeName+"'/><input  type='text' class=\"size_cell_2\" value='"+tmp.sizePrice+"'/><div class=\"size_cell_3\">"
										+ tmp.id
										+ "</div><div class=\"size_cell_4\"><img src=\"images/menu.png\" /></div>";
							} else {
								f.innerHTML = f.innerHTML
										+ "<div class=\"list_box_item\"><input type='text'  class=\"size_cell_1\" value='"+tmp.sizeName+"'/><input  type='text' class=\"size_cell_2\" value='"+tmp.sizePrice+"'/><div class=\"size_cell_3\">"
										+ tmp.id
										+ "</div><div class=\"size_cell_4\"><img src=\"images/menu.png\" /></div><div class=\"subtract\"><img src=\"images/subtract.png\" onclick='subtract("
										+ tmp.id + "," + tmp.itemId
										+ ")' /></div>";
							}
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"spcreate\"></div>";
					}
				});
	}

	function createsp() {
		var f = document.getElementById("spcreate");
		f.innerHTML = "<div class=\"list_box_item\"><input type='text' id='spdesc' class=\"size_cell_1\" value='Description'/><input id='spsize' type='text' class=\"size_cell_2\" value='0.00'/><div class=\"size_cell_3\">SKU</div><div class=\"size_cell_4\"><img src=\"images/menu.png\" /></div><div class=\"subtract\"><img src=\"images/save.PNG\" onclick='savesp()' /></div>";
	}

	function modifiercreate(id, modifierName) {
		var f = document.getElementById("modifiercreate");
		f.innerHTML = "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
				+ "<div class=\"modifier_cell_1\" id='modiName'>"
				+ modifierName
				+ "</div>"
				+ "<div class=\"modifier_cell_2\" id='modiOption'>One-Option</div>"
				+ "<div class=\"modifier_cell_3\">"
				+ "<img src=\"images/menu.png\" />"
				+ "</div>"
				+ "<div class=\"subtract\">"
				+ "<img src=\"images/save.PNG\" onclick='saveModify(\""
				+ id
				+ "\")' />" + "</div></div>";
	}

	function saveModify(modiId) {
		var itemId = document.getElementById("libraryId").value;
		var f = document.getElementById("modifierslist");
		f.innerHTML = "";
		$
				.ajax({
					url : "itemAndModiAction!addItemandmodi",
					data : "modiId=" + modiId + "&itemId=" + itemId,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.iam) {
							var tmp = result.iam[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
									+ "<div class=\"modifier_cell_1\" id='modiName'>"
									+ tmp.modiName
									+ "</div>"
									+ "<div class=\"modifier_cell_2\" id='modiOption'>"
									+ tmp.choiceType
									+ "</div>"
									+ "<div class=\"modifier_cell_3\">"
									+ "<img src=\"images/menu.png\" />"
									+ "</div>"
									+ "<div class=\"subtract\">"
									+ "<img src=\"images/subtract.png\" onclick='subModify(\""
									+ tmp.id + "\")' />" + "</div></div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"modifiercreate\"></div>";
					}
				});
	}

	function initIAM(modiId) {
		var itemId = document.getElementById("libraryId").value;
		var f = document.getElementById("modifierslist");
		f.innerHTML = "";
		$
				.ajax({
					url : "itemAndModiAction!initiam",
					data : "modiId=" + modiId + "&itemId=" + itemId,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.iam) {
							var tmp = result.iam[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
									+ "<div class=\"modifier_cell_1\" id='modiName'>"
									+ tmp.modiName
									+ "</div>"
									+ "<div class=\"modifier_cell_2\" id='modiOption'>"
									+ tmp.choiceType
									+ "</div>"
									+ "<div class=\"modifier_cell_3\">"
									+ "<img src=\"images/menu.png\" />"
									+ "</div>"
									+ "<div class=\"subtract\">"
									+ "<img src=\"images/subtract.png\" onclick='subModify(\""
									+ tmp.id + "\")' />" + "</div></div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"modifiercreate\"></div>";
					}
				});
	}

	function subModify(id) {
		var itemId = document.getElementById("libraryId").value;
		var f = document.getElementById("modifierslist");
		f.innerHTML = "";
		$
				.ajax({
					url : "itemAndModiAction!subIAM",
					data : "id=" + id + "&itemId=" + itemId,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.iam) {
							var tmp = result.iam[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
									+ "<div class=\"modifier_cell_1\" id='modiName'>"
									+ tmp.modiName
									+ "</div>"
									+ "<div class=\"modifier_cell_2\" id='modiOption'>"
									+ tmp.choiceType
									+ "</div>"
									+ "<div class=\"modifier_cell_3\">"
									+ "<img src=\"images/menu.png\" />"
									+ "</div>"
									+ "<div class=\"subtract\">"
									+ "<img src=\"images/subtract.png\" onclick='subModify(\""
									+ tmp.id + "\")' />" + "</div></div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"modifiercreate\"></div>";
					}
				});
	}
	
	//新建税收
	function taxescreate(id, name, rate) {
		var f = document.getElementById("taxescreate");
		f.innerHTML = "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
				+ "<div class=\"modifier_cell_1\">"
				+ name
				+ "</div>"
				+ "<div class=\"modifier_cell_2\">"
				+ rate
				+ "</div>"
				+ "<div class=\"subtract\">"
				+ "<img src=\"images/save.PNG\" onclick='saveTaxes(\""
				+ id
				+ "\")' /></div></div>";
	}
	
	//保存税收
	function saveTaxes(taxesId) {
		var itemId = document.getElementById("libraryId").value;
		var f = document.getElementById("taxesList");
		f.innerHTML = "";
		$
				.ajax({
					url : "ItemAndTaxAction!addItemandtax",
					data : "taxId=" + taxesId + "&itemId=" + itemId,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.iat) {
							var tmp = result.iat[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
									+ "<div class=\"modifier_cell_1\">"
									+ tmp.taxeName
									+ "</div>"
									+ "<div class=\"modifier_cell_2\">"
									+ tmp.rate
									+ "</div>"
									+ "<div class=\"subtract\">"
									+ "<img src=\"images/subtract.png\" onclick='subTaxes(\""
									+ tmp.id + "\")' /></div></div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"taxescreate\"></div>";
					}
				});
	}
	function subTaxes(Id) {
		var itemId = document.getElementById("libraryId").value;
		var f = document.getElementById("taxesList");
		f.innerHTML = "";
		$
				.ajax({
					url : "ItemAndTaxAction!subIAT",
					data : "Id=" + Id + "&itemId=" + itemId,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.iat) {
							var tmp = result.iat[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
									+ "<div class=\"modifier_cell_1\">"
									+ tmp.taxeName
									+ "</div>"
									+ "<div class=\"modifier_cell_2\">"
									+ tmp.rate
									+ "</div>"
									+ "<div class=\"subtract\">"
									+ "<img src=\"images/subtract.png\" onclick='subTaxes(\""
									+ tmp.id + "\")' /></div></div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"taxescreate\"></div>";
					}
				});
	}

	function initIAT(itemId) {
		var f = document.getElementById("taxesList");
		f.innerHTML = "";
		$
				.ajax({
					url : "ItemAndTaxAction!initiat",
					data : "itemId=" + itemId,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.iat) {
							var tmp = result.iat[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_item\" style=\"border: 2px solid #CECECE;\">"
									+ "<div class=\"modifier_cell_1\">"
									+ tmp.taxeName
									+ "</div>"
									+ "<div class=\"modifier_cell_2\">"
									+ tmp.rate
									+ "</div>"
									+ "<div class=\"subtract\">"
									+ "<img src=\"images/subtract.png\" onclick='subTaxes(\""
									+ tmp.id + "\")' /></div></div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"taxescreate\"></div>";
					}
				});
	}
	
	//保存 
	function save() {
		var itemId = document.getElementById("libraryId").value;
		var menuName = document.getElementById("nameinput").value;
		var obj = document.getElementById("selectType1"); //定位id
		var index = obj.selectedIndex; // 选中索引
		var type = obj.options[index].text; // 选中文本
		window.location.href = "libraryAction!addLibrary?itemId=" + itemId
				+ "&menuName=" + menuName + "&type=" + type;
	}
	
	
	//弹出菜单选择框
	function pupopen() {
		document.getElementById("popbox").style.display = "block";
	}
	function pupclose(id, name) {
		modifiercreate(id, name);
		document.getElementById("popbox").style.display = "none";
	}


	//弹出菜单选择框
	function taxesopen() {
		document.getElementById("popbox1").style.display = "block";
	}
	function taxesclose(id, name, rate) {
		taxescreate(id, name, rate);
		document.getElementById("popbox1").style.display = "none";
	}
	
</script>
<style type="text/css">
.list_box {
	width: 100%;
}

.list_box_item .name {
	width: 500px;
	text-align: left;
	font-weight: normal;
}

.list_box_item .category {
	width: 12%;
	text-align: left;
	font-weight: normal;
}

.list_box_item .modifier {
	width: 12%;
	text-align: left;
	font-weight: normal;
}

.list_box_item .price {
	width: 10%;
	text-align: left;
	font-weight: normal;
}

#popbox {
	position: absolute;
	width: 400px;
	right: 5%;
	top: 30%;
	margin: 0 auto;
	margin: -50px 0 0 -50px;
	display: none;
	background: #666666;
	border: 1px solid #898989;
	z-index: 9999;
}

#popbox1 {
	position: absolute;
	width: 400px;
	right: 5%;
	top: 30%;
	margin: 0 auto;
	margin: -50px 0 0 -50px;
	display: none;
	background: #666666;
	border: 1px solid #898989;
	z-index: 9999;
}

.menu_body {
	width: 400px;
}

.menu_list {
	top: 25px;
}
</style>
</head>

<body>
	<input type='checkbox' id='sideToggle'>
	
	<!--右边侧滑栏开始-->
	<aside class="aside" style=" z-index:1;">
		<input type='hidden' value='0' id='libraryId' />
		<div class="content">
			<div class="r_head">
				<div class="r_head_left">
					<div id="target" onClick="testChooseColor(event);"
						style="background: #963E3E;">
						<p>PicName</p>
					</div>
				</div>
				<div class="r_head_right">
					<div class="tabel">
						<table>
							<tr>
								<td><label>NAME</label></td>
								<td>
									<div id='menuName'>
										<input type='text' class="input" value='New Library'
											id="nameinput" />
									</div></td>
							</tr>
						</table>
					</div>
					<div class="select">
						<div
							style="font-size: 20px;font-weight: bold; float:left;padding-right: 20px;margin-top: 20px;">CATEGORY</div>
						<label style="float:left;margin-top: 17px;"> <s:select
								emptyOption="false" list="#session.categoriesMap" name="typeId"
								id="selectType1" /> </label>
					</div>
				</div>
			</div>
			<div class="r_content">
				<div class="r_list">
					<label>SIZES & PRICES</label>
					<div class="add">
						<img src="images/add.png" onclick="createsp();" />
					</div>
					<div class="list">
						<div class="list_box">
							<label id='splist'> <!--里面的列表1-->
								<div class="list_box_item">
									<input type='text' class="size_cell_1" value='Regular' /> <input
										type='text' class="size_cell_2" value='$0.00' />
									<div class="size_cell_3">00001</div>
									<div class="size_cell_4">
										<img src="images/menu.png" />
									</div>
								</div>
								<div id="spcreate"></div> </label>
						</div>
					</div>
				</div>
				<div class="r_list">
					<label>MODIFIERS</label>
					<div class="add">
						<img src="images/add.png" onclick="pupopen();" />
					</div>
					<div class="list">
						<div class="list_box" id="style-default">
							<label id='modifierslist'> <!--里面的列表1-->
								<div id="modifiercreate"></div> </label>
						</div>
					</div>
				</div>
				<div class="r_list">
					<label>TAXES</label>
					<div class="add">
						<img src="images/add.png" onclick="taxesopen();" />
					</div>
					<div class="list">
						<div class="list_box" id="style-default">
							<label id="taxesList"> <!--里面的列表1-->
								<div id="taxescreate"></div> </label>
						</div>
					</div>
				</div>
				<div class="r_list">
					<label>INGREDIENTS</label>
					<div class="add">
						<img src="images/add.png" />
					</div>
					<div class="list">
						<div class="list_box" id="style-default">
							<label> <!--里面的列表1-->
								<div class="list_box_item" style="border: 2px solid #CECECE;">
									<div class="modifier_cell_1">Sprite</div>
									<div class="modifier_cell_2">1Btl.</div>
								</div> </label>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="r_footer">
			<div class="r_footer_content">
				<table>
					<tr>
						<td class="r_footer_img"><img src="images/delete.png" /></td>
						<td class="r_footer_btn">
							<button class="cancel">Cancel</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="save" onclick="save();">Save</button></td>
					</tr>
				</table>
			</div>
		</div>

	</aside>
	<!--右边侧滑栏结束-->


	<s:set name="allsublist" value="#request.productList" />
	
	<div id='wrap' style="width:1350px; z-index:2;">
		<div class="content">
		
			<!-- 头部开始 -->
			<div class="con_top">
				
				<!--选框开始-->
				<div class="select">
					<label> <s:select emptyOption="false"
							list="#session.categoriesMap" headerKey=""
							headerValue="--Menu Categories--" name="typeId" id="selectType"
							onchange="test();" /> </label>
				</div>
				<!--选框结束-->
				
				
				<!--搜索框开始-->
				<div class="search">
					<input type="text" />
				</div>
				<!--搜索框结束-->
				
			</div>
			<!-- 头部结束 -->
			
			<!-- 中部开始 -->
			<div class="con_middle">
			
				<!-- 列表开始 -->
				<div class="list" style="position: relative;top: 20px;">
					
					<!-- 列表头部开始 -->
					<div class="list_box" id="style-default" style="overflow:hidden; height:40px;">
					
						<!--标题内容开始-->
						<div class="list_box_item" style="background:#f1f1f1;">
							<div class="name" style="font-weight: bold;">Name</div>
							<div class="category" style="font-weight: bold;">Category</div>
							<div class="modifier" style="font-weight: bold;">Modifier</div>
							<div class="price" style="font-weight: bold;">Price</div>
						</div>
						<!-- 标题内容结束 -->
						
					</div>
					<!-- 列表头部结束 -->
					
					<!-- 列表内容开始 -->
					<div class="list_box" id="test">
						<div id="create"></div>
						<!--里面的列表1-->
						<s:iterator status="allStatus" value="allsublist">
							<label for='sideToggle'>
								<div class="list_box_item" onClick="itemOnclick('<s:property value="pdtId" />','<s:property value="pdtName" />','Soft Drink')">
									<div class="name">
										<s:property value="pdtName" />
									</div>
									<div class="category">
										<s:property value="typeId" />
									</div>
									<div class="modifier">
										<s:property value="pdtCode" />
									</div>
									<div class="price">
										<s:property value="pdtSalePrice1" />
									</div>
								</div> 
							</label>
						</s:iterator>
					</div>
					<!-- 列表内容结束 -->

				</div>
				<!-- 列表结束 -->
				
			</div>
			<!-- 中部结束 -->
			
		</div>

	</div>

	<!-- -------- -->
	<div id="popbox">
		<!---->
		<div class="menu_body">
			<div class="menu_content">
				<div class="menu_list">
					<s:set name="allsublist" value="#request.modifierList" />
					<s:iterator status="allStatus" value="allsublist">
						<div class="cell"
							onclick="pupclose('<s:property value="id" />','<s:property value="modiName" />');">
							<label><s:property value="modiName" /> </label>
						</div>
					</s:iterator>
				</div>

			</div>
		</div>
		<!---->
	</div>

	<div id="popbox1">
		<!---->
		<div class="menu_body">
			<div class="menu_content">
				<div class="menu_list">
					<s:set name="allsublist" value="#request.taxesList" />
					<s:iterator status="allStatus" value="allsublist">
						<div class="cell"
							onclick="taxesclose('<s:property value="id" />','<s:property value="taxeName" />','<s:property value="rate" />');">
							<label><s:property value="taxeName" /> </label>
						</div>
					</s:iterator>
				</div>
			</div>
		</div>
		<!---->
	</div>

</body>

</html>