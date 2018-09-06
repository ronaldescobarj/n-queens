package com.company;

import javafx.util.Pair;

import java.util.*;

public class Main {

    static Square[][] initialize(int size) {
        Square[][] matrix = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = new Square('f', 0);
            }
        }
        return matrix;
    }

    static void printMatrix(Square[][] matrix, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(matrix[i][j].getValue() + "" + matrix[i][j].getThreatLevel() + " ");
            }
            System.out.println("");
        }
    }

    static String matrixToString(Square[][] matrix, int size) {
        String state = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                state += matrix[i][j].getValue() + "," + matrix[i][j].getThreatLevel() + " ";
            }
        }
        return state;
    }

    static Square[][] stringToMatrix(String matrix, int size) {
        Square[][] state = new Square[size][size];
        String[] pairs = matrix.split(" ");
        List<String> arrPairs = new ArrayList<>();
        for (int i = 0; i < pairs.length; i++) {
            arrPairs.add(pairs[i]);
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char letter = arrPairs.get(0).split(",")[0].charAt(0);
                int threatLevel = Integer.parseInt(arrPairs.get(0).split(",")[1]);
                state[i][j] = new Square(letter, threatLevel);
                arrPairs.remove(0);
            }
        }
        return state;
    }

    static Square[][] copyMatrix(Square[][] matrix, int size) {
        Square[][] newMatrix = new Square[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                newMatrix[i][j] = new Square(matrix[i][j].getValue(), matrix[i][j].getThreatLevel());
            }
        }
        return newMatrix;
    }

    static void addQueen(Square[][] matrix, int size, int x, int y) {
        int initialX, finalX, tempY;
        if (matrix[y][x].getValue() == 'f') {
            for (int i = 0; i < size; i++) {
                matrix[i][x].setValue('t');
                matrix[i][x].increaseThreatLevel();
                matrix[y][i].setValue('t');
                matrix[y][i].increaseThreatLevel();
            }
            //diagonal from upper left
            initialX = (x - y) > 0 ? (x - y) : 0;
            finalX = (x + size - y - 1) < size - 1 ? (x + size - y - 1) : size - 1;
            tempY = (y - x) > 0 ? (y - x) : 0;
            for (int i = initialX; i <= finalX; i++) {
                matrix[tempY][i].setValue('t');
                matrix[tempY][i].increaseThreatLevel();
                tempY++;
            }
            //diagonal from lower left
            initialX = (x - size + y + 1) > 0 ? (x - size + y + 1) : 0;
            finalX = (x + y) < size - 1 ? (x + y) : size - 1;
            tempY = (y + x) < size - 1 ? (y + x) : size - 1;
            for (int i = initialX; i <= finalX; i++) {
                matrix[tempY][i].setValue('t');
                matrix[tempY][i].increaseThreatLevel();
                tempY--;
            }
            matrix[y][x].setValue('q');
            matrix[y][x].setThreatLevel(9999);
        }
    }

    static void removeQueen(Square[][] matrix, int size, int x, int y) {
        int initialX, finalX, tempY;
        for (int i = 0; i < size; i++) {
            matrix[i][x].decreaseThreatLevel();
            if (matrix[i][x].getThreatLevel() == 0)
                matrix[i][x].setValue('f');
            matrix[y][i].decreaseThreatLevel();
            if (matrix[y][i].getThreatLevel() == 0)
                matrix[y][i].setValue('f');
        }

        //diagonal from upper left
        initialX = (x - y) > 0 ? (x - y) : 0;
        finalX = (x + size - y - 1) < size - 1 ? (x + size - y - 1) : size - 1;
        tempY = (y - x) > 0 ? (y - x) : 0;
        for (int i = initialX; i <= finalX; i++) {
            matrix[tempY][i].decreaseThreatLevel();
            if (matrix[tempY][i].getThreatLevel() == 0)
                matrix[tempY][i].setValue('f');
            tempY++;
        }
        //diagonal from lower left
        initialX = (x - size + y + 1) > 0 ? (x - size + y + 1) : 0;
        finalX = (x + y) < size - 1 ? (x + y) : size - 1;
        tempY = (y + x) < size - 1 ? (y + x) : size - 1;
        for (int i = initialX; i <= finalX; i++) {
            matrix[tempY][i].decreaseThreatLevel();
            if (matrix[tempY][i].getThreatLevel() == 0)
                matrix[tempY][i].setValue('f');
            tempY--;
        }
        matrix[y][x].setValue('f');
        matrix[y][x].setThreatLevel(0);
    }

    static int countFreePositions(Square[][] matrix, int size) {
        int counter = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].getValue() == 'f')
                    counter++;
            }
        }
        return counter;
    }

    static int countFreePositionsOnString(String matrix) {
        return matrix.length() - matrix.replace("f", "").length();
    }

    static int getThreatLevelReduce(Square[][] matrix, int size, int x, int y) {
        int initialX, finalX, tempY;
        int threatLevelReduce = 15; //at least it reduces 15 on horizontal and vertical
        //diagonal from upper left
        initialX = (x - y) > 0 ? (x - y) : 0;
        finalX = (x + size - y - 1) < size - 1 ? (x + size - y - 1) : size - 1;
        threatLevelReduce += (finalX - initialX);
        //diagonal from lower left
        initialX = (x - size + y + 1) > 0 ? (x - size + y + 1) : 0;
        finalX = (x + y) < size - 1 ? (x + y) : size - 1;
        threatLevelReduce += (finalX - initialX);
        return threatLevelReduce;
    }

    static int getTotalThreatLevel(String matrix) {
        int totalThreatLevel = 0;
        String[] pairs = matrix.split(" ");
        for (int i = 0; i < pairs.length; i++) {
            totalThreatLevel += Integer.parseInt(pairs[i].split(",")[1]);
        }
        return totalThreatLevel;
    }

    static int getTotalThreatLevel2(Square[][] matrix, int size) {
        int totalThreatLevel = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                totalThreatLevel += matrix[i][j].getThreatLevel();
            }
        }
        return totalThreatLevel;
    }

    static Pair<Integer, Integer> getBestPositionForAdding(Square[][] matrix, int size, List<String> olderStates) {
        Pair<Integer, Integer> bestPosition = new Pair<>(0, 0);
        int mostFreePositions = -1;
        int currentFreePositions;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].getValue() == 'f') {
                    Square[][] auxMatrix = copyMatrix(matrix, size);
                    addQueen(auxMatrix, size, j, i);
                    currentFreePositions = countFreePositions(auxMatrix, size);
                    if (currentFreePositions > mostFreePositions && !olderStates.contains(matrixToString(auxMatrix, size))) {
                        mostFreePositions = currentFreePositions;
                        bestPosition = new Pair<>(j, i);
                    }
                }
            }
        }
        return bestPosition;
    }

    static Pair<Integer, Integer> getBestQueenForRemoving(Square[][] matrix, int size) {
        Pair<Integer, Integer> bestPosition = new Pair<>(0, 0);
        int mostFreePositions = 0;
        int mostThreatLevelReduce = 0;
        int currentFreePositions, currentThreatLevelReduce;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j].getValue() == 'q') {
                    Square[][] auxMatrix = copyMatrix(matrix, size);
                    removeQueen(auxMatrix, size, j, i);
                    currentFreePositions = countFreePositions(auxMatrix, size);
                    currentThreatLevelReduce = getThreatLevelReduce(auxMatrix, size, j, i);
                    if (currentFreePositions > mostFreePositions || (currentFreePositions == mostFreePositions && currentThreatLevelReduce > mostThreatLevelReduce)) {
                        mostFreePositions = currentFreePositions;
                        mostThreatLevelReduce = currentThreatLevelReduce;
                        bestPosition = new Pair<>(j, i);
                    }
                }
            }
        }
        return bestPosition;
    }

//    static void nQueens(Square[][] matrix, int size) {
//        List<String> olderStates = new ArrayList<>();
//        String currentState = "";
//        int queensPlaced = 0;
//        Pair<Integer, Integer> position;
//        while (queensPlaced <= size) {
//            printMatrix(matrix, size);
//            System.out.println("====================");
//            currentState = matrixToString(matrix, size);
//            if (countFreePositions(matrix, size) != 0) {
//                position = getBestPositionForAdding(matrix, size, olderStates);
//                addQueen(matrix, size, position.getKey(), position.getValue());
//                queensPlaced++;
//            }
//            else {
//                position = getBestQueenForRemoving(matrix, size);
//                removeQueen(matrix, size, position.getKey(), position.getValue());
//                queensPlaced--;
//            }
//            olderStates.add(currentState);
//        }
  //  }

    static List<String> getAdjacents(String initialState, int size) {
        Square[][] mat = stringToMatrix(initialState, size);
        List<String> adjacents = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (mat[i][j].getValue() == 'f') {
                    Square[][] aux = copyMatrix(mat, size);
                    addQueen(aux, size, j, i);
                    adjacents.add(matrixToString(aux, size));
                }
            }
        }
        return adjacents;
    }

    static boolean checkNQueens(String matrix, int size) {
        return matrix.length() - matrix.replace("q", "").length() == size;
    }

    static void printPath(Map<String, String> graph, String finalState, int size) {
        Stack<String> statesPath = new Stack<>();
        String currentState = finalState;
        while (!currentState.equals("")) {
            statesPath.add(currentState);
            currentState = graph.get(currentState);
        }
        int counter = 1;
        while (!statesPath.isEmpty()) {
            System.out.println("Iteracion " + counter);
            printMatrix(stringToMatrix(statesPath.pop(), size), size);
            counter++;
        }
    }

    static String dfs(Square[][] matrix, int size, Map<String, String> graph, String parent, List<Integer> counter) {
        String solved = "";
        String origin = matrixToString(matrix, size);
        graph.put(origin, parent);
        List<String> adjacents = getAdjacents(origin, size);
        for (String adj: adjacents) {
            if (solved.length() == 0 && !graph.containsKey(adj)) {
                counter.add(1);
                graph.put(adj, origin);
                if (checkNQueens(adj, size)) {
                    System.out.println(counter.size());
                    solved = adj;
                }
                else
                    solved = dfs(stringToMatrix(adj, size), size, graph, origin, counter);
            }
        }
        return solved;
    }

    static String dfsCountingFreePositions(Square[][] matrix, int size, Map<String, String> graph, String parent, List<Integer> counter) {
        String solved = "";
        String origin = matrixToString(matrix, size);
        graph.put(origin, parent);
        List<String> adjacents = getAdjacents(origin, size);
        adjacents.sort((a1, a2) -> {
            return countFreePositionsOnString(a1) > countFreePositionsOnString(a2) ? -1 : 1;
        });
        for (String adj: adjacents) {
            if (solved.length() == 0 && !graph.containsKey(adj)) {
                counter.add(1);
                graph.put(adj, origin);
                if (checkNQueens(adj, size)) {
                    solved = adj;
                    System.out.println(counter.size());
                }
                else
                    solved = dfsCountingFreePositions(stringToMatrix(adj, size), size, graph, origin, counter);
            }
        }
        return solved;
    }

    static String dfsCalculatingThreatLevel(Square[][] matrix, int size, Map<String, String> graph, String parent, List<Integer> counter) {
        String solved = "";
        String origin = matrixToString(matrix, size);
        graph.put(origin, parent);
        List<String> adjacents = getAdjacents(origin, size);
        adjacents.sort((a1, a2) -> {
            return getTotalThreatLevel2(stringToMatrix(a1, size), size) > getTotalThreatLevel2(stringToMatrix(a2, size), size) ? -1 : 1;
        });
        for (String adj: adjacents) {
            if (solved.length() == 0 && !graph.containsKey(adj)) {
                counter.add(1);
                graph.put(adj, origin);
                if (checkNQueens(adj, size)) {
                    solved = adj;
                    System.out.println(counter.size());
                }
                else
                    solved = dfsCalculatingThreatLevel(stringToMatrix(adj, size), size, graph, origin, counter);
            }
        }
        return solved;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese tamaño");
        int size = scanner.nextInt();
        Square[][] matrix = initialize(size);
        Map<String, String> graph = new HashMap<String, String>();
        List<Integer> counter = new ArrayList<>();
        String finalState = dfs(matrix, size, graph, "", counter);
        System.out.println("Final state");
        printMatrix(stringToMatrix(finalState, size), size);
        //printPath(graph, finalState, size);
    }
}
