package com.ibrahima.sudokusolver;

import java.util.ArrayList;
import java.util.List;

public class DifficultyEvaluator {
    private final SudokuGrid originalGrid;

    public DifficultyEvaluator(SudokuGrid grid) {
        this.originalGrid = grid.clone();
    }

    public Difficulty evaluate() {
        // Test avec DR1 uniquement
        SudokuGrid testGrid = originalGrid.clone();
        if (solveWithRules(testGrid, "DR1")) {
            return Difficulty.EASY;
        }

        // Test avec DR1 + DR2
        testGrid = originalGrid.clone();
        if (solveWithRules(testGrid, "DR1", "DR2")) {
            return Difficulty.MEDIUM;
        }

        // Test avec DR1 + DR2 + DR3
        testGrid = originalGrid.clone();
        if (solveWithRules(testGrid, "DR1", "DR2", "DR3")) {
            return Difficulty.HARD;
        }

        return Difficulty.VERY_HARD;
    }

    private boolean solveWithRules(SudokuGrid grid, String... ruleTypes) {
        boolean progress;
        do {
            progress = false;
            for (String ruleType : ruleTypes) {
                DeductionRule rule = DeductionRuleFactory.createRule(ruleType);
                if (rule.apply(grid)) {
                    progress = true;
                }
            }
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