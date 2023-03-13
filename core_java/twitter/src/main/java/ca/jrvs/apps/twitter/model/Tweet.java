package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Scanner;

public class Tweet {
  private String created_at;
  @JsonProperty("id")
  private Long id;
  private String id_str;
  @JsonProperty("text")
  private String text;
  @JsonProperty("entities")
  private Entities entities;
  @JsonProperty("coordinates")
  private Coordinates coordinates;
  @JsonProperty("retweet_count")
  private Integer retweet_count;
  @JsonProperty("favorite_count")
  private Integer favorite_count;
  @JsonProperty("favorited")
  private Boolean favorited;
  @JsonProperty("retweeted")
  private Boolean retweeted;


  public Tweet(String text, Entities entities, Double Longitude, Double latitude)
  {
    this.text = text;
    this.coordinates = new Coordinates(latitude,Longitude,"type");
    this.entities = entities;
  }

  public Tweet()
  {

  }
  public static Tweet buildTweet(String text, Double lon, Double lat)
  {
    ArrayList<Hashtag> hashtags = new ArrayList<Hashtag>();
    ArrayList<UserMention> user_mentions = new ArrayList<UserMention>();

    Scanner scanner = new Scanner(text);
    for(String word : text.split("\\s+"))
    {
      if(word.startsWith("#"))
      {
        //hashtags.add(word);
        Hashtag hashtag = new Hashtag(word.substring(1), text.indexOf(word), text.indexOf(word)+word.length());
        hashtags.add(hashtag);
      }
      else if(word.startsWith("@"))
      {
        //user_mentions.add(word);
        UserMention mention = new UserMention(word.substring(1), text.indexOf(word), text.indexOf(word)+word.length()
        );
        user_mentions.add(mention);
      }
    }

    Entities entity = new Entities(hashtags, user_mentions);
    Tweet built = new Tweet(text, entity, lon, lat);
    built.setEntities(entity);
    return built;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getId_str() {
    return id_str;
  }

  public void setId_str(String id_str) {
    this.id_str = id_str;
  }

  @JsonProperty("text")
  public String getText() {
    return text;
  }

  @JsonProperty("text")
  public void setText(String text) {
    this.text = text;
  }

  public Entities getEntities() {
    return entities;
  }

  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Integer getRetweet_count() {
    return retweet_count;
  }

  public void setRetweet_count(Integer retweet_count) {
    this.retweet_count = retweet_count;
  }

  public Integer getFavorite_count() {
    return favorite_count;
  }

  public void setFavorite_count(Integer favorite_count) {
    this.favorite_count = favorite_count;
  }

  public Boolean isFavorited() {
    return favorited;
  }

  public void setFavorited(Boolean favorited) {
    this.favorited = favorited;
  }

  public Boolean isRetweeted() {
    return retweeted;
  }

  public void setRetweeted(Boolean retweeted) {
    this.retweeted = retweeted;
  }
}
