package com.ibrahima.sudokusolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SudokuSolver {
    private SudokuGrid grid;
    private List<DeductionRule> rules;
    private List<String> appliedRules;
    private DifficultyEvaluator difficultyEvaluator;
    private boolean hasUsedManualInput;
    private int[] initialState;

    public SudokuSolver(SudokuGrid grid) {
        this.grid = grid;
        this.rules = new ArrayList<>();
        this.appliedRules = new ArrayList<>();
        this.difficultyEvaluator = new DifficultyEvaluator(grid);
        this.hasUsedManualInput = false;
        this.initialState = grid.getGridCopy();

        initializeRules();
    }

    private void initializeRules() {
        DeductionRule rule1 = DeductionRuleFactory.createRule("DR1");
        DeductionRule rule2 = DeductionRuleFactory.createRule("DR2");
        DeductionRule rule3 = DeductionRuleFactory.createRule("DR3");

        rules.add(rule1);
        rules.add(rule2);
        rules.add(rule3);

        grid.addObserver(rule1);
        grid.addObserver(rule2);
        grid.addObserver(rule3);
    }

    private void applyRulesInOrder() {
        boolean hasProgress;
        int maxAttempts = 5;

        do {
            hasProgress = false;

            // Essayer DR1
            DeductionRule dr1 = rules.get(0);
            boolean dr1Progress = dr1.apply(grid);
            if (dr1Progress) {
                hasProgress = true;
                if (!appliedRules.contains("RuleDR1")) {
                    appliedRules.add("RuleDR1");
                }
                System.out.println("\nProgression avec RuleDR1 :");
                grid.printGrid();
                continue;
            }

            // Si DR1 ne fait pas de progrès, essayer DR2
            DeductionRule dr2 = rules.get(1);
            boolean dr2Progress = dr2.apply(grid);
            if (dr2Progress) {
                hasProgress = true;
                if (!appliedRules.contains("RuleDR2")) {
                    appliedRules.add("RuleDR2");
                }
                System.out.println("\nProgression avec RuleDR2 :");
                grid.printGrid();
                continue;
            }

            // Si DR2 ne fait pas de progrès, essayer DR3
            DeductionRule dr3 = rules.get(2);
            boolean dr3Progress = dr3.apply(grid);
            if (dr3Progress) {
                hasProgress = true;
                if (!appliedRules.contains("RuleDR3")) {
                    appliedRules.add("RuleDR3");
                }
                System.out.println("\nProgression avec RuleDR3 :");
                grid.printGrid();
                continue;
            }

            maxAttempts--;
        } while (hasProgress && maxAttempts > 0);

        if (!hasProgress && !grid.isFull()) {
            System.out.println("\nAucune règle (DR1, DR2, DR3) n'a pu faire progresser la grille.");
            System.out.println("Intervention manuelle requise.");
        }
    }
    private boolean hasGridChanged() {
        int[] currentState = grid.getGridCopy();
        for (int i = 0; i < currentState.length; i++) {
            if (currentState[i] != initialState[i]) {
                return true;
            }
        }
        return false;
    }

    private boolean hasInconsistency() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int value = grid.getCell(row, col);
                if (value != 0 && !isConsistent(row, col, value)) {
                    System.out.println("Incohérence détectée pour la valeur " + value +
                            " à la position (" + (row+1) + ", " + (col+1) + ")");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isConsistent(int row, int col, int value) {
        // Vérifie la ligne
        for (int i = 0; i < 9; i++) {
            if (i != col && grid.getCell(row, i) == value) {
                return false;
            }
        }

        // Vérifie la colonne
        for (int i = 0; i < 9; i++) {
            if (i != row && grid.getCell(i, col) == value) {
                return false;
            }
        }

        // Vérifie le bloc 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((startRow + i != row || startCol + j != col) &&
                        grid.getCell(startRow + i, startCol + j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean handleManualInput() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("\nEntrez une valeur (format: ligne colonne valeur, ex: 1 2 5):");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;
            int value = scanner.nextInt();

            if (!isValidInput(row, col, value)) {
                System.out.println("Valeurs invalides. Utilisez des nombres entre 1-9.");
                return false;
            }

            grid.setCell(row, col, value);

            if (hasInconsistency()) {
                System.out.println("Cette valeur crée une incohérence. Veuillez recommencer.");
                return false;
            }

            // Afficher la grille mise à jour
            System.out.println("\nGrille après votre entrée :");
            grid.printGrid();

            return true;
        } catch (Exception e) {
            System.out.println("Format invalide. Utilisez : ligne colonne valeur");
            return false;
        }
    }

    private boolean isValidInput(int row, int col, int value) {
        // Vérifie que les indices sont dans les limites
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            return false;
        }
        // Vérifie que la valeur est entre 1 et 9
        if (value < 1 || value > 9) {
            return false;
        }
        // Vérifie que la case est vide
        if (grid.getCell(row, col) != 0) {
            System.out.println("Cette case est déjà remplie !");
            return false;
        }
        return true;
    }
    public boolean solve() {
        System.out.println("\nDébut de la résolution...");
        //Verifion si la grille est déjà complète
        if(grid.isFull() && !hasInconsistency()){
            System.out.println("La grille est déjà completètement résolue !");
            System.out.println("Aucune règle n'a été nécessaire.");
            return true;
        }
        while (!grid.isFull()) {
            applyRulesInOrder();

            if (!grid.isFull()) {
                if (hasInconsistency()) {
                    System.out.println("\nIncohérence détectée. Veuillez recommencer depuis le début.");
                    return false;
                }

                if (!handleManualInput()) {
                    return false;
                }
                hasUsedManualInput = true;
            }
        }

        if (hasInconsistency()) {
            System.out.println("\nLa grille finale contient des incohérences. Veuillez recommencer.");
            return false;
        }

        System.out.println("\nRésolution terminée !");
        System.out.println("Difficulté de la grille : " + difficultyEvaluator.evaluate());
        System.out.println("Règles appliquées : " + String.join(", ", appliedRules));

        return true;
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

            SudokuSolver solver = new SudokuSolver(grid);

            if (!solver.solve()) {
                System.out.println("La résolution a échoué. Veuillez recommencer avec une nouvelle grille.");
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
    }
}