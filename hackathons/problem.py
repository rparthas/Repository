def main():
    input_list = process_input()
    patients = input_list[0]
    doctors = input_list[1]
    efforts = []
    for i in range(doctors):
        efforts.append(process_input())
    calculate(patients, doctors, efforts)


def process_input():
    input_str = input()
    return [int(i) for i in input_str.split()]


def column(matrix, i):
    return [row[i] for row in matrix]


def calculate(patients, doctors, efforts):
    sum_effort = 0
    for patient in range(1, patients, 2):
        min_effort = efforts[0][patient] + efforts[0][patient-1]
        for doctor in range(1, doctors):
            effort = efforts[doctor][patient] + efforts[doctor][patient-1]
            if effort < min_effort:
                min_effort = effort
        sum_effort = sum_effort+min_effort

    if patients % 2 == 1:
        min_effort = efforts[0][patients-1]
        for doctor in range(1, doctors):
            if efforts[doctor][patients-1] < min_effort:
                min_effort = efforts[doctor][patients-1]
        sum_effort = sum_effort + min_effort

    print(sum_effort)


main()
