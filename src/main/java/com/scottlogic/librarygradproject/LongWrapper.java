package com.scottlogic.librarygradproject;

public class LongWrapper {

    private long value;

    public LongWrapper(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    public void increment() {
        this.value++;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
