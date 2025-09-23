package edu.bsu.project1;

//should be final
public class Revision {
    private String timestampUtc;
    private String username;

    public Revision(String timestampUtc, String username) {
        this.timestampUtc = timestampUtc;
        this.username = username;
    }

    public String timestampUtc() {
        return timestampUtc;
    }
    public String username() {
        return username;
    }
}

