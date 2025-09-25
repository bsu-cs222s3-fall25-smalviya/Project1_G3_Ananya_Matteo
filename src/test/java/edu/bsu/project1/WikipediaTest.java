// src/test/java/edu/bsu/project1/WikipediaTest.java
package edu.bsu.project1;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class WikipediaTest {

    @Test
    void testArticlesReturnRevisions() throws Exception {
        WikipediaFetcher fetcher = new WikipediaFetcher();
        RevisionParser parser = new RevisionParser();

        String[] articles = {"Einstein", "Trump", "Frank Zappa"};

        for (String article : articles) {
            InputStream json = fetcher.fetch(article);
            List<Revision> revisions = parser.parse(json);

            // just make sure we got something back
            assertFalse(revisions.isEmpty(), article + " should return revisions");
        }
    }
}