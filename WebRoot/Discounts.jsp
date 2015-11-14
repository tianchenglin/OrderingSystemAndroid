<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<html>
<head>
<link href="css/xxd2.css" rel="stylesheet" type="text/css" />
<link href="css/cebianlan.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/buttonstyle.css" media="screen"
	type="text/css" />
<script language="JavaScript" src="js/xxd2.js"></script>
<link rel="stylesheet" href="css/popwindow.css" media="screen"
	type="text/css" />
<script type="text/javascript" src="js/popwindow.js"></script>
<script type="text/javascript">
	function itemOnclick(id, name, count) {
		document.getElementById('id').innerHTML = "<input type='hidden' value='"+id+"' id='id1' />";
		document.getElementById('name').innerHTML = "<input type='text' value='"+name+"' id='name1' />";
		document.getElementById('count').innerHTML = "<input type='text' value='"+count+"' id='number1' />";
	}
	function save() {
		var id = document.getElementById('id1').value;
		var name = document.getElementById('name1').value;
		var count = document.getElementById('number1').value;

		window.location.href = "discountAction!addDiscount?id=" + id + "&name="
				+ name + "&count=" + count + "";
	}
	function test() {
		var id = document.getElementById('id1').value;
		window.location.href = "discountAction!discountDelete?id=" + id;
		closeWindow();
	}
</script>
</head>
<body>
	<input type='checkbox' id='sideToggle'>
	<aside>
		<div class="side_top">
			<div style="height: 40px;"></div>
			<div id="id">
				<input type='hidden' value='0' id='id1' />
			</div>
			<div class="side_con">
				<div class="side_name">Name</div>
				<div class="side_name_con" id="name">
					<input type='text' value='New Discount' id='name1' />
				</div>
			</div>
			<div class="side_con">
				<div class="side_name">DIscount Amount</div>
				<div class="side_name_con">
					<div class="side_number" id="count">
						<input type='text' value='0.00' id='number1' />
					</div>
					<div class="side_button">
						<input type="image" value="" src="images/token1_on.JPG"
							class="token1_btn_on" id="btn1" /> <input type="image" value=""
							src="images/token2_off.JPG" class="token2_btn_off" id="btn2" />
					</div>
				</div>
			</div>
			<div class="side_con">
				<div class="side_text">Leave the discount amount blank to
					enter at the time of sale.</div>
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
							<button class="save" onclick="save();">Save</button>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</aside>
	<s:set name="allsublist" value="#request.discountsList" />
	<div id='wrap'>

		<div class="list_box" id="style-default"
			style="overflow:hidden; height:40px;">

			<div class="list_box_item" style="background:#f1f1f1;">
				<div class="name">Name</div>
				<div class="discount">Discount Amount</div>
			</div>
		</div>
		<div class="list_box" id="style-default">
			<div id="create"></div>
			<s:iterator status="allStatus" value="allsublist">
				<label for='sideToggle'>
					<div class="list_box_item"
						onClick="itemOnclick('<s:property value="id" />','<s:property value="discountName" />', '<s:property value="amount" />')">
						<div class="name">
							<s:property value="discountName" />
						</div>
						<div class="discount">
							<s:property value="amount" />
						</div>
					</div> </label>
			</s:iterator>
		</div>

	</div>
</body>
</html>