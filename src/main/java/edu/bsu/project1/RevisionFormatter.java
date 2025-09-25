package edu.bsu.project1;



import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class RevisionFormatter {
    public String format(Revision revision, int index) {
        Instant instant = Instant.parse(revision.getTimestamp());
        String time = DateTimeFormatter.ISO_INSTANT.format(instant);
        return index + "  " + time + "  " + revision.getUser();
    }
}