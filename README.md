# INPUT:
The input file consists of a graph in GML format. The graph is directed, weighted network representing the neural
network of C. Elegans. Data compiled by D. Watts and S. Strogatz

# EXECUTION
To compile the program, use following command on the command prompt:
`javac PA4.java`

To execute the program use following command on the command prompt:
`java PA4`

The program will open a command prompt on which you can execute several commands. The commands are as follows:
graph:			Initializes the graph
bellman-ford:	Run the Bellman Ford single source shortest path algorithm. Graph must be initialized before executing this command
dijkstra:		Run the Dijkstra single source shortest path algorithm. Graph must be initialized before executing this command
print:			Prints the results of last algorithm execution.
performance:	Prints the time of the last algorithm execution.
quit:			Exit the program.
help:			Prints this help.

Please make sure that a valid command is provided for the program. In case the command will be invalid,
the program will simply print the corresponding message.

# INTRODUCTION
In this project we are supposed to implement two graph algorithms. We have selected two algorithms of determining Single
Source Shortest path. The algorithms are: Bellman-Ford and Dijkstra. The graphs on which these algorithms are implmemented
(as mentioned above) is a neural network prepared by C. Elegans and data compiled by D. Watts and S. Strogatz

# DATA STRUCTRE & PROGRAM DESIGN
The program is structured into 4 classes
    ## PA4
    This is the main entry point of the program. The main method in this program displays a command prompt like setup
    which allows the users to enter pre-defined commands. The "help" command will call the "help" method which describe
    the available commands, and what each command does.

    ## Vertex
    The Edge class is a representation of a single vertex of the graph. It consists of the vertex ID (as given by
    the input graph file) and vertex label (as given by the input graph file). Other attributes in this class (parent,
    distance, heapIndex) are auxillary attributes, which are used by remaining program.

    ## Edge
    The Edge class is a representation of a single edge of the graph. It consists of a sourve vertex, target vertex
    and the edge weight. In the input file, the source and target attributes contains ID of the vertex. These values are
    used to identify the Vertex instances in the program which should be assigned to particular Edge instance.

    ## MinHeap
    The MinHeap class is an implementation of binary MinHeap. It has all methods applied to MinHeap. These methods
    are as follows:
        ### insert: adds an item to the minheap
        ### minHeapify: Performs minHeapify on a particular node of the heap
        ### extract: Extracts the minimum node from the min-heap
        ### bubble: It's analogous to the decrease key method. Actually the decreasing of key is done in the other
        part of the program. This method only bubbles the particular node to its correct location once its key is
        decreased

    ## Graph
    The Graph class represents the entire graph and contains algorithm implementation of Bellman-Ford and Dijkstra.
    These are implemented by methods "bellman_ford" and "dijkstra" respectively. The method "relax", relaxes the
    target vertex of the provided edge and the "parse" method reads given "GML" file and initialize our data structures.

    The data structures used by this class are:
        ### Vertex Hashtable: The attribute "vertices" is the hash table of vertices. The key in this hash table is
        the ID of the vertex
        ### Edges List: The attribute "edges" is the simple list of Edges. Nothing sophisticated has to be performed
        on Edges apart from loop, so a simple ArrayList is used for this case
        ### Adjacency Hashtable: This represents an adjacency list. The attribute is "adjacencies". The key of this
        hashtable is the Vertex instance. The value of this table is a List of Edges instance.

# ALGORITHMS IMPLEMENTED
    ## BELLMAN-FORD
    It is basically used to find out the shortest path in a weighted graph, starting from a source vertex and
    reaching out to the other vertices in the graph. Even though, it is slower than Dijkstra’s algorithm,
    but even so it proves more versatile than the latter one. The running time of this algorithm is O(V.E), where
    initialization is done in O(V) time, whereas relaxation takes up O(E(V-1)) time.

    ## DIJKSTRA
    This algorithm is also used to find the shortest path from a single source to all other vertices in a weighted
    graph. Further, this algorithm can work with undirected and directed graphs, both. For this algorithm to work,
    it is necessary that the graph has non-negative weights and that the graph is connected. The running time of
    Dijkstra’s algorithm is O(E logV). Using Fibonacci heaps can further simplify this to O(E+VlogV)

# ENVIRONMENT
The program is written and tested using Java 1.9. However, the methods and structures used are available in
previous version of Java as well. It will run perfectly with java version more than 1.6.

# RESULTS
    ## WHAT WORKS
    The program is able to parse the GML file and read the vertices, nodes and edges from it. The program also
    takes care of all the commands given in the instructions sheet. Every command runs elegantly and also
    in case of an invalid command, the corresponding message is delayed. The program also takes care of the case
    if there is no path available between the source and the end vertex, then it displays the corresponding message
    in the result.

    ## WHAT DOESN'T WORK
    The program will not work for any GML file with any other name than what has been hardcoded in the code itself.
    Also, any other format for the input file will give error too. Further, if any invalid command (other command
    than what was given in the instruction sheet) command is provided, the program makes an elegant exit by
    displaying the corresponding message.