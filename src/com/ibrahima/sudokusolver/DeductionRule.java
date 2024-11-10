package com.ibrahima.sudokusolver;

public abstract class   DeductionRule implements Observer {
    public abstract boolean apply(SudokuGrid grid);

    @Override
    public void update(SudokuGrid grid) {
        apply(grid); //Applique la règle lorsque la grille change
    }

}
