package ca.jrvs.apps.twitter.model;

public class UserMention {
  private String name;
  private int[] indices;
  private String screen_name;
  private int id;
  private String id_str;

  public UserMention(String name, int start, int end)
  {
    String name2 = "placehgolder";
    int id = 1;
    this.name = name;
    indices = new int[]{start,end};
    this.screen_name = name2;
    this.id = id;
    this.id_str = Integer.toString(id);
  }

}
