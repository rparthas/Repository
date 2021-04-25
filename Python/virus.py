def verify(patient, virus):
    match_cnt = 0
    virus_index = 0
    for char in patient:
        while virus_index < len(virus):
            curr_char = virus[virus_index]
            virus_index += 1
            if char == curr_char:
                match_cnt += 1
                break

    if match_cnt == len(patient):
        return "POSITIVE"
    return "NEGATIVE"


def main():
    virus = input()
    count = int(input())
    for patient_cnt in range(count):
        print(verify(input(), virus))


main()

"""
coronavirus
3
abcde
crnas
onarous

"""
