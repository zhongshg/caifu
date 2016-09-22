/*!
 * jquery.scrollLoading.js
 * by zhangxinxu http://www.zhangxinxu.com/wordpress/?p=1259
 * 2010-11-19 v1.0
 * 2012-01-13 v1.1 鍋忕Щ鍊艰绠椾慨鏀� position 鈫� offset
 * 2012-09-25 v1.2 澧炲姞婊氬姩瀹瑰櫒鍙傛暟, 鍥炶皟鍙傛暟
 * 2014-08-11 v1.3 淇璁剧疆婊氬姩瀹瑰櫒鍙傛暟涓€浜沚ug, 浠ュ強璇垹posb鍊肩殑涓€浜涗綆绾ч敊璇�
*/
(function(a){a.fn.scrollLoading=function(b){var c={attr:"data-url",container:a(window),callback:a.noop};var d=a.extend({},c,b||{});d.cache=[];a(this).each(function(){var h=this.nodeName.toLowerCase(),g=a(this).attr(d.attr);var i={obj:a(this),tag:h,url:g};d.cache.push(i)});var f=function(g){if(a.isFunction(d.callback)){d.callback.call(g.get(0))}};var e=function(){var g=d.container.height();if(d.container.get(0)===window){contop=a(window).scrollTop()}else{contop=d.container.offset().top}a.each(d.cache,function(m,n){var p=n.obj,j=n.tag,k=n.url,l,h;if(p){l=p.offset().top-contop,h=l+p.height();if((l>=0&&l<g)||(h>0&&h<=g)){if(k){if(j==="img"){f(p.attr("src",k))}else{p.load(k,{},function(){f(p)})}}else{f(p)}n.obj=null}}})};e();d.container.bind("scroll",e)}})(jQuery);