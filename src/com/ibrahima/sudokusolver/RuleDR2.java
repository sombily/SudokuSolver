package com.ibrahima.sudokusolver;

public class RuleDR2 extends DeductionRule {
    @Override
    public boolean apply(SudokuGrid grid) {
        boolean progress = false;

        // Parcourir chaque chiffre de 1 à 9
        for (int num = 1; num <= 9; num++) {
            // Appliquer la règle à chaque ligne
            for (int row = 0; row < 9; row++) {
                if (placeInRow(grid, row, num)) {
                    progress = true;
                    System.out.println("DR2 a placé " + num + " dans la ligne " + row);
                }
            }

            // Appliquer la règle à chaque colonne
            for (int col = 0; col < 9; col++) {
                if (placeInColumn(grid, col, num)) {
                    progress = true;
                    System.out.println("DR2 a placé " + num + " dans la colonne " + col);
                }
            }

            // Appliquer la règle à chaque bloc 3x3
            for (int block = 0; block < 9; block++) {
                if (placeInBlock(grid, block, num)) {
                    progress = true;
                    System.out.println("DR2 a placé " + num + " dans le bloc " + block);
                }
            }
        }
        return progress;
    }

    private boolean placeInRow(SudokuGrid grid, int row, int num) {
        int possibleCol = -1;
        for (int col = 0; col < 9; col++) {
            if (grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)) {
                if (possibleCol == -1) {
                    possibleCol = col;
                } else {
                    return false; // Plus d'un emplacement possible
                }
            }
        }
        if (possibleCol != -1) {
            grid.setCell(row, possibleCol, num);
            //System.out.println("DR2 a rempli la cellule (" + row + ", " + possibleCol + ") avec " + num);
            return true;
        }
        return false;
    }

    private boolean placeInColumn(SudokuGrid grid, int col, int num) {
        int possibleRow = -1;
        for (int row = 0; row < 9; row++) {
            if (grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)) {
                if (possibleRow == -1) {
                    possibleRow = row;
                } else {
                    return false; // Plus d'un emplacement possible
                }
            }
        }
        if (possibleRow != -1) {
            grid.setCell(possibleRow, col, num);
            //System.out.println("DR2 a rempli la cellule (" + possibleRow + ", " + col + ") avec " + num);
            return true;
        }
        return false;
    }

    private boolean placeInBlock(SudokuGrid grid, int block, int num) {
        int possibleRow = -1;
        int possibleCol = -1;
        int startRow = (block / 3) * 3;
        int startCol = (block % 3) * 3;

        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                if (grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)) {
                    if (possibleRow == -1 && possibleCol == -1) {
                        possibleRow = row;
                        possibleCol = col;
                    } else {
                        return false; // Plus d'un emplacement possible dans le bloc
                    }
                }
            }
        }
        if (possibleRow != -1 && possibleCol != -1) {
            grid.setCell(possibleRow, possibleCol, num);
            //System.out.println("DR2 a rempli la cellule (" + possibleRow + ", " + possibleCol + ") avec " + num);
            return true;
        }
        return false;
    }

    private boolean canPlace(SudokuGrid grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid.getCell(row, i) == num || grid.getCell(i, col) == num) {
                //System.out.println("DR2: Ne peut pas placer " + num + " à (" + row + ", " + col + ") - conflit trouvé");
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid.getCell(startRow + i, startCol + j) == num) {
                    //System.out.println("DR2: Ne peut pas placer " + num + " à (" + row + ", " + col + ") - conflit dans le bloc");
                    return false;
                }
            }
        }
        return true;
    }
}
