package org.silu.admission.school_rank.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class JSoupTest {
  // @Test
  public void testJSoupFetch() throws IOException{
    Document doc = Jsoup.connect("https://www.google.com/search?q=Columbia%20University")
        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
    //    Document doc = Jsoup.connect("https://www.google.com/search?q=Columbia%20University")
    //        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) ").get();
    Elements  refs= doc.select(".rc a");
    Element ref = refs.first();
    System.out.println(ref.text());
    
    /*
    Elements  refs= doc.select("a[href]");
    for(Element ref: refs){
      String str=ref.attr("href");
      if(str.contains("University")) {
        System.out.println(ref.text());
        break;
      }
    }
     */
    //   System.out.println(newsHeadlines.toString());
  }

  @Test
  public void testForDetails() throws IOException{
    Document doc = Jsoup.connect("https://www.google.com/search?q=michigan%20Dearborn%20edu")
        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36").get();
    //    Document doc = Jsoup.connect("https://www.google.com/search?q=Columbia%20University")
    //        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) ").get();
    Elements  refs= doc.select(".rc .crl");
    Element ref = refs.first();
    System.out.println(ref.text());
    
  }
}
