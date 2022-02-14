import static org.junit.Assert.*;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MarkdownParseTest {
    @Test
    public void testFile1() throws IOException {
        String contents= Files.readString(Path.of("./test-file.md"));
        List<String> expect = List.of("https://something.com", "some-page.html");
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testFile2() throws IOException {
        String contents= Files.readString(Path.of("./test-file2.md"));
        List<String> expect = List.of("https://something-else.com", "some-other-page.html");
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testEmptyLink() {
        List<String> expect = List.of("");
        assertEquals(MarkdownParse.getLinks("[]()"), expect);
    }
    
    @Test
    public void testImageVsLinks() throws IOException {
        String contents = Files.readString(Path.of("image-and-link.md"));
        List<String> expect = List.of("a-link.html");
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }

    @Test
    public void testSpace() {
        String contents = "[a]  (b.com)";
        List<String> expect = List.of();
        assertEquals(MarkdownParse.getLinks(contents), expect);
    }
}
