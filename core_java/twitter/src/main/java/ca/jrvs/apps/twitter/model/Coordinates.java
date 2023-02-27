package ca.jrvs.apps.twitter.model;

public class Coordinates {
  private float[] coordinates;
  String type;
  public Coordinates(float longitude, float latitude, String type)
  {
    coordinates = new float[] {longitude, latitude};
    this.type = type;
  }
}
