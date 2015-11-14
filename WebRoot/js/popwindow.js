var isIe = (document.all) ? true : false;
// ����select�Ŀɼ�״̬
function setSelectState(state) {
	var objl = document.getElementsByTagName('select');
	for ( var i = 0; i < objl.length; i++) {
		objl[i].style.visibility = state;
	}
}
function mousePosition(ev) {
	if (ev.pageX || ev.pageY) {
		return {
			x : ev.pageX,
			y : ev.pageY
		};
	}
	return {
		x : ev.clientX + document.body.scrollLeft - document.body.clientLeft,
		y : ev.clientY + document.body.scrollTop - document.body.clientTop
	};
}
// ��������
function showMessageBox(wTitle, content, pos, wWidth) {
	closeWindow();
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	if (isIe) {
		setSelectState('hidden');
	}
	var back = document.createElement("div");
	back.id = "back";
	var styleStr = "top:0px;z-index=2;left:0px;position:absolute;background:#666;width:"
			+ bWidth + "px;height:" + bHeight + "px;";
	styleStr += (isIe) ? "filter:alpha(opacity=0);" : "opacity:0;";
	back.style.cssText = styleStr;
	document.body.appendChild(back);
	showBackground(back, 50);
	var mesW = document.createElement("div");
	mesW.id = "mesWindow";
	mesW.className = "mesWindow";
	mesW.innerHTML = "<div class='mesWindowTop'><table width='100%' height='100%'><tr><td style=\"text-align:center;\">"
			+ wTitle
			+ "</td></tr></table></div><div class='mesWindowContent' id='mesWindowContent'>"
			+ content
			+ "</div><div class='mesWindowBottom'><a href=\"#\" onclick='closeWindow()' class=\"button white\">Cancel</a><a href=\"#\" onclick='test();' class=\"button red\">Delete</a></div>";
	styleStr = "left:40%;top:25%;position:absolute;width:" + wWidth + "px;";
	mesW.style.cssText = styleStr;
	document.body.appendChild(mesW);
}

//��������xxd
function xxdShowMessageBox(wTitle, content, pos, wWidth, id) {
	closeWindow();
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	if (isIe) {
		setSelectState('hidden');
	}
	var back = document.createElement("div");
	back.id = "back";
	var styleStr = "top:0px;z-index=2;left:0px;position:absolute;background:#666;width:"
			+ bWidth + "px;height:" + bHeight + "px;";
	styleStr += (isIe) ? "filter:alpha(opacity=0);" : "opacity:0;";
	back.style.cssText = styleStr;
	document.body.appendChild(back);
	showBackground(back, 50);
	var mesW = document.createElement("div");
	mesW.id = "mesWindow";
	mesW.className = "mesWindow";
	mesW.innerHTML = "<div class='mesWindowTop'><table width='100%' height='100%'><tr><td style=\"text-align:center;\">"
			+ wTitle
			+ "</td></tr></table></div><div class='mesWindowContent' id='mesWindowContent'>"
			+ content
			+ "</div><div class='mesWindowBottom'><a href=\"#\" onclick='closeWindow()' class=\"button white\">Cancel</a><a href=\"#\" onclick='test("+id+");' class=\"button red\">Delete</a></div>";
	styleStr = "left:40%;top:25%;position:absolute;width:" + wWidth + "px;";
	mesW.style.cssText = styleStr;
	document.body.appendChild(mesW);
}

// ������ɫѡ���
function showMessageBox1(pos, wWidth) {
	closeWindow();
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	if (isIe) {
		setSelectState('hidden');
	}
	var back = document.createElement("div");
	back.id = "back";
	var styleStr = "top:0px;z-index=2;left:0px;position:absolute;background:#666;width:"
			+ bWidth + "px;height:" + bHeight + "px;";
	styleStr += (isIe) ? "filter:alpha(opacity=0);" : "opacity:0;";
	back.style.cssText = styleStr;
	document.body.appendChild(back);
	showBackground(back, 50);
	var mesW = document.createElement("div");
	mesW.id = "mesWindow";
	mesW.className = "mesWindow";
	mesW.innerHTML = "<div class='container'><div class='color1' onclick='setTargetColor(this)'></div><div class='color2' onclick='setTargetColor(this)'></div><div class='color3' onclick='setTargetColor(this)'></div><div class='color4' onclick='setTargetColor(this)'></div><div class='color5' onclick='setTargetColor(this)'></div><div class='color6' onclick='setTargetColor(this)'></div><div class='color7' onclick='setTargetColor(this)'></div><div class='color8' onclick='setTargetColor(this)'></div><div class='color9' onclick='setTargetColor(this)'></div></div>";
	styleStr = "left:" + (pos.x) + "px;top:" + (pos.y)
			+ "px;position:absolute;width:" + wWidth + "px;";
	mesW.style.cssText = styleStr;
	document.body.appendChild(mesW);
}

// �ñ��������䰵
function showBackground(obj, endInt) {
	if (isIe) {
		obj.filters.alpha.opacity += 1;
		if (obj.filters.alpha.opacity < endInt) {
			setTimeout(function() {
				showBackground(obj, endInt)
			}, 5);
		}
	} else {
		var al = parseFloat(obj.style.opacity);
		al += 0.01;
		obj.style.opacity = al;
		if (al < (endInt / 100)) {
			setTimeout(function() {
				showBackground(obj, endInt)
			}, 5);
		}
	}
}
// �رմ���
function closeWindow() {
	if (document.getElementById('back') != null) {
		document.getElementById('back').parentNode.removeChild(document
				.getElementById('back'));
	}
	if (document.getElementById('mesWindow') != null) {
		document.getElementById('mesWindow').parentNode.removeChild(document
				.getElementById('mesWindow'));
	}
	if (isIe) {
		setSelectState('');
	}
}
// ���Ե���
function testMessageBox(ev) {
	var objPos = mousePosition(ev);
	messContent = "<div style='padding:20px 0 20px 0;text-align:center'>Are you sure you want to delete Discount Student Discount?</div>";
	showMessageBox('Delete Discount', messContent, objPos, 350);
}
//���Ե���xxd
function xxdMessageBox(ev,id) {
	var objPos = mousePosition(ev);
	messContent = "<div style='padding:20px 0 20px 0;text-align:center'>Are you sure you want to delete Tags?</div>";
	xxdShowMessageBox('Delete Tag', messContent, objPos, 350, id);
}
function DeleteStaffMessageBox(ev) {
	var id=document.getElementById("staffId").value;
	var objPos = mousePosition(ev);
	messContent = "<div style='padding:20px 0 20px 0;text-align:center'>Are you sure you want to delete Tags?</div>";
	DeleteStaffShowMessageBox('Delete Tag', messContent, objPos, 350, id);
}
function DeleteStaffShowMessageBox(wTitle, content, pos, wWidth, id) {
	closeWindow();
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	if (isIe) {
		setSelectState('hidden');
	}
	var back = document.createElement("div");
	back.id = "back";
	var styleStr = "top:0px;z-index=2;left:0px;position:absolute;background:#666;width:"
			+ bWidth + "px;height:" + bHeight + "px;";
	styleStr += (isIe) ? "filter:alpha(opacity=0);" : "opacity:0;";
	back.style.cssText = styleStr;
	document.body.appendChild(back);
	showBackground(back, 50);
	var mesW = document.createElement("div");
	mesW.id = "mesWindow";
	mesW.className = "mesWindow";
	mesW.innerHTML = "<div class='mesWindowTop'><table width='100%' height='100%'><tr><td style=\"text-align:center;\">"
			+ wTitle
			+ "</td></tr></table></div><div class='mesWindowContent' id='mesWindowContent'>"
			+ content
			+ "</div><div class='mesWindowBottom'><a href=\"#\" onclick='closeWindow()' class=\"button white\">Cancel</a><a href=\"#\" onclick='DeleteStaffById("+id+");' class=\"button red\">Delete</a></div>";
	styleStr = "left:40%;top:25%;position:absolute;width:" + wWidth + "px;";
	mesW.style.cssText = styleStr;
	document.body.appendChild(mesW);
}
function DeleteCustomersMessageBox(ev) {
	var id=document.getElementById("customersId").value;
	var objPos = mousePosition(ev);
	messContent = "<div style='padding:20px 0 20px 0;text-align:center'>Are you sure you want to delete Tags?</div>";
	DeleteCustomersShowMessageBox('Delete Tag', messContent, objPos, 350, id);
}
function DeleteCustomersShowMessageBox(wTitle, content, pos, wWidth, id) {
	closeWindow();
	var bWidth = parseInt(document.documentElement.scrollWidth);
	var bHeight = parseInt(document.documentElement.scrollHeight);
	if (isIe) {
		setSelectState('hidden');
	}
	var back = document.createElement("div");
	back.id = "back";
	var styleStr = "top:0px;z-index=2;left:0px;position:absolute;background:#666;width:"
			+ bWidth + "px;height:" + bHeight + "px;";
	styleStr += (isIe) ? "filter:alpha(opacity=0);" : "opacity:0;";
	back.style.cssText = styleStr;
	document.body.appendChild(back);
	showBackground(back, 50);
	var mesW = document.createElement("div");
	mesW.id = "mesWindow";
	mesW.className = "mesWindow";
	mesW.innerHTML = "<div class='mesWindowTop'><table width='100%' height='100%'><tr><td style=\"text-align:center;\">"
			+ wTitle
			+ "</td></tr></table></div><div class='mesWindowContent' id='mesWindowContent'>"
			+ content
			+ "</div><div class='mesWindowBottom'><a href=\"#\" onclick='closeWindow()' class=\"button white\">Cancel</a><a href=\"#\" onclick='DeleteCustomersById("+id+");' class=\"button red\">Delete</a></div>";
	styleStr = "left:40%;top:25%;position:absolute;width:" + wWidth + "px;";
	mesW.style.cssText = styleStr;
	document.body.appendChild(mesW);
}
function testChooseColor(ev) {
	var objPos = mousePosition(ev);
	showMessageBox1(objPos, 200);
}
function setTargetColor(obj) {
	var target = document.getElementById("target");
	target.style.backgroundColor = obj.style.backgroundColor;
	target.className = obj.className;
	closeWindow();
}