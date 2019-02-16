$(document).ready(function() {

	$("#requestForm").submit(function(event) {

		//stop submit the form, we will post it manually.
		event.preventDefault();

		fire_ajax_submit();

	});
	
	$("#registerClient").submit(function(event) {

		//stop submit the form, we will post it manually.
		event.preventDefault();

		registerNewClient_submit();

	});
	
});

function registerNewClient_submit() {
	
	var formData = {

			redirectURI :  $("#redirectURI").val(),
			userEndPoint:  $("#userEndPoint").val(),
			clientName :  $("#clientName").val(),
			clientURI : $("#clientURI").val()
		}
	
	var protocol = window.location.protocol;
	var host = window.location.host;
	var pathArray = window.location.pathname.split('/');
	var pathName = pathArray[1];
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : protocol + "//" + host + "/" + pathName + "/registerNewClient",
		data : JSON.stringify(formData),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {

			alert("Succes");
			var json = "<h4>Request Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			$("#bth-request").prop("disabled", false);
			$('#message').html(json);

		},
		error : function(e) {
			alert("Error");
			var json = "<h4>Request Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#message').html(json);

			console.log("ERROR : ", e);
			$("#btn-search").prop("disabled", false);

		}
	});

}
function fire_ajax_submit() {

	// PREPARE FORM DATA
	var formData = {
		tokenEndPoint : $("#tokenEndPoint").val(),
		clientName :  $("#clientName").val(),
		clientSecret :  $("#clientSecret").val(),
		userName :  $("#userName").val(),
		password : $("#password").val(),
		grantType :  $("#grantType").val(),
		scope :  $("#scope").val(),
		userEndPoint:  $("#userEndPoint").val()
		
	}

	var protocol = window.location.protocol;
	var host = window.location.host;
	var pathArray = window.location.pathname.split('/');
	var pathName = pathArray[1];
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : protocol + "//" + host + "/" + pathName + "/authenticate",
		data : JSON.stringify(formData),
		dataType : 'json',
		cache : false,
		timeout : 600000,
		success : function(data) {

			var json = "<h4>Request Response</h4><pre>"
					+ JSON.stringify(data, null, 4) + "</pre>";
			$("#bth-request").prop("disabled", false);
			$('#message').html(json);

		},
		error : function(e) {

			var json = "<h4>Request Response</h4><pre>" + e.responseText
					+ "</pre>";
			$('#message').html(json);

			console.log("ERROR : ", e);
			$("#btn-search").prop("disabled", false);

		}
	});

}
