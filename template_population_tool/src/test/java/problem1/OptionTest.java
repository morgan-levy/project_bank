package problem1;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import problem1.Option.OptionBuilder;

public class OptionTest {

  private Character expectedKey;
  private String expectedName;
  private Boolean expectedIsRequired;
  private Boolean expectedAcceptsArgs;
  private Boolean expectedIsArgRequired;
  private Option[] expectedDependencies;
  private String expectedDescription;

  private Option testOption;

  @Before
  public void setUp() throws Exception {
    expectedKey = 't';
    expectedName = "test";
    expectedIsRequired = true;
    expectedAcceptsArgs = true;
    expectedIsArgRequired = false;
    expectedDependencies = new Option[]{new Option.OptionBuilder(
        'd', "dependency", true
    ).build()};
    expectedDescription = "test option";

    testOption = new Option.OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();
  }

  @Test
  public void getShortPrefix() {
    assertEquals('-', (char) Option.getShortPrefix());
  }

  @Test
  public void getLongPrefix() {
    assertEquals("--", Option.getLongPrefix());
  }

  @Test
  public void getKey() {
    assertEquals(expectedKey, testOption.getKey());
  }

  @Test
  public void getName() {
    assertEquals(expectedName, testOption.getName());
  }

  @Test
  public void getPrefixedKey() {
    assertEquals("-" + expectedKey, testOption.getPrefixedKey());
  }

  @Test
  public void getPrefixedName() {
    assertEquals("--" + expectedName, testOption.getPrefixedName());
  }

  @Test
  public void isRequired() {
    assertEquals(expectedIsRequired, testOption.isRequired());
  }

  @Test
  public void acceptsArg() {
    assertEquals(expectedAcceptsArgs, testOption.acceptsArg());
  }

  @Test
  public void isArgRequired() {
    assertEquals(expectedIsArgRequired, testOption.isArgRequired());
  }

  @Test
  public void getDependencies() {
    assertArrayEquals(expectedDependencies, testOption.getDependencies());
  }

  @Test
  public void getDescription() {
    assertEquals(expectedDescription, testOption.getDescription());
  }

  @Test
  public void testEqualsReflexivity() {
    assertEquals(testOption, testOption);
  }

  @Test
  public void testEqualsReflexivity2() {
    Option testOption2 = new Option.OptionBuilder('x', expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertNotEquals(testOption, testOption2);
  }

  @Test
  public void testEqualsSymmetry() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertEquals(testOption, testOption2);
    assertEquals(testOption2, testOption);
  }

  @Test
  public void testEqualsDifferentTypes() {
    assertNotEquals(testOption, testOption.getDependencies());
  }

  @Test
  public void testEqualsNull() {
    assertNotEquals(testOption, null);
  }

  @Test
  public void testEqualsDifferentName() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, "diff", expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertNotEquals(testOption, testOption2);
  }

  @Test
  public void testEqualsDifferentIsRequired() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, expectedName, !expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertNotEquals(testOption, testOption2);
  }

  @Test
  public void testEqualsDifferentAcceptsArg() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertNotEquals(testOption, testOption2);
  }

  @Test
  public void testEqualsDifferentIsArgRequired() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(!expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertNotEquals(testOption, testOption2);
  }

  @Test
  public void testEqualsDifferentDependencies() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(new Option.OptionBuilder('x', "diff", false).build())
        .description(expectedDescription)
        .build();

    assertNotEquals(testOption, testOption2);
  }

  @Test
  public void testEqualsDifferentDescription() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(
            "this is a test description and is not the same as the original option's description.")
        .build();

    assertNotEquals(testOption, testOption2);
  }

  @Test
  public void testBuilderEqualsReflexivity() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertEquals(testBuilder1, testBuilder1);
  }

  @Test
  public void testBuilderEqualsReflexivity2() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder('x', expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testBuilderEqualsSymmetry() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertEquals(testBuilder1, testBuilder2);
    assertEquals(testBuilder2, testBuilder1);
  }

  @Test
  public void testBuilderEqualsDifferentTypes() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testOption, testBuilder1);
  }

  @Test
  public void testBuilderEqualsNull() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testBuilder1, null);
  }

  @Test
  public void testBuilderEqualsDifferentName() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, "diffname", expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testBuilderEqualsDifferentIsRequired() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, expectedName, !expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testBuilderEqualsDifferentAcceptsArg() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testBuilderEqualsDifferentIsArgRequired() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(!expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testBuilderEqualsDifferentDependencies() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .description(expectedDescription);

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testBuilderEqualsDifferentDescription() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description("this is a different description.");

    assertNotEquals(testBuilder1, testBuilder2);
  }

  @Test
  public void testHashCodeConsistency() {
    int hash = testOption.hashCode();

    assertEquals(hash, testOption.hashCode());
    assertEquals(hash, testOption.hashCode());
    assertEquals(hash, testOption.hashCode());
    assertEquals(hash, testOption.hashCode());
    assertEquals(hash, testOption.hashCode());
  }

  @Test
  public void testHashCodeEquality() {
    Option testOption2 = new Option.OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertEquals(testOption.hashCode(), testOption2.hashCode());
  }

  @Test
  public void testHashCodeEquality2() {
    Option testOption2 = new Option.OptionBuilder('o', expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription)
        .build();

    assertNotEquals(testOption.hashCode(), testOption2.hashCode());
  }

  @Test
  public void testBuilderHashCodeConsistency() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    int hash = testBuilder1.hashCode();

    assertEquals(hash, testBuilder1.hashCode());
    assertEquals(hash, testBuilder1.hashCode());
    assertEquals(hash, testBuilder1.hashCode());
    assertEquals(hash, testBuilder1.hashCode());
    assertEquals(hash, testBuilder1.hashCode());
  }

  @Test
  public void testBuilderHashCodeEquality() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertEquals(testBuilder1.hashCode(), testBuilder2.hashCode());
  }

  @Test
  public void testBuilderHashCodeEquality2() {
    OptionBuilder testBuilder1 = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    OptionBuilder testBuilder2 = new OptionBuilder('x', expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertNotEquals(testBuilder1.hashCode(), testBuilder2.hashCode());
  }

  @Test
  public void testBuilderToString() {
    OptionBuilder testBuilder = new OptionBuilder(expectedKey, expectedName, expectedIsRequired)
        .acceptsArg(expectedIsArgRequired)
        .dependencies(expectedDependencies)
        .description(expectedDescription);

    assertEquals("OptionBuilder{key=t, name='test', isRequired=true, acceptsArg=true, "
            + "isArgRequired=false, dependencies=[Option{key=d, name='dependency', prefixedKey='-d', "
            + "prefixedName='--dependency', isRequired=true, acceptsArg=false, isArgRequired=false, "
            + "dependencies=[], description=''}], description='test option'}",
        testBuilder.toString());
  }
}