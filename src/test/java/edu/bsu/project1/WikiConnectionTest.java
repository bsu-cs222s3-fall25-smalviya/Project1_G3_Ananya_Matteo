package edu.bsu.project1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

// Tests multiple inputs
public class WikiConnectionTest {
    @Test
    public void testiphoneArticle() throws Exception {
        String json = WikiConnection.fetchArticle("iphone");
        Assertions.assertNotNull(json);
        System.out.println(json.length());
    }

    @Test
    public void testBallStateArticle() throws Exception {
        String json = WikiConnection.fetchArticle("ball state");
        Assertions.assertNotNull(json);
        System.out.println(json.length());
    }

    @Test
    public void testSteamArticle() throws Exception {
        String json = WikiConnection.fetchArticle("steam");
        Assertions.assertNotNull(json);
        System.out.println(json.length());
    }

    @Test
    public void testWhitehouseArticle() throws Exception {
        String json = WikiConnection.fetchArticle("white house");
        Assertions.assertNotNull(json);
        System.out.println(json.length());
    }
}