

data=[ int(x) for x in open("../data/day10.dat").readlines()]
data.sort()
data.insert(0,0)
data.append(max(data)+3)
ways
j=[0,0,0]
for i in range(len(data)-1):
    diff=data[i+1]-data[i]
    j[diff-1]=j[diff-1]+1


def build_reverse_map(data):
    graph={}
    data = data[::-1]
    for i in range(len(data)):
        current = data[i]
        j=i+1;
        while (j<len(data) and data[i] - data[j] <= 3):
            j=j+1
        graph[current]=data[i+1:j]
    return graph

rmap = build_reverse_map(data)
ways={}
for d in data:
    ways[d]=1

for d in data[1:]:
    neighbors = rmap[d]
    sum = 0;
    for n in neighbors:
        print(n)
        sum = sum + max(1,ways[n])

    ways[d]=sum
    

