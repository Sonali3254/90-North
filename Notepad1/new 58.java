returnVal="";
if(NOT(zm_advancedInvoiceStatus == "Success")){
	payload=commerce.createAdvancedInvoice();
	SucessMsg = get(payload,"SuccessResponse");
	ErrorMsg = get(payload,"ErrorResponse");
	FailMsg = "Fail, "+ ErrorMsg;
	ReqPayload = get(payload,"RequestPayload");
	returnVal = returnVal + "1~zm_advancedInvoiceRequestPayload" + "~"+ ReqPayload+"|";
		if (isnull(ErrorMsg)){
		returnVal = returnVal + "1~zm_advancedInvoiceStatus" + "~"+ "Success" + "|" ;
		returnVal = returnVal + "1~zm_advancedInvoiceResponsePayload" + "~"+ SucessMsg+"|";
		v1=find(SucessMsg,"TransactionNumber");
		v2=find(SucessMsg,"TransactionDate");
		
		jsonObj= json (SucessMsg);
		customerTRXId= jsonget(jsonObj,"CustomerTransactionId");
		transactionNumber= jsonget(jsonObj,"TransactionNumber");
		returnVal = returnVal + "1~zm_invoiceTransactionNumber" + "~"+ transactionNumber+"|";
		returnVal = returnVal + "1~zm_customerTRXID" + "~"+ customerTRXId+"|";
		
		invoiceAttResponse=commerce.invoiceAttachment_c(customerTRXId);
		invoiceAttSucessMsg = get(invoiceAttResponse,"Message-Body");
		invoiceAttErrorMsg = get(invoiceAttResponse,"Error-Message");
		AttRequest = get(invoiceAttResponse,"Request");
			if (isnull(invoiceAttSucessMsg)){
				
			returnVal = returnVal + "1~invoiceAttachmentStatus_c" + "~"+ "Fail," +invoiceAttErrorMsg +"|";// Commented By Paresh 02/27/24 Due to invoiceAttachmentStatus_c attribute is not present in system
			returnVal = returnVal + "1~attachmentStatus_c" + "~"+ "Fail," +invoiceAttErrorMsg +"|";// Added By Paresh 02/27/24for above line
			returnVal = returnVal + "1~invoiceAttachmentStatus_c" + "~"+ "Fail," +invoiceAttErrorMsg +"|";// Commented By Paresh 02/27/24 Due to invoiceAttachmentStatus_c attribute is not present in system
			returnVal = returnVal + "1~attachmentStatus_c" + "~"+ "Fail," +invoiceAttErrorMsg +"|"; // Added By Paresh 02/27/24 for above line
			}
			else{
			returnVal = returnVal + "1~attachmentStatus_c" + "~"+"Success"+"|";
			}
		
		}
		else{
		returnVal = returnVal + "1~zm_advancedInvoiceStatus" + "~"+ FailMsg+"|";
		returnVal = returnVal + "1~zm_advancedInvoiceResponsePayload" + "~"+ ErrorMsg+"|";
		}
}else{
throwerror("Invoice is alredy created!!!");
}
return returnVal;

attachmentStatus_c