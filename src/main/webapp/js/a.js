window.onload = function(){

    var obj_select = document.getElementById("changetime");
    var obj_div = document.getElementById("detail");
    obj_select.onchange = function(){
        obj_div.style.display = jQuery("#changetime  option:selected").val() =="restAllTime"? "none" : "block";
    }

	
}