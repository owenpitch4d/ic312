import java.util.List;
import java.util.NoSuchElementException;

/** Graph ADT.
 * A directed, unweighted graph with string labels for vertices.
 *
 * Because the graph is unweighted, the edge weights are treated like
 * true/false boolean values.
 */
public interface Graph {
  /** Returns a list of all vertices in this graph. */
  List<String> vertices();

  /** Returns a list of all outgoing edges from the give source node.
   * @throws NoSuchElementException if source does not exist.
   */
  List<String> neighbors(String source) throws NoSuchElementException;

  /** Returns true if an edge from source to dest exists.
   * @throws NoSuchElementException if source or dest nodes do not exist.
   */
  boolean getEdge(String source, String dest) throws NoSuchElementException;

  /** Adds a new vertex to the graph, if it doesn't already exist.
   * No error if a vertex with that name exists already.
   */
  void addVertex(String label);

  /** Adds or removes an edge from the graph.
   *
   * If weight is true, the edge should be added if it doesn't already exist.
   * If weight is false, the edge should be removed if it does exist.
   *
   * @throws NoSuchElementException if source or dest vertex names don't exist.
   */
  void putEdge(String source, String dest, boolean weight) throws NoSuchElementException;
}
