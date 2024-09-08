import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class StrategiesFinder {
    public static void main(String[] args) {
        File folder = new File("Strategies_Batch_1");
        File[] folderContents = folder.listFiles();
        String[] fileContents;
        int lines;
        float completion = 0;
        
        int targetStrategyLength = 31;
        String targetStrategy;
        ArrayList<String> targetStrategies = new ArrayList<String>();
        int numberOfTargetStrategies = 0;

        for (File file : folderContents) {
            try {
                lines = (int) Files.lines(file.toPath()).count();
                Scanner scanner = new Scanner(file);
                fileContents = new String[lines];
                targetStrategies.clear();

                for (int i=0; i<lines; i++) {
                    fileContents[i] = scanner.nextLine();
                    targetStrategy = fileContents[i].substring(fileContents[i].lastIndexOf("\t") + 1).trim();
                    if (targetStrategy.length()==(targetStrategyLength+1)) {
                        targetStrategies.add(fileContents[i].substring(0, fileContents[i].indexOf("\t"))+"    "+targetStrategy);
                        numberOfTargetStrategies++;
                    }
                }
                
                completion++;

                if (targetStrategies.isEmpty()==false) {
                    System.out.println((int)completion+"/"+folderContents.length+"\t("+(100*completion/((float)folderContents.length))+"%)\t\t"+file.getName()+":\t"+targetStrategies.toString()+"\n");
                }
                else {
                    System.out.println((int)completion+"/"+folderContents.length+"\t("+(100*completion/((float)folderContents.length))+"%)");
                }

                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Number of target strategies:\t"+numberOfTargetStrategies+"/1'000'000'000\t("+((float)numberOfTargetStrategies/1000000000f)+"%)");
    }
}
