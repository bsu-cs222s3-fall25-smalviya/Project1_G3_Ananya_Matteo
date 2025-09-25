package edu.bsu.project1;

import java.util.List;

public class WikipediaResponse {
    private String redirectTarget;
    private List<Revision> revisions;

    public String getRedirectTarget() {
        return redirectTarget;
    }

    public void setRedirectTarget(String redirectTarget) {
        this.redirectTarget = redirectTarget;
    }

    public List<Revision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }
}