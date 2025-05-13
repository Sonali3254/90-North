retStr = "";
//200847229
//200984895
//200876929
//256758914 prod

retStr = "<style>table, th, td {border: 1px solid;font-size: 9px;font-family: Verdana;border-color: #cccccc;}</style>";
retStr = retStr + "<table width='140%' border='1' style='border-collapse:collapse;'>";
retStr = retStr + "<tr bgcolor='#D8eacb'>";
retStr = retStr + "<td width='10%' align='center' height='25'><strong>Seq No</strong></td>";
retStr = retStr + "<td width='10%' align='center' height='25'><strong>Tag No</strong></td>";
retStr = retStr + "<td width='10%' align='center'><strong>Model Decoding</strong></td>";
retStr = retStr + "<td width='20%' align='center'><strong>Option Type </strong></td>";
retStr = retStr + "<td width='8%' align='center'><strong>Decoding </strong></td>";
retStr = retStr + "<td width='20%' align='center'><strong>Option Name</strong></td>";
retStr = retStr + "<td width='10%' align='center'><strong>BOM Cost</strong></td>";
retStr = retStr + "<td width='10%' align='center'><strong>Config List Price</strong> </td>";
retStr = retStr + "<td width='10%' align='center'><strong>List Price</strong> </td>";
retStr = retStr + "<td width='10%' align='center'><strong>Discount %</strong> </td>";
retStr = retStr + "<td width='10%' align='center'><strong>Net Price</strong> </td>";
retStr = retStr + "<td width='5%' align='center'><strong>Qty</strong> </td>";
retStr = retStr + "<td width='10%' align='center'><strong>Total Price Excl VAT</strong> </td>";
retStr = retStr + "<td width='10%' align='center'><strong><font color='red'>Expected Margin</font></strong> </td>";
retStr = retStr + "</tr>";

retStrTbl = "";

arrOOVar = string[];
arrOOVarNew = string[];

PriceListId = "20232";

grand_total_exp_margin = 0;

if(zm_businessLine=="Applied" )
{
	for line in transactionLine 
	{
		if(line._parent_doc_number=="" )
		{
			//print line._part_number ;
			
			tag_no = "";
			model_decoding = "";
			
			bom_price = "0";
			total_bom_price =0;
			total_exp_margin = 0;
			
			
			
			retStr = retStr + "<tr>";
			
			if(line._model_name<> "" and line._model_product_line_name <> "Double Skin AHU" )
			{
				//print line.zm_modelDecoding;
				retStr = retStr + "<td width='10%' align='center'>" + line._group_sequence_number+ "</td>";
				retStr = retStr + "<td width='10%'>" + line.zm_tagNumber+ "</td>";
				retStr = retStr + "<td width='10%'>" + line.zm_modelDecoding + "</td>";
				//retStr = retStr + "<td>" + line._config_attr_info+ "</td>";
				
				retStr = retStr + "<td colspan='5'>" ;
				
				model_decoding = line.zm_modelDecoding;
				
				arrConfigPrice =  split(line._price_component_prices,"~");	
				
				indices=range(sizeofarray(arrConfigPrice));				
				//print "index :" + indices;
				
				myarrayDesc = string[];
				myarrayPrice = string[];
				myarrayVar = string[];
				
				
				for i in indices
				{ 
				 	//print "xxx = "  + arrConfigPrice[i];
				 	
				 	
				 	if(i% 2 == 0)
				 	{
				 		if(arrConfigPrice[i] == "")
				 		{
				 			res = append(myarrayDesc,"Base Model");
				 			//res = append(myarrayVar,"Base Model");
				 		}
				 		elif(arrConfigPrice[i] <> "")
				 		{
				 			res = append(myarrayDesc,arrConfigPrice[i]);
				 		}	
				 		//print myarrayDesc;
				 	}
				 	elif(i% 2 <> 0)
				 	{
				 		if(arrConfigPrice[i] == "")
				 		{
				 			res2 = append(myarrayPrice,"Base Model");
				 		}
				 		elif(arrConfigPrice[i] <> "")
				 		{
				 			res2 = append(myarrayPrice,arrConfigPrice[i]);
				 		}	
				 		//print myarrayPrice;
				 	}
				 	
				 	
				}
					
				
				indicesDesc=range(sizeofarray(myarrayDesc));
				//print indicesDesc;
				
				// Price of std and other Options 
				/*for j in indicesDesc
				{
					print " Price Array = Option Name = " + myarrayDesc[j] + " // Price = " + myarrayPrice[j];
				}*/
				
				//count = range(sizeofarray(arrCongigPrice));
				//print "Count ========== > " + count ;
				
				
				arrConfigData = split(line._config_attr_info,"|^|");
				retStrTbl = ""; //<td>Var</td>
				retStrTbl = "<table width='100%' border='1' style='border-collapse:collapse;'>";
		
				
				// for base model	
				for j in indicesDesc
				{
					
					//print "Option Config Price = " + myarrayDesc[j] + " // " + myarrayPrice[j];
					if(myarrayDesc[j] == "Base Model")
					{
						retStrTbl = retStrTbl + "<tr bgcolor='#C2e9f5'>";				
						retStrTbl = retStrTbl + "<td height='20' width='20%'>Base Model</td>";
						retStrTbl = retStrTbl + "<td width='8%' align='center'>"+line._model_name+"</td>";
						retStrTbl = retStrTbl + "<td width='20%'>-</td>";
						bom_price = "0";
						
						//bom_price = commerce.getBomPrice_c(_model_name, PVarName, Decoding, PriceListId)
							
						bom_price = commerce.getBomPrice_c(line._model_name, "BM", line._model_name, PriceListId);
						
						print "Base Model BOM PRICE = " + bom_price;
						
						total_bom_price = total_bom_price + atof(bom_price);
							
						
						retStrTbl = retStrTbl + "<td width='10%' align='right'>"+bom_price+"</td>"; // BOM COst
						retStrTbl = retStrTbl + "<td width='10%' align='right'>"+myarrayPrice[j]+"</td>";
						//retStrTbl = retStrTbl + "<td width='5%' align='right'>"+string(line.requestedQuantity_l)+"</td>";
						retStrTbl = retStrTbl + "</tr>";
					}
				}
				
				//for std options and oo
				for ArrOpts in arrConfigData
				{				
					
					//print ArrOpts + "\n";
					
					arrOptsData = split(ArrOpts, "~");
					
					if(arrOptsData[0] <> "_config_upgrade_name" and arrOptsData[0] <> "_configuration_id" and arrOptsData[0] <> "enterDecoding" and arrOptsData[0] <> "decoding" and arrOptsData[0] <> "autodecoding" and arrOptsData[0] <> "eZacTemplate" and arrOptsData[0] <> "eZacDetails"  ) // and arrOptsData[0] <> "OptionalAccessories" and len(arrOptsData[2])<=2 
					{
						
						
						if(arrOptsData[2] <> "NONE" AND len(arrOptsData[2])<=2 AND arrOptsData[0] <> "OptionalAccessories" AND arrOptsData[0] <> "otherOptions" )
						{
							bom_price = "0";
							
							retStrTbl = retStrTbl + "<tr>";
							
							print "VAR NAME / arrOptsData[0]  : " + arrOptsData[0] + "\n";
							print "VAR DESC / arrOptsData[1]  : " + arrOptsData[1] + "\n";
							print "DECODING / arrOptsData[2]  : " + arrOptsData[2] + "\n";
							
							//bom_price = commerce.getBomPrice_c(_model_name, PVarName, Decoding, PriceListId)
							
							bom_price = commerce.getBomPrice_c(line._model_name, arrOptsData[0], arrOptsData[2], PriceListId);
							
							//print "BOM PRICE = " + bom_price;
							
							total_bom_price = total_bom_price + atof(bom_price);
							
							//retStrTbl = retStrTbl + "<td width='20%'>" + arrOpts[0]+ "</td>";
							retStrTbl = retStrTbl + "<td width='20%' height='20'>" + arrOptsData[1]+ "</td>"; //Option Type
							retStrTbl = retStrTbl + "<td width='8%' align='center'>" + arrOptsData[2]+ "</td>"; // Decoding
							retStrTbl = retStrTbl + "<td width='20%'>" + arrOptsData[3]+ "</td>"; //Option Name
							retStrTbl = retStrTbl + "<td width='10%' align='right'>"+bom_price+"</td>"; //BOM
							
							tempX ="";
							index =0;
							
							bm_price = "";
							std_opt_price = "";
							
							retStrTbl = retStrTbl + "<td width='10%' align='right'>"; // Config List Price 
							
							//for fetching List Price
							
							for j in indicesDesc
							{
								//print "Option Desc = " + myarrayDesc[j];
								tempX = arrOptsData[1] + " - " + arrOptsData[3];
								std_opt_price = "";
								
								if(tempX == myarrayDesc[j])
								{								
								
									//print tempX+ " // " + myarrayDesc[j]+ " // price = " + myarrayPrice[j]  ;
									
									std_opt_price = myarrayPrice[j];
									
									retStrTbl = retStrTbl + std_opt_price;
								}								
								/*elif(myarrayDesc[j] == "Base Model")
								{
									retStrTbl = retStrTbl + myarrayPrice[j];
								}*/
								elif(tempX <> myarrayDesc[j])
								{
									retStrTbl = retStrTbl + std_opt_price;
								}
								
								
							}
							retStrTbl = retStrTbl + "</td>";							
							retStrTbl = retStrTbl+ "</tr>";
							
						} //end std options
						
						//===================other options ============
						
						if(arrOptsData[0] == "OptionalAccessories" OR arrOptsData[0] == "otherOptions" ) // arrOptsData[2] <> "NONE" AND len(arrOptsData[2])>1 AND
						{							
							
							print "VAR NAME / arrOptsData[0]  : " + arrOptsData[0] + "\n";
							print "VAR DESC / arrOptsData[1]  : " + arrOptsData[1] + "\n";
							print "DECODING / arrOptsData[2]  : " + arrOptsData[2] + "\n";
							print "OO NAME / arrOptsData[3]  : " + arrOptsData[3] + "\n";
							
							arrOOData = split(arrOptsData[2], ",");							
							indicesOO = range(sizeofarray(arrOOData));				
							//print "index :" + indicesOO ;
							
							arrOOName = split(arrOptsData[3], ",");	
				
				
				
							for k in indicesOO
							{ 
								bom_price = "0";
								
								print "OO index = " + arrOOData[k] + " // " + arrOOName[k] ;
								
								//bom_price = commerce.getBomPrice_c(_model_name, PVarName, Decoding, PriceListId)
								bom_price = commerce.getBomPrice_c(line._model_name, arrOptsData[0], arrOOData[k], PriceListId);
							
								//print "BOM PRICE = " + bom_price;
								
								total_bom_price = total_bom_price + atof(bom_price);
									
								retStrTbl = retStrTbl + "<tr>";
								retStrTbl = retStrTbl + "<td width='20%' height='20'>" + arrOptsData[1]+ "</td>"; //Option Type
								retStrTbl = retStrTbl + "<td width='8%' align='center'>" + arrOOData[k]+ "</td>"; // Decoding
								retStrTbl = retStrTbl + "<td width='20%'>" + arrOOName[k]+ "</td>"; //Option Name
								retStrTbl = retStrTbl + "<td width='10%' align='right'>"+bom_price+"</td>"; //BOM
								
								retStrTbl = retStrTbl + "<td width='10%' align='right'>";
								
								//for fetching price
								for j in indicesDesc
								{
									//print "Option Desc = " + myarrayDesc[j];
									tempX = arrOptsData[1] + " - " + arrOOName[k];
									std_opt_price = "";
									
									if(tempX == myarrayDesc[j])
									{								
									
										//print tempX+ " // " + myarrayDesc[j]+ " // price = " + myarrayPrice[j]  ;
										
										std_opt_price = myarrayPrice[j];
										
										retStrTbl = retStrTbl + std_opt_price;
									}
									elif(tempX <> myarrayDesc[j])
									{
										retStrTbl = retStrTbl + std_opt_price;
									}
									
									
								}
								
								retStrTbl = retStrTbl + "</td>";							
								retStrTbl = retStrTbl+ "</tr>";
								
							}
							
						}
						
						//==================end other options =========
						
						
					}
					
					//=================================other options=======================
					
					
					//=================================end other options=======================
					
					
					
					//retStrTbl = retStrTbl+ "</tr>";
				}
				
				
				
				//TOTaL AMOUNT std
				
				retStrTbl = retStrTbl + "<tr bgcolor='#Fbf1ca'>";				
				retStrTbl = retStrTbl + "<td height='20' colspan='3'><strong>Total Price </strong> </td>";	
				//retStrTbl = retStrTbl + "<td height='20'>-</td>";
				retStrTbl = retStrTbl + "<td height='20' width='10%' align='right'><strong>" + formatascurrency(total_bom_price) + "</strong></td>"; // BOM total			
				retStrTbl = retStrTbl + "<td width='10%' align='right'><strong>" + formatascurrency(line._price_configurable_price) + "</strong></td>";
				retStrTbl = retStrTbl + "</tr>";
						
				
				retStrTbl = retStrTbl+ "</table>";
				
				retStr = retStr + retStrTbl+ "</td>";
				
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.listPrice_l) + "</strong></td>"; //List Price Actual
				retStr = retStr + "<td align='center'><strong>" + string(line.customDiscountValue_l) + "</strong></td>"; //Discount Per
				retStr = retStr + "<td align='center'><strong>" + formatascurrency(line.zm_priceNetExclVAT) + "</strong></td>"; //Discount Price
				retStr = retStr + "<td align='center'><strong>" + string(line.requestedQuantity_l) + "</strong></td>"; //config qty
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.zm_amountNetExclVAT) + "</strong></td>"; //Total Price Excl VAT
				
				total_exp_margin =  (line.zm_priceNetExclVAT - total_bom_price ) * line.requestedQuantity_l ;
				
				grand_total_exp_margin = grand_total_exp_margin + total_exp_margin;
				
				retStr = retStr + "<td align='right'><strong><font color='red'>" + formatascurrency(total_exp_margin) + "</font></strong></td>"; // Expected Margin
				
				//print formatascurrency(line.zm_amountNetExclVAT);
			}
			
			//============================================================================================================================================
			
			if(line._model_name == "" and line._part_number <> "Miscellaneous" )
			{
				retStrTbl = retStrTbl + "<tr>";
				
				//print line._part_number ;
				retStr = retStr + "<td width='10%' height='20' align='center'>" + line._group_sequence_number+ "</td>";
				retStr = retStr + "<td width='10%' >" + line.zm_tagNumber+ "</td>";
				retStr = retStr + "<td width='10%'>" + line._part_number + "</td>";
				retStr = retStr + "<td colspan='4'><strong>Total Price </strong></td>";
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.listPrice_l) + "</strong></td>"; //List Price
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.listPrice_l) + "</strong></td>"; //List Price Actual
				retStr = retStr + "<td align='center'><strong>" + string(line.customDiscountValue_l) + "</strong></td>"; //Discount
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.zm_priceNetExclVAT) + "</strong></td>"; //Discount Price
				retStr = retStr + "<td align='center'><strong>" + string(line.requestedQuantity_l) + "</strong></td>"; //qty
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.zm_amountNetExclVAT) + "</strong></td>"; //Total Price Excl VAT
				retStr = retStr + "<td align='right'><strong>-</strong></td>"; // Expected Margin
				
				retStrTbl = retStrTbl+ "</tr>";
			}	
			
			//============================================================================================================================================
			
			if(line._model_name == "" and line._part_number == "Miscellaneous" )
			{
				retStrTbl = retStrTbl + "<tr>";
				
				//print line._part_number ;
				retStr = retStr + "<td width='10%' height='20' align='center'>" + line._group_sequence_number+ "</td>";
				retStr = retStr + "<td width='10%' >" + line.zm_tagNumber+ "</td>";
				retStr = retStr + "<td width='10%'>" + line.zp_lineDescription + " ("+ line._part_number + ")</td>";
				retStr = retStr + "<td colspan='4'><strong>Total Price </strong></td>";
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.listPrice_l) + "</strong></td>"; //List Price
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.listPrice_l) + "</strong></td>"; //List Price Actual
				retStr = retStr + "<td align='center'><strong>" + string(line.customDiscountValue_l) + "</strong></td>"; //Discount
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.zm_priceNetExclVAT) + "</strong></td>"; //Discount Price
				retStr = retStr + "<td align='center'><strong>" + string(line.requestedQuantity_l) + "</strong></td>"; //Qty
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.zm_amountNetExclVAT) + "</strong></td>"; //Total Price Excl VAT
				retStr = retStr + "<td align='right'><strong>-</strong></td>"; // Expected Margin
				
				retStrTbl = retStrTbl+ "</tr>";
			}
			
			//============================================================================================================================================
			
			if(line._model_product_line_name== "Double Skin AHU" )
			{
				retStrTbl = retStrTbl + "<tr>";
				
				//print line._part_number ;
				retStr = retStr + "<td width='10%' height='20' align='center'>" + line._group_sequence_number+ "</td>";
				retStr = retStr + "<td width='10%' >" + line.zm_tagNumber+ "</td>";
				retStr = retStr + "<td width='10%'>" + line.zm_aircalc_aircalcModel+ "</td>";
				retStr = retStr + "<td colspan='4'><strong>Total Price</strong></td>";
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.listPrice_l) + "</strong></td>"; //List Price
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.listPrice_l) + "</strong></td>"; //List Price Actual
				retStr = retStr + "<td align='center'><strong>" + string(line.customDiscountValue_l) + "</strong></td>"; //Discount
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.zm_priceNetExclVAT) + "</strong></td>"; //Discount Price
				retStr = retStr + "<td align='center'><strong>" + string(line.requestedQuantity_l) + "</strong></td>"; //qty
				retStr = retStr + "<td align='right'><strong>" + formatascurrency(line.zm_amountNetExclVAT) + "</strong></td>"; //Total Price Excl VAT
				retStr = retStr + "<td align='right'><strong>-</strong></td>"; // Expected Margin
				
				retStrTbl = retStrTbl+ "</tr>";
			}
			
			//============================================================================================================================================
			
			//retStr = retStr + "</tr>";
		}	
	}

}


retStr = retStr + "</table>";

print " GRAND TOTAL = " + string(grand_total_exp_margin);

retStr = retStr + "<br><table width='140%' border='1' style='border-collapse:collapse;'>";
retStr = retStr + "<tr bgcolor='#D8eacb'>";
retStr = retStr + "<td colspan='13' height='20'><strong>Grand Total (Expected Margin):</strong></td>";
retStr = retStr + "<td align='right'><strong><font color='red'>" + formatascurrency(grand_total_exp_margin) + "</font></strong></td>"; // Expected Margin
retStr = retStr + "</tr>";
retStr = retStr + "</table>";


return retStr;