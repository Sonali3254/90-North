ret = "";

status = "";

if(currentStepForTesting_tempDisplay_t == "orderedBeingFulfilled" AND zm_sONumber == "" OR  zm_sONumber == "null" )
{
	status = "ORDER_PENDING";
}
if(currentStepForTesting_tempDisplay_t == "approved"  AND  zm_releasedToCustomerFlag == "Y" )
{
	status = "RELEASED_TO_CUSTOMER";
}
if(status_t == "REJECTED")
{
	status = "REJECTED";
}
if(status_t == "CREATED")
{
	status = "IN_PROCESS";
}
if(zm_estimationManagerInvolved == true AND zm_estimationManagerApproved == true AND currentStepForTesting_tempDisplay_t == "pending_process_bmClone_1" )
{
	status =  "ESTIMATION_COMPLETED";
}

if(status == "")
{
	resultSet =bmql("select Current_Step, Status from Status_Mapping where Current_Step = $currentStepForTesting_tempDisplay_t");

	for results in resultset
	{
		status = get(results,"Status");
	}
	
	print "status -- " + status ;
	
}


return status;