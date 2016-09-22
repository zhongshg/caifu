/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
function checkscss(id) {
    var menu = document.getElementsByName("menu");
    for (var i = 0; i < menu.length; i++) {
        if (id == menu[i].id) {
            document.getElementById(menu[i].id).className = "nav-top-item current";
        } else {
            document.getElementById(menu[i].id).className = "nav-top-item";
        }
    }
}

