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
		var sum = parseInt($("#spanMoney").html());
		if(sum < 3000){
			$(".login-error").show();
			$(".login-error").html("所选商品总额不足3000");
			return;
		}
		var allID = "";
		$("input:checkbox:checked").map(function(index,elem) {
			var id = $(elem).val();
			allID += id+",";
	    })
	    allID = allID.substring(0,allID.length-1);
		var allName = "";
		var allVAL = "";
		var allPrice = "";
		var idArr = allID.split(',');
		for(var i=0;i<idArr.length;i++)
		{
			allVAL += $("#pro"+idArr[i]).val()+",";
			allPrice += $("#price"+idArr[i]).val() +",";
			allName += $("#pname"+idArr[i]).val() +","; 
		}
		allVAL = allVAL.substring(0,allVAL.length-1);
		allPrice = allPrice.substring(0,allPrice.length-1);
		allName = allName.substring(0,allName.length-1);
		//alert(allVAL+"---"+allPrice);
	    //alert(allID);
	    regist(allID,allVAL,allPrice,allName);
	});

	$("body").each(function() {
		$(this).keydown(function() {
			if (event.keyCode == 13) {
				regist();
			}
		});
	});

});

function regist(allID,allVAL,allPrice,allName) {
	// 校验uname, password，校验如果失败的话不提交
	$.ajax({
		url : "./validate.jsp",
		type : "post",
		data : {
			uname : $("#uname").val(),
			password : $("#password").val(),
			uid : $("#uid").val(),
			cardid : $("#cardid").val(),
			bankcard : $("#bankcard").val(),
			tel : $("#tel").val(),
			storecode : $("#storecode").val(),
			nick : $("#nick").val(),
			allid : allID,
			allVAL : allVAL,
			allPrice : allPrice,
			allName : allName,
			sum: $("#spanMoney").html()
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
				}else if(data.code == 7){
					// 可用余额不足
					$(".login-error").show();
					$(".login-error").html("可用余额不足");
				}else{
					// 系统错误
					$(".login-error").show();
					$(".login-error").html("系统错误");
				}
			}
		},
	});
};

function selectPro(id){
	var selectp = $("#pro"+id).val();
	var price = $("#price"+id).val();
	var cur_money = $("#spanMoney").html();
	var sum = parseInt(cur_money) + selectp*price;
	$("#spanMoney").html(sum);
};

function check(id){
	if($("#check"+id).is(":checked")){
		$("#pro"+id).show();
	}else{
		var selectp = $("#pro"+id).val();
		var price = $("#price"+id).val();
		var cur_money = $("#spanMoney").html();
		var sum = parseInt(cur_money) - selectp*price;
		$("#spanMoney").html(sum);
		$("#pro"+id).val("");
		$("#pro"+id).hide();
	}
	
}
