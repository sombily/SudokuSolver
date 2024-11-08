package com.ibrahima.sudokusolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleDR3Test {

    private SudokuGrid grid;
    private RuleDR3 ruleDR3;

    @BeforeEach
    public void setUp() {
        grid = SudokuGrid.getInstance();
        ruleDR3 = new RuleDR3();

        //Reinitialiser la grille
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
    void testApplyRuleDR3_SingleOptionInRow(){
        //Preparer une grille avec num = 4 comme seul emplacement dans la ligne 0
        grid.setCell(0,0,1);
        grid.setCell(0,1,2);
        grid.setCell(0,2,3);
        grid.setCell(0,4,5);
        grid.setCell(0,5,6);
        grid.setCell(0,6,7);
        grid.setCell(0,7,8);
        grid.setCell(0,8,9);

        //Appliquer la regle DR3
        boolean progress = ruleDR3.apply(grid);

        //verif que le DR3 a fait la progression
        assertTrue(progress, "La regle DR3 devrait avoir rempli une cellule.");
        //Veifier que la cellule(0,3) a été correctment rempli
        assertEquals(4,grid.getCell(0,3),"La cellule (0, 3) devrait etre 4.");
    }
}
