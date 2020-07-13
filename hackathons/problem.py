class Edge():
    def __init__(self, sd, sp, dd, dp, dist=1):
        self.source = f"{sd}:{sp}"
        self.dest = f"{dd}:{dp}"
        self. sd = sd
        self. sp = sp
        self. dd = dd
        self. dp = dp
        self.dist = dist

    def __str__(self):
        return f"{self.source}->{self.dest}={self.dist}"


def process_input():
    input_str = input()
    return [int(i) for i in input_str.split()]


def print_route(route):
    return [x.dest for x in route]


def main():
    input_list = process_input()
    patients = input_list[0]
    doctors = input_list[1]
    efforts = []
    for i in range(doctors):
        efforts.append(process_input())

    edge_map = prepare_graph(efforts, doctors, patients)
    route = run_dijikstra(
        edge_map, "-1:-1", f"{doctors}:{patients}")
    violators = find_violators(route)
    excluded_nodes = []

    while len(violators) > 0:
        excluded_nodes = excluded_nodes + violators
        route = run_dijikstra(
            edge_map, "-1:-1", f"{doctors}:{patients}", excluded_nodes)
        violators = find_violators(route)

    print(calculate_cost(route))


def get_least_weight_edge(edges):
    index = 0
    for i, edge in enumerate(edges):
        if edge.dist < edges[index].dist:
            index = i
    return index


def add_edge(edge, edge_map):
    if edge.source in edge_map.keys():
        edge_map[edge.source].append(edge)
    else:
        edge_map[edge.source] = [edge]


def prepare_graph(efforts, doctors, patients):
    edge_map = {}

    # first case
    for doctor in range(doctors):
        add_edge(Edge(-1, -1, doctor,
                      0, efforts[doctor][0]), edge_map)

    # all others
    for doctor in range(doctors):
        for patient in range(1, patients):
            for doctor_idx in range(doctors):
                add_edge(Edge(doctor_idx, patient-1, doctor,
                              patient, efforts[doctor][patient]), edge_map)

    # last case
    for doctor in range(doctors):
        add_edge(Edge(doctor, patients-1, doctors,
                      patients, 0), edge_map)

    return edge_map


def print_graph(edge_map):
    for vertex in edge_map:
        print(vertex)
        for edge in edge_map[vertex]:
            print(edge)


def calculate_cost(edges):
    sum = 0
    for edge in edges:
        sum += edge.dist
    return sum


def find_violators(edges):
    violators = []
    attended_doctors = []
    for edge in edges:
        if edge.sd == -1:
            continue
        if edge.dd in attended_doctors:
            violators.append(f"{edge.dd}:{edge.dp}")
        if edge.sd != edge.dd:
            attended_doctors.append(edge.sd)
    return violators


def update_minimum_route(route_map, edge):
    if edge.dest in route_map:
        old_route = route_map[edge.dest]
        new_route = route_map[edge.source]+[edge]
        if calculate_cost(old_route) > calculate_cost(new_route):
            return new_route
        return old_route
    return route_map[edge.source]+[edge]


def run_dijikstra(edge_map, start, end, excluded_nodes=[]):
    route_map = {start: [Edge(-1, -1, -1, -1, 0)]}
    visited_nodes = [start]
    edges_to_process = edge_map[start].copy()

    while len(edges_to_process) > 0:
        edge = edges_to_process.pop(get_least_weight_edge(edges_to_process))

        if edge.dest in excluded_nodes:
            continue

        if edge.dest not in visited_nodes:
            visited_nodes.append(edge.dest)
            if edge.dest in edge_map.keys():
                edges_to_process.extend(edge_map[edge.dest])

        route_map[edge.dest] = update_minimum_route(route_map, edge)

    return route_map[end]


main()
