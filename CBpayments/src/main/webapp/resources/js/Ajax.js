/**
 * 
 */

function searchAjax() {
		var data = {}
		data["query"] = $("#query").val();

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "search",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				$.each(data, function(i, item) {
					$("#JSON_table").append(
							"<tr>" + "<td>" + item.sno + "</td>" + "</tr>");
				});
			},
			error : function(e) {
				console.log("ERROR: ", e);
			},
			done : function(e) {
				console.log("DONE");
			}
		});
	}