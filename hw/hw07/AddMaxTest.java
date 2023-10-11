import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Random;
import java.util.NoSuchElementException;

public class AddMaxTest {
  // comparing doubles for exact equality is bad, so we have this helper function
  static void assertClose(double expected, double actual) {
    assertEquals(Math.round(actual*1000), Math.round(expected*1000));
  }

  // helper method in case I want to test a different class name
  private AddMax create() {
    return new DoubleTree();
  }

  @Test
  public void add1Remove1() {
    AddMax am = create();
    am.add(3.7);
    assertClose(3.7, am.removeMax());
  }

  @Test(expected = NoSuchElementException.class)
  public void add1Remove2() {
    AddMax am = create();
    am.add(-1.1);
    am.removeMax();
    am.removeMax();
  }

  @Test
  public void add3remove3() {
    AddMax am = create();
    am.add(1.1);
    am.add(3.3);
    am.add(2.2);
    assertClose(3.3, am.removeMax());
    assertClose(2.2, am.removeMax());
    assertClose(1.1, am.removeMax());
  }

  @Test
  public void addAfterEmpty() {
    AddMax am = create();
    am.add(9.8);
    am.add(9.5);
    assertClose(9.8, am.removeMax());
    assertClose(9.5, am.removeMax());
    am.add(9.7);
    am.add(9.9);
    assertClose(9.9, am.removeMax());
    assertClose(9.7, am.removeMax());
  }

  @Test
  public void twoAtOnce() {
    AddMax am1 = create();
    AddMax am2 = create();
    am1.add(3.4);
    am2.add(4.5);
    am1.add(1.2);
    am2.add(6.7);
    am1.add(9.8);
    assertClose(9.8, am1.removeMax());
    assertClose(6.7, am2.removeMax());
    assertClose(4.5, am2.removeMax());
    assertClose(3.4, am1.removeMax());
    assertClose(1.2, am1.removeMax());
  }

  @Test
  public void add20Remove10() {
    AddMax am = create();
    double[] items = new double[20];
    for (int i = 0; i < 20; ++i) {
      items[i] = Math.sin(i+1);
      am.add(items[i]);
    }
    java.util.Arrays.sort(items);
    for (int i = 0; i < 10; ++i) {
      assertClose(items[19-i], am.removeMax());
    }
  }

  @Test
  public void add2remove1repeat() {
    int n = 15;
    double[] items = new double[n+1];
    AddMax am = create();
    for (int i = 0; i < n; ++i) {
      items[i] = Math.cos(i*.7 + 1);
      items[i+1] = Math.cos(i*.7 + 1.35);
      am.add(items[i]);
      am.add(items[i+1]);
      java.util.Arrays.sort(items, 0, i+2);
      assertClose(items[i+1], am.removeMax());
    }
  }

  @Test(timeout=1000)
  public void speedRandom() {
    int n = 400000;
    Random rng = new Random(2020);
    AddMax am = create();
    for (int i = 0; i < n; ++i) {
      am.add(rng.nextDouble());
      am.add(rng.nextDouble());
      am.removeMax();
    }
  }

  @Test(timeout=1000)
  public void speedOrdered() {
    int n = 200000;
    AddMax am = create();
    for (int i = 0; i < n; ++i) {
      am.add(i*.02 + .01);
      am.add(i*.02);
      assertClose(i*.02 + .01, am.removeMax());
    }
    for (int i = 1; i < n; ++i) {
      am.add(i*-.03);
      am.add(i*-.03 + .01);
      assertClose((n-i)*.02, am.removeMax());
    }
  }

  // helper function to remove max from an array and decrease size by 1
  private double removeMaxFromArray(double[] arr, int size) {
    assert size >= 1;
    int maxInd = 0;
    for (int i = 1; i < size; ++i) {
      if (arr[i] > arr[maxInd]) maxInd = i;
    }
    double max = arr[maxInd];
    arr[maxInd] = arr[size-1];
    return max;
  }

  @Test
  public void randomActions() {
    Random rng = new Random(2000);
    int rounds = 30;
    int steps = 200;
    int initialSize = 20;
    for (int i = 0; i < rounds; ++i) {
      AddMax am = create();
      double[] items = new double[initialSize + steps];
      for (int j = 0; j < initialSize; ++j) {
        items[j] = rng.nextDouble();
        am.add(items[j]);
      }
      int size = initialSize;
      for (int j = 0; j < steps; ++j) {
        if (size == 0 || rng.nextBoolean()) {
          items[size++] = rng.nextDouble();
          am.add(items[size-1]);
        }
        else {
          double expected = removeMaxFromArray(items, size--);
          double actual = am.removeMax();
          assertClose(expected, actual);
        }
      }
    }
  }
}
