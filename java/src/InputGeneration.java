import inputOutputService.Output;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InputGeneration {

    private static Random random = new Random();
    private static List<String> lines = new ArrayList<>();
    private static final String PATH = "/home/diego/entrance/input%d.txt";
    private static final Integer[] LENGTHS = {10000, 50000, 100000, 500000, 1000000};

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            Output out = new Output(String.format(PATH, i + 1));

            for (int j = 0; j < LENGTHS[i % 5]; j++) {
                String s = RandomStringUtils.randomAlphabetic(random.nextInt(100) + 1);
                lines.add(s);
            }
            String[] arr = lines.toArray(new String[lines.size()]);
            try {
                out.saveFile(arr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            lines.clear();
        }
    }
}
