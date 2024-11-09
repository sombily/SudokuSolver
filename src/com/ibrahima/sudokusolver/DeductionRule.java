package com.ibrahima.sudokusolver;

import java.util.Observable;

public abstract class   DeductionRule implements Observer {
    public abstract boolean apply(SudokuGrid grid);

    @Override
    public void update(SudokuGrid grid) {
        apply(grid); //Applique la règle lorsque la grille change
    }

}
