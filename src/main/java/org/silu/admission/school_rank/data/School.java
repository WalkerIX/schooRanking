package org.silu.admission.school_rank.data;

import java.util.HashMap;
import java.util.Map;

public class School {
  protected String name;
  protected Rank rank;
  protected String state;
  protected String city;
  protected Map<String, Rank> majorRanks;
  protected Map<String, Object> fieldMap;
  public School(String name){
    this.name=name;
    majorRanks=new HashMap<String, Rank>();
    fieldMap=new HashMap<String, Object>();
  }
  public School(String name, String city, String state){
    this(name);
    setCity(city);
    setState(state);
  }
  public School(String name, String city, String state, Rank rank){
    this(name, city, state);
    setRank(rank);
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public Rank getRank() {
    return rank;
  }
  public void setRank(Rank rank) {
    this.rank = rank;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public void setMajorRank(String major, int rank){
    majorRanks.put(major, new Rank(rank));
  }
  public void setMajorRank(String major, int head, int bottom){
    majorRanks.put(major, new RangedRank(head, bottom));
  }
  public Rank getMajorRank(String major){
    if(majorRanks.containsKey(major)) {
      return majorRanks.get(major);
    }
    return new Rank(-1);
  }
  public void setField(String fieldName, Object fieldValue){
    fieldMap.put(fieldName, fieldValue);
  }
  public Object getField(String fieldName){
    return fieldMap.containsKey(fieldName)?fieldMap.get(fieldName):null;
  }
}
