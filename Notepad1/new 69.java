prefix ="";
if(orderType == "Atshana Export Order-Quote" OR orderType == "Atshana Org Quotation"){
	prefix = string(1000000) + string(_system_transaction_counter_value);
}
if(orderType == "Dhaban Export Order-Quote" OR orderType == "Dhaban Org Quotation"){
	prefix = string(3000000) + string(_system_transaction_counter_value);
}
if(orderType == "Abha Export Order-Quote" OR orderType == "Abha Org Order-Quote"){
	prefix = string(4000000) + string(_system_transaction_counter_value);
}
if(orderType == "Mekkah Export Order-Quote" OR orderType == "Mekkah Org Quotation"){
	prefix = string(5000000) + string(_system_transaction_counter_value);
}
if(orderType == "Summan Export Order-Quote" OR orderType == "Summan Org Quotation"){
	prefix = string(6000000) + string(_system_transaction_counter_value);
}
if(orderType == "Arar Export Order-Quote" OR orderType == "Arar Org Order-Quote"){
	prefix = string(7000000) + string(_system_transaction_counter_value);
}


return prefix;