package com.example;
public class Boat {
    private int size;
    private boolean direction;
    private Cell [] cells;
    public Boat(int s,boolean d, Cell [] c){
        size = s;
        direction = d;
        cells = c;
    }

    public int get_size(){return size;}
    public boolean get_dir(){return direction;}
    public Cell get_c(int z){return cells[z];}
    public void set_size(int si){size = si;}
    public void set_dir(boolean di){direction = di;}
    public Cell [] get_cc(){return cells;
    }
    public void setLocation(Cell [] cell){
        this.cells = cell;
    }
}
