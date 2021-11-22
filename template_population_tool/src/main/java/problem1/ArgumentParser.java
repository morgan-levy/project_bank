package problem1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class to parse command line arguments.
 */
public class ArgumentParser {

  static private final Integer NOT_FOUND = -1;

  // Default options for use with non profit supporter data processor
  static Option[] defaultOptions = {
      new Option.OptionBuilder('e', "email", false)
          .dependencies(new Option.OptionBuilder('E', "email-template", false)
              .acceptsArg(true)
              .description("Directory path to email template file.")
              .build())
          .description("Toggle to enable email template generation.")
          .build(),
      new Option.OptionBuilder('l', "letter", false)
          .dependencies(new Option.OptionBuilder('L', "letter-template", false)
              .acceptsArg(true)
              .description("Directory path to letter template file.")
              .build())
          .description("Toggle to enable letter template generation.")
          .build(),
      new Option.OptionBuilder('o', "output-dir", true)
          .acceptsArg(true)
          .description("Directory path where templates should be written.")
          .build(),
      new Option.OptionBuilder('f', "csv-file", true)
          .acceptsArg(true)
          .description("Directory path to the input CSV data file.")
          .build()
  };

  // Default examples for use with non profit supporter data processor
  static String[] defaultExamples = new String[]{
      "--email --email-template /path/to/template.txt --output-dir /path/to/output "
          + "--csv-file /path/to/data.csv",
      "-l -L /path/to/letter-template.txt -o /path/to/output -f /path/to/data.csv"};

  private final String[] args;
  private final Option[] options;
  private final Set<String> optionNameSet;
  private final Map<String, Integer> argIndexMap;
  private final Map<String, Boolean> switches;
  private final Map<String, String> targets;
  private final String[] examples;
  private final String usage;

  /**
   * Private constructor for class ArgumentParser.
   *
   * @param builder (ArgumentParserBuilder) An ArgumentParserBuilder instance from which to
   *                construct the ArgumentParser.
   */
  private ArgumentParser(ArgumentParserBuilder builder) {
    this.args = builder.args;
    this.options = builder.options;
    this.optionNameSet = this.populateOptionNames();
    this.argIndexMap = this.populateArgumentIndices();
    this.switches = new HashMap<>();
    this.targets = new HashMap<>();
    this.examples = builder.examples;

    this.usage = this.generateUsage();
  }

  /**
   * Processes the provided Options and arguments.
   */
  public void parse() {
    Arrays.stream(this.options).forEach(opt -> this.processOption(opt, this.indexOf(opt)));
  }

  /**
   * Getter method for command line argument Strings provided to the ArgumentParser.
   *
   * @return (String[]) The command line argument String array.
   */
  public String[] getArgs() {
    return args;
  }

  /**
   * Getter method for Options provided to the ArgumentParser.
   *
   * @return (Option[]) The array of Options.
   */
  public Option[] getOptions() {
    return options;
  }

  /**
   * Getter method for the parsed Options that include targets (i.e. that accept an argument).
   *
   * @return (Map) Map of Option name keys and their String argument values.
   */
  public Map<String, String> getTargets() {
    return targets;
  }

  /**
   * Getter method for the parsed Options that are toggles/switches (i.e. Options that do not accept
   * arguments).
   *
   * @return (Map) Map of Option name keys and their Boolean values.
   */
  public Map<String, Boolean> getSwitches() {
    return switches;
  }

  /**
   * Getter method for all parsed Options, including targets and switches.
   *
   * @return (Map) Map of Option name keys and their String or Boolean values.
   */
  public Map<String, Object> getAllParameters() {
    Map<String, Object> params = new HashMap<>();
    params.putAll(this.switches);
    params.putAll(this.targets);

    return params;
  }

  /**
   * Helper method to populate the Option name Set.
   *
   * @return (Set) A Set of all prefixed Option names and keys.
   */
  private Set<String> populateOptionNames() {
    Set<String> names = new HashSet<>();

    Arrays.stream(this.options).forEach(opt -> {
      this.addOptionIdentifiers(names, opt);
      Arrays.stream(opt.getDependencies()).forEach(dep -> this.addOptionIdentifiers(names, dep));
    });

    return names;
  }

  /**
   * Helper method to populate the arguments map with their associated indices.
   *
   * @return (Map) A map containing the arguments that are consistent with the Option format as
   * keys, and their array indices as values.
   */
  private Map<String, Integer> populateArgumentIndices() {
    Map<String, Integer> argIndices = new HashMap<>();

    for (int i = 0; i < this.args.length; i++) {
      String arg = this.args[i];

      if (this.isOption(arg)) {
        if (!this.isValidOption(arg)) {
          throw new ArgumentParserException.UnrecognizedOptionException(arg, usage);
        }

        argIndices.put(arg, i);
      }
    }

    return argIndices;
  }

  /**
   * Helper method to add the identifiers (long name and single character key) of each Option to an
   * input Set.
   *
   * @param set (Set) A Set of strings, to which the Option's identifiers are to be added.
   * @param opt (Option) The Option whose identifiers are to be added to the Set.
   */
  private void addOptionIdentifiers(Set<String> set, Option opt) {
    set.add(opt.getPrefixedName());
    set.add(opt.getPrefixedKey());
  }

  /**
   * Helper method to determine if the passed String has the format of a command line Option or
   * not.
   *
   * @param candidate (String) The argument to be tested.
   * @return (Boolean) Value indicating if the String has an Option format or not.
   */
  private Boolean isOption(String candidate) {
    return candidate.startsWith(Option.getLongPrefix()) ||
        candidate.startsWith(Option.getShortPrefix().toString());
  }

  /**
   * Helper method to determine if the passed String is one of the specified Options.
   *
   * @param candidate (String) The argument to be tested.
   * @return (Boolean) Value indicating if the String is contained in the valid Option names.
   */
  private Boolean isValidOption(String candidate) {
    return this.optionNameSet.contains(candidate);
  }

  /**
   * Helper method to get the index of a particular Option among the command line arguments.
   *
   * @param opt (Option) The option to be located in the arguments array.
   * @return (Integer) The integer index of the Option in the arguments array. Returns -1 if the
   * Option is not present in the array.
   */
  private Integer indexOf(Option opt) {
    Integer nameIndex = this.argIndexMap.getOrDefault(opt.getPrefixedName(), NOT_FOUND);
    Integer keyIndex = this.argIndexMap.getOrDefault(opt.getPrefixedKey(), NOT_FOUND);

    return nameIndex.equals(NOT_FOUND) ? keyIndex : nameIndex;
  }

  /**
   * Helper method to process an individual Option.
   *
   * @param opt    (Option) The option to be processed.
   * @param argIdx (Integer) The integer index of the Option in the arguments array.
   */
  private void processOption(Option opt, Integer argIdx) {
    if (argIdx.equals(NOT_FOUND)) {
      if (opt.isRequired()) {
        throw new ArgumentParserException.MissingRequiredOptionException(opt.getName(), this.usage);
      }

      return;
    }

    if (opt.acceptsArg()) {
      // Case where arg is required and the argument value doesn't exist
      if (opt.isArgRequired() && (argIdx + 1 >= this.args.length ||
          this.isOption(this.args[argIdx + 1]))) {
        throw new ArgumentParserException.MissingArgumentException(opt.getName(), this.usage);
      } else {
        this.targets.put(opt.getName(), this.args[argIdx + 1]);
      }
    } else {
      // Since the option doesn't accept an argument, we treat it as a switch/toggle
      this.switches.put(opt.getName(), true);
    }

    this.processDependencies(opt);
  }

  /**
   * Helper method to process an Option's dependencies.
   *
   * @param opt (Option) The Option whose dependencies are to be processed.
   */
  private void processDependencies(Option opt) {
    Arrays.stream(opt.getDependencies()).forEach(dep -> {
      Integer depIdx = this.indexOf(dep);

      if (depIdx.equals(NOT_FOUND)) {
        // Exception for dependent Options is different from regular required Options
        throw new ArgumentParserException.MissingDependentOptionException(opt.getName(),
            dep.getName(), this.usage);
      }

      this.processOption(dep, depIdx);
    });
  }

  /**
   * Helper method to generate the usage message based on the provided options and examples.
   *
   * @return (String) The usage message, utilized by the various ArgumentParserException subclasses.
   */
  private String generateUsage() {
    StringBuilder usage = new StringBuilder().append("USAGE:\n");

    Arrays.stream(this.options)
        .forEach(option -> usage.append(option.getUsageString()).append("\n"));

    if (this.examples.length > 0) {
      usage.append("\nEXAMPLES:\n");

      Arrays.stream(this.examples).forEach(ex -> usage.append(ex).append("\n\n"));
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
    ArgumentParser that = (ArgumentParser) o;
    return Arrays.equals(getArgs(), that.getArgs()) && Arrays
        .equals(getOptions(), that.getOptions()) && Objects
        .equals(optionNameSet, that.optionNameSet) && Objects
        .equals(argIndexMap, that.argIndexMap) && Objects
        .equals(getSwitches(), that.getSwitches()) && Objects
        .equals(getTargets(), that.getTargets()) && Arrays.equals(examples, that.examples)
        && Objects.equals(usage, that.usage);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(optionNameSet, argIndexMap, getSwitches(), getTargets(), usage);
    result = 31 * result + Arrays.hashCode(getArgs());
    result = 31 * result + Arrays.hashCode(getOptions());
    result = 31 * result + Arrays.hashCode(examples);
    return result;
  }

  @Override
  public String toString() {
    return "ArgumentParser{" +
        "args=" + Arrays.toString(args) +
        ", options=" + Arrays.toString(options) +
        '}';
  }

  /**
   * Builder class for enclosing ArgumentParser class.
   */
  public static class ArgumentParserBuilder {

    private final String[] args;
    private Option[] options = ArgumentParser.defaultOptions;
    private String[] examples = ArgumentParser.defaultExamples;

    /**
     * Constructor for class ArgumentParserBuilder.
     *
     * @param args (String[]) The array of arguments.
     */
    public ArgumentParserBuilder(String[] args) {
      this.args = args;
    }

    /**
     * Creates a new ArgumentParserBuilder with an array of Options specified.
     *
     * @param options (Option[]) The array of Options.
     * @return (ArgumentParserBuilder) New ArgumentParserBuilder instance with the specified Options
     * array.
     */
    public ArgumentParserBuilder options(Option[] options) {
      this.options = options;
      return this;
    }

    /**
     * Creates a new ArgumentParserBuilder with an array of examples specified.
     *
     * @param examples (String[]) The array of example Strings.
     * @return (ArgumentParserBuilder) New ArgumentParserBuilder instance with the specified
     * examples array.
     */
    public ArgumentParserBuilder examples(String[] examples) {
      this.examples = examples;
      return this;
    }

    /**
     * Generates a new ArgumentParser with state mirroring that of the ArgumentParserBuilder.
     *
     * @return (ArgumentParser) The new ArgumentParser instance built from the
     * ArgumentParserBuilder's state.
     */
    public ArgumentParser build() {
      return new ArgumentParser(this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      ArgumentParserBuilder that = (ArgumentParserBuilder) o;
      return Arrays.equals(args, that.args) && Arrays.equals(options, that.options)
          && Arrays.equals(examples, that.examples);
    }

    @Override
    public int hashCode() {
      int result = Arrays.hashCode(args);
      result = 31 * result + Arrays.hashCode(options);
      result = 31 * result + Arrays.hashCode(examples);
      return result;
    }

    @Override
    public String toString() {
      return "ArgumentParserBuilder{" +
          "args=" + Arrays.toString(args) +
          ", options=" + Arrays.toString(options) +
          ", examples=" + Arrays.toString(examples) +
          '}';
    }
  }
}
