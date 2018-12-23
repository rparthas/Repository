from os import walk
import re

f = []
my_path = "."
for (dir_path, dir_names, file_names) in walk(my_path):
    f.extend(file_names)
    break

extn = ".mkv"
for file_name in file_names:
    if extn in file_name:
        file_name = file_name.replace(extn, "")
        # print(file_name)
        # print(file_name.replace("",""))

for i in range(16):
    print(f"And(a=a[{i}],b=b[{i}],out=out[{i}]);")
