import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class StrategiesGenerator_ArrayLists {
    public static void main(String[] args) {
        try {
            FileWriter strategies = new FileWriter("Strategies_Batch_1/Strategies_"+1+".txt");
            BufferedWriter strategiesWriter = new BufferedWriter(strategies);

            ArrayList<Byte> strategy = new ArrayList<Byte>(); //Raccoglie, in ordine, gli indici delle celle che formano una strategia vincente. Gli indici delle celle vanno da 1 a 9 partendo dall'angolo in alto a sinistra e prcedento in ordine di lettura
            int strategiesCounter = 0;
            int strategiesGroup = 0;
            int strategiesGroupSize = 2000000;
            int groupsCap = 500;
            StringBuilder[] writeBuffer = new StringBuilder[strategiesGroupSize];
            for (int i=0; i<writeBuffer.length; i++)
                writeBuffer[i] = new StringBuilder();
            int strategiesCounterForPrinting = 1;
            
            String lastStrategy = "";
            for (char c : lastStrategy.toCharArray()) {
                strategy.add((byte) (c - '0'));
            }

            boolean[][] gameGrid = new boolean[10][10];
            
            boolean flag = false;
            byte a = 0;
            byte b = 0;
            if (strategy.size()!=0) {
                for (byte i=1; i<=9; i++) {
                    gameGrid[i][0] = true;
                }
                for (byte i=3; i<strategy.size(); i++) {
                    gameGrid[strategy.get(i-1)][strategy.get(i)] = true;
                }
                flag = true;
                a = strategy.get(strategy.size()-2);
                b = strategy.getLast();
                gameGrid[a][b] = false;
                strategy.removeLast();
                gameGrid[a][0] = false;
                if (b==9) {
                    if (a==9) {
                        a = strategy.get(strategy.size()-2);
                        b = strategy.getLast();
                        gameGrid[a][b] = false;
                        strategy.removeLast();
                        gameGrid[a][0] = false;
                        b = a;
                        a = strategy.get(strategy.size()-2);
                        gameGrid[a][b] = false;
                        strategy.removeLast();
                        gameGrid[a][0] = false;
                    }
                    else{
                        a = strategy.get(strategy.size()-2);
                        b = strategy.getLast();
                        gameGrid[a][b] = false;
                        strategy.removeLast();
                        gameGrid[a][0]= false;
                    }
                }
                b++;
            }

            Instant start = Instant.now();
            System.out.println(start);

            byte[] initalCells = {1, 2, 5};
            for (byte i : initalCells) {
                if (strategy.size()==0) {
                    strategy.add(i);
                }

                for (byte j=1; j<=9; j++) {
                    if (flag) {
                        i = a;
                        j = b;
                        flag = false;
                    }
                    if (isPlaceable(gameGrid, i, j)==1) {   //Controlla se si può mettere una X in una certa cella
                        gameGrid[i][j] = true; //Mette la X in quella cella
                        strategy.add(j);    //Aggiunge l'indice della cella alla strategia
                        i= j;
                        j= 0;
                    }
                    else if (gameGrid[1][0] &&   //Controlla se ogni sezione ha un tris
                             gameGrid[2][0] &&
                             gameGrid[3][0] &&
                             gameGrid[4][0] &&
                             gameGrid[5][0] &&
                             gameGrid[6][0] &&
                             gameGrid[7][0] &&
                             gameGrid[8][0] &&
                             gameGrid[9][0]) {
                        if (strategiesCounter%strategiesGroupSize==0 && strategiesCounter!=0) {
                            strategiesGroup = (strategiesCounter/strategiesGroupSize);
                            strategiesWriter.close();
                            if (strategiesCounter>=groupsCap*strategiesGroupSize) {
                                Instant end = Instant.now();
                                System.out.println(end);
                                System.out.print(Duration.between(start, end));
                                System.exit(0);
                            }
                            strategies = new FileWriter("Strategies_Batch_1/Strategies_"+(strategiesGroup+1)+".txt");
                            strategiesWriter = new BufferedWriter(strategies);
                        }

                        writeBuffer[strategiesCounter%strategiesGroupSize].append(formatStrategy(strategy));    //Inserisce la strategia nel buffer
                        if (strategiesCounter%strategiesGroupSize==strategiesGroupSize-1) {
                            for (StringBuilder sb: writeBuffer) {
                                strategiesWriter.write(sb.insert(0, strategiesCounterForPrinting).toString()); //Stampa la strategia usata per vincere su Strategies.txt
                                sb.setLength(0);
                                strategiesCounterForPrinting++;
                            }
                        }
                        strategiesCounter++;

                        i = strategy.get(strategy.size()-2);    //Trovata la strategia vincente, torna indietro di almeno una mossa per provare nuove strategie
                        j = strategy.getLast();
                        gameGrid[i][j] = false;
                        strategy.removeLast();
                        gameGrid[i][0] = false;
                        if (j==9) {
                            if (i==9) {                                 //Se si aggiungesse 1 in questo caso, si otterrebbe un errore della tipologia "Out of bounds", quindi bisogna prima tornare indietro di più mosse
                                i = strategy.get(strategy.size()-2);
                                j = strategy.getLast();
                                gameGrid[i][j] = false;
                                strategy.removeLast();
                                gameGrid[i][0] = false;
                                j = i;
                                i = strategy.get(strategy.size()-2);
                                gameGrid[i][j] = false;
                                strategy.removeLast();
                                gameGrid[i][0] = false;
                            }
                            else{
                                i = strategy.get(strategy.size()-2);
                                j = strategy.getLast();
                                gameGrid[i][j] = false;
                                strategy.removeLast();
                                gameGrid[i][0] = false;
                            }
                        }
                    }
                    else if (j==9) {    //Se la X non si può mettere in nessuna cella, modifica la mossa precedente
                        i = strategy.get(strategy.size()-2);
                        j = strategy.getLast();
                        gameGrid[i][j] = false;
                        strategy.removeLast();
                        gameGrid[i][0] = false;
                        if (j==9) {
                            if (i==9) {                                 //Se si aggiungesse 1 in questo caso, si otterrebbe un errore della tipologia "Out of bounds", quindi bisogna prima tornare indietro di più mosse
                                i = strategy.get(strategy.size()-2);
                                j = strategy.getLast();
                                gameGrid[i][j] = false;
                                strategy.removeLast();
                                gameGrid[i][0] = false;
                                j = i;
                                i = strategy.get(strategy.size()-2);
                                gameGrid[i][j] = false;
                                strategy.removeLast();
                                gameGrid[i][0] = false;
                            }
                            else{
                                i = strategy.get(strategy.size()-2);
                                j = strategy.getLast();
                                gameGrid[i][j] = false;
                                strategy.removeLast();
                                gameGrid[i][0] = false;
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

    private static String formatStrategy(ArrayList<Byte> strategy) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t").append(strategy.size()-1).append("\t");
        for (byte i = 0; i < strategy.size(); i++) {
            sb.append(strategy.get(i));
        }
        sb.append("\n");
        return sb.toString();
    }

    public static final int[][][] trisCombinations = {
        {{2, 3}, {4, 7}, {5, 9}},   // Combinazioni per cella 1
        {{1, 3}, {5, 8}},   // Combinazioni per cella 2
        {{1, 2}, {6, 9}, {5, 7}},   // Combinazioni per cella 3
        {{1, 7}, {5, 6}},   // Combinazioni per cella 4
        {{1, 9}, {2, 8}, {3, 7}, {4, 6}},   // Combinazioni per cella 5
        {{3, 9}, {4, 5}},   // Combinazioni per cella 6
        {{1, 4}, {8, 9}, {3, 5}},   // Combinazioni per cella 7
        {{2, 5}, {7, 9}},   // Combinazioni per cella 8
        {{3, 6}, {7, 8}, {1, 5}}    // Combinazioni per cella 9
    };

    public static int isPlaceable(boolean[][] gameGrid, int section, int cell) {
        if (gameGrid[section][0] || gameGrid[section][cell] || gameGrid[cell][0])    // La sezione ha già un tris, la cella è occupata, o la sezione corrispondente alla cella ha già un tris
            return 0;
        else {
            for (int[] combination : trisCombinations[cell - 1]) {
                if (gameGrid[section][combination[0]] && gameGrid[section][combination[1]]) {
                    gameGrid[section][0] = true;  // Si crea un tris
                    break;
                }
            }
            return 1;
        }
    }
}