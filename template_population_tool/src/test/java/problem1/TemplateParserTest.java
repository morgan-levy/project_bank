package problem1;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TemplateParserTest {
    private TemplateParser parser;
    private TemplateParser parserHash;
    private TemplateParser parserAlt;
    private TemplateReader reader;
    private TemplateReader readerAlt;
    private String templatePath;
    private String templatePathAlt;
    private String destPath;
    private Map<String, List<String>> data;
    private ArrayList<String> supporter_email = new ArrayList<>();
    private ArrayList<String> supporter_firstN = new ArrayList<>();
    private ArrayList<String> supporter_lastN = new ArrayList<>();

    private String key_word1;
    private String key_word2;
    private String key_word3;

    private String email1;
    private String email2;
    private String email3;
    private String email4;

    private String firstName1;
    private String firstName2;
    private String firstName3;
    private String firstName4;

    private String lastName1;
    private String lastName2;
    private String lastName3;
    private String lastName4;



    @Before
    public void setUp() throws Exception {

        data = new HashMap<>();
        key_word1 = "email";
        email1 = "FakeEmail@Fakes.com";
        email2 = "Anotherfake@Fakes.com";
        email3 = "notfake@Fakes.com";
        email4 = "Uberfake@Fakes.com";
        supporter_email.add(email1);
        supporter_email.add(email2);
        supporter_email.add(email3);
        supporter_email.add(email4);

        key_word2 = "first_name";
        firstName1 = "John";
        firstName2 = "Jill";
        firstName3 = "Bob";
        firstName4 = "Joan";
        supporter_firstN.add(firstName1);
        supporter_firstN.add(firstName2);
        supporter_firstN.add(firstName3);
        supporter_firstN.add(firstName4);

        key_word3 = "last_name";
        lastName1 = "Doe";
        lastName2 = "Rae";
        lastName3 = "Me";
        lastName4 = "Fa";
        supporter_lastN.add(lastName1);
        supporter_lastN.add(lastName2);
        supporter_lastN.add(lastName3);
        supporter_lastN.add(lastName4);

        data.put(key_word1, supporter_email);
        data.put(key_word2, supporter_firstN);
        data.put(key_word3, supporter_lastN);

        templatePath = "src/main/java/Input/email-template.txt";
        templatePathAlt = "src/main/java/Input/letter-template.txt";
        destPath = "src/main/java/Output/";

        reader = new TemplateReader(templatePath);
        readerAlt = new TemplateReader(templatePathAlt);

        parserHash = new TemplateParser(reader.getTemplate(),data);
        parser = new TemplateParser(reader.getTemplate(),data);
        parserAlt = new TemplateParser(readerAlt.getTemplate(), data);


    }

    @Test
    public void getTemplateInput() {
        assertEquals(reader.getTemplate(), parser.getTemplateInput());
    }

    @Test
    public void getInputData() {
        assertEquals(data, parser.getInputData());
    }

    @Test
    public void insertDataToKeys() {
        parser.insertDataToKeys(0);
    }

    @Test
    public void getModListHolder() {
        parser.getModListHolder();
    }

    @Test
    public void populateModListHolder(){
        parser.populateModListHolder();
    }

    @Test (expected = IllegalArgumentException.class)
    public void populateModListHolderException() throws IOException {
        parserAlt.populateModListHolder();
    }

    @Test
    public void testEquals() {
        assertTrue(parser.equals(parserHash));
        assertTrue(parser.equals(parser));
        assertFalse(parser.equals(data));
        assertFalse(parser.equals(null));
        assertFalse(parser.equals(parserAlt));
    }

    @Test
    public void testHashCode() {
       assertEquals(parser.hashCode(), parserHash.hashCode());

    }

    @Test
    public void testToString() {
    }
}