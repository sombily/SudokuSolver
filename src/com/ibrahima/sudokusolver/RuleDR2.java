package com.ibrahima.sudokusolver;

public class RuleDR2 extends DeductionRule {
    @Override
    public boolean apply(SudokuGrid grid){
        boolean progress = false;

        //Parourir tous ls chiffres de 1 à 9
        for(int num = 1; num <= 9; num++){
            //verifier chaque ligne
            for (int row = 0; row < 9; row++){
                if(placeInRow(grid, row, num)){
                    progress = true;
                }
            }

            //Verifier chaque colonne
            for (int col = 0; col < 9; col++){
                if(placeInColumn(grid, col, num)){
                    progress = true;
                }
            }

            //verifier chaque bloc 3X3
            for(int block = 0; block < 9; block++){
                if(placeInBlock(grid, block, num)){
                    progress = true;
                }
            }
        }
        return progress;
    }

    //Verifier s'il n'ya qu'un seul emplacement possible pour 'num' dans un bloc 3X3
    private boolean placeInRow(SudokuGrid grid, int row, int num){
        int possiblecol = -1;
        for(int col = 0; col < 9; col++){
            if(grid.getCell(row, col) == 0 && canPlace(grid, row, col, num)){
                if(possiblecol == -1){
                    possiblecol = col;
                } else{
                        return false;// Plus d'un emplacement possible
                }
            }
        }
        if(possiblecol != -1){
            grid.setCell(row,possiblecol,num);
            return true;
        }
        return false;
    }

    //Verifier s'il n'y a qu'un seul emplacement possible pour 'num' dans une ligne donne
    private boolean placeInColumn(SudokuGrid grid, int col, int num){
        int possibleRow = -1;
        for(int row = 0; row < 9; row++){
            if (grid.getCell(row, col) ==0 && canPlace(grid, row,col, num)){
                if(possibleRow == -1){
                    possibleRow = row;
                }else{
                    return false; // plus d'emplacement possible
                }
            }
        }
        if(possibleRow != -1){
            grid.setCell(possibleRow,col,num);
            return true;
        }
        return false;
    }

    //verifer s'il n'ya qu'un seul emplancement possible pour 'num' dans un bloc 3x3
    private boolean placeInBlock(SudokuGrid grid, int block, int num){
        int possibleRow = -1;
        int possibleCol = -1;
        int startRow = (block /3) * 3;
        int startCol = (block % 3) * 3;

        for(int row = startRow; row < startRow + 3; row++){
            for(int col = startCol; col < startCol + 3; col++){
                if(grid.getCell(row,col) == 0 && canPlace(grid, row,col, num)){
                    if(possibleRow == -1 && possibleCol == -1){
                        possibleRow = row;
                        possibleCol = col;
                    }else {
                        return false;// plus d'emplacment posssible
                    }
            }   }

        }
        if(possibleRow != -1 && possibleCol != -1){
            grid.setCell(possibleRow,possibleCol,num);
            return true;
        }
        return false;
    }
    //Verifie si 'num' peut être placé dans une cellule sans violer les regles du sudoku
    private boolean canPlace(SudokuGrid grid, int row, int col, int num){
        for(int i = 0; i < 9; i++){
            if(grid.getCell(row,i) == num || grid.getCell(i,col) == num){
                return false;
            }
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) *3;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(grid.getCell(startRow + i, startCol + j) == num){
                    return false;
                }
            }

        }
        return true;
    }

}

