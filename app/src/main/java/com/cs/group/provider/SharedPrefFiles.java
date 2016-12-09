package com.cs.group.provider;

public enum SharedPrefFiles {
    CUSTOMIZE_SETTINGS("CUSTOMIZE_SETTINGS");

    private String text;

    SharedPrefFiles(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
