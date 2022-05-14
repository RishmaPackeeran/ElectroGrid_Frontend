$(document).ready(function()
{
	 $("#alertSuccess").hide();
 	 $("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event)
{
	// Clear alerts---------------------
	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();

	// Form validation-------------------
	var status = validateBillsForm();
	if (status != true)
	{
		 $("#alertError").text(status);
 		 $("#alertError").show();
 		 return;
 	}
 	
	// If valid-------------------------
 	var type = ($("#hidBillIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
 	{
 		url : "BillsAPI",
 		type : type,
 		data : $("#formBills").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onBillsSaveComplete(response.responseText, status);
 		}
 	}); 
 });

function onBillsSaveComplete(response, status)
	{
		if (status == "success")
		{
			 var resultSet = JSON.parse(response);
 			 if (resultSet.status.trim() == "success")
			 {
 				$("#alertSuccess").text("Successfully saved.");
 				$("#alertSuccess").show();
 				$("#divBillsGrid").html(resultSet.data);
 			 } 
 			 else if (resultSet.status.trim() == "error")
			 {
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			 }
 		} 
 		else if (status == "error")
 		{
 			$("#alertError").text("Error while saving.");
 			$("#alertError").show();
 		} 
 		else
 		{
 			$("#alertError").text("Unknown error while saving..");
 			$("#alertError").show();
 		}
		$("#hidBillIDSave").val("");
 		$("#formBills")[0].reset();
}

	// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
		 $("#hidBillIDSave").val($(this).data("itemid"));
		 $("#BillCode").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#BillMonth").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#CurrentRead").val($(this).closest("tr").find('td:eq(2)').text());
 		 $("#PreviousRead").val($(this).closest("tr").find('td:eq(3)').text());
 		 $("#TotalUnits").val($(this).closest("tr").find('td:eq(4)').text());
 		 $("#FinalAmount").val($(this).closest("tr").find('td:eq(5)').text());
	});
	
	
	//DELETE============================================
	$(document).on("click", ".btnRemove", function(event)
	{
 		$.ajax(
 		{
 			url : "BillsAPI",
 			type : "DELETE",
 			data : "BillID=" + $(this).data("BillID"),
 			dataType : "text",
 			complete : function(response, status)
 			{
 				onBillsDeleteComplete(response.responseText, status);
 			}
 		});
	});


	function onBillsDeleteComplete(response, status)
	{
		if (status == "success")
 		{
 			var resultSet = JSON.parse(response);
 			if (resultSet.status.trim() == "success")
 			{
 				$("#alertSuccess").text("Successfully deleted.");
 				$("#alertSuccess").show();
 				$("#divBillsGrid").html(resultSet.data);
 			} 
 			else if (resultSet.status.trim() == "error")
 			{
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			}
 		} 
 		else if (status == "error")
 		{
 				$("#alertError").text("Error while deleting.");
 				$("#alertError").show();
 		} 
 		else
 		{
 				$("#alertError").text("Unknown error while deleting..");
 				$("#alertError").show();
 		}
}
	

	// CLIENT-MODEL================================================================
	function validateBillsForm()
	{
		// Bill Code
		if ($("#BillCode").val().trim() == "")
		{
 			return "Insert Bill Code.";
 		}

		// Bill Month
		if ($("#BillMonth").val().trim() == "")
 		{
 			return "Insert Bill Issued Month.";
 		}

		// Current Reading-------------------------------
		if ($("#CurrentRead").val().trim() == "")
 		{
 			return "Insert Current Reading.";
 		}
		
		// Previous Reading-------------------------------
		if ($("#PreviousRead").val().trim() == "")
 		{
 			return "Insert Previous Reading.";
 		}
		
		
 		
		// is numerical value
		var tmpPrice = $("#CurrentRead").val().trim();
		if (!$.isNumeric(tmpCurrentRead))
		{
 			return "Insert a numerical value for Current Reading.";
 		}
		
		
		// is numerical value
		var tmpPrice = $("#PreviousRead").val().trim();
		if (!$.isNumeric(tmpPreviousRead))
		{
 			return "Insert a numerical value for Previous Reading.";
 		}
 		
		

		return true;
	}
	
	
	
	
