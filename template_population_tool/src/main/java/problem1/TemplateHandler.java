package problem1;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class representing an umbrella template handler, that includes the individual template
 * parser/reader/write components in one.
 */
public class TemplateHandler {

  private final String outputDir;
  private final String[] templatePaths;
  private final Map<String, List<String>> inputData;
  private TemplateReader reader;
  private TemplateParser parser;
  private TemplateWriter writer;

  /**
   * Constructor for template handler.
   *
   * @param templatePaths (String[]) The paths to the template.
   * @param outputDir     (String) Path to the output directory.
   * @param inputData      (Map) The parsed CSV data.
   */
  public TemplateHandler(String[] templatePaths, String outputDir,
      Map<String, List<String>> inputData) {
    this.templatePaths = templatePaths;
    this.outputDir = outputDir;
    this.inputData = inputData;
  }

  /**
   * Writes the templates to the output directory.
   *
   * @throws IOException when the writer is unable to write to the specified output path.
   */
  public void writeTemplates() throws IOException {
    // Read a template
    for (int i = 0; i < templatePaths.length; i++) {
      reader = new TemplateReader(templatePaths[i]);
      parser = new TemplateParser(reader.getTemplate(), inputData);
      parser.populateModListHolder();
      writer = new TemplateWriter(parser.getModListHolder(), outputDir, reader.getFilename());
      writer.writePopulatedTemplates();
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
    TemplateHandler that = (TemplateHandler) o;
    return Objects.equals(outputDir, that.outputDir)
        && Arrays.equals(templatePaths, that.templatePaths)
        && Objects.equals(inputData, that.inputData)
        && Objects.equals(reader, that.reader)
        && Objects.equals(parser, that.parser)
        && Objects.equals(writer, that.writer);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(outputDir, inputData, reader, parser, writer);
    result = 31 * result + Arrays.hashCode(templatePaths);
    return result;
  }
}
