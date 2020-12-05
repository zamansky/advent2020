

data = open("../data/sample04.dat").read()
data=data.split("\n\n")
data_list = []
for d in data:
    data_list.append([item for item in d.split()])

data_dicts=[]
for d in data_list:
    temp = { item.split(":")[0]:item.split(":")[1] for item in d} 
    temp.pop('cid',None)
    data_dicts.append(temp)

fields = set(["byr","iyr","eyr","hgt","hcl","ecl","pid"])

ans = [set(x.keys()) == fields for x in data_dicts]
