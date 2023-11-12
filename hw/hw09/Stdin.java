import java.io.Console;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/** Dumb class to read from standard in line by line.
 * The method to use is Stdin.input("Prompt? ")
 * which returns the next line from standard in.
 */
public class Stdin {
  private static Console console = null;
  private static BufferedReader reader = null;

  /** Read a line of text from standard in.
   * If input is from a terminal, the prompt is presented.
   * Otherwise it just reads a line and ignores the prompt.
   * @return The next line from stdin, or null if EOF is reached.
   */
  public static String input(String prompt) {
    if (console == null && reader == null) {
      // first run, set up either console or reader
      console = System.console();
      if (console == null)
        reader = new BufferedReader(new InputStreamReader(System.in));
    }
    if (console != null)
      return console.readLine(prompt);
    try {
      return reader.readLine();
    }
    catch (java.io.IOException e) {
      throw new RuntimeException(e);
    }
  }
}
