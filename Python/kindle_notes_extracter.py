file = open("Kindle Clippings.txt")
line = file.readline()
to_add = False
lines = []
while line:
    if "==========" in line:
        for line_pr in lines:
            print(line_pr.replace("\n", ""))
    lines.append(line)
    if line in ['\n', '\r\n']:
        lines = []
    line = file.readline()
