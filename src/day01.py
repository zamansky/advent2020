
import functools

data = [int(x) for x in open("../data/day01.dat").readlines()]

part1_list =  [x for x in data if 2020-x in data]
part1 = part1_list[0] * part1_list[1]

part2_list = [x for x in data for y in data if 2020-(x+y) in data]
part2_set = set(part2_list)
part2 = functools.reduce(lambda a,b: a*b, part2_set)
