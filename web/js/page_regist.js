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
	$.ajax({
		url : "./validate.jsp",
		type : "post",
		data : {
			uname : $("#uname").val(),
			password : $("#password").val(),
			parentid : $("#uid").val(),
			cardid : $("#cardid").val(),
			bankcard : $("#bankcard").val(),
			tel : $("#tel").val(),
			storecode : $("#storecode").val(),
			nick:$("#nick").val()
		},
		dataType : "json",
		beforeSend : function() {
			$('.loading').show();
		},
		success : function(data) {
			$('.loading').hide();
			if (data.hasOwnProperty("code")) {
				if (data.code == 0) {
					// 注册成功
					window.location.href = "manageUsers.jsp?msg=sucr";
				} else if (data.code == 1) {
					// 数据库链接失败
					$(".login-error").show();
					$(".login-error").html("数据库链接失败");
				} else if (data.code == 2) {
					// 参数传递失败
					$(".login-error").show();
					$(".login-error").html("会员号、用户名、密码、身份证号以及银行卡号不能为空");
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
					$(".login-error").html("会员号已经被注册!");
				} else {
					// 系统错误
					$(".login-error").show();
					$(".login-error").html("系统错误");
				}
			}
		},
	});
}
