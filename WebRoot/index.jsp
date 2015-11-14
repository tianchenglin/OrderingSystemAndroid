<%@ page language="java" import="java.util.*" pageEncoding="gb2312"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>Backoffice site</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script>
	function jumpTop(targeFrame) {
		document.cookie = "currentFrame=" + targeFrame;
		if (targeFrame == "Discounts.jsp") {
			window.frames["mainFrame"].location.href = "discountAction!InitPage";
		} else if (targeFrame == "Library.jsp") {
			window.frames["mainFrame"].location.href = "libraryAction!InitPage";
		} else if (targeFrame == "Tags.jsp") {
			window.frames["mainFrame"].location.href = "tagsAction!InitPage";
		} else if (targeFrame == "Taxes.jsp") {
			window.frames["mainFrame"].location.href = "taxesAction!InitPage";
		} else if (targeFrame == "Categories.jsp") {
			window.frames["mainFrame"].location.href = "menutypeAction!InitPage";
		} else if (targeFrame == "modifiers.jsp") {
			window.frames["mainFrame"].location.href = "modifierAction!InitPage";
		} else {
			document.getElementById("mainFrame").src = targeFrame;
		}

	}
	function create(targeFrame) {
		if (targeFrame == 'Discounts') {
			window.frames["mainFrame"].document.getElementById("create").innerHTML = "<label  for='sideToggle'><div class='list_box_item' onClick=\"itemOnclick('0','New Discount', '0.00');\"><div class=\"name\">New Discount</div><div class=\"discount\"> 0.00 %</div></div></label>";
		} else if (targeFrame == 'Library') {
			//window.frames["mainFrame"].document.getElementById("create").innerHTML = "<label for='sideToggle'><div class=\"list_box_item\" onClick=\"itemOnclick('0','New Name','')\"><div class=\"name\">New Name</div><div class=\"category\"></div><div class=\"modifier\"></div><div class=\"price\">$0.00</div></div></label>";
			$
					.ajax({
						url : "libraryAction!crateLibrary",
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
		} else if (targeFrame == 'Categories') {
			window.frames["mainFrame"].document.getElementById("create").innerHTML = "<input type=\"hidden\" id=\"id\" /><div id=\"abc\" class=\"list_box_item\"><input onkeypress='EnterPress(event)' class=\"name\" id=\"name\" style='height:30px; margin-top:4px;'/><div class=\"counts\">0 Items</div></div>";
		} else if (targeFrame == 'modifiers') {
			window.frames["mainFrame"].document.getElementById("create").innerHTML = "<label  for='sideToggle'><div class=\"list_box_item\" onClick=\"itemOnclick('0','New Modifiers');\"><div class=\"names\"><div class=\"leftsides\">New Modifiers</div></div></div></label>";
		} else if (targeFrame == 'Tags') {
			window.frames["mainFrame"].document.getElementById("create").innerHTML = "<input type=\"hidden\" id=\"id\" /><div id=\"abc\" class=\"list_box_item\"><input onkeypress='EnterPress(event)' class=\"name\" id=\"name\" style='height:30px; margin-top:4px;'/><div class=\"counts\">0 Items</div></div>";
		} else if (targeFrame == 'Taxes') {
			window.frames["mainFrame"].document.getElementById("create").innerHTML = "<label for='sideToggle'><div class=\"list_box_item\ onClick=\"itemOnclick('0','New Tax', '0.00');\"><div class=\"name\">New Tax</div><div class=\"category\">0.00 %</div></div></label>";
		}

	}
</script>
</head>
<frameset rows="75,*" cols="*" framespacing="0" frameborder="no"
	border="0">
	<frame src="top.html" name="topFrame" scrolling="No"
		noresize="noresize" id="topFrame" />
	<frame src="reports.html" name="mainFrame" id="mainFrame" />
</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>
