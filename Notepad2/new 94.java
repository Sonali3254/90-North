ret = "";
dval = "";
totalListPrice = 0;
parentDocNum = "";
parentDocNum1 = "";
parentDocNum2 = "";
parentDocNum3 = "";
modelListPrice = dict("float");
modelNetPrice = dict("float");
QuoteTotalPrice = dict("float");
TotalExclVATPrice = dict("float");
NetInclVATPrice = dict("float");
finalQuoteTotalPrice = dict("float");
lineVATPrice = dict("float");
ParentQty = dict("float");
lineVATValue = dict("string");
manPowerListPrice = dict("float");
manPowerNetPrice = dict("float");
manPowerNetInclPrice = dict("float");
manPowerNetExclPrice = dict("float");
manPowerNetAmtPrice = dict("float");
manPowerLineVATAmount = dict("float");
manPowerQty = dict("float");
lineVATAmtDict = dict("float");
parentListPrice = 0;
netAmountSum = 0;
finalAmount = 0;
paymentVal = "";
finalTotalAmount = 0;
previousTotal = 0;
totalQuoteValue  = 0;
totalQuoteValuePME = 0;
totalQuoteValueWorks = 0;
totalVATAmount = 0;
totalExclVAT = 0;
totalDiscVal = 0;
multiListPrice = "";
descVal = "";
totalListPri =0;
size ="";
eachLineVAT = "";
saudiQty = 0.0;
NonSaudiQty = 0.0;
cSPriceWOVAT= 0.0;
contractCost = 0.0;
totalAmtRetroFit=0.0;
totalQuoteValueWOVAT = 0;
totalDiscount = 0;
for line in transactionLine {
// Setting up value for Manpower- starts
	if(line._model_name =="Manpower" AND zm_businessLine =="Maintenance"){
		cSPriceWOVAT = cSPriceWOVAT + atof(getconfigattrvalue(line._document_number,"totalBidSellingPrice_mp"));
		contractCost = contractCost + atof(getconfigattrvalue(line._document_number,"totalCTCPrice_mp"));
			fcSPriceWOVAT = cSPriceWOVAT;	
				fcontractCost = contractCost;					
				grossMargin = fcSPriceWOVAT - fcontractCost;		
		margin = grossMargin/fcSPriceWOVAT;
		contractValue = getconfigattrvalue(line._document_number,"totalBidSellingPrice_mp");
		mROCost = fcontractCost - zamilCost_t;
		if(contractValue<>"" AND NOT isnull(contractValue)){
			fcontractValue=atof(contractValue);
		}
		mROGrossMargin = (fcontractValue - zamilCost_t)-mROCost;
		cdmargin = mROGrossMargin/(fcontractValue-zamilCost_t);
		ret = ret + "1~contractSellingPriceWoVAT_t" + "~"+ string(cSPriceWOVAT)+"|";
		ret = ret + "1~contractCost_t" + "~"+ string(contractCost)+"|";
		ret = ret + "1~grossMargin_t" + "~"+ string(grossMargin)+"|";
		ret = ret + "1~margin_t" + "~"+ string(margin)+"|";
		ret = ret + "1~contractValue_t" + "~"+ contractValue+"|";
		ret = ret + "1~mROCost_t" + "~"+ string(mROCost)+"|";
		ret = ret + "1~mROGrossMargin_t" + "~"+ string(mROGrossMargin)+"|";
		ret = ret + "1~cdmargin_t" + "~"+ string(cdmargin)+"|";

	}

		//List price
	if(zm_businessUnit=="KSA Coolcare MRO" AND zm_businessLine =="Maintenance"){
		if(line.zm_manualPrice == 0.00){
		if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(manPowerListPrice,parentDocNum,0.0);
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		previousTotal = get(manPowerListPrice,parentDocNum);
		totalNetPrice = previousTotal + line.listPrice_l;
		put(manPowerListPrice,parentDocNum,totalNetPrice);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(manPowerListPrice,parentDocNum);	
		parentListPrice = line.listPrice_l + childNetTotal;
		put(manPowerListPrice,parentDocNum,parentListPrice);
		}
		if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(manPowerNetPrice,parentDocNum,0.0);
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		previousTotal = get(manPowerNetPrice,parentDocNum);
		totalNetPrice = previousTotal + line.zm_priceNetExclVAT;
		put(manPowerNetPrice,parentDocNum,totalNetPrice);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(manPowerNetPrice,parentDocNum);	
		parentNetPrice = line.zm_priceNetExclVAT + childNetTotal;
		put(manPowerNetPrice,parentDocNum,parentNetPrice);
		}
		if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(manPowerNetInclPrice,parentDocNum,0.0);
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		previousTotal = get(manPowerNetInclPrice,parentDocNum);
		totalNetPrice = previousTotal + line.netPrice_l;
		put(manPowerNetInclPrice,parentDocNum,totalNetPrice);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(manPowerNetInclPrice,parentDocNum);	
		parentNetInclPrice = line.netPrice_l + childNetTotal;
		put(manPowerNetInclPrice,parentDocNum,parentNetInclPrice);
		}
		if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(manPowerNetExclPrice,parentDocNum,0.0);
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		previousTotal = get(manPowerNetExclPrice,parentDocNum);
		totalNetPrice = previousTotal + line.zm_amountNetExclVAT;
		put(manPowerNetExclPrice,parentDocNum,totalNetPrice);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(manPowerNetExclPrice,parentDocNum);	
		parentNetExclPrice = line.zm_amountNetExclVAT + childNetTotal;
		put(manPowerNetExclPrice,parentDocNum,parentNetExclPrice);
		}
		if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(manPowerNetAmtPrice,parentDocNum,0.0);
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		previousTotal = get(manPowerNetAmtPrice,parentDocNum);
		totalNetPrice = previousTotal + line.netAmount_l ;
		put(manPowerNetAmtPrice,parentDocNum,totalNetPrice);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(manPowerNetAmtPrice,parentDocNum);	
		parentNetExclPrice = line.netAmount_l + childNetTotal;
		put(manPowerNetAmtPrice,parentDocNum,parentNetExclPrice);
		}
				if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(manPowerLineVATAmount,parentDocNum,0.0);
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		previousTotal = get(manPowerLineVATAmount,parentDocNum);
		totalNetPrice = previousTotal + line.zm_lineVATAmount;
		ret = ret + "1~totalVATAmountForMPProposal_t" + "~"+ string(totalNetPrice)+"|";
		put(manPowerLineVATAmount,parentDocNum,totalNetPrice);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(manPowerLineVATAmount,parentDocNum);	
		parentListPrice = line.zm_lineVATAmount + childNetTotal;
		put(manPowerLineVATAmount,parentDocNum,parentListPrice);
		}
		if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(manPowerQty,parentDocNum,0.0);
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		previousqty = get(manPowerQty,parentDocNum);
		totalNetQty = previousqty + line.quantity_Manpower;
			if(startswith(line._part_number,"Saudi")){
			saudiQty = saudiQty+ line.quantity_Manpower;
			ret = ret + "1~qty_saudization" + "~"+ string(saudiQty)+"|";
			}else{
			NonSaudiQty = NonSaudiQty + line.quantity_Manpower;
			ret = ret + "1~qty_nonSaudi" + "~"+ string(NonSaudiQty)+"|";
			}		
		put(manPowerQty,parentDocNum,totalNetQty);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetQty = get(manPowerQty,parentDocNum);	
		parentQuantity = line.quantity_Manpower + childNetQty;
		put(manPowerQty,parentDocNum,parentQuantity);
		}
	}
}

// Setting up value for Manpower- ends
//==============================================================================================================================================================

if(line._part_number == "" AND zm_businessLine =="Retrofit" AND (line._model_variable_name =="manpower" OR line._model_variable_name =="additionalSiteExpenses" OR line._model_variable_name =="zACEquipment"))
{
	parentDocNum1 = line._document_number;
}
if(line._part_number <> "" AND line._parent_doc_number <>"" AND parentDocNum1 == line._parent_doc_number){	
	ret = ret + line._document_number + "~zm_labourUnitPrice" + "~" + "0" + "|";
	ret = ret + line._document_number + "~zm_labourSubTotal" + "~" + "0" + "|";
	ret = ret + line._document_number + "~zm_totalSubTotal" + "~" + "0" + "|";  
	ret = ret + line._document_number + "~zm_totalUnitPrice" + "~" + "0" + "|";
	totalAmtRetroFit = line.quantity_Manpower * line.listPrice_l;
	ret = ret + line._document_number + "~quantity_Manpower" + "~" + string(line.quantity_Manpower) + "|";
	ret = ret + line._document_number + "~zm_totalAmount_retroFit_l" + "~" + string(totalAmtRetroFit) + "|";
}

//==============================================================================================================================================================
	totalQuoteValuePME = totalQuoteValuePME + line.zm_totalAmount_retroFit_l;
	totalQuoteValueWorks = totalQuoteValueWorks + line.ZM_finalTotalSubTotal;
	totalQuoteValueWOVAT = totalQuoteValuePME + totalQuoteValueWorks;
	if(zm_discountType_retrofit == "Percent Off"){
	totalDiscount = totalQuoteValueWOVAT * zm_discount/100;
	}elif(zm_discountType_retrofit == "Fixed Price"){
	totalDiscount = zm_discount;
	}
	totalValWDiscount = totalQuoteValueWOVAT - totalDiscount;
	totalVatVal = totalValWDiscount * zm_vATpercentage/100;
	totalQuoteValue = totalValWDiscount + totalVatVal;
		if(addManualPrice_retrofit_c){
		totalQuoteValue = totalQuoteValue + manualPrice_retrofit_c;
	}
	ret = ret + "1~zm_totalValueAfterDiscount_t" + "~"+ string(totalValWDiscount)+"|";
	ret = ret + "1~zm_totalVATValue_t" + "~"+ string(totalVatVal)+"|";
	ret = ret + "1~zm_quoteValueWithVAT_t" + "~"+ string(totalQuoteValueWOVAT)+"|";
	ret = ret + "1~zm_retroFitquoteValue_t" + "~"+ string(totalQuoteValue)+"|";
//===============================================================================
if(line._part_number == "" AND zm_businessLine =="Retrofit" AND (line._model_variable_name =="electricalWorks" OR line._model_variable_name =="civilWorks" OR line._model_variable_name =="ductWorks" OR line._model_variable_name =="pipeWorks")){
	parentDocNum2 = line._document_number;
}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum2 == line._parent_doc_number){	
	ret = ret + line._document_number + "~zm_totalAmount_retroFit_l" + "~" + string(line.ZM_finalTotalSubTotal) + "|";
	//material MF and Labour MF logic begins
	if(materialMultiplier_retrofit_t){
		ret = ret + line._document_number + "~zm_materialMF" + "~" + string(zm_multiplyingFactor_t) + "|";
	}
	if(labourMultiplier_retrofit_t){
		ret = ret + line._document_number + "~zm_labourMF" + "~" + string(zm_multiplyingFactor_t) + "|";
	}
		
}
if(line._part_number == "" AND zm_businessLine =="Retrofit" AND (line._model_variable_name =="zACEquipment")){
	parentDocNum3 = line._document_number;
}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum3 == line._parent_doc_number){	
		if(line._line_item_spare_rule_var_name <> "" AND line._line_item_spare_rule_var_name <> "recItemRuleForZacEquipment"){
		ret = ret + line._document_number + "~zp_lineDescription" + "~" + line._line_item_spare_rule_var_name + "|";
	}else{
		ret = ret + line._document_number + "~zp_lineDescription" + "~" + "" + "|";
	}
}
//----------------------------------------------------------------------------------------------------------------------------------------
	if(line.zm_multifyingFactor >1){
	multiListPrice = String(line.zm_multifyingFactor * line.zm_orderListPrice);
	ret = ret + line._document_number + "~listPrice_l" + "~"+ multiListPrice +"|";
	}
	if(line.zm_manualPrice<>0){ 
		if(line.listPrice_l==line.zm_manualPrice){
		netPrice = line.listPrice_l;
		}if(line.customDiscountAmount_l<>0){
		netPrice = line.listPrice_l-line.customDiscountAmount_l;
		}
		else{
		netPrice = line.listPrice_l;
		}
		ret = ret + line._document_number + "~zm_priceNetExclVAT" + "~" + string(netPrice) + "|";
	}
	if(zm_businessLine == "SPPI"){
//----------------------------------------------------------------------------------------------------------------------------------------
// Setting few values of attributes for SPPI Proposal- starts
		cSMvalue = getconfigattrvalue(line._document_number,"zm_cSMCorePipe");
        materialType = getconfigattrvalue(line._document_number,"materialType");
        coreDia = getconfigattrvalue(line._document_number,"zm_coreDiameter");
        if(coreDia <> "" AND NOT isnull(coreDia)){
        size = coreDia + "Inch Dia";
		ret = ret + line._document_number + "~zm_cSMValue_l" + "~"+ size +"|";
		}
		if(cSMvalue=="true"){
		ret = ret + "1~pipeAndFittingsText_t" + "~"+ "CSM:Customer Supplied Material :PIPE AND FITTING Supplied by customer" +"|";
		}else{
		ret = ret + "1~pipeAndFittingsText_t" + "~"+ "" +"|";
		}
		if(materialType<>"" AND NOT isnull(materialType)){
		ret = ret + line._document_number + "~zm_materialType_l" + "~"+ materialType +"|";
		}else{
		ret = ret + line._document_number + "~zm_materialType_l" + "~"+ "" +"|";
		}
		if(line._parent_doc_number ==""){
		totalQuoteValue = totalQuoteValue + line.netAmount_l;
		totalVATAmount = totalVATAmount + line.zm_lineVATAmount;
	        totalExclVAT = totalExclVAT + line.zm_amountNetExclVAT;       
	        totalDiscVal = totalDiscVal + line.discountAmount_l;
	       //totalListPri = totalListPri + (line.listPrice_l * line.zm_quantitySPPI);
	       totalListPri = totalListPri + line.zm_amountNetExclVAT;
	       //totalExclVAT = totalListPri + totalDiscVal;
	       
		ret = ret + "1~zm_quoteValue" + "~"+ string(totalQuoteValue)+"|";
		ret = ret + "1~zm_totalVATAmount" + "~"+ string(totalVATAmount)+"|";
		ret = ret + "1~zm_totalExclVAT" + "~"+ string(totalExclVAT)+"|";
		ret = ret + "1~zm_totalDiscountValue_t" + "~"+ string(totalDiscVal)+"|";
		ret = ret + "1~zm_totalListPrice_t" + "~"+ string(totalListPri)+"|";
		}
		if(line._parent_doc_number ==""){
		modelName = line._model_name;
		recordSet=bmql("select custom_field2 from _parts where part_number = $modelName");
			for each in recordSet{
			tempUOM = get(each,"custom_field2");
			ret = ret + line._document_number + "~zm_uOM" + "~" + tempUOM + "|";
			}
				if(line._model_name == "SWP" OR line._model_name == "SWFJ" OR line._model_name == "IJP" OR line._model_name == "IJFJ"){
				descVal = "Preinsulated Pipes";
				}
				elif(line._model_name == "SWFE" OR line._model_name == "IJFE"){
				descVal = "Preinsulated Elbow";
				}
				elif(line._model_name == "SWRT" OR line._model_name == "IJRT"){
				descVal = "Preinsulated Reducing Tees";
				}
				elif(line._model_name == "CWA System" OR line._model_name == "WIDECO System" OR line._model_name == "RATMON" OR line._model_name == "ILA System"){
				descVal = "Leak Detection System";
				}
				else{
				descVal = "Coating/3 LPE";
				}
		ret = ret + line._document_number + "~DescriptionSPPI_l" + "~"+ descVal +"|";
		}
		
// Setting few values of attributes for SPPI Proposal- ends
//----------------------------------------------------------------------------------------------------------------------------------------
		if(zm_discount>=1){
		custDiscountVal = zm_discount;
		}else{
		custDiscountVal = line.customDiscountValue_l;
		}
		ret = ret + line._document_number + "~customDiscountValue_l" + "~"+ string(custDiscountVal) +"|";
//----------------------------------------------------------------------------------------------------------------------------------------
//Setting VAT% for Child line items same as Parent VAT%- starts		
		
		if(line._part_number == "" )
		{
		parentDocNum = line._document_number;
		put(lineVATValue,parentDocNum,line.zm_LinevAT_menu);
		ret = ret + parentDocNum + "~zm_LinevAT_menu" + "~" + line.zm_LinevAT_menu + "|";
		}
		if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		eachLineVAT = get(lineVATValue,parentDocNum);
		ret = ret + line._document_number + "~zm_LinevAT_menu" + "~" + eachLineVAT + "|";
		}	
//Setting VAT% for Child line items same as Parent VAT%- ends
//----------------------------------------------------------------------------------------------------------------------------------------
if(line.zm_transportationCharge <> 0 AND line.zm_manualPrice > 0.00){
	netPrice = (line.listPrice_l - line.customDiscountAmount_l) + line.zm_transportationCharge;
	
	ret = ret + line._document_number + "~zm_priceNetExclVAT" + "~" + string(netPrice) + "|";
}
	}
if(line.zm_manualPrice == 0.00){
if(zm_businessLine == "SPPI"){
if((line._part_number <> "") AND (line._parent_doc_number == "") AND (expectedMargin>0.00)){
	listPrice = (line.unitCost_l+(line.unitCost_l*(expectedMargin/100)));
}
elif((line.listPrice_l == 0.00) AND (line.zm_oMPrice <> 0.00)){
	listPrice = line.zm_oMPrice;
}
elif((line._part_number=="") AND (line._model_product_line_name =="Double Skin AHU")){
	listPrice = line.listPrice_l;
}
elif((line._part_number=="")){
	listPrice = line._price_unit_price_each;
}
elif(line.listPrice_l == 0.00){
	listPrice = line._price_unit_price_each;
}

else{
	listPrice = line.listPrice_l;
}
if(line._part_number == "" AND line._model_name <> ""){	
	parentDocNum = line._document_number;
	put(modelListPrice,parentDocNum,0.0);
	}
	if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		if(NOT isnull(get(modelListPrice,parentDocNum))){
		previousTotal = get(modelListPrice,parentDocNum);
		}
			if(line.zm_multifyingFactor >1){
			listPrice = line.zm_multifyingFactor * line.listPrice_l;
			}
		totalListPrice = previousTotal + listPrice;
		put(modelListPrice,parentDocNum,totalListPrice);
		}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childTotal = get(modelListPrice,parentDocNum);
			if(line.zm_multifyingFactor >1){
			listPrice = line.zm_multifyingFactor * line.listPrice_l;
			}		
		parentListPrice = listPrice + childTotal;
		put(modelListPrice,parentDocNum,parentListPrice);
		}
//List Price calculation ends
//=====================================================================================
//Net price calculations begins -  zm_priceNetExclVAT
if(line.zm_multifyingFactor >1){
	listPrice = line.zm_multifyingFactor * line.listPrice_l;
}

netPrice = (listPrice - line.customDiscountAmount_l) + line.zm_transportationCharge;


if(line._part_number == "" AND line.zm_manualPrice == 0.00)
{
	parentDocNum = line._document_number;
	put(modelNetPrice,parentDocNum,0.0);
}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
		if(NOT isnull(get(modelNetPrice,parentDocNum))){
		previousTotal = get(modelNetPrice,parentDocNum);
		}
		totalNetPrice = previousTotal + netPrice;
		put(modelNetPrice,parentDocNum,totalNetPrice);
	}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(modelNetPrice,parentDocNum);	
		parentNetPrice = netPrice + childNetTotal;
		put(modelNetPrice,parentDocNum,parentNetPrice);
		ret = ret + line._document_number + "~zm_priceNetExclVAT" + "~" + string(netPrice) + "|";
		}

//Net price calculations ends

//zm_LinevAT logic begins
	if(line.zm_LinevAT_menu <> ""){
	lineVAT = atof(line.zm_LinevAT_menu);
	}else{
		lineVAT = line.zm_LinevAT;
	}
//zm_LinevAT logic ends

//VAT Amount logic begins - zm_lineVATAmount
lineVATPerUnit = netPrice * ( lineVAT / 100 );
lineVATAmount = (netPrice * ( lineVAT / 100 )) * line.zm_quantitySPPI;

if(line._part_number == "" )
{
	parentDocNum = line._document_number;
	put(lineVATPrice,parentDocNum,0.0);
}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
	if(NOT isnull(get(lineVATPrice,parentDocNum))){
		previousTotal = get(lineVATPrice,parentDocNum);
	}
		totalVATPrice = previousTotal + lineVATPerUnit;
		put(lineVATPrice,parentDocNum,totalVATPrice);
	}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childVATTotal = get(lineVATPrice,parentDocNum);	
		parentVATPrice = lineVATPerUnit + childVATTotal;
		put(lineVATPrice,parentDocNum,parentVATPrice);
		}
if(line._part_number == "" )
{
	parentDocNum = line._document_number;
	put(lineVATAmtDict,parentDocNum,0.0);
}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
	if(NOT isnull(get(lineVATAmtDict,parentDocNum))){
		previousTotal = get(lineVATAmtDict,parentDocNum);
	}
		totalVATAmt = previousTotal + lineVATAmount;
		put(lineVATAmtDict,parentDocNum,totalVATAmt);
	}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childVATTotal = get(lineVATAmtDict,parentDocNum);	
		parentVATAmt = lineVATAmount + childVATTotal;
		put(lineVATAmtDict,parentDocNum,parentVATAmt);
		}
//ret = ret + line._document_number + "~zm_lineVATAmount" + "~" + string(lineVATAmount) + "|";
//VAT Amount logic ends

//Net Price - Incl VAT logic begins - netPrice_l
if(lineVAT > 0){
	netPriceInclVAT = netPrice + ( ( netPrice * lineVAT ) / 100 );
}else{
	netPriceInclVAT = netPrice;
}

ret = ret + line._document_number + "~netPrice_l" + "~" + string(netPriceInclVAT) + "|";
if(line._part_number == "" )
{
	parentDocNum = line._document_number;
	put(NetInclVATPrice,parentDocNum,0.0);
}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
	if(NOT isnull(get(NetInclVATPrice,parentDocNum))){
		previousTotal = get(NetInclVATPrice,parentDocNum);
	}
		totalPrice = previousTotal + netPriceInclVAT;
		put(NetInclVATPrice,parentDocNum,totalPrice);
	}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(NetInclVATPrice,parentDocNum);	
		parentTotalPrice = netPriceInclVAT + childNetTotal;
		put(NetInclVATPrice,parentDocNum,parentTotalPrice);
		}
//Net Price - Incl VAT logic ends

//Total - Excl VAT logic begins - zm_amountNetExclVAT
totalExclVAT = netPrice * line.zm_quantitySPPI;
ret = ret + line._document_number + "~zm_amountNetExclVAT" + "~" + string(totalExclVAT) + "|";
if(line._part_number == "" )
{
	parentDocNum = line._document_number;
	put(TotalExclVATPrice,parentDocNum,0.0);

}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
	if(NOT isnull(get(TotalExclVATPrice,parentDocNum))){
		previousTotal = get(TotalExclVATPrice,parentDocNum);
	}
		totalPrice = previousTotal + totalExclVAT;
		put(TotalExclVATPrice,parentDocNum,totalPrice);
	}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(TotalExclVATPrice,parentDocNum);	
		parentTotalPrice = totalExclVAT + childNetTotal;
		put(TotalExclVATPrice,parentDocNum,parentTotalPrice);

		}
//Total - Excl VAT logic ends

//Total - Incl VAT logic begins - netAmount_l 
//totalInclVAT = line.netPrice_l * line.zm_quantitySPPI;
totalInclVAT = 	 netPriceInclVAT * line.zm_quantitySPPI;
ret = ret + line._document_number + "~netAmount_l" + "~" + string(totalInclVAT) + "|";
if(line._part_number == "" )
{
	parentDocNum = line._document_number;
	put(QuoteTotalPrice,parentDocNum,0.0);
	put(ParentQty,parentDocNum,0.0);
}
if(line._part_number <> "" and line._parent_doc_number <>"" and parentDocNum == line._parent_doc_number){
	if(NOT isnull(get(QuoteTotalPrice,parentDocNum))){
		previousTotal = get(QuoteTotalPrice,parentDocNum);
	}
		totalPrice = previousTotal + totalInclVAT;
		put(QuoteTotalPrice,parentDocNum,totalPrice);

	}
		if(line._parent_doc_number =="" AND line._model_name <> ""){
		childNetTotal = get(QuoteTotalPrice,parentDocNum);	
		parentTotalPrice = totalInclVAT + childNetTotal;
		put(QuoteTotalPrice,parentDocNum,parentTotalPrice);
		put(ParentQty,parentDocNum,line.zm_quantitySPPI);
		}
//Total - Incl VAT logic ends

// Quote Value logic begins
if((zm_businessUnit=="KSA Coolcare Projects") OR ((zm_businessUnit=="KSA Coolcare MRO") AND (zm_businessLine=="Retrofit"))){
quoteValue = zp_fSellingPriceVATINCL;
}
elif((zm_businessUnit=="KSA Coolcare MRO") AND (zm_businessLine=="Solar")){
	quoteValue = zm_totalProjectValuesolar;
}
elif(zm_freightCharge>0.00){
	//netAmountSum = netAmountSum + line.netAmount_l;
	netAmountSum = netAmountSum + totalInclVAT;
	quoteValue = netAmountSum + zm_freightCharge;
}
else{
	//netAmountSum = netAmountSum + line.netAmount_l;
	netAmountSum = netAmountSum + totalInclVAT;
	quoteValue = netAmountSum;
}
if(line._part_number == "" )
{
	parentDocNum = line._document_number;
	put(finalQuoteTotalPrice,parentDocNum,0.0);
}
if(line._parent_doc_number =="" AND line._model_name <> ""){
		previousTotal  = get(finalQuoteTotalPrice,parentDocNum);
		//totalNetAmount = previousTotal + line.netAmount_l;
		totalQuoteAmount = previousTotal + totalInclVAT;
		put(finalQuoteTotalPrice,parentDocNum,totalQuoteAmount);
		//quoteTotal= (sum(line.netAmount_l)+zm_freightCharge);
}
//ret = ret + "1~zm_quoteValue" + "~"+ string(quoteValue)+"|";

// Quote Value logic Ends
}
}
    if(line._config_attr_info <> "")
	{
    	enterdecodingtxtpos= find(line._config_attr_info,"Enter Decoding",0);
    	edposval= substring(line._config_attr_info, enterdecodingtxtpos);
    	findedsepstart = (find(edposval,"~")+1);
    	findedsepend = find(edposval,"~",findedsepstart);
    	edval = substring(edposval, findedsepstart,findedsepend);    	
    	if(edval=="false")
    	{
    	autodecodingtxtpos= find(line._config_attr_info,"autodecoding~Decoding",0);
    	adposval= substring(line._config_attr_info, autodecodingtxtpos);
    	findadsepstart = 22;
    	findadsepend = find(adposval,"~",findadsepstart);
    	dval = substring(adposval, findadsepstart,findadsepend);    	
    	}
    	elif (edval=="true")
    	{
    	mandecodingtxtpos= find(line._config_attr_info,"decoding~Decoding",0);
    	mdposval= substring(line._config_attr_info, mandecodingtxtpos);
    	findmdsepstart = 18;
    	findmdsepend = find(mdposval,"~",findmdsepstart);
    	dval= substring(mdposval, findmdsepstart,findmdsepend);    	
    	}
    	elif (edval=="")
    	{
    	dval= "";
    	}
    	ret = ret + line._document_number + "~zm_modelDecoding" + "~" + dval + "|";
    	}
    finalListPrice = "";
	if(line.zm_multifyingFactor >1){
	finalListPrice = String(line.zm_multifyingFactor * line.zm_orderListPrice);
	ret = ret + line._document_number + "~listPrice_l" + "~"+ finalListPrice +"|";
	}
	if(line._parent_doc_number ==""){
		modelName = line._model_name;
		recordSet=bmql("select custom_field2 from _parts where part_number = $modelName");
		for each in recordSet{
		tempUOM = get(each,"custom_field2");
		ret = ret + line._document_number + "~zm_uOM" + "~" + tempUOM + "|";
		}
	}

	//if(line.zm_manualPrice == 0.00){
	if(line._model_name =="Manpower"){
	mpListpriceKeys = keys(manPowerListPrice);
		for price in mpListpriceKeys{
			if(containskey(manPowerListPrice,price)){
			listPrice = get(manPowerListPrice,price);
			}
	ret = ret + price + "~listPrice_l" + "~" + string(listPrice) + "|"; 
	}	
	mpNetpriceKeys = keys(manPowerNetPrice);
		for price in mpNetpriceKeys{
			if(containskey(manPowerNetPrice,price)){
			mpnetPrice = get(manPowerNetPrice,price);
			}
		ret = ret + price + "~zm_priceNetExclVAT" + "~" + string(mpnetPrice) + "|"; 
		}
		mpNetInclpriceKeys = keys(manPowerNetInclPrice);
		for price in mpNetInclpriceKeys{
			if(containskey(manPowerNetInclPrice,price)){
			mpnetInclPrice = get(manPowerNetInclPrice,price);
			}
		ret = ret + price + "~netPrice_l" + "~" + string(mpnetInclPrice) + "|"; 
		}
		mpNetExclpriceKeys = keys(manPowerNetExclPrice);
		for price in mpNetExclpriceKeys{
			if(containskey(manPowerNetExclPrice,price)){
			mpnetExclPrice = get(manPowerNetExclPrice,price);
			}
		ret = ret + price + "~zm_amountNetExclVAT" + "~" + string(mpnetExclPrice) + "|"; 
		}	
		mpNetAmtpriceKeys = keys(manPowerNetAmtPrice);
		for price in mpNetAmtpriceKeys{
				if(containskey(manPowerNetAmtPrice,price)){
				mpnetAmtPrice = get(manPowerNetAmtPrice,price);
				}
				ret = ret + price + "~netAmount_l" + "~" + string(mpnetAmtPrice) + "|"; 
		}
		mpLineVATAmountKeys = keys(manPowerLineVATAmount);
			for price in mpListpriceKeys{
				if(containskey(manPowerLineVATAmount,price)){
				lineVATAMt = get(manPowerLineVATAmount,price);
				}
				print "lineVATAMt";
				print lineVATAMt;
		ret = ret + price + "~zm_lineVATAmount" + "~" + string(lineVATAMt) + "|"; 
		}	
		mpqtyKeys = keys(manPowerQty);
			for qty in mpqtyKeys{
				if(containskey(manPowerQty,qty)){
				QtyVal = get(manPowerQty,qty);
				}
				print "qtyval================";
				print QtyVal;
		ret = ret + qty + "~quantity_Manpower" + "~" + string(QtyVal) + "|"; 
		}		
		
	}
	}
	//}
	print QuoteTotalPrice;
//if(line.zm_manualPrice == 0.00){
if(zm_businessLine == "SPPI"){
	netpriceKeys = keys(modelNetPrice);
	priceKeys = keys(modelListPrice);
	totpriceKeys = keys(QuoteTotalPrice);
	ExcVATKeys = keys(TotalExclVATPrice);
	NetInclKeys = keys(NetInclVATPrice);
	VATpriceKeys = keys(lineVATPrice);
	QtyKeys = keys(ParentQty);
	VATAmtkeys = keys(lineVATAmtDict);
lineQty =0;
	for price in priceKeys{
		if(containskey(modelListPrice,price)){
		listPrice = get(modelListPrice,price);

		//ret = ret + price + "~zm_uOM" + "~" + "Meter" + "|";
		ret = ret + price + "~listPrice_l" + "~" + string(listPrice) + "|"; 
		}
	/*multiListPrice = "";
		if(line.zm_multifyingFactor >1){
		multiListPrice = String(line.zm_multifyingFactor * line.zm_orderListPrice);
		ret = ret + price + "~listPrice_l" + "~"+ multiListPrice +"|";
		}*/
	}

	TotalnetPrice = 0;
	for nPrice in netpriceKeys{
		if(containskey(modelNetPrice,nPrice)){
		netPrice = get(modelNetPrice,nPrice);
			for qty in QtyKeys{
				if(containskey(ParentQty,qty)){
					lineQty = get(ParentQty,qty);		
					TotalnetPrice = netPrice * lineQty;	
					ret = ret + nPrice + "~zm_priceNetExclVAT" + "~" + string(netPrice) + "|"; 
					ret = ret + qty + "~zm_amountNetExclVAT" + "~" + string(TotalnetPrice) + "|";
				}
			}
		}

	}
	TotalnetAmt = 0;
	for nIPrice in NetInclKeys{
		if(containskey(NetInclVATPrice,nIPrice)){
		netIncPrice = get(NetInclVATPrice,nIPrice);
				for qty in QtyKeys{
					if(containskey(ParentQty,qty)){
					lineQty = get(ParentQty,qty);		
					TotalnetAmt = netIncPrice * lineQty;
					print "total net amt";
					print TotalnetAmt;
					finalAmount = finalAmount+ TotalnetAmt;
					print "final amount";
					print finalAmount;
					if(zm_freightCharge>0.00){
						finalTotalAmount = finalAmount + zm_freightCharge;
					}else{
						finalTotalAmount = finalAmount;
						
					}	
			
					ret = ret + qty + "~netAmount_l" + "~" + string(TotalnetAmt) + "|"; 
					ret = ret + qty + "~netPrice_l" + "~" + string(netIncPrice) + "|";
					ret = ret + "1~zm_quoteValue" + "~"+ string(finalTotalAmount)+"|";
					ret = ret + "1~zm_totalquotePrice" + "~"+ string(finalTotalAmount)+"|";
					}
				}

			}
		break;
	}

		for VAT in VATpriceKeys{
			if(containskey(lineVATPrice,VAT)){
			UnitlineVAT = get(lineVATPrice,VAT);
			}
		ret = ret + VAT + "~zm_unitVATAmount" + "~" + string(UnitlineVAT) + "|"; 
		}
			for VATamt in VATAmtkeys{
			if(containskey(lineVATAmtDict,VATamt)){
			UnitlineVATAmt = get(lineVATAmtDict,VATamt);
			}
			print "UnitlineVATAmt";
			print UnitlineVATAmt;
		ret = ret + VATamt + "~zm_lineVATAmount" + "~" + string(UnitlineVATAmt) + "|"; 
		}

}
//}
//}
if((zm_businessLine=="Project / Construction" OR zm_businessLine=="Duct") AND zm_allocatedEstimationExecutive==""){
	ret = ret +"|"+ commerce.getExecutionTeamUsers();
}
result="";
if(zm_businessLine=="Duct"){
	d=commerce.zm_setDuctModelTotals();
	keyArr=keys(d);
	for key in keyArr{
		data = get(d,key);
		arr = split(key,":");
		result = result + arr[0]+"~"+arr[1]+"~"+data+"|";
	}
}

ret = ret + result;

return ret;