$(document).ready(function() {
	// 隐藏Loading/注册失败 DIV
	$(".loading").hide();
	$(".login-error").hide();
	registError = $("<label class='error repeated'></label>");

	// 输入框激活焦点、移除焦点
	jQuery.focusblur = function(focusid) {
		var focusblurid = $(focusid);
		var defval = focusblurid.val();
		focusblurid.focus(function() {
			var thisval = $(this).val();
			if (thisval == defval) {
				$(this).val("");
			}
		});
		focusblurid.blur(function() {
			var thisval = $(this).val();
			if (thisval == "") {
				$(this).val(defval);
			}
		});

	};
	/* 下面是调用方法 */
	$.focusblur("#uid");
	// 输入框激活焦点、溢出焦点的渐变特效
	if ($("#uid").val()) {
		$("#uid").prev().fadeOut();
	}
	;
	$("#uid").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#uid").blur(function() {
		if (!$("#uid").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	if ($("#uname").val()) {
		$("#uname").prev().fadeOut();
	}
	;
	$("#uname").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#uname").blur(function() {
		if (!$("#uname").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	if ($("#pwd").val()) {
		$("#pwd").prev().fadeOut();
	}
	;
	$("#pwd").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#pwd").blur(function() {
		if (!$("#pwd").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	
	if ($("#cardid").val()) {
		$("#cardid").prev().fadeOut();
	}
	;
	$("#cardid").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#cardid").blur(function() {
		if (!$("#cardid").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	if ($("#bankcard").val()) {
		$("#bankcard").prev().fadeOut();
	}
	;
	$("#bankcard").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#bankcard").blur(function() {
		if (!$("#bankcard").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	if ($("#phone").val()) {
		$("#phone").prev().fadeOut();
	}
	;
	$("#phone").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#phone").blur(function() {
		if (!$("#phone").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	
	if ($("#roleid").val()) {
		$("#roleid").prev().fadeOut();
	}
	;
	$("#roleid").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#roleid").blur(function() {
		if (!$("#roleid").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	
	$("#submit").bind("click", function() {
		$('.loading').show();
	});

	$("body").each(function() {
		$(this).keydown(function() {
			if (event.keyCode == 13) {
				$('.loading').show();
			}
		});
	});

});
