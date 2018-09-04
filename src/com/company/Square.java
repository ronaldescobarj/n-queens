package com.company;

public class Square {
    private char value;
    private int threatLevel;
    private Square parent;

    public Square(char value, int threatLevel) {
        this.value = value;
        this.threatLevel = threatLevel;
        this.parent = null;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public int getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(int threatLevel) {
        this.threatLevel = threatLevel;
    }

    public void increaseThreatLevel() {
        this.threatLevel++;
    }

    public void decreaseThreatLevel() {
        this.threatLevel--;
    }

    public Square getParent() {
        return parent;
    }

    public void setParent(Square parent) {
        this.parent = parent;
    }
}
