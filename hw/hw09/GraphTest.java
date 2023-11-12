import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import java.util.List;
import java.util.Set;
import java.util.Random;
import java.util.NoSuchElementException;

public class GraphTest {
  // helper method in case I want to test a different class name
  private Graph create() {
    return new MyGraph();
  }

  @Rule
  public Timeout globalTimeout = Timeout.seconds(2);

  @Test
  public void emptyVertices() {
    Graph g = create();
    assertTrue(g.vertices().isEmpty());
  }

  @Test(expected = NoSuchElementException.class)
  public void emptyNeighbors() {
    Graph g = create();
    g.neighbors("a");
  }

  @Test(expected = NoSuchElementException.class)
  public void emptyGetEdge() {
    Graph g = create();
    g.getEdge("a", "b");
  }

  @Test
  public void size1() {
    Graph g = create();
    g.addVertex("a");
    assertEquals(List.of("a"), g.vertices());
    assertTrue(g.neighbors("a").isEmpty());
    assertFalse(g.getEdge("a", "a"));
  }

  @Test
  public void size2() {
    Graph g = create();
    g.addVertex("tom");
    g.addVertex("jerry");
    g.putEdge("tom", "jerry", true);
    assertEquals(2, g.vertices().size());
    assertEquals(Set.of("tom", "jerry"), Set.copyOf(g.vertices()));
    assertEquals(List.of("jerry"), g.neighbors("tom"));
    assertEquals(List.of(), g.neighbors("jerry"));
    assertTrue(g.getEdge("tom", "jerry"));
    assertFalse(g.getEdge("jerry", "tom"));
    g.putEdge("jerry", "tom", true);
    assertTrue(g.getEdge("jerry", "tom"));
    assertEquals(List.of("tom"), g.neighbors("jerry"));
  }

  @Test
  public void vertexAfterEdge() {
    Graph g = create();
    g.addVertex("salt");
    g.addVertex("pepper");
    g.putEdge("salt", "pepper", true);
    assertEquals(Set.of("salt", "pepper"), Set.copyOf(g.vertices()));
    assertEquals(List.of("pepper"), g.neighbors("salt"));
    g.addVertex("vinegar");
    g.putEdge("salt", "vinegar", true);
    assertEquals(Set.of("salt", "pepper", "vinegar"), Set.copyOf(g.vertices()));
    assertEquals(Set.of("pepper", "vinegar"), Set.copyOf(g.neighbors("salt")));
    assertTrue(g.neighbors("vinegar").isEmpty());
  }

  @Test
  public void square() {
    Graph g = create();
    g.addVertex("north");
    g.addVertex("south");
    g.addVertex("east");
    g.addVertex("west");
    g.putEdge("north", "east", true);
    g.putEdge("east", "south", true);
    g.putEdge("south", "west", true);
    g.putEdge("west", "north", true);
    assertEquals(Set.of("north", "south", "east", "west"), Set.copyOf(g.vertices()));
    assertTrue(g.getEdge("east", "south"));
    assertFalse(g.getEdge("south", "east"));
    assertFalse(g.getEdge("north", "south"));
    assertTrue(g.getEdge("west", "north"));
    assertEquals(List.of("west"), g.neighbors("south"));
  }

  @Test
  public void twoAtOnce() {
    Graph g1 = create();
    Graph g2 = create();
    g1.addVertex("a");
    g1.addVertex("b");
    g2.addVertex("a");
    g2.addVertex("b");
    g1.addVertex("c");
    g1.putEdge("a", "b", true);
    g2.putEdge("b", "a", true);
    g1.putEdge("a", "c", true);
    assertTrue(g1.getEdge("a", "b"));
    assertFalse(g2.getEdge("a", "b"));
    assertEquals(Set.of("a", "b"), Set.copyOf(g2.vertices()));
    assertEquals(Set.of("a", "b", "c"), Set.copyOf(g1.vertices()));
    assertEquals(List.of(), g1.neighbors("b"));
    assertEquals(List.of("a"), g2.neighbors("b"));
  }

  @Test
  public void removeEdges() {
    Graph g = create();
    g.addVertex("fizz");
    g.addVertex("buzz");
    g.putEdge("fizz", "buzz", true);
    g.putEdge("buzz", "fizz", false);
    assertFalse(g.getEdge("buzz", "fizz"));
    assertTrue(g.getEdge("fizz", "buzz"));
    assertEquals(List.of(), g.neighbors("buzz"));
    assertEquals(List.of("buzz"), g.neighbors("fizz"));
    g.putEdge("fizz", "buzz", false);
    assertEquals(List.of(), g.neighbors("fizz"));
    assertFalse(g.getEdge("fizz", "buzz"));
    g.putEdge("buzz", "fizz", true);
    assertTrue(g.getEdge("buzz", "fizz"));
  }

  @Test(expected = NoSuchElementException.class)
  public void badNodeNeighbors() {
    Graph g = create();
    g.addVertex("fizz");
    g.neighbors("buzz");
  }

  @Test(expected = NoSuchElementException.class)
  public void badNodeGet() {
    Graph g = create();
    g.addVertex("a");
    g.addVertex("c");
    g.putEdge("a", "c", true);
    g.getEdge("a", "b");
  }

  @Test(expected = NoSuchElementException.class)
  public void badNodePut() {
    Graph g = create();
    g.addVertex("north");
    g.addVertex("west");
    g.putEdge("west", "north", true);
    g.putEdge("north", "east", true);
  }

  @Test
  public void random() {
    Random rng = new Random(1984);
    for (int i = 0; i < 100; ++i) {
      Graph g = create();
      java.util.Map<String,Set<String>> check = new java.util.HashMap<>();
      List<String> cvert = new java.util.ArrayList<>();
      for (int j = 0; j < 1000; ++j) {
        float r = rng.nextFloat();
        if (check.size() >= 2 && r < .6) {
          // put or get edge
          String v1 = cvert.get(rng.nextInt(cvert.size()));
          String v2;
          do { v2 = cvert.get(rng.nextInt(cvert.size())); }
          while (v1.equals(v2));
          boolean has = check.get(v1).contains(v2);
          if (r < .4) {
            // put edge (toggle)
            if (has) check.get(v1).remove(v2);
            else check.get(v1).add(v2);
            g.putEdge(v1, v2, !has);
          }
          else {
            // get edge
            assertEquals(has, g.getEdge(v1, v2));
          }
        }
        else if (check.size() >= 1 && r < .85) {
          // neighbors
          String v = cvert.get(rng.nextInt(cvert.size()));
          assertEquals(check.get(v), Set.copyOf(g.neighbors(v)));
        }
        else if (r < .9) {
          // add vertex
          String v;
          do { v = Integer.toString(rng.nextInt(1000)); }
          while (check.containsKey(v));
          check.put(v, new java.util.HashSet<>());
          cvert.add(v);
          g.addVertex(v);
        }
        else {
          // vertices
          assertEquals(Set.copyOf(cvert), Set.copyOf(g.vertices()));
        }
      }
    }
  }
}
