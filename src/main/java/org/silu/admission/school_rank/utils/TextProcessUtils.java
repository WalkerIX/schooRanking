package org.silu.admission.school_rank.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.silu.admission.school_rank.data.Rank;
import org.silu.admission.school_rank.data.School;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextProcessUtils {
  private static Logger logger=LoggerFactory.getLogger(TextProcessUtils.class);
  /**
   * Crete School from an input line
   * @param line The text line got from input
   * @param colU column of university
   * @param colRank column of rank
   * @param colCity column of city
   * @param colState column of state
   * @return school if scucceed, else null
   */
  public School createSchool(String line, int colU, int colRank, int colCity,
      int colState, String seperator){
    String[] cells=line.split(seperator);
    if(cells.length<=2) {
      return null;
    }
    School school=new School(cells[colU]);
    school.setRank(new Rank(Integer.parseInt(cells[colRank])));
    if(cells.length>colCity) {
      school.setCity(cells[colCity]);
    }
    if(cells.length>colState) {
      school.setState(cells[colState]);
    }
    return school;
  }
  
  /**
   * Create schools from text file
   * @param inputPath
   * @param colU
   * @param colRank
   * @param colCity
   * @param colState
   * @param seperator
   * @return a list of schools from text file
   */
  public List<School> SchoolTextParser(String inputPath, int colU, int colRank,
      int colCity, int colState, String seperator){
    List<School> list=new ArrayList<School>();
    try {
      List<String> lines=FileUtils.readFile(0, 99, inputPath, seperator);
      for(String line : lines){
        School school=createSchool(line, colU, colRank, colCity, colState, seperator);
        if(school==null){
          logger.warn("Fail to create school for line: "+line);
          continue;
        }
        list.add(school);
      }
      return list;
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("Fail to create Schools from file: "+inputPath);
      return list;
    }
  }
}
