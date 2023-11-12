import java.util.Set;
import java.util.HashSet;
import java.util.Stack;

public class CycleFinder {
  public static boolean hasCycle(Graph graph, String start) {
    Set<String> visited = new HashSet<String>();
    Stack<String> fringe = new Stack<>();

    fringe.push(start);
    while(!fringe.empty()) {
      String u = fringe.pop();
      
      if(!visited.contains(u)) {
        visited.add(u);
        for(String v : graph.neighbors(u)) {
          if(fringe.contains(v)) {return true;}
          fringe.push(v);
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    // XXX you shouldn't need to change anything here
    String dotf = Stdin.input("graph file: ");
    Graph graph = DotReader.readFrom(dotf);

    while (true) {
      String start = Stdin.input("starting node or 'quit': ");
      if (start == null || start.equals("quit")) break;
      if (hasCycle(graph, start))
        System.out.format("%s is in a cycle.\n", start);
      else
        System.out.format("%s is NOT in a cycle.\n", start);
    }
  }
}
