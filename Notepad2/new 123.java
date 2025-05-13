widthProfileArr = range(framesProfilecontroller);
gableWidthArr = range(gableArraySize);
ret = String []; 
for each in gableWidthArr {
fGable = gableNo[each];
fGable1 = atoi(substring(fGable, 6));
if(widthprofile[each] <> "" AND gableWidthM[each] <> ""){
	if(atof(widthprofile[each]) > atof(gableWidthM[fGable1 - 1])){
		return true;
	}
}
}
return false;


widthProfileArr = range(framesProfilecontroller);
//gableWidthArr = range(gables);
gableWidthArr = range(gableArraySize);
ret = String []; 
for each in widthProfileArr {
//fGable = gableNo[each];
fGable = gableNo_steel[each];
fGable1 = atoi(substring(fGable, 6));
if(widthprofile[each] <> "" AND gableWidthM[each] <> ""){
	if(atof(widthprofile[each]) > atof(gableWidthM[fGable1 - 1])){
		ret[each] = widthprofile[each];
	}
}
}
return ret;