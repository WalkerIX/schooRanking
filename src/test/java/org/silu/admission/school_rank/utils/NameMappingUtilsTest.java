package org.silu.admission.school_rank.utils;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class NameMappingUtilsTest {
  @Test
  public void testNameMapping() throws IOException, InterruptedException{
    Map<String, String> map=NameMappingUtils.getAllMappingPairs();
  }
}
