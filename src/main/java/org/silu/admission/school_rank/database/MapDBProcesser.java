package org.silu.admission.school_rank.database;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.silu.admission.school_rank.data.School;
import org.silu.admission.school_rank.utils.SchoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapDBProcesser {
  
  private static DB db;
  private static Map<String, String> map;
  private static Logger logger=LoggerFactory.getLogger(MapDBProcesser.class);
  /**
   * Dump all schools to the MapDb
   * @param schools
   * @return the number of fails
   */
  public static int dumpToDB(List<School> schools){
    if((db==null) || (map==null)) {
      logger.warn("The db is null or map is null");
      return -1;
    }
    int count=0;
    for(School school : schools){
      try{
        map.put(school.getName(), SchoolUtils.getSchoolJsonString(school));
      }catch(Exception e){
        e.printStackTrace();
        logger.info("Fail to dump school: "+school.toString()+" to database");
        count++;
        continue;
      }
    }
    db.commit();
    return count;
  }
  /**
   * Init the database
   * @param dbName the name of database
   * @param key the password
   * @param collection the name of collection
   * @return false if init fails
   */
  public static boolean init(String dbName, String key, String collection){
    logger.info("Start DB ... ...");
    try{
      db=DBMaker.newFileDB(new File(dbName)).closeOnJvmShutdown()
          .encryptionEnable("password")
          .make();
      if(db.getHashMap(collection)==null) {
        map=db.createHashMap(collection).make();
      }else{
        map=db.getHashMap(collection);
      }
      return true;
    }catch(Exception e){
      logger.info("Fail to init database: "+dbName);
      e.printStackTrace();
      return false;
    }
  }
  
  /**
   * Get the school object from db
   * @param schoolName
   * @return null if db is off or the school doesn't exist
   */
  public static School getFromDB(String schoolName, Map<String, String> cMap){
    if((db==null) || (cMap==null)) {
      return null;
    }
    String jsonStr=cMap.get(schoolName);
    if(jsonStr==null) {
      return null;
    }
    try{
      School school=SchoolUtils.createSchoolFromJson(jsonStr);
      return school;
    }catch(Exception e){
      logger.warn("Fail to get school info from db for "+schoolName);
      e.printStackTrace();
      return null;
    }
  }
  
  /**
   * Get all schools in DB
   * @return all schools in db
   */
  public static List<School> getAllSchoolFromDB(String mapName){
    List<School> list=new ArrayList<School>();
    if(db==null){
      logger.warn("db is null or map is null");
      return list;
    }
    for(String schoolName : map.keySet()){
      list.add(getFromDB(schoolName, map));
    }
    return list;
  }
  /**
   * Close db
   */
  public static void close(){
    db.close();
    logger.info("Close DB ... ...");
  }
}
