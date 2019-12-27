x, y = "GeeksforGeek", "GeekQuizGeeks"
x, y = "abcdxyz", "xyzabcd"
x, y = "zxabcdezy", "yzabcdezx"
x, y = 'OldSite:GeeksforGeeks.org', 'NewSite:GeeksQuiz.com'

best_match = ''

lc_map = [['' for j in range(len(y) + 1)] for i in range(len(x) + 1)]

for s1 in range(len(x)):
    for s2 in range(len(y)):
        if x[s1] == y[s2]:
            if s1 == 0 or s2 == 0:
                lc_map[s1][s2] = x[s1]
            else:
                lc_map[s1][s2] = lc_map[s1 - 1][s2 - 1] + x[s1]
            best_match = lc_map[s1][s2] if len(lc_map[s1][s2]) > len(best_match) else best_match

print('Longest common substring is', best_match)
