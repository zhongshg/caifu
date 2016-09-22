<%-- 
    Document   : jsapi
    Created on : 2015-1-14, 16:00:27
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>${wx.name}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=0">
    </head>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script>
//新版微信支付
        function onBridgeReady() {
            WeixinJSBridge.invoke(
                    'getBrandWCPayRequest', {
                        "appId": '${jsapimap.appId}', //公众号名称，由商户传入     
                        "timeStamp": '${jsapimap.timeStamp}', //时间戳，自1970年以来的秒数     
                        "nonceStr": '${jsapimap.nonceStr}', //随机串     
                        "package": '${jsapimap.packages}',
                        "signType": '${jsapimap.signType}', //微信签名方式:     
                        "paySign": '${jsapimap.paySign}' //微信签名 
                    },
            function(res) {
                if (res.err_msg == "get_brand_wcpay_request:ok") {
                    window.location.replace("/shop3/orderdetail.jsp?act=result&wx=${wx.id}&openid=${jsapimap.openid}&sign=1&F_No=${jsapimap.F_No}");
                    //跳转支付成功页面！
                }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
                else {
                    window.location.replace("/shop3/orderdetail.jsp?act=result&wx=${wx.id}&openid=${jsapimap.openid}&sign=0&F_No=${jsapimap.F_No}");
                }
            }
            );
        }
        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        } else {
            onBridgeReady();
        }
    </script>
</html>
