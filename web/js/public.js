$(document).ready(function() {
    $("#username").keyup(function() {
        if (5 > $(this).val().length) {
            $("#usernamespan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#usernamespan").html("用户名要多于5位");
        } else {
            $("#usernamespan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#usernamespan").html("");
        }
    });
    $("#username").blur(function() {
        if (5 > $(this).val().length) {
            $("#usernamespan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#usernamespan").html("用户名要多于5位");
        } else {
            $("#usernamespan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#usernamespan").html("");
        }
    });
    $("#name").keyup(function() {
        if ("" == $(this).val()) {
            $("#namespan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#namespan").html("名称不能为空");
        } else {
            $("#namespan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#namespan").html("");
        }
    });
    $("#name").blur(function() {
        if ("" == $(this).val()) {
            $("#namespan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#namespan").html("名称不能为空");
        } else {
            $("#namespan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#namespan").html("");
        }
    });
    $("#identity").keyup(function() {
        if (!/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/.test($(this).val())) {
            $("#identityspan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#identityspan").html("请输入正确的身份证号码");
        } else {
            $("#identityspan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#identityspan").html("");
        }
    });
    $("#identity").blur(function() {
        if (!/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/.test($(this).val())) {
            $("#identityspan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#identityspan").html("请输入正确的身份证号码");
        } else {
            $("#identityspan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#identityspan").html("");
        }
    });
    $("#password1").keyup(function() {
        if (5 > $(this).val().length) {
            $("#password1span").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#password1span").html("密码要多于5位");
        } else {
            $("#password1span").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#password1span").html("");
        }
    });
    $("#password2").keyup(function() {
        if ($("#password1").val() != $(this).val()) {
            $("#password2span").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#password2span").html("两次密码输入不一致");
        } else {
            $("#password2span").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#password2span").html("");
        }
    });
    $("#ages").keyup(function() {
        if (!/^([0-9]|[0-9]{2}|100)$/.test($(this).val())) {
            $("#agesspan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#agesspan").html("年龄输入不正确");
        } else {
            $("#agesspan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#agesspan").html("");
        }
    });
    $("#ages").blur(function() {
        if (!/^([0-9]|[0-9]{2}|100)$/.test($(this).val())) {
            $("#agesspan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#agesspan").html("年龄输入不正确");
        } else {
            $("#agesspan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#agesspan").html("");
        }
    });
    $("#url").keyup(function() {
        if ("" == $(this).val()) {
            $("#urlspan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#urlspan").html("跳转路径不能为空");
        } else {
            $("#urlspan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#urlspan").html("");
        }
    });
    $("#url").blur(function() {
        if ("" == $(this).val()) {
            $("#urlspan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
            $("#urlspan").html("跳转路径不能为空");
        } else {
            $("#urlspan").removeClass("input-notification error png_bg").addClass("input-notification success png_bg");
            $("#urlspan").html("");
        }
    });
});