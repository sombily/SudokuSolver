package com.ibrahima.sudokusolver;

public class RuleDR3 extends DeductionRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean progress = false;

        // 1. D'abord essayer Hidden Singles
        if (applyHiddenSingles(grid)) {
            progress = true;
        }

        // 2. Si pas de progrès, essayer Pointing Pairs
        if (!progress && applyPointingPairs(grid)) {
            progress = true;
        }

        // 3. Si toujours pas de progrès, essayer Hidden Pairs
        if (!progress && applyHiddenPairs(grid)) {
            progress = true;
        }

        return progress;
    }

    // Hidden Singles : quand un chiffre n'apparaît comme possible que dans une seule cellule
    private boolean applyHiddenSingles(SudokuGrid grid) {
        for (int num = 1; num <= 9; num++) {
            // Vérifier chaque bloc 3x3
            for (int blockRow = 0; blockRow < 3; blockRow++) {
                for (int blockCol = 0; blockCol < 3; blockCol++) {
                    int possibleRow = -1;
                    int possibleCol = -1;
                    int count = 0;

                    // Parcourir chaque cellule du bloc
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            int row = blockRow * 3 + i;
                            int col = blockCol * 3 + j;

                            if (grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)) {
                                possibleRow = row;
                                possibleCol = col;
                                count++;
                            }
                        }
                    }

                    if (count == 1) {
                        grid.setCell(possibleRow, possibleCol, num);
                        //System.out.println("\nProgression avec RuleDR3 (Hidden Single) :");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Pointing Pairs : quand un chiffre dans un bloc ne peut être que dans une ligne ou colonne
    private boolean applyPointingPairs(SudokuGrid grid) {
        for (int num = 1; num <= 9; num++) {
            for (int block = 0; block < 9; block++) {
                int startRow = (block / 3) * 3;
                int startCol = (block % 3) * 3;

                // Vérifier alignement en ligne
                boolean[] rowPossible = new boolean[3];
                int rowCount = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (grid.getCell(startRow + i, startCol + j) == 0 &&
                                canPlace(grid, startRow + i, startCol + j, num)) {
                            rowPossible[i] = true;
                            rowCount++;
                        }
                    }
                }

                // Si le chiffre ne peut être que dans une ligne du bloc
                if (rowCount > 1) {
                    for (int i = 0; i < 3; i++) {
                        if (rowPossible[i]) {
                            int row = startRow + i;
                            boolean progress = false;

                            // Éliminer les possibilités hors du bloc
                            for (int col = 0; col < 9; col++) {
                                if (col < startCol || col >= startCol + 3) {
                                    if (grid.getCell(row, col) == 0 &&
                                            canPlace(grid, row, col, num)) {
                                        progress = true;
                                        grid.setCell(row, col, num);
                                        return true;
                                    }
                                }
                            }
                            if (progress) return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Hidden Pairs : quand deux chiffres ne peuvent être que dans deux cellules d'une unité
    private boolean applyHiddenPairs(SudokuGrid grid) {
        // Pour chaque paire de chiffres
        for (int num1 = 1; num1 <= 8; num1++) {
            for (int num2 = num1 + 1; num2 <= 9; num2++) {
                // Vérifier chaque ligne
                for (int row = 0; row < 9; row++) {
                    int[] positions = new int[2];
                    int count = 0;

                    // Trouver les positions possibles pour la paire
                    for (int col = 0; col < 9; col++) {
                        if (grid.getCell(row, col) == 0) {
                            if (canPlace(grid, row, col, num1) &&
                                    canPlace(grid, row, col, num2)) {
                                if (count < 2) {
                                    positions[count] = col;
                                }
                                count++;
                            }
                        }
                    }

                    // Si exactement deux positions trouvées
                    if (count == 2) {
                        boolean progress = false;
                        // Éliminer les autres possibilités dans ces cellules
                        for (int num = 1; num <= 9; num++) {
                            if (num != num1 && num != num2) {
                                for (int pos : positions) {
                                    if (canPlace(grid, row, pos, num)) {
                                        progress = true;
                                        grid.setCell(row, pos, num);
                                        return true;
                                    }
                                }
                            }
                        }
                        if (progress) return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canPlace(SudokuGrid grid, int row, int col, int num) {
        // Vérification ligne et colonne
        for (int i = 0; i < 9; i++) {
            if (grid.getCell(row, i) == num || grid.getCell(i, col) == num) {
                return false;
            }
        }

        // Vérification bloc 3x3
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getCell(startRow + i, startCol + j) == num) {
                    return false;
                }
            }
        }
        return true;
    }
}