package problem1;

/**
 * Abstract class representing an ArgumentParserException.
 */
public abstract class ArgumentParserException extends RuntimeException {

  /**
   * Constructor for class ArgumentParserException.
   *
   * @param msg   (String) The exception message to be passed onto RuntimeException.
   * @param usage (String) The usage/help string.
   */
  private ArgumentParserException(String msg, String usage) {
    super("ERROR: " + msg + "\n\n" + usage);
  }

  /**
   * Class representing a MissingArgumentException. Thrown when an argument is missing from an
   * Option that requires an argument.
   */
  public static class MissingArgumentException extends ArgumentParserException {

    public MissingArgumentException(String optName, String usage) {
      super("Required argument is missing for option: '" + optName + '\'', usage);
    }
  }

  /**
   * Class representing a MissingDependentOptionException. Thrown when an Option's dependent Option
   * is missing.
   */
  public static class MissingDependentOptionException extends ArgumentParserException {

    public MissingDependentOptionException(String optName, String depName, String usage) {
      super("Argument: '" + optName + "' is missing required option: '" + depName + "'", usage);
    }
  }

  /**
   * Class representing a MissingRequiredOptionException. Thrown when a required Option is not
   * provided.
   */
  public static class MissingRequiredOptionException extends ArgumentParserException {

    public MissingRequiredOptionException(String optName, String usage) {
      super("Required option: '" + optName + "' was not found.", usage);
    }
  }

  /**
   * Class representing an UnrecognizedOptionException. Thrown when an Option that was not specified
   * is included.
   */
  public static class UnrecognizedOptionException extends ArgumentParserException {

    public UnrecognizedOptionException(String optName, String usage) {
      super("Unrecognized option: '" + optName + "'", usage);
    }
  }
}
