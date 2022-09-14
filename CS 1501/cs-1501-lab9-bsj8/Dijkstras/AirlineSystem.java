/*************************************************************************
*  An Airline management system that uses a weighted-edge directed graph
*  implemented using adjacency lists.
*************************************************************************/
import java.util.*;
import java.io.*;

public class AirlineSystem {
  private String [] cityNames = null;
  private Digraph G = null;
  private static Scanner scan = null;
  private static final int INFINITY = Integer.MAX_VALUE;


  /**
  * Test client.
  */
  public static void main(String[] args) throws IOException {
    AirlineSystem airline = new AirlineSystem();
    scan = new Scanner(System.in);
    while(true){
      switch(airline.menu()){
        case 1:
          airline.readGraph();
          break;
        case 2:
          airline.printGraph();
          break;
        case 3:
          airline.shortestHops();
          break;
        case 4:
            airline.shortestDistance();
            break;
        case 5:
          scan.close();
          System.exit(0);
          break;
        default:
          System.out.println("Incorrect option.");
      }
    }
  }

  private int menu(){
    System.out.println("*********************************");
    System.out.println("Welcome to FifteenO'One Airlines!");
    System.out.println("1. Read data from a file.");
    System.out.println("2. Display all routes.");
    System.out.println("3. Compute shortest path based on number of hops.");
    System.out.println("4. Compute shortest path based on distance.");
    System.out.println("4. Exit.");
    System.out.println("*********************************");
    System.out.print("Please choose a menu option (1-4): ");

    int choice = Integer.parseInt(scan.nextLine());
    return choice;
  }

  private void readGraph() throws IOException {
    System.out.println("Please enter graph filename:");
    String fileName = scan.nextLine();
    Scanner fileScan = new Scanner(new FileInputStream(fileName));
    int v = Integer.parseInt(fileScan.nextLine());
    G = new Digraph(v);

    cityNames = new String[v];
    for(int i=0; i<v; i++){
      cityNames[i] = fileScan.nextLine();
    }

    while(fileScan.hasNext()){
      int from = fileScan.nextInt();
      int to = fileScan.nextInt();
      int weight = fileScan.nextInt();
      G.addEdge(new WeightedDirectedEdge(from-1, to-1, weight));
      fileScan.nextLine();
    }
    fileScan.close();
    System.out.println("Data imported successfully.");
    System.out.print("Please press ENTER to continue ...");
    scan.nextLine();
  }

  private void printGraph() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      for (int i = 0; i < G.v; i++) {
        System.out.print(cityNames[i] + ": ");
        for (WeightedDirectedEdge e : G.adj(i)) {
          System.out.print(cityNames[e.to()] + "(" + e.weight() + ") ");
        }
        System.out.println();
      }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();

    }
  }

  private void shortestHops() {
    if(G == null){
      System.out.println("Please import a graph first (option 1).");
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    } else {
      for(int i=0; i<cityNames.length; i++){
        System.out.println(i+1 + ": " + cityNames[i]);
      }
      System.out.print("Please enter source city (1-" + cityNames.length + "): ");
      int source = Integer.parseInt(scan.nextLine());
      System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
      int destination = Integer.parseInt(scan.nextLine());
      source--;
      destination--;
      G.bfs(source);
      if(!G.marked[destination]){
        System.out.println("There is no route from " + cityNames[source]
                            + " to " + cityNames[destination]);
      } else {
        // TODO: Use a stack to construct the shortest path from the edgeTo array
        // then print the shortest distance (from the distTo array) and the path
	// with the weight of each edge (see example output)
  Stack<Integer> path = new Stack<>();
  for(int x = destination; x != source; x = G.edgeTo[x]){
    path.push(x);
  }
  path.push(source);

  System.out.print("The shortest route from" + cityNames[source] + "to" + cityNames[destination] + "has"
  + G.distTo[destination] + "hops" );
  while(!path.empty()){
    System.out.print(cityNames[path.pop()] + " ");
  }
  System.out.println();

      }
      System.out.print("Please press ENTER to continue ...");
      scan.nextLine();
    }
  }

    private void shortestDistance() {
      if(G == null){
        System.out.println("Please import a graph first (option 1).");
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      } else {
        for(int i=0; i<cityNames.length; i++){
          System.out.println(i+1 + ": " + cityNames[i]);
        }
        System.out.print("Please enter source city (1-" + cityNames.length + "): ");
        int source = Integer.parseInt(scan.nextLine());
        System.out.print("Please enter destination city (1-" + cityNames.length + "): ");
        int destination = Integer.parseInt(scan.nextLine());
        source--;
        destination--;
        G.dijkstras(source, destination);
        if(!G.marked[destination]){
          System.out.println("There is no route from " + cityNames[source]
                              + " to " + cityNames[destination]);
        } else {
          Stack<Integer> path = new Stack<>();
          for (int x = destination; x != source; x = G.edgeTo[x]){
              path.push(x);
          }
          System.out.print("The shortest route from " + cityNames[source] +
                             " to " + cityNames[destination] + " has " +
                             G.distTo[destination] + " miles: ");

          int prevVertex = source;
          System.out.print(cityNames[source] + " ");
          while(!path.empty()){
            int v = path.pop();
            System.out.print(G.distTo[v] - G.distTo[prevVertex] + " "
                             + cityNames[v] + " ");
            prevVertex = v;
          }
          System.out.println();

        }
        System.out.print("Please press ENTER to continue ...");
        scan.nextLine();
      }
  }

  /**
  *  The <tt>Digraph</tt> class represents an directed graph of vertices
  *  named 0 through v-1. It supports the following operations: add an edge to
  *  the graph, iterate over all of edges leaving a vertex.Self-loops are
  *  permitted.
  */
  private class Digraph {
    private final int v;
    private int e;
    private LinkedList<WeightedDirectedEdge>[] adj;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path


    /**
    * Create an empty digraph with v vertices.
    */
    public Digraph(int v) {
      if (v < 0) throw new RuntimeException("Number of vertices must be nonnegative");
      this.v = v;
      this.e = 0;
      @SuppressWarnings("unchecked")
      LinkedList<WeightedDirectedEdge>[] temp =
      (LinkedList<WeightedDirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        adj[i] = new LinkedList<WeightedDirectedEdge>();
    }

    /**
    * Add the edge e to this digraph.
    */
    public void addEdge(WeightedDirectedEdge edge) {
      int from = edge.from();
      adj[from].add(edge);
      e++;
    }


    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (WeightedDirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<WeightedDirectedEdge> adj(int v) {
      return adj[v];
    }

    public void bfs(int source) {
      marked = new boolean[this.v];
      distTo = new int[this.e];
      edgeTo = new int[this.v];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) {
        int v = q.remove();
        for (WeightedDirectedEdge w : adj(v)) {
          if (!marked[w.to()]) {
            edgeTo[w.to()] = v;
            distTo[w.to()] = distTo[v] + 1;
            marked[w.to()] = true;
            q.add(w.to());
          }
        }
      }
    }

    public void dijkstras(int source, int destination) {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];


      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) {
          if (distTo[current]+w.weight() < distTo[w.to()]) {
	      //TODO:update edgeTo and distTo
	      edgeTo[w.to()]=current;
        distTo[w.to()]= distTo[current] + w.weight();
          }
        }
        //Find the vertex with minimim path distance
        //This can be done more effiently using a priority queue!
        int min = INFINITY;
        current = -1;

        for(int i=0; i<distTo.length; i++){
          if(marked[i])
            continue;
          if(distTo[i] < min){
            min = distTo[i];
            current = i;
          }
        }

	//TODO: Update marked[] and nMarked. Check for disconnected graph.
      if(current >= 0){
        marked[current] = true;
        nMarked++;
      }else{
        break;
      }
      }
    }
  }

  /**
  *  The <tt>WeightedDirectedEdge</tt> class represents a weighted edge in an directed graph.
  */

  private class WeightedDirectedEdge {
    private final int v;
    private final int w;
    private int weight;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedDirectedEdge(int v, int w, int weight) {
      this.v = v;
      this.w = w;
      this.weight = weight;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }

    public int weight(){
      return weight;
    }
  }
}
