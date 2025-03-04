def twoWayMerge(lst1, lst2):
    # Implement the two way merge algorithm on
    #          two ascending order sorted lists
    # return a fresh ascending order sorted list that
    #          merges lst1 and lst2
    # your code here
    tmp_list = []
    i, j = 0, 0
    while i < len(lst1) and j < len(lst2):
        if lst1[i] < lst2[j]:
            tmp_list.append(lst1[i])
            i = i + 1
        else:
            tmp_list.append(lst2[j])
            j = j + 1
    while i < len(lst1):
        tmp_list.append(lst1[i])
        i = i + 1
    while j < len(lst2):
        tmp_list.append(lst2[j])
        j = j + 1
    return tmp_list
