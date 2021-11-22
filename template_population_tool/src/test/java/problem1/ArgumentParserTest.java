package problem1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import problem1.ArgumentParser.ArgumentParserBuilder;

public class ArgumentParserTest {

  private String[] expectedArgs;
  private ArgumentParser testParser;

  @Before
  public void setUp() throws Exception {
    expectedArgs = new String[]{"--email", "--email-template", "/path/to/etemplate/", "--letter",
        "--letter-template", "/path/to/ltemplate/", "--output-dir", "/path/to/output", "--csv-file",
        "/path/to/data.csv"};

    testParser = new ArgumentParserBuilder(expectedArgs).build();
  }

  @Test(expected = ArgumentParserException.UnrecognizedOptionException.class)
  public void testConstructorThrowsException() {
    expectedArgs = new String[]{"--unknown", "--email-template", "/path/to/etemplate/", "--letter",
        "--letter-template", "/path/to/ltemplate/", "--output-dir", "/path/to/output", "--csv-file",
        "/path/to/data.csv"};

    testParser = new ArgumentParserBuilder(expectedArgs).build();
  }

  @Test(expected = ArgumentParserException.MissingRequiredOptionException.class)
  public void testConstructorThrowsException2() {
    expectedArgs = new String[]{"--email", "--email-template", "/path/to/etemplate/", "--letter",
        "--letter-template", "/path/to/ltemplate/", "--output-dir", "/path/to/output"};

    testParser = new ArgumentParserBuilder(expectedArgs).build();
    testParser.parse();
  }

  @Test(expected = ArgumentParserException.MissingArgumentException.class)
  public void testConstructorThrowsException3() {
    expectedArgs = new String[]{"--email", "--email-template", "--letter",
        "--letter-template", "/path/to/ltemplate/", "--output-dir", "/path/to/output", "--csv-file",
        "/path/to/data.csv"};

    testParser = new ArgumentParserBuilder(expectedArgs).build();
    testParser.parse();
  }

  @Test(expected = ArgumentParserException.MissingArgumentException.class)
  public void testConstructorThrowsException4() {
    expectedArgs = new String[]{"--email", "--email-template", "/path/to/etemplate/", "--letter",
        "--letter-template", "/path/to/ltemplate/", "--output-dir", "/path/to/output",
        "--csv-file"};

    testParser = new ArgumentParserBuilder(expectedArgs).build();
    testParser.parse();
  }

  @Test(expected = ArgumentParserException.MissingDependentOptionException.class)
  public void testConstructorThrowsException5() {
    expectedArgs = new String[]{"--email", "--letter",
        "--letter-template", "/path/to/ltemplate/", "--output-dir", "/path/to/output", "--csv-file",
        "/path/to/data.csv"};

    testParser = new ArgumentParserBuilder(expectedArgs).build();
    testParser.parse();
  }

  @Test
  public void testParseOptionalOption() {
    expectedArgs = new String[]{"--letter",
        "--letter-template", "/path/to/ltemplate/", "--output-dir", "/path/to/output", "--csv-file",
        "/path/to/data.csv"};

    testParser = new ArgumentParserBuilder(expectedArgs).build();
    testParser.parse();

    assertNull(testParser.getTargets().get("email-template"));
  }

  @Test
  public void testCustomOptions() {
    Option[] newOptions = new Option[]{
        new Option.OptionBuilder('t', "test", true).build()
    };

    expectedArgs = new String[]{"--test"};

    testParser = new ArgumentParserBuilder(expectedArgs).options(newOptions).build();
    testParser.parse();

    assertTrue(testParser.getSwitches().get("test"));

    // Keys can be used instead of long option names, as long as those keys are associated with
    // the names when the option is instantiated
    expectedArgs = new String[]{"-t"};

    testParser = new ArgumentParserBuilder(expectedArgs).options(newOptions).build();
    testParser.parse();

    assertTrue(testParser.getSwitches().get("test"));
  }

  @Test
  public void getArgs() {
    assertArrayEquals(expectedArgs, testParser.getArgs());
  }

  @Test
  public void getOptions() {
    assertArrayEquals(ArgumentParser.defaultOptions, testParser.getOptions());
  }

  @Test
  public void getTargets() {
    assertEquals(0, testParser.getTargets().size());

    testParser.parse();
    assertEquals(4, testParser.getTargets().size());
    assertEquals("/path/to/etemplate/", testParser.getTargets().get("email-template"));
    assertEquals("/path/to/ltemplate/", testParser.getTargets().get("letter-template"));
    assertEquals("/path/to/output", testParser.getTargets().get("output-dir"));
    assertEquals("/path/to/data.csv", testParser.getTargets().get("csv-file"));
  }

  @Test
  public void getSwitches() {
    testParser.parse();

    assertEquals(2, testParser.getSwitches().size());
    assertTrue(testParser.getSwitches().get("email"));
    assertTrue(testParser.getSwitches().get("letter"));
  }

  @Test
  public void getAllParameters() {
    testParser.parse();

    assertEquals(6, testParser.getAllParameters().size());
    assertEquals("/path/to/etemplate/", testParser.getTargets().get("email-template"));
    assertEquals("/path/to/ltemplate/", testParser.getTargets().get("letter-template"));
    assertEquals("/path/to/output", testParser.getTargets().get("output-dir"));
    assertEquals("/path/to/data.csv", testParser.getTargets().get("csv-file"));
    assertTrue(testParser.getSwitches().get("email"));
    assertTrue(testParser.getSwitches().get("letter"));
  }

  @Test
  public void testEqualsReflexivity() {
    assertEquals(testParser, testParser);
  }

  @Test
  public void testEqualsReflexivity2() {
    Option[] newOptions = new Option[]{
        new Option.OptionBuilder('t', "test", true).build()
    };
    expectedArgs = new String[]{"--test"};
    ArgumentParser testParser2 = new ArgumentParserBuilder(expectedArgs)
        .options(newOptions).build();

    assertNotEquals(testParser, testParser2);
  }

  @Test
  public void testEqualsSymmetry() {
    ArgumentParser testParser2 = new ArgumentParserBuilder(expectedArgs).build();

    assertEquals(testParser, testParser2);
    assertEquals(testParser2, testParser);
  }

  @Test
  public void testEqualsDifferentTypes() {
    assertNotEquals(testParser, ArgumentParser.defaultOptions[0]);
  }

  @Test
  public void testEqualsNull() {
    assertNotEquals(testParser, null);
  }

  @Test
  public void testEqualsDifferentArgs() {
    expectedArgs = new String[]{"--letter", "--letter-template", "/path/to/ltemplate/",
        "--output-dir", "/path/to/output", "--csv-file", "/path/to/data.csv"};
    ArgumentParser testParser2 = new ArgumentParserBuilder(expectedArgs).build();

    assertNotEquals(testParser, testParser2);
  }

  @Test
  public void testEqualsDifferentExamples() {
    String[] newExamples = new String[]{"Test example", "Test example 2", "Test example 3"};
    ArgumentParser testParser2 = new ArgumentParserBuilder(expectedArgs)
        .examples(newExamples).build();

    assertNotEquals(testParser, testParser2);
  }

  @Test
  public void testEqualsBuilderReflexivity() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);

    assertEquals(testBuilder1, testBuilder1);
  }

  @Test
  public void testEqualsBuilderReflexivity2() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);
    expectedArgs = new String[]{"--test"};
    ArgumentParserBuilder testBuilder2 = new ArgumentParserBuilder(expectedArgs);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testEqualsBuilderSymmetry() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);
    ArgumentParserBuilder testBuilder2 = new ArgumentParserBuilder(expectedArgs);

    assertEquals(testBuilder1, testBuilder2);
    assertEquals(testBuilder2, testBuilder1);
  }

  @Test
  public void testEqualsBuilderDifferentTypes() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);

    assertNotEquals(testBuilder1, testParser);
  }

  @Test
  public void testEqualsBuilderNull() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);

    assertNotEquals(testBuilder1, null);
  }

  @Test
  public void testEqualsBuilderDifferentOptions() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);
    Option[] newOptions = new Option[]{
        new Option.OptionBuilder('t', "test", true).build()
    };
    ArgumentParserBuilder testBuilder2 = new ArgumentParserBuilder(expectedArgs).options(newOptions);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testEqualsBuilderDifferentExamples() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);
    String[] newExamples = new String[]{"Test example", "Test example 2", "Test example 3"};
    ArgumentParserBuilder testBuilder2 = new ArgumentParserBuilder(expectedArgs).examples(newExamples);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testHashCodeConsistency() {
    int hash = testParser.hashCode();

    assertEquals(hash, testParser.hashCode());
    assertEquals(hash, testParser.hashCode());
    assertEquals(hash, testParser.hashCode());
    assertEquals(hash, testParser.hashCode());
    assertEquals(hash, testParser.hashCode());
  }

  @Test
  public void testHashCodeEquality() {
    ArgumentParser testParser2 = new ArgumentParserBuilder(expectedArgs).build();

    assertEquals(testParser.hashCode(), testParser2.hashCode());
  }

  @Test
  public void testHashCodeEquality2() {
    Option[] newOptions = new Option[]{
        new Option.OptionBuilder('t', "test", true).build()
    };
    expectedArgs = new String[]{"--test"};
    ArgumentParser testParser2 = new ArgumentParserBuilder(expectedArgs)
        .options(newOptions).build();

    assertNotEquals(testParser.hashCode(), testParser2.hashCode());
  }

  @Test
  public void testBuilderHashCodeConsistency() {
    ArgumentParserBuilder testBuilder = new ArgumentParserBuilder(expectedArgs);
    int hash = testBuilder.hashCode();

    assertEquals(hash, testBuilder.hashCode());
    assertEquals(hash, testBuilder.hashCode());
    assertEquals(hash, testBuilder.hashCode());
    assertEquals(hash, testBuilder.hashCode());
    assertEquals(hash, testBuilder.hashCode());
  }

  @Test
  public void testBuilderHashCodeEquality() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);
    ArgumentParserBuilder testBuilder2 = new ArgumentParserBuilder(expectedArgs);

    assertEquals(testBuilder1.hashCode(), testBuilder2.hashCode());
  }

  @Test
  public void testBuilderHashCodeEquality2() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);
    String[] newExamples = new String[]{"Test example", "Test example 2", "Test example 3"};
    ArgumentParserBuilder testBuilder2 = new ArgumentParserBuilder(expectedArgs).examples(newExamples);

    assertNotEquals(testBuilder1.hashCode(), testBuilder2.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("ArgumentParser{args=[--email, --email-template, /path/to/etemplate/, "
            + "--letter, --letter-template, /path/to/ltemplate/, --output-dir, /path/to/output, "
            + "--csv-file, /path/to/data.csv], options=[Option{key=e, name='email', prefixedKey='-e', "
            + "prefixedName='--email', isRequired=false, acceptsArg=false, isArgRequired=false, "
            + "dependencies=[Option{key=E, name='email-template', prefixedKey='-E', "
            + "prefixedName='--email-template', isRequired=false, acceptsArg=true, isArgRequired=true, "
            + "dependencies=[], description='Directory path to email template file.'}], "
            + "description='Toggle to enable email template generation.'}, Option{key=l, "
            + "name='letter', prefixedKey='-l', prefixedName='--letter', isRequired=false, "
            + "acceptsArg=false, isArgRequired=false, dependencies=[Option{key=L, "
            + "name='letter-template', prefixedKey='-L', prefixedName='--letter-template', "
            + "isRequired=false, acceptsArg=true, isArgRequired=true, dependencies=[], "
            + "description='Directory path to letter template file.'}], "
            + "description='Toggle to enable letter template generation.'}, Option{key=o, "
            + "name='output-dir', prefixedKey='-o', prefixedName='--output-dir', isRequired=true, "
            + "acceptsArg=true, isArgRequired=true, dependencies=[], description='Directory path "
            + "where templates should be written.'}, Option{key=f, name='csv-file', prefixedKey='-f', "
            + "prefixedName='--csv-file', isRequired=true, acceptsArg=true, isArgRequired=true, "
            + "dependencies=[], description='Directory path to the input CSV data file.'}]}",
        testParser.toString());
  }

  @Test
  public void testBuilderToString() {
    ArgumentParserBuilder testBuilder1 = new ArgumentParserBuilder(expectedArgs);
    assertEquals("ArgumentParserBuilder{args=[--email, --email-template, "
        + "/path/to/etemplate/, --letter, --letter-template, /path/to/ltemplate/, "
        + "--output-dir, /path/to/output, --csv-file, /path/to/data.csv], "
        + "options=[Option{key=e, name='email', prefixedKey='-e', prefixedName='--email', "
        + "isRequired=false, acceptsArg=false, isArgRequired=false, dependencies=[Option{key=E, "
        + "name='email-template', prefixedKey='-E', prefixedName='--email-template', "
        + "isRequired=false, acceptsArg=true, isArgRequired=true, dependencies=[], "
        + "description='Directory path to email template file.'}], "
        + "description='Toggle to enable email template generation.'}, Option{key=l, "
        + "name='letter', prefixedKey='-l', prefixedName='--letter', isRequired=false, "
        + "acceptsArg=false, isArgRequired=false, dependencies=[Option{key=L, "
        + "name='letter-template', prefixedKey='-L', prefixedName='--letter-template', "
        + "isRequired=false, acceptsArg=true, isArgRequired=true, dependencies=[], "
        + "description='Directory path to letter template file.'}], "
        + "description='Toggle to enable letter template generation.'}, "
        + "Option{key=o, name='output-dir', prefixedKey='-o', prefixedName='--output-dir', "
        + "isRequired=true, acceptsArg=true, isArgRequired=true, dependencies=[], "
        + "description='Directory path where templates should be written.'}, Option{key=f, "
        + "name='csv-file', prefixedKey='-f', prefixedName='--csv-file', isRequired=true, "
        + "acceptsArg=true, isArgRequired=true, dependencies=[], "
        + "description='Directory path to the input CSV data file.'}], "
        + "examples=[--email --email-template /path/to/template.txt --output-dir /path/to/output "
        + "--csv-file /path/to/data.csv, -l -L /path/to/letter-template.txt -o /path/to/output -f "
        + "/path/to/data.csv]}", testBuilder1.toString());
  }
}