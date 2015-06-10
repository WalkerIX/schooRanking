package org.silu.admission.school_rank.ranker;

import org.silu.admission.school_rank.data.School;
import org.silu.admission.school_rank.database.MapDBProcesser;
import org.silu.admission.school_rank.utils.NameConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchoolRanker {
  private static Logger logger=LoggerFactory.getLogger(SchoolRanker.class);
  // private final static String DEFAULT_DB_NAME="universityDB";
  private final static String DEFAULT_DB_NAME="/Users/wudili/Documents/workspace/school-rank/universityDB";
  private final static String DEFAULT_KEY="silu12345";
  private final static String DEFAULT_COLLECTION="university-rank";
  public static int getRank(String rawName, String dbName, String key, String collection) {
    try {
      String schoolNameInDB=NameConversionUtils.getSchoolNameInDB(rawName);
      if(schoolNameInDB.isEmpty()){
        logger.info("Fail to school rank for "+rawName+" because schoolName in DB is empty");
        return -1;
      }
      if(!MapDBProcesser.isInited()){
        MapDBProcesser.init(dbName, key, collection);
      }
      School school=MapDBProcesser.getSchoolFromDB(schoolNameInDB);
      if((school==null) || school.getName().isEmpty()){
        logger.info("Fail to school rank for "+rawName+" because school is null or name is empty");
        return -1;
      }
      return school.getRank().getRank();
    } catch (InterruptedException e) {
      e.printStackTrace();
      return -1;
    }
  }
  /**
   * Search the default database and map
   * @param rawName the raw name
   * @return the rank
   */
  public static int getRank(String rawName){
    return getRank(rawName, DEFAULT_DB_NAME, DEFAULT_KEY, DEFAULT_COLLECTION);
  }
}
