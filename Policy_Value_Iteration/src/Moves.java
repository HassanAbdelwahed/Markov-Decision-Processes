public class Moves {
    static double GAMMA = 0.99;
    static double up(int[][] world, double[][] values, int iteration, int x, int y) {
        int up = x - 1 < 0 ? x : x - 1;
        int left = y - 1 < 0 ? y : y - 1;
        int right = y + 1 > 2 ? y : y + 1;
        return valueCalculation(0.8, world[up][y], values[iteration][up * 3 + y]) +
                valueCalculation(0.1, world[x][left], values[iteration][x * 3 + left]) +
                valueCalculation(0.1, world[x][right], values[iteration][x * 3 + right]);
    }

    static double down(int[][] world, double[][] values, int iteration, int x, int y) {
        int down = x + 1 > 2 ? x : x + 1;
        int left = y - 1 < 0 ? y : y - 1;
        int right = y + 1 > 2 ? y : y + 1;
        return valueCalculation(0.8, world[down][y], values[iteration][down * 3 + y]) +
                valueCalculation(0.1, world[x][left], values[iteration][x * 3 + left]) +
                valueCalculation(0.1, world[x][right], values[iteration][x * 3 + right]);
    }

    static double right(int[][] world, double[][] values, int iteration, int x, int y) {
        int up = x - 1 < 0 ? x : x - 1;
        int down = x + 1 > 2 ? x : x + 1;
        int right = y + 1 > 2 ? y : y + 1;
        return valueCalculation(0.8, world[x][right], values[iteration][x * 3 + right]) +
                valueCalculation(0.1, world[up][y], values[iteration][up * 3 + y]) +
                valueCalculation(0.1, world[down][y], values[iteration][down * 3 + y]);
    }

    static double left(int[][] world, double[][] values, int iteration, int x, int y) {
        int up = x - 1 < 0 ? x : x - 1;
        int down = x + 1 > 2 ? x : x + 1;
        int left = y - 1 < 0 ? y : y - 1;
        return valueCalculation(0.8, world[x][left], values[iteration][x * 3 + left]) +
                valueCalculation(0.1, world[up][y], values[iteration][up * 3 + y]) +
                valueCalculation(0.1, world[down][y], values[iteration][down * 3 + y]);
    }
    private static double valueCalculation(double T, double R, double V) {
        return T * (R + GAMMA * V);
    }

}
