<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<meta charset="utf-8">
		<title>列表</title>
		<link href="css/xxd2.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="css/popwindow.css" media="screen" type="text/css" />
        <link rel="stylesheet" href="css/buttonstyle.css" media="screen" type="text/css" />
        <script type="text/javascript" src="js/popwindow.js"></script>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <link rel="stylesheet" href="css/mystyle.css" />
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
			function itemOnclick(id, name) {
				document.getElementById('menutypeid').value=id;
				document.getElementById('abc').innerHTML = "<div id=\"cde\" class=\"name\">" +name + "</div>";
				save(name);
			}
			function pupopen(menutypeid) {
				$("input[name='checkbox']").removeAttr("checked"); 
				document.getElementById('menutypeid').value=menutypeid;
				$.ajax({
					url:"ItemAndMenutypeAction!list",
					type:"get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "success_jsonpCallback",
					async : false,
					success:function(result){
						for (i in result.iat) {
							var tmp = result.iat[i];
							if(tmp.menutypeId == menutypeid){
								document.getElementById(tmp.itemId).disabled=false;
								document.getElementById(tmp.itemId).checked=true;
								document.getElementById(tmp.itemId).parentNode.parentNode.style.backgroundColor="#EDF9FF";
							}
							else{
								document.getElementById(tmp.itemId).disabled=true;
								document.getElementById(tmp.itemId).parentNode.parentNode.style.backgroundColor="#FFFFFF";
							}
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
			function EnterPress(e) {
				var e = e || window.event;
				var name = document.getElementById('name').value;
				if (e.keyCode == 13) {
					itemOnclick('0', name);
					
				}
			}
			function save(name1) {
				var id1 = document.getElementById('menutypeid').value;
				alert(name1);
				window.location.href = "menutypeAction!addMenutype?id=" + id1 + "&name="
						+ name1 + "";
			}
			function test(id) {
				window.location.href = "menutypeAction!menutypeDelete?id=" + id;
				closeWindow();
			}
			function submitCheck(){
				var menutypeid = document.getElementById('menutypeid').value;
				var val =""; 
				$("input[name='checkbox']:checkbox:checked").each(function(){ 
					val = val+","+ $(this).val();
				});
				window.location.href = "ItemAndMenutypeAction!add?menutypeid=" + menutypeid+"&val="+val;
			}
		</script>
	</head>
	<body>
		<input type="hidden" id="menutypeid" value="1"/>
		<div class="list_box" id="style-default">
        <div id="create"></div>
        <s:set name="allsublist" value="#request.menutypeList" />
			<!--里面的列表1-->
			<s:iterator status="allStatus" value="allsublist">
			<div class="list_box_item">
				<div class="name">
					<s:property value='typeName' />
				</div>
				<div class="counts">
					<s:property value='include' /> Items
				</div>
				<div class="assign">
                	
					<input type="image" value="" src="images/assign.JPG" class="assign_btn" onclick="pupopen(<s:property value='typeId' />)" />
                   
				</div>
				<div class="delete">
                <a href="#" onClick="xxdMessageBox(event,'<s:property value='typeId' />');">
					<input type="image" value="" src="images/delete.JPG" class="delete_btn" />
                </a>
				</div>
			</div>
			</s:iterator>
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
