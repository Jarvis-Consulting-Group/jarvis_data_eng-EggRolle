package ca.jrvs.apps.practice;

import ca.jrvs.apps.practice.RegexExc;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexExcImp implements RegexExc
{

  public boolean matchJpeg(String filename)
  {
    Pattern pattern = Pattern.compile(".?\\.jpeg|.?\\.jpg");
    Matcher matcher  = pattern.matcher(filename);
    boolean found = matcher.find();
    return found;
  }

  public boolean matchIP(String ip)
  {
    Pattern pattern = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
    Matcher matcher  = pattern.matcher(ip);
    boolean found = matcher.find();
    return found;
  }

  public boolean isEmptyLine(String line)
  {
    Pattern pattern = Pattern.compile("\\s");
    Matcher matcher  = pattern.matcher(line);
    boolean found = matcher.find();
    return found;
  }
}