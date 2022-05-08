import sys
import os

''' Read input from STDIN. Print your output to STDOUT '''
# Use input() to read input from STDIN and use print to write your output to STDOUT
# Write code here
missile_start = input()
travel_time = input()

result = int(missile_start.split(" ")[0]) * 60 + int(missile_start.split(" ")[1])
result = result + int(travel_time.split(" ")[0]) * 60 + int(travel_time.split(" ")[1])

hrs = int(result / 60)
mins = result - (hrs * 60)
if hrs >= 24:
    hrs = hrs - 24
print(f"{hrs:02} {mins:02}")


'''
12 39
11 21
'''