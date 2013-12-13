curCpu = input["cpu"]

state = dataStore.getData(input["deviceid"])

def tsList = []

if (state != null)
{
	tsList = state["tsList"]
}
else
{
	state = ["flagstatus": "false"]
}

tsList.add(curCpu)

if (tsList.size() == 6)
{
	tsList.remove(0)
}

state["tsList"] = tsList;

def value = tsList.findAll {it > 50}

//3 among last 5 readings were > 50
if (value.size() > 2 && state["flagstatus"] == "false")
{
	println "raising flag for device: " + input["deviceid"]
	state["flagstatus"] = "true"
}
else if (value.size() < 2)
{
	if (state["flagstatus"] == "true")
	{
		println "taking down the flag for device: " + input["deviceid"]
		state["flagstatus"] = "false"
	}
}


dataStore.storeData(input["deviceid"], state)

