package com.ibrahima.sudokusolver;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuSolver {
    private SudokuGrid grid;
    private List<DeductionRule> rules;
    private List<String> appliedRules;  // Liste pour suivre les règles appliquées

    public SudokuSolver(SudokuGrid grid) {
        this.grid = grid;
        this.rules = new ArrayList<>();
        this.appliedRules = new ArrayList<>();

        // Ajouter les règles à la liste
        this.rules.add(DeductionRuleFactory.createRule("DR1"));
        this.rules.add(DeductionRuleFactory.createRule("DR2"));
        this.rules.add(DeductionRuleFactory.createRule("DR3"));
    }

    private List<Integer> getPossibleValues(int row, int col) {
        List<Integer> possibleValues = new ArrayList<>();

        for (int num = 1; num <= 9; num++) {
            if (isConsistent(row, col, num)) {
                possibleValues.add(num);  // Ajoute le nombre s'il est valide
            }
        }

        return possibleValues;
    }


    public boolean solve() {
        boolean progress;

        do {
            progress = false;

            for (DeductionRule rule : rules) {
                if (rule.apply(grid)) {
                    progress = true;
                    String ruleName = rule.getClass().getSimpleName();
                    if (!appliedRules.contains(ruleName)) {
                        appliedRules.add(ruleName);
                    }
                }
            }

            // Si aucune progression et la grille n'est pas complète, lance le backtracking
            if (!progress && !grid.isFull()) {
                System.out.println("Les règles de déduction ne suffisent pas, tentative de résolution par backtracking...");
                if (!solveWithBacktracking()) {  // Si le backtracking échoue
                    System.out.println("Backtracking échoué. Veuillez entrer une valeur manuellement.");
                    userInputWithSuggestions();  // Appelle l'intervention utilisateur avec suggestions
                }
                return grid.isFull();  // Retourne l'état de complétion de la grille
            }

        } while (progress && !grid.isFull());

        return grid.isFull();
    }

    private void userInputWithSuggestions() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Veuillez entrer les coordonnées de la cellule où vous souhaitez entrer une valeur.");
        System.out.print("Ligne (0-8) : ");
        int row = scanner.nextInt();
        System.out.print("Colonne (0-8) : ");
        int col = scanner.nextInt();

        // Affiche les valeurs possibles pour aider l'utilisateur
        List<Integer> possibleValues = getPossibleValues(row, col);
        if (possibleValues.isEmpty()) {
            System.out.println("Aucune valeur valide pour cette cellule. Veuillez vérifier les coordonnées.");
            return;
        }

        System.out.println("Valeurs possibles pour la cellule (" + row + ", " + col + ") : " + possibleValues);
        System.out.print("Veuillez choisir une valeur parmi les suggestions : ");
        int value = scanner.nextInt();

        if (possibleValues.contains(value)) {
            grid.setCell(row, col, value);
            System.out.println("Valeur ajoutée avec succès. Continuons la résolution.");
        } else {
            System.out.println("Valeur non valide pour cette cellule. Veuillez réessayer.");
        }
    }

    //Methode pour evaluer la difficulté en fonction des régles
    public String evaluateDifficulty() {
        if (appliedRules.contains("RuleDR1") && !appliedRules.contains("RuleDR2")) {
            return "Facile";
        } else if (appliedRules.contains("RuleDR2") && !appliedRules.contains("RuleDR3")) {
            return "Moyenne";
        } else if (appliedRules.contains("RuleDR3")) {
            return "Difficile";
        } else {
            return "Très Difficile";
        }
    }

    private boolean userInputAndValidate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ligne (0-8) : ");
        int row = scanner.nextInt();
        System.out.println("Colonne (0-8) : ");
        int col = scanner.nextInt();
        System.out.println("Valeur (1-9) : ");
        int value = scanner.nextInt();

        if (isConsistent(row, col, value)) {
            grid.setCell(row, col, value);
            return true;
        } else {
            System.out.println("Valeur incohérente à cette position.");
            return false;
        }
    }

    // Vérifie si l'ajout d'une valeur à une position spécifique respecte les règles du Sudoku
    private boolean isConsistent(int row, int col, int value) {
        // Vérifie la ligne
        for (int i = 0; i < 9; i++) {
            if (grid.getCell(row, i) == value || grid.getCell(i, col) == value) {
                return false;  // Conflit détecté
            }
        }

        // Vérifie le bloc 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getCell(startRow + i, startCol + j) == value) {
                    return false;  // Conflit détecté dans le bloc 3x3
                }
            }
        }

        return true;  // Aucun conflit détecté
    }



    public void printAppliedRules() {
        System.out.println("Règles appliquées pour résoudre la grille : " + String.join(", ", appliedRules));
    }
    public boolean solveWithBacktracking() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid.getCell(row, col) == 0) {  // Si la cellule est vide
                    for (int num = 1; num <= 9; num++) {
                        if (isConsistent(row, col, num)) {  // Vérifie si le nombre est compatible
                            grid.setCell(row, col, num);  // Place le nombre
                            if (solveWithBacktracking()) {  // Appel récursif pour continuer la résolution
                                return true;  // La grille est complète
                            }
                            grid.setCell(row, col, 0);  // Annule si cela ne mène pas à une solution
                        }
                    }
                    return false;  // Aucun nombre ne convient, revient en arrière
                }
            }
        }
        return true;  // Grille complète
    }


    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Veuillez spécifier le fichier de grille en argument.");
            return;
        }

        String filename = args[0];

        try {
            SudokuGrid grid = SudokuGrid.getInstance();
            grid.loadGrid(filename);
            System.out.println("Grille initiale :");
            grid.printGrid();

            SudokuSolver solver = new SudokuSolver(grid);

            if (solver.solve()) {
                System.out.println("Grille résolue :");
                grid.printGrid();
                solver.printAppliedRules();

                //Affiche la difficulté
                String difficulty = solver.evaluateDifficulty();
                System.out.println("Difficulté de la grille: "+difficulty);
            } else {
                System.out.println("La résolution n'a pas pu être complétée en raison d'une incohérence.");
            }

        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}
