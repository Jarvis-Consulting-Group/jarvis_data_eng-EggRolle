package ca.jrvs.apps.twitter.model;

import java.util.ArrayList;

public class Entities {
  private ArrayList<Hashtag> hashtags;
  private ArrayList<UserMention> userMentions;

  public Entities()
  {

  }
  public Entities(ArrayList<Hashtag> hashtags, ArrayList<UserMention> mentions)
  {
    this.hashtags = hashtags;
    this.userMentions = mentions;
  }

  public ArrayList<Hashtag> getHashtags() {
    return hashtags;
  }

  public void setHashtags(ArrayList<Hashtag> hashtags) {
    this.hashtags = hashtags;
  }

  public ArrayList<UserMention> getUserMentions() {
    return userMentions;
  }

  public void setUserMentions(ArrayList<UserMention> userMentions) {
    this.userMentions = userMentions;
  }
}
