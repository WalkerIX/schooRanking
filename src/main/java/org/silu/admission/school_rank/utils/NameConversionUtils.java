package org.silu.admission.school_rank.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameConversionUtils {
  private static Logger logger=LoggerFactory.getLogger(NameConversionUtils.class);
  private final static int SLEEP_TIME=1000;
  private static String AGENT="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) "
      + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
  private static String GOOGLE_URL="https://www.google.com/search?q=";
  private static String UNIVERSITY="%20edu";
  private static String CLASSES=".rc .crl";
  /**
   * Get the school name in DB from a rawName
   * @param rawName rawName to process
   * @return the name in DB if successful, empty string else
   * @throws InterruptedException
   */
  public static String getSchoolNameInDB(String rawName) throws InterruptedException{
    String normalizedName=getNormalizedName(rawName);
    TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
    try {
      return NameMappingUtils.getSchoolNameInDB(normalizedName);
    } catch (IOException e) {
      logger.error("Fail to get school name in DB: "+e.getMessage());
      e.printStackTrace();
      return "";
    }
  }
  /**
   * Get the normalized name for a unviersity from a rawName
   * @param rawName
   * @return emtpy string if fails
   * @example nyu ==> New York University
   */
  public static String getNormalizedName(String rawName){
    if((rawName==null) || rawName.isEmpty()) {
      return "";
    }
    return getNormalizedName(rawName, GOOGLE_URL, CLASSES, UNIVERSITY);
  }
  private static String getNormalizedName(String rawName, String url, String className,
      String decoretor){
    try{
      // Document doc = Jsoup.connect(decorate(url, rawName, decoretor)).userAgent(AGENT).get();
      Document doc = Jsoup.connect(decorate(url, rawName, decoretor)).
          userAgent(AGENT).followRedirects(true).get();
      Elements  refs= doc.select(CLASSES);
      // check if the size is 0
      if(refs.size()==0){
        logger.warn("Fail to get normalized name for input: "+rawName);
        return "";
      }
      Element ref = refs.first();
      return ref.text();
    }catch(IOException e){
      e.printStackTrace();
      logger.error("Fail to get normalized name for input: "+rawName);
      return "";
    }
    
  }
  private static String decorate(String url, String rawName, String decoretor){
    String processedRawName=replaceSpace(rawName);
    String decoratedQuery=url+processedRawName+decoretor;
    return decoratedQuery;
  }
  /**
   * Replace space with percent code
   * @param rawName
   * @return
   */
  public static String replaceSpace(String rawName){
    return rawName.replace(" ","%20");
  }
}
