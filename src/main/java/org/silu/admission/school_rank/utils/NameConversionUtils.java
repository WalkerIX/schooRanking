package org.silu.admission.school_rank.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameConversionUtils {
  private static Logger logger=LoggerFactory.getLogger(NameConversionUtils.class);
  private static String AGENT="Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) "
      + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
  private static String GOOGLE_URL="https://www.google.com/search?q=";
  private static String UNIVERSITY="%20University";
  private static String CLASS_RC=".rc";
  private static String ELEMENT_A=" a";
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
    return getNormalizedName(rawName, GOOGLE_URL, CLASS_RC, ELEMENT_A, UNIVERSITY);
  }
  private static String getNormalizedName(String rawName, String url, String className, String elementName,
      String decoretor){
    try{
      Document doc = Jsoup.connect(decorate(url, rawName, decoretor)).userAgent(AGENT).get();
      Elements  refs= doc.select(CLASS_RC+ELEMENT_A);
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
    return url+rawName+decoretor;
  }
}
