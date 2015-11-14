window.onload = function() {
	var temp1 = document.getElementById('box1');
	var img1 = document.getElementById('box_img1');
	var flag = 0;

	temp1.onclick = function() {			
		if (flag == 0) {		
			flag = 1;
			img1.src = "images/jiantou_xia.JPG";
		} else {
			flag = 0;
			img1.src = "images/jiantou_you.JPG";
		}
	}
}