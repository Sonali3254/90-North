ret = String []; 
frameProfileArr = range(framesProfilecontroller);
d = dict("string");
for each in frameProfileArr{
if(containskey(d, gableNo[each])){
v = get(d,gableNo[each]);
put(d,gableNo[each], string(atof(v)+atof(widthprofile[each])));
}
else{
put(d,gableNo[each],widthprofile[each]);
}
}

gableArr = range(gableArraySize);
d1 = dict("string");
for each in gableArr{
if(containskey(d1, gableNo_steel[each])){
v = get(d1,gableNo_steel[each]);
put(d1,gableNo_steel[each], string(atoi(v)+atof(gableWidthM[each])));
}
else{
put(d1,gableNo_steel[each],gableWidthM[each]);
}
}
print d;
print d1;

for each in frameProfileArr{
	if(atof(get(d,gableNo[each])) > atof(get(d1,gableNo[each]))){
		return true;
	}
}

return false;	



ret = String []; 
frameProfileArr = range(framesProfilecontroller);
d = dict("string");
for each in frameProfileArr{
if(containskey(d, gableNo[each])){
v = get(d,gableNo[each]);
put(d,gableNo[each], string(atof(v)+atof(widthprofile[each])));
}
else{
put(d,gableNo[each],widthprofile[each]);
}
}

gableArr = range(gableArraySize);
d1 = dict("string");
for each in gableArr{
if(containskey(d1, gableNo_steel[each])){
v = get(d1,gableNo_steel[each]);
put(d1,gableNo_steel[each], string(atoi(v)+atof(gableWidthM[each])));
}
else{
put(d1,gableNo_steel[each],gableWidthM[each]);
}
}


for each in frameProfileArr{
	if(atof(get(d,gableNo[each])) > atof(get(d1,gableNo[each]))){
		ret[each] = widthprofile[each];
	}
}

return ret;