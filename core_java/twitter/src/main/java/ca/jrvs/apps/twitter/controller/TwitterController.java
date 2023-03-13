package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Controller
public class TwitterController implements Controller{

  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private TwitterService service;

  @Autowired
  public TwitterController(TwitterService service){this.service = service;}


  /**
   * Parse user argument and post a tweet by calling service classes
   *
   * @param args text and coordinates
   * @return a posted tweet
   * @throws IllegalArgumentException if args are invalid
   */
  public Tweet postTweet(String[] args) {
    if(args.length != 3)
    {
      throw new IllegalArgumentException("Incorrect argument. \n Correct arguments: post text latitude:longitude (3 values)");
    }
    String text = args[1];
    String unfilteredCo = args[2];
    Double lat;
    Double lon;
    try {
      lat = Double.parseDouble(unfilteredCo.split(COORD_SEP)[0]);
      lon = Double.parseDouble(unfilteredCo.split(COORD_SEP)[1]);
    }catch(Exception e)
    {
      throw new IllegalArgumentException("Coordinate values incorrect. Cooordinates should be of form double:double");
    }
    Tweet tweet = Tweet.buildTweet(text,lon,lat);
    return service.postTweet(tweet);
  }

  /**
   * Parse user argument and search a tweet by calling service classes
   *
   * @param args id to look for
   * @return a tweet
   * @throws IllegalArgumentException if args are invalid
   */
  public Tweet showTweet(String[] args) {
    if(args.length != 3)
    {
      throw new IllegalArgumentException("Incorrect argument. \n Correct arguments: post text field1:field2:field3... ");
    }
    String id = args[1];

    String[] fields = args[2].split(COORD_SEP);
    return service.showTweet(id,fields);
  }

  /**
   * Parse user argument and delete tweets by calling service classes
   *
   * @param args ids to delete
   * @return a list of deleted tweets
   * @throws IllegalArgumentException if args are invalid
   */
  public List<Tweet> deleteTweet(String[] args) {
    if(args.length != 2)
    {
      throw new IllegalArgumentException("Incorrect argument. \n Correct arguments: post id1:id2:id3... ");
    }
    return service.deleteTweets(args[1].split(COORD_SEP));
  }
}
