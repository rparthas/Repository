''' Read input from STDIN. Print your output to STDOUT '''

from bisect import bisect_left, bisect_right


# Use input() to read input from STDIN and use print to write your output to STDOUT

def main():
    results = []
    t = int(input())
    for i in range(0, t):
        a_team = []
        b_team = []
        n = input()
        for j in input().split():
            a_team.append(int(j))
        for j in input().split():
            b_team.append(int(j))
        results.append(calc_win(a_team, b_team))

    [print(res) for res in results]


def calc_win(a_team, b_team):
    a_team.sort()
    win = 0
    for b in b_team:
        pos = bisect_left(a_team, b)
        if 0 <= pos < len(a_team):
            if a_team[pos] == b:
                pos = bisect_right(a_team, b, pos)
                if pos < len(a_team):
                    del a_team[pos]
                    win += 1
            else:
                del a_team[pos]
                win += 1
    return win


main()
