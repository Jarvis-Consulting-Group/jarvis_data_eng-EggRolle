package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.*;

import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Before;
import org.junit.Test;


public class TwitterDaoTest {

  private TwitterDao dao;
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

    this.dao = new TwitterDao(httpHelper);
  }

  @Test
  public void create() {
    String hashTag = "#yeehaw";
    String text = "@obama hi hello #yeehaw ";
    Double lat = 1d;
    Double lon = -1d;
    Tweet tweet = Tweet.buildTweet(text, lat, lon);

    assertEquals(text, tweet.getText());

    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates());

    assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }
}