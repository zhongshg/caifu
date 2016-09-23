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
	$.focusblur("#uname");

	// 输入框激活焦点、溢出焦点的渐变特效
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
	if ($("#password").val()) {
		$("#password").prev().fadeOut();
	}
	;
	$("#password").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#password").blur(function() {
		if (!$("#password").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	if ($("#passwordAgain").val()) {
		$("#passwordAgain").prev().fadeOut();
	}
	;
	$("#passwordAgain").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#passwordAgain").blur(function() {
		if (!$("#passwordAgain").val()) {
			$(this).prev().fadeIn();
		}
		;
	});
	if ($("#parentid").val()) {
		$("#parentid").prev().fadeOut();
	}
	;
	$("#parentid").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#parentid").blur(function() {
		if (!$("#parentid").val()) {
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
	if ($("#tel").val()) {
		$("#tel").prev().fadeOut();
	}
	;
	$("#tel").focus(function() {
		$(this).prev().fadeOut();
	});
	$("#tel").blur(function() {
		if (!$("#tel").val()) {
			$(this).prev().fadeIn();
		}
		;
	});

	// ajax提交注册信息
	$("#submit").bind("click", function() {
		regist();
	});

	$("body").each(function() {
		$(this).keydown(function() {
			if (event.keyCode == 13) {
				regist();
			}
		});
	});

});

function regist() {
	// 校验uname, password，校验如果失败的话不提交
	var md5 = new MD5();
	$.ajax({
		url : "./validate.jsp",
		type : "post",
		data : {
			uname : $("#uname").val(),
			password : md5.MD5($("#password").val()),
			parentid : $("#parentid").val(),
			cardid : $("#cardid").val(),
			bankcard : $("#bankcard").val(),
			tel : $("#tel").val()
		},
		dataType : "json",
		beforeSend : function() {
			debugger;
			$('.loading').show();
		},
		success : function(data) {
			debugger;
			$('.loading').hide();
			if (data.hasOwnProperty("code")) {
				if (data.code == 0) {
					// 注册成功
					window.location.href = "./login.jsp";
				} else if (data.code == 1) {
					// 数据库链接失败
					$(".login-error").show();
					$(".login-error").html("数据库链接失败");
				} else if (data.code == 2) {
					// 参数传递失败
					$(".login-error").show();
					$(".login-error").html("数据存在空值");
				} else if (data.code == 3) {
					// 银行卡号已经被注册
					$("#bankcard").addClass("error");
					$("#bankcard").after(registError);
					$("#bankcard").next("label.repeated").text("银行卡号已经被注册");
					registError.show();
				} else if (data.code == 4) {
					// 手机号已经被注册
					$("#tel").addClass("error");
					$("#tel").after(registError);
					$("#tel").next("label.repeated").text("手机号已经被注册");
					registError.show();
				} else if (data.code == 5) {
					// 身份证号已经被注册
					$("#cardid").addClass("error");
					$("#cardid").after(registError);
					$("#cardid").next("label.repeated").text("身份证号已经被注册");
					registError.show();
				} else if (data.code == 6) {
					// 新增用户失败
					$(".login-error").show();
					$(".login-error").html("新增用户失败");
				} else {
					// 系统错误
					$(".login-error").show();
					$(".login-error").html("系统错误");
				}
			}
		},
	});
}
