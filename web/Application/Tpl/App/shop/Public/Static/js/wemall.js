$(document).ready(function() {
//    document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
//        WeixinJSBridge.call('hideOptionMenu');
//    });
//	推荐商品
//	$('.menu .ccbg dd').each(function(){
//		if( $(this).attr("menu") == '1' ){
//			$(this).show();
//		}else{
//			$(this).hide();
//		}
//	});

    $('#cart').on('click', function() {
        $('#menu-container').hide();
        $('#cart-container').show();
        $('#user-container').hide();

        $(".footermenu ul li a").each(function() {
            $(this).attr("class", "");
        });
        $(this).children("a").attr("class", "active");

        $(".footermenu").hide();
    });
    $('#home').on('click', function() {
        $('#menu-container').show();
        $('#cart-container').hide();
        $('#user-container').hide();

        $(".footermenu ul li a").each(function() {
            $(this).attr("class", "");
        });
        $(this).children("a").attr("class", "active");
    });


    $('#ticket').on('click', function() {
        $('#tx-container').hide();
        $('#member-container').hide();
        $('#user-container').hide();
        $('#ticket-container').show();
        $(".footermenu ul li a").each(function() {
            $(this).attr("class", "");
        });
        $(this).children("a").attr("class", "active");
    })

    $('#member').on('click', function() {
        $('#tx-container').hide();
        $('#ticket-container').hide();
        $('#user-container').hide();
        $('#member-container').show();

        $(".footermenu ul li a").each(function() {
            $(this).attr("class", "");
        });
        $(this).children("a").attr("class", "active");
    })

    $('#tx').on('click', function() {
        $('#ticket-container').hide();
        $('#member-container').hide();
        $('#user-container').hide();
        $('#tx-container').show();

        $(".footermenu ul li a").each(function() {
            $(this).attr("class", "");
        });
        $(this).children("a").attr("class", "active");
    })

    $('#user').on('click', function() {
        $('#menu-container').hide();
        $('#cart-container').hide();
        $('#ticket-container').hide();
        $('#member-container').hide();
        $('#tx-container').hide();
        $('#user-container').show();

        $(".footermenu ul li a").each(function() {
            $(this).attr("class", "");
        });
        $(this).children("a").attr("class", "active");

        $.ajax({
            type: 'POST',
            url: appurl + '/App/Index/getorders',
            data: {
                uid: $_GET['uid']
            },
            success: function(response, status, xhr) {
                if (response) {
                    var json = eval(response);
                    var html = '';
                    var order_status = '';

                    $.each(json, function(index, value) {
                        var pay = '';
                        var order = '';
                        if (value.order_status == '0') {
                            order_status = 'no';
                            order = '未发货';
                        } else if (value.order_status == '1') {
                            order_status = 'no';
                            var confirm_url = appurl + '/App/Index/confirm_order?id=' + value.orderid + '&uid=' + $_GET['uid'];
                            order = '<a href="' + confirm_url + '" style="color:red">确认收货</a>';
                        } else if (value.order_status == '4') {
                            order_status = 'no';
                            order = '已退货';
                        } else {
                            order_status = 'ok';
                            order = '已完成';
                        }

                        if (value.pay_status == '0') {
                            pay_status = 'no';
                            pay = '<a href="' + value.pay_url + '">去支付</a>';
                        } else if (value.pay_status == '1') {
                            pay_status = 'ok';
                            pay = '已支付';
                        }
                        //html += '<tr><td>'+value.orderid+'</td><td class="cc">'+value.totalprice+'元</td><td class="cc"><em class="'+pay_status+'">'+pay+'</em></td><td class="cc"><em class="'+order_status+'">'+order+'</em></td></tr>';

                        html += '<li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:#FFF;"><table><tr><td style="border-bottom:0px">订单编号:' + value.orderid + '</td></tr>';
                        html += '<td style="border-bottom:0px">订单金额:' + value.totalprice + '元</td></tr>';
                        html += '<td style="border-bottom:0px">订单时间:' + value.time + '</td></tr>';
                        html += '<td style="border-bottom:0px">支付状态:<em class="' + pay_status + '">' + pay + '</em>';
                        if (value.pay_status == '0')
                        {
                            html += '<a href="' + value.pay_url + '">(已经支付?)</a>';
                        }
                        html += '</td></tr>';
                        if (value.order_status == '1')
                        {
                            html += '<td style="border-bottom:0px">订单状态:<em class="' + order_status + '" style="background-color:#FFFF00;">' + order + '</em></td></tr>';
                        }
                        else
                        {
                            html += '<td style="border-bottom:0px">订单状态:<em class="' + order_status + '">' + order + '</em></td></tr>';
                        }

                        html += '<td style="border-bottom:0px">商品名称:' + value.cart_name + '</td></tr>';
                        html += '<td style="border-bottom:0px">订单详情:' + value.note + '</td></tr>';

                        html += '<td style="border-bottom:0px">快递公司:' + value.order_info_name + '</em></td></tr>';
                        html += '<td style="border-bottom:0px">快递单号:' + value.order_info_num + '</em></td></tr>';

                        html += '</table></li>';
                    });

                    $('#orderlistinsert').empty();
                    $('#orderlistinsert').append(html);
                }

            },
            beforeSend: function() {
                $('#page_tag_load').show();
            },
            complete: function() {
                $('#page_tag_load').hide();
            }
        });
    });
});

function user() {
    $('#user').click();
}
function home() {
    $('#home').click();
}
function clearCache() {
    $('#ullist').find('li').remove();

    $('#home').click();

    $('.reduce').each(function() {
        $(this).children().css('background', '');
    });
    $('#totalNum').html(0);
    $('#cartN2').html(0);
    $('#totalPrice').html(0);
}
function addProductN(wemallId) {
    var last_cnt = $("#last_cnt").html();
    var jqueryid = wemallId.split('_')[0] + '_' + wemallId.split('_')[1];
//    var price = parseFloat(wemallId.split('_')[2]);
    var price = parseFloat($("#price").val());
    var productN = parseFloat($('#' + jqueryid).find('.count').html());
    if (Number($('#' + jqueryid).find('.count').html()) < Number(last_cnt)) {
        $('#' + jqueryid).find('.count').html(productN + 1);
    }
    var thistotalNum = parseFloat($('#thistotalNum').html());
    var thistotalPrice = parseFloat($('#thistotalPrice').html()) + parseFloat(price);

    var discountratio = $("#discountratio").val();
    var thistotaldiscountratioPrice = parseFloat(thistotalPrice) * parseFloat(discountratio) / 100;
    if (Number(last_cnt) > thistotalNum) {
        $('#thistotalNum').html(thistotalNum + 1);
        $('#thistotalPrice').html(thistotalPrice.toFixed(2));
        $("#thistotaldiscountratioPrice").html(thistotaldiscountratioPrice.toFixed(2));
    }
}
function reduceProductN(wemallId) {
//    var price = parseFloat(wemallId.split('_')[2]);
    var price = parseFloat($("#price").val());
    var jqueryid = wemallId.split('_')[0] + '_' + wemallId.split('_')[1];
    var reduceProductN = parseFloat($('#' + jqueryid).find('.count').html());
    if (reduceProductN == 1) {
        return false;
    }
    if (Number($('#' + jqueryid).find('.count').html()) > 1) {
        $('#' + jqueryid).find('.count').html(reduceProductN - 1);
    }
    var thistotalNum = parseFloat($('#thistotalNum').html());
    var thistotalPrice = parseFloat($('#thistotalPrice').html()) - parseFloat(price);

    var discountratio = $("#discountratio").val();
    var thistotaldiscountratioPrice = parseFloat(thistotalPrice) * parseFloat(discountratio) / 100;
    if (0 < thistotalNum) {
        $('#thistotalNum').html(thistotalNum - 1);
        $('#thistotalPrice').html(thistotalPrice.toFixed(2));
        $("#thistotaldiscountratioPrice").html(thistotaldiscountratioPrice.toFixed(2));
    }
}
function doProduct(id, name, price, img) {
    var bgcolor = $('#' + id).children().css('background-color').colorHex().toUpperCase();
    if (bgcolor == '#FFFFFF') {
        $('#' + id).children().css('background-color', '#D00A0A');

        var cartMenuN = parseFloat($('#cartN2').html()) + 1;
        $('#totalNum').html(cartMenuN);
        $('#cartN2').html(cartMenuN);

        var totalPrice = parseFloat($('#totalPrice').html()) + parseFloat(price);
        $('#totalPrice').html(totalPrice.toFixed(2));

        var wemallId = 'wemall_' + id;
        var html = '<li class="ccbg2" id="' + wemallId + '"><div class="orderdish"><span class="idss"  style="display:none;">' + id + '</span><span name="title">' + name + '</span><span class="price" id="v_0" style="display:none;">' + price + '</span><span style="display:none; class="price">元</span></div><div class="orderchange"><a href=javascript:addProductN("' + wemallId + '_' + price + '") class="increase"><b class="ico_increase">加一份</b></a><span class="count" id="num_1_499">1</span><a href=javascript:reduceProductN("' + wemallId + '_' + price + '") class="reduce"><b class="ico_reduce">减一份</b></a></div></li>';

        $('#ullist').append(html);

        $('#good_pic').attr('src', img)
    } else {
        $('#' + id).children().css('background-color', '');

        var cartMenuN = parseFloat($('#cartN2').html()) - 1;
        $('#totalNum').html(cartMenuN);
        $('#cartN2').html(cartMenuN);

        var totalPrice = parseFloat($('#totalPrice').html()) - parseFloat(price);
        $('#totalPrice').html(totalPrice.toFixed(2));

        var wemallId = 'wemall_' + id;
        $('#' + wemallId).remove();
    }
}

function submitTxOrder() {

    if (!confirm("您确认需要提现吗？"))
    {
        return false;
    }

    var price = $("#price").val();
    var testprice = price.replace(".", "");
    if ("" == price || !/^[0-9]*$/.test(testprice)) {
        alert("请完善提现信息！");
        setTimeout(function() {
            return false;
        }, 3000);
    } else {
        $.ajax({
            type: 'POST',
            url: 'shop_do.jsp?act=tx',
            data: {
                wxsid: $("#wxsid").val(),
                openid: $("#openid").val(),
                price: $("#price").val()
            },
            success: function(result) {
                result = eval('(' + result + ')');
                if (true == result.success)
                {
                    alert(result.message);
                    setTimeout(function() {
                        location.reload();
                    }, 3000);
                    return true;
                }
                else
                {
                    alert(result.message);
                    return false;
                }
            },
            beforeSend: function() {
                $('#tx-menu-shadow').show();
                $('#txshowcard').hide();
            },
            complete: function() {
                $('#tx-menu-shadow').hide();
                $('#txshowcard').show();
            }
        });
    }
}

function submitOrder() {
    //获取订单信息
    var json = '';
    $('#ullist li').each(function() {
        var name = $(this).find('span[name=title]').html();
        var num = $(this).find('span[class=count]').html();
        var price = $(this).find('span[class=price]').html();
        var id = $(this).find('span[class=idss]').html();
        json += '{"name":"' + name + '","num":"' + num + '","price":"' + price + '","id":"' + id + '"},';

    });
    json = json.substring(0, json.length - 1);
    json = '[' + json + ']';

    if ($('#totalPrice').html() <= 0)
    {
        alert('请选择商品');
        return false;
    }

    var name = $('#name').val();
    var phone = $('#phone').val();
    var weixin = $('#weixin').val();
    var address = $('#address').val();
    var s_province = $('#s_province').val();
    var s_city = $('#s_city').val();
    var s_county = $('#s_county').val();

    if (province_check)
    {
        if (s_province == '省份')
        {
            alert('请选择省份');
            return false;
        }

        if (s_city == '城市')
        {
            alert('请选择城市');
            return false;
        }

        var user_address = s_province + ',' + s_city + ',' + s_county + ',' + address;
    }
    else
    {
        var user_address = address;
    }

    if (s_county == '区域')
    {
        s_county = '';
    }

    if (name.length <= 0)
    {
        alert('请输入名称');
        return false;
    }

    if (phone.length <= 0)
    {
        alert('请输入电话');
        return false;
    }

    if (address.length <= 0)
    {
        alert('请输入地址');
        return false;
    }

    $.ajax({
        type: 'POST',
        url: appurl + '/App/Index/addorder',
        data: {
            uid: $_GET['uid'],
            cartData: json,
            userData: $('form').serializeArray(),
            totalPrice: $('#totalPrice').html(),
            user_address: user_address,
            good_num_cnt: good_num_cnt,
            good_num_id: good_num_id
        },
        success: function(response, status, xhr) {

            if (response.msg) {
                alert(response.msg);
                return false;
            }

            $('#user').click();
            $('#ullist').find('li').remove();
            $('.reduce').each(function() {
                $(this).children().css('background', '');
            });
            $('#totalNum').html(0);
            $('#cartN2').html(0);
            $('#totalPrice').html(0);

            if (response) {
                window.location.href = response;
                return false;
            }

            $.ajax({
                type: 'POST',
                url: appurl + '/App/Index/getorders',
                data: {
                    uid: $_GET['uid']
                },
                success: function(response, status, xhr) {
                    if (response) {
                        var json = eval(response);
                        var html = '';
                        var order_status = '';

                        $.each(json, function(index, value) {
                            var pay = '';
                            var order = '';
                            if (value.order_status == '0') {
                                order_status = 'no';
                                order = '未发货';
                            } else if (value.order_status == '1') {
                                order_status = 'no';
                                var confirm_url = appurl + '/App/Index/confirm_order?id=' + value.orderid + '&uid=' + $_GET['uid'];
                                order = '<a href="' + confirm_url + '" style="color:red">确认收货</a>';
                            } else if (value.order_status == '4') {
                                order_status = 'no';
                                order = '已退货';
                            } else {
                                order_status = 'ok';
                                order = '已完成';
                            }

                            if (value.pay_status == '0') {
                                pay_status = 'no';
                                pay = '<a href="' + value.pay_url + '">去支付</a>';
                            } else if (value.pay_status == '1') {
                                pay_status = 'ok';
                                pay = '已支付';
                            }
                            //html += '<tr><td>'+value.orderid+'</td><td class="cc">'+value.totalprice+'元</td><td class="cc"><em class="'+pay_status+'">'+pay+'</em></td><td class="cc"><em class="'+order_status+'">'+order+'</em></td></tr>';

                            html += '<li style="border: 1px solid #d0d0d0;border-radius: 10px;margin-bottom:10px;background-color:#FFF;"><table><tr><td style="border-bottom:0px">订单编号:' + value.orderid + '</td></tr>';
                            html += '<td style="border-bottom:0px">订单金额:' + value.totalprice + '元</td></tr>';
                            html += '<td style="border-bottom:0px">订单时间:' + value.time + '</td></tr>';
                            html += '<td style="border-bottom:0px">支付状态:<em class="' + pay_status + '">' + pay + '</em>';
                            if (value.pay_status == '0')
                            {
                                html += '<a href="' + value.pay_url + '">(已经支付?)</a>';
                            }
                            html += '</td></tr>';
                            if (value.order_status == '1')
                            {
                                html += '<td style="border-bottom:0px">订单状态:<em class="' + order_status + '" style="background-color:#FFFF00;">' + order + '</em></td></tr>';
                            }
                            else
                            {
                                html += '<td style="border-bottom:0px">订单状态:<em class="' + order_status + '">' + order + '</em></td></tr>';
                            }


                            html += '<td style="border-bottom:0px">商品名称:' + value.cart_name + '</td></tr>';
                            html += '<td style="border-bottom:0px">订单详情:' + value.note + '</td></tr>';

                            html += '<td style="border-bottom:0px">快递公司:' + value.order_info_name + '</em></td></tr>';
                            html += '<td style="border-bottom:0px">快递单号:' + value.order_info_num + '</em></td></tr>';

                            html += '</table></li>';

                        });
                        $('#orderlistinsert').empty();
                        $('#orderlistinsert').append(html);
                    }
                },
                beforeSend: function() {
                    $('#page_tag_load').show();
                },
                complete: function() {
                    $('#page_tag_load').hide();
                }
            });
        },
        beforeSend: function() {
            $('#menu-shadow').show();
            $('#showcard').hide();
        },
        complete: function() {
            $('#menu-shadow').hide();
            $('#showcard').show();
        }
    });


}
var $_GET = (function() {
    var url = window.document.location.href.toString();
    var u = url.split("?");
    if (typeof (u[1]) == "string") {
        u = u[1].split("&");
        var get = {};
        for (var i in u) {
            var j = u[i].split("=");
            get[j[0]] = j[1];
        }
        return get;
    } else {
        return {};
    }
})();
String.prototype.colorHex = function() {
    var that = this;
    if (/^(rgb|RGB)/.test(that)) {
        var aColor = that.replace(/(?:\(|\)|rgb|RGB)*/g, "").split(",");
        var strHex = "#";
        for (var i = 0; i < aColor.length; i++) {
            var hex = Number(aColor[i]).toString(16);
            if (hex === "0") {
                hex += hex;
            }
            strHex += hex;
        }
        if (strHex.length !== 7) {
            strHex = that;
        }
        return strHex;
    } else if (reg.test(that)) {
        var aNum = that.replace(/#/, "").split("");
        if (aNum.length === 6) {
            return that;
        } else if (aNum.length === 3) {
            var numHex = "#";
            for (var i = 0; i < aNum.length; i += 1) {
                numHex += (aNum[i] + aNum[i]);
            }
            return numHex;
        }
    } else {
        return that;
    }
};
var good_num_key = '';
var good_num_cnt = 'null';
var good_num_id = 0;
function showDetail(id, name, price, img) {

    window.shareData = {
        "imgUrl": shareData_url + "/Public" + img,
        "sendFriendLink": shareData_sendFriendLink,
        "tTitle": name,
        "tContent": shareData_tTitle
    };

    $.ajax({
        type: 'post',
        url: appurl + '/App/Index/fetchgooddetail',
        data: {
            id: id,
        },
        success: function(response, status, xhr) {
            $('body').show();
            $('#mcover').show();
            var json = eval(response);
            $('#detailpic').attr('src', rooturl + '/Public/Uploads/' + json.image);
            $('#detailtitle').html(json.title);
            $('#detailinfo').html(json.detail);

            good_num_key = json.good_num;
            good_num_id = id;

            if (typeof (json.guigename1) != "object" && typeof (json.guigevalue1) != "object")
            {
                if (json.guigename1.length > 0 && json.guigevalue1.length > 0)
                {
                    $('#guigename1').html(json.guigename1 + '：');
                    $('#guigevalue1').html(json.guigevalue1);

                    $('#type_siz1').attr('show', 1);
                    $('#type_siz1').show();
                }
            }

            if (typeof (json.guigename2) != "object" && typeof (json.guigevalue2) != "object")
            {
                if (json.guigename2.length > 0 && json.guigevalue2.length > 0)
                {
                    $('#guigename2').html(json.guigename2 + '：');
                    $('#guigevalue2').html(json.guigevalue2);

                    $('#type_siz2').attr('show', 1);
                    $('#type_siz2').show();
                }
            }

            check_shengyu();

            $('#detailinfo img').click(function() {
                return false;
            })

            $('#add_cart').click(function() {
                doProductNew(id, name, price, rooturl + '/Public/Uploads/' + json.image);
                //$('#cart').click();
            });
        }
    });
}

function check_shengyu()
{
    var type1 = $('#type_siz1').attr('show');
    var type2 = $('#type_siz2').attr('show');

    if (type1 == 1 && type2 == 1)
    {
        var type1_value = $("input[name='type_size1']:checked").attr('id');
        var type2_value = $("input[name='type_size2']:checked").attr('id');

        var name = type1_value + '|' + type2_value;
    }
    else
    {
        var type1_value = $("input[name='type_size1']:checked").attr('id');

        var name = type1_value + '|gz2_0';
    }

    if (typeof (good_num_key[name]) != 'object')
    {
        return false;
    }
    var good_num = good_num_key[name]['num'];
    good_num_cnt = good_num_key[name]['key'];
    if (typeof (good_num) == 'object')
    {
        good_num = 0;
    }

    if (typeof (good_num_cnt) == 'object')
    {
        good_num_cnt = 'null';
    }

    $('#last_cnt').html(good_num);

    if (good_num <= 0)
    {
        $('#showcard').css("background-color", "#E0E0E0");
        $('#showcard').css("border", "#E0E0E0");
        $('#showcard').attr("href", "javascript:alert('该规格的产品已经卖完了！');");
    }
    else
    {
        $('#showcard').css("background-color", "");
        $('#showcard').css("border", "");
        $('#showcard').attr("href", "javascript:submitOrder();");
    }
}

var order_list = new Array();


function in_array(search, array) {
    for (var i in array) {
        if (array[i] == search) {
            return true;
        }
    }
    return false;
}

function now_buy() {
    $('#add_cart').click();
    $('#cart').click();
}

function doProductNew(id, name, price, img) {
    $(".footermenu ul li a").each(function() {
        $(this).attr("class", "");
    });
    $('#add_cart').children("a").attr("class", "active");

    if (!in_array(id, order_list)) {
        order_list[id] = id;
        var cartMenuN = parseFloat($('#cartN2').html()) + 1;
        $('#totalNum').html(cartMenuN);
        $('#cartN2').html(cartMenuN);
        $('#cartN3').html(cartMenuN);

        var totalPrice = parseFloat($('#totalPrice').html()) + parseFloat(price);
        $('#totalPrice').html(totalPrice.toFixed(2));

        var wemallId = 'wemall_' + id;
        var html = '<li class="ccbg2" id="' + wemallId + '"><div class="orderdish"><span class="idss" style="display:none;">' + id + '</span><span name="title">' + name + '</span><span class="price" id="v_0" style="display:none;">' + price + '</span><span style="display:none; class="price">元</span></div><div class="orderchange"><a href=javascript:addProductN("' + wemallId + '_' + price + '") class="increase"><b class="ico_increase">加一份</b></a><span class="count" id="num_1_499">1</span><a href=javascript:reduceProductN("' + wemallId + '_' + price + '") class="reduce"><b class="ico_reduce">减一份</b></a></div></li>';

        $('#ullist').append(html);

        $('#good_pic').attr('src', img)
    }
    return false;
}

function showMenu() {
    $("#menu").find("ul").toggle();
}
function toggleBar() {
    $(".footermenu ul li a").each(function() {
        $(this).attr("class", "");
    });
    $(this).children("a").attr("class", "active");
}
function showProducts(id) {
    $('#menu_id li').each(function() {
        if ($(this).attr("menu") == id) {
            $(this).show();
        } else {
            $(this).hide();
        }
    });
    $('#menu ul').hide();
}
function showAll() {
    $('#menu_id li').each(function() {
        $(this).show();
    });
    $('#menu ul').hide();
}