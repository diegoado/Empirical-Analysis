import inputOutputService.Input;
import inputOutputService.Output;
import sorting.SortingImpl;
import sorting.concurrent.ConcurrentMergesort;
import sorting.sequential.SequentialMergesort;

import java.io.IOException;
import java.util.Scanner;

public class Executor {

    private static Input input;
    private static Output output;

    private static String[] words;
    private static SortingImpl<String> sorting;

    private static final String SEQUENTIAL = "sequential";
    private static final String CONCURRENT = "concurrent";

    public static void main(String[] args) {
        int argsLength = args.length;

        if (argsLength == 3) {
            input  = new Input(args[1]);
            output = new Output(args[2]);
        } else if (argsLength == 0 || argsLength > 3) {
            System.err.println("Invalid parameters");
            System.exit(1);
        }
        if (input == null) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Please type the data input (type a empty line to continue program): ");

            StringBuilder lines = new StringBuilder();
            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                if (line.isEmpty())
                    break;
                lines.append(line).append(Input.LINE_BREAK);
            }
            scan.close();
            words = lines.toString().split(Input.LINE_BREAK);
        } else {
            try {
                words = input.getFile();
            } catch (IOException e) {
                System.err.println("Input file not found");
                System.exit(1);
            }
        }
        switch (args[0]) {
            case SEQUENTIAL:
                sorting = new SequentialMergesort<>();
                System.out.print("Executing sequential sorting. ");
                break;
            case CONCURRENT:
                sorting = new ConcurrentMergesort<>();
                System.out.println("Executing concurrent sorting. ");
                break;
            default:
                System.err.println("Invalid parameters");
                System.exit(1);
        }
        sorting.sort(words);

        if (output == null) {
            System.out.println("Output: ");
            for (String string : words)
                System.out.println(string);
        } else {
            try {
                output.saveFile(words);
                System.out.println();
            } catch (IOException e) {
                System.err.println("Output file not found");
                System.exit(1);
            }
        }
    }
}