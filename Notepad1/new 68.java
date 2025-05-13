transactionNumberSB = stringbuilder();
counterVal = 0;
currCounter = 0;
bsID = "";
companyName = "";
businessUnit = "";
if(USG_companyName == "Gerflor Middle East Co. Ltd" OR USG_companyName == "Faysal Al Bassam General Trading"){
	companyName = USG_companyName;
}else{
	companyName = "ALL";
}
if( transactionID_t == ""){
resultSet = BMQL("SELECT BS_ID,COUNTER,BU FROM TransactionCounter WHERE BU = $companyName ORDER BY COUNTER Desc ");
//print resultSet;
for each in resultSet{
	currCounter = atoi(get(each,"COUNTER"));
	bsID = get(each,"BS_ID");
	businessUnit = get(each,"BU");
	//print currCounter;
	//print counterVal;

	print "dbbsID ---"+bsID;
	print "TrxID ---"+bs_id;
	if(bsID <>bs_id AND version_t==1){
	//print "Inside";
		counterVal = currCounter+1;
	}
	print "counterVal "+string(counterVal);
	break;
}
set1 = "BU ='"+companyName+"' ,COUNTER='"+string(currCounter)+"'";
lang = dict("string");
fields = dict("string");


if(bsID <> bs_id AND counterVal>0){
	x_var = counterVal;
	a_var = bs_id;
	b_var = companyName;
		resultSet = BMQL("insert into TransactionCounter (COUNTER, BS_ID, BU) values ($x_var, $a_var, $b_var)");
	for result in resultSet  {
		insert_count_integer = get(result, "records_inserted");
		insert_count_integer = get(result, "records_updated");
		records_error_string = get(result, "records_error");
    }
		if(companyName == "Gerflor Middle East Co. Ltd" ) {
			sbappend(transactionNumberSB,"1~transactionID_t~","CPQ-GME-",string(counterVal));
		}elif(companyName == "Faysal Al Bassam General Trading"){
			sbappend(transactionNumberSB,"1~transactionID_t~","CPQ-FBGT-",string(counterVal));
		}else{
			sbappend(transactionNumberSB,"1~transactionID_t~","CPQ-",string(counterVal));
		}
}
}
return sbtostring(transactionNumberSB);