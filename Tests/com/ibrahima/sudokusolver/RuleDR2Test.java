package com.ibrahima.sudokusolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RuleDR2Test {

    private SudokuGrid grid;
    private RuleDR2 ruleDR2;

    @BeforeEach
    void setUp() {
        grid = SudokuGrid.getInstance();
        ruleDR2 = new RuleDR2();

        resetGrid();
    }

    private void resetGrid() {
        // Remet toutes les cellules à zéro pour que la grille soit vide pour chaque test
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                grid.setCell(row, col, 0);
            }
        }
    }

    @Test
    void testApplyRuleDR2_Row() {
        // Prépare une grille où `num = 4` n'a qu'un seul emplacement possible dans la ligne 0
        grid.setCell(0, 0, 1);
        grid.setCell(0, 1, 2);
        grid.setCell(0, 2, 3);
        grid.setCell(0, 4, 5);
        grid.setCell(0, 5, 6);
        grid.setCell(0, 6, 7);
        grid.setCell(0, 7, 8);
        grid.setCell(0, 8, 9);

        // La cellule (0, 3) devrait être 4 car c'est le seul emplacement possible dans la ligne 0
        boolean progress = ruleDR2.apply(grid);

        assertTrue(progress, "La règle DR2 devrait avoir rempli une cellule.");
        assertEquals(4, grid.getCell(0, 3), "La cellule (0, 3) devrait être 4.");
    }
}
