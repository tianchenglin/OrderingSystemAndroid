//得到所有交易的列表
function OnloadTransaction(){
	GetAllStaff();
	GetAllPayment();
	GetAllDept();
}

//得到所有隶属部门
function GetAllDept(){
	$.ajax({
		url : "salerecordAction!GetAllDept",
		data : {},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			var currentHtml=document.getElementById("deptList");
			currentHtml.innerHTML="<option>Dept</option>";
			for (i in data){			
				var insertHtml= '<option value='+data[i].dept+'>'+data[i].dept+'</option>';
				currentHtml.innerHTML=currentHtml.innerHTML+insertHtml;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

//得到所有支付方式
function GetAllPayment(){
	$.ajax({
		url : "salerecordAction!GetAllPayment",
		data : {},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			var currentHtml=document.getElementById("paymentList");
			currentHtml.innerHTML="<option>Payment</option>";
			for (i in data){	
				
				var insertHtml= '<option value='+data[i].payment+'>'+data[i].payment+'</option>';
				currentHtml.innerHTML=currentHtml.innerHTML+insertHtml;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}


//到鼠标悬浮到圆圈i上面时
function GetFocus(element){
	var elementId=element.getAttribute("id");
	var elementValue=element.getAttribute("value");
	var ID='#'+elementId;
	$(ID).tipso({
		useTitle: false,
		position: 'left',
		width: 300,
		offsetX: -20,
		background: '#F9F9F9',
		color: '#6A687C',
		border: '1px solid #000',
	});
    $.ajax({
		url : "salerecordAction!GetPdtByBillid",
		data : {
			salerecordId:elementValue,
		},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			var startHtml="<table class='detail'>";
			for (i in data){
				startHtml = startHtml+"<tr>"+
							"<td>"+data[i].pdtName+"</td>"+
							"<td>"+data[i].number+"</td>"+
							"<td>"+data[i].price+"</td>"+
							"</tr>";
			}
			var endHtml="</table>";
			var content = startHtml+endHtml;
		    $(ID).tipso('update', 'content', content);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

//得到全部员工列表
function GetAllStaff(){
	$.ajax({
		url : "staffAction!GetAllStaff",
		data : {},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			var currentHtml=document.getElementById("staffList");
			currentHtml.innerHTML="<option>Staff</option>";
			for (i in data){			
				var insertHtml= '<option value='+data[i].SAccount+'>'+data[i].SName+'</option>';
				currentHtml.innerHTML=currentHtml.innerHTML+insertHtml;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

//导出excel表格
function ExportExcel(){
	var code_Values = document.getElementsByTagName("input"); 
	var itemArr=new Array();
	var j=0;
	for(var i=1;i<code_Values.length;i++){
		if(code_Values[i].checked == true){
			var value=code_Values[i].getAttribute("value");
			itemArr[j]=value;
			j++;
		}
	}
	var itemNo=itemArr.join("@");
	$.ajax({
		url : "salerecordAction!GetTransactionByItemNo",
		data : {
			itemNo:itemNo,
		},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			var url="./download/"+data[0].path;
			window.open(url);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
	
}

//全选和取消全选转换
function ChangeCheckAll(checkbox){
	if(checkbox.checked==true){//选中时全选
		checkAll();
	}
	else{
		uncheckAll();//取消全选
	}
}

//全选，选择所有的checkbox
function checkAll() 
{ 
	var code_Values = document.all['checkbox']; 
	if(code_Values.length){ 
		for(var i=0;i<code_Values.length;i++) 
		{ 
			code_Values[i].checked = true; 
		} 
	}else{ 
		code_Values.checked = true; 
	} 
} 

//取消全选，取消选中所有的checkbox
function uncheckAll() 
{ 
	var code_Values = document.all['checkbox']; 
	if(code_Values.length){ 
		for(var i=0;i<code_Values.length;i++) 
		{ 
			code_Values[i].checked = false; 
		} 
	}else{ 
		code_Values.checked = false; 
	} 
} 

//按照时间排序转换，更换箭头图标
var flagDate=1;//标记,2时升序,1时降序
function ChangeDateSort(imgElement){
	document.getElementById("checkAll").checked=false;
	if(flagDate==2){
		imgElement.src = "images/jiantou_bottom_blue.png";
		ChangeDateUp();
		flagDate=1;
	}
	else{
		imgElement.src = "images/jiantou_top_blue.png";
		ChangeDateDown();
		flagDate=2;
	}
}

//按照支付方式转换
var flagPayment=1;
function ChangePaymentSort(imgElement){
	document.getElementById("checkAll").checked=false;
	if(flagDate==2){
		imgElement.src = "images/jiantou_bottom_blue.png";
		ChangePaymentUp();
		flagDate=1;
	}
	else{
		imgElement.src = "images/jiantou_top_blue.png";
		ChangePaymentDown();
		flagDate=2;
	}
}

//按照员工排序
var flagStaff=1;
function ChangeStaffSort(imgElement){
	document.getElementById("checkAll").checked=false;
	if(flagStaff==2){
		imgElement.src = "images/jiantou_bottom_blue.png";
		ChangeStaffUp();
		flagStaff=1;
	}
	else{
		imgElement.src = "images/jiantou_top_blue.png";
		ChangeStaffDown();
		flagStaff=2;
	}
}

//按照部门排序
var flagDept=1;
function ChangeDeptSort(imgElement){
	if(flagDept==2){
		imgElement.src = "images/jiantou_bottom_blue.png";
		ChangeDeptUp();
		flagDept=1;
	}
	else{
		imgElement.src = "images/jiantou_top_blue.png";
		ChangeDeptDown();
		flagDept=2;
	}
}

//按照部门升序排序
function ChangeDeptUp(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY dept asc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}

//按照部门降序排列
function ChangeDeptDown(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY dept desc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}

//按照员工姓名升序排序
function ChangeStaffUp(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY SName asc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}

//按照员工姓名降序排列
function ChangeStaffDown(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY SName desc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}

//按照支付方式升序排列
function ChangePaymentUp(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY payment asc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}

//按照支付方式降序排列
function ChangePaymentDown(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY payment desc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}

//按照时间升序排列
function ChangeDateUp(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY closeTime asc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}

//按照时间降序排列
function ChangeDateDown(){
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	sqlArr[6]="ORDER BY closeTime desc ";
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	
	GetTransactionAjax(sqlString);
}


//得到交易记录的Ajax函数
function GetTransactionAjax(sqlString){
	$.ajax({
		url : "salerecordAction!GetTransactionsBySql",
		data : {
			sql:sqlString,
		},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			localStorage.checkboxLength=data.length;
			var currentHtml=document.getElementById("transactionsList");
			currentHtml.innerHTML="";
			var dueTotal=0,
				collectedTotal=0,
				tipTotal=0;
			
			for (i in data){		
				
				var insertHtml= '<label for="sideToggle">'+
								'<div class="list_box_item"><div class="checkbox">'+
								'<input value="'+data[i].itemNo+'" name="checkbox" onclick="CheckedBox(this)" type="checkbox" /></div>'+
								'<div id="img'+i+'" onmouseover="GetFocus(this)" value="'+data[i].itemNo+'" class="checkbox" style="display:block">'+
								'<img src="images/infor.png" /></div>'+
								'<div class="date">'+data[i].dateAndTime+'</div>'+
								'<div class="staff" style="padding-left: 15px;">'+data[i].staff+'</div>'+
								'<div class="payment" style="padding-left: 10px;">'+""+'</div>'+
								'<div class="dept" style="padding-left: 15px;">'+data[i].dept+'</div>'+
								'<div class="tip">'+(parseFloat(data[i].tip)).toFixed(2)+'</div>'+
								'<div class="collected" style="">'+(parseFloat(data[i].collected)).toFixed(2)+'</div>'+
								'<div class="due" style="">'+(parseFloat(data[i].due)).toFixed(2)+'</div>'+
								'</div></label>';
				dueTotal=dueTotal+parseFloat(data[i].due);
				tipTotal=tipTotal+parseFloat(data[i].tip);
				collectedTotal=collectedTotal+parseFloat(data[i].collected);
				currentHtml.innerHTML=currentHtml.innerHTML+insertHtml;
			}
			document.getElementById("dueTotal").innerHTML=dueTotal.toFixed(2);
			document.getElementById("collectedTotal").innerHTML=collectedTotal.toFixed(2);
			document.getElementById("tipTotal").innerHTML=tipTotal.toFixed(2);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

//根据员工姓名获得交易记录列表
function GetTransactionByStaff(){
	document.getElementById("checkAll").checked=false;
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	
	var selectElement=document.getElementById("staffList");
	if(selectElement.value=="Staff"){
		sqlArr[2]="";
	}
	else{
		var SAccount=selectElement.value;
		sqlArr[2]="and SAccount='"+SAccount+"' ";	
	}
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	GetTransactionAjax(sqlString);
}

//根据交易方式获得交易记录列表
function GetTransactionByPayment(){
	document.getElementById("checkAll").checked=false;
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	
	var selectElement=document.getElementById("paymentList");
	if(selectElement.value=="Payment"){
		sqlArr[3]="";
	}
	else{
		var payment=selectElement.value;
		sqlArr[3]="and payment='"+payment+"' ";	
	}
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	GetTransactionAjax(sqlString);
}

//根据隶属部门获得交易记录列表
function GetTransactionByDept(){
	document.getElementById("checkAll").checked=false;
	var sql=localStorage.sql;
	var sqlArr=sql.split(",");
	
	var selectElement=document.getElementById("deptList");
	if(selectElement.value=="Dept"){
		sqlArr[4]="";
	}
	else{
		var dept=selectElement.value;
		sqlArr[4]="and dept='"+dept+"' ";	
	}
	localStorage.sql=sqlArr;
	var sqlString=sqlArr.join("");
	GetTransactionAjax(sqlString);
}

//得到员工详细信息表
function GetStaffList(){
	localStorage.setItem("op","update");
	$.ajax({
		url : "staffAction!GetAllStaff",
		data : {},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			var currentHtml=document.getElementById("staffList");
			currentHtml.innerHTML="";
			for (i in data){
				var sex="man";
				if(data[i].SSex==1){
					sex="woman";
				}
				var type;
				if(data[i].SType==0){
					type="Boss";
				}
				else if(data[i].SType==1){
					type="Chief";
				}
				else if(data[i].SType==2){
					type="Waiter";
				}
				var insertHtml= '<label for="sideToggle">'+
				  				'<div class="list_box_item" onClick="itemOnclick('+data[i].id+')">'+
				  				'<div class="name">'+data[i].SName+'</div>'+
				  				'<div class="name">'+data[i].SAccount+'</div>'+
								'<div class="category">'+sex+'</div>'+
								'<div class="modifier">'+data[i].SPhone+'</div>'+
								'<div class="price">'+type+'</div>'+
								'</div></label>';
				currentHtml.innerHTML=currentHtml.innerHTML+insertHtml;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}
//根据ID得到员工详细信息
function GetStaffById(staffId){
	$.ajax({
		url : "staffAction!GetStaffById",
		data : {id:staffId},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			document.getElementById("staffId").value=staffId;
			document.getElementById("staffAccount").value=data[0].SAccount;
			document.getElementById("staffName").value=data[0].SName;
			document.getElementById("staffSex").value=data[0].SSex;
			document.getElementById("staffPhone").value=data[0].SPhone;
			document.getElementById("staffAddr").value=data[0].SAddr;
			document.getElementById("staffIdent").value=data[0].SIdent;
			document.getElementById("staffType").value=data[0].SType;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}
//staff点击列表
var flagRight=0;
function itemOnclick(staffId) {
	localStorage.setItem("op","update");
	if(flagRight==1){flagRight=0;document.getElementById("aside").style.right="-700px";}
	else{
		document.getElementById("aside").style.right="0px";
		GetStaffById(staffId);
		flagRight=1;
	}
	if(localStorage.getItem("op")!="add"){
		document.getElementById("deleteImg").style.display="block";
	}
}
//cusstomers点击列表
function itemOnclick1(customersId) {
	localStorage.setItem("op","update");
	if(flagRight==1){flagRight=0;document.getElementById("aside").style.right="-700px";}
	else{
		document.getElementById("aside").style.right="0px";
		GetCustomersById(customersId);
		flagRight=1;
	}
	if(localStorage.getItem("op")!="add"){
		document.getElementById("deleteImg").style.display="block";
	}
	document.getElementById("importName").style.color="#000";
	document.getElementById("importPhone").style.color="#000";
}
//保存员工信息
function SaveStaffMessage() {
	var op=localStorage.getItem("op");
	if(op=="add"){
		var account=document.getElementById("staffAccount");
		var name=document.getElementById("staffName");
		var sex=document.getElementById("staffSex");
		var phone=document.getElementById("staffPhone");
		var addr=document.getElementById("staffAddr");
		var ident=document.getElementById("staffIdent");
		var type=document.getElementById("staffType");
		$.ajax({
			url : "staffAction!AddStaffMessage",
			data : {
				SAccount:account.value,
				SName:name.value,
				SPhone:phone.value,
				SSex:sex.value,
				SAddr:addr.value,
				SIdent:ident.value,
				SType:type.value
			},
			type : "get",
			dataType : "jsonp",
			jsonp : "jsoncallback",
			jsonpCallback : "success_jsonpCallback",
			async : false,
			success : function(data) {
				if(data.addResult==1){
					document.getElementById("aside").style.right="-700px";
					flagRight=0;
				};
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("error");
			}
		});
	}
	else if(op=="update"){
		var id=document.getElementById("staffId").value;
		var name=document.getElementById("staffName").value;
		var sex=document.getElementById("staffSex").value;
		var phone=document.getElementById("staffPhone").value;
		var addr=document.getElementById("staffAddr").value;
		var account=document.getElementById("staffAccount").value;
		var type=document.getElementById("staffType").value;
		var ident=document.getElementById("staffIdent").value;
		$.ajax({
			url : "staffAction!UpdateStaffMessage",
			data : {
				id:id,
				SAccount:account,
				SName:name,
				SPhone:phone,
				SSex:sex,
				SAddr:addr,
				SType:type,
				SIdent:ident
			},
			type : "get",
			dataType : "jsonp",
			jsonp : "jsoncallback",
			jsonpCallback : "success_jsonpCallback",
			async : false,
			success : function(data) {
				if(data.updateResult==1){
					document.getElementById("aside").style.right="-700px";
					flagRight=0;
				};
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("error");
			}
		});
	}
	GetStaffList();
	closeWindow();
}

//取消按钮关闭右面板
function CancelRightAside(){
	document.getElementById("aside").style.right="-700px";
	flagRight=0;
}

//根据ID删除员工
function DeleteStaffById(id){
	$.ajax({
		url : "staffAction!DeleteStaffById",
		data : {
			SAccount:id
		},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
	document.getElementById("aside").style.right="-700px";
	flagRight=0;
	GetStaffList();
	closeWindow();
}

//添加员工
function AddStaff(){
	document.getElementById("aside").style.right="0px";
	flagRight=1;
	
	var account=document.getElementById("staffAccount");
	var name=document.getElementById("staffName");
	var sex=document.getElementById("staffSex");
	var phone=document.getElementById("staffPhone");
	var addr=document.getElementById("staffAddr");
	var ident=document.getElementById("staffIdent");
	var type=document.getElementById("staffType");
	name.value="";
	sex.value="0";
	phone.value="";
	addr.value="";
	ident.value="";
	type.value="2";
	account.value="";
	document.getElementById("deleteImg").style.display="none";
	localStorage.setItem("op","add");
}

//得到顾客信息列表
function GetCustomersList(){
	localStorage.setItem("op","update");
	$.ajax({
		url : "contactAction!GetAllCustomers",
		data : {},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			var currentHtml=document.getElementById("customersList");
			currentHtml.innerHTML="";
			for (i in data){
				var insertHtml= '<label for="sideToggle">'+
				  				'<div class="list_box_item" onClick="itemOnclick1('+data[i].id+')">'+
				  				'<div class="name">'+data[i].Name+'</div>'+
				  				'<div class="name">'+data[i].Phone+'</div>'+
								'<div class="category">'+data[i].Add_City+'</div>'+
								'<div class="modifier">'+data[i].Add_Street+'</div>'+
								'<div class="price">'+data[i].Add_Apt+'</div>'+
								'</div></label>';
				currentHtml.innerHTML=currentHtml.innerHTML+insertHtml;
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

//根据ID获得顾客的详细列表
function GetCustomersById(id){
	$.ajax({
		url : "contactAction!GetCustomersById",
		data : {id:id},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
			document.getElementById("customersId").value=id;
			document.getElementById("customersName").value=data[0].Name;
			document.getElementById("customersPhone").value=data[0].Phone;
			document.getElementById("customersCity").value=data[0].Add_Street;
			document.getElementById("customersStreet").value=data[0].Add_Apt;
			document.getElementById("customersApt").value=data[0].Add_City;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
}

//添加顾客信息
function AddCustomers(){
	document.getElementById("aside").style.right="0px";
	flagRight=1;
	localStorage.setItem("op","add");
	var name=document.getElementById("customersName");
	var phone=document.getElementById("customersPhone");
	var city=document.getElementById("customersCity");
	var street=document.getElementById("customersStreet");
	var apt=document.getElementById("customersApt");
	name.value="";
	phone.value="";
	city.value="";
	street.value="";
	apt.value="";
	document.getElementById("deleteImg").style.display="none";
}

//保存顾客信息
function SaveCustomersMessage() {
	var name=document.getElementById("customersName").value;
	var phone=document.getElementById("customersPhone").value;
	
	if(name==""||name==null){
		document.getElementById("customersName").focus();
		document.getElementById("importName").style.color="#ff0000";
		return;
	}
	if(phone==""||phone==null){
		document.getElementById("importName").style.color="#000";
		document.getElementById("customersPhone").focus();
		document.getElementById("importPhone").style.color="#ff0000";
		return;
	}
	document.getElementById("importName").style.color="#000";
	document.getElementById("importPhone").style.color="#000";
	var op=localStorage.getItem("op");
	
	if(op=="add"){
		var name=document.getElementById("customersName");
		var phone=document.getElementById("customersPhone");
		var city=document.getElementById("customersCity");
		var street=document.getElementById("customersStreet");
		var apt=document.getElementById("customersApt");
		$.ajax({
			url : "contactAction!AddCustomersMessage",
			data : {
				Name:name.value,
				Phone:phone.value,
				Add_Street:street.value,
				Add_Apt:apt.value,
				setAdd_City:city.value
			},
			type : "get",
			dataType : "jsonp",
			jsonp : "jsoncallback",
			jsonpCallback : "success_jsonpCallback",
			async : false,
			success : function(data) {
				
				if(data.addResult==1){
					document.getElementById("aside").style.right="-700px";
					flagRight=0;
				};
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("error");
			}
		});
		
	}
	else if(op=="update"){
		var id=document.getElementById("customersId").value;
		var name=document.getElementById("customersName").value;
		var phone=document.getElementById("customersPhone").value;
		var city=document.getElementById("customersCity").value;
		var street=document.getElementById("customersStreet").value;
		var apt=document.getElementById("customersApt").value;
		$.ajax({
			url : "contactAction!UpdateCustomersMessage",
			data : {
				id:id,
				Name:name,
				Phone:phone,
				Add_Street:street,
				Add_Apt:apt,
				setAdd_City:city
			},
			type : "get",
			dataType : "jsonp",
			jsonp : "jsoncallback",
			jsonpCallback : "success_jsonpCallback",
			async : false,
			success : function(data) {
				if(data.updateResult==1){
					document.getElementById("aside").style.right="-700px";
					flagRight=0;
				};
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				alert("error");
			}
		});
	}
	GetCustomersList();
	closeWindow();
}

//根据ID删除顾客
function DeleteCustomersById(id){
	$.ajax({
		url : "contactAction!DeleteCustomersById",
		data : {
			id:id
		},
		type : "get",
		dataType : "jsonp",
		jsonp : "jsoncallback",
		jsonpCallback : "success_jsonpCallback",
		async : false,
		success : function(data) {
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("error");
		}
	});
	document.getElementById("aside").style.right="-700px";
	flagRight=0;
	GetCustomersList();
	closeWindow();
}