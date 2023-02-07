import java.util.Objects;

public class PolicyIteration {
    static int MAX_ITERATION = 1000;
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int r = -3;
        int iterations = 1;
        int dummyIterations = 0;
        int[][] world = {
                {r, -1, 10},
                {-1, -1, -1},
                {-1, -1, -1}
        };
        double[][] values = new double[MAX_ITERATION][9];
        double[][] dummyValues = new double[MAX_ITERATION][9];
        String[][] policies = new String[MAX_ITERATION][9];
        dummyValues[0][0] = 0;
        dummyValues[0][2] = 0;
        //set initial policy
        for (int i = 0; i < 9; i++){
            if (i == 0 || i == 2){
                policies[0][i] = "_";
                continue;
            }
            policies[0][i] = "up";
        }

        for (int m = 1; m < MAX_ITERATION; m++){
            int equal = 0;
            if (m > 1){
                for (int z = 0; z < 9; z++){
                    if (Objects.equals(policies[m - 1][z], policies[m - 2][z])){
                        equal++;
                    }
                }
            }
            if (equal == 9){
                break;
            }

            for (int i = 1; i < MAX_ITERATION; i++) {
                int equality = 0;
                if (i > 1) {
                    for (int j = 0; j < 9; j++) {
                        if (Math.abs(dummyValues[i - 1][j] - dummyValues[i - 2][j]) <= 0.0000000000000001) {
                            equality++;
                        }
                    }
                }
                if (equality == 9) {
                    dummyIterations = i;
                    for (int x = 0; x < 9; x++){
                        values[iterations - 1][x] = dummyValues[dummyIterations - 1][x];
                    }

                    break;
                }
                for (int j = 0; j < 9; j++) {
                    dummyValues[i][j] = findValue(world, dummyValues, policies,iterations - 1, i - 1, j / 3, j % 3);
                }
            }
            for (int i = 0; i < 9; i++){
                policyExtraction(world, values, policies, iterations - 1, i / 3, i % 3);
            }
            iterations++;

        }
        System.out.println("-------------------------------------------");
        for (int i = 0; i < iterations; i++){
            System.out.println("Iteration: \t" + i);
            System.out.println("-------------------------------------------");
            if (i > 0){
                System.out.println("Values:");
                for (int j = 0; j < 9; j++){
                    if(j%3==0&&j>0){
                        System.out.println();
                    }
                    System.out.printf("%.4f\t",values[i - 1][j]);
                }
                System.out.println();
            }

            System.out.println("\npolicy:");
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
    private static double policyExtraction(int[][] world, double[][] values, String[][] policies, int iteration, int x, int y) {
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


    private static double findValue(int[][] world, double[][] dummyValues, String[][] policies,int iteration,  int dummyIterations, int x, int y) {
        if (x * 3 + y == 0 || x * 3 + y == 2) {
            return dummyValues[iteration][x*3+y];
        }
        if (policies[iteration][x * 3 + y].equalsIgnoreCase("up")){
            return Moves.up(world, dummyValues, dummyIterations, x, y);
        } else if (policies[iteration][x * 3 + y].equalsIgnoreCase("down")) {
            return Moves.down(world, dummyValues, dummyIterations, x, y);
        } else if (policies[iteration][x * 3 + y].equalsIgnoreCase("left")) {
            return Moves.left(world, dummyValues, dummyIterations, x, y);
        } else {
            return Moves.right(world, dummyValues, dummyIterations, x, y);
        }
    }

}