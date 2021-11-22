package problem1;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TemplateHandlerTest {
    private String output_dir;

    private String[] template_paths;
    private String[] template_pathsAlt;

    Map<String, List<String>> emptyData;

    private TemplateReader reader;
    private TemplateParser parser;
    private TemplateWriter writer;

    private TemplateHandler templateHandler;
    private TemplateHandler templateHandlerHash;
    private TemplateHandler templateHandlerAlt;

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
        emptyData = new HashMap<>();
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

        output_dir = "src/test/java/testOutput";

        template_paths = new String[] {"src/main/java/Input/email-template.txt"};

        template_pathsAlt = new String[]{"src/main/java/Input/letter-template.txt"};

        templateHandler = new TemplateHandler(template_paths, output_dir, data);
        templateHandlerHash = new TemplateHandler(template_paths, output_dir, data);
        templateHandlerAlt = new TemplateHandler(template_pathsAlt, output_dir, emptyData);
    }

    @Test
    public void writeTemplates() throws IOException {
        templateHandler.writeTemplates();
    }

    @Test
    public void writeTemplatesException() throws IOException {
        templateHandlerAlt.writeTemplates();
    }

    @Test
    public void testEquals() {
        assertTrue(templateHandler.equals(templateHandler));
        assertTrue(templateHandler.equals(templateHandlerHash));
        assertFalse(templateHandler.equals(null));
        assertFalse(templateHandler.equals(data));
    }

    @Test
    public void testHashCode() {
        assertEquals(templateHandler.hashCode(), templateHandlerHash.hashCode());
    }
}