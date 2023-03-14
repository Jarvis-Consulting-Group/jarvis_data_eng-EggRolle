package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Entities;
import ca.jrvs.apps.twitter.model.Hashtag;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.model.UserMention;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.apache.catalina.User;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import javax.json.JsonArray;
import javax.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
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
  private static final int HTTP_OK = 201;

  private HttpHelper httpHelper;

  @Autowired
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
    String body = "{\"text\":\"" + tweet.getText() + "\"}";
    //System.out.println(body);
    //String body = "{\"text\":\"Hello World from Java4!\"}";
    try {
      StringEntity stringEntity = new StringEntity(body);
      HttpResponse response = httpHelper.httpPost(uri,stringEntity);
      return parseResponseBody(response, HTTP_OK);
    }catch(Exception e)
    {
      throw new RuntimeException("Argument error: ",e);
    }


  }

  public URI getPostUri(Tweet tweet)
  {
    URI uri;
    String yeah = "";
    yeah += API_BASE_URI + POST_PATH + QUERY_SYM;
    yeah += "status=" + tweet.getText();
    try
    {
      //uri = new URI(yeah);
      uri = new URI("https://api.twitter.com/2/tweets");
    }catch(URISyntaxException e)
    {
      throw new RuntimeException("Invalid tweet input", e);
    }
    return uri;
  }

  @JsonIgnoreProperties(ignoreUnknown = true)
  //@JsonProperty("data")
  Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode)
  {
    Tweet tweet = null;

    //Check status
    int status = response.getStatusLine().getStatusCode();
    if(status != 200 && status != 201)
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
      System.out.println(response);
      jsonStr = EntityUtils.toString(response.getEntity());

    }catch(IOException e){
      throw new RuntimeException("Failed to convert entity to String",e);
    }

    //Deter JSON string to Tweet object
      System.out.println(jsonStr);
      JSONObject jo = new JSONObject(jsonStr);

      //Get created Tweet object (post returns incomplete values)
      long id = jo.getJSONObject("data").getLong("id");
      try {
        response = httpHelper.httpGet(new URI("https://api.twitter.com/2/tweets?tweet.fields=entities,public_metrics,geo&ids=" + String.valueOf(id)));
        jsonStr = EntityUtils.toString(response.getEntity());
      }catch(Exception e){}

      //Manually parse through JSON object as V2 API does not match internal representation
      jo = new JSONObject(jsonStr);
      tweet = new Tweet();
      jo = jo.getJSONArray("data").getJSONObject(0);

      //Get text field and ID field
      tweet.setText(jo.getString("text"));
      tweet.setId(jo.getLong("id"));
      tweet.setId_str(String.valueOf(tweet.getId()));


      //Get all hashtags
    JSONObject jo2 = jo;
    ArrayList<Hashtag> tags = new ArrayList<Hashtag>();
    try {
      JSONArray ja = jo2.getJSONObject("entities").getJSONArray("hashtags");
      int n = ja.length();

      for (int i = 0; i < n; i++) {
        JSONObject temp = ja.getJSONObject(i);
        Hashtag tag = new Hashtag(temp.getString("tag"), temp.getInt("start"),
            temp.getInt("end"));
        tags.add(tag);
      }
      //Get all user mentions

      }catch(Exception e) {}
    ArrayList<UserMention> mentions = new ArrayList<UserMention>();
    try {
      JSONArray ja = jo2.getJSONObject("entities").getJSONArray("mentions");
      int n = ja.length();

      for (int i = 0; i < n; i++) {
        JSONObject temp = ja.getJSONObject(i);
        UserMention mention = new UserMention(temp.getString("tag"), temp.getInt("start"),
            temp.getInt("end"));
        mentions.add(mention);
      }
      //Get all user mentions

    }catch(Exception e) {}

    tweet.setEntities(new Entities(tags, mentions));

    //Get retweet and favorite information
    try {
      Integer retweets = Integer.parseInt(
          jo2.getJSONObject("public_metrics").getString("retweet_count"));
      tweet.setRetweet_count(retweets);
      if (retweets > 0) {
        tweet.setRetweeted(true);
      } else {
        tweet.setRetweeted(false);
      }

      Integer likes = Integer.parseInt(jo2.getJSONObject("public_metrics").getString("like_count"));
      tweet.setFavorite_count(likes);
      if (likes > 0) {
        tweet.setFavorited(true);
      } else {
        tweet.setFavorited(false);
      }
    }
    catch(Exception e) {}


      //Get coordinates
      //tweet.setEntities(new Entities(tags, new ArrayList<UserMention>()));
      //Double lat = jo2.getJSONObject("geo").getJSON
      //Coordinates co = new Coordinates();


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

    return parseResponseBody(response, 201);
  }

  public URI getGetUri(String s)
  {
    URI uri;
    String yeah = "";
    yeah += API_BASE_URI + SHOW_PATH + QUERY_SYM;
    yeah += "id=" + s;
    try
    {
      //uri = new URI(yeah);
      uri = new URI("https://api.twitter.com/2/tweets/" + s);
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
    try {
      HttpResponse response = httpHelper.httpDelete(uri);
      return parseResponseBody(response, HTTP_OK);

    }catch(Exception e)
    {
      throw new RuntimeException("placeholder:", e);
    }
  }

  public URI getDeleteUri(String s) {
    URI uri;

    String yeah = "";
    yeah += API_BASE_URI + DELETE_PATH+ QUERY_SYM;
    yeah += "id=" + s;
    try
    {
      uri = new URI("https://api.twitter.com/2/tweets/" + s);
    }catch(URISyntaxException e)
    {
      throw new RuntimeException("Invalid tweet input", e);
    }
    return uri;
  }
}
