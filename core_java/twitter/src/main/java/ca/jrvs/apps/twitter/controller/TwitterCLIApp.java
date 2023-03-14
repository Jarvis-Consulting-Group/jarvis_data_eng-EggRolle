package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwitterCLIApp {

  private Controller controller;

  @Autowired
  public TwitterCLIApp(Controller controller) {this.controller = controller;}

  public static void main(String[] args)
  {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    TwitterHttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken
        , tokenSecret);

    TwitterDao dao = new TwitterDao(httpHelper);
    TwitterService service = new TwitterService(dao);
    Controller controller = new TwitterController(service);
    TwitterCLIApp cli = new TwitterCLIApp(controller);

    cli.start(args);

  }

  public void start(String[] args)
  {
    if(args.length < 1 || args.length > 3)
    {
      throw new IllegalArgumentException("USAGE: \n post text longitude:latitude \n show id field1:field2:field3 \n delete id1:id2:id3...");
    }
    if(args[0].toLowerCase().equalsIgnoreCase("post"))
    {
      printTweet(controller.postTweet(args));
    }
    else if(args[0].toLowerCase().equalsIgnoreCase("show"))
    {
      printTweet(controller.showTweet(args));
    }
    else if(args[0].toLowerCase().equalsIgnoreCase("delete"))
    {
      for(Tweet tweet: controller.deleteTweet(args))
      {
        printTweet(tweet);
      }
    }
  }

  private void printTweet(Tweet tweet)
  {
    try
    {
      System.out.println(JsonParser.toJson(tweet,true, true));
    }catch(JsonProcessingException e)
    {
      throw new RuntimeException("Could not convert tweet to JSON String", e);
    }
  }

}
