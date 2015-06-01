package org.silu.admission.school_rank.ranker;

import org.silu.admission.school_rank.data.School;
import org.silu.admission.school_rank.database.MapDBProcesser;
import org.silu.admission.school_rank.utils.NameConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchoolRanker {
  private static Logger logger=LoggerFactory.getLogger(SchoolRanker.class);
  public static int getRank(String rawName, String dbName, String key, String collection) {
    try {
      String schoolNameInDB=NameConversionUtils.getSchoolNameInDB(rawName);
      if(schoolNameInDB.isEmpty()){
        logger.info("Fail to school rank for "+rawName);
        return -1;
      }
      MapDBProcesser.init(dbName, key, collection);
      School school=MapDBProcesser.getSchoolFromDB(schoolNameInDB);
      if((school==null) || school.getName().isEmpty()){
        logger.info("Fail to school rank for "+rawName);
        return -1;
      }
      return school.getRank().getRank();
    } catch (InterruptedException e) {
      
      e.printStackTrace();
      return -1;
    }
  }

}
