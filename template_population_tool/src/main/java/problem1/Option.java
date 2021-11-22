package problem1;

import java.util.Arrays;
import java.util.Objects;

/**
 * A class representing a command line option.
 */
public class Option {

  static private final Character SHORT_PREFIX = '-';
  static private final String LONG_PREFIX = "--";

  private final Character key;
  private final String name;
  private final Boolean isRequired;
  private final Boolean acceptsArg;
  private final Boolean isArgRequired;
  private final Option[] dependencies;
  private final String description;

  /**
   * Constructor for class Option.
   *
   * @param builder (OptionBuilder) An OptionBuilder instance.
   */
  private Option(OptionBuilder builder) {
    this.key = builder.key;
    this.name = builder.name;
    this.isRequired = builder.isRequired;
    this.acceptsArg = builder.acceptsArg;
    this.isArgRequired = builder.isArgRequired;
    this.dependencies = builder.dependencies;
    this.description = builder.description;
  }

  /**
   * Getter method for short prefix value of Option class.
   *
   * @return (Character) The Option class' short name (key) prefix (e.g. '-').
   */
  public static Character getShortPrefix() {
    return SHORT_PREFIX;
  }

  /**
   * Getter method for long prefix value of Option class.
   *
   * @return (String) The Option class' long name prefix (e.g. '--').
   */
  public static String getLongPrefix() {
    return LONG_PREFIX;
  }

  /**
   * Getter method for key value of Option.
   *
   * @return (Character) The Option's single-character key.
   */
  public Character getKey() {
    return key;
  }

  /**
   * Getter method for name value of Option.
   *
   * @return (String) The Option's long name.
   */
  public String getName() {
    return name;
  }

  /**
   * Getter method for prefixed key value of Option.
   *
   * @return (String) The Option's key prefixed with a single hyphen (e.g. '-f').
   */
  public String getPrefixedKey() {
    return SHORT_PREFIX.toString() + key.toString();
  }

  /**
   * Getter method for prefixed long name value of Option.
   *
   * @return (String) The Option's name prefixed with two hyphens (e.g. '--file').
   */
  public String getPrefixedName() {
    return LONG_PREFIX + name;
  }

  /**
   * Getter method for isRequired value of Option.
   *
   * @return (Boolean) Value indicating if Option is required or not.
   */
  public Boolean isRequired() {
    return isRequired;
  }

  /**
   * Getter method for acceptsArg value of Option.
   *
   * @return (Boolean) Value indicating if the Option accepts an argument or not.
   */
  public Boolean acceptsArg() {
    return acceptsArg;
  }

  /**
   * Getter method for isArgRequired value of Option.
   *
   * @return (Boolean) Value indicating if the Option's accepted argument is required or not.
   */
  public Boolean isArgRequired() {
    return isArgRequired;
  }

  /**
   * Getter method for dependencies value of Option.
   *
   * @return (Option[]) Array of Option dependencies.
   */
  public Option[] getDependencies() {
    return dependencies;
  }

  /**
   * Getter method for description value of Option.
   *
   * @return (String) The description string of the Option.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Provides the usage documentation string for use in the usage/help message generation.
   *
   * @return (String) The usage string for the given Option.
   */
  public String getUsageString() {
    StringBuilder usage = new StringBuilder();
    usage.append(String.format("%-30s%s %s",
        this.getPrefixedName() + ", " + this.getPrefixedKey(),
        this.getDescription(),
        this.isRequired() ? "This option is required." : ""));

    for (Option dep : this.getDependencies()) {
      usage.append("\n").append(String.format("\t%s\n\t%-30s%s",
          dep.getUsageString(),
          " ",
          "This option is required if option '" + this.getName() + "' is included."));
    }

    return usage.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Option option = (Option) o;
    return Objects.equals(getKey(), option.getKey()) && Objects
        .equals(getName(), option.getName()) && Objects
        .equals(isRequired, option.isRequired) && Objects
        .equals(acceptsArg, option.acceptsArg) && Objects
        .equals(isArgRequired, option.isArgRequired) && Arrays
        .equals(getDependencies(), option.getDependencies()) && Objects
        .equals(getDescription(), option.getDescription());
  }

  @Override
  public int hashCode() {
    int result = Objects
        .hash(getKey(), getName(), isRequired, acceptsArg, isArgRequired, getDescription());
    result = 31 * result + Arrays.hashCode(getDependencies());
    return result;
  }

  @Override
  public String toString() {
    return "Option{" +
        "key=" + key +
        ", name='" + name + '\'' +
        ", prefixedKey='" + getPrefixedKey() + '\'' +
        ", prefixedName='" + getPrefixedName() + '\'' +
        ", isRequired=" + isRequired +
        ", acceptsArg=" + acceptsArg +
        ", isArgRequired=" + isArgRequired +
        ", dependencies=" + Arrays.toString(dependencies) +
        ", description='" + description + '\'' +
        '}';
  }

  /**
   * Builder class for Option enclosing class.
   */
  public static class OptionBuilder {

    private final Character key;
    private final String name;
    private final Boolean isRequired;
    private Boolean acceptsArg = false;
    private Boolean isArgRequired = false;
    private Option[] dependencies = {};
    private String description = "";

    /**
     * Constructor for class OptionBuilder.
     *
     * @param key        (Character) The single-character key/flag of the Option.
     * @param name       (String) The long name of the Option.
     * @param isRequired (Boolean) Whether the Option is required or not.
     */
    public OptionBuilder(Character key, String name, Boolean isRequired) {
      this.key = key;
      this.name = name;
      this.isRequired = isRequired;
    }

    /**
     * Creates a new OptionBuilder that accepts arguments, whether optional or required.
     *
     * @param isArgRequired (Boolean) Value indicating if the accepted argument is required.
     * @return (OptionBuilder) New OptionBuilder that accepts arguments.
     */
    public OptionBuilder acceptsArg(Boolean isArgRequired) {
      this.acceptsArg = true;
      this.isArgRequired = isArgRequired;
      return this;
    }

    /**
     * Creates a new OptionBuilder that has an array of dependent Options.
     *
     * @param dependency (Option) A single dependent Option.
     * @return (OptionBuilder) New OptionBuilder that has a single dependent Option.
     */
    public OptionBuilder dependencies(Option dependency) {
      this.dependencies = new Option[]{dependency};
      return this;
    }

    /**
     * Creates a new OptionBuilder that has an array of dependent Options.
     *
     * @param dependencies (Option[]) An array of dependent Options.
     * @return (OptionBuilder) New OptionBuilder that has an array of dependent Options.
     */
    public OptionBuilder dependencies(Option[] dependencies) {
      this.dependencies = dependencies;
      return this;
    }

    /**
     * Creates a new OptionBuilder that has a description string, which is used for usage/help
     * messages.
     *
     * @param description (String) A description of the Option.
     * @return (OptionBuilder) New OptionBuilder that has a description string.
     */
    public OptionBuilder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Generates a new Option mirroring the state of the OptionBuilder.
     *
     * @return (Option) The new Option instance with a state mirroring the OptionBuilder.
     */
    public Option build() {
      return new Option(this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      OptionBuilder that = (OptionBuilder) o;
      return Objects.equals(key, that.key) && Objects.equals(name, that.name)
          && Objects.equals(isRequired, that.isRequired) && Objects
          .equals(acceptsArg, that.acceptsArg) && Objects
          .equals(isArgRequired, that.isArgRequired) && Arrays
          .equals(dependencies, that.dependencies) && Objects
          .equals(description, that.description);
    }

    @Override
    public int hashCode() {
      int result = Objects.hash(key, name, isRequired, acceptsArg, isArgRequired, description);
      result = 31 * result + Arrays.hashCode(dependencies);
      return result;
    }

    @Override
    public String toString() {
      return "OptionBuilder{" +
          "key=" + key +
          ", name='" + name + '\'' +
          ", isRequired=" + isRequired +
          ", acceptsArg=" + acceptsArg +
          ", isArgRequired=" + isArgRequired +
          ", dependencies=" + Arrays.toString(dependencies) +
          ", description='" + description + '\'' +
          '}';
    }
  }
}
