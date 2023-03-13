package ca.jrvs.apps.twitter.model;

public class Hashtag {
  private String text;
  private int[] indices;

  public Hashtag(String text, int start, int end)
  {
    this.text = text;
    indices = new int[] {start,end};
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }
}
