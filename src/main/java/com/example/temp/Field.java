package com.example.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    private boolean bomb;
    private int number;
    private boolean isClicked;
    private boolean isFlagged;

    public Field() {
        this.bomb = false;
        this.number = 0;
        this.isClicked = false;
        this.isFlagged = false;
    }

    public Field(boolean bomb, int number) {
        this.bomb = bomb;
        this.number = number;
        this.isClicked = false;
        this.isFlagged = false;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void setBomb(boolean bomb) {
        this.bomb = bomb;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
