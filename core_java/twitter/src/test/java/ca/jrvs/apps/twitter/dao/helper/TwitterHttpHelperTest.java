package ca.jrvs.apps.twitter.dao.helper;

import static org.junit.Assert.*;

import java.net.URI;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TwitterHttpHelperTest {

  @Test
  public void httpGet() throws Exception{
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println("conumserKey: " + consumerKey +"\nconsumerSecret: " + consumerSecret +
        "\naccessToken: " + accessToken + "\ntokenSecret: " +tokenSecret);
    TwitterHttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken
        , tokenSecret);
    HttpResponse response = httpHelper.httpGet(new URI("https://api.twitter.com/2/tweets/1629195312064565249"));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
}