 SudokuSolver 
Ce projet est un solveur de Sudoku dévéloppé en Java dans le cadre d'un projet de Software Engeneer. Le Solveur
 utilise une combinaison de règles de deduction (RuleDR1, RuleDR2 et RuleDR3) et de backtracking pour résoudre des grilles de différents niveaux 
 de difficulté. Lorsque toutes les techniques échouent, il offre la possibilité d'intervention manuelle avec des suggestions de valeus.

**Intallation** 

**Clonez ce dépôt**

    git clone https://github.com/sombily/SudokuSolver.git
    cd SudokuSolver

**Compilez le programme:**

    javac -d out src/com/ibrahima/sudokusolver/*.java

Utilisation
Pour exécuter le solveur avec une grille de test, utilisez la commande
suivante:

    java -cp out com.ibrahima.sudokusolver.SudokuSolver ./Tests/nom_test.txt
J'ai mis quelques tests dans le dossier Tests.
par exemple, vous pouvez éxecuter cette commande:

    java -cp out com.ibrahima.sudokusolver.SudokuSolver .\Tests\test1.txt

**Format des grilles**
Les grilles sont fournies sous forme de dichiers texte. Le format attendu est liste de lignes contenant chacune
9 chiffres séparés par des virgules (0 pour une cellule vide).

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
comme j'avais expliqué j'ai inclu quelques test dans le dossier Tests pour evaluer les capacités du solveur:
* **easy.txt:** Résolu avec RuleDR1 uniquement.
* **Moyenne.txt**: peut nécessite RuleDR2 pour la résolution.
* **difficile.txt**: RuleDR3 est nécessaire pour la résolution.
* 


