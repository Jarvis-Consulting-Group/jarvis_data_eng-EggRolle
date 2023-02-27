package ca.jrvs.apps.twitter.dao.helper;

import java.io.IOException;
import java.net.URI;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.HttpMethod;

public class TwitterHttpHelper implements HttpHelper{

  private OAuthConsumer consumer;
  private HttpClient httpClient;

  /**
   * Main constructor
   * Sets up dependiencies using keys
   * @param consumerKey
   * @param consumerSecret
   * @param accessToken
   * @param tokenSecret
   */
  public TwitterHttpHelper(String consumerKey, String consumerSecret, String accessToken, String tokenSecret)
  {
    consumer = new CommonsHttpOAuthConsumer(consumerKey,consumerSecret);
    consumer.setTokenWithSecret(accessToken, tokenSecret);

    /**
     * Default = single connection.
     */
    httpClient = new DefaultHttpClient();

  }


  /**
   * Execute a HTTP Post call
   *
   * @param uri
   * @return
   */
  public HttpResponse httpPost(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.POST, uri, null);
    }catch (OAuthException e)
    {
      throw new RuntimeException("OAuth exception ocurred", e);
    }catch(IOException e)
    {
      throw new RuntimeException("IO exception ocurred",e);
    }

  }

  private HttpResponse executeHttpRequest(HttpMethod type, URI uri, StringEntity stringEntity)
    throws OAuthException, IOException
  {
    if(type == HttpMethod.POST)
    {
      HttpPost request = new HttpPost(uri);
      if(stringEntity != null)
      {
        request.setEntity(stringEntity);
      }
      consumer.sign(request);
      return httpClient.execute(request);
    }
    else if(type == HttpMethod.GET)
    {
      HttpGet request = new HttpGet(uri);
      consumer.sign(request);
      return httpClient.execute(request);
    }
    else
    {
      throw new IllegalArgumentException("Not a POST or GET HTTP method: "+ type.name());
    }
  }
  /**
   * Execute a HTTP Get call
   *
   * @param uri
   * @return
   */
  public HttpResponse httpGet(URI uri) {
    try {
      return executeHttpRequest(HttpMethod.GET, uri, null);
    }catch (OAuthException e)
    {
      throw new RuntimeException("OAuth exception ocurred", e);
    }catch(IOException e)
    {
      throw new RuntimeException("IO exception ocurred",e);
    }
  }
}
