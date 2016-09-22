/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function() {
    $("#username").blur(function() {
        $.post(wx + "/UsersServlet?method=testUsername", {"id": $("#id").val(), "username": $(this).val()}, function(result) {
            if ("true" == result) {
                $("#usernamespan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
                $("#usernamespan").html("用户名已被注册");
            }
            ;
        });
    });
    $("#identity").blur(function() {
        $.post(wx + "/UsersServlet?method=testIdentity", {"id": $("#id").val(), "identity": $(this).val()}, function(result) {
            if ("true" == result) {
                $("#identityspan").removeClass("input-notification success png_bg").addClass("input-notification error png_bg");
                $("#identityspan").html("身份证号已被注册");
            }
            ;
        });
    });
    $("#testbutton").click(function() {
        $.post(wx + "/UsersServlet?method=testUsername", {"id": $("#id").val(), "username": $("#username").val()}, function(result1) {
            $.post(wx + "/UsersServlet?method=testIdentity", {"id": $("#id").val(), "identity": $("#identity").val()}, function(result2) {
                if ("true" == result1 || "true" == result2 || (5 > $("#username").val().length || "" == $("#name").val() || !/^[1-9]{1}[0-9]{14}$|^[1-9]{1}[0-9]{16}([0-9]|[xX])$/.test($("#identity").val()) || !/^([0-9]|[0-9]{2}|100)$/.test($("#ages").val()))) {
                    return false;
                } else {
                    $("#submit").html("<input type='submit'></input>");
                    $("#form1").submit();
                }
            });
        });
    });
});