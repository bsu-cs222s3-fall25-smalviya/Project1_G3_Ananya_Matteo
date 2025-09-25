package edu.bsu.project1;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RevisionParser {
// turns the revision entries into a list of revision objects. Each hols a username and a timestamp.
    public List<Revision> parse(InputStream input) throws Exception {
        String json = new String(input.readAllBytes(), StandardCharsets.UTF_8);
        ReadContext ctx = JsonPath.parse(json);

        List<Map<String, Object>> rawRevisions = ctx.read("$.query.pages.*.revisions[*]");

        List<Revision> revisions = new ArrayList<>();
        for (Map<String, Object> rev : rawRevisions) {
            String username = rev.get("user").toString();
            String timestamp = rev.get("timestamp").toString();
            revisions.add(new Revision(username, timestamp));
        }
        return revisions;
    }
}