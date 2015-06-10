package org.silu.admission.school_rank.ranker;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
public class SchoolRankerTest {
  private final static String DB_NAME="universityDB";
  private final static String KEY="silu12345";
  private final static String COLLECTION="university-rank";
  @Test
  public void testRanker(){
    int rank=SchoolRanker.getRank("nyu", DB_NAME, KEY, COLLECTION);
    assertThat(rank, is(32));
  }

  @Test
  public void testRankerDefault(){
    int rank=SchoolRanker.getRank("nyu");
    assertThat(rank, is(32));
  }
}
