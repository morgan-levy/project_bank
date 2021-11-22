package problem1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Class representing a template writer.
 */
public class TemplateWriter {

  private final List<List<String>> templates;
  private final String dest;
  private final String filename;

  /**
   * Constructor for a new template writer
   *
   * @param templates (List) Template data in the form of strings.
   * @param dest      (String) The location to write files.
   * @param filename  (String) The name of the files to write.
   */
  public TemplateWriter(List<List<String>> templates, String dest, String filename) {
    this.templates = templates;
    this.dest = dest;
    this.filename = filename;
  }

  /**
   * This method takes a list of strings and writes them to a file.
   *
   * @param curTemplate (List) The current populated template to write.
   * @param curDest     (String) The location of the directory to write the file to.
   * @throws IOException when the destination directory cannot be found.
   */
  public int writeTemplate(List<String> curTemplate, String curDest) throws IOException {
    // Throws an exception if destination directory is not found.
    try (FileWriter writer = new FileWriter(curDest)) {
      for (String line : curTemplate) {
        writer.write(line);
        writer.write("\n");
      }

      return 0;
    } catch (IOException e) {
      System.err.println(e.getMessage());
      throw e;
    }
  }

  /**
   * This method will iterate through the modified lists and print out the personalized emails
   */
  public void writePopulatedTemplates() throws IOException {
    String tempFilename = filename.split("\\.")[0];

    for (int i = 0; i < templates.size(); i++) {
      File f = new File(dest, tempFilename.concat(i + ".txt"));
      writeTemplate(templates.get(i), f.getAbsolutePath());
    }
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) {
          return true;
      }
      if (o == null || getClass() != o.getClass()) {
          return false;
      }
    TemplateWriter that = (TemplateWriter) o;
    return Objects.equals(templates, that.templates)
        && Objects.equals(dest, that.dest)
        && Objects.equals(filename, that.filename);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templates, dest, filename);
  }

  @Override
  public String toString() {
    return "TemplateWriter{" +
        "templates=" + templates +
        ", dest='" + dest + '\'' +
        ", filename='" + filename + '\'' +
        '}';
  }
}
