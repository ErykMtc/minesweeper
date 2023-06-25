package com.example.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {

    private Field[][] board;
    private int height;
    private int width;
    private int fieldToWin;
    private int bombNum;
    Random generator = new Random();

    public Board(){
        this.height = 0;
        this.width = 0;
        this.fieldToWin = 0;
        this.board = null;
        this.bombNum = 0;
    }

    public Board(int height, int width, int bomb) {
        this.height = height;
        this.width = width;
        this.bombNum = bomb;
        board = new Field[height][width];
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                board[i][j] = new Field(false,0);
            }
        }
    }

    public void generatedBoard(int bomb){
        fieldToWin = height * width - bomb;

        int randX;
        int randY;

        for (int i = 0; i < bomb; i++){
            randX = generator.nextInt(height);
            randY = generator.nextInt(width);

                if (!board[randX][randY].isBomb()){
                    if(randX != 0 && randX != height - 1 && randY != 0 && randY != width - 1) {
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX + 1][randY].setNumber(board[randX + 1][randY].getNumber() + 1);
                        board[randX][randY + 1].setNumber(board[randX][randY + 1].getNumber() + 1);
                        board[randX + 1][randY + 1].setNumber(board[randX + 1][randY + 1].getNumber() + 1);

                        board[randX - 1][randY].setNumber(board[randX - 1][randY].getNumber() + 1);
                        board[randX][randY - 1].setNumber(board[randX][randY - 1].getNumber() + 1);
                        board[randX - 1][randY - 1].setNumber(board[randX - 1][randY - 1].getNumber() + 1);

                        board[randX + 1][randY - 1].setNumber(board[randX + 1][randY - 1].getNumber() + 1);
                        board[randX - 1][randY + 1].setNumber(board[randX - 1][randY + 1].getNumber() + 1);
                    }else if (randX == 0 && randY == 0){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX + 1][randY].setNumber(board[randX + 1][randY].getNumber() + 1);
                        board[randX][randY + 1].setNumber(board[randX][randY + 1].getNumber() + 1);
                        board[randX + 1][randY + 1].setNumber(board[randX + 1][randY + 1].getNumber() + 1);
                    }else if (randX == 0 && randY == width - 1){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX + 1][randY].setNumber(board[randX + 1][randY].getNumber() + 1);
                        board[randX][randY - 1].setNumber(board[randX][randY - 1].getNumber() + 1);
                        board[randX + 1][randY - 1].setNumber(board[randX + 1][randY - 1].getNumber() + 1);
                    }else if (randX == height - 1 && randY == 0){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX][randY + 1].setNumber(board[randX][randY + 1].getNumber() + 1);
                        board[randX - 1][randY].setNumber(board[randX - 1][randY].getNumber() + 1);
                        board[randX - 1][randY + 1].setNumber(board[randX - 1][randY + 1].getNumber() + 1);
                    }else if (randX == height - 1 && randY == width - 1){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX - 1][randY].setNumber(board[randX - 1][randY].getNumber() + 1);
                        board[randX][randY - 1].setNumber(board[randX][randY - 1].getNumber() + 1);
                        board[randX - 1][randY - 1].setNumber(board[randX - 1][randY - 1].getNumber() + 1);
                    }else if (randX == 0){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX + 1][randY].setNumber(board[randX + 1][randY].getNumber() + 1);
                        board[randX][randY + 1].setNumber(board[randX][randY + 1].getNumber() + 1);
                        board[randX + 1][randY + 1].setNumber(board[randX + 1][randY + 1].getNumber() + 1);
                        board[randX][randY - 1].setNumber(board[randX][randY - 1].getNumber() + 1);
                        board[randX + 1][randY - 1].setNumber(board[randX + 1][randY - 1].getNumber() + 1);
                    }else if (randY == 0){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX + 1][randY].setNumber(board[randX + 1][randY].getNumber() + 1);
                        board[randX][randY + 1].setNumber(board[randX][randY + 1].getNumber() + 1);
                        board[randX + 1][randY + 1].setNumber(board[randX + 1][randY + 1].getNumber() + 1);
                        board[randX - 1][randY].setNumber(board[randX - 1][randY].getNumber() + 1);
                        board[randX - 1][randY + 1].setNumber(board[randX - 1][randY + 1].getNumber() + 1);
                    }else if (randX == height - 1){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX][randY + 1].setNumber(board[randX][randY + 1].getNumber() + 1);
                        board[randX - 1][randY].setNumber(board[randX - 1][randY].getNumber() + 1);
                        board[randX][randY - 1].setNumber(board[randX][randY - 1].getNumber() + 1);
                        board[randX - 1][randY - 1].setNumber(board[randX - 1][randY - 1].getNumber() + 1);
                        board[randX - 1][randY + 1].setNumber(board[randX - 1][randY + 1].getNumber() + 1);
                    }else if (randY == width - 1){
                        board[randX][randY].setBomb(true);
                        board[randX][randY].setNumber(0);

                        board[randX + 1][randY].setNumber(board[randX + 1][randY].getNumber() + 1);
                        board[randX - 1][randY].setNumber(board[randX - 1][randY].getNumber() + 1);
                        board[randX][randY - 1].setNumber(board[randX][randY - 1].getNumber() + 1);
                        board[randX - 1][randY - 1].setNumber(board[randX - 1][randY - 1].getNumber() + 1);
                        board[randX + 1][randY - 1].setNumber(board[randX + 1][randY - 1].getNumber() + 1);
                    }

                } else {
                    i--;
                }
        }


    }

    public int getNumber(int height, int width) {
        return board[height][width].getNumber();
    }

    public boolean isBomb(int height, int width){
        return board[height][width].isBomb();
    }

    public boolean isClicked(int height, int width){
        return board[height][width].isClicked();
    }

    public boolean isFlagged(int height, int width){
        return board[height][width].isFlagged();
    }

    public void setClicked(int height, int width){
        fieldToWin--;
        board[height][width].setClicked(true);
    }

    public void antiSetClicked(int height, int width){
        fieldToWin++;
        board[height][width].setClicked(false);
    }

    public void setFlagged(int height, int width){
        board[height][width].setFlagged(true);
    }

    public void antiSetFlagged(int height, int width){
        board[height][width].setFlagged(false);
    }

    public int getFieldToWin() {
        return fieldToWin;
    }

    public boolean winGame(){
        if (fieldToWin == 0) return true;
        return false;
    }

    public Field getBoard(int i, int j) {
        try {
            return board[i][j];
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e);
        }
        return null;
    }

    public Field[][] getArray() {
        return board;
    }

    public void setBoardField(Field[][] board) {
        this.board = board;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setFieldToWin(int fieldToWin) {
        this.fieldToWin = fieldToWin;
    }

    public int getBombNum() {
        return bombNum;
    }

    public void setBombNum(int bombNum) {
        this.bombNum = bombNum;
    }
}
