package org.silu.admission.school_rank.utils;

import java.io.File;
import java.util.Map;

import org.mapdb.DB;
import org.mapdb.DBMaker;


public class App
{
  public static void main( String[] args )
  {
    // import org.mapdb.*;
    
    // configure and open database using builder pattern.
    // all options are available with code auto-completion.
    DB db = DBMaker.newFileDB(new File("testdb"))
        .closeOnJvmShutdown()
        .encryptionEnable("password")
        .make();

    // open existing an collection (or create new)
    Map<Integer,String> map =  db.createHashMap("collectionName").make();

    map.put(1, "one");
    map.put(2, "two");
    // map.keySet() is now [1,2]

    db.commit();  //persist changes into disk

    map.put(3, "three");
    // map.keySet() is now [1,2,3]
    db.rollback(); //revert recent changes
    // map.keySet() is now [1,2]

    db.close();
    
  }
}
