package problem1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A Class representing a Nonprofit Supporter Processor, which takes a CSV file as its input and
 * creates and returns a HashMap with String keys made from the column headers of the CSV (first
 * line of the file), and List values of all Strings in each of the columns under the headers.
 */
public class CSVProcessor {

  private final String filePath;
  private final List<String> rawData;
  private final Map<String, List<String>> supporterInfo;

  /**
   * Constructor for a Nonprofit Supporter Processor
   *
   * @param filePath (String) Represents the file path to the CSV file to be used in creating the
   *                 HashMap of information.
   * @throws IOException when the processor is not able to properly read the input file.
   */
  public CSVProcessor(String filePath) throws IOException {
    this.filePath = filePath;
    this.rawData = this.readFile(this.filePath);
    this.supporterInfo = this.populateInformation(this.rawData);
  }

  /**
   * Method to read the file and return a List of strings representing all the lines contained in
   * the file found at the passed file path string
   *
   * @param filePath (String) The path to the file to be read and processed
   * @return lines   (List) A list containing all lines in the specified file as strings
   * @throws IOException when the reader is unable to open the file at the specified path.
   */
  private List<String> readFile(String filePath) throws IOException {
    List<String> lines = new ArrayList<>();

    try (BufferedReader inputFile = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = inputFile.readLine()) != null) {
        lines.add(line);
      }
    } catch (FileNotFoundException fnfe) {
      throw new FileNotFoundException(fnfe.getMessage());
    } catch (IOException ioe) {
      throw new IOException("Error: Something went wrong : " + ioe.getMessage());
    }

    return lines;
  }

  /**
   * Method to take in a raw list of Strings representing the data from a CSV file, create a Map
   * with the keys from the first line of the CSV, and populate the Map with the corresponding info
   * on each of the subsequent lines
   *
   * @param lines (List) A list of Strings representing the data from a CSV file
   * @return supporters (Map) A map representing the information of a CSV file, with keys made from
   * the information types specified in the first line, and ArrayList values populated with the
   * corresponding information of each individual in the lines following
   */
  private Map<String, List<String>> populateInformation(List<String> lines) {
    // populate the array list values with all the information in the CSV
    /*
     * Set up the LinkedHashMap using the header values for the info columns in the CSV as the
     * name of the keys, with blank ArrayLists to store all the information as the values
     * Regex splits on (") or (","), causing the first element of the string array to be a blank
     * String, and therefore start index 1 is used to create keys and add to the ArrayList values
     */
    Map<String, List<String>> supporters = new LinkedHashMap<>();
    String[] keys = lines.get(0).split("(\",)?\"");

    for (int i = 1; i < keys.length; i++) {
      supporters.put(keys[i], new ArrayList<>());
    }

    /*
     * For each subsequent line in the CSV, add each piece of information for each supporter to
     * the correct ArrayList value in order of the keys in the LinkedHashMap
     */
    for (int i = 1; i < lines.size(); i++) {
      String[] parts = lines.get(i).split("(\",)?\"");

      for (int j = 1; j < parts.length; j++) {
        List<String> info = supporters.get(keys[j]);

        if (info == null) {
          info = new ArrayList<>();
          info.add(parts[j]);
          supporters.put(keys[j], info);
        } else {
          info.add(parts[j]);
        }
      }
    }
    return supporters;
  }

  /**
   * Gets the file path to the file containing all the supporter information
   *
   * @return filePath   (String) The path to the CSV file to be processed in creating a HashMap of
   * supporter information
   */
  public String getFilePath() {
    // "src/main/java/Input/nonprofit-supporters.csv"
    return filePath;
  }

  /**
   * Gets the raw data of the specified CSV file in the form of a List of Strings
   *
   * @return rawData (List) The raw data of the specified CSV file
   */
  public List<String> getRawData() {
    return rawData;
  }

  /**
   * Gets the HashMap of supporter information
   *
   * @return supporterInfo (Map) The HashMap of the processed supporter information.
   */
  public Map<String, List<String>> getSupporterInfo() {
    return supporterInfo;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CSVProcessor)) {
      return false;
    }
    CSVProcessor that = (CSVProcessor) o;
    return Objects.equals(filePath, that.filePath) && Objects
        .equals(rawData, that.rawData) && Objects.equals(supporterInfo, that.supporterInfo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filePath, rawData, supporterInfo);
  }

  @Override
  public String toString() {
    return "NonprofitSupportersProcessor{" +
        "filePath='" + filePath + '\'' +
        ", rawData=" + rawData +
        ", supporterInfo=" + supporterInfo +
        '}';
  }
}
