ret = String []; 

d = dict("string");
put(d,"Gable 1", "100");
put(d,"Gable 2", "200");
if(containskey(d, "Gable 1")){
v = get(d,"Gable 1");
put(d,"Gable 1", string(atoi(v)+300));
}
print d;

return ret;