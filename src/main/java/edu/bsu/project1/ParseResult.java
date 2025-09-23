package edu.bsu.project1;

import java.util.List;

public final class ParseResult {
    private final String resolvedTitle;
    private final boolean wasRedirected;
    private final boolean pageMissing;
    private final List<Revision> revisions;

    public ParseResult(String resolvedTitle, boolean wasRedirected,
                       boolean pageMissing, List<Revision> revisions) {
        this.resolvedTitle = resolvedTitle;
        this.wasRedirected = wasRedirected;
        this.pageMissing = pageMissing;
        this.revisions = revisions;
    }

    public String resolvedTitle() {
        return resolvedTitle;
    }
    public boolean wasRedirected() {
        return wasRedirected;
    }
    public boolean pageMissing() {
        return pageMissing;
    }
    public List<Revision> revisions() {
        return revisions;
    }
}
