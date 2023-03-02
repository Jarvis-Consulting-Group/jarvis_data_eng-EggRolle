package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class TwitterServiceIntTest {

  TwitterService service;
  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    System.out.println("conumserKey: " + consumerKey +"\nconsumerSecret: " + consumerSecret +
        "\naccessToken: " + accessToken + "\ntokenSecret: " +tokenSecret);
    TwitterHttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken
        , tokenSecret);

    service = new TwitterService(httpHelper);
  }

  @Test
  public void postTweet() {
    //Tweet tweet = new Tweet();
    String text = "hi hello3yah 33 #yeehaw @espn " + Math.random();
    Tweet tweet = Tweet.buildTweet(text, 5.0, 5.0);
    tweet = service.postTweet(tweet);
    assertEquals(tweet.getText(),text);

  }

  @Test
  public void showTweet() {
    //Tweet tweet = new Tweet();
    String id = "1631359205725593605";
    String[] fields = {"text","id","entities"};
    //Tweet tweet = Tweet.buildTweet(text, 5.0, 5.0);
    Tweet tweet = service.showTweet(id,fields);
    assertEquals(tweet.getText(),"hi hello3yah 33 #yeehaw @espn 0.47905963159462417");
    assertEquals("@espn",tweet.getEntities().getUserMentions().get(0).getName());

  }

  @Test
  public void deleteTweets() {
    //Tweet tweet = new Tweet();
    String[] ids = {"1631359205725593605", "1631359205725593205"};
    //Tweet tweet = Tweet.buildTweet(text, 5.0, 5.0);
    ArrayList<Tweet> tweets = service.deleteTweets(ids);
    assertEquals(tweets.get(0).getId(),"1631359205725593605");
    assertEquals(tweets.get(1).getId(),"1631359205725593205");



  }
}