var min = 1; 
var reg = function(x) {  
    return new RegExp("^[1-9]\\d*$").test(x);  
} ;
var  amount = function(obj, mode) {  
    var x = $(obj).val();  
    if (this.reg(parseInt(x))) {  
        if (mode) {  
            x++;  
        } else {  
            x--;  
        }  
    } else {  
        $(obj).val(1);  
        $(obj).focus();  
    }  
    return x;  
} ;
var reduce = function(obj) {  
    var x = this.amount(obj, false);  
    if (parseInt(x) >= this.min) {  
        $(obj).val(x);  
    } else {  
        $(obj).val(1);  
        $(obj).focus();  
    }  
} ;
var add = function(obj) {  
    var x = this.amount(obj, true);  
    var max = $('#nAmount').val();  
    if (parseInt(x) <= parseInt(max)) {  
        $(obj).val(x);  
    } else {  
        $(obj).val(max == 0 ? 1 : max);  
        $(obj).focus();  
    }  
};

function search() {
	var search = document.getElementById("search-sort").value;
	var value = document.getElementById("keywords").value;
	var src_to = "manageProducts.jsp?sr=search&search=" + search + "&value="
			+ value;
	window.location.href = encodeURI(encodeURI(src_to));
};
function buy(pid){
	var amount = $('#J_Amount').val();
	alert("manageProduct.jsp?rm=buy&pid="+pid+"&amount="+amount);
	window.location.href = "manageProduct.jsp?rm=buy&pid="+pid+"&amount="+amount;
}
