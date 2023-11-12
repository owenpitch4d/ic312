import java.nio.file.Path;
import java.nio.file.Files;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** Utility to read a .dot "graphviz" file into a Graph.
 * Sample usage:
 *   Graph g = DotReader.readFrom("mygraph.dot");
 * */
public class DotReader {
  /** Reads the filename into a new graph object.
   * @param filename the name of a dot-formatted file.
   */
  public static Graph readFrom(String filename) {
    Graph g = new MyGraph();

    // read entire file into a string
    String fileString = null;
    try {
      fileString = Files.readString(Path.of(filename));
    }
    catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }

    Matcher outer = OUTER.matcher(fileString);
    if (!outer.lookingAt())
      throw new RuntimeException("file does not match dot syntax");

    Matcher lines = LINE.matcher(outer.group("tail"));
    boolean endLines = false;
    while (!endLines && lines.find()) {
      endLines = lines.group("finish") != null;
      Matcher firstNode = NODE.matcher(lines.group());
      if (!firstNode.lookingAt())
        continue;
      String src = extractLabel(firstNode);
      g.addVertex(src);
      Matcher edgeNode = EDGE_NODE.matcher(firstNode.group("tail"));
      while (edgeNode.lookingAt()) {
        String dest = extractLabel(edgeNode);
        g.addVertex(dest);
        g.putEdge(src, dest, true);
        if (edgeNode.group("edge").equals("--")) {
          // undirected edge - add both ways
          g.putEdge(dest, src, true);
        }
        // look for more edge connections
        src = dest;
        edgeNode = EDGE_NODE.matcher(edgeNode.group("tail"));
      }
    }

    return g;
  }

  private static String extractLabel(Matcher mat) {
    String label = mat.group("label1");
    return (label == null) ? mat.group("label2") : label;
  }

  private static Pattern OUTER = Pattern.compile(
      "\\s*(strict\\s*)?(di)?graph\\s*" // file header
    + "\\w*\\s*" // optional graph name
    + "\\{" // opening curly brace
    + "(?<tail>.*)"
    , Pattern.CASE_INSENSITIVE | Pattern.DOTALL
  );

  private static Pattern LINE = Pattern.compile(
      "(\\\\." // ignore any backslash-escaped character
    + "|\\'(\\\\.|[^'])*\\'" // single-quoted strings
    + "|\\\"(\\\\.|[^\"])*\\\"" // double-quoted strings
    + "|[^;\\}\\n]" // otherwise take anything except semicolon or newline
    + ")*(;|$|(?<finish>\\}))"
    , Pattern.DOTALL | Pattern.MULTILINE
  );

  private static Pattern NODE = Pattern.compile(
      "\\s*"
    + "((?<label1>\\w+)|\\\"(?<label2>(\\\\.|[^\"])*)\\\")" // word or double-quoted
    + "(?<tail>.*)"
    , Pattern.DOTALL
  );

  private static Pattern EDGE_NODE = Pattern.compile(
      "\\s*(?<edge>--|->)" // edge indicator
    + "\\s*"
    + "((?<label1>\\w+)|\\\"(?<label2>(\\\\.|[^\"])*)\\\")" // word or double-quoted
    + "(?<tail>.*)"
    , Pattern.DOTALL
  );
}
