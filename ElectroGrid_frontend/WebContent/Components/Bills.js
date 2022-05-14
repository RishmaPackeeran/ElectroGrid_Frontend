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
});
	
	
	//DELETE============================================
	$(document).on("click", ".btnRemove", function(event)
	{
 		$.ajax(
 		{
 			url : "BillsAPI",
 			type : "DELETE",
 			data : "BillID=" + $(this).data("billid"),
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
 				$("#alertSuccess").text("Successfully Deleted.");
 				$("#alertSuccess").show();
 				$("#divBillsGrid").html(resultSet.data);
 			} else if (resultSet.status.trim() == "error")
 			{
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			}
 		} else if (status == "error")
 		{
 				$("#alertError").text("Error while Deleting.");
 				$("#alertError").show();
 		} else
 		{
 				$("#alertError").text("Unknown error while Deleting..");
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
		

		return true;
	}
	
	

	
