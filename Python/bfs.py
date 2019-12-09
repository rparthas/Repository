graph = {"cab": ["cat", "car"], "mat": ["bat"], "cat": ["mat", "bat"], "car": ["cat", "bar"]}
visited_nodes = {}


def get_neighbours(vertex):
    vertices = []
    if vertex in graph:
        for node in graph[vertex]:
            vertices.append({node: vertex})
    return vertices


def print_path(dest):
    path = []
    route = dest
    path.append(dest)
    while route in visited_nodes:
        parent_node = visited_nodes[route]
        path.append(parent_node)
        route = parent_node

    path.reverse()
    print(path)


start, end = "cab", "bat"
neighbours = get_neighbours(start)

while len(neighbours) > 0:
    neighbour = neighbours.pop(0)
    first_vertex = list(neighbour.keys())[0]
    if first_vertex not in visited_nodes:
        visited_nodes[first_vertex] = neighbour[first_vertex]
        if first_vertex == end:
            print_path(first_vertex)
            break
        neighbours = neighbours + get_neighbours(first_vertex)
