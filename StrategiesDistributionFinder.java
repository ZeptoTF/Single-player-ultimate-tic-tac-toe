import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.util.Scanner;

public class StrategiesDistributionFinder {
    public static void main(String[] args) {
        File folder = new File("Strategies_Batch_1");
        File[] folderContents = folder.listFiles();
        String[] fileContents;
        int lines;
        float completion = 0;
        
        String strategy;
        int[] strategiesDistribution = new int[64];
        

        for (File file : folderContents) {
            try {
                lines = (int) Files.lines(file.toPath()).count();
                Scanner scanner = new Scanner(file);
                fileContents = new String[lines];

                for (int i=0; i<lines; i++) {
                    fileContents[i] = scanner.nextLine();
                    strategy = fileContents[i].substring(fileContents[i].lastIndexOf("\t") + 1).trim();
                    strategiesDistribution[strategy.length()-1] += 1;
                }
                
                completion++;

                System.out.println(completion+"/"+folderContents.length+" ("+100*completion/((float)folderContents.length)+"%)");

                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Number of strategies by length:");
        for (int i=27; i<strategiesDistribution.length; i++) {
            if (strategiesDistribution[i]!=0)
                System.out.println(i+"\t"+strategiesDistribution[i]);
        }
    }
}
