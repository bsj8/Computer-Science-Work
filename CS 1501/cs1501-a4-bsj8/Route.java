import java.util.Objects;

final public class Route {
  public String source;
  public String destination;
  public int distance;
  public double price;

  public Route(String source, String destination, int distance, double price){
    this.source = source;
    this.destination = destination;
    this.distance = distance;
    this.price = price;
  }

  @Override
  public boolean equals(Object other){
    if (other instanceof Route){
      Route otherRoute = (Route) other;
      return equalEndPoints(otherRoute)
             && distance == otherRoute.distance
             && price == otherRoute.price;
    }
    return false;
  }

  private boolean equalEndPoints(Route otherRoute){
    return (source.equals(otherRoute.source)
            && destination.equals(otherRoute.destination))
          ||(source.equals(otherRoute.destination)
            && destination.equals(otherRoute.source));
  }

  @Override
  public int hashCode(){
    return Objects.hash(source.hashCode() + destination.hashCode(),
      distance, price);
  }

  @Override
  public String toString(){
    return source + " (" + distance + " miles, $" + price + ") "
      + destination;
  }
}
