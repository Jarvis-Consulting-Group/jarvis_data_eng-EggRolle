package ca.jrvs.apps.twitter.model;

public class UserMention {
  private String name;
  private int[] indices;
  private String screen_name;
  private int id;
  private String id_str;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  public String getScreen_name() {
    return screen_name;
  }

  public void setScreen_name(String screen_name) {
    this.screen_name = screen_name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getId_str() {
    return id_str;
  }

  public void setId_str(String id_str) {
    this.id_str = id_str;
  }

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
