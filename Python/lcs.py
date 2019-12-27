x, y = "GeekforGeeks", "GeeksQuiz"
x, y = "abcdxyz", "xyzabcd"
x, y = "zxabcdezy", "yzabcdezx"
x, y = 'OldSite:GeeksforGeeks.org', 'NewSite:GeeksQuiz.com'

s1, s2 = 0, 0
best_match = ''
count = 0


def check_best_match():
    global best_match
    best_match = match if len(match) > len(best_match) else best_match


while s1 < len(x):
    s2 = 0
    match = ''
    matched = False
    while s2 < len(y) and s1 < len(x):
        if x[s1] == y[s2]:
            match += x[s1]
            s1 += 1
            matched = True
        else:
            check_best_match()
            match = ''
        s2 += 1
        count += 1
    s1 = s1 if matched else s1 + 1
    check_best_match()

print('Longest common substring is', best_match)
print('loops:', count, 'out of', len(x) * len(y))
