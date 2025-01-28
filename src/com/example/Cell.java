package com.example;
public class Cell {
    private int row;
    private int col;
    private char status;
    public Cell(int r, int c, char s){
        row = r;
        col = c;
        status = s;
    }
    public char get_status(){return status;}
    public void set_status(char c){status = c;}
    public int get_r(){return row;}
    public int get_c(){return col;}
    public void set_r(int ro){row = ro;}
    public void set_c(int co){col = co;}
}
