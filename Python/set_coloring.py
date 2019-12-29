class Graph:
    def __init__(self, vertices):
        self.vertices_length = vertices
        self.graph = [[0 for column in range(vertices)] for row in range(vertices)]
        self.colour = [0] * vertices

    def is_safe_to_color(self, color, vertex_index):
        vertex_map = self.graph[vertex_index]
        for i in range(self.vertices_length):
            if vertex_map[i] == 1 and self.colour[i] == color:
                return False

        return True

    def color_vertex(self, num_of_colors, vertex_index):
        if vertex_index < self.vertices_length:
            for color_index in range(num_of_colors):
                if self.is_safe_to_color(color_index + 1, vertex_index):
                    self.colour[vertex_index] = color_index + 1
                    return self.color_vertex(num_of_colors, vertex_index + 1)
        else:
            return True
        return False

    def color_graph(self, num_of_colors):
        if self.color_vertex(num_of_colors, 0):
            print(self.colour)
            return
        print("Coloring Not Possible")


g = Graph(4)
g.graph = [[0, 1, 1, 1], [1, 0, 1, 0], [1, 1, 0, 1], [1, 0, 1, 0]]
g.color_graph(3)
