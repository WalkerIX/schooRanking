package org.silu.admission.school_rank.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameMappingUtils {
  private static Logger logger=LoggerFactory.getLogger(NameMappingUtils.class);
  private static String PATH="src/main/resources/schoolRank.tsv";
  private static int sleepTime=3000;
  private static Map<String, String> normToDBMap=null;
  private final static String PLUS_MARK="+";
  public static void init(){
    try{
      normToDBMap=FileUtils.readFileToMap(0, 1, PLUS_MARK, PATH);
    }catch(IOException e){
      e.printStackTrace();
      logger.error("Fail to init normToDB map due to: "+e.getMessage());
      normToDBMap=new HashMap<String, String>();
    }
  }
  /**
   * Get the name of a school in database
   * @param normName the normalized name
   * @return the name of school stored in database, empty string if fails
   * @throws IOException
   */
  public static String getSchoolNameInDB(String normName) throws IOException{
    if(normToDBMap==null){
      init();
    }
    if(normToDBMap.containsKey(normName)){
      return normToDBMap.get(normName);
    }else{
      logger.info("Fail to find school name in DB for normalized Name: "+normName);
      return "";
    }
  }
  /**
   * This method is used to generate the resources. Should not be called in general use
   * Creat pairs<normalizedNameForSchool, dbStoredNameForSchool>
   * @return pairs<normalizedNameForSchool, dbStoredNameForSchool>
   * @throws IOException
   * @throws InterruptedException
   */
  public static Map<String, String> getAllMappingPairs() throws IOException, InterruptedException{
    return getMappingPairs(0,Integer.MAX_VALUE);
  }

  /**
   * This method is used to generate the resources. Should not be called in general use
   * @param start Include
   * @param end Include
   * @return The mapping pairs from start to end
   * @throws IOException
   * @throws InterruptedException
   */
  public static Map<String, String> getMappingPairs(int start, int end) throws IOException, InterruptedException{
    Map<String, String> map=new HashMap<String, String>();
    // check start valid
    if(start>end) {
      return map;
    }
    List<String> currentNames=FileUtils.readFile(1, 1, PATH, "\t");
    if(currentNames.size()<start) {
      return map;
    }
    for(int i=start;(i<=end) && (i<=(currentNames.size()-1));i++){
      String currentName=currentNames.get(i);
      String normalizedName=NameConversionUtils.getNormalizedName(currentName);
      TimeUnit.MILLISECONDS.sleep(sleepTime);
      map.put(normalizedName, currentName);
      System.out.println(normalizedName+"+"+currentName);
    }
    return map;
  }

}
