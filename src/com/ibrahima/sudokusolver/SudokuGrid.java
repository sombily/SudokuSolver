package com.ibrahima.sudokusolver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

//Patron singleton pour la grille de Sudoku
public class SudokuGrid {
    private static SudokuGrid instance;
    private int[] grid; // Grille lineaire de taille 81
    private List<DeductionRule> observers = new ArrayList<>();

    //Constructeur pour le patron Singleton
    private SudokuGrid(){
        grid = new int[81]; // Initiation de la grille vide
    }
    //charger la grille depuis un fichier texte
    public void loadGrid(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        int index = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            for (String value : values) {
                grid[index++] = Integer.parseInt(value);
            }
        }
        reader.close();
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
    //methode singleton pour obtenir l'instance unique
    public static SudokuGrid getInstance(){
        if(instance == null){
            instance = new SudokuGrid();
        }
        return instance;
    }
    // Définir la cellule d'une cellule
    public void setCell(int row, int col, int value){
        grid[row * 9 + col] =  value;
        notifyObservers(); // notifie pour chaque changement
    }
    //Obtenir la valeur d'une cellule
    public int getCell(int row, int col){
        return grid[row * 9 + col];
    }

    //Verifier si la grille est pleine 
    public boolean isFull(){
        for (int value : grid){
            if(value == 0){
                return false;
            }
        }
        return true;
    }

    //Affichage de la grille
    // Affichage de la grille avec format et séparations de blocs
    public void printGrid() {
        for (int row = 0; row < 9; row++) {
            if (row > 0 && row % 3 == 0) {
                System.out.println("---------|---------|---------");  // Ligne de séparation pour les blocs
            }
            for (int col = 0; col < 9; col++) {
                int value = getCell(row, col);
                System.out.print(value == 0 ? "0" : value);  // Affiche "0" pour les cellules vides

                // Ajouter des séparateurs entre colonnes
                if (col < 8) {
                    if ((col + 1) % 3 == 0) {
                        System.out.print(" | ");  // Séparateur de blocs 3x3
                    } else {
                        System.out.print(",");  // Séparateur entre cellules
                    }
                }
            }
            System.out.println();  // Nouvelle ligne après chaque rangée
        }
    }

}
