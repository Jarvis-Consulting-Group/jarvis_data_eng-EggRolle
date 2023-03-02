package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ch.qos.logback.core.encoder.EchoEncoder;
import java.util.ArrayList;
import java.util.List;

public class TwitterService implements Service{

  private TwitterDao dao;


  public TwitterService(TwitterHttpHelper helper)
  {
    this.dao = new TwitterDao(helper);
  }

  /**
   * Validate and post a user input Tweet
   *
   * @param tweet tweet to be created
   * @return created tweet
   * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long
   *                                  out of range
   */
  public Tweet postTweet(Tweet tweet) {
    try
    {
      if(tweet.getText().length() > 140)
      {
        throw new IllegalArgumentException("Tweet too long Error");
      }
      else
      {
        return dao.create(tweet);
      }
    }catch(Exception e)
    {
      throw new RuntimeException(e);
    }
    //return null;
  }

  /**
   * Search a tweet by ID
   *
   * @param id     tweet id
   * @param fields set fields not in the list to null
   * @return Tweet object which is returned by the Twitter API
   * @throws IllegalArgumentException if id or fields param is invalid
   */
  public Tweet showTweet(String id, String[] fields) {
    Tweet tweet = null;
    try {
      tweet = dao.findById(id);
      for (String field : fields) {
        if (!field.contains("text")) {
          tweet.setText(null);
        } else if (!field.contains("id")) {
          tweet.setId(null);
        } else if (!field.contains("entities")) {
          tweet.setEntities(null);
        } else if (!field.contains("coordinates")) {
          tweet.setCoordinates(null);
        } else if (!field.contains("favorited")) {
          tweet.setFavorited(null);
        } else if (!field.contains("retweet_count")) {
          tweet.setRetweet_count(null);
        } else if (!field.contains("retweeted")) {
          tweet.setRetweeted(null);
        } else if (!field.contains("favorite_count")) {
          tweet.setFavorite_count(null);
        }
      }
    }catch(Exception e)
    {
      throw new IllegalArgumentException("Tweet id in invalid: " + id);
    }


    return tweet;
  }

  /**
   * Delete Tweet(s) by id(s).
   *
   * @param ids tweet IDs which will be deleted
   * @return A list of Tweets
   * @throws IllegalArgumentException if one of the IDs is invalid.
   */
  public ArrayList<Tweet> deleteTweets(String[] ids) {
    ArrayList<Tweet> deleted = new ArrayList<Tweet>();
    Tweet temp;
    String current = "";
    try{
      for(String id : ids)
      {
        current = id;
        deleted.add(dao.deleteById(id));
      }
    }catch(Exception e)
    {
      throw new IllegalArgumentException("Invalid id: " + current);
    }
    return deleted;
  }
}
