<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<html>
<head>
<meta charset="utf-8">
<title>Modifies</title>

<link href="css/aside.css" rel="stylesheet" type="text/css" />
<link href="css/cebianlan.css" rel="stylesheet" type="text/css" />
<link href="css/mystyle.css" rel="stylesheet" type="text/css" />
<link href="css/xxd2_xxd2.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="css/buttonstyle.css" media="screen"
	type="text/css" />

<link rel="stylesheet" href="css/popwindow.css" media="screen"
	type="text/css" />
<script type="text/javascript" src="js/popwindow.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<style type="text/css">
.list_box_item .discounts {
	width: 80%;
	height: 45px;
	line-height: 45px;
	float: right;
	text-align: center;
	font-size: 16px;
	color: #6A687C;
}

.list_box_item .names {
	width: 20%;
	height: 45px;
	line-height: 45px;
	float: left;
	margin: 0 auto;
	font-size: 16px;
	color: #6A687C;
}

.list_box_item .leftsides {
	width: 100%;
	height: 45px;
	line-height: 45px;
	float: left;
	margin-left: 33px;
	font-size: 16px;
	color: #6A687C;
}

#bg {
				width: 100%;
				height: 100%;
				top: 0px;
				left: 0px;
				position: absolute;
				opacity: 0.5;
				background: #000000;
				display: none;
			}
			#popbox {
				position: absolute;
				width: 545px;
				left: 40%;
				top: 30%;
				margin: 0 auto;
				margin: -200px 0 0 -200px;
				display: none;
				background: #666666;
			}
</style>
<script type="text/javascript">
	function itemOnclick(id, nameinput) {
		document.getElementById('id').value = id;
		document.getElementById('nameinput').value = nameinput;

		initOptions(id);
	}

	function initOptions(modiid) {
		var f = document.getElementById("optList");
		f.innerHTML = "";
		$
				.ajax({
					url : "ModiAndOptionsAction!initOptions",
					data : "modiid=" + modiid,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.opt) {
							var tmp = result.opt[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_items\">"
									+ "<div class=\"names\">"
									+ tmp.optionName
									+ "</div>"
									+ "<div class=\"discountes\">"
									+ "<input class=\"delimage\" type=\"image\" src=\"images/menu.png\" />"
									+ "</div>"
									+ "<div class=\"pad\">&nbsp;</div>"
									+ "<div class=\"discountes\">$ "
									+ tmp.optionPrice
									+ "</div>"
									+ "</div>"
									+ "<div class=\"subimage\">"
									+ "<input class=\"subtract\" type=\"image\" src=\"images/subtract.png\" onclick=\"subOption('"
									+ tmp.id + "')\" />" + "</div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"optCreate\"></div>";
					},
					error : function(result) {
						f.innerHTML = f.innerHTML
								+ "<div  id=\"optCreate\"></div>";
					}
				});
	}

	function save() {
		var id = document.getElementById('id').value;
		var nameinput = document.getElementById('nameinput').value;

		window.location.href = "modifierAction!addModifier?id=" + id
				+ "&nameinput=" + nameinput + "";
	}
	function test() {
		var id = document.getElementById('id').value;
		window.location.href = "modifierAction!modifierDelete?id=" + id;
		closeWindow();
	}
	function optCreate() {
		var f = document.getElementById('optCreate');
		f.innerHTML = "<div class=\"list_box_items\">"
				+ "<input type=\"text\" id='optionName' class=\"newoption\" value=\"New Option\" style=\"border:0;\"/>"
				+ "<div class=\"pad\">&nbsp;</div>"
				+ "<div class=\"discountes\">"
				+ "<input class=\"delimage\" type=\"image\" src=\"images/menu.png\" />"
				+ "</div>"
				+ "<input type=\"text\" id='price' class=\"newmoney\" value=\"0.00\" style=\"border:0;\"/>"
				+ "</div>"
				+ "<div class=\"subimage\">"
				+ "<input class=\"subtract\" type=\"image\" src=\"images/save.PNG\" onclick=\"saveOptions();\" />"
				+ "</div>";
	}

	function saveOptions() {
		var modiid = document.getElementById('id').value;
		var optionName = document.getElementById('optionName').value;
		var price = document.getElementById('price').value;

		var f = document.getElementById("optList");
		f.innerHTML = "";
		$
				.ajax({
					url : "ModiAndOptionsAction!saveOptions",
					data : "modiid=" + modiid + "&optionName=" + optionName
							+ "&price=" + price,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.opt) {
							var tmp = result.opt[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_items\">"
									+ "<div class=\"names\">"
									+ tmp.optionName
									+ "</div>"
									+ "<div class=\"discountes\">"
									+ "<input class=\"delimage\" type=\"image\" src=\"images/menu.png\" />"
									+ "</div>"
									+ "<div class=\"pad\">&nbsp;</div>"
									+ "<div class=\"discountes\">$ "
									+ tmp.optionPrice
									+ "</div>"
									+ "</div>"
									+ "<div class=\"subimage\">"
									+ "<input class=\"subtract\" type=\"image\" src=\"images/subtract.png\" onclick=\"subOption('"
									+ tmp.id + "')\" />" + "</div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"optCreate\"></div>";
					},
					error : function(result) {
						alert(result);
					}
				});
	}

	function subOption(optid) {
		var f = document.getElementById("optList");
		var modiid = document.getElementById('id').value;
		f.innerHTML = "";
		$
				.ajax({
					url : "ModiAndOptionsAction!subOption",
					data : "optid=" + optid + "&modiid=" + modiid,
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success : function(result) {
						for (i in result.opt) {
							var tmp = result.opt[i];
							f.innerHTML = f.innerHTML
									+ "<div class=\"list_box_items\">"
									+ "<div class=\"names\">"
									+ tmp.optionName
									+ "</div>"
									+ "<div class=\"discountes\">"
									+ "<input class=\"delimage\" type=\"image\" src=\"images/menu.png\" />"
									+ "</div>"
									+ "<div class=\"pad\">&nbsp;</div>"
									+ "<div class=\"discountes\">$ "
									+ tmp.optionPrice
									+ "</div>"
									+ "</div>"
									+ "<div class=\"subimage\">"
									+ "<input class=\"subtract\" type=\"image\" src=\"images/subtract.png\" onclick=\"subOption('"
									+ tmp.id + "')\" />" + "</div>";
						}
						f.innerHTML = f.innerHTML
								+ "<div  id=\"optCreate\"></div>";
					}
				});
	}
			function pupopen(modiid) {
				$("input[name='checkbox']").removeAttr("checked"); 
				document.getElementById('modiid').value=modiid;
				$.ajax({
					url:"itemAndModiAction!list",
					data:"modiid="+modiid,
					type:"get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success:function(result){
						for (i in result.iat) {
							var tmp = result.iat[i];
							document.getElementById(tmp.itemId).checked=true;
							document.getElementById(tmp.itemId).parentNode.parentNode.style.backgroundColor="#EDF9FF";
					
						}
						}
				});
				document.getElementById("bg").style.display = "block";
				document.getElementById("popbox").style.display = "block";
			}

			function pupclose() {
				document.getElementById("bg").style.display = "none";
				document.getElementById("popbox").style.display = "none";
			}
			function change_color(c) {
				if(c.checked==true){
					c.parentNode.parentNode.style.backgroundColor="#EDF9FF";
				}
				if(c.checked==false){
					c.parentNode.parentNode.style.backgroundColor="white";
				}
			}	
			function submitCheck(){
				var modiid = document.getElementById('modiid').value;
				var val =""; 
				$("input[name='checkbox']:checkbox:checked").each(function(){ 
					val = val+","+ $(this).val();
				});
				window.location.href = "itemAndModiAction!add?modiid=" + modiid+"&val="+val;
			}
</script>
</head>
<body>
	<input type='checkbox' id='sideToggle'>
	<aside>
		<div id="wraps">
			<div id="head"></div>
			<div id="mainbody">
				<input type='hidden' value='0' id='id' />
				<div class="body_1">
					<div class="left">MODIFIER NAME</div>
					<div class="right">
						<input class="input" type="text" value="TOPPING" id='nameinput'>
					</div>
				</div>
				<div class="body_2">
					<div class="opt_name">OPTION NAME</div>
					<div class="select">
						<input type="radio" name="mode" id="mode_1" /> <label
							class="select_font">Single</label> <input type="radio"
							name="mode" checked="checked"> <label class="select_font">Multiple</label>
					</div>
				</div>
				<div class="body_4">
					<div class="option">OPTIONS</div>
					<div class="delimage">
						<input type="image" src="images/add.png" onclick="optCreate();" />
					</div>
				</div>
				<div class="body_3">
					<div class="list_boxs" id="optList">
						<!--里面的列表1-->
						<div class="list_box_items">
							<div class="names">Cream</div>
							<div class="discountes">
								<input class="delimage" type="image" src="images/menu.png" />
							</div>
							<div class="pad">&nbsp;</div>
							<div class="discountes">$ 0.00</div>
						</div>
						<div class="subimage">
							<input class="subtract" type="image" src="images/subtract.png" />
						</div>
						<div id="optCreate"></div>
					</div>
				</div>
			</div>
			<div class="r_footer">
				<div class="r_footer_content">
					<table>
						<tr>
							<td class="r_footer_img"><img src="images/delete.png"
								onClick="testMessageBox(event);" /></td>
							<td class="r_footer_btn">
								<button class="cancel">Cancel</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="save" onclick="save();">Save</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</aside>
	<input type="hidden" id="modiid" value=""/>
	<s:set name="allsublist" value="#request.modifierList" />
	<div id='wrap'>

		<div class="list_box" id="style-default"
			style="overflow:hidden; height:40px;">
			<!--里面的标题-->
			<div class="list_box_item" style="background:#f1f1f1;">
				<div class="names">
					<div class="leftsides">Modifier Name</div>
				</div>
				<div class="discounts">
					<div class="lefts">Choice Type</div>
					<div class="lefts">Options</div>
					<div class="lefts"></div>
				</div>

			</div>
		</div>

		<div class="list_box" id="style-default">
			<div id="create"></div>
			<s:iterator status="allStatus" value="allsublist">
				<!--里面的列表1-->
				<label for='sideToggle'>
					<div class="list_box_item"
						onClick="itemOnclick('<s:property value="id" />','<s:property value="modiName" />')">
						<div class="names">
							<div class="leftsides">
								<s:property value="modiName" />
							</div>
						</div>
						<div class="discounts">
							<div class="lefts">multiple</div>
							<div class="lefts">Strawberry;Cream;Blackbreey</div>
							<div class="lefts">
								<input type="image" value="" src="images/assign.JPG"
									class="assign_btn" onclick="pupopen(<s:property value='id' />)" />
							</div>
						</div>
					</div> </label>
			</s:iterator>

		</div>

	</div>
	<!--弹出框 -->
		
				<div id="bg"></div>
		<div id="popbox">
			<!---->
			<div class="menu_body">
				<div class="menu_header">
					<div class="menu_title">
						<h1>Apply <span>MA State Tax</span> to Items</h1>
						<p>One item can have multiple taxes</p>
					</div>
				</div>
				<div class="menu_content">
					<div class="menu_search">
						<input type="text" placeholder="Search for Items" style="margin-left:20px;" />
					</div>
					<div class="menu_list" style="margin-top:30px;">
					<s:set name="allsublist" value="#request.productList" />
					<s:iterator status="allStatus" value="allsublist">
						<div class="cell">
							<label><s:property value='pdtName' />
								<input id='<s:property value='pdtId' />' type="checkbox" value='<s:property value='pdtId' />' name="checkbox" onclick="change_color(this)" />
							</label>
						</div>
					</s:iterator>
					</div>

				</div>
				<div class="menu_footer">
					<div class="menu_footer_content">
						<table>
							<tr>
								<td class="menu_cancel">
									<button value="cancel" onclick="pupclose()">Cancel</button>
								</td>
								<td class="menu_confirm">
									<button value="confirm" onclick="submitCheck();">Confirm</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<!---->
		</div>
</body>
</html>
