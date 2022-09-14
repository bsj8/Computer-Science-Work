# CS 1501 – Algorithm Implementation – Assignment #4

_(Assignment adapted from Dr. John Ramirez’s CS 1501 class.)_

Due: Monday December 20th @ 11:59pm on Gradescope

_There is no late submission deadline for this assignment._

## Overview

Purpose: The purpose of this assignment is to make you practice implementing some graph
algorithms and to see how they can be used in a somewhat practical way.

**Feel free to use as much code as you need from Lab 8 and Lab 9.**

## Details

You are to implement a simple information program for a fictional airline.

.	Your program should be able to handle the following queries as specified in `AirlineInterface.java`:

1. Load from a file (whose name is input by the user) a list of all of the service routes that your airline runs. These routes include the cities served and the various non-stop destinations from each city. Clearly, you will be interpreting these routes as a graph with the cities being the vertices and the non-stop trips being the edges. To keep things simple, assume that all routes are bidirectional, so that you can use an undirected graph (this is not necessarily an incorrect assumption, as airlines most often fly non-stop routes in both directions). Alternatively, you could use a directed graph, with two edges (one for each direction) for each trip. You can think of these as the current active routes, which would be updated periodically in case a route is cancelled or perhaps snow closes an airport somewhere. The routes (edges) should have 2 different weights: one weight based on the **distance** in miles between the cities and the other based on the **price** of a ticket between the cities. There are two example input files in the repository: `a4data1.txt` and `a4data2.txt`. Your program may be tested on other data files.

  ```java
  /**
   * reads the city names and the routes from a file
   * @param fileName the String file name
   * @return true if routes loaded successfully and false otherwise
   */
  public boolean loadRoutes(String fileName);
  ```

2. Return a set of all city names served by the Airline.
```java
/**
 * returns the set of city names in the Airline system
 * @return a (possibly empty) Set<String> of city names
 */
public Set<String> retrieveCityNames();
```

3. Return a set of all non-stop `Route`s out of a given city. Please check `Route.java` for the specification of the `Route` objects.

  ```java
  /**
   * returns the set of direct routes out of a given city
   * @param city the String city name
   * @return a (possibly empty) Set<Route> of Route objects representing the direct routes out
   * of city
   * @throws CityNotFoundException if the city is not found in the Airline
   * system
   */
  public Set<Route> retrieveDirectRoutesFrom(String city) throws CityNotFoundException;
  ```

4.	Allow for each of the three "shortest path" searches below. If multiple paths "tie" for the shortest, you should return any one of them.

    a.	Shortest path based on number of hops (individual segments) from the source to the destination. This option could be useful to passengers who prefer fewer segments for one reason or other (e.g., traveling with small children).

    ```java
    /**
     * finds fewest-stops path(s) between two cities
     * @param source the String source city name
     * @param destination the String destination city name
     * @return a (possibly empty) Set<ArrayList<String>> of fewest-stops paths. Each path is an
     * ArrayList<String> of city names that includes the source and destination
     * city names.
     * @throws CityNotFoundException if any of the two cities are not found in the
     * Airline system
     */
    public Set<ArrayList<String>> fewestStopsItinerary(String source, String destination) throws CityNotFoundException;
    ```

    b.	Shortest path based on total miles (one way) from the source to the destination. Assuming distance and time are directly related, this could be useful to passengers who are in a hurry. It would also appeal to passengers who want to limit their carbon footprints.

    ```java
    /**
     * finds shortest distance path(s) between two cities
     * @param source the String source city name
     * @param destination the String destination city name
     * @return a (possibly empty) Set<ArrayList<Route>> of shortest-distance paths. Each path is
     * an ArrayList<Route> of Route objects that includes a Route out of source and into destination.
     * @throws CityNotFoundException if any of the two cities are not found in the
     * Airline system
     */
    public Set<ArrayList<Route>> shortestDistanceItinerary(String source, String destination) throws CityNotFoundException;
    ```

    c. Shortest path from a source city to a destination city through a third (transit) city. In other words, "What is the shortest path from A to B given that I
    want to stop at C for a while?"
    ```java
    /**
     * finds shortest distance path(s) between two cities going through
     * a third city
     * @param source the String source city name
     * @param transit the String transit city name
     * @param destination the String destination city name
     * @return a (possibly empty) Set<ArrayList<Route>> of shortest-distance paths. Each path is
     * an ArrayList<Route> of Route objects that includes a Route into source, into and out of transit, and
     * into destination.
     * @throws CityNotFoundException if any of the three cities are not found in the
     * Airline system
     */
    public Set<ArrayList<Route>> shortestDistanceItinerary(String source, String transit, String destination) throws CityNotFoundException;
    ```

5. Add a new city to be serviced by the Airline.
  ```java
  /**
   * adds a city to the Airline system
   * @param city  the city name
   * @return true if city added successfully and false if the city already exists
   */
  public boolean addCity(String city);
  ```

6.	Add a new route to the schedule. Both cities have to already exist with no prior route between them. Clearly, adding a new route to the schedule may affect the searches and algorithms indicated above.
```java
/**
 * adds a direct route between two existing cities to the Airline system
 * @param source the source city name
 * @param destination the destination city name
 * @param distance the int distance between the two cities in miles
 * @param price the double ticket price in dollars
 * @return true if route added successfully and false if a route already
 * exists between the two cities
 * @throws CityNotFoundException if any of the two cities are not found in the
 * Airline system
 */
public boolean addRoute(String source, String destination, int distance, double price) throws CityNotFoundException;
```

7.	Update an already existing route between two cities. Clearly, updating a route in the schedule may affect the searches and algorithms indicated above.
```java
/**
 * updates a direct route between two existing cities in the Airline system
 * @param source the String source city name
 * @param destination the String destination city name
 * @param distance the int distance between the two cities in miles
 * @param price the double ticket price in dollars
 * @return true if route updated successfully and false if no route already
 * exists between the two cities
 * @throws CityNotFoundException if any of the two cities are not found in the
 * Airline system
 */
public boolean updateRoute(String source, String destination, int distance, double price) throws CityNotFoundException;
```

.	You must encapsulate the functionality of your airline in a **single, cohesive class** named `AirlineSystem.java`, which has to `implements` the `AirlineInterface`. You must represent the graph using **adjacency lists**. The cities should minimally have a string for a name and any other information you want to add. The edges will have multiple weights (distance, price). **Again, you may use the code from Lab 8 and Lab 9**.

.	You must use the algorithms and implementations discussed in class for your queries. For example,  for the shortest distance paths you must use **Dijkstra’s algorithm** and to obtain the shortest-hops path you must use **breadth-first search**.

. The test program `AirlineTest.java` has a menu-driven loop that asks the user for many choices. Please use this program to test your code.

.	Below is an example input file, visual graph, and response to some of the queries listed above. The index numbers for the vertices are based on the order that the cities appear in the file (note that the indexing starts at 1).

![](docs/a4.png)

## Extra Credit (10 points)

For each of the three "shortest path" searches listed above, if multiple paths "tie" for the shortest, you should return **all** of them.

## Submission Requirements

You must submit to Gradescope at least the following file:
1.	`AirlineSystem.java`

The idea from your submission is that the autograder can compile and run your programs from the command line WITHOUT ANY additional files or changes, so be sure to test it thoroughly before submitting it. If the autograder cannot compile or run your submitted code it will be graded as if the program does not work.

Note: If you use an IDE such as NetBeans, Eclipse, or IntelliJ, to develop your programs, make sure they will compile and run on the command-line before submitting – this may require some modifications to your program (such as removing some package information).

## Rubrics

Item|Points
----|------|
(**Mandatory**) `loadRoutes`|	12
(**Mandatory**) `retrieveCityNames`|	12
(**Mandatory**) `retrieveDirectRoutesFrom`|	12
`fewestStopsItinerary`|	12
`shortestDistanceItinerary`|	12
`shortestDistanceItinerary` with transit city|	10
`addCity`|	10
`addRoute`|	10
`updateRoute`|	10
Extra Credit|	10 points
