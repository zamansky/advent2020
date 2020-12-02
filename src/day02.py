import re

parser= "(\d+)-(\d+) ([a-z]): ([a-z]*)"

def frequencies(word):
    d={}
    for letter in word:
        d.setdefault(letter,0)
        d[letter]=d[letter]+1
    return d

valids=0
for line in open("../data/day02.dat").readlines():
    x = re.search(parser,line)
    (mini,maxi,letter,password) = x.groups()
    mini = int(mini)
    maxi = int(maxi)

    counts = frequencies(password)
    if letter in counts:
        count = counts[letter]
    else:
        count = 0

    if count >= mini and count <= maxi:
        valids = valids+1
print(valids)

import re

parser= "(\d+)-(\d+) ([a-z]): ([a-z]*)"

def frequencies(word):
    d={}
    for letter in word:
        d.setdefault(letter,0)
        d[letter]=d[letter]+1
    return d

valids=0
for line in open("../data/day02.dat").readlines():
    x = re.search(parser,line)
    (mini,maxi,letter,password) = x.groups()
    mini = int(mini)-1
    maxi = int(maxi)-1

    if (letter == password[mini] or letter == password[maxi]) \
       and (password[mini] != password[maxi]):
        valids = valids + 1

print(valids)

