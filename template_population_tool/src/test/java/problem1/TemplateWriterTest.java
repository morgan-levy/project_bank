package problem1;

import org.graalvm.compiler.lir.LIRInstruction;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.Assert.*;

public class TemplateWriterTest {
    private List<List<String>> templates;
    private String dest;
    private String destAlt;
    private String filename;

    private String templatePath;

    private TemplateWriter writer;
    private TemplateWriter writerHash;
    private TemplateWriter writerAlt;
    private TemplateReader reader;
    private TemplateParser parser;

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

    private String testdir;

    private String expectedString;


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
        reader = new TemplateReader(templatePath);
        filename = reader.getFilename();

        parser = new TemplateParser(reader.getTemplate(), data);
        parser.populateModListHolder();
        templates = parser.getModListHolder();

        dest = "src/test/java/testOutput/";
        testdir = "src/test/java/testdir/";


        writer = new TemplateWriter(templates, dest, filename);
        writerHash = new TemplateWriter(templates, dest, filename);
        writerAlt = new TemplateWriter(templates, dest, filename);

        expectedString = "TemplateWriter{" +
                "templates=" + templates +
                ", dest='" + dest + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }

    @Test
    public void writeTemplate() throws IOException {
         assertEquals(0, writer.writeTemplate(reader.getTemplate(), testdir));
    }

    @Test(expected = IOException.class)
    public void writePopulatedTemplatesException() throws IOException {
        TemplateWriter temp_writer = new TemplateWriter(templates, testdir, "Tester");
        temp_writer.writePopulatedTemplates();
    }

    @Test
    public void writePopulatedTemplates() throws IOException {
         writer.writePopulatedTemplates();
    }

    @Test
    public void testEquals() {
        assertTrue(writer.equals(writer));
        assertTrue(writer.equals(writerHash));
        assertFalse(writer.equals(null));

    }

    @Test
    public void testHashCode() {
        assertEquals(writer.hashCode(), writerHash.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(expectedString, writer.toString());
    }
}