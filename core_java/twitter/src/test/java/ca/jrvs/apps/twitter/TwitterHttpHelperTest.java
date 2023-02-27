package ca.jrvs.apps.twitter;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import java.net.URI;
import javax.swing.text.html.parser.Entity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class TwitterHttpHelperTest
{
  public static void main(String[] args) throws Exception
  {
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
