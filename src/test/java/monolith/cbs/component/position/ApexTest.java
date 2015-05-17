package monolith.cbs.component.position;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ApexTest {

  @Test
  public void shouldMarkAsEqualApexes() throws Exception {

    //given
    Apex first = new Apex(1, 1);
    Apex second = new Apex(1, 1);

    //when
    boolean isEqual = first.equals(second);

    //then
    assertThat(isEqual).isTrue();
  }
}