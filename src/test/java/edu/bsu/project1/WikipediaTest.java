package edu.bsu.project1;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WikipediaTest {

    @Test
    void testArticlesReturnRevisions() throws Exception {
        WikipediaFetcher fetcher = new WikipediaFetcher();
        RevisionParser parser = new RevisionParser();


        String[] articles = {"Einstein", "Ball State", "Frank Zappa", "Trump", "Macbook",
                "dddhfushfius", "Wikipedia", "iPhone"};

        for (String article : articles) {
            InputStream json = fetcher.fetch(article);
            List<Revision> revisions = parser.parse(json);
            assertFalse(revisions.isEmpty(), article + " should return revisions");
        }
    }

    // makes sure that findRedirect() returns null on invalid JSON.
    @Test
    void findRedirect_null_onMalformedJson() {
        assertNull(WikipediaGuiApp.findRedirect("{nope}".getBytes(StandardCharsets.UTF_8)));
    }

    // checks isMissingPage(), and throws false with a invalid JSON.
    @Test
    void isMissingPage_false_onMalformedJson() {
        assertFalse(WikipediaGuiApp.isMissingPage("{oops".getBytes(StandardCharsets.UTF_8)));
    }

    // makes sure that the method readALL() correctly returns
    // a emopty byte array when the InoutSTream has no data
    @Test
    void readAll_emptyStream_returnsEmptyArray() throws Exception {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        byte[] out = WikipediaGuiApp.readAll(in);
        assertArrayEquals(new byte[0], out);
    }


    //in isMissingPage() reutrns true when a JSON contains a missing key.
    // this is supposed to be a unknown wiki peage.
    @Test
    void isMissingPage_true_whenMissingPresent() {
        String json = """
            { "query": { "pages": { "1": { "missing": true } } } }
            """;
        assertTrue(WikipediaGuiApp.isMissingPage(json.getBytes(StandardCharsets.UTF_8)));
    }

    // checks that isMissingPage() reutrns false when
    // a page does exist.
    @Test
    void isMissingPage_false_whenMissingAbsent() {
        String json = """
            { "query": { "pages": { "1": { "title": "Sample" } } } }
            """;
        assertFalse(WikipediaGuiApp.isMissingPage(json.getBytes(StandardCharsets.UTF_8)));
    }
}