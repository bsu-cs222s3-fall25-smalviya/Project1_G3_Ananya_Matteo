package edu.bsu.project1;



import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import java.util.ArrayList;
import java.util.List;

public class RevisionParser {

    public WikipediaResponse parse(String json) {
        ReadContext ctx = JsonPath.parse(json);
        WikipediaResponse response = new WikipediaResponse();

        List<String> redirects;
        try {
            redirects = ctx.read("$.query.redirects[*].to", List.class);
        } catch (com.jayway.jsonpath.PathNotFoundException e) {
            redirects = List.of();
        }
        if (!redirects.isEmpty()) {
            response.setRedirectTarget(redirects.get(0));
        }

        List<String> users = ctx.read("$.query.pages.*.revisions[*].user", List.class);
        List<String> timestamps = ctx.read("$.query.pages.*.revisions[*].timestamp", List.class);

        List<Revision> revisions = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            revisions.add(new Revision(users.get(i), timestamps.get(i)));
        }
        response.setRevisions(revisions);
        return response;
    }
}