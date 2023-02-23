package ca.jrvs.apps.grep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep{

  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  private String regex;
  private String rootPath;
  private String outFile;

  /**
   * Implmentation of grep regular expression search implemented in Java
   *
   * @param args three arguments, pattern to search, pathto directory to search recursively, output file for matched lines
   */
  public static void main(String[] args)
  {
    if (args.length != 3)
    {
      throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
    }

    //BasicConfigurator.configure();

    JavaGrepImp javaGrepImp = new JavaGrepImp();
    javaGrepImp.setRegex(args[0]);
    javaGrepImp.setRootPath(args[1]);
    javaGrepImp.setOutFile(args[2]);

    try
    {
      javaGrepImp.process();
    } catch(Exception ex)
    {
      javaGrepImp.logger.error("Error: Unable to process ", ex);
    }
  }

  /**
   * Top level search workflow
   *
   * @throws IOException
   */
  public void process() throws IOException {

    //get all files recursively and keep list of matched lines
    try {
      List<File> allFiles = listFiles(getRootPath());
      List<String> matchedLines = new ArrayList<String>();

      for (File file : allFiles) {
        for (String line : readLines(file)) {
          if (containsPattern(line)) {
            matchedLines.add(line);
          }
        }
      }
      writeToFile(matchedLines);

    }catch(IOException err)
    {
      throw new IOException("IO Exception: ", err);
    }
  }

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under the rootDir
   */
  public List<File> listFiles(String rootDir) {

    List<File> allFiles = new ArrayList<File>();
    Queue<File> directories = new LinkedList<File>();

    File root = new File(rootDir);
    File[] firstList = root.listFiles();

    //Loop through files in root directory
    for (File file : firstList)
    {
      if(file.isDirectory())
      {
        directories.add(file);
      } else if (file.isFile())
      {
        allFiles.add(file);
      }
    }

    //Recursively search through queue of directories untilno more directories left
    while(!directories.isEmpty())
    {
      String currentDirectory = directories.remove().getPath();

      File[] files = new File(currentDirectory).listFiles();

      for (File file : files)
      {
        if(file.isDirectory())
        {
          directories.add(file);
        } else if (file.isFile())
        {
          allFiles.add(file);
        }
      }
    }
    return allFiles;
  }

  /**
   * Read a file and return all the lines
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException if a given file is not file
   */
  public List<String> readLines(File inputFile) {

    try
    {
      BufferedReader br = new BufferedReader(new FileReader(inputFile));
      String line;
      List<String> allLines = new ArrayList<String>();
      while((line = br.readLine()) != null)
      {
        allLines.add(line);
      }
      return allLines;
    }
    catch (FileNotFoundException err)
    {
      throw new IllegalArgumentException("File not found error: ", err);
    }
    catch (IOException ioErr)
    {
      throw new RuntimeException("File read error: ", ioErr);
    }
  }

  /**
   * check if a line contains the regex pattern
   *
   * @param line input string
   * @return true if match
   */
  public boolean containsPattern(String line) {
    Pattern pattern = Pattern.compile(getRegex());
    Matcher matcher  = pattern.matcher(line);
    boolean found = matcher.find();
    return found;
  }

  /**
   * Write lines to a file
   *
   * @param lines matched line
   * @throws IOException if write failed
   */
  public void writeToFile(List<String> lines) throws IOException {
    try
    {
      File outputF =new File(getOutFile());
      FileOutputStream fos = new FileOutputStream(outputF);
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

      for(String line : lines)
      {
        bw.write(line);
        bw.newLine();
      }
      bw.close();
    }
    catch(FileNotFoundException err)
    {
      throw new IOException("File not found exception: ", err);
    }
  }

  public String getRootPath() {
    return rootPath;
  }

  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  public String getRegex() {
    return regex;
  }

  public void setRegex(String regex) {
    this.regex = regex;
  }

  public String getOutFile() {
    return outFile;
  }

  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
