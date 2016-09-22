import inputOutputService.Output;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationData {

    private static Random rand = new Random();
    private static List<String> file = new ArrayList<>();

    private static final Integer[] FILES_LEN = {10000, 50000, 100000, 500000, 1000000};
    private static final String FILES_FOLDER = File.separator + "entrance" + File.separator + "input%d.txt";

    public static void main(String[] args) {
        int l = FILES_LEN.length;
        String path = System.getProperty("user.dir") + FILES_FOLDER;

        for (int i = 0; i < 50; i++) {
            Output out = new Output(String.format(path, i + 1));

            for (int j = 0; j < FILES_LEN[i % l]; j++) {
                String line = RandomStringUtils.randomAlphabetic(rand.nextInt(100) + 1);
                file.add(line);
            }
            String[] arr = file.toArray(new String[file.size()]);
            try {
                out.saveFile(arr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.clear();
        }
    }
}
