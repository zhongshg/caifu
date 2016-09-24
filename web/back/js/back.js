var $ = function(id) {
	return document.getElementById(id);
}

function show_menu(mname, fname) {
	var func = document.getElementById('func');
	var module = document.getElementById('module');
	module.innerHTML = mname;
	func.innerHTML = '&gt;' + fname;// 触发以后信息块
}

function show_menuB(numB) {
	for (j = 0; j < 50; j++) {
		if (j != numB) {
			if ($('Bli0' + j)) {
				$('Bli0' + j).style.display = 'none';
				$('Bf0' + j).style.background = 'url(images/01.gif)';
			}
		}
	}
	if ($('Bli0' + numB)) {
		if ($('Bli0' + numB).style.display == 'block') {
			$('Bli0' + numB).style.display = 'none';
			$('Bf0' + numB).style.background = 'url(images/01.gif)';
		} else {
			$('Bli0' + numB).style.display = 'block';
			$('Bf0' + numB).style.background = 'url(images/02.gif)';
		}
	}
}

var temp = 0;
function show_menuC() {
	if (temp == 0) {
		document.getElementById('LeftBox').style.display = 'none';
		document.getElementById('RightBox').style.marginLeft = '0';
		document.getElementById('Mobile').style.background = 'url(images/center.gif)';

		temp = 1;
	} else {
		document.getElementById('RightBox').style.marginLeft = '222px';
		document.getElementById('LeftBox').style.display = 'block';
		document.getElementById('Mobile').style.background = 'url(images/center0.gif)';

		temp = 0;
	}
}