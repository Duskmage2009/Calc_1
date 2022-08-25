package org.example.entity.validator;

public class ExpCharacter {
    private char c;
    private int type;
    private int[] afterCharTypes;

    public ExpCharacter(char c) {
        this.c = c;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int[] getAfterCharTypes() {
        return afterCharTypes;
    }

    public void setAfterCharTypes(int[] afterCharTypes) {
        this.afterCharTypes = afterCharTypes;
    }
}
