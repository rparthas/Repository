import functools


class Edge:
    def __init__(self, node1, node2, weight):
        self.node1 = node1
        self.node2 = node2
        self.weight = weight

    def __str__(self):
        return self.node1 + "->" + self.node2 + "=" + str(self.weight)


edges = {"A": [Edge("A", "B", 5), Edge("A", "C", 2)],
         "B": [Edge("B", "E", 2), Edge("B", "D", 4)],
         "C": [Edge("C", "B", 8), Edge("C", "E", 7)],
         "D": [Edge("D", "E", 6), Edge("D", "F", 3)],
         "E": [Edge("E", "F", 1)]
         }

start = "A"
route_map = {start: [Edge(start, start, 0)]}


def get_least_weight_edge(edges_for_node):
    index = 0
    for i in range(len(edges_for_node)):
        if edges_for_node[i].weight < edges_for_node[index].weight:
            index = i
    return index


def get_route_cost(route_to_node):
    weights = map(lambda x: x.weight, route_to_node)
    return functools.reduce(lambda a, b: a + b, weights)


edges_to_process = edges[start]
while len(edges_to_process) > 0:
    edge = edges_to_process.pop(get_least_weight_edge(edges_to_process))
    if edge.node2 not in route_map:
        if edge.node2 in edges:
            edges_to_process = edges_to_process + edges[edge.node2]
        route_map[edge.node2] = route_map[edge.node1] + [edge]
    elif get_route_cost(route_map[edge.node1]) + edge.weight < get_route_cost(route_map[edge.node2]):
        route_map[edge.node2] = route_map[edge.node1] + [edge]

for node_to_process in route_map:
    if node_to_process == start:
        continue
    final_route, cost = [], 0
    for route in route_map[node_to_process]:
        final_route.append(route.node2)
        cost += route.weight
    print(final_route, " with cost ", cost)
