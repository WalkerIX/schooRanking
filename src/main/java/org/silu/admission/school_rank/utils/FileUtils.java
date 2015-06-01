package org.silu.admission.school_rank.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FileUtils {
  /**
   *
   * @param colStart: include
   * @param colEnd: include
   * @param inputPath
   * @return
   * @throws IOException
   */
  public static List<String> readFile(int colStart, int colEnd, String inputPath, String seperator)
      throws IOException{
    List<String> list=new ArrayList<String>();
    if(colStart>colEnd) {
      return list;
    }
    BufferedReader reader=new BufferedReader(new FileReader(inputPath));
    String line="";
    while((line=reader.readLine())!=null){
      String[] parts=line.split(seperator);
      if(parts.length<=colStart){
        System.out.println("Error: the input file does not have column "+colStart);
        reader.close();
        return list;
      }
      StringBuilder builder=new StringBuilder();
      for(int i=colStart;i<=Math.min(parts.length-1,colEnd);i++) {
        builder.append(parts[i]+seperator);
      }
      list.add(builder.substring(0,builder.length()-1));
    }
    reader.close();
    return list;
  }
  
  /**
   *
   * @param colStart: include
   * @param colEnd: include
   * @param inputPath
   * @param sepInput: the seperator from the input file
   * @param sepOutput: the seperator to the output file
   * @return A list of each line
   * @throws IOException
   */
  public static List<String> readFile(int colStart, int colEnd, String inputPath, String sepInput, String sepOutput)
      throws IOException{
    List<String> list=new ArrayList<String>();
    if(colStart>colEnd) {
      return list;
    }
    BufferedReader reader=new BufferedReader(new FileReader(inputPath));
    String line="";
    while((line=reader.readLine())!=null){
      String[] parts=line.split(sepInput);
      if(parts.length<=colStart){
        System.out.println("Error: the input file does not have column "+colStart);
        reader.close();
        return list;
      }
      StringBuilder builder=new StringBuilder();
      for(int i=colStart;i<=Math.min(parts.length-1,colEnd);i++) {
        builder.append(parts[i]+sepOutput);
      }
      list.add(builder.substring(0,builder.length()-1));
    }
    reader.close();
    return list;
  }
  /**
   *
   * @param colKey
   * @param colValue
   * @param inputPath
   * @return By default the seperator is tab
   * @throws IOException
   */
  public static Map<String, String> readFileToMap(int colKey, int colValue, String inputPath)
      throws IOException{
    return readFileToMap(colKey, colValue, "\t", inputPath);
  }
  
  /**
   * Read the file to Map
   * @param colKey
   * @param colValue
   * @param seperator
   * @param inputPath
   * @return
   * @throws IOException
   */
  public static Map<String, String> readFileToMap(int colKey, int colValue, String seperator,
      String inputPath)
          throws IOException{
    Map<String, String> map=new HashMap<String, String>();
    BufferedReader reader=new BufferedReader(new FileReader(inputPath));
    String line="";
    while((line=reader.readLine())!=null){
      String[] parts=line.split(seperator);
      if((colKey>=parts.length) || (colValue>=parts.length)){
        System.out.println("Error: the input file doesn not have column "+Math.max(colKey, colValue));
        reader.close();
        return map;
      }
      map.put(parts[colKey], parts[colValue]);
    }
    reader.close();
    return map;
  }
  /**
   *
   * @param inputPath
   * @return set
   * @throws IOException
   */
  public static Set<String> readFileToSet(String inputPath) throws IOException{
    Set<String> set=new HashSet<String>();
    BufferedReader reader=new BufferedReader(new FileReader(inputPath));
    String line="";
    while((line=reader.readLine())!=null){
      set.add(line);
    }
    reader.close();
    return set;
  }
  /**
   *
   * @param <T>
   * @param iterable
   * @param outputPath
   * @throws FileNotFoundException
   */
  public static <T> void writeToFile(Iterable<T> iterable, String outputPath)
      throws FileNotFoundException{
    if(iterable==null){
      System.out.println("The input iterable is none");
      return;
    }
    PrintWriter out = new PrintWriter(new String(outputPath));
    for(T line : iterable) {
      out.println(line);
    }
    out.close();
  }
  
  /**
   * By default the seperator is tab
   * @param <T>
   * @param <V>
   * @param <V>
   * @param map
   * @param outputPath
   * @throws FileNotFoundException
   */
  public static <T, V> void writeToFile(Map<T, V> map, String outputPath)
      throws FileNotFoundException{
    writeToFile(map, outputPath, "\t");
  }
  
  /**
   *
   * @param map
   * @param outputPath
   * @param seperator the output seperator between key-value
   * @throws FileNotFoundException
   */
  public static <T, V> void writeToFile(Map<T, V> map, String outputPath, String seperator)
      throws FileNotFoundException{
    if((map==null) || (map.size()==0)){
      System.out.println("WriteToFile Error: The input map is none or empty");
      return;
    }
    PrintWriter out = new PrintWriter(new FileOutputStream(outputPath),true);
    for(T key : map.keySet()) {
      out.println(key+seperator+map.get(key));
    }
    out.close();
  }

  /**
   *
   * @param inputPath
   * @param outputPath
   * @param colStart
   * @param colEnd
   * @throws IOException
   */
  public static void readWriteColumns(String inputPath, String outputPath, int colStart, int colEnd)
      throws IOException{
    if(colStart>colEnd) {
      return;
    }
    BufferedReader reader=new BufferedReader(new FileReader(inputPath));
    PrintWriter out = new PrintWriter(new String(outputPath));
    String line="";
    int lineNum=0;
    while((line=reader.readLine())!=null){
      String[] parts=line.split("\t");
      lineNum++;
      if(parts.length<=colStart){
        System.out.println("Error: the input file does not have column "+colStart
            +" at line: "+lineNum);
        continue;
      }
      StringBuilder builder=new StringBuilder();
      for(int i=colStart;i<=Math.min(parts.length-1,colEnd);i++) {
        builder.append(parts[i]+"\t");
      }
      out.println(builder.substring(0,builder.length()-1));
    }
    reader.close();
    out.close();
  }
}
