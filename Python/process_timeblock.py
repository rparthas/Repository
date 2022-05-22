from collections import OrderedDict
blocks  ='''
'''
activity_count = {}
for block in blocks.split("\n"):
    activities = block.split(": ")
    if len(activities) < 2:
        continue
    measure = 0.5
    activities = activities[1].split(",")
    if len(activities) > 1:
        measure = 1/len(activities)
    for activity in activities:
        activity_cnt = 0
        activity = activity.strip()
        if activity in activity_count:
            activity_cnt = activity_count[activity]
        activity_count[activity] = activity_cnt+measure

activity_count = OrderedDict(sorted(activity_count.items(), key=lambda t: t[1]))
for activity,cnt in activity_count.items():
    print(f"{activity},{cnt}")