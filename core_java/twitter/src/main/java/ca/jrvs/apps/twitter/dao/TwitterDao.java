package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class TwitterDao implements CrdDao<Tweet, String>{

  //URI constants
  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";
//URI symbols
  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";
  //Response code
  private static final int HTTP_OK = 200;

  private HttpHelper httpHelper;

  public TwitterDao(HttpHelper httpHelper){ this.httpHelper = httpHelper;}

  /**
   * Create an entity(Tweet) to the underlying storage
   *
   * @param tweet entity that to be created
   * @return created entity
   */
  public Tweet create(Tweet tweet) {
    URI uri;
    uri = getPostUri(tweet);


    HttpResponse response = httpHelper.httpPost(uri);

    return parseResponseBody(response, HTTP_OK);
  }

  public URI getPostUri(Tweet tweet)
  {
    URI uri;
    String yeah = "";
    yeah += API_BASE_URI + POST_PATH + QUERY_SYM;
    yeah += "status=" + tweet.getText();
    try
    {
      uri = new URI(yeah);
    }catch(URISyntaxException e)
    {
      throw new RuntimeException("Invalid tweet input", e);
    }
    return uri;
  }

  private Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode)
  {
    Tweet tweet = null;

    //Check status
    int status = response.getStatusLine().getStatusCode();
    if(status != expectedStatusCode)
    {
      throw new RuntimeException("Undexpected HTTP status: " + status);
    }
    if(response.getEntity() == null)
    {
      throw new RuntimeException("Empty Response Body");
    }

    //Convert response entity to str
    String jsonStr;
    try
    {
      jsonStr = EntityUtils.toString(response.getEntity());
    }catch(IOException e){
      throw new RuntimeException("Failed to convert entity to String",e);
    }

    //Deter JSON string to Tweet object
    try
    {
      tweet = JsonParser.toObjectFromJson(jsonStr, Tweet.class);
    }catch(IOException e)
    {
      throw new RuntimeException("Unable to convert JSON str to Object", e);
    }

    return tweet;
  }
  /**
   * Find an entity(Tweet) by its id
   *
   * @param s entity id
   * @return Tweet entity
   */
  public Tweet findById(String s) {
    URI uri = getGetUri(s);
    HttpResponse response = httpHelper.httpGet(uri);

    return parseResponseBody(response, HTTP_OK);
  }

  public URI getGetUri(String s)
  {
    URI uri;
    String yeah = "";
    yeah += API_BASE_URI + SHOW_PATH + QUERY_SYM;
    yeah += "id=" + s;
    try
    {
      uri = new URI(yeah);
    }catch(URISyntaxException e)
    {
      throw new RuntimeException("Invalid tweet input", e);
    }
    return uri;
  }

  /**
   * Delete an entity(Tweet) by its ID
   *
   * @param s of the entity to be deleted
   * @return deleted entity
   */
  public Tweet deleteById(String s) {
    URI uri = getDeleteUri(s);
    HttpResponse response = httpHelper.httpPost(uri);

    return parseResponseBody(response, HTTP_OK);
  }

  public URI getDeleteUri(String s) {
    URI uri;

    String yeah = "";
    yeah += API_BASE_URI + DELETE_PATH+ QUERY_SYM;
    yeah += "id=" + s;
    try
    {
      uri = new URI(yeah);
    }catch(URISyntaxException e)
    {
      throw new RuntimeException("Invalid tweet input", e);
    }
    return uri;
  }
}
