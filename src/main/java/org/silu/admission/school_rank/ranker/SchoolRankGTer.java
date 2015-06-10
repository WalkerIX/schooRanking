package org.silu.admission.school_rank.ranker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchoolRankGTer {

  private final static String INPUT_PATH="/Users/wudili/school/gter.tsv";
  private static String OUTPUT_PATH="/Users/wudili/school/gter.processed.tsv";
  private final static int colRawName=7;
  private static Logger logger=LoggerFactory.getLogger(SchoolRankGTer.class);
  public static void addRankGter(int lStart, int lEnd) throws IOException{
    BufferedReader reader=new BufferedReader(new FileReader(INPUT_PATH));
    OUTPUT_PATH+=Integer.toString(lStart)+"-"+Integer.toString(lEnd);
    PrintWriter out = new PrintWriter(new String(OUTPUT_PATH));
    String line="";
    int lineNum=0;
    while((line=reader.readLine())!=null){
      lineNum++;
      if(lineNum<lStart) {
        continue;
      }
      if(lineNum>lEnd) {
        break;
      }
      String[] parts=line.split("\t");
      StringBuilder builder=new StringBuilder();
      for(int i=0;i<=colRawName;i++) {
        builder.append(parts[i]+"\t");
      }
      int rank=SchoolRanker.getRank(parts[colRawName]);
      System.out.println(parts[colRawName]+": "+rank);
      builder.append(rank+"\t");
      for(int i=colRawName+1;i<parts.length;i++) {
        builder.append(parts[i]+"\t");
      }
      out.println(builder.substring(0,builder.length()-1));
    }
    reader.close();
    out.close();
    System.out.println((lineNum-1)+" lines are processed");
  }
  public static void addRankGter() throws IOException{
    addRankGter(0,Integer.MAX_VALUE);
  }
  
  public static void main(String[] args) throws IOException {
    if(args.length>0){
      int lStart=Integer.parseInt(args[0]);
      int lEnd=Integer.parseInt(args[1]);
      addRankGter(lStart, lEnd);
    } else {
      addRankGter(1, 3);
    }
  }

}
