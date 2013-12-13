
curCpu = input["cpu"]
curTs = input["timestamp"]

state = dataStore.getData(input["deviceid"])

println "----${curCpu}----"

def tsList = []

if (state != null)
{
	tsList = state["tsList"]
}
else
{
	state = ["flagstatus": "false"]
}

if (curCpu > 50)
{
	tsList.add(curTs)
}

if (tsList.size() == 0)
{	
	return
}

def timeWindow = 10 //5*60*1000

//remove all entries which are older than the time window required
def maxvar = tsList.max()
tsList.removeAll {maxvar - it > timeWindow}

state["tsList"] = tsList;

//if 5 entries remain, we have problem
if ((tsList.size() > 4) && (state["flagstatus"] == "false"))
{
	println "${curCpu} | ${tsList} | ${curTs} | " + tsList.min()
	
	println "raising flag for device: " + input["deviceid"]
	state["flagstatus"] = "true"
}
else if (tsList.size() < 5)
{	
	if (state["flagstatus"] == "true")
	{
		println "${curCpu} | ${tsList} | ${curTs} | " + tsList.min()
		
		println "taking down the flag for device: " + input["deviceid"]
		state["flagstatus"] = "false"
	}
}

dataStore.storeData(input["deviceid"], state)

