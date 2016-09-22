/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function onBridgeReady() {
    WeixinJSBridge.call('hideOptionMenu');
    WeixinJSBridge.call('hideToolbar');
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

var ua = navigator.userAgent.toLowerCase();
if (!/micromessenger/.test(ua)) {
    alert("请在微信客户端打开！");
    window.opener = null;
    window.open('', '_self');
    window.location.replace("http://m.tl-kj.com/tljs.asp");
    window.close();
}