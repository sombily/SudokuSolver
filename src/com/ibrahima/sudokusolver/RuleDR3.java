package com.ibrahima.sudokusolver;

public class RuleDR3 extends DeductionRule {

    @Override
    public boolean apply(SudokuGrid grid) {
        boolean progress = false;
        //System.out.println("Début de l'application de DR3");
        // Parcourt tous les chiffres de 1 à 9
        for (int num = 1; num <= 9; num++) {
            // Applique la déduction pour chaque ligne
            for (int row = 0; row < 9; row++) {
                if (placeIfSingleOptionInRow(grid, row, num)) {
                    progress = true;
                    System.out.println("DR3 a rempli une cellule.");
                }
            }

            // Applique la déduction pour chaque colonne
            for (int col = 0; col < 9; col++) {
                if (placeIfSingleOptionInColumn(grid, col, num)) {
                    progress = true;
                }
            }

            // Applique la déduction pour chaque bloc 3x3
            for (int block = 0; block < 9; block++) {
                if (placeIfSingleOptionInBlock(grid, block, num)) {
                    progress = true;
                }
            }
        }
        if (!progress) {
            //System.out.println("DR3 n'a fait aucun progrès.");
        }
        return progress;
    }

    private boolean placeIfSingleOptionInRow(SudokuGrid grid, int row, int num) {
        int possibleColumn = -1;
        for (int col = 0; col < 9; col++) {
            if (grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)) {
                if (possibleColumn == -1) {
                    possibleColumn = col;  // Colonne possible trouvée
                } else {
                    return false;  // Plus d'un emplacement possible dans la ligne
                }
            }
        }

        // Place `num` uniquement si `possibleColumn` est défini
        if (possibleColumn != -1) {
            grid.setCell(row, possibleColumn, num);
            return true;
        }
        return false;
    }

    private boolean placeIfSingleOptionInColumn(SudokuGrid grid, int col, int num) {
        int possibleRow = -1;
        for (int row = 0; row < 9; row++) {
            if (grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)) {
                if (possibleRow == -1) {
                    possibleRow = row;  // Ligne possible trouvée
                } else {
                    return false;  // Plus d'un emplacement possible dans la colonne
                }
            }
        }

        // Place `num` uniquement si `possibleRow` est défini
        if (possibleRow != -1) {
            grid.setCell(possibleRow, col, num);
            return true;
        }
        return false;
    }

    private boolean placeIfSingleOptionInBlock(SudokuGrid grid, int block, int num) {
        int possibleRow = -1;
        int possibleColumn = -1;
        int startRow = (block / 3) * 3;
        int startCol = (block % 3) * 3;

        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)) {
                    if (possibleRow == -1 && possibleColumn == -1) {
                        possibleRow = row;
                        possibleColumn = col;
                    } else {
                        return false;  // Plus d'un emplacement possible dans le bloc
                    }
                }
            }
        }

        // Place `num` uniquement si `possibleRow` et `possibleColumn` sont définis
        if (possibleRow != -1 && possibleColumn != -1) {
            grid.setCell(possibleRow, possibleColumn, num);
            return true;
        }
        return false;
    }

    // Vérifie si `num` peut être placé dans une cellule sans violer les règles du Sudoku
    private boolean canPlace(SudokuGrid grid, int row, int col, int num) {
        // Vérification de la ligne et de la colonne pour `num`
        for (int i = 0; i < 9; i++) {
            if (grid.getCell(row, i) == num || grid.getCell(i, col) == num) {
                return false;
            }
        }

        // Vérification du bloc 3x3 pour `num`
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
