package com.ibrahima.sudokusolver;

public class RuleDR1 extends DeductionRule {
    @Override
    public boolean apply(SudokuGrid grid) {
        // Implémentation de la logique de la règle DR1
        //ici, nous vérifions si la règle trouve une case vide unique
        

        boolean progress = false;

        // Parcourt chaque cellule de la grille
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid.getCell(row, col) == 0) {  // Si la cellule est vide
                    int possibleValue = findUniquePossibleValue(grid, row, col);
                    if (possibleValue != -1) {
                        grid.setCell(row, col, possibleValue);
                        progress = true;
                    }
                }
            }
        }

        return progress;  // Retourne true si une case a ete remplie
    }

    /**
     * Méthode auxiliaire qui détermine s'il n'y a qu'une seule valeur possible pour une cellule vide.
     * Retourne la valeur possible si elle est unique, ou -1 sinon.
     */
    private int findUniquePossibleValue(SudokuGrid grid, int row, int col) {
        boolean[] possible = new boolean[10];  // Tableau pour marquer les valeurs possibles (1 à 9)
        for (int i = 1; i <= 9; i++) {
            possible[i] = true;  // Initialise toutes les valeurs comme possibles
        }

        // Marque les valeurs présentes dans la ligne, la colonne et le bloc 3x3
        for (int i = 0; i < 9; i++) {
            possible[grid.getCell(row, i)] = false;  // Ligne
            possible[grid.getCell(i, col)] = false;  // Colonne
            int blockRow = (row / 3) * 3 + i / 3;
            int blockCol = (col / 3) * 3 + i % 3;
            possible[grid.getCell(blockRow, blockCol)] = false;  // Bloc 3x3
        }

        // Cherche une seule valeur possible
        int possibleCount = 0;
        int lastPossibleValue = -1;
        for (int i = 1; i <= 9; i++) {
            if (possible[i]) {
                possibleCount++;
                lastPossibleValue = i;
            }
        }

        // Si une seule valeur est possible, retourne cette valeur, sinon retourne -1
        return possibleCount == 1 ? lastPossibleValue : -1;
    }
}
