package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  @Test
  public void postTweet() throws IOException {
    when(dao.create(any())).thenReturn(new Tweet());
    try {
      service.postTweet(Tweet.buildTweet("test", 5.0, 0.0));
    }catch(Exception e)
    {
      assert(true);
    }

    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "     \"hashtags\":[],"
        + "     \"userMentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";

    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    TwitterService spy = Mockito.spy(service);
    doReturn(expectedTweet).when(spy).postTweet(Tweet.buildTweet("test with loc223",0.0,0.0));
    Tweet tweety = spy.postTweet(expectedTweet);
    assertTrue(tweety.getText().equalsIgnoreCase("Test with loc223"));
  }

  @Test
  public void getTweet() throws IOException {

    when(dao.create(any())).thenReturn(new Tweet());
    try {
      service.postTweet(Tweet.buildTweet("test", 5.0, 0.0));
    }catch(Exception e)
    {
      assert(true);
    }

    String tweetJsonStr = "{\n"
        + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
        + "   \"id\":1097607853932564480,\n"
        + "   \"text\":\"test with loc223\",\n"
        + "   \"entities\":{\n"
        + "     \"hashtags\":[],"
        + "     \"userMentions\":[]"
        + "   },\n"
        + "   \"coordinates\":null,"
        + "   \"retweet_count\":0,\n"
        + "   \"favorite_count\":0,\n"
        + "   \"favorited\":false,\n"
        + "   \"retweeted\":false\n"
        + "}";

    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);

    TwitterService spy = Mockito.spy(service);
    doReturn(expectedTweet).when(spy).deleteTweets(new String[]{"1097607853932564480"});
    Tweet tweety = spy.deleteTweets(new String[]{"1097607853932564480"}).get(0);
    assertTrue(tweety.getText().equalsIgnoreCase("1097607853932564480"));
  }

}
