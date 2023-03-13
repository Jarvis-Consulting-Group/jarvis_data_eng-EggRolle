package ca.jrvs.apps.twitter.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {

  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  @Test
  public void showTweet() throws Exception{
    //test failed request
    String hashtag = "#abc";
    String text = "@someone sometext " + hashtag + " " + System.currentTimeMillis();
    Double lat = 1d;
    Double lon = -1d;
    //Exception
    when(mockHelper.httpPost(isNotNull(),isNotNull())).thenThrow(new RuntimeException("mock"));
    try{
      dao.create(Tweet.buildTweet(text,lon,lat));

      fail();
    }catch(RuntimeException e)
    {
      //throw new Exception(e);
      assertTrue(true);
    }

    //Test happy path

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

    //Test post/parse
    when(mockHelper.httpPost(isNotNull(),isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonParser.toObjectFromJson(tweetJsonStr, Tweet.class);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(),anyInt());
    Tweet tweet = spyDao.create(Tweet.buildTweet(text,lon,lat));
    assertNotNull(tweet);

    //Test Delete
    when(mockHelper.httpDelete(isNotNull())).thenReturn(null);
    int expectedResponse = 200;

  }

}
