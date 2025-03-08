import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StrategiesGenerator_ArrayLists {
    public static void main(String[] args) {
        try {
            int newBatch = 25;
            int firstGroup = 1;
            int strategiesGroupSize = 2000000;
            int groupsCap = 5;
            groupsCap = groupsCap + 1 - firstGroup;
            int strategiesCounterForPrinting = strategiesGroupSize * (firstGroup - 1) + 1;

            Files.createDirectories(Paths.get("Strategies_Batch_" + newBatch));

            FileWriter strategies = new FileWriter("Strategies_Batch_" + newBatch + "/Strategies_" + firstGroup + ".txt");
            BufferedWriter strategiesWriter = new BufferedWriter(strategies);

            ArrayList<Integer> strategy = new ArrayList<>();
            int strategiesCounter = 0;
            int strategiesGroup = 0;
            StringBuilder[] writeBuffer = new StringBuilder[strategiesGroupSize];
            for (int i = 0; i < writeBuffer.length; i++)
                writeBuffer[i] = new StringBuilder();

            String lastStrategy = "1121322334353644587795486575967697884989466";
            for (char c : lastStrategy.toCharArray()) {
                strategy.add((int) (c - '0'));
            }

            int[] gameGrid = new int[10]; // Compact representation of the grid

            boolean flag = false;
            int a = 0;
            int b = 0;
            if (!strategy.isEmpty()) {
                for (int i = 1; i <= 9; i++) {
                    gameGrid[i] |= 1; // Mark section as having a tris
                }
                for (int i = 3; i < strategy.size(); i++) {
                    int row = strategy.get(i - 1);
                    int col = strategy.get(i);
                    gameGrid[row] |= (1 << col); // Mark the cell as filled
                }
                flag = true;
                a = strategy.get(strategy.size() - 2);
                b = strategy.get(strategy.size() - 1);
                gameGrid[a] &= ~(1 << b); // Unmark the last cell
                strategy.remove(strategy.size() - 1);
                gameGrid[a] &= ~1; // Unmark section tris
                if (b == 9) {
                    if (a == 9) {
                        a = strategy.get(strategy.size() - 2);
                        b = strategy.get(strategy.size() - 1);
                        gameGrid[a] &= ~(1 << b);
                        strategy.remove(strategy.size() - 1);
                        gameGrid[a] &= ~1;
                        b = a;
                        a = strategy.get(strategy.size() - 2);
                        gameGrid[a] &= ~(1 << b);
                        strategy.remove(strategy.size() - 1);
                        gameGrid[a] &= ~1;
                    } else {
                        a = strategy.get(strategy.size() - 2);
                        b = strategy.get(strategy.size() - 1);
                        gameGrid[a] &= ~(1 << b);
                        strategy.remove(strategy.size() - 1);
                        gameGrid[a] &= ~1;
                    }
                }
                b++;
            }

            ZonedDateTime start = ZonedDateTime.now();
            System.out.println(start);

            int[] initialCells = {1, 2, 5};
            for (int i : initialCells) {
                if (strategy.isEmpty()) {
                    strategy.add(i);
                }

                for (int j = 1; j <= 9; j++) {
                    if (flag) {
                        i = a;
                        j = b;
                        flag = false;
                    }
                    if (isPlaceable(gameGrid, i, j) == 1) {
                        gameGrid[i] |= (1 << j);
                        strategy.add(j);
                        i = j;
                        j = 0;
                    } else if ((gameGrid[1] & 1) != 0 &&
                            (gameGrid[2] & 1) != 0 &&
                            (gameGrid[3] & 1) != 0 &&
                            (gameGrid[4] & 1) != 0 &&
                            (gameGrid[5] & 1) != 0 &&
                            (gameGrid[6] & 1) != 0 &&
                            (gameGrid[7] & 1) != 0 &&
                            (gameGrid[8] & 1) != 0 &&
                            (gameGrid[9] & 1) != 0) {
                        if (strategiesCounter % strategiesGroupSize == 0 && strategiesCounter != 0) {
                            strategiesGroup = (strategiesCounter / strategiesGroupSize);
                            strategiesWriter.close();
                            if (strategiesCounter >= groupsCap * strategiesGroupSize) {
                                ZonedDateTime end = ZonedDateTime.now();
                                System.out.println(end);
                                System.out.print(Duration.between(start, end));
                                System.exit(0);
                            }
                            strategies = new FileWriter("Strategies_Batch_" + newBatch + "/Strategies_" + (strategiesGroup + firstGroup) + ".txt");
                            strategiesWriter = new BufferedWriter(strategies);
                        }

                        writeBuffer[strategiesCounter % strategiesGroupSize].append(formatStrategy(strategy));
                        if (strategiesCounter % strategiesGroupSize == strategiesGroupSize - 1) {
                            for (StringBuilder sb : writeBuffer) {
                                strategiesWriter.write(sb.insert(0, strategiesCounterForPrinting).toString());
                                sb.setLength(0);
                                strategiesCounterForPrinting++;
                            }
                        }
                        strategiesCounter++;

                        i = strategy.get(strategy.size() - 2);
                        j = strategy.get(strategy.size() - 1);
                        gameGrid[i] &= ~(1 << j);
                        strategy.remove(strategy.size() - 1);
                        gameGrid[i] &= ~1;
                        if (j == 9) {
                            if (i == 9) {
                                i = strategy.get(strategy.size() - 2);
                                j = strategy.get(strategy.size() - 1);
                                gameGrid[i] &= ~(1 << j);
                                strategy.remove(strategy.size() - 1);
                                gameGrid[i] &= ~1;
                                j = i;
                                i = strategy.get(strategy.size() - 2);
                                gameGrid[i] &= ~(1 << j);
                                strategy.remove(strategy.size() - 1);
                                gameGrid[i] &= ~1;
                            } else {
                                i = strategy.get(strategy.size() - 2);
                                j = strategy.get(strategy.size() - 1);
                                gameGrid[i] &= ~(1 << j);
                                strategy.remove(strategy.size() - 1);
                                gameGrid[i] &= ~1;
                            }
                        }
                    } else if (j == 9) {
                        i = strategy.get(strategy.size() - 2);
                        j = strategy.get(strategy.size() - 1);
                        gameGrid[i] &= ~(1 << j);
                        strategy.remove(strategy.size() - 1);
                        gameGrid[i] &= ~1;
                        if (j == 9) {
                            if (i == 9) {
                                i = strategy.get(strategy.size() - 2);
                                j = strategy.get(strategy.size() - 1);
                                gameGrid[i] &= ~(1 << j);
                                strategy.remove(strategy.size() - 1);
                                gameGrid[i] &= ~1;
                                j = i;
                                i = strategy.get(strategy.size() - 2);
                                gameGrid[i] &= ~(1 << j);
                                strategy.remove(strategy.size() - 1);
                                gameGrid[i] &= ~1;
                            } else {
                                i = strategy.get(strategy.size() - 2);
                                j = strategy.get(strategy.size() - 1);
                                gameGrid[i] &= ~(1 << j);
                                strategy.remove(strategy.size() - 1);
                                gameGrid[i] &= ~1;
                            }
                        }
                    }
                }
            }
            strategiesWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String formatStrategy(ArrayList<Integer> strategy) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t").append(strategy.size() - 1).append("\t");
        for (int i = 0; i < strategy.size(); i++) {
            sb.append(strategy.get(i));
        }
        sb.append("\n");
        return sb.toString();
    }

    public static final int[][][] trisCombinations = {
        {{2, 3}, {4, 7}, {5, 9}},
        {{1, 3}, {5, 8}},
        {{1, 2}, {6, 9}, {5, 7}},
        {{1, 7}, {5, 6}},
        {{1, 9}, {2, 8}, {3, 7}, {4, 6}},
        {{3, 9}, {4, 5}},
        {{1, 4}, {8, 9}, {3, 5}},
        {{2, 5}, {7, 9}},
        {{3, 6}, {7, 8}, {1, 5}}
    };

    public static int isPlaceable(int[] gameGrid, int section, int cell) {
        if ((gameGrid[section] & 1) != 0 || (gameGrid[section] & (1 << cell)) != 0 || (gameGrid[cell] & 1) != 0)
            return 0;
        else {
            for (int[] combination : trisCombinations[cell - 1]) {
                if (((gameGrid[section] & (1 << combination[0])) != 0) && ((gameGrid[section] & (1 << combination[1])) != 0)) {
                    gameGrid[section] |= 1; // Mark the section as having a tris
                    break;
                }
            }
            return 1;
        }
    }
}