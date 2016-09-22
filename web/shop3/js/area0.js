/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
//id,title
//province,city,area

$(document).ready(function() {
    $("#province").change(function() {
        if ("0" != $(this).val()) {
            $.post("area_do.jsp", "pid=" + $(this).val(), function(result) {
                var $city = $("#city");
                $city.empty();
                $(result).find("city").each(function(i, obj) {
                    var $id = $(obj).find("id").text();
                    var $name = $(obj).find("title").text();
                    var $option = $("<option></option>");
                    $option.val($id);
                    $option.html($name);
                    $city.append($option);
                });
                var $area = $("#area");
                $area.empty();
                var $option = $("<option></option>");
                $option.val("0");
                $option.html("区域");
                $area.append($option);
            });
        } else {
            var $city = $("#city");
            $city.empty();
            var $option = $("<option></option>");
            $option.val("0");
            $option.html("城市");
            $city.append($option);

            var $area = $("#area");
            $area.empty();
            $option = $("<option></option>");
            $option.val("0");
            $option.html("区域");
            $area.append($option);
        }
    });
    $("#city").change(function() {
        if ("0" != $(this).val()) {
            $.post("area_do.jsp", "pid=" + $(this).val(), function(result) {
                var $city = $("#area");
                $city.empty();
                $(result).find("city").each(function(i, obj) {
                    var $id = $(obj).find("id").text();
                    var $name = $(obj).find("title").text();
                    var $option = $("<option></option>");
                    $option.val($id);
                    $option.html($name);
                    $city.append($option);
                });
            });
        } else {
            var $area = $("#area");
            $area.empty();
            var $option = $("<option></option>");
            $option.val("0");
            $option.html("区域");
            $area.append($option);
        }
    });
});


