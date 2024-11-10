package com.ibrahima.sudokusolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Patron singleton pour la grille de Sudoku
public class SudokuGrid extends observable {
    private static SudokuGrid instance;
    private int[] grid; // Grille linéaire de taille 81

    // Constructeur pour le patron Singleton
    private SudokuGrid() {
        grid = new int[81]; // Initiation de la grille vide
    }

    // Clone pour l'évaluation de difficulté
    public SudokuGrid clone() {
        SudokuGrid copy = new SudokuGrid();
        copy.grid = this.grid.clone();
        return copy;
    }

    // Réinitialiser l'instance (utile pour l'évaluation de difficulté)
    public static void resetInstance() {
        instance = null;
    }
    public int[] getGridCopy() {
        return grid.clone();
    }

    // Charger la grille depuis un fichier texte
    public void loadGrid(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            for (String value : values) {
                grid[index++] = Integer.parseInt(value.trim());
            }
        }
        reader.close();

        System.out.println("Grille initiale :");
        printGrid();
    }
    // Méthode singleton pour obtenir l'instance unique
    public static SudokuGrid getInstance() {
        if (instance == null) {
            instance = new SudokuGrid();
        }
        return instance;
    }

    // Définir la valeur d'une cellule et notifier les observateurs
    public void setCell(int row, int col, int value) {
        grid[row * 9 + col] = value;
        notifyObservers(this); // Utilise la méthode de la classe parent observable
    }

    // Obtenir la valeur d'une cellule
    public int getCell(int row, int col) {
        return grid[row * 9 + col];
    }

    // Vérifier si la grille est pleine
    public boolean isFull() {
        for (int value : grid) {
            if (value == 0) {
                return false;
            }
        }
        return true;
    }

    // Affichage de la grille avec format et séparations de blocs
    public void printGrid() {
        for (int row = 0; row < 9; row++) {
            if (row > 0 && row % 3 == 0) {
                System.out.println("---------|---------|---------");
            }
            for (int col = 0; col < 9; col++) {
                int value = getCell(row, col);
                System.out.print(value == 0 ? "0" : value);

                if (col < 8) {
                    if ((col + 1) % 3 == 0) {
                        System.out.print(" | ");
                    } else {
                        System.out.print(",");
                    }
                }
            }
            System.out.println();
        }
    }
}