package org.silu.admission.school_rank.utils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

public class NameMappingUtilsTest {
  private final static String NYU="New York University";
  @Test
  public void testGetSchoolNameInDB() throws IOException, InterruptedException{
    assertThat(NameMappingUtils.getSchoolNameInDB(NYU), is(NYU));
  }
}
