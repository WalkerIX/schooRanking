package org.silu.admission.school_rank.ranker;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.silu.admission.school_rank.data.School;
import org.silu.admission.school_rank.database.MapDBProcesser;
import org.silu.admission.school_rank.utils.FileUtils;
import org.silu.admission.school_rank.utils.NameMappingUtils;
import org.silu.admission.school_rank.utils.TextProcessUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SchoolRankingProcessor
{
  private static Logger logger=LoggerFactory.getLogger(SchoolRankingProcessor.class);
  // private final static String INPUT_PATH="src/main/resources/schoolRank.tsv";
  private final static String INPUT_PATH="/schoolRank.tsv";
  private final static int COL_UNIVERSITY=1;
  private final static int COL_RANK=0;
  private final static int COL_CITY=2;
  private final static int COL_STATE=3;
  private final static String TAB="\t";
  private final static String DB_NAME="universityDB";
  private final static String KEY="silu12345";
  private final static String COLLECTION="university-rank";
  /*
  public static void main( String[] args ) throws IOException, InterruptedException
  {
    //  saveToDB();
    readAndPrint();
    //getMappingInfo();
  }
   */
  private static void saveToDB(){
    List<School> schools=TextProcessUtils.SchoolTextParser(INPUT_PATH,
        COL_UNIVERSITY, COL_RANK, COL_CITY, COL_STATE, TAB);
    MapDBProcesser.init(DB_NAME, KEY, COLLECTION);
    int fails=MapDBProcesser.dumpToDB(schools);
    if(fails>0){
      logging("Warning: number of schools from input ["+schools.size()
          +" ] is not the same as saved ["+(schools.size()-fails)+"]");
    }
  }
  private static void readAndPrint(){
    MapDBProcesser.init(DB_NAME, KEY, COLLECTION);
    List<School> schools=MapDBProcesser.getAllSchoolFromDB();
    System.out.println("Total input: "+schools.size());
    for(School school : schools){
      System.out.println(school.toString());
    }
    MapDBProcesser.close();
  }
  private static void getMappingInfo() throws IOException, InterruptedException{
    Map<String, String> map=NameMappingUtils.getAllMappingPairs();
    for(String normalizedName : map.keySet()){
      System.out.println(normalizedName+"+"+map.get(normalizedName));
    }
    FileUtils.writeToFile(map, "src/main/resources/schoolMapping.tsv","+");
  }
  private static void logging(String str){
    logger.info(str);
    System.out.println(str);
  }
}
