package ca.jrvs.apps.practice;

public interface RegexExc{

  /**
   * return true if filename extension is jpg or jpeg (case insensitive)
   * @param filename
   * @return
   */
  public boolean matchJpeg(String filename);

  /**
   * returen true if ip is valid
   * @param ip
   * @return
   */
  public boolean matchIP(String ip);

  /**
   * return true if line is empty
   * @param line
   * @return
   */
  public boolean isEmptyLine(String line);
}