package problem1;

import java.io.IOException;

/**
 * Main entry point into application.
 */
public class Main {

  public static void main(String[] args) throws IOException {
    ArgumentParser parser;

    parser = new ArgumentParser.ArgumentParserBuilder(args).build();
    parser.parse();

    CSVProcessor dataProcessor = new CSVProcessor(parser.getTargets().get("csv-file"));

    TemplateHandler handler = new TemplateHandler(
        new String[]{parser.getTargets().get("email-template"),
            parser.getTargets().get("letter-template")},
        parser.getTargets().get("output-dir"),
        dataProcessor.getSupporterInfo()
    );

    handler.writeTemplates();
  }
}
