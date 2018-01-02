window.onload = function() {

	$("#changetime").click(function() {
		if ($(this).val() === "restAllTime") {
			$("#detail").hide();
		} else {
			$("#detail").show();
		}
	})

	// function changetime(){
	// var obj_select = document.getElementById("changetime");
	// var obj_div = document.getElementById("detail");
	// obj_select.onchange = function(){
	// obj_div.style.display = jQuery("#changetime option:selected").val()
	// =="restAllTime"? "none" : "block";
	// }
	// }

	$("#fengdaiDeleteType")
			.click(
					function() {
						var p = "<input  class='form-control' type='text' name='moneynumStr' placeholder='100.00'>";
						if ($(this).val() === "changeUserAmount") {

							if ($("input[name=moneynumStr]").length === 0) {
								$("#fengdaiusername").after(p);
							} else {
								;
							}
						} else {
							if ($("input[name=moneynumStr]").length > 0) {
								$("input[name=moneynumStr]").remove();
							}
						}

					})


}
