import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class MyGraph implements Graph {
    private Map<String, ArrayList<String> > graph = new HashMap<>();

    public MyGraph() {

    }

    /** Returns a list of all vertices in this graph. */
    public List<String> vertices() {
        return new ArrayList<>(graph.keySet());
    }
  
    /** Returns a list of all outgoing edges from the give source node.
     * @throws NoSuchElementException if source does not exist.
     */
    public List<String> neighbors(String source) throws NoSuchElementException {
        if(!graph.containsKey(source)) { throw new NoSuchElementException(); }

        return graph.get(source);
    }
  
    /** Returns true if an edge from source to dest exists.
     * @throws NoSuchElementException if source or dest nodes do not exist.
     */
    public boolean getEdge(String source, String dest) throws NoSuchElementException {
        if(!graph.containsKey(source) || !graph.containsKey(dest)) { 
            throw new NoSuchElementException(); 
        }

        return graph.get(source).contains(dest);
    }
  
    /** Adds a new vertex to the graph, if it doesn't already exist.
     * No error if a vertex with that name exists already.
     */
    public void addVertex(String label) {
        graph.putIfAbsent(label, new ArrayList<>());
    }
  
    /** Adds or removes an edge from the graph.
     *
     * If weight is true, the edge should be added if it doesn't already exist.
     * If weight is false, the edge should be removed if it does exist.
     *
     * @throws NoSuchElementException if source or dest vertex names don't exist.
     */
    public void putEdge(String source, String dest, boolean weight) throws NoSuchElementException {
        if(!graph.containsKey(source) || !graph.containsKey(dest)) { 
            throw new NoSuchElementException(); 
        }

        if(weight) {
            graph.get(source).add(dest);
        } else {
            graph.get(source).remove(dest);
        }
    }
    
}