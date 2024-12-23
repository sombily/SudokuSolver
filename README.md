# SudokuSolver
**Auteur:** Ibrahima DIALLO (21913239)

Ce projet est un solveur de Sudoku développé en Java dans le cadre d'un projet de Software Engineering. Le solveur
utilise une combinaison de règles de déduction (RuleDR1, RuleDR2 et RuleDR3) et c'est seulement à l'échec de ces trois règles qu'il offre la possibilité d'intervention manuelle.

## Installation

**1. Clonez ce dépôt**
```bash
git clone https://github.com/sombily/SudokuSolver.git
cd SudokuSolver
```
**2.Compilez le programme:**

    javac -d out src/com/ibrahima/sudokusolver/*.java

**Utilisation**
Pour exécuter le solveur avec une grille de test :

    java -cp out com.ibrahima.sudokusolver.SudokuSolver ./Tests/nom_test.txt
Par exemple :

    java -cp out com.ibrahima.sudokusolver.SudokuSolver .\Tests\harder_test.txt
Resulat attendu:
* Difficulté de la grille: Difficile
* Règle appliquées: RuleDR3

**Format des grilles**
Les grilles sont fournies sous forme de fichiers texte. Chaque ligne contient 9 chiffres séparés par des virgules (0 pour une cellule vide).
Exemple:

    5,3,0,0,7,0,0,0,0
    6,0,0,1,9,5,0,0,0
    0,9,8,0,0,0,0,6,0
    8,0,0,0,6,0,0,0,3
    4,0,0,8,0,3,0,0,1
    7,0,0,0,2,0,0,0,6
    0,6,0,0,0,0,2,8,0
    0,0,0,4,1,9,0,0,5
    0,0,0,0,8,0,0,7,9

**Grilles de Test**
Le dossier Tests contient plusieurs grilles pour évaluer les différentes capacités du solveur :
* **easy_hard.txt:** Résolu avec RuleDR1 uniquement.
* **Moyenne.txt**: peut nécessite RuleDR2 pour la résolution.
* **hard_test.txt**: RuleDR3 est nécessaire pour la résolution.
*  ** very_difficult.txt : nécessite une intervention manuelle.




