public class ValueIteration {

    static int MAX_ITERATION = 1000;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int r = -3;
        System.out.println("R = " + r);
        System.out.println("==========================================");
        int iterations = 0;
        int[][] world = {
                {r, -1, 10},
                {-1, -1, -1},
                {-1, -1, -1}
        };
        double[][] values = new double[MAX_ITERATION][9];
        String[][] policies = new String[MAX_ITERATION][9];
//        Initial State
        for (int i = 0; i < 9; i++) {
            if(i==0||i==2){
                values[0][i] = 0;
                policies[0][i] = "_";
                continue;
            }
            values[0][i] = 0;
            policies[0][i] = "start";
        }

        for (int i = 1; i < MAX_ITERATION; i++) {
            int equality = 0;
            if (i > 1) {
                for (int j = 0; j < 9; j++) {
                    if (Math.abs(values[i - 1][j] - values[i - 2][j]) <= 0.0000000000000001) {
                        equality++;
                    }
                }
            }
            if (equality == 9) {
                iterations = i;
                break;
            }
            for (int j = 0; j < 9; j++) {
                values[i][j] = findValue(world, values, policies, i - 1, j / 3, j % 3);
            }
        }
        for (int i = 0; i < iterations; i++){
            System.out.println("Iteration: \t" + i);
            System.out.println("-------------------------------------------");
            System.out.println("Values:");
            for (int j = 0; j < 9; j++){
                if(j%3==0&&j>0){
                    System.out.println();
                }
                System.out.printf("%.4f\t",values[i][j]);
            }
            System.out.println("\nBest policy:");
            for (int j = 0; j < 9; j++){
                if(j%3==0&&j>0){
                    System.out.println();
                }
                System.out.printf("%4s\t",policies[i][j]);
            }
            System.out.println("\n-------------------------------------------");
        }
        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Execution Time: " + totalTime + " Millis");
    }

    private static double findValue(int[][] world, double[][] values, String[][] policies, int iteration, int x, int y) {
        if (x * 3 + y == 0 || x * 3 + y == 2) {
            policies[iteration + 1][x * 3 + y] = "_";
            return values[iteration][x*3+y];
        }
        String move;
        double maxValue;
        double upValue = Moves.up(world, values, iteration, x, y);
        double downValue = Moves.down(world, values, iteration, x, y);
        double leftValue = Moves.left(world, values, iteration, x, y);
        double rightValue = Moves.right(world, values, iteration, x, y);

        if (upValue > downValue) {
            move = "up";
            maxValue = upValue;
        } else {
            move = "down";
            maxValue = downValue;
        }
        if (maxValue < leftValue) {
            maxValue = leftValue;
            move = "left";
        }
        if (maxValue < rightValue) {
            maxValue = rightValue;
            move = "right";
        }

        policies[iteration + 1][x * 3 + y] = move;
        return maxValue;
    }


}