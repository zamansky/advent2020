seat="FBFBBFFRLR"
row=seat[0:7]
col=seat[7:]

def f(i,l):
    if l=='F' or l=='L':
        return 2**(i)
    else:
        return 0
    

def calc_seat(seat):
    row = seat[0:7][::-1]
    col = seat[7:][::-1]
    row_num = 127 - sum([ f(i,row[i]) for i in range(len(row))])
    col_num = 7 - sum([ f(i,col[i]) for i in range(len(col))])
    
    return (row_num*8+ col_num)

x = calc_seat(seat)
