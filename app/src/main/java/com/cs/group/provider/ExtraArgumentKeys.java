package com.cs.group.provider;

public enum ExtraArgumentKeys {
    OPEN_ACTIVITES("OPEN_ACTIVITES");

    private String text;

    ExtraArgumentKeys(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
