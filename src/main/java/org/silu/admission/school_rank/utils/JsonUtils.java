package org.silu.admission.school_rank.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
  private static GsonBuilder builder = new GsonBuilder();
  private static Gson gson = builder.disableHtmlEscaping()
      .serializeSpecialFloatingPointValues().create();
  public static String toJsonString(Object obj){
    return gson.toJson(obj);
  }
  @SuppressWarnings("unchecked")
  public static Object getObjectFromJson(String jsonStr, @SuppressWarnings("rawtypes") Class clazz){
    return gson.fromJson(jsonStr, clazz);
  }
}
