import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;

public class PA4 {
    /**
     * Class representing an edge of graph
     */
    private static class Edge {
        // Source vertex of the edge
        private Vertex source;
        // Target vertex of the edge
        private Vertex target;
        // Weight of the edge
        private int weight;

        /**
         * Get source vertex
         *
         * @return Instance of Vertex
         */
        public Vertex getSource() {

            return source;
        }

        /**
         * Set the source vertex
         *
         * @param source Instance of Vertex
         */
        public void setSource(Vertex source) {

            this.source = source;
        }

        /**
         * Get the target Vertex
         * @return Instance of Vertex
         */
        public Vertex getTarget() {

            return target;
        }

        /**
         * Set the target vertex
         *
         * @param target Instance of Vertex
         */
        public void setTarget(Vertex target) {

            this.target = target;
        }

        /**
         * Get weight of the edge
         *
         * @return Integral weight value
         */
        public int getWeight() {

            return weight;
        }

        /**
         * Set weight of the edge
         *
         * @param weight Integral weight value
         */
        public void setWeight(int weight) {

            this.weight = weight;
        }
    }

    /**
     * Class representing a vertex of graph
     */
    private static class Vertex {
        // Vertex ID
        private int id;
        // Vertex label
        private String label;
        // Vertex parent (used by the algorithms)
        private Vertex parent;
        // Vertex distance from the source (used by the algorithms)
        private int distance;
        // Index in MinHeap (used by the MinHeap class)
        private int heapIndex;

        /**
         * Get the vertex ID
         *
         * @return Integer
         */
        public int getId() {
            return id;
        }

        /**
         * Set the vertex ID
         *
         * @param id Integer
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * Get the Vertex Label
         *
         * @return String label
         */
        public String getLabel() {
            return label;
        }

        /**
         * Set the vertex label
         *
         * @param label String label
         */
        public void setLabel(String label) {
            this.label = label;
        }

        /**
         * Get the vertex parent
         *
         * @return Instance of Vertex
         */
        public Vertex getParent() {
            return parent;
        }

        /**
         * Set the vertex parent
         *
         * @param parent Instance of Vertex
         */
        public void setParent(Vertex parent) {
            this.parent = parent;
        }

        /**
         * Get the vertex distance from the source vertex
         *
         * @return Integer vertex distance
         */
        public int getDistance() {
            return distance;
        }

        /**
         * Set the vertex distance from the source vertex
         *
         * @param distance Integer vertex distance
         */
        public void setDistance(int distance) {
            this.distance = distance;
        }

        /**
         * Get the index in MinHeap array
         *
         * @return Integer index
         */
        public int getHeapIndex() {
            return this.heapIndex;
        }

        /**
         * Set the index in MinHeap array
         *
         * @param heapIndex Integer index
         */
        public void setHeapIndex(int heapIndex) {
            this.heapIndex = heapIndex;
        }
    }

    /**
     * Class representing Binary MinHeap. It is used by the Dijkstra algorithm to
     * get the nearest vertex
     */
    private static class MinHeap {
        // Array containing vertices
        private Vertex[] vertices;
        // Size of the heap
        private int size = 0;

        /**
         * Initialize the heap with the length of vertices available
         *
         * @param size Total size
         */
        public MinHeap(int size) {
            this.vertices = new Vertex[size];
            this.size = 0;
        }

        /**
         * Get the size of the heap
         *
         * @return Size integer
         */
        private int size() {
            return this.size;
        }

        /**
         * Returns parent index of the given index in a binary heap
         *
         * @param index Given Index
         * @return Parent Index
         */
        private int parent(int index) {
            return index / 2;
        }

        /**
         * Returns the left child index of the given index in a binary heap
         *
         * @param index Given Index
         * @return Left Child Index
         */
        private int left(int index) {
            return index * 2;
        }

        /**
         * Returns the right child index of the given index in a binary heap
         *
         * @param index Given Index
         * @return Right Child Index
         */
        private int right(int index) {
            return (index * 2) + 1;
        }

        /**
         * Returns true if the given index is belongs to the leaf nodes in a binary heap
         *
         * @param index Given Index
         * @return True if the given index belongs to leaf nodes, else false
         */
        private boolean isLeaf(int index) {
            return index >= size / 2 && index <= size;
        }

        /**
         * Swap two vertices in the binary heap array
         *
         * @param pos1 Index Position of Vertex
         * @param pos2 Index Position of vertex
         */
        private void swap(int pos1, int pos2) {
            // Use a temporary variable for swap
            Vertex temp = this.vertices[pos1];
            this.vertices[pos1] = this.vertices[pos2];
            this.vertices[pos2] = temp;

            // Update the heapIndex attribute of the vertices
            this.vertices[pos1].setHeapIndex(pos1);
            this.vertices[pos2].setHeapIndex(pos2);
        }

        /**
         * Insert a vertex in the binary min-heap
         *
         * @param item Vertex instance
         */
        public void insert(Vertex item) {
            // Set the heap index to the last element index + 1
            item.setHeapIndex(size);
            // Set the vertex at the last element index + 1
            this.vertices[size] = item;

            // Bubble the vertex until its correct position
            int current = size;
            int parent = this.parent(current);
            // Loop until the distance attribute of the current vertex is smaller than the distance
            // attribute of the parent vertex
            while (this.vertices[current].getDistance() < this.vertices[parent].getDistance()) {
                // Swap current with parent
                swap(current, parent);
                current = parent;
                parent = this.parent(current);
            }
            // Increment the size of the heap
            size++;
        }

        /**
         * Extract the minimum vertex from the min heap
         *
         * @return Vertex instance
         */
        public Vertex extract() {
            // Extract the root vertex
            Vertex output = this.vertices[0];

            // Replace the root vertex with the last vertex
            this.vertices[0] = this.vertices[this.size - 1];
            this.vertices[0].setHeapIndex(0);
            // Decrease the size of the heap
            this.size--;

            // Run MinHeapify procedure to bring the root vertex to its correct location
            this.minHeapify(0);

            // Return the extracted vertex
            return output;
        }

        /**
         * MinHeapify procedure to place particular vertex at its correct location
         *
         * @param index Index to perform min-heapify on
         */
        public void minHeapify(int index) {
            // If the vertex belongs to leaves, then it's already at its correct location
            if (this.isLeaf(index)) {
                return;
            }

            // Get left and right indices
            int left = this.left(index);
            int right = this.right(index);

            // If the distance attribute of current vertex is greater than any of its children
            if (this.vertices[index].getDistance() > this.vertices[left].getDistance()
                    || this.vertices[index].getDistance() > this.vertices[right].getDistance()) {
                // Pick up the smallest children and replace the current vertex with that
                if (this.vertices[left].getDistance() < this.vertices[right].getDistance()) {
                    this.swap(index, left);
                    this.minHeapify(left);
                } else {
                    this.swap(index, right);
                    this.minHeapify(right);
                }
            }
        }

        /**
         * Bubble the vertex to its correct position after decreasing its key
         *
         * @param vertex Vertex Instance to bubble
         */
        public void bubble(Vertex vertex) {
            int current = vertex.getHeapIndex();
            int parent = this.parent(current);

            // Loop until the current vertex's key is lesser than the parent vertex's key
            while (this.vertices[current].getDistance() < this.vertices[parent].getDistance()) {
                // Swap current and parent
                swap(current, parent);
                current = parent;
                parent = this.parent(current);
            }
        }
    }

    /**
     * Class representing Graph and implements method for finding
     * Single source shortest path from the first vertex
     */
    private static class Graph {
        // Source vertex (first) for finding single source shortest path to other vertices
        private static final int SOURCE_ID = 0;

        // Hashtable of vertices. The key of the table is ID of vertex
        private Hashtable<Integer, Vertex> vertices;

        // List of all edges
        private List<Edge> edges;

        // Adjacency list of vertex with edges
        private Hashtable<Vertex, List<Edge>> adjacency;

        // Execution time of the algorithm
        private long executionTime;

        // Flag to check if an algorith is executed
        private boolean algorithmExecuted;

        /**
         * Initialize the graph instance
         *
         * @param dataFile GML file representing the graph
         * @throws IOException Exception if the file is not readable
         */
        private Graph(String dataFile) throws IOException {
            this.vertices = new Hashtable<Integer, Vertex>();
            this.edges = new ArrayList<Edge>();
            this.adjacency = new Hashtable<Vertex, List<Edge>>();
            this.executionTime = 0;
            this.algorithmExecuted = false;

            // Parse the GML file
            this.parse(dataFile);
        }

        /**
         * Parse the GML File and fill objects
         * @param dataFile GML file representing the graph
         * @throws IOException Exception if the file is not readable
         */
        private void parse(String dataFile) throws IOException {
            // Read the file
            BufferedReader br = new BufferedReader(new FileReader(dataFile));
            String line = null;

            // Loop on line by line
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // "graph" label is found
                if (line.startsWith("graph")) {
                    // Loop inside the "graph" label
                    while ((line = br.readLine()) != null) {
                        line = line.trim();
                        // "node" label is found
                        if (line.startsWith("node")) {
                            // Initialize new vertex instance
                            Vertex vertex = new Vertex();
                            // Loop on "node" attributes
                            while ((line = br.readLine()) != null) {
                                line = line.trim();
                                // Read the ID of the node
                                if (line.startsWith("id")) {
                                    String[] value = line.split(" ");
                                    vertex.setId(Integer.parseInt(value[1]));
                                } else if (line.startsWith("label")) {
                                    // Read the label of the node
                                    String value = line.substring(6);
                                    // Remove quotes from label
                                    vertex.setLabel(value.replaceAll("^\"|\"$", ""));
                                } else if (line.startsWith("]")) {
                                    // Node is ended, add the vertex to the table, with ID as the key
                                    vertices.put(vertex.getId(), vertex);
                                    break;
                                }
                            }
                        } else if (line.startsWith("edge")) {
                            // "edge" label is found
                            Edge edge = new Edge();
                            // Loop on edge attributes
                            while ((line = br.readLine()) != null) {
                                line = line.trim();
                                if (line.startsWith("source")) {
                                    // Read the edge's source
                                    String[] value = line.split(" ");
                                    edge.setSource(vertices.get(Integer.parseInt(value[1])));
                                } else if (line.startsWith("target")) {
                                    // Read the edge's target
                                    String[] value = line.split(" ");
                                    edge.setTarget(vertices.get(Integer.parseInt(value[1])));
                                } else if (line.startsWith("value")) {
                                    // Read the edge's weight value
                                    String[] value = line.split(" ");
                                    edge.setWeight(Integer.parseInt(value[1]));
                                } else if (line.startsWith("]")) {
                                    // Edge is ended, add the edge to the table and adjacency list
                                    this.edges.add(edge);
                                    if (!adjacency.containsKey(edge.getSource())) {
                                        adjacency.put(edge.getSource(), new ArrayList<Edge>());
                                    }
                                    adjacency.get(edge.getSource()).add(edge);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        /**
         * Relax an edge
         *
         * @param edge Edge instance
         * @return True if the edge is relaxed otherwise false
         */
        private boolean relax(Edge edge) {
            // If the source vertex was never relaxed, then no need to relax the target vertex
            if (edge.getSource().getDistance() == Integer.MAX_VALUE) {
                return false;
            }

            // Weight = Source distance + weight of the edge
            int weight = edge.getSource().getDistance() + edge.getWeight();

            // If the weight of target vertex is more than the weight calculated above
            // Update the weight of target and source
            if (edge.getTarget().getDistance() > weight) {
                edge.getTarget().setDistance(weight);
                edge.getTarget().setParent(edge.getSource());
                return true;
            }

            // Return false is relaxation was not required
            return false;
        }

        /**
         * Bellman-Ford algorithm for Single Source shortest path implementation
         *
         * @return True if there were no negative weight cycles otherwise false
         */
        public boolean bellman_ford() {
            // Set the algorithm executed flag
            this.algorithmExecuted = true;

            // Record the execution time
            this.executionTime = System.currentTimeMillis();

            // Initialize the vertices
            for (int id : vertices.keySet()) {
                Vertex vertex = vertices.get(id);
                // Set the distance to Max Integer value, except of source vertex
                vertex.setDistance(id != SOURCE_ID ? Integer.MAX_VALUE : 0);
                // Set parent of all vertices to null
                vertex.setParent(null);
            }

            // Relax each edge |Vertices| - 1 times.
            for (int i = 1; i < this.vertices.size(); i++) {
                for (Edge edge : this.edges) {
                    this.relax(edge);
                }
            }

            // Check if negative weight loops were there
            for (Edge edge : this.edges) {
                // If the distance of any edge is still more than the would be distance
                if (edge.getTarget().getDistance() > (edge.getSource().getDistance() + edge.getWeight())) {
                    executionTime = System.currentTimeMillis() - executionTime;
                    return false;
                }
            }

            // Record the time took to complete execution
            executionTime = System.currentTimeMillis() - executionTime;
            return true;
        }

        /**
         * Dijkstra algorithm for Single source shortest path implementation
         */
        public void dijkstra() {
            // Set the algorithm executed flag
            this.algorithmExecuted = true;

            // Record the execution time
            this.executionTime = System.currentTimeMillis();

            // Initialize the min-heap
            MinHeap minHeap = new MinHeap(vertices.size());

            // Initialize the vertices
            for (int id : vertices.keySet()) {
                Vertex vertex = vertices.get(id);
                // Set the distance to Max Integer value, except of source vertex
                vertex.setDistance(id != SOURCE_ID ? Integer.MAX_VALUE : 0);
                // Set parent of all vertices to null
                vertex.setParent(null);
                // Add vertices to the min-heap
                minHeap.insert(vertex);
            }

            // Loop on all vertices from the min-heap
            while (minHeap.size() > 0) {
                // Extract the least distance vertex
                Vertex source = minHeap.extract();
                // Read all edges from the source vertex
                List<Edge> edges = this.adjacency.get(source);
                if(edges != null) {
                    for (Edge edge : edges) {
                        // Relax each edge
                        if (this.relax(edge)) {
                            // Bubble the relaxed vertex to its correct position in the min-heap
                            minHeap.bubble(edge.getTarget());
                        }
                    }
                }
            }

            // Record the time took to complete execution
            executionTime = System.currentTimeMillis() - executionTime;
        }

        /**
         * Print the result of last algorithm execution
         */
        public void print() {
            if (!this.algorithmExecuted) {
                // No algorithm was executed
                System.out.println("No algorithm was executed after initialization of graph.");
                return;
            }

            System.out.printf("%-35s%15s   %s\n", "Vertex", "Distance", "Path");
            Vertex source = vertices.get(SOURCE_ID);
            for (int id : vertices.keySet()) {
                Vertex target = vertices.get(id);
                System.out.printf("%-35s", source.getId() + "(" + source.getLabel() + ") -> " + target.getId() + "(" + target.getLabel() + ")");
                if (target.getDistance() == Integer.MAX_VALUE) {
                    System.out.printf("%15s\n", "Not reachable");
                } else {
                    System.out.printf("%15d   ", target.getDistance());
                    String path = String.valueOf(target.getId());
                    while (target.getParent() != null) {
                        target = target.getParent();
                        path = target.getId() + " -> " + path;
                    }
                    System.out.println(path);
                }
            }
        }

        /**
         * Print the performance of algorithm execution
         */
        public void performance() {
            if (!this.algorithmExecuted) {
                // No algorithm was executed
                System.out.println("No algorithm was executed after initialization of graph.");
                return;
            }
            System.out.println("Time of execution: " + this.executionTime + " milliseconds");
        }
    }

    // Input file
    /**
     * A directed, weighted network representing the neural network of C. Elegans. Data compiled by D. Watts and S. Strogatz
     * http://www-personal.umich.edu/~mejn/netdata/celegansneural.zip
     * Collective dynamics of 'small-world' networks. Duncan J. Watts; & Steven H. Strogatz. Nature 393 (1998)
     */
    private static final String INPUT_FILE = "celegansneural.gml";

    // Error messages
    private static final String MESSAGE_UNREADABLE_FILE = "Unable to read the input file" + INPUT_FILE + ". Please make sure the file exists";
    private static final String MESSAGE_NOT_INITIZLIAED = "Graph is not initialized. Please run command \"graph\"";
    private static final String MESSAGE_NO_COMMAND = "Invalid command. Enter \"help\" to see a list of available commands";

    public static void main(String[] args) {
        Graph graph = null;
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.print("> ");
            // Read input command
            command = scanner.next();
            command = command.toLowerCase();
            switch (command) {
                case "graph":
                    // Initialize the Graph instance
                    try {
                        graph = new Graph(INPUT_FILE);
                    } catch (IOException ex) {
                        System.out.println(MESSAGE_UNREADABLE_FILE);
                    }
                    break;
                case "help":
                    // Print the help
                    help();
                    break;
                case "quit":
                    // Quit the program
                    System.out.println("Thank you for using this program!");
                    System.exit(0);
                case "bellman-ford":
                    // Execute the Bellman-Ford algorithm on the graph instance
                    if (graph == null) {
                        System.out.println(MESSAGE_NOT_INITIZLIAED);
                        continue;
                    }
                    graph.bellman_ford();
                    break;
                case "dijkstra":
                    // Execute the Dijkstra algorithm on the graph instance
                    if (graph == null) {
                        System.out.println(MESSAGE_NOT_INITIZLIAED);
                        continue;
                    }
                    graph.dijkstra();
                    break;
                case "print":
                    // Print results of last algorithm execution
                    if (graph == null) {
                        System.out.println(MESSAGE_NOT_INITIZLIAED);
                        continue;
                    }
                    graph.print();
                    break;
                case "performance":
                    // Print performance of last algorithm execution
                    if (graph == null) {
                        System.out.println(MESSAGE_NOT_INITIZLIAED);
                        continue;
                    }
                    graph.performance();
                    break;
                default:
                    // Invalid command
                    System.out.println(MESSAGE_NO_COMMAND);
            }
        }
    }

    /**
     * Print help of the program
     */
    private static void help() {
        System.out.println("List of commands:\n\n" +
                "graph:\t\t\tInitializes the graph\n" +
                "bellman-ford:\tRun the Bellman Ford single source shortest path algorithm. " +
                "Graph must be initialized before executing this command\n" +
                "dijkstra:\t\tRun the Dijkstra single source shortest path algorithm. " +
                "Graph must be initialized before executing this command\n" +
                "print:\t\t\tPrints the results of last algorithm execution.\n" +
                "performance:\tPrints the time of the last algorithm execution.\n" +
                "quit:\t\t\tExit the program.\n" +
                "help:\t\t\tPrints this help.\n");
    }
}
