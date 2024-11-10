package com.ibrahima.sudokusolver;

public enum Difficulty {
    EASY("Facile"),         // Résolu par DR1
    MEDIUM("Moyen"),        // Résolu par DR2
    HARD("Difficile"),      // Résolu par DR3
    VERY_HARD("Très Difficile"); // Non résolu

    private final String description;

    Difficulty(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}