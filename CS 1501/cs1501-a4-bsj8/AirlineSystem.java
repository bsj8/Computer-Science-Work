import java.util.Set;
import java.util.ArrayList;
import java.util.*;
import java.io.*;


final public class AirlineSystem implements AirlineInterface {
  private String [] cityNames = null;
  private Digraph G = null;
  private static final int INFINITY = Integer.MAX_VALUE;


  public boolean loadRoutes(String fileName) {
    try{
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
        int distance = fileScan.nextInt();
        double price = fileScan.nextDouble();
        G.addEdge(new WeightedDirectedEdge(from-1, to-1, distance,price));
        G.addEdge(new WeightedDirectedEdge(to-1, from-1, distance,price));
        if(fileScan.hasNext()){
          fileScan.nextLine();

        }
      }
      fileScan.close();
      return true;
    }catch(FileNotFoundException F){
      return false;
    }
  }

  public Set<String> retrieveCityNames() {
    //ArrayList<String> cities= new ArrayList<String>();
    Set<String> cities = new HashSet<>();
    Collections.addAll(cities,cityNames);
    //Set<String> temp = new HashSet<>(cities);
    return cities;
  }

  public Set<Route> retrieveDirectRoutesFrom(String city)
    throws CityNotFoundException {
      int check=-1;
      Set<Route> temp = new HashSet<>();
      for(int i = 0; i < G.v; i++){
        if(cityNames[i].equals(city)){
          check=i;
          break;
        }
      }
        for(WeightedDirectedEdge e : G.adj(check)) {
            Route hold = new Route(city, cityNames[e.to()],e.distance(),e.price());
            temp.add(hold);
        }

    return temp;
  }

  public Set<ArrayList<String>> fewestStopsItinerary(String source,
    String destination) throws CityNotFoundException {
      Set<ArrayList<String>> all =new HashSet<>();
      ArrayList<String> temp= new ArrayList<String>();
      int s=-1;
      int d=-1;
      for(int i = 0; i < G.v; i++){
        if(cityNames[i].equals(source)){
          s=i;
        }
        if(cityNames[i].equals(destination)){
          d=i;
        }
      }
      G.bfs(s,d,false);
      if(!G.marked[d]){
        return all;
      } else {
        // TODO: Use a stack to construct the shortest path from the edgeTo array
        // then print the shortest distance (from the distTo array) and the path
	// with the weight of each edge (see example output)
  Stack<Integer> path = new Stack<>();
  for(int x = d; x != s; x = G.edgeTo[x]){
    System.out.println(cityNames[x]);
    path.push(x);
  }
  path.push(s);

  while(!path.empty()){
    temp.add(cityNames[path.pop()]);
  }
  all.add(temp);
  G.bfs(s,d,true);
  temp= new ArrayList<String>();
  for(int x = d; x != s; x = G.edgeTo[x]){
    System.out.println(cityNames[x]);
    path.push(x);
  }
  path.push(s);

  while(!path.empty()){
    temp.add(cityNames[path.pop()]);
  }
  all.add(temp);

      }

      return all;
  }


  public Set<ArrayList<Route>> shortestDistanceItinerary(String source,
    String destination) throws CityNotFoundException {
      Set<ArrayList<Route>> all =new HashSet<>();
      ArrayList<Route> temp= new ArrayList<Route>();
      int s=-1;
      int d=-1;
      for(int i = 0; i < G.v; i++){
        if(cityNames[i].equals(source)){
          s=i;
        }
        if(cityNames[i].equals(destination)){
          d=i;
        }
      }
      G.dijkstras(s, d,false);
      if(!G.marked[d]){
        return all;
      } else {
        Stack<Integer> path = new Stack<>();
        for (int x = d; x != s; x = G.edgeTo[x]){
            path.push(x);
            System.out.println("Pushing these cities: " + cityNames[x]);
        }

        int prevVertex = s;
        while(!path.empty()){
          int v = path.pop();
          System.out.println(v);
            Route build= new Route(source,cityNames[v],G.distTo[v] - G.distTo[prevVertex],G.priceTo[v] - G.priceTo[prevVertex]);
            System.out.println(build.source + " " + build.destination);
            temp.add(build);
            source=cityNames[v];
          prevVertex = v;
        }
        all.add(temp);
      }
      source=cityNames[s];
      ArrayList<Route> holder= new ArrayList<Route>();
      G.dijkstras(s, d,true);
      if(!G.marked[d]){
        return all;
      } else {
        Stack<Integer> path = new Stack<>();
        for (int x = d; x != s; x = G.edgeTo[x]){
            path.push(x);
            System.out.println("Pushing these cities: " + cityNames[x]);
        }

        int prevVertex = s;
        while(!path.empty()){
          int v = path.pop();
          System.out.println(v);
            Route build= new Route(source,cityNames[v],G.distTo[v] - G.distTo[prevVertex],G.priceTo[v] - G.priceTo[prevVertex]);
            System.out.println(build.source + " " + build.destination);
            holder.add(build);
            source=cityNames[v];
          prevVertex = v;
        }
        all.add(temp);
        all.add(holder);

        System.out.println(all);
        return all;
      }

  }

  public Set<ArrayList<Route>> shortestDistanceItinerary(String source,
    String transit, String destination) throws CityNotFoundException {
      String keep=source;
      Set<ArrayList<Route>> all =new HashSet<>();
      ArrayList<Route> temp= new ArrayList<Route>();
      int s=-1;
      int t=-1;
      for(int i = 0; i < G.v; i++){
        if(cityNames[i].equals(source)){
          s=i;
        }
        if(cityNames[i].equals(transit)){
          t=i;
        }
      }
      G.dijkstras(s, t,false);
      if(!G.marked[t]){
        return all;
      } else {
        Stack<Integer> path = new Stack<>();
        for (int x = t; x != s; x = G.edgeTo[x]){
            path.push(x);
        }



        int prevVertex = s;
        while(!path.empty()){
          int v = path.pop();

            Route build= new Route(source,cityNames[v],G.distTo[v] - G.distTo[prevVertex],G.priceTo[v] - G.priceTo[prevVertex]);
            temp.add(build);
            source=cityNames[v];
          prevVertex = v;
        }
        int d=-1;
        for(int i = 0; i < G.v; i++){
          if(cityNames[i].equals(transit)){
            s=i;
          }
          if(cityNames[i].equals(destination)){
            d=i;
          }
        }
        G.dijkstras(s, d,false);
        if(!G.marked[d]){
          return all;
        } else {
          for (int x = d; x != s; x = G.edgeTo[x]){
              path.push(x);
          }



           prevVertex = s;
          while(!path.empty()){
            int v = path.pop();

              Route build= new Route(source,cityNames[v],G.distTo[v] - G.distTo[prevVertex],G.priceTo[v] - G.priceTo[prevVertex]);
              temp.add(build);
              source=cityNames[v];
            prevVertex = v;
          }

          all.add(temp);

        }

      }
      source=keep;
      for(int i=0; i<cityNames.length; i++){
        if(cityNames[i].equals(source)){
          s=i;
        }
      }
      temp= new ArrayList<Route>();
      G.dijkstras(s, t,true);
      if(!G.marked[t]){
        return all;
      } else {
        Stack<Integer> path = new Stack<>();
        for (int x = t; x != s; x = G.edgeTo[x]){
            path.push(x);
        }



        int prevVertex = s;
        System.out.print(cityNames[s] + " ");
        while(!path.empty()){
          int v = path.pop();

            Route build= new Route(source,cityNames[v],G.distTo[v] - G.distTo[prevVertex],G.priceTo[v] - G.priceTo[prevVertex]);
            temp.add(build);
            source=cityNames[v];
          prevVertex = v;
        }
        int d=-1;
        for(int i = 0; i < G.v; i++){
          if(cityNames[i].equals(transit)){
            s=i;
          }
          if(cityNames[i].equals(destination)){
            d=i;
          }
        }
        G.dijkstras(s, d,true);
        if(!G.marked[d]){
          return all;
        } else {
          for (int x = d; x != s; x = G.edgeTo[x]){
              path.push(x);
          }



           prevVertex = s;
          while(!path.empty()){
            int v = path.pop();

              Route build= new Route(source,cityNames[v],G.distTo[v] - G.distTo[prevVertex],G.priceTo[v] - G.priceTo[prevVertex]);
              temp.add(build);
              source=cityNames[v];
            prevVertex = v;
          }



        }

      }
      all.add(temp);
      System.out.println(all);
      return all;


  }

  public boolean addCity(String city){

     String [] expand = new String[cityNames.length+1];
     for(int i=0; i<cityNames.length;i++){
       if(cityNames[i].equals(city)){
         return false;
       }
       expand[i]=cityNames[i];
     }
     expand[cityNames.length]=city;
     cityNames=expand;
     // for(int j=0;j<cityNames.length;j++){
     //   System.out.println(cityNames[j]);
     // }
     return true;

  }

  public boolean addRoute(String source, String destination, int distance,
    double price) throws CityNotFoundException {
      int s=-1;
      int d=-1;
      for(int i=0; i<cityNames.length;i++){
        if(cityNames[i].equals(source)){
          s=i;
        }
        if(cityNames[i].equals(destination)){
          d=i;
        }
      }
      for (WeightedDirectedEdge e: G.adj(s)){
        if(e.to()==d){
          return false;
        }
      }
      G.addEdge(new WeightedDirectedEdge(s,d,distance,price));
      G.addEdge(new WeightedDirectedEdge(d,s,distance,price));
      return true;
  }

  public boolean updateRoute(String source, String destination, int distance,
    double price) throws CityNotFoundException {
      boolean check=false;
      Set<Route> temp= retrieveDirectRoutesFrom(source);
      for(Route r : temp){
        if(r.destination.equals(destination)){
          check=true;
        }
      }
      int s=-1;
      int d=-1;
      for(int i=0; i<cityNames.length;i++){
        if(cityNames[i].equals(source)){
          s=i;
        }
        if(cityNames[i].equals(destination)){
          d=i;
        }
      }
      for(WeightedDirectedEdge e: G.adj(s)){
        if(e.to()==d){
          e.setDistance(distance);
          e.setPrice(price);
        }
      }
      for(WeightedDirectedEdge w: G.adj(d)){
        if(w.to()==s){
          w.setDistance(distance);
          w.setPrice(price);
          break;
        }
      }
      return check;
  }
  private class Digraph {
    private final int v;
    private int e;
    private LinkedList<WeightedDirectedEdge>[] adj;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private double[] priceTo;


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

    public void bfs(int source,int destination, boolean equal) {
      marked = new boolean[this.v];
      distTo = new int[this.e];
      edgeTo = new int[this.v];
      priceTo = new double [this.e];

      Queue<Integer> q = new LinkedList<Integer>();
      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        priceTo[i]= INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      priceTo[source] = 0;
      marked[source] = true;
      q.add(source);

      while (!q.isEmpty()) {
        int v = q.remove();
        for (WeightedDirectedEdge w : adj(v)) {
          if (!marked[w.to()] || (edgeTo[v]==source && w.to()==destination && equal)) {
            edgeTo[w.to()] = v;
            System.out.println("parent: " +  cityNames[v] + " " + "child: " + cityNames[w.to()]);
            distTo[w.to()] = distTo[v] + 1;
            priceTo[w.to()] = priceTo[v] + 1;
            marked[w.to()] = true;

            System.out.println(" q add: " + cityNames[w.to()]);
            q.add(w.to());
          }
        }
      }
    }

    public void dijkstras(int source, int destination, boolean equal) {
      marked = new boolean[this.v];
      distTo = new int[this.v];
      edgeTo = new int[this.v];
      priceTo = new double[this.v];

      for (int i = 0; i < v; i++){
        distTo[i] = INFINITY;
        priceTo[i] = INFINITY;
        marked[i] = false;
      }
      distTo[source] = 0;
      priceTo[source] = 0;
      marked[source] = true;
      int nMarked = 1;

      int current = source;
      while (nMarked < this.v) {
        for (WeightedDirectedEdge w : adj(current)) {
          // System.out.println(distTo[current]+w.distance());
          // System.out.println(distTo[w.to()]);
          // System.out.println(cityNames[w.to()]);
          if(equal){
            if (distTo[current]+w.distance() <= distTo[w.to()]) {
          //TODO:update edgeTo and distTo
            edgeTo[w.to()]=current;
          distTo[w.to()]= distTo[current] + w.distance();
           priceTo[w.to()]= priceTo[current] + w.price();
            }
          }else{
            if (distTo[current]+w.distance() < distTo[w.to()]) {
          //TODO:update edgeTo and distTo
          edgeTo[w.to()]=current;
          distTo[w.to()]= distTo[current] + w.distance();
           priceTo[w.to()]= priceTo[current] + w.price();
            }
          }


        //   if (priceTo[current]+w.price() < priceTo[w.to()]) {
        // //TODO:update edgeTo and distTo
        // edgeTo[w.to()]=current;
        // priceTo[w.to()]= priceTo[current] + w.price();
        //   }
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
          // if(priceTo[i] < min){
          //   min = (int) priceTo[i];
          //   current = i;
          // }
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
  private class WeightedDirectedEdge {
    private final int v;
    private final int w;
    private int distance;
    private double price;
    /**
    * Create a directed edge from v to w with given weight.
    */
    public WeightedDirectedEdge(int v, int w, int distance, double price) {
      this.v = v;
      this.w = w;
      this.distance = distance;
      this.price=price;
    }

    public int from(){
      return v;
    }

    public int to(){
      return w;
    }

    public int distance(){
      return distance;
    }
    public double price(){
      return price;
    }
    public void setDistance(int d){
      this.distance=d;
    }
    public void setPrice(double p){
      this.price=p;
    }
  }
}
