package ca.jrvs.apps.twitter.model;

public class Coordinates {
  private Double[] coordinates;
  String type;
  public Coordinates(Double longitude, Double latitude, String type)
  {
    coordinates = new Double[] {longitude, latitude};
    this.type = type;
  }
}
