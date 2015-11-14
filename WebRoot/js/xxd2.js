window.onload = function() {
	var btn1 = document.getElementById('btn1');
	var btn2 = document.getElementById('btn2');

	btn1.onclick = function() {		
		var numberimg = document.getElementById('number1');
		btn1.src = "images/token1_on.JPG";
		btn2.src = "images/token2_off.JPG";
		numberimg.style.backgroundImage = "url(images/token1.JPG)";
	}
	
	btn2.onclick = function() {
		var numberimg = document.getElementById('number1');
		btn2.src = "images/token2_on.JPG";
		btn1.src = "images/token1_off.JPG";
		numberimg.style.backgroundImage = "url(images/token2.JPG)";
	}
}