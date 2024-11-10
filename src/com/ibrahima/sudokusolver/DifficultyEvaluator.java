package com.ibrahima.sudokusolver;

import java.util.ArrayList;
import java.util.List;

public class DifficultyEvaluator {
    private final SudokuGrid originalGrid;

    public DifficultyEvaluator(SudokuGrid grid) {
        this.originalGrid = grid.clone();
    }

    public Difficulty evaluate() {
        // Test DR1 uniquement
        SudokuGrid testGrid = originalGrid.clone();
        if (solveWithRule(testGrid, "DR1")) {
            return Difficulty.EASY;
        }

        // Test avec DR2 (si DR1 n'a pas résolu)
        testGrid = originalGrid.clone();
        if (solveWithRule(testGrid, "DR2")) {
            return Difficulty.MEDIUM;
        }

        /// Test avec DR3 (si DR2 n'a pas résolu)
        testGrid = originalGrid.clone();
        if (solveWithRule(testGrid, "DR3")) {
            return Difficulty.HARD;
        }

        return Difficulty.VERY_HARD;  // Si même toutes les règles ensemble ne résolvent pas

    }


    private boolean solveWithRule(SudokuGrid grid, String ruleType) {
        DeductionRule rule = DeductionRuleFactory.createRule(ruleType);
        boolean progress;
        do {
            progress = rule.apply(grid);
        } while (progress);

        return grid.isFull() && !hasInconsistency(grid);
    }



    private boolean hasInconsistency(SudokuGrid grid) {
        // Vérification des lignes
        for (int row = 0; row < 9; row++) {
            if (hasConflictInRow(grid, row)) return true;
        }

        // Vérification des colonnes
        for (int col = 0; col < 9; col++) {
            if (hasConflictInColumn(grid, col)) return true;
        }

        // Vérification des blocs 3x3
        for (int block = 0; block < 9; block++) {
            if (hasConflictInBlock(grid, block)) return true;
        }

        return false;
    }

    private boolean hasConflictInRow(SudokuGrid grid, int row) {
        boolean[] used = new boolean[10];
        for (int col = 0; col < 9; col++) {
            int value = grid.getCell(row, col);
            if (value != 0) {
                if (used[value]) return true;
                used[value] = true;
            }
        }
        return false;
    }

    private boolean hasConflictInColumn(SudokuGrid grid, int col) {
        boolean[] used = new boolean[10];
        for (int row = 0; row < 9; row++) {
            int value = grid.getCell(row, col);
            if (value != 0) {
                if (used[value]) return true;
                used[value] = true;
            }
        }
        return false;
    }

    private boolean hasConflictInBlock(SudokuGrid grid, int block) {
        boolean[] used = new boolean[10];
        int startRow = (block / 3) * 3;
        int startCol = (block % 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = grid.getCell(startRow + i, startCol + j);
                if (value != 0) {
                    if (used[value]) return true;
                    used[value] = true;
                }
            }
        }
        return false;
    }
}