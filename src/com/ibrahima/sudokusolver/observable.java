package com.ibrahima.sudokusolver;
import java.util.ArrayList;
import java.util.List;
public class observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    protected void notifyObservers(SudokuGrid grid) {
        for (Observer observer : observers) {
            observer.update(grid);
        }
    }
}
