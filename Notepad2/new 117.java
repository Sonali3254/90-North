ret="";
positionplus=0;
separator1=" + ";
positionhash=0;
separator2=" # ";
owned="";
detail="";
lineid="";
promiseddate = "";
requestedShipDate = "";
quantity = "";
mfgLeadTime = "";
mfgLeadDays = "";	
totalListPrice = 0;
multiListPrice = "";
uomArr = string[];
OTArr = string[];
parentDocNum = "";
for line in transactionLine{
	if(line.zm_multifyingFactor>1){
	multiListPrice = String(line.zm_multifyingFactor * line.zm_orderListPrice);
	ret = ret + line._document_number + "~listPrice_l" + "~"+ multiListPrice +"|";
	}
		if(zm_salesOrderStatus =="SUCCESS"){
				if(line.zm_itemAssignment=="Yes"){
			ret = ret + line._document_number + "~zm_sOLineStatus" + "~"+ transactionID_t +"|";
			}		else{
			ret = ret + line._document_number + "~zm_sOLineStatus" + "~"+ "" +"|";
		}
		}
		
// Added by Paresh

if(zm_businessLine == "PEB" OR zm_businessLine == "SSD"){
if(line._model_name == "SSD"){
	totalqty =  getconfigattrvalue(line._document_number, "totalQuantity_SSD");
		ret = ret + line._document_number + "~costQTY_ssd_c" + "~" + totalqty + "|";
	costUsd=  getconfigattrvalue(line._document_number, "grandTotal_SSD");
		ret = ret + line._document_number + "~totalCostusd_ssd_c" + "~" + costUsd + "|";
	sellingUsd =  getconfigattrvalue(line._document_number, "sellingGrandTotalUSD_SSD");
		ret = ret + line._document_number + "~totalSellingUSD_ssd_c" + "~" + sellingUsd + "|";
}
}
if(zm_businessLine == "SRWF"){
	if(line.zm_manualPrice<>0){
		ret = ret + line._document_number + "~listPrice_l" + "~" + string(line.zm_manualPrice) + "|";
	}
}
if(zm_businessUnit=="KSA Coolcare MRO" AND (zm_businessLine =="Maintenance" OR zm_businessLine =="Retrofit")){
	firstSplit = split(line._line_item_comment,"^N^");
	count = range(sizeofarray(firstSplit));
	for each in count{
		combinedArr = split(firstSplit[each],"###");
		qtyArr = split(combinedArr[0],"@@@");
		priceArr = split(combinedArr[1],"@@@");
		if(zm_businessLine =="Maintenance"){
		OTArr = split(combinedArr[2],"@@@");
		}
		if(zm_businessLine =="Retrofit"){
		uomArr = split(combinedArr[2],"@@@");
		}
			if(line._part_number == "" AND zm_businessLine =="Retrofit" AND (line._model_name =="Electrical Works")){
			   parentDocNum = line._document_number;
			   ret = ret + line._document_number + "~zm_uOM" + "~" + "" + "|";
			}

			if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){	
			   uomArr = split(combinedArr[3],"@@@");
			   if(NOT isnull(uomArr)){
					for uom in uomArr{
						if(uom <>""){
							   ret = ret + line._document_number + "~zm_uOM" + "~" + uom + "|";
						}
					}
				}
			}
			if(line._parent_doc_number==""){
				ret = ret + line._document_number + "~quantity_Manpower" + "~" + "1" + "|";
			}else{
				for qty in qtyArr{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~quantity_Manpower" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number==""){
				ret = ret + line._document_number + "~zm_uOM" + "~" + "" + "|";
			}
			elif(NOT isnull(uomArr))
			{	
				for uom in uomArr{
					if(uom <>""){
						   ret = ret + line._document_number + "~zm_uOM" + "~" + uom + "|";	
					}
				}
			}
			if(line._parent_doc_number==""){
				ret = ret + line._document_number + "~zm_cTCRateManpower_l" + "~" + "" + "|";
			}
			elif(NOT isnull(priceArr))
			{
					for ctc in priceArr{
						if(ctc <> "null" and ctc <> ""){
			ret = ret + line._document_number + "~zm_cTCRateManpower_l" + "~" + ctc + "|";
			ret = ret + line._document_number + "~zm_materialUnitPrice" + "~" + ctc + "|";
						}
				}
			}

				if(line._parent_doc_number==""){
				ret = ret + line._document_number + "~zm_overtimeHour_l" + "~" + "0" + "|";
			}
			elif(NOT isnull(OTArr))
			{
				for OT in OTArr{
					if(NOT isnull(OT)){
						if(zm_businessLine =="Maintenance"){
			ret = ret + line._document_number + "~zm_overtimeHour_l" + "~" + OT + "|";
						}else{
							ret = ret + line._document_number + "~zm_overtimeHour_l" + "~" + "0" + "|";
						}
					}
				}
			}
	}
			
}
if(zm_businessLine == "SPPI"){
	if(line._parent_doc_number==""){
		ret = ret + line._document_number + "~zm_quantitySPPI" + "~" + string(line.zm_quantitySPPI) + "|";
		if(line._model_name<>""){
		transCost = getconfigattrvalue(line._document_number, "zm_transportationCharge");
	ret = ret + line._document_number + "~zm_transportationCharge" + "~" + transCost + "|";
		}
	}
	else{
	ret = ret + line._document_number + "~zm_quantitySPPI" + "~" + line._line_item_comment + "|";
	}
}else{
ret = ret + line._document_number + "~zm_quantitySPPI" + "~" + string(line.zm_quantitySPPI) + "|";
}

		zamilCost = getconfigattrvalue(line._document_number, "zamilCost_mp");
		if(NOT isnull(zamilCost) AND zamilCost <> ""){
		ret = ret + "1~zamilCost_t" + "~"+ zamilCost +"|";
		}

// End for Quantity logic
if(line._part_number<> "" AND line._parent_doc_number<>"")
{
	warranty = line.zm_warranty;
	if(warranty<>""){
	ret = ret + line._document_number + "~line.zm_warranty"  + "~" + warranty + "|";				
}
}
if(line.zm_requestedShipDateLeadDays==0){
	whereClause = line._part_custom_field9;
	whereClause2 = line.zm_manufacturingPlant;
	results = bmql("select MFG_LEAD_TIME from Item_Create_Val where Category_Name = $whereClause AND MFG_Plant = $whereClause2");
		for r in results{
			mfgLeadTime = get(r,"MFG_LEAD_TIME");
			//ret = ret + line._document_number + "~zm_requestedShipDateLeadDays" + "~" + mfgLeadTime + "|";	
		}
		if(mfgLeadTime=="0" OR mfgLeadTime==""){
		mfgLeadDays = "1";
		}else{
		mfgLeadDays = mfgLeadTime;
		}
}
if(
(line.zm_promisedDate=="")
AND (line.zm_promisedDateLeadDays<>0)
)
{
promiseddate=datetostr(adddays(getdate(), line.zm_promisedDateLeadDays));
ret = ret + line._document_number + "~zm_promisedDate" + "~" + promiseddate + "|";
}
//if((line.zm_requestedShipDate=="")AND (line.zm_requestedShipDateLeadDays<>0)){
if(line.zm_requestedShipDate=="" OR line.zm_requestedShipDate < line.zm_promisedDate){
	if(mfgLeadDays=="1" AND line.zm_promisedDate <> "" AND (line.zm_requestedShipDate=="" OR line.zm_requestedShipDate < line.zm_promisedDate)){
		ret = ret + line._document_number + "~zm_requestedShipDate" + "~" + line.zm_promisedDate + "|";
	}
	elif(line.zm_requestedShipDateLeadDays==1){
		ret = ret + line._document_number + "~zm_requestedShipDate" + "~" + line.zm_promisedDate + "|";
	}else{
		requestedShipDate = datetostr(adddays(getdate(), line.zm_requestedShipDateLeadDays));	
		ret = ret + line._document_number + "~zm_requestedShipDate" + "~" + requestedShipDate + "|";	
	}
}

		elif(
		(zm_businessUnit=="KSA Coolcare Services BU") 
		AND (zm_businessLine=="Maintenance")
		AND (line._model_name=="")
		AND (line._part_number<> "")
		AND (line._parent_doc_number<>"")
		)
		{
		ret = ret + line._document_number + "~zm_mROUnitCost" + "~" + line._line_item_comment + "|";
		}
			elif(
			(zm_businessUnit=="KSA Coolcare Services BU") 
			AND (zm_businessLine=="Coils")
			AND (line._model_name=="")
			AND (line._part_number<> "")
			AND (line._parent_doc_number<>"")
			)
			{
			ret = ret + line._document_number + "~customerItemName_l" + "~" + line._line_item_comment + "|";
			}
				elif(
				(zm_businessUnit=="KSA Projects BU") 
				AND (zm_businessLine=="Projects / Construction")
				AND (line._model_name=="")
				AND (line._part_number<> "")
				AND (line._line_item_comment<>"")
				)
				{
				positionplus=find(line._line_item_comment,separator1,1);
				owned=substring(line._line_item_comment, 0, positionplus);
				positionhash=find(line._line_item_comment,separator2,positionplus);
				detail=substring(line._line_item_comment, positionplus+3, positionhash);
				lineid=substring(line._line_item_comment, positionhash+3);

				ret= ret + line._document_number + "~zp_onwedBy" + "~" + owned + "|" +
				line._document_number + "~zp_lineDetails" + "~" + detail + "|" +
				line._document_number + "~zp_lineID" + "~" + lineid+ "|";
				}
				
	if(zm_businessUnit=="KSA Zamil Steel PEB" AND (zm_businessLine =="PEB")){
	firstSplit = split(line._line_item_comment,"^N^");
	count = range(sizeofarray(firstSplit));
	print firstSplit;
	for each in count{
		combinedArr = split(firstSplit[each],"###");
		discWeight= split(combinedArr[0],"@@@");
		unDiscWeight= split(combinedArr[1],"@@@");		
		totalWeightkg= split(combinedArr[2],"@@@");
		totalWeightkgPer= split(combinedArr[3],"@@@");
		discEstimated= split(combinedArr[4],"@@@");
		unDiscEstimated= split(combinedArr[5],"@@@");
		ExWorkPrice= split(combinedArr[6],"@@@");
		ExWorkPricePer= split(combinedArr[7],"@@@");
		
		if(line._parent_doc_number <> ""){
				for qty in discWeight{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_discoubtableWeightKG_c_l_c" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number <> ""){
				for qty in unDiscWeight{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_unDiscountableWeightKG_l_c" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number <> ""){
				for qty in totalWeightkg{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_totalWeightKG_l_c" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number <> ""){
				for qty in totalWeightkgPer{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_weightKG_l_c" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number <> ""){
				for qty in discEstimated{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_discountableEstPrice_l_c" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number <> ""){
				for qty in unDiscEstimated{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_unDiscountableEstPrice_l_c" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number <> ""){
				for qty in ExWorkPrice{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_totalExworksPrice_l_c" + "~" + qty + "|";
					}
				}
			}
			if(line._parent_doc_number <> ""){
				for qty in ExWorkPricePer{
					if(NOT isnull(qty)){
			ret = ret + line._document_number + "~peb_ExworksPrice_l_c" + "~" + qty + "|";
					}
				}
			}
		}
		
	}


}

if(zm_estimatorAssigned==false AND _system_user_groups=="estimationManager"){
	ret = ret + "|1~zm_estimatorAssigned~true";
}
/*temp = commerce.getDatafromConfigforSSDandPEB_c();
ret = ret + temp;*/
return ret;