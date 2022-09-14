/*************************************************************************
*  An Airline management system that uses a directed graph implemented using
*  adjacency lists.
*
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
    System.out.println("3. Compute Shortest path based on number of hops.");
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
      G.addEdge(new DirectedEdge(from-1, to-1));
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
        for (DirectedEdge e : G.adj(i)) {
          System.out.print(cityNames[e.to()] + "  ");
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
        // then print the number of hops (from the distTo array) and the path
        Stack<Integer> path = new Stack<>();
        for(int x = destination; x != source; x = G.edgeTo[x]){
          path.push(x);
        }
        path.push(source);

        System.out.println("The shortest route from" + " " + cityNames[source] + " " + "to" + " " + cityNames[destination] + " " + "has"
        + " " + G.distTo[destination] + " " + "hop(s): " );
        while(!path.empty()){
          System.out.print(cityNames[path.pop()] + " ");
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
    private LinkedList<DirectedEdge>[] adj;
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
      LinkedList<DirectedEdge>[] temp =
      (LinkedList<DirectedEdge>[]) new LinkedList[v];
      adj = temp;
      for (int i = 0; i < v; i++)
        adj[i] = new LinkedList<DirectedEdge>();
    }

    /**
    * Add the edge e to this digraph.
    */
    public void addEdge(DirectedEdge edge) {
      int from = edge.from();
      adj[from].add(edge);
      e++;
    }


    /**
    * Return the edges leaving vertex v as an Iterable.
    * To iterate over the edges leaving vertex v, use foreach notation:
    * <tt>for (DirectedEdge e : graph.adj(v))</tt>.
    */
    public Iterable<DirectedEdge> adj(int v) {
      return adj[v];
    }

    public void bfs(int source) {
      marked = new boolean[this.v];
      distTo = new int[this.v];
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
        for (DirectedEdge w : adj(v)) {
          if (!marked[w.to()]) {
          //TODO: Complete BFS implementation
          edgeTo[w.to()]=v;
          distTo[w.to()]=distTo[v]+1;
          marked[w.to()]=true;
          q.add(w.to());
          }
        }
      }
    }
  }

  /**
  *  The <tt>DirectedEdge</tt> class represents an edge in an directed graph.
  */

  private class DirectedEdge {
    private final int v;
    private final int w;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public DirectedEdge(int v, int w) {
      this.v = v;
      this.w = w;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }
  }
}
