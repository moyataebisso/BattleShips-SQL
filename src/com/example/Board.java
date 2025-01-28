package com.example;
//package com.example;
import java.util.Scanner;
import java.util.Random;
import java.lang.Math;

public class Board {
    private Cell[][] cells;
    private Cell[][] debug;
    private Boat[] boats;
    private int turns;
    private int b_remain;
    private int pp;
    private boolean deb;

    public Board(int g) {
        this.cells = new Cell[g][g];
        this.debug = new Cell[g][g];
        this.turns = 1;
        this.deb = false;
        b_remain = 0;
        pp = 1;

        for (int i = 0; i < g; i++) {
            for (int j = 0; j < g; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = new Cell(i, j, '-');
                    debug[i][j] = new Cell(i, j, '-');
                }
            }
        }
        this.placeBoats(g / 3);
    }

    public void setDebug() {
        this.deb = true;
    }

    public void showBoard() {
        if (this.deb) {
            this.print();
        } else {
            this.display();
        }
    }

    public void changeCellStatus(Cell c, char ch) {
        this.cells[c.get_r()][c.get_c()].set_status(ch);
    }

    public void placeBoats(int difficulty) {
        int numBoats = (2 * difficulty) - 1;
        Boat[] boatArray = new Boat[numBoats];
        for (int i = 0; i < numBoats; i++) {
            boatArray[i] = new Boat(i + 2, new Random().nextBoolean(), new Cell[i + 2]);
        }

        for (Boat b : boatArray) {
            int boatSize = b.get_size();
            int boardSize = this.cells.length;
            boolean validLocation = false;

            while (!validLocation) {
                if (b.get_dir()) {
                    int col = new Random().nextInt(boardSize + 1 - boatSize);
                    int row = new Random().nextInt(boardSize);
                    Cell[] c = new Cell[boatSize];
                    for (int i = 0; i < boatSize; i++) {
                        c[i] = new Cell(row, col + i, 'B');
                    }
                    b.setLocation(c);
                } else {
                    int row = new Random().nextInt(boardSize + 1 - boatSize);
                    int col = new Random().nextInt(boardSize);
                    Cell[] c = new Cell[boatSize];
                    for (int i = 0; i < boatSize; i++) {
                        c[i] = new Cell(row + i, col, 'B');
                    }
                    b.setLocation(c);
                }
                validLocation = isVaildBoatLocation(b);
            }
        }
        this.boats = boatArray;
        this.b_remain = this.boats.length;
        this.pp = this.boats.length;
    }

    public boolean isVaildBoatLocation(Boat b) {
        Cell[] boatCoords = b.get_cc();
        for (Cell coord : boatCoords) {
            int row = coord.get_r();
            int col = coord.get_c();
            if (this.cells[row][col].get_status() == 'B') {
                return false;
            }
        }
        for (Cell coord : boatCoords) {
            this.changeCellStatus(coord, 'B');
        }
        return true;
    }

    public int getB_remain() {
        return b_remain;
    }

    public int getTurns() {
        return turns;
    }

    public int getPp() {
        return pp;
    }

    public void print() {
        for (Cell[] row : debug) {
            for (Cell cell : row) {
                System.out.print(cell.get_status() + " ");
            }
            System.out.println();
        }
    }

    public void display() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                System.out.print(cell.get_status() + " ");
            }
            System.out.println();
        }
        for (Boat b : this.boats) {
            if (b != null) {
                String res = "Boat Data: size = " + b.get_size() + " coordinates = ";
                for (Cell c : b.get_cc()) {
                    res += "(" + c.get_r() + ", " + c.get_c() + ") ";
                }
                System.out.println(res);
            } else {
                System.out.println("Boat Data: Sunk");
            }
        }
    }

    public void fire(int x, int y) {
        if (x < 0 || y < 0 || x >= this.cells.length || y >= this.cells.length) {
            System.out.println("Penalty input not in range, lose next turn");
            turns += 2;
            return;
        }

        if (cells[x][y].get_status() == 'B') {
            cells[x][y].set_status('H');
            debug[x][y].set_status('H');
            int boatsRemainingBeforeShot = this.b_remain;
            this.boatSunk();
            if (boatsRemainingBeforeShot > this.b_remain) {
                System.out.println("Turn " + this.turns + ": sunk");
            } else {
                System.out.println("Turn " + this.turns + ": hit");
            }
        } else {
            System.out.println("Turn " + this.turns + ": miss");
            cells[x][y].set_status('M');
            debug[x][y].set_status('M');
        }
        turns++;
    }

    public void missile(int x, int y) {
        if (pp > 0) {
            pp--;
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && i < cells.length && j >= 0 && j < cells.length) {
                        cells[i][j].set_status('H');
                    }
                }
            }
        } else {
            System.out.println("Missile has been used the max amount of times");
        }
    }

    public void drone() {
        if (pp > 0) {
            pp--;
            int randRow = new Random().nextInt(cells.length);
            int count = 0;
            for (int j = 0; j < cells.length; j++) {
                if (cells[randRow][j].get_status() == 'B' || cells[randRow][j].get_status() == 'H') {
                    count++;
                }
            }
            System.out.println("Drone has scanned " + count + " targets in row " + randRow);
        } else {
            System.out.println("Drone has been used the max amount of times");
        }
    }

    public void submarine(int x, int y) {
        if (pp > 0) {
            pp--;
            if (cells[x][y].get_status() == 'B' || cells[x][y].get_status() == 'H') {
                for (Cell c : boats[0].get_cc()) {
                    cells[c.get_r()][c.get_c()].set_status('H');
                }
            } else {
                System.out.println("miss");
            }
        } else {
            System.out.println("Submarine has been used the max amount of times");
        }
    }

    public void boatSunk() {
        for (Boat b : boats) {
            if (b != null) {
                boolean sunk = true;
                for (Cell c : b.get_cc()) {
                    if (cells[c.get_r()][c.get_c()].get_status() == 'B') {
                        sunk = false;
                        break;
                    }
                }
                if (sunk) {
                    b = null;
                    b_remain--;
                }
            }
        }
    }
}
