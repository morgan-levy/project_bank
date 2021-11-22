package problem1;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TemplateReaderTest {
    private String stuff;
    private String morestuff;
    private TemplateReader reader;
    private TemplateReader readerHash;
    private TemplateReader readerAlt;

    private String expectedString;
    private List<String> template;
    private ArrayList<String> log;

    @Before
    public void setUp() throws Exception {
        stuff = "src/main/java/Input/email-template.txt";
        morestuff = "src/main/java/Input/letter-template.txt";
        reader = new TemplateReader(stuff);
        readerHash = new TemplateReader(stuff);
        readerAlt = new TemplateReader(morestuff);

        List<String> template;
        template = reader.getTemplate();

        expectedString = "TemplateReader{" +
                "template=" + template +
                ", path='" + stuff + '\'' +
                '}';
     }

    @Test
    public void getFilename() throws IOException {
        TemplateReader reader = new TemplateReader(stuff);
        assertEquals("email-template.txt", reader.getFilename());
    }

    @Test
    public void getTemplate() throws IOException {
        TemplateReader reader = new TemplateReader(stuff);
        List<String> temp_template = new ArrayList<>();
        assertTrue(temp_template.isEmpty());
        temp_template = reader.getTemplate();
        assertFalse(temp_template.isEmpty());
    }

    @Test
    public void testEquals() {
        assertTrue(reader.equals(readerHash));
        assertTrue(reader.equals(reader));
        assertFalse(reader.equals(null));
        assertFalse(reader.equals(readerAlt));
    }

    @Test
    public void testHashCode() {
        assertEquals(reader.hashCode(), readerHash.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(expectedString, reader.toString());
    }
}