
println input;

def key = "samplekey" + input
Map val = [("asdf" + input) : "lkjh", ("qwer" + input) : "poiu"]

dataStore.storeData(key, val)

val2 = dataStore.getData(key)

output = val2["asdf" + input] + " -- " + val2["qwer" + input]

