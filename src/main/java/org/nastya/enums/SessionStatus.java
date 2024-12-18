package org.nastya.enums;

public enum SessionStatus {
    ACTIVE("Active"),
    EXPIRED("Expired");

    private final String label;

    SessionStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
