package com.ibrahima.sudokusolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

//Patron singleton pour la grille de Sudoku
public class SudokuGrid {
    private static SudokuGrid instance;
    private int[][] grid;
    private List<DeductionRule> observers = new ArrayList<>();

    private SudokuGrid(){
        grid = new int[9][9]; // Grille vide de 9x9
    }

    //Ajout des observaters (règles)
    public  void addObserver(DeductionRule rule){
        observers.add(rule);
    }

    // Notifie les règles observatrices
    public void notifyObservers(){
        for (DeductionRule rule : observers){
            rule.apply(this);
        }
    }
    public static SudokuGrid getInstance(){
        if(instance == null){
            instance = new SudokuGrid();
        }
        return instance;
    }
    public void setCell(int row, int col, int value){
        grid[row][col] = value;
        notifyObservers(); // notifie pour chaque changement
    }

    public int getCell(int row, int col){
        return grid[row][col];
    }
}
