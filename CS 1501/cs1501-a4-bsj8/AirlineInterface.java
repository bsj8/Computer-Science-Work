import java.util.Set;
import java.util.ArrayList;

public interface AirlineInterface {

  /**
   * reads the city names and the routes from a file
   * @param fileName the String file name
   * @return true if routes loaded successfully and false otherwise
   */
  public boolean loadRoutes(String fileName);

  /**
   * returns the set of city names in the Airline system
   * @return a (possibly empty) Set<String> of city names
   */
  public Set<String> retrieveCityNames();

  /**
   * returns the set of direct routes out of a given city
   * @param city the String city name
   * @return a (possibly empty) Set<Route> of Route objects representing the
   * direct routes out of city
   * @throws CityNotFoundException if the city is not found in the Airline
   * system
   */
  public Set<Route> retrieveDirectRoutesFrom(String city)
    throws CityNotFoundException;

  /**
   * finds fewest-stops path(s) between two cities
   * @param source the String source city name
   * @param destination the String destination city name
   * @return a (possibly empty) Set<ArrayList<String>> of fewest-stops paths.
   * Each path is an ArrayList<String> of city names that includes the source
   * and destination city names.
   * @throws CityNotFoundException if any of the two cities are not found in the
   * Airline system
   */
  public Set<ArrayList<String>> fewestStopsItinerary(String source,
    String destination) throws CityNotFoundException;


  /**
   * finds shortest distance path(s) between two cities
   * @param source the String source city name
   * @param destination the String destination city name
   * @return a (possibly empty) Set<ArrayList<Route>> of shortest-distance
   * paths. Each path is an ArrayList<Route> of Route objects that includes a
   * Route out of the source and a Route into the destination.
   * @throws CityNotFoundException if any of the two cities are not found in the
   * Airline system
   */
  public Set<ArrayList<Route>> shortestDistanceItinerary(String source,
    String destination) throws CityNotFoundException;


  /**
   * finds shortest distance path(s) between two cities going through a third
   * city
   * @param source the String source city name
   * @param transit the String transit city name
   * @param destination the String destination city name
   * @return a (possibly empty) Set<ArrayList<Route>> of shortest-distance
   * paths. Each path is an ArrayList<Route> of city names that includes
   * a Route out of source, into and out of transit, and into destination.
   * @throws CityNotFoundException if any of the three cities are not found in
   * the Airline system
   */
  public Set<ArrayList<Route>> shortestDistanceItinerary(String source,
    String transit, String destination) throws CityNotFoundException;

  /**
   * adds a city to the Airline system
   * @param city the city name
   * @return true if city added successfully and false if the city already
   * exists
   */
  public boolean addCity(String city);

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
  public boolean addRoute(String source, String destination, int distance,
    double price) throws CityNotFoundException;

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
  public boolean updateRoute(String source, String destination, int distance,
    double price) throws CityNotFoundException;

}
