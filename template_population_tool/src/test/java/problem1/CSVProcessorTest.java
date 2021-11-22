package problem1;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CSVProcessorTest {
  CSVProcessor testProcessor1;
  CSVProcessor testProcessor2;
  CSVProcessor testProcessor3;
  String validTestPath1;
  String validTestPath2;
  String nonExistentTestPath;

  @Before
  public void setUp() throws Exception {
    validTestPath1 = "src/main/java/Input/nonprofit-supporters.csv";
    testProcessor1 = new CSVProcessor(validTestPath1);
    validTestPath2 = "src/main/java/Input/WA-mammals-csv.csv";
    testProcessor2 = new CSVProcessor(validTestPath2);
    nonExistentTestPath = "src/main/java/Input/non-existent-csv.csv";
  }

  @Test
  public void getFilePath() {
    assertTrue(testProcessor1.getFilePath().equals(validTestPath1));
  }

  @Test(expected = FileNotFoundException.class)
  public void fileNotFound() throws FileNotFoundException, IOException {
    testProcessor3 = new CSVProcessor(nonExistentTestPath);
  }

  @Test
  public void getRawData() {
    List<String> expected = new ArrayList<String>();
    String lineOne =
        "\"animal_name\",\"latin_name\",\"diet\",\"habitat\"";
    String lineTwo =
        "\"river otter\",\"lupus lupus\",\"fish, snakes, and snails\",\"rivers and marshes\"";
    String lineThree =
        "\"porcupine\",\"erethizon dorsatum\",\"saplings and leaves\",\"forested areas\"";
    String lineFour =
        "\"red squirrel\",\"tamiasciurus hudsonicus\",\"seeds of conifers\",\"coniferous forests\"";
    expected.add(lineOne);
    expected.add(lineTwo);
    expected.add(lineThree);
    expected.add(lineFour);
    assertTrue(testProcessor2.getRawData().equals(expected));
  }

  @Test
  public void getSupporterInfo() {
    Map<String, ArrayList<String>> expected = new LinkedHashMap<>();
    String animalName = "animal_name";
    ArrayList<String> animalNames = new ArrayList<String>(){{
      add("river otter");
      add("porcupine");
      add("red squirrel");
    }};
    expected.put(animalName, animalNames);
    String latinName = "latin_name";
    ArrayList<String> latinNames = new ArrayList<String>(){{
      add("lupus lupus");
      add("erethizon dorsatum");
      add("tamiasciurus hudsonicus");
    }};
    expected.put(latinName, latinNames);
    String diet = "diet";
    ArrayList<String> diets = new ArrayList<String>(){{
      add("fish, snakes, and snails");
      add("saplings and leaves");
      add("seeds of conifers");
    }};
    expected.put(diet, diets);
    String habitat = "habitat";
    ArrayList<String> habitats = new ArrayList<String>(){{
      add("rivers and marshes");
      add("forested areas");
      add("coniferous forests");
    }};
    expected.put(habitat, habitats);
    assertTrue(testProcessor2.getSupporterInfo().equals(expected));
  }



  @Test
  public void testEqualsReflexivity1() {
    assertTrue(testProcessor1.equals(testProcessor1));
  }

  @Test
  public void testEqualsReflexivity2() {
    assertFalse(testProcessor1.equals(testProcessor2));
  }

  @Test
  public void testEqualsDifferentDataTypes() {
    assertFalse(testProcessor1.equals(validTestPath1));
  }

  @Test
  public void testEqualsSameFields() throws IOException {
    testProcessor3 = new CSVProcessor(validTestPath1);
    assertTrue(testProcessor1.equals(testProcessor3));
  }

  @Test
  public void testEqualsDifferentFilePath() throws IOException {
    testProcessor3 = new CSVProcessor(validTestPath2);
    assertFalse(testProcessor1.equals(testProcessor3));
  }

  @Test
  public void testHashCode() throws IOException {
    testProcessor3 = new CSVProcessor(validTestPath1);
    assertEquals(testProcessor1.hashCode(), testProcessor3.hashCode());
  }

  @Test
  public void testToString() {
    String expected = "NonprofitSupportersProcessor{"
        + "filePath='src/main/java/Input/WA-mammals-csv.csv', "
        + "rawData=[\"animal_name\",\"latin_name\",\"diet\",\"habitat\", \"river otter\","
        + "\"lupus lupus\",\"fish, snakes, and snails\",\"rivers and marshes\", \"porcupine\","
        + "\"erethizon dorsatum\",\"saplings and leaves\",\"forested areas\", \"red squirrel\","
        + "\"tamiasciurus hudsonicus\",\"seeds of conifers\",\"coniferous forests\"], "
        + "supporterInfo={animal_name=[river otter, porcupine, red squirrel], "
        + "latin_name=[lupus lupus, erethizon dorsatum, tamiasciurus hudsonicus], "
        + "diet=[fish, snakes, and snails, saplings and leaves, seeds of conifers], "
        + "habitat=[rivers and marshes, forested areas, coniferous forests]}}";
    assertTrue(testProcessor2.toString().equals(expected));
  }
}