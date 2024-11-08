package com.ibrahima.sudokusolver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RuleDR1Test {

    private SudokuGrid grid;
    private RuleDR1 ruleDR1;

    @BeforeEach
    void setUp() {
        grid = SudokuGrid.getInstance();
        ruleDR1 = new RuleDR1();
        // Intialiser la grille pour chqaque test

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
    void testApplyRuleDR1_SimpleCase() {
        // Remplir la grille de sorte que DR1 puisse appliquer une déduction
        grid.setCell(0, 0, 5);
        grid.setCell(0, 1, 3);
        grid.setCell(0, 3, 6);
        grid.setCell(0, 4, 7);
        grid.setCell(0, 5, 8);
        grid.setCell(0, 6, 9);
        grid.setCell(0, 7, 1);
        grid.setCell(0, 8, 2);

        // La cellule (0, 2) devrait logiquement être 4
        boolean progress = ruleDR1.apply(grid);

        // Vérifier que la règle a fait une progression
        assertTrue(progress, "La règle DR1 devrait avoir rempli une cellule.");

        // Vérifier que la cellule (0, 2) a été correctement remplie avec la valeur 4
        assertEquals(4, grid.getCell(0, 2), "La cellule (0, 2) devrait être 4.");
    }
}
