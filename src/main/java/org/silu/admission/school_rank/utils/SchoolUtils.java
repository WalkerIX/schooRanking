package org.silu.admission.school_rank.utils;

import org.silu.admission.school_rank.data.School;

public class SchoolUtils {
  public static String getSchoolJsonString(School school){
    try{
      return JsonUtils.toJsonString(school);
    }catch(Exception e){
      e.printStackTrace();
      return "";
    }
  }

  public static School createSchoolFromJson(String jsonStr){
    try{
      Object obj=JsonUtils.getObjectFromJson(jsonStr, School.class);
      return (School)obj;
    }catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }
}
