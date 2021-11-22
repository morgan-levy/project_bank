package problem1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a template reader.
 */
public class TemplateReader {

  private final List<String> template;
  private final String path;

  /**
   * Constructor for a new TemplateReader
   *
   * @param filepath (String) A path to the template file
   * @throws IOException when the filepath does not exist or cannot be accessed.
   */
  public TemplateReader(String filepath) throws IOException {
    this.template = this.populateTemplate(filepath);
    this.path = filepath;
  }

  /**
   * This method will read in a text file and store the data in a list.
   *
   * @param filepath (String) The path to the template file.
   * @return (List) The read-in content of template.
   * @throws IOException if file is not found.
   */
  private List<String> populateTemplate(String filepath) throws IOException {
    if (filepath == null) {
      throw new IllegalArgumentException("Expected a filename, found null argument.");
    }

    return this.readFile(filepath);
  }

  /**
   * Reads a file.
   *
   * @param path (String) A path to the file.
   * @return (List) The read-in content of template.
   * @throws IOException if file is not found.
   */
  private List<String> readFile(String path) throws IOException {
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String line;

      while ((line = reader.readLine()) != null) {
        lines.add(line);
      }
    } catch (FileNotFoundException e) {
      System.err.println("File could not be found");
      throw e;
    } catch (IOException e) {
      System.err.println("IO Exception");
      throw e;
    }

    return lines;
  }

  /**
   * Gets the filename from the template file.
   *
   * @return (String) The file name.
   */
  public String getFilename() {
    // String path and grab the text after the last /
    String fileName;
    Path p = Paths.get(path);
    fileName = p.getFileName().toString();
    return fileName;
  }

  /**
   * Returns the separated input template.
   *
   * @return (List) The template data.
   */
  public List<String> getTemplate() {
    return template;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateReader that = (TemplateReader) o;
    return Objects.equals(template, that.template) && Objects
        .equals(path, that.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(template, path);
  }

  @Override
  public String toString() {
    return "TemplateReader{" +
        "template=" + template +
        ", path='" + path + '\'' +
        '}';
  }
}
