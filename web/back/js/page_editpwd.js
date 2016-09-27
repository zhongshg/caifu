$(document).ready(function() {
	// 隐藏Loading/注册失败 DIV
	$(".loading").hide();
	$(".login-error").hide();
	registError = $("<label class='error repeated'></label>");
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
