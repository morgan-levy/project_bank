package problem1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing a parser for an input template.
 */
public class TemplateParser {

  private final List<String> templateInput;
  private final List<List<String>> modListHolder;
  private final Map<String, List<String>> inputData;
  private final String pattern = "\\[{2}([a-zA-Z0-9_-]+)]{2}";
  private final Pattern key = Pattern.compile(this.pattern);
  private final Integer index;


  /**
   * Constructor for template parser to read and replace keys in template
   *
   * @param templateInput (List) The lines from base template.
   * @param inputData     (Map) The data for insertion.
   */
  public TemplateParser(List<String> templateInput, Map<String, List<String>> inputData) {
    this.templateInput = templateInput;
    this.inputData = inputData;
    this.index = 0;
    this.modListHolder = new ArrayList<>();
  }

  /**
   * Builds the template and replaces key words with desired data adding the templates to a list for
   * writing.
   */
  public void insertDataToKeys(int index) {
    List<String> modList = new ArrayList<>();

    for (String line : this.templateInput) {
      Matcher match = key.matcher(line);

      while (match.find()) {
        String info = match.group(1);
        String inputData = getDataFromHash(info, index);
        line = line.replace(match.group(), inputData);
      }

      modList.add(line);
    }
    modListHolder.add(modList);
  }

  /**
   * Grabs the data from hashmap
   *
   * @param keyWord (String) Used to grab data
   * @param index   (Integer) Links data from arraylists within the hashmap
   * @return (String) The data to be inserted.
   */
  private String getDataFromHash(String keyWord, Integer index) {
    String inputData;
    // This throws a NullPointer exception if the key does not exist
    if (!keyExists(keyWord)) {
      throw new IllegalArgumentException("This key was not found.");
    }
    // Add a check for key exists
    inputData = this.inputData.get(keyWord).get(index);
    return inputData;
  }

  /**
   * Returns the List of Lists of Strings.
   *
   * @return Returns the List of Lists of Strings, containing the modified template lines.
   */
  public List<List<String>> getModListHolder() {
    return this.modListHolder;
  }

  /**
   * This method goes through and compiles a populated template for each list in the map.
   */
  public void populateModListHolder() {
    int size = 0;
    // Go through each data point in arraylist for key
    for (List<String> value : inputData.values()) {
      size = value.size();
    }

    for (int i = 0; i < size; i++) {
      insertDataToKeys(i);
    }
  }

  /**
   * Checks to see if a key exists in the map data.
   *
   * @param key (String) The key to be checked against the map's keys.
   * @return (Boolean) Returns true if the key exists in the map.
   */
  private boolean keyExists(String key) {
    for (String k : inputData.keySet()) {
      if (k.equals(key)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Getter method for the separated template input.
   *
   * @return (List) A List of Strings containing the lines of the parsed template.
   */
  public List<String> getTemplateInput() {
    return templateInput;
  }

  /**
   * Getter method for the processed data from the input file.
   *
   * @return (Map) A Map of String keys and Lists representing the different row values associated
   * with that key.
   */
  public Map<String, List<String>> getInputData() {
    return inputData;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TemplateParser that = (TemplateParser) o;
    return Objects.equals(templateInput, that.templateInput)
        && Objects.equals(modListHolder, that.modListHolder)
        && Objects.equals(inputData, that.inputData)
        && Objects.equals(index, that.index);
  }

  @Override
  public int hashCode() {
    return Objects.hash(templateInput, modListHolder, inputData, index);
  }

  @Override
  public String toString() {
    return "TemplateParser{" +
        "templateInput=" + templateInput +
        ", modListHolder=" + modListHolder +
        ", inputData=" + inputData +
        ", pattern='" + pattern + '\'' +
        ", key=" + key +
        ", index=" + index +
        '}';
  }
}
