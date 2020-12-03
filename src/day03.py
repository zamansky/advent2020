
def build_candidates(data,deltas):
    (dx,dy) = deltas
    rows = len(data)
    c = [ (r*dy,r*dx) for  r in range(rows) if r*dy < rows]
    return c

def test_candidate(data,loc):
    (row,col)=loc
    num_rows = len(data)
    num_cols = len(data[0])-1
    new_col = col % num_cols
    row_string = data[row]
    c = row_string[new_col]
    if c=='#':
        return 1
    else:
        return 0


data = open("../data/day03.dat").readlines()

candidates = build_candidates(data,[3,1])

def part1(data,candidates):
    return sum([test_candidate(data, x) for x in candidates ])
slopes= [ [1, 1], [3, 1], [5, 1], [7, 1], [1, 2]]

x= [build_candidates(data, deltas) for deltas in slopes]
y = [part1(data, c) for c in x]

