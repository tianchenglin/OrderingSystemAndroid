<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<html>
<head>
<meta charset="utf-8">
<title>列表</title>
<link rel="stylesheet" href="css/mystyle.css" />
<link href="css/xxd2.css" rel="stylesheet" type="text/css" />
<link href="css/cebianlan.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" href="css/buttonstyle.css" media="screen"
	type="text/css" />

<link rel="stylesheet" href="css/popwindow.css" media="screen"
	type="text/css" />
<script type="text/javascript" src="js/popwindow.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<style type="text/css">
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
      function itemOnclick(id, nameinput, categoryinput){
                document.getElementById('taxesid').value = id;
				document.getElementById('nameinput').innerHTML="<input type='text' value='"+nameinput+" ' id='nameinput1'/>";
				document.getElementById('categoryinput').innerHTML="<input type='text' value='"+categoryinput+"' id='categoryinput1' />";
			}	
		function pupopen(taxesid) {
					$("input[name='checkbox']").removeAttr("checked"); 
					document.getElementById('taxesid').value=taxesid;
					$.ajax({
						url:"ItemAndTaxAction!list",
						data:"taxesid="+taxesid,
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
	  function save() {
		var id = document.getElementById('id').value;
		var nameinput = document.getElementById('nameinput1').value;
		var categoryinput = document.getElementById('categoryinput1').value;
		alert(id);

		window.location.href = "taxesAction!addTaxes?id=" + id + "&nameinput="
				+ nameinput + "&categoryinput=" + categoryinput + "";
	}
	function test() {
		var id = document.getElementById('id').value;
		window.location.href = "taxesAction!taxesDelete?id=" + id;
		closeWindow();
	}
	function submitCheck(){
				var taxesid = document.getElementById('taxesid').value;
				var val =""; 
				$("input[name='checkbox']:checkbox:checked").each(function(){ 
					val = val+","+ $(this).val();
				});
				window.location.href = "ItemAndTaxAction!add?taxesid=" + taxesid+"&val="+val;
			}
</script>
<style type="text/css">
.list_box {
	width: 100%;
}

.list_box_item .name {
	width: 30%;
	text-align: left;
	font-weight: normal;
}

.list_box_item .price {
	width: 20%;
	text-align: right;
}

.list_box_item .modifier {
	width: 1%;
}

.list_box_item .category {
	width: 15%;
}

.r_head_right .tabel .input {
	border-radius: 0px;
}
.taxes_right {
	height: 100px;
	width: 60%;
	color: #686868;
	font-size: 20px;
	font-weight: bold;
}
.r_head_right .tabel .input #nameinput1 {
	border-radius: 0px;
width:347px;
height:32px;
}
.r_head_right .tabel .input #categoryinput1 {
	border-radius: 0px;width:122px;
height:32px;
}
</style>
</head>

<body>
	<input type='checkbox' id='sideToggle'>
	<!--右边侧滑栏-->
	<aside class="aside">
		<!---->
		
		<div class="content">
		   <input type='hidden' value='0' id='id' />
			<div class="r_head">
				<div class="r_head_right" style="float: none;">
					<div class="tabel">
						<table>
						
							<tr>
								<td><label>NAME</label></td>
								<td>
									<div class="input" id="nameinput">
									<input type='text' value='New Taxes' id='nameinput1' />
									</div></td>
							</tr>
							<tr>
								<td><label>Tax Rate</label></td>
								<td>
									<div class="input" id="categoryinput"
										style="width: 100px;text-align: right;padding-right: 20px;">
										<input type='text' value='0.00' id='categoryinput1' />
									</div></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="r_content">
				<table class="taxes_right">
					<tr>
						<td>Include taxes in price</td>
						<td><label> <input type="radio" value="Yes"
								name="Include taxes in price" />Yes </label></td>
						<td><label> <input type="radio" checked="checked"
								value="No" name="Include taxes in price" />No </label></td>
					</tr>
					<tr>
						<td>Assign tax to all items</td>
						<td><label> <input type="radio" checked="checked"
								value="Yes" name="Assign tax to all items" />Yes </label></td>
						<td><label> <input type="radio" value="No"
								name="Assign tax to all items" />No </label></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="r_footer">
			<div class="r_footer_content">
				<table>
					<tr>
						<td class="r_footer_img"><img src="images/delete.png"
							onClick="testMessageBox(event);" />
						</td>
						<td class="r_footer_btn">
							<button class="cancel">Cancel</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<button class="save" onclick="save();">Save</button></td>
					</tr>
				</table>
			</div>
		</div>
		<!---->
	</aside>
	<input type="hidden" id="taxesid" value=""/>
	<s:set name="allsublist" value="#request.taxesList" />
	<div id='wrap'>
		<div class="content">
			<div class="con_middle">
				<div class="list" style="position: relative;">
                	
					<div class="list_box" id="style-default"
						style="overflow:hidden; height:40px;">
						<!--里面的标题-->
						<div class="list_box_item" style="background:#f1f1f1;">
							<div class="name" style="font-weight: bold;">Name</div>
							<div class="category" style="font-weight: bold;width: 14.5%;">
								Tax Rate</div>
							<div class="modifier" style="font-weight: bold;"></div>
							<div class="price" style="font-weight: bold;width: 11%;"></div>
						</div>
					</div>
					<div class="list_box" id="style-default">
                    	<div id="create"></div>
                    	<s:iterator status="allStatus" value="allsublist">
                    	<%-- <input type="hidden" value="<s:property value="id" />" id="id<s:property value="id" />" /> --%>
						<!--里面的列表1-->
						<label for='sideToggle'>
							<div class="list_box_item" 
							onClick="itemOnclick('<s:property value="id" />','<s:property value="taxeName" />', '<s:property value="rate" />')">
								<div class="name"><s:property value="taxeName" /></div>
								<div class="category" style="font-weight: bold;width: 14.5%;"><s:property value="rate" /></div>
								<div class="modifier"></div>
								<div class="price" style="font-weight: bold;width: 11%;">
									<input type="image" value="" src="images/assign.JPG"
										class="assign_btn" onclick="pupopen(<s:property value='id' />)" />
								</div>
							</div> </label></s:iterator>
						
					</div>

				</div>
			</div>
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